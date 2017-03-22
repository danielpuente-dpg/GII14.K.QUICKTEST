<?php

/**
 * Created by PhpStorm.
 * User: Daniel Puente Gabarri
 * Date: 08/03/2017
 * Time: 23:09
 */

if (!empty($_SERVER["DOCUMENT_ROOT"]))
    $URL_GLOBAL = $_SERVER["DOCUMENT_ROOT"];

require_once($URL_GLOBAL . '/_QuickTest_TFG/app/controller/Usuarios_Controller.php');

/**
 * ControlAcesoProfesor
 * Class Usuario, esta clase se comunica con el Controlador para el tratamiento de toda
 * la lógica del acceso y registro de QuickTest
 */
class ControlAccesoProfesor
{
    /**
     * Constantes de estado para los mensajes de salida
     */
    const ESTADO_EXITO = 1;
    const ESTADO_NO_ENCONTRADO = 2;
    const ESTADO_USER_EXISTE = 3;
    const ESTADO_ERROR_PARAMETROS = 4;

    /**
     * Método encargado de tratar la lógica de un petición POST
     * @param $peticion , un array con la lógica a seguir
     * registrar o logearse en QuickTest.
     * @return array, información sobre el estado de la ejecución
     * @throws APIException
     */
    public static function post($peticion)
    {
        if ($peticion[0] == 'registro') {
            return self::registrar();
        } else if ($peticion[0] == 'login') {
            return self::login();
        } else {
            throw new APIException(self::ESTADO_ERROR_PARAMETROS,
                "Error al añadir/editar un cuestionario " . "<class> " . ControlAccesoProfesor::class . " </class>",
                APIEstados::ESTADO_UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * Método encargado de registrar un Usuario para la gestión del cuestionario
     * esta tarea será realizada por el profesor que desea registrarse en la aplicación
     * para la gestión del mismo
     * @return array, información sobre el estado de la ejecución
     * @throws APIException
     */
    private static function registrar()
    {
        $cuerpo = file_get_contents('php://input');
        $usuario = json_decode($cuerpo, true);

        $usuarioController = new Usuarios_Controller();
        if ($usuarioController->registrarNuevoUsuario($usuario['email'], $usuario['password'])) {
            // Respuesta OK
            http_response_code(APIEstados::ESTADO_OK);
            // Retornamos la informacion
            return
                [
                    "estado" => self::ESTADO_EXITO,
                    "mensaje" => "Registro de usuario realizado correctamente"

                ];
        } else {
            throw new APIException(self::ESTADO_USER_EXISTE,
                "Error en su email de usuario, ya existe", APIEstados::ESTADO_METHOD_NOT_ALLOWED);
        }

        // Validar campos - OK
        // Crear usuario - OK
        // Imprimir respuesta - OK
    }

    /**
     * Método encargado de logear a un Usuario, este usuario para poder acceder tendra que
     * haberse registrado. Esta tarea se realizará por el profesor
     * @return array, información sobre el estado de la ejecución
     * @throws APIException
     */
    private static function login()
    {
        $cuerpo = file_get_contents('php://input');
        $usuario = json_decode($cuerpo, true);

        $usuarioController = new Usuarios_Controller();

        // Si es 1, permite el accesp
        if ($usuarioController->login($usuario['email'], $usuario['password'])) {
            // Respuesta OK
            http_response_code(APIEstados::ESTADO_OK);
            // Retornamos la informacion
            return
                [
                    "estado" => self::ESTADO_EXITO,
                    "mensaje" => "Inicio de sesion correctamente"

                ];
            // Sino, es 0, deniega
        } else {
            throw new APIException(self::ESTADO_NO_ENCONTRADO,
                "Los campos son erroneos o no existen", APIEstados::ESTADO_NOT_FOUND);
        }


        // Autenticar - OK
        // Obtener datos de usuario - OK
        // Imprimir respuesta - OK
    }

}