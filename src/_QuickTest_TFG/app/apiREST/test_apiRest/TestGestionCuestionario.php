<?php

/**
 * Created by PhpStorm.
 * User: Daniel Puente Gabarri
 * Date: 21/03/2017
 * Time: 12:20
 */

require_once ('Defines.php');

require_once($URL_GLOBAL . '/_QuickTest_TFG/app/model/Database.class.php');
require_once($URL_GLOBAL . '/_QuickTest_TFG/app/apiREST/utilidades/TablaCuestionario.php');


class TestGestionCuestionario extends PHPUnit_Framework_TestCase
{
    public function testGestionCuestionario(){

        // Establecemos la conexion con DB
        $db = new Database();
        $db->conectar();

        // Borramos un cuestionario con el mismo identificador que posteriormente añadiremos para evitar
        // problemas de PK.
        $query = $db->consultaPreparada(
            "DELETE IGNORE FROM " . TablaCuestionario::NOMBRE_TABLA .
            " WHERE " . TablaCuestionario::ID_CUESTIONARIO . " = 123456789;");

        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);

        //$cuestionario_Model = new Cuestionario_Model();

        // Llamamos al controlador correspondiente para que realice la insercion de un nuevo cuestionario
        //$cuestionarioModel = new Cuestionario_CrearNuevo_Controller();



        // Insertar un cuestionario

        //$idCuestionario = 123456789;
        //$cuestionario_Model->createCuestionarioEditado($idCuestionario, "cuest123456789", 123456789, "asig123456789", 0.25, 0.25, 0.25, 0.25);

        // Duplicar un cuestionario

        // Obtener los datos

        // Eliminar el cuestionario

    }

}