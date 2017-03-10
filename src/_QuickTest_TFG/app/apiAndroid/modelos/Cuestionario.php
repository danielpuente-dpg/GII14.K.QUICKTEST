<?php

/**
 * Created by PhpStorm.
 * User: Daniel Puente Gabarri
 * Date: 08/03/2017
 * Time: 10:48
 */

require_once "../controller/Cuestionario_CrearNuevo_Controller.php";
require_once "../controller/Cuestionario_Gestionar_Controller.php";

/**
 * Class Cuestionario, esta clase se comunica con el Controlador o con el
 * Modelo para el tratamiento de todas las lógicas relacionadas con un
 * cuestionario.
 */
class Cuestionario
{
    /**
     * Constantes de estado para los mensajes de salida
     */
    const ESTADO_EXITO = 1;
    const ESTADO_NO_ENCONTRADO = 2;
    const ESTADO_ERROR_PARAMETROS = 4;

    /**
     * Método encargado de tratar la lógica de un petición GET
     * @param $peticion , identificador de la Asignatura a obtener.
     * @return array, los cuestionarios de esa asignatura en JSON.
     * @throws APIException
     */
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
                "Error al obtener. Falta el ID de Asignatura " . "<class> " . ConstantesCuestionario::class . " </class>",
                APIEstados::ESTADO_UNPROCESSABLE_ENTITY);
        }

    }

    /**
     * Método encargado de tratar la lógica de un petición POST
     * @param $peticion , un array con la lógica a seguir
     * insertar o editar un cuestionario.
     * @return array, información sobre el estado de la ejecución
     * @throws APIException
     */
    public static function post($peticion)
    {

        if ($peticion[0] == 'duplicar') {
            if (isset($peticion[1])) {
                return self::duplicarCuestionario($peticion[1]);
            } else {
                throw new APIException(self::ESTADO_ERROR_PARAMETROS,
                    "Error al obtener. Falta el ID de Cuestionario al duplicar"
                    . "<class> " . ConstantesCuestionario::class . " </class>",
                    APIEstados::ESTADO_UNPROCESSABLE_ENTITY);
            }
        } else if ($peticion[0] == 'insertar') {
            if (isset($peticion[1])) {
                return self::insertarCuestionario();
            } else {
                throw new APIException(self::ESTADO_ERROR_PARAMETROS,
                    "Error al insertar un cuestionario. Falta el ID de Cuestionario " . "<class> " . ConstantesCuestionario::class . " </class>",
                    APIEstados::ESTADO_UNPROCESSABLE_ENTITY);
            }
        } else {
            throw new APIException(self::ESTADO_ERROR_PARAMETROS,
                "Error al intentar insertar o duplicar un cuestionario " . "<class> " . ConstantesCuestionario::class . " </class>",
                APIEstados::ESTADO_UNPROCESSABLE_ENTITY);
        }

    }

    /**
     * Método encargado de tratar la lógica de un petición DELETE
     * @param $peticion , identificador del cuestionario a eliminar
     * @return array, información sobre el estado de la ejecución
     * @throws APIException
     */
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
                "Error al eliminar un cuestionario. Falta el ID del Cuestionario " . "<class> " . ConstantesCuestionario::class . " </class>",
                APIEstados::ESTADO_UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * Método encargado de insertar un Cuestionario, para ello se comunica con el Controlador
     * @return array, información sobre el estado de la ejecución
     * @throws APIException
     */
    private static function insertarCuestionario()
    {
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
                "Error al añadir/editar un cuestionario " . "<class> " . ConstantesCuestionario::class . " </class>",
                APIEstados::ESTADO_UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * Método encargado de duplicar un Cuestionario, para ello se comunica con el Modelo
     * @param $idCuestionario , Identificador del cuestionario a duplicar
     * @return array, información sobre el estado de la ejecución
     * @throws APIException
     */
    private static function duplicarCuestionario($idCuestionario)
    {
        $cuestionarioModel = new Cuestionario_Model();
        $retorno = $cuestionarioModel->duplicarCuestionario($idCuestionario);

        if (isset($retorno)) {
            // Establecemos la respuesta indicando que se creo un recurso
            http_response_code(APIEstados::ESTADO_CREATED);
            return
                [
                    "estado" => self::ESTADO_EXITO,
                    "mensaje" => "Cuestionario nuevo con <id: " . $retorno . "> duplicado correctamente"
                ];
        } else {
            throw new APIException(self::ESTADO_NO_ENCONTRADO,
                "El cuestionario que se intenta duplicar no existe", APIEstados::ESTADO_NOT_FOUND);
        }
    }


}