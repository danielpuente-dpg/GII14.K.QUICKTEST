<?php
require_once $_SERVER["DOCUMENT_ROOT"] . "/app/model/Database.class.php";
require_once $_SERVER["DOCUMENT_ROOT"] . "/app/model/Alumno_has_cuestionario_Model.php";
require_once $_SERVER["DOCUMENT_ROOT"] . "/app/model/Alumno_Model.php";
require_once $_SERVER["DOCUMENT_ROOT"] . "/app/model/Cuestionario_Model.php";
require_once $_SERVER["DOCUMENT_ROOT"] . "/app/model/Respuestas_Model.php";
require_once $_SERVER["DOCUMENT_ROOT"] . "/app/model/Preguntas_Model.php";


/**
 * @author Alejandro Martínez García
 * Class Respuesta_ModelTest
 * Clase que contiene los test que comprueban el modelo Respuesta_Model.
 */
class Respuesta_ModelTest extends PHPUnit_Framework_TestCase
{


    /**
     * Test que comprueba si la BD, devuelve las respuestas de una pregunta
     */
    public function testGetRespuestas_Pregunta()
    {

        $db = new Database();
        $db->conectar();
        $cuestionario_Model = new Cuestionario_Model();
        $preguntas_Model = new Preguntas_Model();
        $respuestas_Model = new Respuestas_Model();

        $query = $db->consultaPreparada("DELETE IGNORE FROM cuestionario WHERE idCuestionario = 123456789; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);

//creamos un cuestionario para relacionarlo con la pregunta
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
        $tituloPreg = "tituloPreg123456789";
        $pmax = 10;


        $idPreguntaNueva = $preguntas_Model->createPregunta($tituloPreg, $idCuestionario, $pmax);


        //creamos 2 respuestas
        $tituloRes1 = "r1";
        $tituloRes2 = "r2";
        $idRespuestaNueva1 = $respuestas_Model->createRespuestas($idPreguntaNueva, $tituloRes1, 0);
        $idRespuestaNueva2 = $respuestas_Model->createRespuestas($idPreguntaNueva, $tituloRes2, 1);
        $preguntas = $preguntas_Model->getIDPreguntaTitulo_Cuestionario($idCuestionario);


        //llamamos al método que queremos comprobar
        $respuestasPregunta = $respuestas_Model->getRespuestas($preguntas[0]);


//Respuesta1
        $this->assertEquals($respuestasPregunta[0]['idRespuesta'], $idRespuestaNueva1);
        $this->assertEquals($respuestasPregunta[0]['tit_Respuesta'], $tituloRes1);
        $this->assertEquals($respuestasPregunta[0]['correcta'], 0);
        $this->assertEquals($respuestasPregunta[0]['pregunta_idPregunta'], $idPreguntaNueva);
        $this->assertEquals($respuestasPregunta[0]['contador'], 0);
        //respuesta 2
        $this->assertEquals($respuestasPregunta[1]['idRespuesta'], $idRespuestaNueva2);
        $this->assertEquals($respuestasPregunta[1]['tit_Respuesta'], $tituloRes2);
        $this->assertEquals($respuestasPregunta[1]['correcta'], 1);
        $this->assertEquals($respuestasPregunta[1]['pregunta_idPregunta'], $idPreguntaNueva);
        $this->assertEquals($respuestasPregunta[1]['contador'], 0);


        //Restablecemos la BD borrando el ejemplo
        $query = $db->consultaPreparada("DELETE FROM cuestionario WHERE idCuestionario = $idCuestionario; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);


    }

    /**
     * Test que comprueba si la BD, incrementa el contador, cuando un alumno resuelve una respuesta.
     */
    public function testIncrementarContadorRespuestas()
    {

        $db = new Database();
        $db->conectar();
        $cuestionario_Model = new Cuestionario_Model();
        $preguntas_Model = new Preguntas_Model();
        $respuestas_Model = new Respuestas_Model();

        $query = $db->consultaPreparada("DELETE IGNORE FROM cuestionario WHERE idCuestionario = 123456789; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);


//creamos un cuestionario para relacionarlo con la pregunta
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
        $tituloPreg = "tituloPreg123456789";
        $pmax = 10;


        $idPreguntaNueva = $preguntas_Model->createPregunta($tituloPreg, $idCuestionario, $pmax);


        //creamos 1 respuesta
        $tituloRes1 = "r1";
        $idRespuestaNueva1 = $respuestas_Model->createRespuestas($idPreguntaNueva, $tituloRes1, 0);



        //llamamos al método que queremos comprobar 3 veces
        $return = $respuestas_Model->incContRespu($idRespuestaNueva1);



 //El contador se debe haber incrmentado 3 veces
        $this->assertTrue($return);

        //Restablecemos la BD borrando el ejemplo
        $query = $db->consultaPreparada("DELETE FROM cuestionario WHERE idCuestionario = $idCuestionario; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);


    }

    /**
     * Test que comprueba si la BD, devuelve cuantos han elegido una respuesta.
     */
    public function testCuantosHanRespondido()
    {

        $db = new Database();
        $db->conectar();
        $cuestionario_Model = new Cuestionario_Model();
        $preguntas_Model = new Preguntas_Model();
        $respuestas_Model = new Respuestas_Model();

        $query = $db->consultaPreparada("DELETE IGNORE FROM cuestionario WHERE idCuestionario = 123456789; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);

//creamos un cuestionario para relacionarlo con la pregunta
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
        $tituloPreg = "tituloPreg123456789";
        $pmax = 10;


        $idPreguntaNueva = $preguntas_Model->createPregunta($tituloPreg, $idCuestionario, $pmax);


        //creamos 1 respuesta
        $tituloRes1 = "r1";
        $idRespuestaNueva1 = $respuestas_Model->createRespuestas($idPreguntaNueva, $tituloRes1, 0);

        $antes = $respuestas_Model->cuantosHanRespondido($idPreguntaNueva);

        //Suponemos que 3 alumnos han contestado
        $respuestas_Model->incContRespu($idRespuestaNueva1);
        $respuestas_Model->incContRespu($idRespuestaNueva1);
        $respuestas_Model->incContRespu($idRespuestaNueva1);

        $despues = $respuestas_Model->cuantosHanRespondido($idPreguntaNueva);

//El contador se debe haber incrmentado 3 veces
        $this->assertEquals($antes + 3, $despues);
        $this->assertEquals($antes, 0);
        $this->assertEquals($despues, 3);

        //Restablecemos la BD borrando el ejemplo
        $query = $db->consultaPreparada("DELETE FROM cuestionario WHERE idCuestionario = $idCuestionario; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);


    }

    /**
     * Test que comprueba si la BD, devuelve si una respuesta es correcta.
     */
    public function testEsCorrecta_Respuesta()
    {

        $db = new Database();
        $db->conectar();
        $cuestionario_Model = new Cuestionario_Model();
        $preguntas_Model = new Preguntas_Model();
        $respuestas_Model = new Respuestas_Model();

        $query = $db->consultaPreparada("DELETE IGNORE FROM cuestionario WHERE idCuestionario = 123456789; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);

//creamos un cuestionario para relacionarlo con la pregunta
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
        $tituloPreg = "tituloPreg123456789";
        $pmax = 10;
        $idPreguntaNueva = $preguntas_Model->createPregunta($tituloPreg, $idCuestionario, $pmax);


        //creamos 2 respuestas
        $tituloRes1 = "r1";
        $idRespuestaNueva1 = $respuestas_Model->createRespuestas($idPreguntaNueva, $tituloRes1, 0);
        $idRespuestaNueva2 = $respuestas_Model->createRespuestas($idPreguntaNueva, $tituloRes1, 1);


        $incorrecta = $respuestas_Model->isCorrect($idRespuestaNueva1);
        $correcta = $respuestas_Model->isCorrect($idRespuestaNueva2);

        //1 es correcta la otra no
        $this->assertEquals($incorrecta, 0);
        $this->assertEquals($correcta, 1);

        //Restablecemos la BD borrando el ejemplo
        $query = $db->consultaPreparada("DELETE FROM cuestionario WHERE idCuestionario = $idCuestionario; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);


    }

    /**
     * Test que comprueba si la BD, crea respuestas nuevas
     */
    public function testCrearRespuestas()
    {
        $db = new Database();
        $db->conectar();
        $cuestionario_Model = new Cuestionario_Model();
        $preguntas_Model = new Preguntas_Model();
        $respuestas_Model = new Respuestas_Model();

        $query = $db->consultaPreparada("DELETE IGNORE FROM cuestionario WHERE idCuestionario = 123456789; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);

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

        // Miramos cuantas respuestas había antes
        $query = $db->consultaPreparada("SELECT COUNT(*) FROM respuesta; ");
        mysqli_stmt_execute($query);
        mysqli_stmt_bind_result($query, $antes);
        mysqli_stmt_fetch($query);
        $db->closeFreeStatement($query);

        $titRespuesta = "respuesta123456789";
        $correcta = 1;

        $respuestas_Model->createRespuestas($idPreguntaNueva, $titRespuesta, $correcta);
        $respuestas_Model->createRespuestas($idPreguntaNueva, $titRespuesta, $correcta);

        // Miramos cuantas respuestas había despues
        $query = $db->consultaPreparada("SELECT COUNT(*) FROM respuesta; ");
        mysqli_stmt_execute($query);
        mysqli_stmt_bind_result($query, $despues);
        mysqli_stmt_fetch($query);
        $db->closeFreeStatement($query);


        $this->assertEquals($despues, ($antes + 2));
        $this->assertTrue($result);


//        //Restablecemos la BD borrando el ejemplo
        $query = $db->consultaPreparada("DELETE FROM cuestionario WHERE idCuestionario = $idCuestionario; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);


    }



}