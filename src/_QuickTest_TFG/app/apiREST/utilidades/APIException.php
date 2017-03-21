<?php


/**
 * Class APIException - Sera nuestra propia clase para manejar las posibles Excepciones
 * utilazando los códigos de estado correpondientes de HTTP
 */
class APIException extends Exception
{
    public $estado;

    public function __construct($estado, $mensaje, $codigo = 400)
    {
        $this->estado = $estado;
        $this->message = $mensaje;
        $this->code = $codigo;
    }

}