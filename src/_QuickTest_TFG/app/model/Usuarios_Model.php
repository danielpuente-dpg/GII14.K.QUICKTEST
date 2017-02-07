<?php
require_once "Database.class.php";
/**
 * @author Alejandro Martínez García <amg0098@alu.ubu.es>
 * @version 1.0.
 */

/**
 * Class Usuarios_Model.
 * Implementa el modelo.
 * Envía datos de la BD al controlador, para que éste los envíe a la vista.
 * Extiende de la clase Database.
 */
class Usuarios_Model extends Database
{


//TESTED
    /**
     * Crea un nuevo usuario que pueda usar QuickTest.
     * @param $email . email.
     * @param $consKey . Consumer_Key.
     * @param $secret . Password.
     * @param $linkLTI . Link lti.
     * @return mixed. id nuevo usuario. False si hay error
     */
    function crearUsuarioLTI($email, $consKey, $secret, $linkLTI)
    {

        $link = $this->conectar();
        $query = $this->consultaPreparada("INSERT INTO lti_keys_users
                                          (emailUsuario, oauth_consumer_key, secret, resource_link, created_at)
                                            VALUES    (?, ?, ?, ?, NOW()) ; ");

        $ok = mysqli_stmt_bind_param($query, 'ssss', $emailUsuario, $consumer_key, $shared_secret, $resource_link);

        $emailUsuario = $email;
        $consumer_key = $consKey;
        $shared_secret = $secret;
        $resource_link = $linkLTI;

        $ok = mysqli_stmt_execute($query);


        $query2 = $this->consultaPreparada("SELECT LAST_INSERT_ID(); ");

        $ok = mysqli_stmt_execute($query2);
        $ok = mysqli_stmt_bind_result($query2, $idUsuarioLTI);

        if ($ok == FALSE) {

            echo "Error en la ejecucion de la consulta. <br />";
            echo "error : " . mysqli_stmt_error($query);
            $this->closeFreeStatement($query);
            $this->closeFreeStatement($query2);
            exit();

        } else {

            mysqli_stmt_fetch($query2);
            $this->closeFreeStatement($query);
            $this->closeFreeStatement($query2);

            return $idUsuarioLTI;

        }

    }
//TESTED

    /**
     * Devuelve los datos de un usuario, filtrando por su id.
     * @param $idUsuaLTI . id usuario.
     * @return array|null. datos usuario. False si hay error.
     */
    function getUsuarioLTI_id($idUsuaLTI)
    {
        $this->conectar();
        $query = $this->consultaPreparada("SELECT id_lti_keys, oauth_consumer_key, secret, resource_link
                                            from lti_keys_users
                                            where id_lti_keys=? ;");

        $ok = mysqli_stmt_bind_param($query, 'i', $id_lti_keys);

        $id_lti_keys = $idUsuaLTI;

        $ok = mysqli_stmt_execute($query);
        $ok = mysqli_stmt_bind_result($query, $idUsuarioLTI, $consumer_key, $shared_secret, $resource_link);

        if ($ok == FALSE) {

            echo "Error en la ejecucion de la consulta. <br />";
            echo "error : " . mysqli_stmt_error($query);
            $this->closeFreeStatement($query);
            exit();

        } else {

            $lti_datos = null;

            while (mysqli_stmt_fetch($query)) {

                $lti_datos = array(
                    'id_lti_keys' => $idUsuarioLTI,
                    'consumer_key' => $consumer_key,
                    'shared_secret' => $shared_secret,
                    'resource_link' => $resource_link,
                );
            }

            $this->closeFreeStatement($query);
            return $lti_datos;

        }

    }


    //TESTED
    /**
     * Devuelve los datos de un usuario, filtrando por su email.
     * @param $email . email usuario.
     * @return array|null. datos usuario. False si hay error.
     */
    function getUsuarioLTI_Email($email)
    {
        $this->conectar();
        $query = $this->consultaPreparada("SELECT id_lti_keys, oauth_consumer_key, secret, resource_link
                                            from lti_keys_users
                                            where emailUsuario=? ;");

        $ok = mysqli_stmt_bind_param($query, 's', $emailUsuario);

        $emailUsuario = $email;


        $ok = mysqli_stmt_execute($query);
        $ok = mysqli_stmt_bind_result($query, $idUsuarioLTI, $consumer_key, $shared_secret, $resource_link);

        if ($ok == FALSE) {

            echo "Error en la ejecucion de la consulta. <br />";
            echo "error : " . mysqli_stmt_error($query);
            $this->closeFreeStatement($query);
            exit();

        } else {

            $lti_datos = null;

            while (mysqli_stmt_fetch($query)) {
                $lti_datos = array(
                    'id_lti_keys' => $idUsuarioLTI,
                    'consumer_key' => $consumer_key,
                    'shared_secret' => $shared_secret,
                    'resource_link' => $resource_link,
                );
            }

            $this->closeFreeStatement($query);
            return $lti_datos;

        }

    }


    //TESTED
    /**
     * Devuelve los datos de un usuario, filtrando por su email.
     * @param $email . email usuario.
     * @return mixed. El email existe o  False si hay error.
     */
    function checkEmailExiste($email)
    {
        $link = $this->conectar();
        $query = $this->consultaPreparada("SELECT count(emailUsuario)
                                            from lti_keys_users
                                            where emailUsuario= ? ;");

        $ok = mysqli_stmt_bind_param($query, 's', $emailUsuario);

        $emailUsuario = $email;

        $ok = mysqli_stmt_execute($query);
        $ok = mysqli_stmt_bind_result($query, $numEmails);

        if ($ok == FALSE) {

            echo "Error en la ejecucion de la consulta.";
            echo "error : " . mysqli_stmt_error($query);
            $this->closeFreeStatement($query);
            exit();

        } else {

            mysqli_stmt_fetch($query);
            $this->closeFreeStatement($query);

            return $numEmails;

        }

    }

}

?>