

<?php

require_once($_SERVER["DOCUMENT_ROOT"] . "/_QuickTest_TFG/lib/ims-blti/OAuth.php");
require_once($_SERVER["DOCUMENT_ROOT"] . "/_QuickTest_TFG/app/controller/LTI_Controller.php");

//The MIT License
//
//Copyright (c) 2007 Andy Smith
//
//Permission is hereby granted, free of charge, to any person obtaining a copy
//of this software and associated documentation files (the "Software"), to deal
//in the Software without restriction, including without limitation the rights
//to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//copies of the Software, and to permit persons to whom the Software is
//furnished to do so, subject to the following conditions:
//
//The above copyright notice and this permission notice shall be included in
//all copies or substantial portions of the Software.
//
//THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//                                           LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
//THE SOFTWARE.


/**
 * @author IMS
 * @modifiedby Alejandro Martínez García <amg0098@alu.ubu.es>
 */



error_reporting(E_ALL & ~E_NOTICE & ~E_DEPRECATED);

// Returns true if this is a Basic LTI message
// with minimum values to meet the protocol
function is_basic_lti_request()
{
    $good_message_type = $_REQUEST["lti_message_type"] == "basic-lti-launch-request";
    $good_lti_version = $_REQUEST["lti_version"] == "LTI-1p0";
    $resource_link_id = $_REQUEST["resource_link_id"];
    if ($good_message_type and $good_lti_version and isset($resource_link_id))
        return (true);
    return false;
}

/**
 * A Trivial memory-based store - no support for tokens
 */
class TrivialOAuthDataStore extends OAuthDataStore
{
    private $consumers = array();

    function add_consumer($consumer_key, $consumer_secret)
    {
        $this->consumers[$consumer_key] = $consumer_secret;
    }

    function lookup_consumer($consumer_key)
    {
        if (strpos($consumer_key, "http://") === 0) {
            $consumer = new OAuthConsumer($consumer_key, "secret", NULL);
            return $consumer;
        }
        if ($this->consumers[$consumer_key]) {
            $consumer = new OAuthConsumer($consumer_key, $this->consumers[$consumer_key], NULL);
            return $consumer;
        }
        return NULL;
    }

    function lookup_token($consumer, $token_type, $token)
    {
        return new OAuthToken($consumer, "");
    }

    // Return NULL if the nonce has not been used
    // Return $nonce if the nonce was previously used
    function lookup_nonce($consumer, $token, $nonce, $timestamp)
    {
        // Should add some clever logic to keep nonces from
        // being reused - for no we are really trusting
        // that the timestamp will save us
        return NULL;
    }

    function new_request_token($consumer)
    {
        return NULL;
    }

    function new_access_token($token, $consumer)
    {
        return NULL;
    }

}

// Basic LTI Class that does the setup and provides utility
// functions
class BLTI
{

    public $valid = false;
    public $complete = false;
    public $message = false;
    public $basestring = false;
    public $info = false;
    public $row = false;
    public $context_id = false;

    // Override context_id

