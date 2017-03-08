<?php

/**
 * Created by PhpStorm.
 * User: Daniel Puente Gabarri
 * Date: 08/03/2017
 * Time: 10:48
 */

require_once "../controller/Cuestionario_CrearNuevo_Controller.php";
require_once "../controller/Cuestionario_Gestionar_Controller.php";

class Cuestionario
{
    const ESTADO_ERROR_PARAMETROS = 4;
    const ESTADO_EXITO = 1;
    const ESTADO_NO_ENCONTRADO = 2;

    public static function get($peticion)
    {
        if (!empty($peticion[0])) {

            $cuestionarioModel = new Cuestionario_Model();
            // Devuelve un array con los cuestionarios de esa Asignatura
            $cuestionarios = $cuestionarioModel->getCuestionariosAsignatura($peticion[0]);
            // Establecemos la respuesta
            http_response_code(APIEstados::ESTADO_OK);
            // Retornamos la informacion
            return
                [
                    "estado" => self::ESTADO_EXITO,
                    "mensaje" => $cuestionarios

                ];
        } else {
            throw new APIException(self::ESTADO_ERROR_PARAMETROS,
                "Error al obtener. Falta el ID de Asignatura " . "<class> " . Cuestionario::class . " </class>",
                APIEstados::ESTADO_UNPROCESSABLE_ENTITY);
        }

    }

    public static function post($peticion)
    {

        if ($peticion[0] == 'duplicar') {

            // TODO IMPLEMENTAR DUPLICAR CUESTIONARIO

        } else {
            // Obtenemos la informacion necesaria para crear ese Cuestionario
            $body = file_get_contents('php://input');
            $cuestionario = json_decode($body, true);

            // Llamamos al controlador correspondiente para que realice la insercion de un nuevo cuestionario
            $cuestionarioModel = new Cuestionario_CrearNuevo_Controller();
            if ($cuestionarioModel->cuestionario_CrearNuevo($cuestionario)) {

                // Establecemos la respuesta indicando que se creo un recurso
                http_response_code(APIEstados::ESTADO_CREATED);
                return
                    [
                        "estado" => self::ESTADO_EXITO,
                        "mensaje" => "Cuestionario creado correctamente"
                    ];

            } else {
                throw new APIException(self::ESTADO_ERROR_PARAMETROS,
                    "Error al añadir/editar un cuestionario " . "<class> " . Cuestionario::class . " </class>",
                    APIEstados::ESTADO_UNPROCESSABLE_ENTITY);
            }
        }

    }

    public static function put($peticion)
    {
        // IGNORAR
    }

    public static function delete($peticion)
    {
        if (!empty($peticion[0])) {

            $cuestionario = new Cuestionario_Gestionar_Controller();
            if ($cuestionario->borrarCuestionario($peticion[0])) {
                // Respuesta OK
                http_response_code(APIEstados::ESTADO_OK);
                // Retornamos la informacion
                return
                    [
                        "estado" => self::ESTADO_EXITO,
                        "mensaje" => "Cuestionario eliminado correctamente"

                    ];
            } else {
                throw new APIException(self::ESTADO_NO_ENCONTRADO,
                    "El cuestionario que se intenta eliminar no existe", APIEstados::ESTADO_NOT_FOUND);
            }

        } else {
            throw new APIException(self::ESTADO_ERROR_PARAMETROS,
                "Error al eliminar un cuestionario. Falta el ID del Cuestionario " . "<class> " . Cuestionario::class . " </class>",
                APIEstados::ESTADO_UNPROCESSABLE_ENTITY);
        }
    }


}