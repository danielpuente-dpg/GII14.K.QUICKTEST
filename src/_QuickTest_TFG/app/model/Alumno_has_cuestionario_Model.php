<?php

require_once "Database.class.php";

/**
 * @author Alejandro Martínez García <amg0098@alu.ubu.es>
 * @version 1.0.
 */

/**
 * Class Alumno_has_cuestionario_Model.
 * Implementa el modelo.
 * Envía datos de la BD al controlador, para que éste los envíe a la vista.
 * Extiende de la clase Database.
 */
class Alumno_has_cuestionario_Model extends Database
{

//tested
    /**
     * Cuando el alumno acaba el cuestionario, se graba en la tabla alumno_has_cuestionario.
     * @param $idAlu . id del alumno.
     * @param $idCuest . id del cuestionario.
     * @return bool. True si se ha realizado correctamente. False si no.
     */
    function insertarAlumno_Cuestionario($idAlu, $idCuest, $ord)
    {

        $link = $this->conectar();

        $query = $this->consultaPreparada("INSERT IGNORE INTO alumno_has_cuestionario
                                          (alumno_idAlumno,  	cuestionario_idCuestionario, orden) VALUES
                                           (?, ?, ?) ; ");

        $ok = mysqli_stmt_bind_param($query, 'sii', $idAlumno, $idCuestionario, $orden);

        $idAlumno = $idAlu;
        $idCuestionario = $idCuest;
        $orden= $ord;

        $ok = mysqli_stmt_execute($query);

        if ($ok == FALSE) {
            echo "Error en la ejecucion de la consulta. <br />";
            echo "error : " . mysqli_stmt_error($query);
            $this->closeFreeStatement($query);
            return false;
            exit();
        } else {
            mysqli_stmt_fetch($query);
            $this->closeFreeStatement($query);
            return true;
        }

    }

//tested
    /**
     * Verifica si el alumno, ya ha resuelto ese cuestionario.
     * @param $idAlu . id del alumno.
     * @param $idCuest . id del cuestionario.
     * @return mixed. 1 si se ha realizado correctamente. False si no.
     */
    function estaResuelto($idAlu, $idCuest)
    {
        $this->conectar();
        $query = $this->consultaPreparada("SELECT count(cuestionario_idCuestionario)
                                                FROM alumno_has_cuestionario

                                                WHERE cuestionario_idCuestionario = ?
                                                AND alumno_idAlumno  = ? ");

        $ok = mysqli_stmt_bind_param($query, 'is', $idCuestionario, $alumno_idAlumno);

        $idCuestionario = $idCuest;
        $alumno_idAlumno = $idAlu;

        $ok = mysqli_stmt_execute($query);
        $ok = mysqli_stmt_bind_result($query, $estaResuelto);

        if ($ok == FALSE) {
            echo "Error en la ejecucion de la consulta.";
            echo "error : " . mysqli_stmt_error($query);
            $this->closeFreeStatement($query);
            exit();
        } else {

            mysqli_stmt_fetch($query);
            $this->closeFreeStatement($query);
            return $estaResuelto;

        }
    }

//tested
    /**
     * Cuenta cuántos alumnos, han resuelto el cuestionario.
     * @param $idCuest . id del cuestionario.
     * @return mixed. Cuántos le han resuelto. False si hay error.
     */
    function contarAlumnosFinalizado_Cuestionario($idCuest)
    {
        $this->conectar();
        $query = $this->consultaPreparada("SELECT count(cuestionario_idCuestionario)
                                           FROM alumno_has_cuestionario
                                           WHERE cuestionario_idCuestionario = ? ");

        $ok = mysqli_stmt_bind_param($query, 'i', $idCuestionario);
        $idCuestionario = $idCuest;


        $ok = mysqli_stmt_execute($query);
        $ok = mysqli_stmt_bind_result($query, $cuantosHanAcabado);

        if ($ok == FALSE) {
            echo "Error en la ejecucion de la consulta. <br />";
            echo "error : " . mysqli_stmt_error($query);
            $this->closeFreeStatement($query);
            exit();

        } else {

            mysqli_stmt_fetch($query);
            $this->closeFreeStatement($query);
            return $cuantosHanAcabado;

        }
    }

    /**
     * Cuenta cuántos alumnos, han resuelto el cuestionario.
     * @param $idCuest . id del cuestionario.
     * @return mixed. Cuántos le han resuelto. False si hay error.
     */
    function selectOrdenRespuesta($idCuest, $idAlu)
    {
        $this->conectar();
        $query = $this->consultaPreparada("SELECT orden
                                           FROM alumno_has_cuestionario
                                           WHERE cuestionario_idCuestionario = ?
                                            AND alumno_idAlumno= ? ");

        $ok = mysqli_stmt_bind_param($query, 'is', $idCuestionario, $idAlumno);
        $idCuestionario = $idCuest;
        $idAlumno=$idAlu;

        $ok = mysqli_stmt_execute($query);
        $ok = mysqli_stmt_bind_result($query, $orden);

        if ($ok == FALSE) {

            echo "Error en la ejecucion de la consulta. <br />";
            echo "error : " . mysqli_stmt_error($query);
            $this->closeFreeStatement($query);
            exit();

        } else {

            mysqli_stmt_fetch($query);
            $this->closeFreeStatement($query);
            return $orden;

        }
    }



}

?>