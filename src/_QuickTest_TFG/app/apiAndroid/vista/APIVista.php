<?php

/**
 * Created by PhpStorm.
 * User: Daniel Puente Gabarri
 * Date: 08/03/2017
 * Time: 10:10
 */
abstract class APIVista
{
    // Se encargará de almacenar el códido correspondiente al error producido
    public $estado;
    // Método encargado de enviar los datos en el formato deseado
    public abstract function imprimir($cuerpo);
}