<?php

/**
 * Created by PhpStorm.
 * User: Daniel Puente Gabarri
 * Date: 08/03/2017
 * Time: 23:09
 */

require_once "../controller/Usuarios_Controller.php";

class Usuario
{

    const ESTADO_EXITO = 1;
    const ESTADO_NO_ENCONTRADO = 2;
    const ESTADO_USER_EXISTE = 3;
    const ESTADO_ERROR_PARAMETROS = 4;

    public static function post($peticion)
    {
        if ($peticion[0] == 'registro') {
            //TODO metodo registrar
            return self::registrar();
        } else if ($peticion[0] == 'login') {
            // TODO metodo login
            return self::login();
        } else {
            throw new APIException(self::ESTADO_ERROR_PARAMETROS,
                "Error al añadir/editar un cuestionario " . "<class> " . ConstantesCuestionario::class . " </class>",
                APIEstados::ESTADO_UNPROCESSABLE_ENTITY);
        }
    }

    private static function registrar()
    {
        $cuerpo = file_get_contents('php://input');
        $usuario = json_decode($cuerpo, true);

        $usuarioController = new Usuarios_Controller();
        if ($usuarioController->registrarNuevoUsuario($usuario['email'], $usuario['password'])) {
            // TODO devuelve una vista del contenido, seria podria hacer un metodo aparte para la recoleccion de esta informacion
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
        // Crear usuario - TODO Si es preciso
        // Imprimir respuesta - OK
    }

    private static function login()
    {
        $cuerpo = file_get_contents('php://input');
        $usuario = json_decode($cuerpo, true);

        $usuarioController = new Usuarios_Controller();

        // Si es 1, permite el accesp
        if ($usuarioController->login($usuario['email'], $usuario['password']) == 1) {

            // TODO devuelve una vista del contenido, seria podria hacer un metodo aparte para la recoleccion de esta informacion
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


        // Autenticar - Ok
        // Obtener datos de usuario - TODO Si es preciso
        // Imprimir respuesta - OK
    }

}