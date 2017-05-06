<?php
require_once($_SERVER["DOCUMENT_ROOT"] . "/_QuickTest_TFG/app/model/LTI_Model.php");
require_once($_SERVER["DOCUMENT_ROOT"] . "/_QuickTest_TFG/app/model/Usuarios_Model.php");
require_once($_SERVER["DOCUMENT_ROOT"] . "/_QuickTest_TFG/lib/ims-blti/OAuthBody.php");
/**
 * @author Alejandro Martínez García <amg0098@alu.ubu.es>
 * @version 1.0.
 */

/**
 * Class LTI_Controller.
 * Implementa un controlador.
 * Este controlador interactúa con la librería creada por la IMS para gestionar la seguridad LTI.
 * Se comunica con el modelo
 * y la vista para servir datos del estandar LTI o controlar los accesos.
 */
class LTI_Controller
{
    /**
     * Verifica si se está accediendo a QuickTest desde un LTI_Consumer válido.
     * @return bool. Verdadero o falso si el acceso es o no válido.
     */
    function LTI_valid()
    {

        $lti_Model = new LTI_Model();
        $context = $lti_Model->establish_LTI_Context();

        if ($context->complete)
            exit();
        if (!$context->valid) {
            //Devuelve el mensaje generado por la librería LTI.
            $this->denegarAcceso($context->message);
            return false;

        } else {
              return true;
        }

    }

    /**
     * Función que sirve una vista en la que se informa al usuario de que no se le permite acceder a QuickTest.
     * @param $mensaje. Mensaje de error.
     * @return bool
     */
    function denegarAcceso($mensaje)
    {
        require_once($_SERVER["DOCUMENT_ROOT"] . "/_QuickTest_TFG/app/views/managementView/accesoDenegado_View.php");
        return true;

    }

    /**
     * Comprueba el rol que el usuario tiene en el LTI_Consumer, desde el que ha accedido.
     * @return bool. Verdadero o falso, si es o no profesor.
     */
    function isProfesor()
    {
        $lti_Model = new LTI_Model();
        $context = $lti_Model->establish_LTI_Context();
        return $context->isInstructor();
    }

    /**
     * Devuelve el idCuestionario  que la tarea del LTI_Consumer desde la que se ha accedido, tiene configurado.
     * @return bool. Devuelve el cuestionario, o false, si no está configurado.
     */
    function get_custom_idcuestionario()
    {

        $lti_Model = new LTI_Model();
        $context = $lti_Model->establish_LTI_Context();
        $custom_idcuestionario = $context->get_LTI_idcuestionario();

        return $custom_idcuestionario;
    }

    /**
     * Devuelve el nombre del curso del LTI_Consumer desde el que se ha accedido.
     * @return bool. Nombre del curso, o false, si no está configurado.
     */
    function getCourseName()
    {

        $lti_Model = new LTI_Model();
        $context = $lti_Model->establish_LTI_Context();

        $asigName = $context->getCourseName();

        return $asigName;
    }

    /**
     * Devuelve el id del curso del LTI_Consumer desde el que se ha accedido.
     * @return bool. id del curso, o false, si no está configurado.
     */
    function getContextID()
    {
        $lti_Model = new LTI_Model();
        $context = $lti_Model->establish_LTI_Context();

        $idAsig = $context->get_idContext();

        return $idAsig;
    }

    /**
     * Devuelve el nombre de la tarea del LTI_Consumer desde la que se ha accedido.
     * @return bool. nombre de la tarea o false, si no está configurado.
     */
    function getActivityTitle()
    {
        $lti_Model = new LTI_Model();
        $context = $lti_Model->establish_LTI_Context();

        $activityTitle = $context->getResourceTitle();

        return $activityTitle;
    }

    /**
     * Devuelve el id del usuario del LTI_Consumer que ha accedido a QuickTest.
     * @return bool. id del usuario, o false, si hay error.
     */
    function getUserId()
    {
        $lti_Model = new LTI_Model();
        $context = $lti_Model->establish_LTI_Context();

        $idAlu = $context->getUserKey();

        return $idAlu;

    }
    /**
     * Devuelve el nombre del usuario del LTI_Consumer que ha accedido a QuickTest.
     * @return bool. nombre del usuario, o false, si hay error.
     */
    function getNombreUser()
    {
        $lti_Model = new LTI_Model();
        $context = $lti_Model->establish_LTI_Context();

        $nombreAlu = $context->getUserName();

        return $nombreAlu;

    }
    /**
     * Devuelve el apellido del usuario del LTI_Consumer que ha accedido a QuickTest.
     * @return bool. apellido del usuario, o false, si hay error.
     */
    function getApeUser()
    {
        $lti_Model = new LTI_Model();
        $context = $lti_Model->establish_LTI_Context();

        $apeAlu = $context->getUserSurname();

        return $apeAlu;

    }
    /**
     * Devuelve el idioma empleado por el usuario del LTI_Consumer que ha accedido a QuickTest.
     * @return bool. Idioma del usuario, o false, si hay error.
     */
    function getLanguage()
    {
        $lti_Model = new LTI_Model();
        $context = $lti_Model->establish_LTI_Context();

        $language = $context->getLanguage();

        return $language;

    }
    /**
     * Devuelve el email del usuario del LTI_Consumer que ha accedido a QuickTest.
     * @return bool. Email del usuario, o false, si hay error.
     */
    function getEmail()
    {
        $lti_Model = new LTI_Model();
        $context = $lti_Model->establish_LTI_Context();

        $email = $context->getEmail();

        return $email;

    }

