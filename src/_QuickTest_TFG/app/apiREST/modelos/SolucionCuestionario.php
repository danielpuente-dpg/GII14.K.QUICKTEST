<?php

/**
 * Created by PhpStorm.
 * User: Daniel Puente Gabarri
 * Date: 10/03/2017
 * Time: 18:17
 */

if (!empty($_SERVER["DOCUMENT_ROOT"]))
    $URL_GLOBAL = $_SERVER["DOCUMENT_ROOT"];

require_once($URL_GLOBAL . '/_QuickTest_TFG/app/model/Alumno_has_cuestionario_Model.php');
require_once($URL_GLOBAL . '/_QuickTest_TFG/app/model/Alumno_has_respuesta_Model.php');
require_once($URL_GLOBAL . '/_QuickTest_TFG/app/controller/Cuestionario_Resolver_Controller.php');
require_once($URL_GLOBAL . '/_QuickTest_TFG/app/model/Preguntas_Model.php');
require_once($URL_GLOBAL . '/_QuickTest_TFG/app/model/Respuestas_Model.php');
require_once($URL_GLOBAL . '/_QuickTest_TFG/app/controller/LTI_Controller.php');
//Importamos la librería de seguridad OAuth
require_once($URL_GLOBAL . "/_QuickTest_TFG/lib/ims-blti/OAuthBody.php");

/**
 *
 * Class Alumno
 *
 */
class SolucionCuestionario
{

    /**
     * Constantes de estado para los mensajes de salida
     */
    const ESTADO_EXITO = 1;
    const ESTADO_NO_ENCONTRADO = 2;
    const ESTADO_CUESTIONARIO_FINALIZADA = 3;
    const ESTADO_ERROR_PARAMETROS = 4;
    const ESTADO_SOY_PROFESOR = true;


    public static function post($peticion)
    {
        // Si el alumno decide finalizar el cuestionario
        if ($peticion[0] == 'finalizar') {
            return self::finalizarCuestionario();

        } else if ($peticion[0] == 'mostrar') {
            return self::mostrarResultados();

        } else if ($peticion[0] == 'resolver') {
            return self::iniciarCuestionario();

        } else {
            throw new APIException(self::ESTADO_ERROR_PARAMETROS,
                "Error al finalizar un cuestionario " . "<class> " . SolucionCuestionario::class . " </class>",
                APIEstados::ESTADO_UNPROCESSABLE_ENTITY);
        }
    }

