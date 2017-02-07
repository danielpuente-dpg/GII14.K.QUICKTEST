<?php

require_once $_SERVER["DOCUMENT_ROOT"] . "/app/model/Database.class.php";
require_once $_SERVER["DOCUMENT_ROOT"] . "/app/model/Alumno_Model.php";
require_once $_SERVER["DOCUMENT_ROOT"] . "/app/model/Cuestionario_Model.php";
require_once $_SERVER["DOCUMENT_ROOT"] . "/app/model/Respuestas_Model.php";
require_once $_SERVER["DOCUMENT_ROOT"] . "/app/model/Preguntas_Model.php";
require_once $_SERVER["DOCUMENT_ROOT"] . "/app/model/Usuarios_Model.php";



/**
 * @author Alejandro Martínez García
 * Class Usuarios_ModelTest
 * Clase que contiene los test que comprueban el modelo Usuarios_Model.
 */
class Usuarios_ModelTest extends PHPUnit_Framework_TestCase
{

    /**
     * Test que comprueba si la BD, inserta un usuario nuevo con los parametros LTI
     */
    public function testCrearUsuarioLTI()
    {

        $db = new Database();
        $db->conectar();
       // $lti_Model = new LTI_Model();
        $usuarios_Model = new Usuarios_Model();

        $email="email@email.com";
        $consumer_key ="consumer_key12345";
        $secret="clavePersonal";
        $linkLTI="localhost/_QuickTest_TFG/index.php";

        //llamamos al método que queremos comprobar
        $idNuevoUsuario = $usuarios_Model->crearUsuarioLTI($email,$consumer_key,$secret,$linkLTI);


        $this->assertNotEmpty($idNuevoUsuario);

        //Restablecemos la BD borrando el ejemplo
        $query = $db->consultaPreparada("DELETE FROM lti_keys_users WHERE id_lti_keys = $idNuevoUsuario; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);


    }

    /**
     * Test que comprueba si la BD, devuelve el usuario pedido por su id.
     */
    public function testGetUsuarioLTI_id()
    {

        $db = new Database();
        $db->conectar();
        //$lti_Model = new LTI_Model();
        $usuarios_Model = new Usuarios_Model();


        $email="email@email.com";
        $consumer_key ="consumer_key12345";
        $secret="clavePersonal";
        $linkLTI="localhost/_QuickTest_TFG/index.php";

        //creamos un nuevo usuario
        $idNuevoUsuario = $usuarios_Model->crearUsuarioLTI($email,$consumer_key,$secret,$linkLTI);

        //comprobamos el metodo
        $lti_datos = $usuarios_Model->getUsuarioLTI_id($idNuevoUsuario);


        $this->assertEquals($lti_datos['id_lti_keys'],$idNuevoUsuario);
        $this->assertEquals($lti_datos['consumer_key'],$consumer_key);
        $this->assertEquals($lti_datos['resource_link'],$linkLTI);

        //Restablecemos la BD borrando el ejemplo
        $query = $db->consultaPreparada("DELETE FROM lti_keys_users WHERE id_lti_keys = $idNuevoUsuario; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);
    }

    /**
     * Test que comprueba si la BD, devuelve el usuario pedido por su email.
     */
    public function testGetUsuarioLTI_Email()
    {

        $db = new Database();
        $db->conectar();
        //$lti_Model = new LTI_Model();
        $usuarios_Model = new Usuarios_Model();


        $email="email@email.com";
        $consumer_key ="consumer_key12345";
        $secret="clavePersonal";
        $linkLTI="localhost/_QuickTest_TFG/index.php";

        //creamos un nuevo usuario
        $idNuevoUsuario = $usuarios_Model->crearUsuarioLTI($email,$consumer_key,$secret,$linkLTI);

        //comprobamos el metodo
        $lti_datos = $usuarios_Model->getUsuarioLTI_Email($email);


        $this->assertEquals($lti_datos['id_lti_keys'],$idNuevoUsuario);
        $this->assertEquals($lti_datos['consumer_key'],$consumer_key);
        $this->assertEquals($lti_datos['resource_link'],$linkLTI);

        //Restablecemos la BD borrando el ejemplo
        $query = $db->consultaPreparada("DELETE FROM lti_keys_users WHERE id_lti_keys = $idNuevoUsuario; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);
    }

    /**
     * Test que comprueba si en la BD, existe un email de usuario.
     */
    public function testCheckEmailExiste()
    {

        $db = new Database();
        $db->conectar();
//        $lti_Model = new LTI_Model();
        $usuarios_Model = new Usuarios_Model();


        $email="email1@email.com";

        $consumer_key ="consumer_key12345";
        $secret="clavePersonal";
        $linkLTI="localhost/_QuickTest_TFG/index.php";

        //creamos un nuevo usuario
        $idNuevoUsuario = $usuarios_Model->crearUsuarioLTI($email,$consumer_key,$secret,$linkLTI);


        //comprobamos el metodo
        $emailExiste = $usuarios_Model->checkEmailExiste($email);
        $emailNOexiste = $usuarios_Model->checkEmailExiste("email_INEXISTENTE");


        $this->assertEquals(1,$emailExiste);
        $this->assertEquals(0,$emailNOexiste);

        //Restablecemos la BD borrando el ejemplo
        $query = $db->consultaPreparada("DELETE FROM lti_keys_users WHERE id_lti_keys = $idNuevoUsuario; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);
    }

}