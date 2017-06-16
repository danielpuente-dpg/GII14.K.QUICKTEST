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
require_once($URL_GLOBAL . '/_QuickTest_TFG/app/apiREST/modelos/SolucionCuestionario.php');

/**
 * Class TestSolucionCuestionario, Clase encargada de comprobar el correcto funcionamiento del almacenamiento
 * de una nueva calificacion.
 *
 * @autor Daniel Puente Gabarri.
 */
class TestSolucionCuestionario extends PHPUnit_Framework_TestCase
{

    /**
     * Método encargado de comprobar la correcta insercion de la calificacion de un alumno en un cuestionario.
     */
    public function testObtenerNota()
    {
        $idAlumno = "12345";
        $idCuestionario = 2;
        $retorno = SolucionCuestionario::obtenerNota($idAlumno, $idCuestionario);

        $this->assertEquals($retorno, "0.78");


        // Eliminamos esa nota
        $db = new Database();
        $db->conectar();

        $query = $db->consultaPreparada("DELETE FROM notas WHERE idAlumno = $idAlumno AND idCuestionario = $idCuestionario;");

        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);

    }

    /**
     * Metodo encargado de comprobar la correcta de insertar la calificacion del cuestionario resuelto.
     */
    public function testInsertarNota()
    {
        $idAlumno = "12345";
        $idCuestionario = 2;
        $grade = "0.78";

        $retorno = SolucionCuestionario::insertarNota($idAlumno, $idCuestionario, $grade);

        $this->assertTrue($retorno);
    }

}