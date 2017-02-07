<?php
require_once $_SERVER["DOCUMENT_ROOT"] . "/app/model/Database.class.php";
require_once $_SERVER["DOCUMENT_ROOT"] . "/app/model/Alumno_has_cuestionario_Model.php";
require_once $_SERVER["DOCUMENT_ROOT"] . "/app/model/Alumno_Model.php";
require_once $_SERVER["DOCUMENT_ROOT"] . "/app/model/Cuestionario_Model.php";
require_once $_SERVER["DOCUMENT_ROOT"] . "/app/model/Respuestas_Model.php";
require_once $_SERVER["DOCUMENT_ROOT"] . "/app/model/Preguntas_Model.php";


/**
 * @author Alejandro Martínez García
 * Class Pregunta_ModelTest
 * Clase que contiene los test que comprueban el modelo Pregunta_Model.
 */
class Pregunta_ModelTest extends PHPUnit_Framework_TestCase
{

    /**
     * Test que comprueba si la BD, devuelve las preguntas de un cuestionario, por id.
     */
    public function testGetIDPreguntaTitulo_Cuestionario()
    {
        $db = new Database();
        $db->conectar();
        $cuestionario_Model = new Cuestionario_Model();
        $preguntas_Model = new Preguntas_Model();

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


        //Creamos una pregunta de prueba
        $tituloPreg = "tituloPreg123456789;";
        $pmax = 10;
        $idPreguntaNueva = $preguntas_Model->createPregunta($tituloPreg, $idCuestionario, $pmax);


        $datos = $preguntas_Model->getIDPreguntaTitulo_Cuestionario($idCuestionario);

        $this->assertEquals($datos[0]['idPregunta'], $idPreguntaNueva);
        $this->assertEquals($datos[0]['tituloPreg'], $tituloPreg);
        $this->assertEquals($datos[0]['max_puntuacion'], $pmax);


//        //Restablecemos la BD borrando el ejemplo
        $query = $db->consultaPreparada("DELETE FROM cuestionario WHERE idCuestionario = $idCuestionario; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);




    }

    /**
     * Test que comprueba si la BD, devuelve la información de una pregunta.
     */
    public function testGet_infoPreguntas()
    {
        $db = new Database();
        $db->conectar();
        $cuestionario_Model = new Cuestionario_Model();
        $preguntas_Model = new Preguntas_Model();

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


        //Creamos una pregunta de prueba
        $tituloPreg = "tituloPreg123456789;";
        $pmax = 10;
        $idPreguntaNueva = $preguntas_Model->createPregunta($tituloPreg, $idCuestionario, $pmax);


        $datos = $preguntas_Model->get_infoPreguntas($idCuestionario);

        $this->assertEquals($datos[0]['idPregunta'], $idPreguntaNueva);
        $this->assertEquals($datos[0]['titulo'], $tituloPreg);
        $this->assertEquals($datos[0]['max_puntuacion'], $pmax);
        $this->assertEquals($datos[0]['cuestionario_idCuestionario'], $idCuestionario);


//        //Restablecemos la BD borrando el ejemplo
        $query = $db->consultaPreparada("DELETE FROM cuestionario WHERE idCuestionario = $idCuestionario; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);




    }

    /**
     * Test que comprueba si la BD, devuelve cuantas preguntas tiene un cuestionario.
     */
    public function testContar_Preguntas_Cuestionario()
    {
        $db = new Database();
        $db->conectar();
        $cuestionario_Model = new Cuestionario_Model();
        $preguntas_Model = new Preguntas_Model();

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


        //Creamos varias pregunta de prueba
        $tituloPreg = "tituloPreg123456789;";
        $pmax = 10;
        $idPreguntaNueva1 = $preguntas_Model->createPregunta($tituloPreg, $idCuestionario, $pmax);
        $idPreguntaNueva2 = $preguntas_Model->createPregunta($tituloPreg, $idCuestionario, $pmax);
        $idPreguntaNueva3 = $preguntas_Model->createPregunta($tituloPreg, $idCuestionario, $pmax);


        $cuantas = $preguntas_Model->countPreguntasCuestionario($idCuestionario);

        $this->assertEquals(3, $cuantas);


//        //Restablecemos la BD borrando el ejemplo
        $query = $db->consultaPreparada("DELETE FROM cuestionario WHERE idCuestionario = $idCuestionario; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);




    }

    /**
     * Test que comprueba si la BD, inserta una nueva pregunta.
     */
    public function testCrearPregunta()
    {
        $db = new Database();
        $db->conectar();
        $cuestionario_Model = new Cuestionario_Model();
        $preguntas_Model = new Preguntas_Model();

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


        //Miramos cuantas  preguntas había antes
        $query = $db->consultaPreparada("SELECT COUNT(*) FROM pregunta; ");
        mysqli_stmt_execute($query);
        mysqli_stmt_bind_result($query, $antes);
        mysqli_stmt_fetch($query);
        $db->closeFreeStatement($query);


        $tituloPreg = "tituloPreg123456789;";
        $pmax = 10;

        $idPreguntaNueva = $preguntas_Model->createPregunta($tituloPreg, $idCuestionario, $pmax);

        // Miramos cuantos había antes
        $query = $db->consultaPreparada("SELECT COUNT(*) FROM pregunta; ");
        mysqli_stmt_execute($query);
        mysqli_stmt_bind_result($query, $despues);
        mysqli_stmt_fetch($query);
        $db->closeFreeStatement($query);


        $this->assertEquals($despues, ($antes + 1));
        $this->assertTrue($result);


//        //Restablecemos la BD borrando el ejemplo
        $query = $db->consultaPreparada("DELETE FROM cuestionario WHERE idCuestionario = $idCuestionario; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);



    }

    /**
     * Test que comprueba si la BD, devuelve la puntuación máxima de una pregunta.
     */
    public function testGet_MaximaPuntuacion()
    {
        $db = new Database();
        $db->conectar();
        $cuestionario_Model = new Cuestionario_Model();
        $preguntas_Model = new Preguntas_Model();

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


        //Creamos varias pregunta de prueba
        $tituloPreg = "tituloPreg123456789;";
        $pmax = 125;
        $idPreguntaNueva1 = $preguntas_Model->createPregunta($tituloPreg, $idCuestionario, $pmax);


        $max_puntuacion = $preguntas_Model->get_maxPunt_pregunta($idPreguntaNueva1);

        $this->assertEquals($max_puntuacion, 125);


//        //Restablecemos la BD borrando el ejemplo
        $query = $db->consultaPreparada("DELETE FROM cuestionario WHERE idCuestionario = $idCuestionario; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);



    }

    /**
     * Test que comprueba si la BD, devuelve la puntuación máxima de un cuestionario.
     */
    public function testGet_PuntMaxima_Cuestionario()
    {
        $db = new Database();
        $db->conectar();
        $cuestionario_Model = new Cuestionario_Model();
        $preguntas_Model = new Preguntas_Model();

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


        //Creamos varias pregunta de prueba
        $tituloPreg = "tituloPreg123456789;";

        $idPreguntaNueva1 = $preguntas_Model->createPregunta($tituloPreg, $idCuestionario, 15);
        $idPreguntaNueva2 = $preguntas_Model->createPregunta($tituloPreg, $idCuestionario, 60);
        $idPreguntaNueva3 = $preguntas_Model->createPregunta($tituloPreg, $idCuestionario, 25);


        $puntMax = $preguntas_Model->getPuntMaxima_Cuestionario($idCuestionario);

        $this->assertEquals(100, $puntMax);


//        //Restablecemos la BD borrando el ejemplo
        $query = $db->consultaPreparada("DELETE FROM cuestionario WHERE idCuestionario = $idCuestionario; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);




    }

    /**
     * Test que comprueba si la BD, devuelve a qué pregunta corresponde una respuesta.
     */
    public function testGetIDpregunta_respuesta (){


            $db = new Database();
            $db->conectar();
            $cuestionario_Model = new Cuestionario_Model();
            $preguntas_Model = new Preguntas_Model();
            $respuestas_Model = new Respuestas_Model();

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
            $idRepuestaNueva = $respuestas_Model->createRespuestas($idPreguntaNueva, $titRespuesta, $correcta);


          //miramos si la respuesta pertenece a la pregunta asignada

            $idPregunta_Respuesta = $preguntas_Model->getIdPregunta_respuesta($idRepuestaNueva);

            $this->assertEquals($idPregunta_Respuesta, $idPreguntaNueva);



//        //Restablecemos la BD borrando el ejemplo
            $query = $db->consultaPreparada("DELETE FROM cuestionario WHERE idCuestionario = $idCuestionario; ");
            mysqli_stmt_execute($query);
            $db->closeFreeStatement($query);


        }



}
 