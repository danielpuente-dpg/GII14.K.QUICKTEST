<?php


require_once $_SERVER["DOCUMENT_ROOT"] . "/app/model/Alumno_Model.php";



/**
 * @author Alejandro Martínez García
 * Class Alumno_has_cuestionario_ModelTest
 * Clase que contiene los test que comprueban el modelo Alumno_has_cuestionario.
 */
class Alumno_ModelTest extends PHPUnit_Framework_TestCase
{

    /**
     * Test que comprueba que la base de datos, inserta un nuevo alumno
     */
    public function testInsertaAlumno()
    {

        $db = new Database();
        $db->conectar();
        $alumno_Model = new Alumno_Model();

        //Miramos cuantos había antes
        $query = $db->consultaPreparada("SELECT COUNT(*) FROM alumno; ");
        mysqli_stmt_execute($query);
        mysqli_stmt_bind_result($query, $antes);
        mysqli_stmt_fetch($query);
        $db->closeFreeStatement($query);

        $idAlumno = "123456789";
        $nombre = "NOMBRE1";
        $apellidos = "APELLIDO1";

        $alumno_Model->insertarAlumno($idAlumno, $nombre, $apellidos);


        // Miramos cuantos había antes
        $query2 = $db->consultaPreparada("SELECT COUNT(*) FROM alumno; ");
        mysqli_stmt_execute($query2);
        mysqli_stmt_bind_result($query2, $despues);
        mysqli_stmt_fetch($query2);
        $db->closeFreeStatement($query2);



        //mysqli devueve un Resource si la conexión es satisfactoria y False si no lo es
        $this->assertEquals($despues, ($antes + 1));

        //Restablecemos la BD borrando el ejemplo
        $query3 = $db->consultaPreparada("DELETE FROM alumno WHERE idAlumno = '123456789'; ");
        mysqli_stmt_execute($query3);
        //  $db->closeFreeStatement($query3);


    }



}
 