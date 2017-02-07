<?php
require_once $_SERVER["DOCUMENT_ROOT"] . "/app/model/Database.class.php";
require_once $_SERVER["DOCUMENT_ROOT"] . "/app/model/Alumno_has_cuestionario_Model.php";
require_once $_SERVER["DOCUMENT_ROOT"] . "/app/model/Alumno_Model.php";
require_once $_SERVER["DOCUMENT_ROOT"] . "/app/model/Cuestionario_Model.php";
require_once $_SERVER["DOCUMENT_ROOT"] . "/app/model/Respuestas_Model.php";
require_once $_SERVER["DOCUMENT_ROOT"] . "/app/model/Preguntas_Model.php";



/**
 * @author Alejandro Martínez García
 * Class Cuestionario_ModelTest
 * Clase que contiene los test que comprueban el modelo Cuestionario_Model.
 */
class Cuestionario_ModelTest extends PHPUnit_Framework_TestCase
{

    /**
     * Test que comprueba que la base de datos, crea un cuestionario con id autoincrementado.
     */
    public function testCrearCuestionarioAUTOINCREMENT()
    {
        $db = new Database();
        $db->conectar();
        $cuestionario_Model = new Cuestionario_Model();

        //Miramos cuantos había antes
        $query = $db->consultaPreparada("SELECT COUNT(*) FROM cuestionario; ");
        mysqli_stmt_execute($query);
        mysqli_stmt_bind_result($query, $antes);
        mysqli_stmt_fetch($query);
        $db->closeFreeStatement($query);

        $idAsig = 123456789;
        $cuestTit = "cuest123456789";
        $nombreAsig = "asig123456789";
        $corrVerdT = 0.25;
        $corrVerdF = 0.25;
        $corrAmT = 0.25;
        $corrAmF = 0.25;


        $idCuestionarioCreado = $cuestionario_Model->createCuestionario($cuestTit, $idAsig, $nombreAsig, $corrVerdT, $corrVerdF, $corrAmT, $corrAmF);


        // Miramos cuantos había antes
        $query = $db->consultaPreparada("SELECT COUNT(*) FROM cuestionario; ");
        mysqli_stmt_execute($query);
        mysqli_stmt_bind_result($query, $despues);
        mysqli_stmt_fetch($query);
        $db->closeFreeStatement($query);


        $this->assertEquals($despues, ($antes + 1));

//        //Restablecemos la BD borrando el ejemplo
        $query = $db->consultaPreparada("DELETE FROM cuestionario WHERE idCuestionario = $idCuestionarioCreado; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);


    }

    /**
     * Test que comprueba que la base de datos, que la base de datos devuelve un cuestionario, sin filtrar por id.
     */
    public function testCrearCuestionarioEditado_SIN_AUTOINCREMENT()
    {
        $db = new Database();
        $db->conectar();
        $cuestionario_Model = new Cuestionario_Model();

        //Miramos cuantos había antes
        $query = $db->consultaPreparada("SELECT COUNT(*) FROM cuestionario; ");
        mysqli_stmt_execute($query);
        mysqli_stmt_bind_result($query, $antes);
        mysqli_stmt_fetch($query);
        $db->closeFreeStatement($query);
        $idCuestionario = 123456789;
        $idAsig = 123456789;
        $cuestTit = "cuest123456789";
        $nombreAsig = "asig123456789";
        $corrVerdT = 0.25;
        $corrVerdF = 0.25;
        $corrAmT = 0.25;
        $corrAmF = 0.25;


        $result = $cuestionario_Model->createCuestionarioEditado($idCuestionario, $cuestTit, $idAsig, $nombreAsig, $corrVerdT, $corrVerdF, $corrAmT, $corrAmF);


        // Miramos cuantos había antes
        $query = $db->consultaPreparada("SELECT COUNT(*) FROM cuestionario; ");
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
     * Test que comprueba que la base de datos, devuelve los cuestionarios de una asignatura.
     */
    public function testSeleccionarCuestionario_PorAsignatura()
    {
        $db = new Database();
        $db->conectar();
        $cuestionario_Model = new Cuestionario_Model();


        //Creamos 2 cuestionario de prueba
        $idCuestionario1 = 123456789;
        $idCuestionario2 = 987654321;
        $idAsig = 123456789;
        $cuestTit = "cuest123456789";
        $nombreAsig = "asig123456789";
        $corrVerdT = 0.25;
        $corrVerdF = 0.25;
        $corrAmT = 0.25;
        $corrAmF = 0.25;
        $cuestionario_Model->createCuestionarioEditado($idCuestionario1, $cuestTit, $idAsig, $nombreAsig, $corrVerdT, $corrVerdF, $corrAmT, $corrAmF);
        $cuestionario_Model->createCuestionarioEditado($idCuestionario2, $cuestTit, $idAsig, $nombreAsig, $corrVerdT, $corrVerdF, $corrAmT, $corrAmF);


        //Seleccionamos el cuestionario recien creado por la asignatura a la que pertence.
        $arrayResultado = $cuestionario_Model->getCuestionariosAsignatura($idAsig);

        $this->assertEquals(sizeof($arrayResultado), 2);


//        //Restablecemos la BD borrando el ejemplo
        $query = $db->consultaPreparada("DELETE FROM cuestionario WHERE idCuestionario = $idCuestionario1; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);

        $query = $db->consultaPreparada("DELETE FROM cuestionario WHERE idCuestionario = $idCuestionario2; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);


    }

    /**
     * Test que comprueba que la base de datos, los correctores de un cuestionario.
     */
    public function testGetCorrectores()
    {
        $db = new Database();
        $db->conectar();
        $cuestionario_Model = new Cuestionario_Model();


        //Creamos 2 cuestionario de prueba
        $idCuestionario1 = 123456789;
        $idAsig = 123456789;
        $cuestTit = "cuest123456789";
        $nombreAsig = "asig123456789";
        $corrVerdT = 1.0;
        $corrVerdF = 2.0;
        $corrAmT = 3.0;
        $corrAmF = 4.0;


        $cuestionario_Model->createCuestionarioEditado($idCuestionario1, $cuestTit, $idAsig, $nombreAsig, $corrVerdT, $corrVerdF, $corrAmT, $corrAmF);


        //Seleccionamos el cuestionario recien creado por la asignatura a la que pertence.
        $correctores = $cuestionario_Model->getCorrectores($idCuestionario1);


        $this->assertEquals($correctores['correctorVerdeTrue'], 1.0);
        $this->assertEquals($correctores['correctorVerdeFalse'], 2.0);
        $this->assertEquals($correctores['correctorAmarilloTrue'], 3.0);
        $this->assertEquals($correctores['correctorAmarilloFalse'], 4.0);


        //Restablecemos la BD borrando el ejemplo
        $query = $db->consultaPreparada("DELETE FROM cuestionario WHERE idCuestionario = $idCuestionario1; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);


    }

    /**
     * Test que comprueba que la base de datos, devuelve los datos de un cuestionario.
     */
    public function testGetInfoCuestionarios()
    {
        $db = new Database();
        $db->conectar();
        $cuestionario_Model = new Cuestionario_Model();


        //Creamos 2 cuestionario de prueba
        $idCuestionario1 = 123456789;
        $idAsig = 123456789;
        $cuestTit = "cuest123456789";
        $nombreAsig = "asig123456789";
        $corrVerdT = 1.0;
        $corrVerdF = 2.0;
        $corrAmT = 3.0;
        $corrAmF = 4.0;


        $cuestionario_Model->createCuestionarioEditado($idCuestionario1, $cuestTit, $idAsig, $nombreAsig, $corrVerdT, $corrVerdF, $corrAmT, $corrAmF);


        //Seleccionamos el cuestionario recien creado por la asignatura a la que pertence.
        $info = $cuestionario_Model->getInfoCuestionarios($idCuestionario1);


        $this->assertEquals($info['idCuestionario'], $idCuestionario1);
        $this->assertEquals($info['titulo'], $cuestTit);
        $this->assertEquals($info['contadorAlumnos'], 0);
        $this->assertEquals($info['idAsignatura'], $idAsig);
        $this->assertEquals($info['nombreAsignatura'], $nombreAsig);
        $this->assertEquals($info['correctorVerdeTrue'], $corrVerdT);
        $this->assertEquals($info['correctorVerdeFalse'], $corrVerdF);
        $this->assertEquals($info['correctorAmarilloTrue'], $corrAmT);
        $this->assertEquals($info['correctorAmarilloFalse'], $corrAmF);


        //Restablecemos la BD borrando el ejemplo
        $query = $db->consultaPreparada("DELETE FROM cuestionario WHERE idCuestionario = $idCuestionario1; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);


    }

    /**
     * Test que comprueba que la base de datos, devuelve los alumnos conectados a un cuestionario.
     */
    public function testGetAlumnosConectados()
    {
        $db = new Database();
        $db->conectar();
        $cuestionario_Model = new Cuestionario_Model();


        //Creamos 1 cuestionario de prueba
        $idCuestionario1 = 123456789;
        $contadorAlumnos = 1500;


        $query = $db->consultaPreparada("INSERT INTO cuestionario  ( idCuestionario, contadorAlumnos)
                                          VALUES ( $idCuestionario1, $contadorAlumnos ) ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);


        //Seleccionamos el cuestionario recien creado por la asignatura a la que pertence.
        $cuantos = $cuestionario_Model->getAlumnosConectados($idCuestionario1);


        $this->assertEquals($cuantos, $contadorAlumnos);


        //Restablecemos la BD borrando el ejemplo
        $query = $db->consultaPreparada("DELETE FROM cuestionario WHERE idCuestionario = $idCuestionario1; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);


    }

    /**
     * Test que comprueba que la base de datos, borra un cuestionario.
     */
    public function testBorrarCuestionario()
    {
        $db = new Database();
        $db->conectar();
        $cuestionario_Model = new Cuestionario_Model();


        //Creamos 1 cuestionario de prueba
        $idCuestionario1 = 123456789;



        $query = $db->consultaPreparada("INSERT INTO cuestionario  ( idCuestionario)
                                          VALUES ( $idCuestionario1) ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);


        //Seleccionamos el cuestionario recien creado por la asignatura a la que pertence.
        $idCuestBorrado = $cuestionario_Model->borrarCuestionario($idCuestionario1);


        $this->assertEquals($idCuestBorrado, $idCuestionario1);





    }


    public function testDuplicarCuestionario()
    {
        $db = new Database();
        $db->conectar();
        $cuestionario_Model = new Cuestionario_Model();


        $idCuestionario1 = 123456789;
        $idAsig = 123456789;
        $cuestTit = "cuest123456789";
        $nombreAsig = "asig123456789";
        $corrVerdT = 0.25;
        $corrVerdF = 0.25;
        $corrAmT = 0.25;
        $corrAmF = 0.25;
        $cuestionario_Model->createCuestionarioEditado($idCuestionario1, $cuestTit, $idAsig, $nombreAsig, $corrVerdT, $corrVerdF, $corrAmT, $corrAmF);

        //Miramos cuantos había antes
        $query = $db->consultaPreparada("SELECT COUNT(*) FROM cuestionario; ");
        mysqli_stmt_execute($query);
        mysqli_stmt_bind_result($query, $antes);
        mysqli_stmt_fetch($query);
        $db->closeFreeStatement($query);

        $idCuestionarioClonado = $cuestionario_Model->duplicarCuestionario($idCuestionario1);


        // Miramos cuantos había antes
        $query = $db->consultaPreparada("SELECT COUNT(*) FROM cuestionario; ");
        mysqli_stmt_execute($query);
        mysqli_stmt_bind_result($query, $despues);
        mysqli_stmt_fetch($query);
        $db->closeFreeStatement($query);


        $this->assertEquals($despues, ($antes + 1));



//        //Restablecemos la BD borrando el ejemplo
        $query = $db->consultaPreparada("DELETE FROM cuestionario WHERE idCuestionario = $idCuestionario1 ;");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);

        $query = $db->consultaPreparada("DELETE FROM cuestionario WHERE idCuestionario = $idCuestionarioClonado; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);


    }

    public function testIncrementarAlumnosConectados()
    {
        $db = new Database();
        $db->conectar();
        $cuestionario_Model = new Cuestionario_Model();


        $idCuestionario1 = 123456789;
        $idAsig = 123456789;
        $cuestTit = "cuest123456789";
        $nombreAsig = "asig123456789";
        $corrVerdT = 0.25;
        $corrVerdF = 0.25;
        $corrAmT = 0.25;
        $corrAmF = 0.25;
        $cuestionario_Model->createCuestionarioEditado($idCuestionario1, $cuestTit, $idAsig, $nombreAsig, $corrVerdT, $corrVerdF, $corrAmT, $corrAmF);

        $result = $cuestionario_Model->incAlumnosConectados($idCuestionario1);

        //Miramos cuantos había antes
        $query = $db->consultaPreparada("SELECT contadorAlumnos FROM cuestionario WHERE idCuestionario= $idCuestionario1; ");
        mysqli_stmt_execute($query);
        mysqli_stmt_bind_result($query, $despues);
        mysqli_stmt_fetch($query);
        $db->closeFreeStatement($query);


        $this->assertEquals(1, $despues);
        $this->assertEquals(true, $result);




//        //Restablecemos la BD borrando el ejemplo
        $query = $db->consultaPreparada("DELETE FROM cuestionario WHERE idCuestionario = $idCuestionario1; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);


    }

    public function testGetIdcuestionario_respuesta()
    {
        $db = new Database();
        $db->conectar();
        $cuestionario_Model = new Cuestionario_Model();
        $preguntas_Model = new Preguntas_Model();
        $respuestas_Model = new Respuestas_Model();



        //Creamos 2 cuestionario de prueba
        $idCuestionario1 = 123456789;
        $idAsig = 123456789;
        $cuestTit = "cuest123456789";
        $nombreAsig = "asig123456789";
        $corrVerdT = 1.0;
        $corrVerdF = 2.0;
        $corrAmT = 3.0;
        $corrAmF = 4.0;


        $cuestionario_Model->createCuestionarioEditado($idCuestionario1, $cuestTit, $idAsig, $nombreAsig, $corrVerdT, $corrVerdF, $corrAmT, $corrAmF);

        //creamos una pregunta para relacionarla con la respuesta
        $tituloPreg = "tituloPreg123456789";
        $pmax = 10;
        $idPreguntaNueva = $preguntas_Model->createPregunta($tituloPreg, $idCuestionario1, $pmax);


        //creamos 1 respuestas
        $tituloRes1 = "r1";
        $idRespuestaNueva1 = $respuestas_Model->createRespuestas($idPreguntaNueva, $tituloRes1, 0);


        //Seleccionamos el cuestionario recien creado por la asignatura a la que pertence.
        $idCuest_respuesta = $cuestionario_Model->getIdCuest_respuesta($idRespuestaNueva1);


        $this->assertEquals($idCuest_respuesta, $idCuestionario1);


        //Restablecemos la BD borrando el ejemplo
        $query = $db->consultaPreparada("DELETE FROM cuestionario WHERE idCuestionario = $idCuestionario1; ");
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);


    }



}
