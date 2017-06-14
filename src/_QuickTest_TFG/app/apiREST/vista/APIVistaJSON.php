<?php

/**
 * Created by PhpStorm.
 * User: Daniel
 * Date: 08/03/2017
 * Time: 10:14
 */

require_once "APIVista.php";

/**
 * Clase APIVistaJSON - encargada de proporcionar la salida en formato JSON.
 *
 * @autor Daniel Puente Gabarri.
 */
class APIVistaJSON extends APIVista
{

    public function imprimir($cuerpo)
    {
        if ($this->estado) {
            http_response_code($this->estado);
        }
        header('Content-Type: application/json; charset=utf8');
        echo json_encode($cuerpo, JSON_PRETTY_PRINT);
        exit;
    }
}