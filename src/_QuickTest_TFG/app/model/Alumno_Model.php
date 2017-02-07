<?php
require_once "Database.class.php";

/**
 * @author Alejandro Martínez García <amg0098@alu.ubu.es>
 * @version 1.0.
 */

/**
 * Class Alumno_Model.
 * Implementa el modelo.
 * Envía datos de la BD al controlador, para que éste los envíe a la vista.
 * Extiende de la clase Database.
 */
class Alumno_Model extends Database
{


//TESTED
    /**
     * Registra un alumno en la tabla alumno.
     * @param $idAlu. id del alumno.
     * @param $nombre. nombre del alumno.
     * @param $apellidos. apellidos del alumno.
     * @return bool. True o False.
     */
    function insertarAlumno($idAlu, $nombre, $apellidos)
    {

        $link = $this->conectar();
        $query = $this->consultaPreparada("INSERT IGNORE INTO alumno
                                          (idAlumno,  nombre, apellidos) VALUES
                                           (?, ?,?) ; ");


        $ok = mysqli_stmt_bind_param($query, 'sss', $idAlumno, $nombre, $apellidos);

        $idAlumno = $idAlu;
        $nombre = $nombre;
        $apellidos = $apellidos;

        $ok = mysqli_stmt_execute($query);

        if ($ok == FALSE) {

            echo "Error en la ejecucion de la consulta. <br />";
            echo "error : " . mysqli_stmt_error($query);
            $this->closeFreeStatement($query);
            exit();

        } else {

            mysqli_stmt_fetch($query);
            $this->closeFreeStatement($query);
            return true;

        }

    }

}

?>