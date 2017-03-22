<?php

/**
 * Created by PhpStorm.
 * User: Daniel Puente Gabarri
 * Date: 21/03/2017
 * Time: 12:20
 */

require_once('../utilidades/Defines.php');

require_once($URL_GLOBAL . '/_QuickTest_TFG/app/model/Database.class.php');
require_once($URL_GLOBAL . '/_QuickTest_TFG/app/apiREST/test_apiRest/TablaCuestionario.php');
require_once($URL_GLOBAL . '/_QuickTest_TFG/app/model/Cuestionario_Model.php');
require_once($URL_GLOBAL . '/_QuickTest_TFG/app/controller/Cuestionario_CrearNuevo_Controller.php');

/**
 * Class TestGestionCuestionario, Clase de Test encargada de comprobar el correcto funcionamiento del controlador.
 * Estos test se realizan sobre el controlador, ya que sobre este se basa el APIREST
 */
class TestGestionCuestionario extends PHPUnit_Framework_TestCase
{

    /**
     * Método encargado de realizar el test del método insertar un nuevo cuestionario
     */
    public function testInsertarCuestionario()
    {
        $cuestionario = array(
            "cuestTitulo" => "Cuestionario de test",
            "correctorVerdeTrue" => "0.5",
            "correctorVerdeFalse" => "-0.5",
            "correctorAmarilloTrue" => "0.5",
            "correctorAmarilloFalse" => "0.0",
            'idCuestDuplicar' => "7",
            'idCuestEditar' => "7",
            'isEditar' => "7",
            'isDuplicar' => "7",
            'countPreguntas' => "7",
            "idAsig" => "112233",
            "nombreAsig" => "Nombre asignatura de test",
            "isProfesor" => "1",
            "nombreAlu" => "Profesor Test",
            "apeAlu" => "Apellido Profesor Test",
            "idUser" => "fghjkljhg",
            "lang" => "es",
            "emailLTI" => "emailprofesor",
            "launch_presentation_return_url" => "",
            "pregRes1" => array("pmax" => "1",
                "title" => "Pregunta test",
                "res" => array(" Respuesta test"),
                "correcta" => "1"));


        $cuestionario_CrearNuevo_Controller = new Cuestionario_CrearNuevo_Controller();
        $result = $cuestionario_CrearNuevo_Controller->cuestionario_CrearNuevo($cuestionario);

        $this->assertNotEmpty($result);
    }

    /**
     * Método encargado de realizar el test del método obtener un nuevo cuestionario
     */
    public function testObtenerCuestionario()
    {
        // No existe un metodo sobre el controlador asi que lo comprobamos sobre la propia BD
        // Obtenemos el titulo del cuestionario que hemos añadido anteriormente
        $mysqli = new mysqli("localhost", "root", "", "quicktest_tfg");

        $resultado = $mysqli->query("SELECT " . TablaCuestionario::TITULO .
                        " FROM " . TablaCuestionario::NOMBRE_TABLA .
                        " ORDER BY " . TablaCuestionario::ID_CUESTIONARIO .
                        " DESC LIMIT 1" );

        $fila = $resultado->fetch_row();
        $titulo = $fila[0];

        // Comprobamos que tenga el mismo titulo de cuestionario
        $this->assertEquals("Cuestionario de test", $titulo);
    }

    /**
     * Método encargado de realizar el test del método duplicar un nuevo cuestionario
     */
    public function testDuplicarCuestionario(){

        // Obtenemos el Id del cuestionario a duplicar
        $mysqli = new mysqli("localhost", "root", "", "quicktest_tfg");

        $resultado = $mysqli->query("SELECT " . TablaCuestionario::ID_CUESTIONARIO .
                    " FROM " . TablaCuestionario::NOMBRE_TABLA .
                    " ORDER BY " . TablaCuestionario::ID_CUESTIONARIO .
                    " DESC LIMIT 1");

        $fila = $resultado->fetch_row();
        $idCuestionario = $fila[0];

        $cuestionario_Gestionar_Controller = new Cuestionario_Gestionar_Controller();
        $result = $cuestionario_Gestionar_Controller->duplicarCuestionarioExistente($idCuestionario);

        $this->assertEquals(null, $result);

    }

    /**
     * Método encargado de realizar el test del método borrar un nuevo cuestionario
     */
    public function testBorrarCuestionario(){

        // Obtenemos los dos Id de los cuestionarios añadidos anteriormente
        $mysqli = new mysqli("localhost", "root", "", "quicktest_tfg");

        $resultado = $mysqli->query("SELECT " . TablaCuestionario::ID_CUESTIONARIO .
                        " FROM " . TablaCuestionario::NOMBRE_TABLA .
                        " ORDER BY " . TablaCuestionario::ID_CUESTIONARIO .
                        " DESC LIMIT 2");

        $fila = $resultado->fetch_row();
        $idCuestionario = $fila[0];
        $fila = $resultado->fetch_row();
        $idCuestionario2 = $fila[0];

        // Instanciamos y borramos los cuestionatios
        $cuestionario_Gestionar_Controller = new Cuestionario_Gestionar_Controller();

        $return = $cuestionario_Gestionar_Controller->borrarCuestionario($idCuestionario);
        $this->assertTrue($return);

        $return = $cuestionario_Gestionar_Controller->borrarCuestionario($idCuestionario2);
        $this->assertTrue($return);
    }

}