    function __construct($parm = false, $usesession = true, $doredirect = true)
    {

        // If this request is not an LTI Launch, either
        // give up or try to retrieve the context from session
        if (!is_basic_lti_request()) {
            if ($usesession === false)
                return;
            if (strlen(session_id()) > 0) {
                $row = $_SESSION['_basiclti_lti_row'];
                if (isset($row))
                    $this->row = $row;
                $context_id = $_SESSION['_basiclti_lti_context_id'];
                if (isset($context_id))
                    $this->context_id = $context_id;
                $info = $_SESSION['_basic_lti_context'];
                if (isset($info)) {
                    $this->info = $info;
                    $this->valid = true;
                    return;
                }
                $this->message = "Could not find context in session";
                return;
            }
            $this->message = "Session not available";
            return;
        }

        // Insure we have a valid launch
        if (empty($_REQUEST["oauth_consumer_key"])) {
            $this->message = "Missing oauth_consumer_key in request";
            return;
        }
        $oauth_consumer_key = $_REQUEST["oauth_consumer_key"];

        // Find the secret - either form the parameter as a string or
        // look it up in a Database from parameters we are given
        $secret = false;
        $row = false;
        if (is_string($parm)) {
            $secret = $parm;
        } else if (!is_array($parm)) {
            $this->message = "Constructor requires a secret or Database information.";
            return;
        } else {
            $sql = 'SELECT * FROM ' . $parm['table'] . ' WHERE ' . ($parm['key_column'] ? $parm['key_column'] : 'oauth_consumer_key') . '=' . "'" . mysql_real_escape_string($oauth_consumer_key) . "'";
            $result = mysql_query($sql);
            $num_rows = mysql_num_rows($result);

            if ($num_rows != 1) {
                $this->message = "Your consumer is not authorized oauth_consumer_key=" . $oauth_consumer_key;
                return;
            } else {
                while ($row = mysql_fetch_assoc($result)) {
                    $secret = $row[$parms['secret_column'] ? $parms['secret_column'] : 'secret'];
                    $usuarios_Controller = new Usuarios_Controller();
                    $secret=$usuarios_Controller->desencriptarPassword($secret);

                    $context_id = $row[$parms['context_column'] ? $parms['context_column'] : 'context_id'];
                    if ($context_id)
                        $this->context_id = $context_id;
                    $this->row = $row;
                    break;
                }
                if (!is_string($secret)) {
                    $this->message = "Could not retrieve secret oauth_consumer_key=" . $oauth_consumer_key;
                    return;
                }
            }

        }

        // Verify the message signature
        $store = new TrivialOAuthDataStore();
        $store->add_consumer($oauth_consumer_key, $secret);

        $server = new OAuthServer($store);

        $method = new OAuthSignatureMethod_HMAC_SHA1();
        $server->add_signature_method($method);
        $request = OAuthRequest::from_request();

        $this->basestring = $request->get_signature_base_string();

        try {
            $server->verify_request($request);
            $this->valid = true;
        } catch (Exception $e) {
            $this->message = $e->getMessage();
            return;
        }

        // Store the launch information in the session for later
        $newinfo = array();
        foreach ($_POST as $key => $value) {
            if ($key == "basiclti_submit")
                continue;
            if (strpos($key, "oauth_") === false) {
                $newinfo[$key] = $value;
                continue;
            }
            if ($key == "oauth_consumer_key") {
                $newinfo[$key] = $value;
                continue;
            }
        }

        $this->info = $newinfo;
        if ($usesession == true and strlen(session_id()) > 0) {
            $_SESSION['_basic_lti_context'] = $this->info;
            unset($_SESSION['_basiclti_lti_row']);
            unset($_SESSION['_basiclti_lti_context_id']);
            if ($this->row)
                $_SESSION['_basiclti_lti_row'] = $this->row;
            if ($this->context_id)
                $_SESSION['_basiclti_lti_context_id'] = $this->context_id;
        }

        if ($this->valid && $doredirect) {
            $this->redirect();
            $this->complete = true;
        }
    }

    function addSession($location)
    {
        if (ini_get('session.use_cookies') == 0) {
            if (strpos($location, '?') > 0) {
                $location = $location . '&';
            } else {
                $location = $location . '?';
            }
            $location = $location . session_name() . '=' . session_id();
        }
        return $location;
    }

    function isInstructor()
    {
        $roles = $this->info['roles'];
        $roles = strtolower($roles);
        if (!(strpos($roles, "instructor") === false))
            return true;
        if (!(strpos($roles, "administrator") === false))
            return true;
        return false;
    }

    /**
     * Función añadida para obtener el email.
     * @author Alejandro Martínez García.
     * @return bool
     */
    function getUserEmail()
    {
        $email = $this->info['lis_person_contact_email_primary'];
        if (strlen($email) > 0)
            return $email;
        # Sakai Hack
        $email = $this->info['lis_person_contact_emailprimary'];
        if (strlen($email) > 0)
            return $email;
        return false;
    }

    function getUserShortName()
    {
        $email = $this->getUserEmail();
        $givenname = $this->info['lis_person_name_given'];
        $familyname = $this->info['lis_person_name_family'];
        $fullname = $this->info['lis_person_name_full'];
        if (strlen($email) > 0)
            return $email;
        if (strlen($givenname) > 0)
            return $givenname;
        if (strlen($familyname) > 0)
            return $familyname;
        return $this->getUserName();
    }

    function getUserName()
    {
        $givenname = $this->info['lis_person_name_given'];
//		$familyname = $this -> info['lis_person_name_family'];
//		$fullname = $this -> info['lis_person_name_full'];
//		if (strlen($fullname) > 0)
//			return $fullname;
//		if (strlen($familyname) > 0 and strlen($givenname) > 0)
//			return $givenname + $familyname;
        if (strlen($givenname) > 0)
            return $givenname;
//		if (strlen($familyname) > 0)
//			return $familyname;

    }

    function getUserSurname()
    {
        $familyname = $this->info['lis_person_name_family'];

        if (strlen($familyname) > 0)
            return $familyname;
        return $this->getUserEmail();
    }

    /**
     * Función añadida para obtener el idioma.
     * @author Alejandro Martínez García.
     * @return bool
     */
    function getLanguage()
    {
        $language = $this->info['launch_presentation_locale'];


            return $language;

    }

    function getEmail()
    {
        $email = $this->info['lis_person_contact_email_primary'];


        return $email;

    }

    function getConsumerURL()
    {
        $consumerURL = $this->info['launch_presentation_return_url'];


        return $consumerURL;

    }

