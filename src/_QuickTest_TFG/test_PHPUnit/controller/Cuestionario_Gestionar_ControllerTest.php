<?php

require_once $_SERVER["DOCUMENT_ROOT"] . "/app/controller/Cuestionario_Gestionar_Controller.php";


/**
 * @author Alejandro Martínez García
 * Class Cuestionario_Gestionar_ControllerTest
 * Clase que contiene los test que comprueban la clase Cuestionario_Gestionar_Controller.
 */
class Cuestionario_Gestionar_ControllerTest extends PHPUnit_Framework_TestCase
{


    /**
     * Test que comprueba si un cuestionario se ha podido borrar
     */
    public function testBorrarCuestionario()
    {
        $db = new Database();
        $db->conectar();
        $cuestionario_Model = new Cuestionario_Model();

        $query = $db->consultaPreparada("DELETE IGNORE FROM cuestionario WHERE idCuestionario = 123456789; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);

        //creamos un cuestionario
        $idCuestionario = 123456789;
        $cuestionario_Model->createCuestionarioEditado($idCuestionario, "cuest123456789", 123456789, "asig123456789", 0.25, 0.25, 0.25, 0.25);

        $cuestionario_Gestionar_Controller = new Cuestionario_Gestionar_Controller();
        $return = $cuestionario_Gestionar_Controller->borrarCuestionario($idCuestionario);

        $this->assertTrue($return);


        //Restablecemos la BD borrando el ejemplo
        $query = $db->consultaPreparada("DELETE FROM cuestionario WHERE idCuestionario = $idCuestionario; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);


    }


}