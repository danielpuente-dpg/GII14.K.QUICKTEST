<?php

/**
 * Created by PhpStorm.
 * User: Daniel Puente Gabarri
 * Date: 22/03/2017
 * Time: 1:00
 */

require_once('../utilidades/Defines.php');

require_once($URL_GLOBAL . '/_QuickTest_TFG/app/controller/Usuarios_Controller.php');
require_once($URL_GLOBAL . '/_QuickTest_TFG/app/apiREST/test_apiRest/TablaLTI_Keys_Users.php');

/**
 * Class TestControlAccesoProfesor, Clase de Test encargada de comprobar el correcto funcionamiento del controlador.
 * Estos test se realizan sobre el controlador, ya que sobre este se basa el APIREST.
 *
 * @autor Daniel Puente Gabarri.
 */
class TestControlAccesoProfesor extends PHPUnit_Framework_TestCase
{
    /**
     * Método encargado de realizar el test del método registrar un nuevo profesor
     */
    public function testRegistrarProfesor()
    {

        // Borramos el usuario que vamos a añadir
        $db = new Database();
        $db->conectar();
        $usuarios_Controller = new Usuarios_Controller();

        $email = "emailTestApiRest";
        $password = 112233;

        $query = $db->consultaPreparada("DELETE FROM " . TablaLTI_Keys_Users::NOMBRE_TABLA .
                    " WHERE " . TablaLTI_Keys_Users::EMAIL . "=?" );

        $ok = mysqli_stmt_bind_param($query, 's', $email);
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);

        $usuarioController = new Usuarios_Controller();
        $retorno = $usuarioController->registrarNuevoUsuario($email, $password);

        $this->assertEquals ($retorno, 1);

    }

    /**
     * Método encargado de realizar el test del método iniciar sesion de un profesor
     */
    public function testLoginProfesor()
    {
        // Obtenemos el email y el consumer_key del usuario que hemos registrado anteriomrmente
        $mysqli = new mysqli("localhost", "root", "", "quicktest_tfg");

        $resultado = $mysqli->query("SELECT " . TablaLTI_Keys_Users::EMAIL . ", " . TablaLTI_Keys_Users::OAUTH_CONSUMER_KEY .
                        " FROM " . TablaLTI_Keys_Users::NOMBRE_TABLA .
                        " ORDER BY " . TablaLTI_Keys_Users::ID_LTI_KEYS_USERS .
                        " DESC LIMIT 1");

        $fila = $resultado->fetch_row();
        $email = $fila[0];
        $password = $fila[1];

        $usuarioController = new Usuarios_Controller();
        $retorno = $usuarioController->login($email, $password);

        $this->assertEquals($retorno);


        // Eliminamos ese user registado en el test
        $db = new Database();
        $db->conectar();

        $query = $db->consultaPreparada("DELETE FROM " . TablaLTI_Keys_Users::NOMBRE_TABLA .
            " WHERE " . TablaLTI_Keys_Users::EMAIL . "=?" );

        $ok = mysqli_stmt_bind_param($query, 's', $email);
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);
    }

}