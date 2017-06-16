<?php

/**
 * Created by PhpStorm.
 * User: Daniel
 * Date: 16/06/2017
 * Time: 17:37
 */

require_once('../utilidades/Defines.php');

require_once($URL_GLOBAL . '/_QuickTest_TFG/app/model/Database.class.php');
require_once($URL_GLOBAL . '/_QuickTest_TFG/app/apiREST/utilidades/APIEstados.php');
require_once($URL_GLOBAL . '/_QuickTest_TFG/app/apiREST/modelos/ObtenerCuestionario.php');
require_once($URL_GLOBAL . '/_QuickTest_TFG/app/model/Preguntas_Model.php');
require_once($URL_GLOBAL . '/_QuickTest_TFG/app/model/Respuestas_Model.php');

/**
 * Clase TestObtenerCuestionario encargada de comprobar el correcto funcionamiento de la clase
 * ObtenerCuestionario.
 *
 * @author Daniel Puente Gabarri.
 */
class TestObtenerCuestionario extends PHPUnit_Framework_TestCase
{
    /**
     * Metodo encargado de comprobar el correcto funcionamiento en la obtencion de un cuestionario.
     */
    public function testObtenerCuestionario()
    {
        $idCuestionario = 2;

        $retorno = ObtenerCuestionario::getCuestionario($idCuestionario);

        $this->assertEquals(1, $retorno['estado']);
        $this->assertEmpty($retorno['mensaje']);

    }

    /**
     * Metodo encargado de comprobar el correcto funcionamiento en la obtencion del comodin verde.
     */
    public function testComodinVerde()
    {
        $idCuestionario = 1;

        $retorno = ObtenerCuestionario::getComodinVerde($idCuestionario);

        $this->assertEquals(1, $retorno['estado']);
        $this->assertNotEmpty($retorno['mensaje']);
    }

    /**
     * Metodo encargado de comprobar el correcto funcionamiento en la obtencion del comodin ambar.
     */
    public function testComodinAmbar()
    {
        $idCuestionario = 1;

        $retorno = ObtenerCuestionario::getComodinAmbar($idCuestionario);

        $this->assertEquals(1, $retorno['estado']);
        $this->assertNotEmpty($retorno['mensaje']);
    }
    /**
     * Metodo encargado de comprobar el correcto funcionamiento en la obtencion de la retroalimentacion
     * sobre como se ha resuelto el cuestionario.
     */
    public function testFeedBack()
    {
        $idCuestionario = 1;
        $idAlu = 2;

        $retorno = ObtenerCuestionario::getFeedBack($idCuestionario, $idAlu);

        $this->assertEquals(1, $retorno['estado']);
        $this->assertNotEmpty($retorno['mensaje']);
    }
    /**
     * Metodo encargado de comprobar el correcto funcionamiento en la del estado de un cuestionario
     * para un alumno.
     */
    public function testEstadoCustionario()
    {
        $idCuestionario = 1;
        $idUser = 2;
        $retorno = ObtenerCuestionario::getEstadoCuestionario($idUser, $idCuestionario);

        $this->assertEquals(1, $retorno['estado']);
        $this->assertEmpty($retorno['mensaje']);
    }


}