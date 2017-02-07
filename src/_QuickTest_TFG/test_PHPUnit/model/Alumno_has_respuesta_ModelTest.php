<?php
require_once $_SERVER["DOCUMENT_ROOT"] . "/app/model/Database.class.php";
require_once $_SERVER["DOCUMENT_ROOT"] . "/app/model/Alumno_has_cuestionario_Model.php";
require_once $_SERVER["DOCUMENT_ROOT"] . "/app/model/Alumno_has_respuesta_Model.php";
require_once $_SERVER["DOCUMENT_ROOT"] . "/app/model/Alumno_Model.php";
require_once $_SERVER["DOCUMENT_ROOT"] . "/app/model/Cuestionario_Model.php";
require_once $_SERVER["DOCUMENT_ROOT"] . "/app/model/Respuestas_Model.php";
require_once $_SERVER["DOCUMENT_ROOT"] . "/app/model/Preguntas_Model.php";



/**
 * @author Alejandro Martínez García
 * Class Alumno_has_respuesta_ModelTest
 * Clase que contiene los test que comprueban el modelo Alumno_has_respuesta_Model.
 */
class Alumno_has_respuesta_ModelTest extends PHPUnit_Framework_TestCase
{

    /**
     * Test que comprueba que en la base de datos, se guarda la puntuación del alumno.
     */
    public function testSetPuntuacion()
    {
        $db = new Database();
        $db->conectar();
        $cuestionario_Model = new Cuestionario_Model();
        $preguntas_Model = new Preguntas_Model();
        $respuestas_Model = new Respuestas_Model();
        $alumno_Model = new Alumno_Model();

        $alumno_has_respuesta_Model = new Alumno_has_respuesta_Model();

//        //Restablecemos la BD borrando el ejemplo
        $query3 = $db->consultaPreparada("DELETE IGNORE FROM cuestionario WHERE idCuestionario = 123456789; ");
        mysqli_stmt_execute($query3);
        $db->closeFreeStatement($query3);

//creamos un cuestionario para relacionarlo con la pregunta
        $idCuestionario = 123456789;
        $idAsig = 123456789;
        $cuestTit = "cuest123456789";
        $nombreAsig = "asig123456789";
        $corrVerdT = 0.25;
        $corrVerdF = 0.25;
        $corrAmT = 0.25;
        $corrAmF = 0.25;
        $result = $cuestionario_Model->createCuestionarioEditado($idCuestionario, $cuestTit, $idAsig, $nombreAsig, $corrVerdT, $corrVerdF, $corrAmT, $corrAmF);

//creamos una pregunta para relacionarla con la respuesta
        $tituloPreg = "tituloPreg123456789;";
        $pmax = 10;
        $idPreguntaNueva = $preguntas_Model->createPregunta($tituloPreg, $idCuestionario, $pmax);


        $titRespuesta = "respuesta123456789";
        $correcta = 1;
        $idRespuestaNueva = $respuestas_Model->createRespuestas($idPreguntaNueva, $titRespuesta, $correcta);


        $punt=5;
        $idAlu= 123456789;
        $comUsado ="verde";
        //Creamos un alumno
        $alumno_Model->insertarAlumno($idAlu, "alu1", "ape1");

        //Registra la puntuacion
        $return = $alumno_has_respuesta_Model->setPuntuacion($punt, $idAlu,$idRespuestaNueva,$comUsado ,$idPreguntaNueva, $correcta);
        $puntRegistrada = $alumno_has_respuesta_Model->getSumaPuntComodin_Alumno($idAlu,$idCuestionario,$comUsado);

        $this->assertEquals($punt, $puntRegistrada);
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
     * Test que comprueba que  la base de datos, devuelve la puntuación total del alumno.
     */
    public function testGetSumaPuntTotal_Alumno()
    {
        $db = new Database();
        $db->conectar();
        $cuestionario_Model = new Cuestionario_Model();
        $preguntas_Model = new Preguntas_Model();
        $respuestas_Model = new Respuestas_Model();
        $alumno_Model = new Alumno_Model();

        $alumno_has_respuesta_Model = new Alumno_has_respuesta_Model();

//        //Restablecemos la BD borrando el ejemplo
        $query3 = $db->consultaPreparada("DELETE IGNORE FROM cuestionario WHERE idCuestionario = 123456789; ");
        mysqli_stmt_execute($query3);
        $db->closeFreeStatement($query3);

//creamos un cuestionario para relacionarlo con la pregunta
        $idCuestionario = 123456789;
        $idAsig = 123456789;
        $cuestTit = "cuest123456789";
        $nombreAsig = "asig123456789";
        $corrVerdT = 0.25;
        $corrVerdF = 0.25;
        $corrAmT = 0.25;
        $corrAmF = 0.25;
        $result = $cuestionario_Model->createCuestionarioEditado($idCuestionario, $cuestTit, $idAsig, $nombreAsig, $corrVerdT, $corrVerdF, $corrAmT, $corrAmF);

//creamos 3 pregunta para relacionarla con la respuesta
        $tituloPreg = "tituloPreg123456789;";
        $pmax = 10;
        $idPreguntaNueva1 = $preguntas_Model->createPregunta($tituloPreg, $idCuestionario, $pmax);
        $idPreguntaNueva2 = $preguntas_Model->createPregunta($tituloPreg, $idCuestionario, $pmax);
        $idPreguntaNueva3 = $preguntas_Model->createPregunta($tituloPreg, $idCuestionario, $pmax);


        $titRespuesta = "respuesta123456789";
        $correcta = 1;
        $idRespuestaNueva1 = $respuestas_Model->createRespuestas($idPreguntaNueva1, $titRespuesta, $correcta);
        $idRespuestaNueva2 = $respuestas_Model->createRespuestas($idPreguntaNueva2, $titRespuesta, $correcta);
        $idRespuestaNueva3 = $respuestas_Model->createRespuestas($idPreguntaNueva3, $titRespuesta, $correcta);


        $punt1=5;
        $punt2=10;
        $punt3=15;
        $idAlu= 123456789;
        $comUsado ="verde";
        //Creamos un alumno
        $alumno_Model->insertarAlumno($idAlu, "alu1", "ape1");

        //Registra la puntuacion
        $return1 = $alumno_has_respuesta_Model->setPuntuacion($punt1, $idAlu,$idRespuestaNueva1,$comUsado ,$idPreguntaNueva1, $correcta);
        $return2= $alumno_has_respuesta_Model->setPuntuacion($punt2, $idAlu,$idRespuestaNueva2,$comUsado ,$idPreguntaNueva2, $correcta);
        $return3 = $alumno_has_respuesta_Model->setPuntuacion($punt3, $idAlu,$idRespuestaNueva3,$comUsado ,$idPreguntaNueva3, $correcta);

        $puntRegistrada = $alumno_has_respuesta_Model->getSumaPuntComodin_Alumno($idAlu,$idCuestionario,$comUsado);

        $this->assertEquals($punt1+$punt2+$punt3, $puntRegistrada);
        $this->assertTrue($return1);
        $this->assertTrue($return2);
        $this->assertTrue($return3);



       //Restablecemos la BD borrando el ejemplo
        $query3 = $db->consultaPreparada("DELETE FROM cuestionario WHERE idCuestionario = $idCuestionario; ");
        mysqli_stmt_execute($query3);
        $db->closeFreeStatement($query3);

        $query4 = $db->consultaPreparada("DELETE FROM alumno WHERE idAlumno = $idAlu; ");
        mysqli_stmt_execute($query4);
        $db->closeFreeStatement($query4);



    }

    /**
     * Test que comprueba que la base de datos, devuelve las respuestas que ha contestado un alumno.
     */
    public function testGetRespuestasRespondidas_Cuestionario()
    {
        $db = new Database();
        $db->conectar();
        $cuestionario_Model = new Cuestionario_Model();
        $preguntas_Model = new Preguntas_Model();
        $respuestas_Model = new Respuestas_Model();
        $alumno_Model = new Alumno_Model();

        $alumno_has_respuesta_Model = new Alumno_has_respuesta_Model();

//        //Restablecemos la BD borrando el ejemplo
        $query3 = $db->consultaPreparada("DELETE IGNORE FROM cuestionario WHERE idCuestionario = 123456789; ");
        mysqli_stmt_execute($query3);
        $db->closeFreeStatement($query3);

//creamos un cuestionario para relacionarlo con la pregunta
        $idCuestionario = 123456789;
        $idAsig = 123456789;
        $cuestTit = "cuest123456789";
        $nombreAsig = "asig123456789";
        $corrVerdT = 0.25;
        $corrVerdF = 0.25;
        $corrAmT = 0.25;
        $corrAmF = 0.25;
        $result = $cuestionario_Model->createCuestionarioEditado($idCuestionario, $cuestTit, $idAsig, $nombreAsig, $corrVerdT, $corrVerdF, $corrAmT, $corrAmF);

//creamos 3 pregunta para relacionarla con la respuesta
        $tituloPreg = "tituloPreg123456789;";
        $pmax = 10;
        $idPreguntaNueva1 = $preguntas_Model->createPregunta($tituloPreg, $idCuestionario, $pmax);
        $idPreguntaNueva2 = $preguntas_Model->createPregunta($tituloPreg, $idCuestionario, $pmax);
        $idPreguntaNueva3 = $preguntas_Model->createPregunta($tituloPreg, $idCuestionario, $pmax);


        $titRespuesta = "respuesta123456789";
        $correcta = 1;
        $idRespuestaNueva1 = $respuestas_Model->createRespuestas($idPreguntaNueva1, $titRespuesta, $correcta);
        $idRespuestaNueva2 = $respuestas_Model->createRespuestas($idPreguntaNueva2, $titRespuesta, $correcta);
        $idRespuestaNueva3 = $respuestas_Model->createRespuestas($idPreguntaNueva3, $titRespuesta, $correcta);


        $punt1=5;
        $punt2=10;
        $punt3=15;
        $idAlu= 123456789;
        $comUsado ="verde";
        //Creamos un alumno
        $alumno_Model->insertarAlumno($idAlu, "alu1", "ape1");

        //Registramos las tres respuestas como ya respondidas
        $return1 = $alumno_has_respuesta_Model->setPuntuacion($punt1, $idAlu,$idRespuestaNueva1,$comUsado ,$idPreguntaNueva1, $correcta);
        $return2= $alumno_has_respuesta_Model->setPuntuacion($punt2, $idAlu,$idRespuestaNueva2,$comUsado ,$idPreguntaNueva2, $correcta);
        $return3 = $alumno_has_respuesta_Model->setPuntuacion($punt3, $idAlu,$idRespuestaNueva3,$comUsado ,$idPreguntaNueva3, $correcta);

        $puntRegistrada = $alumno_has_respuesta_Model->getRespuestasRespondidas_Cuestionario($idAlu,$idCuestionario);

         $this->assertEquals($puntRegistrada[0]['idRespuesta'], $idRespuestaNueva1);
        $this->assertEquals($puntRegistrada[1]['idRespuesta'], $idRespuestaNueva2);
        $this->assertEquals($puntRegistrada[2]['idRespuesta'], $idRespuestaNueva3);

        $this->assertTrue($return1);
        $this->assertTrue($return2);
        $this->assertTrue($return3);



               //Restablecemos la BD borrando el ejemplo
        $query3 = $db->consultaPreparada("DELETE FROM cuestionario WHERE idCuestionario = $idCuestionario; ");
        mysqli_stmt_execute($query3);
        $db->closeFreeStatement($query3);

        $query4 = $db->consultaPreparada("DELETE FROM alumno WHERE idAlumno = $idAlu; ");
        mysqli_stmt_execute($query4);
        $db->closeFreeStatement($query4);



    }

    /**
     * Test que comprueba que la base de datos, devuelve el numero de preguntas respondidas con comodin.
     */
    public function testGetCuantos_Comodin()
    {
        $db = new Database();
        $db->conectar();
        $cuestionario_Model = new Cuestionario_Model();
        $preguntas_Model = new Preguntas_Model();
        $respuestas_Model = new Respuestas_Model();
        $alumno_Model = new Alumno_Model();

        $alumno_has_respuesta_Model = new Alumno_has_respuesta_Model();

//        //Restablecemos la BD borrando el ejemplo
        $query3 = $db->consultaPreparada("DELETE IGNORE FROM cuestionario WHERE idCuestionario = 123456789; ");
        mysqli_stmt_execute($query3);
        $db->closeFreeStatement($query3);

//creamos un cuestionario para relacionarlo con la pregunta
        $idCuestionario = 123456789;
        $idAsig = 123456789;
        $cuestTit = "cuest123456789";
        $nombreAsig = "asig123456789";
        $corrVerdT = 0.25;
        $corrVerdF = 0.25;
        $corrAmT = 0.25;
        $corrAmF = 0.25;
        $result = $cuestionario_Model->createCuestionarioEditado($idCuestionario, $cuestTit, $idAsig, $nombreAsig, $corrVerdT, $corrVerdF, $corrAmT, $corrAmF);

//creamos 3 pregunta para relacionarla con la respuesta
        $tituloPreg = "tituloPreg123456789;";
        $pmax = 10;
        $idPreguntaNueva1 = $preguntas_Model->createPregunta($tituloPreg, $idCuestionario, $pmax);
        $idPreguntaNueva2 = $preguntas_Model->createPregunta($tituloPreg, $idCuestionario, $pmax);
        $idPreguntaNueva3 = $preguntas_Model->createPregunta($tituloPreg, $idCuestionario, $pmax);


        $titRespuesta = "respuesta123456789";
        $correcta = 1;
        $idRespuestaNueva1 = $respuestas_Model->createRespuestas($idPreguntaNueva1, $titRespuesta, $correcta);
        $idRespuestaNueva2 = $respuestas_Model->createRespuestas($idPreguntaNueva2, $titRespuesta, $correcta);
        $idRespuestaNueva3 = $respuestas_Model->createRespuestas($idPreguntaNueva3, $titRespuesta, $correcta);


        $punt1=5;
        $punt2=10;
        $punt3=15;
        $idAlu= 123456789;
        $comUsado1 ="verde";
        $comUsado2 ="ambar";

        //Creamos un alumno
        $alumno_Model->insertarAlumno($idAlu, "alu1", "ape1");

        //Registramos las tres respuestas como ya respondidas
        $return1 = $alumno_has_respuesta_Model->setPuntuacion($punt1, $idAlu,$idRespuestaNueva1,$comUsado1 ,$idPreguntaNueva1, $correcta);
        $return2= $alumno_has_respuesta_Model->setPuntuacion($punt2, $idAlu,$idRespuestaNueva2,$comUsado1 ,$idPreguntaNueva2, $correcta);
        $return3 = $alumno_has_respuesta_Model->setPuntuacion($punt3, $idAlu,$idRespuestaNueva3,$comUsado2 ,$idPreguntaNueva3, $correcta);

        $cuantosVerde = $alumno_has_respuesta_Model->getCuantos_Comodin($idAlu,$idCuestionario, "verde");
        $cuantosAmbar = $alumno_has_respuesta_Model->getCuantos_Comodin($idAlu,$idCuestionario, "ambar");

        $this->assertEquals($cuantosVerde, 2);
        $this->assertEquals($cuantosAmbar, 1);

        //Restablecemos la BD borrando el ejemplo
        $query3 = $db->consultaPreparada("DELETE FROM cuestionario WHERE idCuestionario = $idCuestionario; ");
        mysqli_stmt_execute($query3);
        $db->closeFreeStatement($query3);

        $query4 = $db->consultaPreparada("DELETE FROM alumno WHERE idAlumno = $idAlu; ");
        mysqli_stmt_execute($query4);
        $db->closeFreeStatement($query4);



    }

    /**
     * Test que comprueba que la base de datos, devuelve la puntuacion de las preguntas respondidas con comodin.
     */
    public function testGetSumaPuntComodin_Alumno()
    {
        $db = new Database();
        $db->conectar();
        $cuestionario_Model = new Cuestionario_Model();
        $preguntas_Model = new Preguntas_Model();
        $respuestas_Model = new Respuestas_Model();
        $alumno_Model = new Alumno_Model();

        $alumno_has_respuesta_Model = new Alumno_has_respuesta_Model();

//        //Restablecemos la BD borrando el ejemplo
        $query3 = $db->consultaPreparada("DELETE IGNORE FROM cuestionario WHERE idCuestionario = 123456789; ");
        mysqli_stmt_execute($query3);
        $db->closeFreeStatement($query3);

//creamos un cuestionario para relacionarlo con la pregunta
        $idCuestionario = 123456789;
        $idAsig = 123456789;
        $cuestTit = "cuest123456789";
        $nombreAsig = "asig123456789";
        $corrVerdT = 0.25;
        $corrVerdF = 0.25;
        $corrAmT = 0.25;
        $corrAmF = 0.25;
        $result = $cuestionario_Model->createCuestionarioEditado($idCuestionario, $cuestTit, $idAsig, $nombreAsig, $corrVerdT, $corrVerdF, $corrAmT, $corrAmF);

//creamos 3 pregunta para relacionarla con la respuesta
        $tituloPreg = "tituloPreg123456789;";
        $pmax = 10;
        $idPreguntaNueva1 = $preguntas_Model->createPregunta($tituloPreg, $idCuestionario, $pmax);
        $idPreguntaNueva2 = $preguntas_Model->createPregunta($tituloPreg, $idCuestionario, $pmax);
        $idPreguntaNueva3 = $preguntas_Model->createPregunta($tituloPreg, $idCuestionario, $pmax);


        $titRespuesta = "respuesta123456789";
        $correcta = 1;
        $idRespuestaNueva1 = $respuestas_Model->createRespuestas($idPreguntaNueva1, $titRespuesta, $correcta);
        $idRespuestaNueva2 = $respuestas_Model->createRespuestas($idPreguntaNueva2, $titRespuesta, $correcta);
        $idRespuestaNueva3 = $respuestas_Model->createRespuestas($idPreguntaNueva3, $titRespuesta, $correcta);


        $punt1=5;
        $punt2=10;
        $punt3=20;
        $idAlu= 123456789;
        $comUsado1 ="verde";
        $comUsado2 ="ambar";

        //Creamos un alumno
        $alumno_Model->insertarAlumno($idAlu, "alu1", "ape1");

        //Registramos las tres respuestas como ya respondidas
        $return1 = $alumno_has_respuesta_Model->setPuntuacion($punt1, $idAlu,$idRespuestaNueva1,$comUsado1 ,$idPreguntaNueva1, $correcta);
        $return2= $alumno_has_respuesta_Model->setPuntuacion($punt2, $idAlu,$idRespuestaNueva2,$comUsado1 ,$idPreguntaNueva2, $correcta);
        $return3 = $alumno_has_respuesta_Model->setPuntuacion($punt3, $idAlu,$idRespuestaNueva3,$comUsado2 ,$idPreguntaNueva3, $correcta);

        $sumaPuntVerde = $alumno_has_respuesta_Model->getSumaPuntComodin_Alumno($idAlu,$idCuestionario, "verde");
        $sumaPuntVerdeAmbar = $alumno_has_respuesta_Model->getSumaPuntComodin_Alumno($idAlu,$idCuestionario, "ambar");

        $this->assertEquals($sumaPuntVerde, 15);
        $this->assertEquals($sumaPuntVerdeAmbar, 20);

        //Restablecemos la BD borrando el ejemplo
        $query3 = $db->consultaPreparada("DELETE FROM cuestionario WHERE idCuestionario = $idCuestionario; ");
        mysqli_stmt_execute($query3);
        $db->closeFreeStatement($query3);

        $query4 = $db->consultaPreparada("DELETE FROM alumno WHERE idAlumno = $idAlu; ");
        mysqli_stmt_execute($query4);
        $db->closeFreeStatement($query4);



    }


    public function testGetCuantasPreguntasRespondidas_Alumno()
    {
        $db = new Database();
        $db->conectar();
        $cuestionario_Model = new Cuestionario_Model();
        $preguntas_Model = new Preguntas_Model();
        $respuestas_Model = new Respuestas_Model();
        $alumno_Model = new Alumno_Model();

        $alumno_has_respuesta_Model = new Alumno_has_respuesta_Model();

//        //Restablecemos la BD borrando el ejemplo
        $query3 = $db->consultaPreparada("DELETE IGNORE FROM cuestionario WHERE idCuestionario = 123456789; ");
        mysqli_stmt_execute($query3);
        $db->closeFreeStatement($query3);

//creamos un cuestionario para relacionarlo con la pregunta
        $idCuestionario = 123456789;
        $idAsig = 123456789;
        $cuestTit = "cuest123456789";
        $nombreAsig = "asig123456789";
        $corrVerdT = 0.25;
        $corrVerdF = 0.25;
        $corrAmT = 0.25;
        $corrAmF = 0.25;
        $result = $cuestionario_Model->createCuestionarioEditado($idCuestionario, $cuestTit, $idAsig, $nombreAsig, $corrVerdT, $corrVerdF, $corrAmT, $corrAmF);

//creamos 3 pregunta para relacionarla con la respuesta
        $tituloPreg = "tituloPreg123456789;";
        $pmax = 10;
        $idPreguntaNueva1 = $preguntas_Model->createPregunta($tituloPreg, $idCuestionario, $pmax);
        $idPreguntaNueva2 = $preguntas_Model->createPregunta($tituloPreg, $idCuestionario, $pmax);
        $idPreguntaNueva3 = $preguntas_Model->createPregunta($tituloPreg, $idCuestionario, $pmax);


        $titRespuesta = "respuesta123456789";
        $correcta = 1;
        $idRespuestaNueva1 = $respuestas_Model->createRespuestas($idPreguntaNueva1, $titRespuesta, $correcta);
        $idRespuestaNueva2 = $respuestas_Model->createRespuestas($idPreguntaNueva2, $titRespuesta, $correcta);
        $idRespuestaNueva3 = $respuestas_Model->createRespuestas($idPreguntaNueva3, $titRespuesta, $correcta);


        $punt1=5;
        $punt2=10;
        $punt3=20;
        $idAlu1= 123456789;
        $idAlu2= 987654321;
        $comUsado1 ="verde";
        $comUsado2 ="ambar";

        //Creamos 2 alumnos
        $alumno_Model->insertarAlumno($idAlu1, "alu1", "ape1");
        $alumno_Model->insertarAlumno($idAlu2, "alu2", "ape2");

        //Registramos las tres respuestas como ya respondidas
        $return1 = $alumno_has_respuesta_Model->setPuntuacion($punt1, $idAlu1,$idRespuestaNueva1,$comUsado1 ,$idPreguntaNueva1, $correcta);
        $return2= $alumno_has_respuesta_Model->setPuntuacion($punt2, $idAlu2,$idRespuestaNueva2,$comUsado1 ,$idPreguntaNueva2, $correcta);
        $return3 = $alumno_has_respuesta_Model->setPuntuacion($punt3, $idAlu2,$idRespuestaNueva3,$comUsado2 ,$idPreguntaNueva3, $correcta);

        $pregAlu1 = $alumno_has_respuesta_Model->getCuantasPreguntasRespondidas_Alumno($idAlu1,$idCuestionario );
        $pregAlu2 = $alumno_has_respuesta_Model->getCuantasPreguntasRespondidas_Alumno($idAlu2,$idCuestionario);

        $this->assertEquals($pregAlu1, 1);
        $this->assertEquals($pregAlu2, 2);

        //Restablecemos la BD borrando el ejemplo
        $query3 = $db->consultaPreparada("DELETE FROM cuestionario WHERE idCuestionario = $idCuestionario; ");
        mysqli_stmt_execute($query3);
        $db->closeFreeStatement($query3);

        $query4 = $db->consultaPreparada("DELETE FROM alumno WHERE idAlumno = $idAlu1 OR idAlumno = $idAlu2 ");
        mysqli_stmt_execute($query4);
        $db->closeFreeStatement($query4);



    }

    public function testGetCuantosAciertos_Comodin()
    {
        $db = new Database();
        $db->conectar();
        $cuestionario_Model = new Cuestionario_Model();
        $preguntas_Model = new Preguntas_Model();
        $respuestas_Model = new Respuestas_Model();
        $alumno_Model = new Alumno_Model();

        $alumno_has_respuesta_Model = new Alumno_has_respuesta_Model();

//        //Restablecemos la BD borrando el ejemplo
        $query3 = $db->consultaPreparada("DELETE IGNORE FROM cuestionario WHERE idCuestionario = 123456789; ");
        mysqli_stmt_execute($query3);
        $db->closeFreeStatement($query3);

//creamos un cuestionario para relacionarlo con la pregunta
        $idCuestionario = 123456789;
        $idAsig = 123456789;
        $cuestTit = "cuest123456789";
        $nombreAsig = "asig123456789";
        $corrVerdT = 0.25;
        $corrVerdF = 0.25;
        $corrAmT = 0.25;
        $corrAmF = 0.25;
        $result = $cuestionario_Model->createCuestionarioEditado($idCuestionario, $cuestTit, $idAsig, $nombreAsig, $corrVerdT, $corrVerdF, $corrAmT, $corrAmF);

//creamos 3 pregunta para relacionarla con la respuesta
        $tituloPreg = "tituloPreg123456789;";
        $pmax = 10;
        $idPreguntaNueva1 = $preguntas_Model->createPregunta($tituloPreg, $idCuestionario, $pmax);
        $idPreguntaNueva2 = $preguntas_Model->createPregunta($tituloPreg, $idCuestionario, $pmax);
        $idPreguntaNueva3 = $preguntas_Model->createPregunta($tituloPreg, $idCuestionario, $pmax);


        $titRespuesta = "respuesta123456789";
        $correcta = 1;
        $idRespuestaNueva1 = $respuestas_Model->createRespuestas($idPreguntaNueva1, $titRespuesta, $correcta);
        $idRespuestaNueva2 = $respuestas_Model->createRespuestas($idPreguntaNueva2, $titRespuesta, $correcta);
        $idRespuestaNueva3 = $respuestas_Model->createRespuestas($idPreguntaNueva3, $titRespuesta, $correcta);


        $punt1=5;
        $punt2=10;
        $punt3=20;
        $idAlu= 123456789;
        $comUsado1 ="verde";
        $comUsado2 ="ambar";

        //Creamos un alumno
        $alumno_Model->insertarAlumno($idAlu, "alu1", "ape1");

        //Registramos las tres respuestas como ya respondidas
        $return1 = $alumno_has_respuesta_Model->setPuntuacion($punt1, $idAlu,$idRespuestaNueva1,$comUsado1 ,$idPreguntaNueva1, 1);
        $return2= $alumno_has_respuesta_Model->setPuntuacion($punt2, $idAlu,$idRespuestaNueva2,$comUsado1 ,$idPreguntaNueva2, 1);
        $return3 = $alumno_has_respuesta_Model->setPuntuacion($punt3, $idAlu,$idRespuestaNueva3,$comUsado2 ,$idPreguntaNueva3, 0);

        $aciertosVerde = $alumno_has_respuesta_Model->getCuantosAciertos_Comodin($idAlu,$idCuestionario, "verde", 1);
        $aciertosAmbar = $alumno_has_respuesta_Model->getCuantosAciertos_Comodin($idAlu,$idCuestionario, "ambar", 1);
        $fallosVerde = $alumno_has_respuesta_Model->getCuantosAciertos_Comodin($idAlu,$idCuestionario, "verde", 0);
        $fallosAmbar = $alumno_has_respuesta_Model->getCuantosAciertos_Comodin($idAlu,$idCuestionario, "ambar", 0);

        $this->assertEquals($aciertosVerde, 2);
        $this->assertEquals($aciertosAmbar, 0);
        $this->assertEquals($fallosVerde, 0);
        $this->assertEquals($fallosAmbar, 1);

        //Restablecemos la BD borrando el ejemplo
        $query3 = $db->consultaPreparada("DELETE FROM cuestionario WHERE idCuestionario = $idCuestionario; ");
        mysqli_stmt_execute($query3);
        $db->closeFreeStatement($query3);

        $query4 = $db->consultaPreparada("DELETE FROM alumno WHERE idAlumno = $idAlu; ");
        mysqli_stmt_execute($query4);
        $db->closeFreeStatement($query4);



    }

}