    function getUserKey()
    {
        $oauth = $this->info['oauth_consumer_key'];
        $id = $this->info['user_id'];
        if (strlen($id) > 0 and strlen($oauth) > 0)
            return $oauth . ':' . $id;
        return false;
    }

    function getUserImage()
    {
        $image = $this->info['user_image'];
        if (strlen($image) > 0)
            return $image;
        $email = $this->getUserEmail();
        if ($email === false)
            return false;
        $size = 40;
        $grav_url = $_SERVER['HTTPS'] ? 'https://' : 'http://';
        $grav_url = $grav_url . "www.gravatar.com/avatar.php?gravatar_id=" . md5(strtolower($email)) . "&size=" . $size;
        return $grav_url;
    }

    function getResourceKey()
    {
        $oauth = $this->info['oauth_consumer_key'];
        $id = $this->info['resource_link_id'];
        if (strlen($id) > 0 and strlen($oauth) > 0)
            return $oauth . ':' . $id;
        return false;
    }

    function getResourceTitle()
    {
        $title = $this->info['resource_link_title'];
        if (strlen($title) > 0)
            return $title;
        return false;
    }

    function getConsumerKey()
    {
        $oauth = $this->info['oauth_consumer_key'];
        return $oauth;
    }

    function getCourseKey()
    {
        if ($this->context_id)
            return $this->context_id;
        $oauth = $this->info['oauth_consumer_key'];
        $id = $this->info['context_id'];
        if (strlen($id) > 0 and strlen($oauth) > 0)
            return $oauth . ':' . $id;
        return false;
    }

    function getCourseName()
    {
        $label = $this->info['context_label'];
        $title = $this->info['context_title'];
        $id = $this->info['context_id'];
        if (strlen($label) > 0)
            return $label;
        if (strlen($title) > 0)
            return $title;
        if (strlen($id) > 0)
            return $id;
        return false;
    }

    /**
     * Función añadida para obtener el idContext.
     * @author Alejandro Martínez García.
     * @return bool
     */
    function get_idContext()
    {

        $id = $this->info['context_id'];
        if (strlen($id) > 0)
            return $id;
        return false;
    }


    function getLis_result_sourcedid()
    {
        $lis_result_sourcedid = $this->info['lis_result_sourcedid'];
        if (strlen($lis_result_sourcedid) > 0)
            return $lis_result_sourcedid;
        return false;
    }

    function getLis_outcome_service_url()
    {
        $lis_outcome_service_url = $this->info['lis_outcome_service_url'];
        if (strlen($lis_outcome_service_url) > 0)
            return $lis_outcome_service_url;
        return false;
    }

    function getSecret()
    {
        $secret = $this->row['secret'];

        if (strlen($secret) > 0)
            return $secret;
        return false;
    }




    function get_LTI_idcuestionario()
    {

        $custom_idcuestionario = $this->info['custom_idcuestionario'];
        if (strlen($custom_idcuestionario) > 0)
            return $custom_idcuestionario;
        return false;
    }

    function imprimir()
    {
        foreach ($this->info as $key => $value) {
            echo $key;
            echo "=";
            echo $value;
            echo "<br>";

        }
    }

    // TODO: Add javasript version if headers are already sent
    function redirect()
    {
        $host = $_SERVER['HTTP_HOST'];
        $uri = $_SERVER['PHP_SELF'];
        $location = $_SERVER['HTTPS'] ? 'https://' : 'http://';
        $location = $location . $host . $uri;
        $location = $this->addSession($location);
        header("Location: $location");
    }

    function dump()
    {
        if (!$this->valid or $this->info == false)
            return "Context not valid\n";
        $ret = "";
        if ($this->isInstructor()) {
            $ret .= "<b>isInstructor() (Profesor)</b> = true\n<br>";
        } else {
            $ret .= "<b>isInstructor() (Profesor)</b> = false\n<br>";
        }
        $ret .= "<b>getUserKey() </b>= " . $this->getUserKey() . "\n<br>";
        $ret .= "<b>getUserEmail() </b>= " . $this->getUserEmail() . "\n<br>";
        $ret .= "<b>getUserShortName()</b> = " . $this->getUserShortName() . "\n<br>";
        $ret .= "<b>getUserName()</b> = " . $this->getUserName() . "\n<br>";
        $ret .= "<b>getUserImage()</b> = " . $this->getUserImage() . "\n<br>";
        $ret .= "<b>getResourceKey() </b>= " . $this->getResourceKey() . "\n<br>";
        $ret .= "<b>getResourceTitle() </b>= " . $this->getResourceTitle() . "\n<br>";
        $ret .= "<b>getCourseName() </b>= " . $this->getCourseName() . "\n<br>";
        $ret .= "<b>getCourseKey() </b>= " . $this->getCourseKey() . "\n<br>";
        $ret .= "<b>getConsumerKey()</b> = " . $this->getConsumerKey() . "\n<br>";
        return $ret;
    }

}

?>