    private static function iniciarCuestionario()
    {
        $cuerpo = file_get_contents('php://input');
        $datos = json_decode($cuerpo, true);

        $idCuestionario = $datos['data-idCuestVisualizar'];
        $courseName = $datos['nombreAsig'];
        $activityName = $datos['nombreCuest'];
        $isProfesor = $datos['isProfesor'];
        $idUser = $datos['idUser'];
        $nombreAlu = $datos['nombreAlu'];
        $apeAlu = $datos['apeAlu'];
        $lang = $datos['lang'];
        $idAsig = $datos['idAsig'];
        $ltiConsumerURL = $datos['ltiConsumerURL'];

        $alumno_has_cuestionarioModel = new Alumno_has_cuestionario_Model();
        $cuestionarioResolverController = new Cuestionario_Resolver_Controller();
        $preguntasModel = new Preguntas_Model();
        $respuestasModel = new Respuestas_Model();
        $preguntasVerde = null;
        $preguntasAmarillo = null;

        // Comprobar si es profesor
        if ($isProfesor == 1) {
            // Lógica a seguir si eres profesor
            // Devolvemos la información
            http_response_code(APIEstados::ESTADO_OK);
            // Retornamos la informacion
            return
                [
                    "estado" => self::ESTADO_EXITO,
                    "mensaje" => self::ESTADO_SOY_PROFESOR,
                    "datos" => null
                ];

        } else {
            // Comprobar si ese cuestionario ha sido ya resuelto por ese alumno.
            // Si no esta resuelto, registramos la conexión

            if ($alumno_has_cuestionarioModel->estaResuelto($idUser, $idCuestionario) != 1) {
                // Comprobamos si existe un estado previo de ese cuestionario para dicho alumno
                $resultado = $cuestionarioResolverController->checkSegundoIntento($idUser, $idCuestionario);
                if ($resultado == NULL) {

                    // Registramos la conexión para incrementar el número de alumnos que resuelven
                    // el cuestionario
                    $cuestionarioResolverController->registrarConexion($idCuestionario);

                    // Registramos tambien al usuario
                    $cuestionarioResolverController->registrarAlumno($idUser, $nombreAlu, $apeAlu);

                    // Si es la primera vez que intenta resolver el cuestionario se le permite el
                    // uso de comodines
                    $preguntasVerde = $cuestionarioResolverController->hasComodinVerde($idCuestionario);
                    $preguntasAmarillo = $cuestionarioResolverController->hasComodinAmbar($idCuestionario);

                    // Obtenemos las preguntas para dicho Custionario
                    $preguntasModel->getIDPreguntaTitulo_Cuestionario($idCuestionario);
                    // Para cada pregunta obtenemos sus posibles respuestas
                    foreach ($preguntasModel as $p) {
                        $respuestasPregunta[] = $respuestasModel->getRespuestas($p);
                    }

                    // Sino, devuelve un array con las preguntas ya contestadas
                } else {

                    // Obtenemos las preguntas para dicho Custionario
                    $preguntasModel->getIDPreguntaTitulo_Cuestionario($idCuestionario);

                    // Para cada pregunta obtenemos sus posibles respuestas
                    foreach ($preguntasModel as $p) {
                        $respuestasPregunta[] = $respuestasModel->getRespuestas($p);
                    }

                }

            } else {
                throw new APIException(self::ESTADO_CUESTIONARIO_FINALIZADA,
                    "Error al iniciar un cuestionario " . "<class> " . SolucionCuestionario::class . " </class>
                    el cuestionario ya esta resuelto", APIEstados::ESTADO_UNPROCESSABLE_ENTITY);

            }

            $datos = array(
                "preguntas" => $preguntasModel,
                "respuestas" => $respuestasModel,
                "comodinVerde" => $preguntasVerde,
                "comodinAmarillo" => $preguntasAmarillo
            );

            // Devolvemos la información
            http_response_code(APIEstados::ESTADO_OK);
            // Retornamos la informacion
            return
                [
                    "estado" => self::ESTADO_EXITO,
                    "mensaje" => !self::ESTADO_SOY_PROFESOR,
                    "datos" => $datos
                ];
        }

    }

    private static function finalizarCuestionario()
    {
        // Obtenemos la informacion necesaria sobre el cuestionario
        $body = file_get_contents('php://input');
        $cuestionario = json_decode($body, true);

        $cuestionarioResolverController = new Cuestionario_Resolver_Controller();

        //print_r($cuestionario);


        $idCuestionario = $cuestionario['idCuestionario'];
        $context = $cuestionario['context'];

        //print_r(json_encode($context[0]['lis_result_sourcedid']));


        // Obtenemos las respuestas
        $respuestas = $cuestionario['respuestas'];

        /*foreach ($respuestas as $r) {
            $pregResuelta = $r['idPregunta'];
            $idRespuesta = $r['idRespuesta'];
            $tipoComUsado = $r['tipoComUsado'];
            $idAlumno = $r['idAlumno'];

            $cuestionarioResolverController->guardarCadaRespuesta($idRespuesta, $tipoComUsado, $idAlumno, $pregResuelta);
        }*/

        $idAlumno = $respuestas[0]['idAlumno'];

        $retorno = self::calcularNotaMoodle($idCuestionario, $idAlumno, $context);


        echo $retorno;
        if($retorno > 0){
        // Establecemos la respuesta indicando que se creo un recurso
        http_response_code(APIEstados::ESTADO_OK);
        return
            [
                "estado" => self::ESTADO_EXITO,
                "mensaje" => "Cuestionario finalizado correctamente"
            ];
        }else{
            throw new APIException(self::ESTADO_ERROR_PARAMETROS,
                "Error al finalizar un cuestionario " . "<class> " . SolucionCuestionario::class . " </class>",
                APIEstados::ESTADO_UNPROCESSABLE_ENTITY);
        }

    }

    public static function calcularNotaMoodle($idCuestionario, $idAlumno, $context)
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
        $grade_moodle_format = ($calificObtenida + $premio) / $calificMaxPosible;

        if ($grade_moodle_format < 0) {
            $grade_moodle_format = 0; // moodle no permite calificaciones negativas
        }

        if ($grade_moodle_format > 1) {
            $grade_moodle_format = 1; // moodle no permite calificaciones mayores que 1
        }

        $retorno = self::enviarNotaAMoodle($grade_moodle_format, $context);

        return $retorno;

    }

    private static function enviarNotaAMoodle($grade, $context)
    {

        //Importamos la librería de seguridad OAuth
        require_once($_SERVER["DOCUMENT_ROOT"] . "/_QuickTest_TFG/lib/ims-blti/OAuthBody.php");
        $usuarios_Controller = new Usuarios_Controller();

        //PARAMETROS REQUERIDOS POR LTI
        $lis_outcome_service_url = $context[0]['lis_outcome_service_url'];
        $lis_result_sourcedid = json_encode($context[0]['lis_result_sourcedid']);
        $oauth_consumer_key = $context[0]['oauth_consumer_key'];
        $oauth_consumer_secret = $usuarios_Controller->desencriptarPassword($context[0]['secret']);


        echo $lis_outcome_service_url ;
        echo "\n";
        echo $lis_result_sourcedid;
        echo "\n";
        echo $oauth_consumer_key;
        echo "\n";
        echo $oauth_consumer_secret;
        echo "\n";

        echo $grade;
        echo "\n";

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
        return $response;
    }


    private
    static function mostrarResultados()
    {
        // Obtenemos la informacion necesaria sobre el cuestionario ya resuelto
        $body = file_get_contents('php://input');
        $cuestionario = json_decode($body, true);

        $idCuestionario = $cuestionario['idCuestionario'];
        $idAlu = $cuestionario['idAlu'];
        $idAsig = $cuestionario['idAsig'];

        $cuestionarioResolverController = new Cuestionario_Resolver_Controller();
        $datos = $cuestionarioResolverController->mostarResultados($idCuestionario, $idAlu, $idAsig);
        // Devolvemos la información
        http_response_code(APIEstados::ESTADO_OK);
        // Retornamos la informacion
        return
            [
                "estado" => self::ESTADO_EXITO,
                "mensaje" => $datos
            ];
    }

}