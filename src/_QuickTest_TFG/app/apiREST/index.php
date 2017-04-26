<?php
/**
 * Created by PhpStorm.
 * User: Daniel Puente Gabarri
 * Date: 08/03/2017
 * Time: 10:35
 */

require 'utilidades/APIException.php';
require 'vista/APIVistaJSON.php';
require 'modelos/ControlAccesoProfesor.php';
require 'modelos/GestionCuestionario.php';
require 'modelos/SolucionCuestionario.php';
require 'modelos/ObtenerCuestionario.php';
require 'utilidades/APIEstados.php';

global $RAIZ;
$RAIZ = "D:/xampp/htdocs";

// Constantes de estado
const ESTADO_URL_INCORRECTA = 2;
const ESTADO_EXISTENCIA_RECURSO = 3;
const ESTADO_METODO_NO_PERMITIDO = 4;


$vista = new APIVistaJSON();

// establecemos nuesta gestion de las posibles excepciones producidas
set_exception_handler(function ($exception) use ($vista) {
    $cuerpo = array(
        "estado" => $exception->estado,
        "mensaje" => $exception->getMessage()
    );
    if ($exception->getCode()) {
        $vista->estado = $exception->getCode();
    } else {
        $vista->estado = APIEstados::ESTADO_INTERNAL_SERVER_ERROR;
    }

    $vista->imprimir($cuerpo);
}
);

// Obtenemos la información de la URL sobre la que operar
if (isset($_GET['PATH_INFO']))
    $peticion = explode('/', $_GET['PATH_INFO']);
else
    throw new APIException(ESTADO_URL_INCORRECTA, "Peticion no reconocida");

// Obtener recurso
$recurso = array_shift($peticion);
$recursos_existentes = array('gestionCuestionario', 'controlAccesoProfesor', 'solucionCuestionario', 'obtenerCuestionario');

// Comprobar si existe el recurso
if (!in_array($recurso, $recursos_existentes)) {
    throw new APIException(ESTADO_EXISTENCIA_RECURSO,
        "Recurso no existente");
}

// La operacion a realizar la convertimos en Mayusculas para poder trabajar
$metodo = strtolower($_SERVER['REQUEST_METHOD']);

// Filtrar método
switch ($metodo) {

    case 'get':
    case 'post':
    case 'put':
    case 'delete':

        if (method_exists($recurso, $metodo)) {
            // Realiza la llamada la metodo correspondiente y lo retorna
            // De esta manera lo hacemos generico para que sepa a que tiene que llamar
            // sin realizar ninguna especificacion
            $respuesta = call_user_func(array($recurso, $metodo), $peticion);
            $vista->imprimir($respuesta);
            break;
        }
    default:
        // Método no aceptado
        $vista->estado = APIEstados::ESTADO_METHOD_NOT_ALLOWED;
        $cuerpo = [
            "estado" => ESTADO_METODO_NO_PERMITIDO,
            "mensaje" => utf8_encode("Metodo no permitido")
        ];
        $vista->imprimir($cuerpo);

}