    /**
     * Devuelve la URL del LTI_Consumer desde el que se ha accedido a QuickTest.
     * @return bool. URL, o false, si hay error.
     */
    function getConsumerURL()
    {
        $lti_Model = new LTI_Model();
        $context = $lti_Model->establish_LTI_Context();

        $consumerURL = $context->getConsumerURL();

        return $consumerURL;

    }


    /**
     *Convierte la calificación obtenida por el usuario, a un formato aceptado por
     * el LTI_Consumer. (de 0.00 a 1.00)
     * @param $idCuestionario. id del cuestionario calificado.
     * @param $idAlumno. id del alumno.
     * @return float|int. Calificación en formato válido.
     */
    function prepare_Grade_toMoodle($idCuestionario, $idAlumno)
    {

        $preguntasModel = new Preguntas_Model();
        $alumno_has_cuestionario = new Alumno_has_cuestionario_Model();
        $cuestionario_Controller = new Cuestionario_Resolver_Controller();
        $alumno_has_respuestaModel = new Alumno_has_respuesta_Model();

        //Miramos en que orden ha contestado (los primeros, los del medio, los ultimos)
        $orden = $cuestionario_Controller->calculaOrdenPrimeraVez($idCuestionario);

        //Se registra que el alumno ha finalizado ese cuestionario.
        $alumno_has_cuestionario->insertarAlumno_Cuestionario($idAlumno, $idCuestionario, $orden);
        $calificObtenida = $alumno_has_respuestaModel->getSumaPuntTotal_Alumno($idCuestionario, $idAlumno);
        $calificMaxPosible = $preguntasModel->getPuntMaxima_Cuestionario($idCuestionario);

        //Se calcula una bonificación en función de cuando se ha resuelto el cuestionario.

        if ($orden == 1) { // se bonifica con 1 punto a los primeros en responder

            $premio = $calificMaxPosible / 10;

        } else if ($orden == 2) {// se bonifica con 1/2 punto al grupo central

            $premio = ($calificMaxPosible / 10) / 2;

        } else if ($orden == 3) { // se PENALIZA con -1/2 punto al grupo final

            $premio = -(($calificMaxPosible / 10) / 2);

        }

        //se añade la bonificación, y se convierte a formato moodle.
        $grade_moodle_format = ($calificObtenida + $premio ) / $calificMaxPosible;

        if ($grade_moodle_format < 0) {
            $grade_moodle_format = 0; // moodle no permite calificaciones negativas
        }

        if ($grade_moodle_format > 1) {
            $grade_moodle_format = 1; // moodle no permite calificaciones mayores que 1
        }

        $this->comunicate_Grade_toMoodle_XML($grade_moodle_format);

        return $grade_moodle_format;

    }

    /**
     * Comunica la calificación al LTI_Consumer.
     * Emplea técnicas de cifrado y seguridad proporcionadas por la librería LTI así como código XML.
     * @param $grade. calificación.
     * @throws Exception. Excepción en caso de error.
     */
    function comunicate_Grade_toMoodle_XML($grade)
    {
        //Importamos la librería de seguridad OAuth
        require_once($_SERVER["DOCUMENT_ROOT"] . "/_QuickTest_TFG/lib/ims-blti/OAuthBody.php");

        $lti_Model = new LTI_Model();
        $usuarios_Controller =new Usuarios_Controller();

        $context = $lti_Model->establish_LTI_Context();

        print_r($context);

        //PARAMETROS REQUERIDOS POR LTI
        $lis_outcome_service_url = $context->getLis_outcome_service_url();
        $lis_result_sourcedid = $context->getLis_result_sourcedid();
        $oauth_consumer_key = $context->getConsumerKey();

        $oauth_consumer_secret = $usuarios_Controller->desencriptarPassword($context->getSecret());

        //Tipo de petición a moodle:
        $operacion = "replaceResultRequest"; //Guardar Calificación

        //XML impuesto por LTI para comunicar calificación al LMS
        $bodyReplace = '<?xml version="1.0" encoding="UTF-8"?>

 <imsx_POXEnvelopeRequest xmlns="http://www.imsglobal.org/services/ltiv1p1/xsd/imsoms_v1p0">

  <imsx_POXHeader>

    <imsx_POXRequestHeaderInfo>

      <imsx_version>V1.0</imsx_version>

      <imsx_messageIdentifier>' . (time()) . '</imsx_messageIdentifier>

    </imsx_POXRequestHeaderInfo>

  </imsx_POXHeader>

  <imsx_POXBody>

    <' . $operacion . '>

      <resultRecord>

        <sourcedGUID>

          <sourcedId>' . $lis_result_sourcedid . '</sourcedId>

        </sourcedGUID>

        <result>

          <resultScore>

            <language>en</language>

            <textString>' . $grade . '</textString>

          </resultScore>

        </result>

      </resultRecord>

    </' . $operacion . '>

  </imsx_POXBody>

</imsx_POXEnvelopeRequest>';

        //Se firma la petición con OAuth
        $response = sendOAuthBodyPOST('POST', $lis_outcome_service_url, $oauth_consumer_key, $oauth_consumer_secret, 'application/xml', $bodyReplace);

    }

}

?>