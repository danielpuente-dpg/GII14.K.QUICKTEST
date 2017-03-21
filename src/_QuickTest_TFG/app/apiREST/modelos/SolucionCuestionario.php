<?php

/**
 * Created by PhpStorm.
 * User: Daniel Puente Gabarri
 * Date: 10/03/2017
 * Time: 18:17
 */

require_once($_SERVER["DOCUMENT_ROOT"] . '/_QuickTest_TFG/app/model/Alumno_has_cuestionario_Model.php');
require_once($_SERVER["DOCUMENT_ROOT"] . '/_QuickTest_TFG/app/controller/Cuestionario_Resolver_Controller.php');
require_once($_SERVER["DOCUMENT_ROOT"] . '/_QuickTest_TFG/app/model/Preguntas_Model.php');
require_once($_SERVER["DOCUMENT_ROOT"] . '/_QuickTest_TFG/app/model/Respuestas_Model.php');
require_once($_SERVER["DOCUMENT_ROOT"] . '/_QuickTest_TFG/app/controller/LTI_Controller.php');

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

        }else if($peticion[0] == 'resolver'){
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
        $ltiController = new LTI_Controller();
        $idCuestionario = $cuestionario['idCuestionario'];

        foreach ($cuestionario as $c) {
            $idRespuesta = $cuestionario['idRespuesta'];
            $tipoComUsado = $cuestionario['comodin'];
            $idAlumno = $cuestionario['idAlumno'];
            $pregResuelta = $cuestionario['pregunta'];

            $cuestionarioResolverController->guardarCadaRespuesta($idRespuesta, $tipoComUsado, $idAlumno, $pregResuelta);
        }

        $ltiController->prepare_Grade_toMoodle($idCuestionario, $idAlumno);
    }

    private static function mostrarResultados()
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