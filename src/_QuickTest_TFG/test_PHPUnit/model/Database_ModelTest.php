<?php
require_once $_SERVER["DOCUMENT_ROOT"] . "/app/model/Database.class.php";


/**
 * @author Alejandro Martínez García
 * Class Database_ModelTest
 * Clase que contiene los test que comprueban el modelo Database_Model.
 */
class Database_ModelTest extends PHPUnit_Framework_TestCase {

    /**
     * Test que comprueba la conexión con la base de datos
     */
    public function testConexionConBaseDatos()
    {
        $db = new Database();

        $conexion = $db->conectar();

        //mysqli devueve un object si la conexión es satisfactoria y False si no lo es
        $this->assertInternalType('object', $conexion);
     }

    /**
     * Test que comprueba la conexión con la base de datos de LTI
     */
    public function testConexionLTI()
    {
        $db = new Database();

        $conexion = $db->conectarLTI();


        //mysqli devueve un Resource si la conexión es satisfactoria y False si no lo es
        $this->assertInternalType('resource', $conexion);
     }

    /**
     * Test que comprueba si se crean sentencias preparadas.
     */
    public function testConsultaPreparada_Correcta()
    {
        $db = new Database();
         $db->conectar();

        $resultado = $db->consultaPreparada("SELECT * FROM PREGUNTA ; ");


        //mysqli devueve un Resource si la conexión es satisfactoria y False si no lo es
        $this->assertInternalType('object', $resultado);
    }

    /**
     * Test que comprueba si se rechazan sentencias preparadas erroneas.
     */
    public function testConsultaPreparada_Incorrecta()
    {
        $db = new Database();
        $db->conectar();

        $resultado = $db->consultaPreparada("SELECT * FROM TABLAQUENOEXISTE ; ");


        //mysqli devueve un Resource si la conexión es satisfactoria y False si no lo es
        $this->assertEquals(false, $resultado);
    }

}
 