<?php

/**
 * Created by PhpStorm.
 * User: Daniel
 * Date: 24/04/2017
 * Time: 16:40
 */

if (!empty($_SERVER["DOCUMENT_ROOT"]))
    $URL_GLOBAL = $_SERVER["DOCUMENT_ROOT"];

require_once($URL_GLOBAL . '/_QuickTest_TFG/app/model/Preguntas_Model.php');
require_once($URL_GLOBAL . '/_QuickTest_TFG/app/model/Respuestas_Model.php');

class ObtenerCuestionario
{

    /**
     * Constantes de estado para los mensajes de salida
     */
    const ESTADO_EXITO = 1;
    const ESTADO_NO_ENCONTRADO = 2;
    const ESTADO_ERROR_PARAMETROS = 4;

    public static function get($peticion)
    {
        if (!empty($peticion[0])) {

            $preguntasModel = new Preguntas_Model();
            $respuestasModel = new Respuestas_Model();
            $retorno = array();

            // Obtenemos el Identificador del cuestionario
            $idCuestionario = $peticion[0];
            // Obtenemos todas las preguntas de ese cuestionario
            $preguntas = $preguntasModel->getIDPreguntaTitulo_Cuestionario($idCuestionario);
            // Para cada pregunta de ese cuestionario obtenemos sus respuestas
            foreach($preguntas as $p){

                // Obtenemos las respuestas
                $respuestas = $respuestasModel->getRespuestas($p);
                $datos = array();
                $datos["pregunta"] = $p;
                $datos["respuesta"] = $respuestas;
                $retorno[] = $datos;

            }

            // Establecemos la respuesta
            http_response_code(APIEstados::ESTADO_OK);
            // Retornamos la informacion
            return
                [
                    "estado" => self::ESTADO_EXITO,
                    "mensaje" => $retorno

                ];
        } else {
            throw new APIException(self::ESTADO_ERROR_PARAMETROS,
                "Error al obtener. Falta el ID de Cuestionario " . "<class> " . ObtenerCuestionario::class . " </class>",
                APIEstados::ESTADO_UNPROCESSABLE_ENTITY);
        }

    }

}