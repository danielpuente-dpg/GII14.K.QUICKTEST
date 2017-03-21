<?php

//require_once $_SERVER["DOCUMENT_ROOT"] . "/_QuickTest_TFG/app/controller/Usuarios_Controller.php";

require_once "D:/xampp/htdocs" . "/_QuickTest_TFG/app/controller/Usuarios_Controller.php";

/**
 * @author Alejandro Martínez García
 * Class Usuario_ControllerTest
 * Clase que contiene los test que comprueban la clase Usuario_Controller.
 */
class Usuario_ControllerTest extends PHPUnit_Framework_TestCase
{

    /**
     * Test que registra a un nuevo alumno.
     */
    public function testRegistrarNuevoUsuario()
    {
        $db = new Database();
        $db->conectar();
        $usuarios_Controller = new Usuarios_Controller();

        //Restablecemos la BD borrando el ejemplo
        $query = $db->consultaPreparada("DELETE IGNORE FROM lti_keys_users WHERE emailUsuario= 'emailTEST'; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);

        $query = $db->consultaPreparada("SELECT COUNT(*) FROM lti_keys_users ; ");
        mysqli_stmt_execute($query);
        mysqli_stmt_bind_result($query, $antes);
        mysqli_stmt_fetch($query);
        $db->closeFreeStatement($query);

        //creamos un usuario y testeamos que se añade
        $emailUsuario = "emailTEST";
        $password = 12345;
        $return = $usuarios_Controller->registrarNuevoUsuario($emailUsuario, $password);

      $query = $db->consultaPreparada("SELECT COUNT(*) FROM lti_keys_users ; ");
        mysqli_stmt_execute($query);
        mysqli_stmt_bind_result($query, $despues);
        mysqli_stmt_fetch($query);
        $db->closeFreeStatement($query);


        $this->assertTrue($return);
        $this->assertEquals($antes + 1, $despues);

        //Restablecemos la BD borrando el ejemplo
        $query = $db->consultaPreparada("DELETE IGNORE FROM lti_keys_users WHERE emailUsuario= 'emailTEST'; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);

    }

    /**
     * Test que comprueba que un alumno no se registra más de una vez.
     */
    public function testRegistrarNuevoUsuario_YaExistente()
    {
        $db = new Database();
        $db->conectar();
        $usuarios_Controller = new Usuarios_Controller();

        //Restablecemos la BD borrando el ejemplo
        $query = $db->consultaPreparada("DELETE FROM lti_keys_users WHERE emailUsuario= 'emailTEST'; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);



        //creamos un cuestionario
        $emailUsuario = "emailTEST";
        $password = 12345;
        $insert1 = $usuarios_Controller->registrarNuevoUsuario($emailUsuario, $password);
        $insert2 = $usuarios_Controller->registrarNuevoUsuario($emailUsuario, $password);

        $this->assertTrue($insert1);
        $this->assertEquals($insert2, 0);

        //Restablecemos la BD borrando el ejemplo
        $query = $db->consultaPreparada("DELETE FROM lti_keys_users WHERE emailUsuario= 'emailTEST'; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);


    }

    /**
     * Test que simula una operación de login.
     */
    public function testLogin()
    {
        $db = new Database();
        $db->conectar();
        $usuarios_Controller = new Usuarios_Controller();

        //Restablecemos la BD borrando el ejemplo
        $query = $db->consultaPreparada("DELETE IGNORE FROM lti_keys_users WHERE emailUsuario= 'emailTEST'; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);



        //creamos un usuario para hacer login con el

        $emailUsuario = "emailTEST";
        $password = 12345;
        $usuarios_Controller->registrarNuevoUsuario($emailUsuario, $password);

        $return = $usuarios_Controller->login($emailUsuario, $password);

        $this->assertEquals($return ,0);

        //Restablecemos la BD borrando el ejemplo
        $query = $db->consultaPreparada("DELETE IGNORE FROM lti_keys_users WHERE emailUsuario= 'emailTEST'; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);


    }

    /**
     * Test que simula que el consumer key se genera correctamente.
     */
    public function testCrearConsumerKey()
    {

       $usuarios_Controller = new Usuarios_Controller();
        $consumer=$usuarios_Controller->crearConsumerKey();
        $this->assertEquals(strlen ($consumer) , 7);




    }

    /**
     * Test que simula que el consumer key se genera correctamente.
     */
    public function testEncriptarPassword()
    {

        $usuarios_Controller = new Usuarios_Controller();
        $consumer=$usuarios_Controller->encriptarPassword(12345);
        $this->assertEquals(strlen ($consumer) , 44);




    }

    /**
     * Test que simula que las funciones de desencriptado funcionan.
     */
    public function testDesencriptarParametros()
    {

        $usuarios_Controller = new Usuarios_Controller();
        $return= $consumer=$usuarios_Controller->desencriptarParametros();
        $this->assertEquals(sizeof ($return) , 2);
        $this->assertEquals(gettype ($return) , "array");




    }

    /**
     * Test que simula que un password se desencripta.
     */
    public function testDesencriptarPassword()
    {

        $usuarios_Controller = new Usuarios_Controller();
        $passwordEncriptado=$usuarios_Controller->encriptarPassword(12345);
        $passwordDesencriptado= $usuarios_Controller->desencriptarPassword($passwordEncriptado);
        $this->assertEquals($passwordDesencriptado , 12345);




    }

}