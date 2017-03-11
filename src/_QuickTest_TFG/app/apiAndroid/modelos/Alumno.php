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


class Alumno
{

    /**
     * Constantes de estado para los mensajes de salida
     */
    const ESTADO_EXITO = 1;
    const ESTADO_NO_ENCONTRADO = 2;
    const ESTADO_ERROR_PARAMETROS = 4;
    const ESTADO_SOY_PROFESOR = true;

    public static function get($peticion)
    {
        // Si el alumno decide iniciar el cuestionario a resolver
        if ($peticion[0] == 'resolver') {
            return self::iniciarCuestionario($peticion);
        } else {
            throw new APIException(self::ESTADO_ERROR_PARAMETROS,
                "Error al resolver un cuestionario " . "<class> " . Alumno::class . " </class>",
                APIEstados::ESTADO_UNPROCESSABLE_ENTITY);
        }
    }

    public static function post($peticion)
    {
        // Si el alumno decide finalizar el cuestionario
        if ($peticion[0] == 'finalizar') {
            return self::finalizarCuestionario();
        } else if ($peticion[0] == 'mostrar') {
            return self::mostrarResultados();
        } else {
            throw new APIException(self::ESTADO_ERROR_PARAMETROS,
                "Error al finalizar un cuestionario " . "<class> " . Alumno::class . " </class>",
                APIEstados::ESTADO_UNPROCESSABLE_ENTITY);
        }
    }

    private static function iniciarCuestionario($peticion)
    {
        $idCuestionario = $peticion['data-idCuestVisualizar'];
        $courseName = $peticion['nombreAsig'];
        $activityName = $peticion['nombreCuest'];
        $isProfesor = $peticion['isProfesor'];
        $idUser = $peticion['idUser'];
        $nombreAlu = $peticion['nombreAlu'];
        $apeAlu = $peticion['apeAlu'];
        $lang = $peticion['lang'];
        $idAsig = $peticion['idAsig'];
        $ltiConsumerURL = $peticion['ltiConsumerURL'];

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

            } else {
                // Obtenemos las preguntas para dicho Custionario
                $preguntasModel->getIDPreguntaTitulo_Cuestionario($idCuestionario);

                // Obtenemos los Id de las preguntas ya contestadas, sino null sino a constestado.
                $respuestasModel = $cuestionarioResolverController->checkSegundoIntento($idCuestionario, $idUser);
                // TODO hacer función que dado un ID Pregunta de el ID de la respuesta, para establecer el titulo
            }

            //TODO Faltaria la condición de que el cuestionario haya sido finalizado y directamente nos muestre los datos.
            // TODO se podria tratar con algo en el FRONT END que almacenemos para indicar que ya esta resuelto.

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
        $idCuestionario  = $cuestionario['idCuestionario'];

        foreach ($cuestionario as $c) {
            $idRespuesta = $cuestionario['idRespuesta'];
            $tipoComUsado = $cuestionario['comodin'];
            $idAlumno = $cuestionario['idAlumno'];
            $pregResuelta = $cuestionario['pregunta'];

            $cuestionarioResolverController->guardarCadaRespuesta($idRespuesta, $tipoComUsado, $idAlumno, $pregResuelta);
        }

        // TODO no seguro que haya que hacer tambien esta tarea
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