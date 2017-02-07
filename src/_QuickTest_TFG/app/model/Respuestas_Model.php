<?php
require_once "Database.class.php";

/**
 * @author Alejandro Martínez García <amg0098@alu.ubu.es>
 * @version 1.0.
 */

/**
 * Class Respuestas_Model.
 * Implementa el modelo.
 * Envía datos de la BD al controlador, para que éste los envíe a la vista.
 * Extiende de la clase Database.
 */
class Respuestas_Model extends Database
{


//TESTED
    /**
     * Devuelve las respuestas de las preguntas que recibe por parámetro.
     * @param $p . array con las preguntas.
     * @return array|null. Respuestas, o null si hay error.
     */
    function getRespuestas($p)
    {

        $this->conectar();
        $query = $this->consultaPreparada("SELECT idRespuesta, titulo, correcta, pregunta_idPregunta, contador
											FROM respuesta
											WHERE pregunta_idPregunta = ? ");

        $ok = mysqli_stmt_bind_param($query, 'i', $pregunta_idPregunta);
        $pregunta_idPregunta = $p['idPregunta'];

        $ok = mysqli_stmt_execute($query);
        $ok = mysqli_stmt_bind_result($query, $idRespuesta, $tit_Respuesta, $correcta, $pregunta_idPregunta, $contador);

        if ($ok == FALSE) {
            
            echo "Error en la ejecucion de la consulta. <br />";
            echo "error : " . mysqli_stmt_error($query);
            $this->closeFreeStatement($query);
            exit();

        } else {

            $respuestas = null;
            while (mysqli_stmt_fetch($query)) {
                $respuestas[] = array('idRespuesta' => $idRespuesta,
                    'tit_Respuesta' => $tit_Respuesta,
                    'correcta' => $correcta,
                    'pregunta_idPregunta' => $pregunta_idPregunta,
                    'contador' => $contador);
            }
            $this->closeFreeStatement($query);

            return $respuestas;


        }
    }

//TESTED
    /**
     * Devuelve las respuestas de las preguntas que recibe por parámetro.
     * @param $idPreg . array con las preguntas.
     * @return array . Respuestas, o null si hay error.
     */
    function get_infoRespuestas($idPreg)
    {

        $this->conectar();
        $query = $this->consultaPreparada("SELECT  idRespuesta ,  titulo , correcta, contador, pregunta_idPregunta
                                           FROM respuesta
                                           WHERE  pregunta_idPregunta =?;");

        $ok = mysqli_stmt_bind_param($query, 'i', $idPregunta);
        $idPregunta = $idPreg;

        $ok = mysqli_stmt_execute($query);
        $ok = mysqli_stmt_bind_result($query, $idRespuesta, $titulo, $correcta, $contador, $pregunta_idPregunta);

        if ($ok == FALSE) {

            echo "Error en la ejecucion de la consulta. \n";
            echo "error : " . mysqli_stmt_error($query);
            $this->closeFreeStatement($query);
            exit();

        } else {

            while (mysqli_stmt_fetch($query)) {

                $respuestas = array('idRespuesta' => $idRespuesta,
                    'titulo' => $titulo,
                    'correcta' => $correcta,
                    'contador' => $contador,
                    'pregunta_idPregunta' => $pregunta_idPregunta,);
            }
            $this->closeFreeStatement($query);
            return $respuestas;

        }

    }

    //TESTED
    /**
     * Incrementa el contador, cuando el alumno guarda la respuesta.
     * @param $idRes . array con las preguntas.
     * @return bool. True o False, si se incrementa o no.
     */
    function incContRespu($idRes)
    {
        $this->conectar();
        $query = $this->consultaPreparada(" UPDATE Respuesta
		 									  SET contador = contador +1
		 									  WHERE  idRespuesta= ?	");

        $ok = mysqli_stmt_bind_param($query, 'i', $idRespuesta);
        $idRespuesta = $idRes;

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
     * Devuelve cuántos han respondido a esa pregunta.
     * @param $idPreg . idPregunta.
     * @return mixed. Cuántos. False si hay error
     */
    function cuantosHanRespondido($idPreg)
    {

        $this->conectar();
        $query = $this->consultaPreparada(" SELECT sum(`contador`)
                                            FROM respuesta
                                            where Pregunta_idPregunta = ?  ");

        $ok = mysqli_stmt_bind_param($query, 'i', $pregunta_idPregunta);
        $pregunta_idPregunta = $idPreg;

        $ok = mysqli_stmt_execute($query);
        $ok = mysqli_stmt_bind_result($query, $cuantos);

        if ($ok == FALSE) {

            echo "Error en la ejecucion de la consulta. <br />";
            echo "error : " . mysqli_stmt_error($query);
            $this->closeFreeStatement($query);
            exit();

        } else {

            mysqli_stmt_fetch($query);
            $this->closeFreeStatement($query);
            return $cuantos;
        }

    }

    //TESTED
    /**
     * Devuelve si una respuesta es correcta o no.
     * @param $idRes . idRespuesta.
     * @return mixed. Correcta o no. False si hay error
     */
    function isCorrect($idRes)
    {

        $this->conectar();
        $query = $this->consultaPreparada("SELECT correcta
											FROM respuesta
											WHERE idRespuesta = ? ");

        $ok = mysqli_stmt_bind_param($query, 'i', $idRespuesta);
        $idRespuesta = $idRes;

        $ok = mysqli_stmt_execute($query);
        $ok = mysqli_stmt_bind_result($query, $correcta);

        if ($ok == FALSE) {

            echo "Error en la ejecucion de la consulta. <br />";
            echo "error : " . mysqli_stmt_error($query);
            $this->closeFreeStatement($query);
            exit();

        } else {

            mysqli_stmt_fetch($query);
            $this->closeFreeStatement($query);

            return $correcta;

        }
    }

    //TESTED
    /**
     * @param $idPreg . idPregunta
     * @param $tit . título.
     * @param $corr . correcta o no.
     * @return mixed. id nueva respuesta. False si hay error.
     */
    function createRespuestas($idPreg, $tit, $corr)
    {

        $link = $this->conectar();
        $query = $this->consultaPreparada("INSERT INTO respuesta
                                            (titulo, correcta, contador, pregunta_idPregunta)
                                            VALUES (?, ?, ?, ? ); ");

        $ok = mysqli_stmt_bind_param($query, 'siii', $titulo, $correcta, $contador, $pregunta_idPregunta);
        $titulo = $tit;

        $correcta = $corr;
        $contador = 0;
        $pregunta_idPregunta = $idPreg;

        $ok = mysqli_stmt_execute($query);
        $query2 = $this->consultaPreparada("SELECT LAST_INSERT_ID(); ");
        $ok = mysqli_stmt_execute($query2);
        $ok = mysqli_stmt_bind_result($query2, $idRespuesta);

        if ($ok == FALSE) {

            echo "Error en la ejecucion de la consulta. <br />";
            echo "error : " . mysqli_stmt_error($query);
            $this->closeFreeStatement($query);
            exit();

        } else {

            mysqli_stmt_fetch($query2);
            return $idRespuesta;

        }
    }

}

?>