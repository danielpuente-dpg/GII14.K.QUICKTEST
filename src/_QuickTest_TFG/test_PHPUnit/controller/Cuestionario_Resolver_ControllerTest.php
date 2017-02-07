<?php

require_once $_SERVER["DOCUMENT_ROOT"] . "/app/controller/Cuestionario_Resolver_Controller.php";

/**
 * @author Alejandro Martínez García
 * Class Cuestionario_Resolver_ControllerTest
 * Clase que contiene los test que comprueban la clase Cuestionario_Resolver_Controller.
 */
class Cuestionario_Resolver_ControllerTest extends PHPUnit_Framework_TestCase
{

    /**
     * Test que comprueba si el alumno ha sido registrado
     */
    public function testRegistrarConexion()
    {
        $db = new Database();
        $db->conectar();
        $cuestionario_Model = new Cuestionario_Model();
        $preguntas_Model = new Preguntas_Model();
        $respuestas_Model = new Respuestas_Model();

        $query = $db->consultaPreparada("DELETE IGNORE FROM cuestionario WHERE idCuestionario = 123456789; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);

        //creamos un cuestionario
        $idCuestionario = 123456789;
        $cuestionario_Model->createCuestionarioEditado($idCuestionario, "cuest123456789", 123456789, "asig123456789", 0.25, 0.25, 0.25, 0.25);


//        $courseName="Programacion123";
//        $activityName="Cuestionario123";
//        $isProfesor=1;
//        $idUser=1;
//        $nombreAlu="Alu123456789";
//        $apeAlu="Ape123456789";
//        $lang="ES";
//        $idAsig=123456789;
//        $ltiConsumerURL="www.prueba.es";

        $cuestionario_Resolver_Controller = new Cuestionario_Resolver_Controller();
        $return = $cuestionario_Resolver_Controller->registrarConexion($idCuestionario);

        $this->assertTrue($return);


        //Restablecemos la BD borrando el ejemplo
        $query = $db->consultaPreparada("DELETE FROM cuestionario WHERE idCuestionario = $idCuestionario; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);


    }

    /**
     * Test que comprueba si un cuestionario guarda las respuestas del alumno
     */
    public function testGuardarCadaRespuesta()
    {


        $db = new Database();
        $db->conectar();
        $cuestionario_Model = new Cuestionario_Model();
        $preguntas_Model = new Preguntas_Model();
        $respuestas_Model = new Respuestas_Model();
        $alumno_Model = new Alumno_Model();

        $query = $db->consultaPreparada("DELETE IGNORE FROM cuestionario WHERE idCuestionario = 123456789; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);

//creamos un cuestionario para relacionarlo con la pregunta
        $idCuestionario = 123456789;
        $cuestionario_Model->createCuestionarioEditado($idCuestionario, "cuest123456789", 123456789, "asig123456789", 0.25, 0.25, 0.25, 0.25);

//creamos una pregunta para relacionarla con la respuesta
        $tituloPreg = "tituloPreg123456789";
        $pmax = 10;
        $idPreguntaNueva = $preguntas_Model->createPregunta($tituloPreg, $idCuestionario, $pmax);

        //Creamos un alumno
        $idAlu = 123456789;
        $alumno_Model->insertarAlumno($idAlu, "alu1", "ape1");

        //creamos 2 respuestas
        $tituloRes1 = "r1";
        $tituloRes2 = "r2";
        $idRespuestaNueva1 = $respuestas_Model->createRespuestas($idPreguntaNueva, $tituloRes1, 0);
        $idRespuestaNueva2 = $respuestas_Model->createRespuestas($idPreguntaNueva, $tituloRes2, 1);


        $cuestionario_Resolver_Controller = new Cuestionario_Resolver_Controller();

        //Funcion a comprobar
        $return = $cuestionario_Resolver_Controller->guardarCadaRespuesta($idRespuestaNueva1, "bg-success", 123456789, $idPreguntaNueva);


        $this->assertTrue($return);


        //Restablecemos la BD borrando el ejemplo
        $query = $db->consultaPreparada("DELETE FROM cuestionario WHERE idCuestionario = $idCuestionario; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);

        $query4 = $db->consultaPreparada("DELETE FROM alumno WHERE idAlumno = $idAlu; ");
        mysqli_stmt_execute($query4);
        $db->closeFreeStatement($query4);

    }

    /**
     * Test que comprueba si la tasa de fiabilidad ha sido bien generada.
     */
    public function testGetTasaFiabilidad()
    {


        $db = new Database();
        $db->conectar();
        $cuestionario_Model = new Cuestionario_Model();


        $query = $db->consultaPreparada("DELETE IGNORE FROM cuestionario WHERE idCuestionario = 123456789; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);

//creamos un cuestionario para relacionarlo con la pregunta
        $idCuestionario = 123456789;
        $cuestionario_Model->createCuestionarioEditado($idCuestionario, "cuest123456789", 123456789, "asig123456789", 0.25, 0.25, 0.25, 0.25);

        //registramos 6 conexiones
        for ($i = 0; $i < 6; $i++) {
            $cuestionario_Model->incAlumnosConectados($idCuestionario);
        }

        $cuestionario_Resolver_Controller = new Cuestionario_Resolver_Controller();

        //Funcion a comprobar
        $tasa = $cuestionario_Resolver_Controller->getTasaFiabilidad($idCuestionario);


        $this->assertEquals($tasa, (6 / 3));


        //Restablecemos la BD borrando el ejemplo
        $query = $db->consultaPreparada("DELETE FROM cuestionario WHERE idCuestionario = $idCuestionario; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);


    }

    /**
     * Test que comprueba si una pregunta tiene comodín verde.
     */
    public function testHasComodinVerde()
    {


        $db = new Database();
        $db->conectar();
        $cuestionario_Model = new Cuestionario_Model();
        $preguntas_Model = new Preguntas_Model();
        $respuestas_Model = new Respuestas_Model();
        $alumno_Model = new Alumno_Model();

        $query = $db->consultaPreparada("DELETE IGNORE FROM cuestionario WHERE idCuestionario = 123456789; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);

//creamos un cuestionario para relacionarlo con la pregunta
        $idCuestionario = 123456789;
        $cuestionario_Model->createCuestionarioEditado($idCuestionario, "cuest123456789", 123456789, "asig123456789", 0.25, 0.25, 0.25, 0.25);

//creamos una pregunta para relacionarla con la respuesta
        $tituloPreg = "tituloPreg123456789";
        $pmax = 10;
        $idPreguntaNueva = $preguntas_Model->createPregunta($tituloPreg, $idCuestionario, $pmax);

        //Creamos un alumno
        $idAlu = 123456789;
        $alumno_Model->insertarAlumno($idAlu, "alu1", "ape1");

        //creamos 2 respuestas
        $tituloRes1 = "r1";
        $tituloRes2 = "r2";
        $respuestas_Model->createRespuestas($idPreguntaNueva, $tituloRes1, 0);
        $respuestas_Model->createRespuestas($idPreguntaNueva, $tituloRes2, 1);


        $cuestionario_Resolver_Controller = new Cuestionario_Resolver_Controller();

        //Funcion a comprobar
        $preguntasVerde = $cuestionario_Resolver_Controller->hasComodinVerde($idCuestionario);


        $this->assertEquals(gettype($preguntasVerde), "string");


        //Restablecemos la BD borrando el ejemplo
        $query = $db->consultaPreparada("DELETE FROM cuestionario WHERE idCuestionario = $idCuestionario; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);

        $query4 = $db->consultaPreparada("DELETE FROM alumno WHERE idAlumno = $idAlu; ");
        mysqli_stmt_execute($query4);
        $db->closeFreeStatement($query4);

    }

    /**
     * Test que comprueba si un cuestionario tiene comodín ámbar.
     */
    public function testHasComodinAmbar()
    {


        $db = new Database();
        $db->conectar();
        $cuestionario_Model = new Cuestionario_Model();
        $preguntas_Model = new Preguntas_Model();
        $respuestas_Model = new Respuestas_Model();
        $alumno_Model = new Alumno_Model();

        $query = $db->consultaPreparada("DELETE IGNORE FROM cuestionario WHERE idCuestionario = 123456789; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);

//creamos un cuestionario para relacionarlo con la pregunta
        $idCuestionario = 123456789;
        $cuestionario_Model->createCuestionarioEditado($idCuestionario, "cuest123456789", 123456789, "asig123456789", 0.25, 0.25, 0.25, 0.25);

//creamos una pregunta para relacionarla con la respuesta
        $tituloPreg = "tituloPreg123456789";
        $pmax = 10;
        $idPreguntaNueva = $preguntas_Model->createPregunta($tituloPreg, $idCuestionario, $pmax);

        //Creamos un alumno
        $idAlu = 123456789;
        $alumno_Model->insertarAlumno($idAlu, "alu1", "ape1");

        //creamos 2 respuestas
        $tituloRes1 = "r1";
        $tituloRes2 = "r2";
        $respuestas_Model->createRespuestas($idPreguntaNueva, $tituloRes1, 0);
        $respuestas_Model->createRespuestas($idPreguntaNueva, $tituloRes2, 1);


        $cuestionario_Resolver_Controller = new Cuestionario_Resolver_Controller();

        //Funcion a comprobar
        $preguntasAmbar = $cuestionario_Resolver_Controller->hasComodinAmbar($idCuestionario);


        $this->assertEquals(gettype($preguntasAmbar), "string");


        //Restablecemos la BD borrando el ejemplo
        $query = $db->consultaPreparada("DELETE FROM cuestionario WHERE idCuestionario = $idCuestionario; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);

        $query4 = $db->consultaPreparada("DELETE FROM alumno WHERE idAlumno = $idAlu; ");
        mysqli_stmt_execute($query4);
        $db->closeFreeStatement($query4);

    }

    /**
     * Test que comprueba si un cuestionario registra al alumno
     */
    public function testRegistrarAlumno()
    {
        $db = new Database();
        $db->conectar();
        $cuestionario_Model = new Cuestionario_Model();
        $alumno_Model = new Alumno_Model();
        $respuestas_Model = new Respuestas_Model();


        //Creamos un alumno
        $idAlu = 123456789;
        $alumno_Model->insertarAlumno($idAlu, "alu1", "ape1");


//
        $cuestionario_Resolver_Controller = new Cuestionario_Resolver_Controller();
        $return = $cuestionario_Resolver_Controller->registrarAlumno($idAlu, "nombre", "ape");

        $this->assertTrue($return);


        $query4 = $db->consultaPreparada("DELETE FROM alumno WHERE idAlumno = $idAlu; ");
        mysqli_stmt_execute($query4);
        $db->closeFreeStatement($query4);

    }

    /**
     * Test que comprueba si un cuestionario es resuelto por segunda vez, por un alumno.
     */
    public function testCheckSegundoIntento()
    {


        $db = new Database();
        $db->conectar();
        $cuestionario_Model = new Cuestionario_Model();
        $alumno_Model = new Alumno_Model();


        $query = $db->consultaPreparada("DELETE IGNORE FROM cuestionario WHERE idCuestionario = 123456789; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);

//creamos un cuestionario para relacionarlo con la pregunta
        $idCuestionario = 123456789;
        $cuestionario_Model->createCuestionarioEditado($idCuestionario, "cuest123456789", 123456789, "asig123456789", 0.25, 0.25, 0.25, 0.25);

        //Creamos un alumno
        $idAlu = 123456789;
        $alumno_Model->insertarAlumno($idAlu, "alu1", "ape1");


        $cuestionario_Resolver_Controller = new Cuestionario_Resolver_Controller();

        //Funcion a comprobar
        $preguntasRespondidas = $cuestionario_Resolver_Controller->checkSegundoIntento($idCuestionario, $idAlu);


        $this->assertEquals(gettype($preguntasRespondidas), "NULL");


        //Restablecemos la BD borrando el ejemplo
        $query = $db->consultaPreparada("DELETE FROM cuestionario WHERE idCuestionario = $idCuestionario; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);

        $query4 = $db->consultaPreparada("DELETE FROM alumno WHERE idAlumno = $idAlu; ");
        mysqli_stmt_execute($query4);
        $db->closeFreeStatement($query4);


    }

    /**
     * Test que comprueba si un cuestionario tiene correctamente los resultados.
     */
    public function testMostarResultados()
    {


        $db = new Database();
        $db->conectar();
        $cuestionario_Model = new Cuestionario_Model();
        $alumno_Model = new Alumno_Model();


        $query = $db->consultaPreparada("DELETE IGNORE FROM cuestionario WHERE idCuestionario = 123456789; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);

        //creamos un cuestionario para relacionarlo con la pregunta
        $idCuestionario = 123456789;
        $cuestionario_Model->createCuestionarioEditado($idCuestionario, "cuest123456789", 123456789, "asig123456789", 0.25, 0.25, 0.25, 0.25);

        //Creamos un alumno
        $idAlu = 123456789;
        $alumno_Model->insertarAlumno($idAlu, "alu1", "ape1");


        $cuestionario_Resolver_Controller = new Cuestionario_Resolver_Controller();

        //Funcion a comprobar
        $resultados = $cuestionario_Resolver_Controller->mostarResultados($idCuestionario, $idAlu, "programacion");


        $this->assertEquals(gettype($resultados), "array");


        //Restablecemos la BD borrando el ejemplo
        $query = $db->consultaPreparada("DELETE FROM cuestionario WHERE idCuestionario = $idCuestionario; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);

        $query4 = $db->consultaPreparada("DELETE FROM alumno WHERE idAlumno = $idAlu; ");
        mysqli_stmt_execute($query4);
        $db->closeFreeStatement($query4);


    }

    /**
     * Test que comprueba si el orden de respuesta ha sido bien generado.
     */
    public function testGetOrden()
    {


        $db = new Database();
        $db->conectar();
        $cuestionario_Model = new Cuestionario_Model();
        $alumno_Model = new Alumno_Model();


        $query = $db->consultaPreparada("DELETE IGNORE FROM cuestionario WHERE idCuestionario = 123456789; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);

        //creamos un cuestionario para relacionarlo con la pregunta
        $idCuestionario = 123456789;
        $cuestionario_Model->createCuestionarioEditado($idCuestionario, "cuest123456789", 123456789, "asig123456789", 0.25, 0.25, 0.25, 0.25);


        $cuestionario_Resolver_Controller = new Cuestionario_Resolver_Controller();

        //Funcion a comprobar
        $orden = $cuestionario_Resolver_Controller->calculaOrdenPrimeraVez($idCuestionario);


        $this->assertEquals($orden, 1);


        //Restablecemos la BD borrando el ejemplo
        $query = $db->consultaPreparada("DELETE FROM cuestionario WHERE idCuestionario = $idCuestionario; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);


    }

}
