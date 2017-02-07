<?php

require_once "Database.class.php";

/**
 * @author Alejandro Martínez García <amg0098@alu.ubu.es>
 * @version 1.0.
 */

/**
 * Class Alumno_has_respuesta_Model.
 * Implementa el modelo.
 * Envía datos de la BD al controlador, para que éste los envíe a la vista.
 * Extiende de la clase Database.
 */
class Alumno_has_respuesta_Model extends Database
{


//TESTED
    /**
     * Guarda la puntuación del alumno en la tabla alumno_has_respuesta.
     * @param $punt . Puntuación obtenida.
     * @param $idAlu . id del alumno.
     * @param $idRes . id de la respuesta.
     * @param $comUsado . tipo de comodín usado.
     * @param $idPreg . id de la pregunta.
     * @param $correct . es correcta o no.
     * @return bool. True si se ha guardado. False si no.
     */
    function setPuntuacion($punt, $idAlu, $idRes, $comUsado, $idPreg, $correct)
    {

        $this->conectar();
        $query = $this->consultaPreparada(" INSERT IGNORE INTO alumno_has_respuesta
		 									  (alumno_idAlumno, respuesta_idRespuesta, pregunta, puntuacion, comodinUsado, correcta)
		 									  VALUES (?,?,?,?,?,?) ");

        $ok = mysqli_stmt_bind_param($query, 'siidsi', $idAlumno, $idRespuesta, $idPregunta, $puntuacion, $tipoComUsado, $correcta);
        $idRespuesta = $idRes;
        $idPregunta = $idPreg;
        $idAlumno = (String)$idAlu;
        $tipoComUsado = $comUsado;
        $puntuacion = $punt;
        $correcta = $correct;

        $ok = mysqli_stmt_execute($query);

        if ($ok == FALSE) {

            echo "Error en la ejecucion de la consulta. <br />";
            echo "error : " . mysqli_stmt_error($query);
            $this->closeFreeStatement($query);
            exit;

        } else {

            $this->closeFreeStatement($query);
            return true;

        }


    }

//TESTED
    /**
     * Suma todas las puntuaciones, de las diferentes preguntas, de un mismo cuestionario, para un alumno.
     * @param $idCuest . id del cuestionario.
     * @param $idAlu . id del alumno.
     * @return mixed Suma. False si no.
     */
    function getSumaPuntTotal_Alumno($idCuest, $idAlu)
    {
        $this->conectar();

        $query = $this->consultaPreparada("SELECT sum(puntuacion)
                                                FROM alumno_has_respuesta
                                                JOIN respuesta ON (alumno_has_respuesta.respuesta_idRespuesta = respuesta.idRespuesta)
                                                JOIN pregunta ON ( respuesta.pregunta_idPregunta = pregunta.idPregunta)
                                                JOIN cuestionario ON (pregunta.cuestionario_idCuestionario = cuestionario.idCuestionario)
                                                WHERE idCuestionario = ?
                                                AND alumno_idAlumno  = ?  ");

        $ok = mysqli_stmt_bind_param($query, 'is', $idCuestionario, $alumno_idAlumno);

        $idCuestionario = $idCuest;
        $alumno_idAlumno = $idAlu;

        $ok = mysqli_stmt_execute($query);
        $ok = mysqli_stmt_bind_result($query, $puntuacionTotal);

        if ($ok == FALSE) {

            echo "Error en la ejecucion de la consulta. <br />";
            echo "error : " . mysqli_stmt_error($query);
            $this->closeFreeStatement($query);
            exit();

        } else {

            mysqli_stmt_fetch($query);
            $this->closeFreeStatement($query);
            return $puntuacionTotal;

        }


    }


//TESTED
    /**
     * Devuelve las respuestas guardadas,  de un  cuestionario, para un alumno.
     * @param $idAlu . id del alumno.
     * @param $idCuest . id del cuestionario.
     * @return mixed Suma. False si no.
     */
    function getRespuestasRespondidas_Cuestionario($idAlu, $idCuest)
    {

        $this->conectar();
        $query = $this->consultaPreparada("SELECT respuesta_idRespuesta
                                            FROM alumno_has_respuesta
                                            JOIN respuesta ON (alumno_has_respuesta.respuesta_idRespuesta = respuesta.idRespuesta )
                                            JOIN pregunta ON (respuesta.pregunta_idPregunta = pregunta.idPregunta)
                                            JOIN cuestionario ON (pregunta.cuestionario_idCuestionario = cuestionario.idCuestionario)
                                            WHERE alumno_idAlumno = ?
                                            AND cuestionario.idCuestionario= ?  ; ");

        $ok = mysqli_stmt_bind_param($query, 'si', $idAlumno, $idCuestionario);
        $idAlumno = $idAlu;
        $idCuestionario = $idCuest;

        $ok = mysqli_stmt_execute($query);
        $ok = mysqli_stmt_bind_result($query, $respuesta_idRespuesta);


        if ($ok == FALSE) {
            echo "Error en la ejecucion de la consulta. <br />";
            echo "error : " . mysqli_stmt_error($query);
            $this->closeFreeStatement($query);
            exit();
        } else {

            $respuestas = null;

            while (mysqli_stmt_fetch($query)) {

                $respuestas[] = array('idRespuesta' => $respuesta_idRespuesta);
            }
            $this->closeFreeStatement($query);
            return $respuestas;

        }

    }

    //TESTED

    /**
     * Devuelve la cantidad de comodines, que el alumno ha usado en ese cuestionario, filtrados por el tipo de comodín.
     * @param $idAlu. id del alumno.
     * @param $idCuest. id del cuestionario.
     * @param $tipoCom. tipo comodín.
     * @return mixed Cuantos. False si hay error.
     */
    function getCuantos_Comodin($idAlu, $idCuest, $tipoCom)
    {

       $this->conectar();
        $query = $this->consultaPreparada("SELECT  COUNT(comodinUsado)
                                            FROM alumno_has_respuesta
                                            JOIN respuesta ON (alumno_has_respuesta.respuesta_idRespuesta = respuesta.idRespuesta )
                                            JOIN pregunta ON (respuesta.pregunta_idPregunta = pregunta.idPregunta)
                                            JOIN cuestionario ON (pregunta.cuestionario_idCuestionario = cuestionario.idCuestionario)
                                            WHERE alumno_idAlumno = ?
                                            AND cuestionario.idCuestionario= ?
                                            AND comodinUsado=? ; ");

        $ok = mysqli_stmt_bind_param($query, 'sis', $idAlumno, $idCuestionario, $comodinUsado);
        $idAlumno = $idAlu;
        $idCuestionario = $idCuest;
        $comodinUsado = $tipoCom;

        $ok = mysqli_stmt_execute($query);
        $ok = mysqli_stmt_bind_result($query, $cuantosComodin);


        if ($ok == FALSE) {
            echo "Error en la ejecucion de la consulta. <br />";
            echo "error : " . mysqli_stmt_error($query);
            $this->closeFreeStatement($query);
            exit();
        } else {

            mysqli_stmt_fetch($query);
            $this->closeFreeStatement($query);

            return $cuantosComodin;

        }

    }

//TESTED
    /**
     * Devuelve la suma de las puntuaciones,
     * que el alumno ha obtenido en ese cuestionario, filtrados por el tipo de comodín.
     * @param $idAlu. id del alumno.
     * @param $idCuest. id del cuestionario.
     * @param $tipoCom. tipo comodín.
     * @return mixed Suma. False si hay error.
     */
    function getSumaPuntComodin_Alumno($idAlu, $idCuest, $tipoCom)
    {
        $this->conectar();

        $query = $this->consultaPreparada("SELECT sum(puntuacion)
                                                FROM alumno_has_respuesta
                                                JOIN respuesta ON (alumno_has_respuesta.respuesta_idRespuesta = respuesta.idRespuesta)
                                                JOIN pregunta ON ( respuesta.pregunta_idPregunta = pregunta.idPregunta)
                                                JOIN cuestionario ON (pregunta.cuestionario_idCuestionario = cuestionario.idCuestionario)
                                                WHERE idCuestionario = ?
                                                AND alumno_idAlumno  = ?
                                                AND comodinUsado= ? ");

        $ok = mysqli_stmt_bind_param($query, 'iss', $idCuestionario, $alumno_idAlumno, $comodinUsado);

        $idCuestionario = $idCuest;
        $alumno_idAlumno = $idAlu;
        $comodinUsado = $tipoCom;

        $ok = mysqli_stmt_execute($query);
        $ok = mysqli_stmt_bind_result($query, $puntuacionComodin);

        if ($ok == FALSE) {
            echo "Error en la ejecucion de la consulta. <br />";
            echo "error : " . mysqli_stmt_error($query);
            $this->closeFreeStatement($query);
            exit();
        } else {

            mysqli_stmt_fetch($query);
            $this->closeFreeStatement($query);

            return $puntuacionComodin;

        }
    }

//TESTED
     /**
      * Devuelve cuantas preguntas ha respondido el alumno, en ese cuestionario.
      * @param $idAlu. id del alumno.
      * @param $idCuest. id del cuestionario.
      * @return mixed Cuantos. False si hay error.
     */
    function getCuantasPreguntasRespondidas_Alumno($idAlu, $idCuest)
    {


        $this->conectar();
        $query = $this->consultaPreparada("SELECT  COUNT(pregunta)
                                            FROM alumno_has_respuesta
                                            JOIN respuesta ON (alumno_has_respuesta.respuesta_idRespuesta = respuesta.idRespuesta )
                                            JOIN pregunta ON (respuesta.pregunta_idPregunta = pregunta.idPregunta)
                                            JOIN cuestionario ON (pregunta.cuestionario_idCuestionario = cuestionario.idCuestionario)
                                            WHERE alumno_idAlumno = ?
                                            AND cuestionario.idCuestionario= ?
                                             ; ");

        $ok = mysqli_stmt_bind_param($query, 'si', $idAlumno, $idCuestionario);
        $idAlumno = $idAlu;
        $idCuestionario = $idCuest;

        $ok = mysqli_stmt_execute($query);
        $ok = mysqli_stmt_bind_result($query, $cuantasPreguntas);

        if ($ok == FALSE) {
            echo "Error en la ejecucion de la consulta. <br />";
            echo "error : " . mysqli_stmt_error($query);
            $this->closeFreeStatement($query);
            exit();
        } else {

            mysqli_stmt_fetch($query);
            $this->closeFreeStatement($query);

            return $cuantasPreguntas;

        }

    }

//TESTED
    /**
     * Devuelve cuantas respuestas ha respondido el alumno, en ese cuestionario, correctamente, para un tipo de comodin.
     * @param $idAlu. id del alumno.
     * @param $idCuest. id del cuestionario.
     * @param $tipoCom. tipo comodin.
     * @param $correcta. correcta o no
     * @return mixed. Cuantos. False si hay error.
     */
    function getCuantosAciertos_Comodin($idAlu, $idCuest, $tipoCom, $correcta)
    {
        $this->conectar();
        $query = $this->consultaPreparada("SELECT count(alumno_has_respuesta.correcta)
                                                FROM alumno_has_respuesta
                                                JOIN respuesta ON (alumno_has_respuesta.respuesta_idRespuesta = respuesta.idRespuesta)
                                                JOIN pregunta ON ( respuesta.pregunta_idPregunta = pregunta.idPregunta)
                                                JOIN cuestionario ON (pregunta.cuestionario_idCuestionario = cuestionario.idCuestionario)
                                                WHERE idCuestionario = ?
                                                AND alumno_idAlumno  = ?
                                                AND comodinUsado= ?
                                                AND alumno_has_respuesta.correcta= ? ");

        $ok = mysqli_stmt_bind_param($query, 'issi', $idCuestionario, $alumno_idAlumno, $comodinUsado, $aciertoFallo);

        $idCuestionario = $idCuest;
        $alumno_idAlumno = $idAlu;
        $comodinUsado = $tipoCom;
        $aciertoFallo = $correcta;

        $ok = mysqli_stmt_execute($query);
        $ok = mysqli_stmt_bind_result($query, $aciertos_Comodin);

        if ($ok == FALSE) {

            echo "Error en la ejecucion de la consulta. <br />";
            echo "error : " . mysqli_stmt_error($query);
            $this->closeFreeStatement($query);
            exit();

        } else {

            mysqli_stmt_fetch($query);
            $this->closeFreeStatement($query);
            return $aciertos_Comodin;

        }
    }

}

?>