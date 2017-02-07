<?php
require_once $_SERVER["DOCUMENT_ROOT"] . "/app/model/Alumno_Model.php";

 require_once $_SERVER["DOCUMENT_ROOT"] . "/app/model/Database.class.php";
require_once $_SERVER["DOCUMENT_ROOT"] . "/app/model/Alumno_has_cuestionario_Model.php";
require_once $_SERVER["DOCUMENT_ROOT"] . "/app/model/Alumno_has_respuesta_Model.php";
require_once $_SERVER["DOCUMENT_ROOT"] . "/app/model/Alumno_Model.php";
require_once $_SERVER["DOCUMENT_ROOT"] . "/app/model/Cuestionario_Model.php";
require_once $_SERVER["DOCUMENT_ROOT"] . "/app/model/Respuestas_Model.php";
require_once $_SERVER["DOCUMENT_ROOT"] . "/app/model/Preguntas_Model.php";

/**
 * @author Alejandro Martínez García
 * Class Alumno_has_cuestionario_ModelTest
 * Clase que contiene los test que comprueban el modelo Alumno_has_cuestionario_Model.
 */
class Alumno_has_cuestionario_ModelTest extends PHPUnit_Framework_TestCase
{

    /**
     * Test que comprueba que en la base de datos, se inserta un alumno
     */
    public function testInsertaAlumno_CuestionarioOK()
    {


        $db = new Database();
        $db->conectar();
        $cuestionario_Model = new Cuestionario_Model();
        $alumno_Model = new Alumno_Model();
        $alumno_has_cuestionario_Model= new Alumno_has_cuestionario_Model();

         //Restablecemos la BD borrando el ejemplo
        $query3 = $db->consultaPreparada("DELETE IGNORE FROM cuestionario WHERE idCuestionario = 123456789; ");
        mysqli_stmt_execute($query3);
        $db->closeFreeStatement($query3);



//creamos un cuestionario para relacionarlo con el alumno
        $idCuestionario = 123456789;
        $idAsig = 123456789;
        $cuestTit = "cuest123456789";
        $nombreAsig = "asig123456789";
        $corrVerdT = 0.25;
        $corrVerdF = 0.25;
        $corrAmT = 0.25;
        $corrAmF = 0.25;
        $cuestionario_Model->createCuestionarioEditado($idCuestionario, $cuestTit, $idAsig, $nombreAsig, $corrVerdT, $corrVerdF, $corrAmT, $corrAmF);

//creamos una pregunta para relacionarla con la respuesta


        $idAlu = 123456789;
        $comUsado = "verde";
        //Creamos un alumno
        $alumno_Model->insertarAlumno($idAlu, "alu1", "ape1");

        $query = $db->consultaPreparada("SELECT COUNT(*) FROM alumno_has_cuestionario ; ");
        mysqli_stmt_execute($query);
        mysqli_stmt_bind_result($query, $antes);
        mysqli_stmt_fetch($query);
        $db->closeFreeStatement($query);

        //insertamos alumno
        $return = $alumno_has_cuestionario_Model->insertarAlumno_Cuestionario($idAlu, $idCuestionario);
        echo $antes;


        $query = $db->consultaPreparada("SELECT COUNT(*) FROM alumno_has_cuestionario ; ");
        mysqli_stmt_execute($query);
        mysqli_stmt_bind_result($query, $despues);
        mysqli_stmt_fetch($query);
        $db->closeFreeStatement($query);



        $this->assertEquals($antes+1, $despues);
        $this->assertTrue($return);


//        //Restablecemos la BD borrando el ejemplo
        $query3 = $db->consultaPreparada("DELETE FROM cuestionario WHERE idCuestionario = $idCuestionario; ");
        mysqli_stmt_execute($query3);
        $db->closeFreeStatement($query3);

        $query4 = $db->consultaPreparada("DELETE FROM alumno WHERE idAlumno = $idAlu; ");
        mysqli_stmt_execute($query4);
        $db->closeFreeStatement($query4);
    }

