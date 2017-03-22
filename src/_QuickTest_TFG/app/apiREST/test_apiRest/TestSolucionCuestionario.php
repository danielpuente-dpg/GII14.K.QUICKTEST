<?php

/**
 * Created by PhpStorm.
 * User: Daniel Puente Gabarri
 * Date: 22/03/2017
 * Time: 11:34
 */

require_once('../utilidades/Defines.php');

require_once($URL_GLOBAL . '/_QuickTest_TFG/app/model/Database.class.php');
require_once($URL_GLOBAL . '/_QuickTest_TFG/app/controller/Cuestionario_Resolver_Controller.php');

/**
 * Class TestSolucionCuestionario, Clase de Test encargada de comprobar el correcto funcionamiento del controlador.
 * Estos test se realizan sobre el controlador, ya que sobre este se basa el APIREST
 */
class TestSolucionCuestionario extends PHPUnit_Framework_TestCase
{
    /**
     * Método encargado de realizar el test del método iniciar un cuestionario por parte de un alumno
     */
    public function testIniciarCuestionario(){}

    /**
     * Método encargado de realizar el test del método finalizar un cuestionario por parte de un alumno
     */
    public function testFinalizarCuestionario(){

        /*$cuestionarioResolverController = new Cuestionario_Resolver_Controller();

        foreach ($cuestionario as $c) {
            $idRespuesta = $cuestionario['idRespuesta'];
            $tipoComUsado = $cuestionario['comodin'];
            $idAlumno = $cuestionario['idAlumno'];
            $pregResuelta = $cuestionario['pregunta'];

            $cuestionarioResolverController->guardarCadaRespuesta($idRespuesta, $tipoComUsado, $idAlumno, $pregResuelta);
        }*/
    }

    /**
     * Método encargado de realizar el test del método mostrar los resultados de un cuestionario
     * finalizado por parte de un alumno
     */
    public function testMostrarResultados(){

        $idCuestionario = 'idCuestionarioTestApiRest';
        $idAlu = 'idAluTestApiRest';
        $idAsig = 'idAsigTestApiRest';

        $cuestionarioResolverController = new Cuestionario_Resolver_Controller();
        $datos = $cuestionarioResolverController->mostarResultados($idCuestionario, $idAlu, $idAsig);

        $this->assertNotEquals(null, $datos);
    }
}