    /**
     * Test que comprueba que  la base de datos, devuelve si un cuestionario está resuelto o no.
     */
    public function testEstaResuelto()
    {


        $db = new Database();
        $db->conectar();
        $cuestionario_Model = new Cuestionario_Model();
        $alumno_Model = new Alumno_Model();
        $alumno_has_cuestionario_Model= new Alumno_has_cuestionario_Model();

        //Restablecemos la BD borrando el ejemplo
        $query3 = $db->consultaPreparada("DELETE IGNORE FROM cuestionario WHERE idCuestionario = 123456789; ");
        mysqli_stmt_execute($query3);
        $db->closeFreeStatement($query3);



//creamos un cuestionario para relacionarlo con el alumno
        $idCuestionario = 123456789;
        $idAsig = 123456789;
        $cuestTit = "cuest123456789";
        $nombreAsig = "asig123456789";
        $corrVerdT = 0.25;
        $corrVerdF = 0.25;
        $corrAmT = 0.25;
        $corrAmF = 0.25;
        $cuestionario_Model->createCuestionarioEditado($idCuestionario, $cuestTit, $idAsig, $nombreAsig, $corrVerdT, $corrVerdF, $corrAmT, $corrAmF);

//creamos una pregunta para relacionarla con la respuesta

        //Creamos un alumno
        $idAlu = 123456789;
       $alumno_Model->insertarAlumno($idAlu, "alu1", "ape1");



        //insertamos alumno
        $return = $alumno_has_cuestionario_Model->insertarAlumno_Cuestionario($idAlu, $idCuestionario);

        $resuelto = $alumno_has_cuestionario_Model->estaResuelto($idAlu, $idCuestionario);




        $this->assertEquals($resuelto, 1);
        $this->assertTrue($return);


//        //Restablecemos la BD borrando el ejemplo
        $query3 = $db->consultaPreparada("DELETE FROM cuestionario WHERE idCuestionario = $idCuestionario; ");
        mysqli_stmt_execute($query3);
        $db->closeFreeStatement($query3);

        $query4 = $db->consultaPreparada("DELETE FROM alumno WHERE idAlumno = $idAlu; ");
        mysqli_stmt_execute($query4);
        $db->closeFreeStatement($query4);
    }

    /**
     * Test que comprueba que en la base de datos, devuelve cuantos alumnos han acabado el cuestionario.
     */
    public function test_ContarAlumnosFinalizado_Cuestionario()
    {


        $db = new Database();
        $db->conectar();
        $cuestionario_Model = new Cuestionario_Model();
        $alumno_Model = new Alumno_Model();
        $alumno_has_cuestionario_Model= new Alumno_has_cuestionario_Model();

        //Restablecemos la BD borrando el ejemplo
        $query3 = $db->consultaPreparada("DELETE IGNORE FROM cuestionario WHERE idCuestionario = 123456789; ");
        mysqli_stmt_execute($query3);
        $db->closeFreeStatement($query3);



//creamos un cuestionario para relacionarlo con el alumno
        $idCuestionario = 123456789;
        $idAsig = 123456789;
        $cuestTit = "cuest123456789";
        $nombreAsig = "asig123456789";
        $corrVerdT = 0.25;
        $corrVerdF = 0.25;
        $corrAmT = 0.25;
        $corrAmF = 0.25;
        $cuestionario_Model->createCuestionarioEditado($idCuestionario, $cuestTit, $idAsig, $nombreAsig, $corrVerdT, $corrVerdF, $corrAmT, $corrAmF);

//creamos una pregunta para relacionarla con la respuesta

        //Creamos un alumno
        $idAlu1 = 123456789;
        $idAlu2 = 987654321;
        $alumno_Model->insertarAlumno($idAlu1, "alu1", "ape1");
        $alumno_Model->insertarAlumno($idAlu2, "alu2", "ape1");




        //insertamos los alumnos
        $return = $alumno_has_cuestionario_Model->insertarAlumno_Cuestionario($idAlu1, $idCuestionario);
        $return = $alumno_has_cuestionario_Model->insertarAlumno_Cuestionario($idAlu2, $idCuestionario);

        $cuantos = $alumno_has_cuestionario_Model->contarAlumnosFinalizado_Cuestionario($idCuestionario);




        $this->assertEquals($cuantos, 2);


//        //Restablecemos la BD borrando el ejemplo
        $query3 = $db->consultaPreparada("DELETE FROM cuestionario WHERE idCuestionario = $idCuestionario; ");
        mysqli_stmt_execute($query3);
        $db->closeFreeStatement($query3);

        $query4 = $db->consultaPreparada("DELETE FROM alumno WHERE idAlumno = $idAlu1 AND idAlumno = $idAlu2; ");
        mysqli_stmt_execute($query4);
        $db->closeFreeStatement($query4);
    }



}
 