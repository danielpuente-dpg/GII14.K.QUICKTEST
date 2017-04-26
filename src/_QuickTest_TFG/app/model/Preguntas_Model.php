<?php
require_once "Database.class.php";

/**
 * @author Alejandro Martínez García <amg0098@alu.ubu.es>
 * @version 1.0.
 */

/**
 * Class Preguntas_Model.
 * Implementa el modelo.
 * Envía datos de la BD al controlador, para que éste los envíe a la vista.
 * Extiende de la clase Database.
 */
class Preguntas_Model extends Database
{

//TESTED
    /**
     * Selecciona las preguntas de un cuestionario.
     * @param $idCuest. idCuestionario.
     * @return array. Preguntas.
     */
    function getIDPreguntaTitulo_Cuestionario($idCuest)
    {

        $this->conectar();
        $query = $this->consultaPreparada("SELECT pregunta.idPregunta, pregunta.titulo, max_puntuacion
									         FROM cuestionario
										     JOIN pregunta ON (pregunta.Cuestionario_idCuestionario = cuestionario.idCuestionario)
											 WHERE cuestionario.idCuestionario = ?;");

        $ok = mysqli_stmt_bind_param($query, 'i', $idCuestionario);
        $idCuestionario = $idCuest;

        $ok = mysqli_stmt_execute($query);
        $ok = mysqli_stmt_bind_result($query, $idPregunta, $titulo, $max_puntuacion);

        if ($ok == FALSE) {

            echo "Error en la ejecucion de la consulta. <br />";
            echo "error : " . mysqli_stmt_error($query);
            $this->closeFreeStatement($query);
            exit();

        } else {

            $preguntas = array();
            while (mysqli_stmt_fetch($query)) {
                //echo "ENTRE";

                $preguntas[] = array(
                    'idPregunta' => $idPregunta,
                    'tituloPreg' => $titulo,
                    'max_puntuacion'=>$max_puntuacion
                );


            }

            $this->closeFreeStatement($query);
            return $preguntas;

        }

    }

//TESTED
    /**
     * Selecciona las preguntas de un cuestionario.
     * @param $idCuest. idCuestionario.
     * @return array. Preguntas.
     */
    function get_infoPreguntas($idCuest)
    {

        $this->conectar();
        $query = $this->consultaPreparada("SELECT  idPregunta ,  pregunta.titulo ,  max_puntuacion ,  feedBack ,  cuestionario_idCuestionario
                                           FROM pregunta
                                           WHERE  cuestionario_idCuestionario =?;");

        $ok = mysqli_stmt_bind_param($query, 'i', $idCuestionario);
        $idCuestionario = $idCuest;

        $ok = mysqli_stmt_execute($query);
        $ok = mysqli_stmt_bind_result($query, $idPregunta, $titulo, $max_puntuacion, $feedBack, $cuestionario_idCuestionario);

        if ($ok == FALSE) {

            echo "Error en la ejecucion de la consulta. <br />";
            echo "error : " . mysqli_stmt_error($query);
            $this->closeFreeStatement($query);
            exit();

        } else {

            $preguntas = array();

            while (mysqli_stmt_fetch($query)) {

                $preguntas[] = array('idPregunta' => $idPregunta,
                    'titulo' => $titulo,
                    'max_puntuacion' => $max_puntuacion,
                    'feedBack' => $feedBack,
                    'cuestionario_idCuestionario' => $cuestionario_idCuestionario);
            }
            $this->closeFreeStatement($query);
            return $preguntas;

        }

    }

//TESTED
     /**
     * Cuenta cuantas preguntas tiene el cuestionario.
     * @param $idCuest. idCuestionario.
     * @return mixed. Cuantas, o false si hay error.
     */
    function countPreguntasCuestionario ($idCuest){

        $this->conectar();
        $query = $this->consultaPreparada("SELECT count(idPregunta)
                                            from pregunta
                                            where cuestionario_idCuestionario=? ;");

        $ok = mysqli_stmt_bind_param($query, 'i', $idCuestionario);
        $idCuestionario = $idCuest;

        $ok = mysqli_stmt_execute($query);
        $ok = mysqli_stmt_bind_result($query, $countPreguntas);

        if ($ok == FALSE) {

            echo "Error en la ejecucion de la consulta. <br />";
            echo "error : " . mysqli_stmt_error($query);
            $this->closeFreeStatement($query);
            exit();

        } else {

            mysqli_stmt_fetch($query);
            $this->closeFreeStatement($query);
            return $countPreguntas;

        }

    }

//TESTED
    /**
     * Crea una nueva pregunta.
     * @param $tit. titutlo
     * @param $idCuest. idCuestionario.
     * @param $pmax. puntación máxima
     * @return mixed. id nueva pregunta, o false si hay error.
     */
    function createPregunta($tit, $idCuest, $pmax)
    {

        $link = $this->conectar();
        $query = $this->consultaPreparada("INSERT INTO pregunta
                                          (titulo,  cuestionario_idCuestionario, max_puntuacion) VALUES
                                           (?, ?, ?) ; ");


        $ok = mysqli_stmt_bind_param($query, 'sii', $titulo, $idCuestionario, $max_puntuacion);

        $titulo = $tit;
        $idCuestionario = $idCuest;
        $max_puntuacion = $pmax;

        $ok = mysqli_stmt_execute($query);

        $query2 = $this->consultaPreparada("SELECT LAST_INSERT_ID(); ");

        $ok = mysqli_stmt_execute($query2);
        $ok = mysqli_stmt_bind_result($query2, $idPregunta);

        if ($ok == FALSE) {

            echo "Error en la ejecucion de la consulta. <br />";
            echo "error : " . mysqli_stmt_error($query);
            $this->closeFreeStatement($query);
            exit();

        } else {

            mysqli_stmt_fetch($query2);
            $this->closeFreeStatement($query);
            return $idPregunta;

        }

    }

    //TESTED
    /**
     * Devuelve la puntuación máxima de una pregunta.
     * @param $idPreg. id Pregunta.
     * @return mixed. id nueva pregunta, o false si hay error.
     */
    function get_maxPunt_pregunta($idPreg)
    {
        $this->conectar();
        $query = $this->consultaPreparada("SELECT max_puntuacion
									         FROM pregunta
											 WHERE idPregunta = ?;");

        $ok = mysqli_stmt_bind_param($query, 'i', $idPregunta);
        $idPregunta = $idPreg;

        $ok = mysqli_stmt_execute($query);
        $ok = mysqli_stmt_bind_result($query, $max_puntuacion);

        if ($ok == FALSE) {

            echo "Error en la ejecucion de la consulta. <br />";
            echo "error : " . mysqli_stmt_error($query);
            $this->closeFreeStatement($query);
            exit();

        } else {

            mysqli_stmt_fetch($query);
            $this->closeFreeStatement($query);
            return $max_puntuacion;

        }

    }

    //TESTED
    /**
     * Devuelve la puntuación máxima de un cuestionario.
     * @param $idCuest. id cuestionario.
     * @return mixed. id nueva pregunta, o false si hay error.
     */
    function getPuntMaxima_Cuestionario($idCuest)
    {

        $this->conectar();
        $query = $this->consultaPreparada("SELECT sum(`max_puntuacion`)
                                                FROM pregunta
                                                JOIN cuestionario ON (pregunta.cuestionario_idCuestionario = cuestionario.idCuestionario)
                                                WHERE idCuestionario = ? ");

        $ok = mysqli_stmt_bind_param($query, 'i', $idCuestionario);

        $idCuestionario = $idCuest;

        $ok = mysqli_stmt_execute($query);
        $ok = mysqli_stmt_bind_result($query, $puntuacionMax_Cuest);

        if ($ok == FALSE) {

            echo "Error en la ejecucion de la consulta. <br />";
            echo "error : " . mysqli_stmt_error($query);
            $this->closeFreeStatement($query);
            exit();

        } else {

            mysqli_stmt_fetch($query);
            $this->closeFreeStatement($query);
            return $puntuacionMax_Cuest;

        }


    }


//TESTED
    /**
     * Devuelve la pregunta, a la que pertenece una respuesta.
     * @param $idRes. idRespuesta
     * @return mixed. id pregunta, o false si hay error.
     */
    function getIdPregunta_respuesta($idRes)
    {
        $this->conectar();
        $query = $this->consultaPreparada("SELECT idPregunta
                                                FROM pregunta
                                                 JOIN respuesta ON (pregunta.idPregunta = respuesta.pregunta_idPregunta)
                                                WHERE idRespuesta=?; ");

        $ok = mysqli_stmt_bind_param($query, 'i', $idRespuesta);
        $idRespuesta = $idRes;

        $ok = mysqli_stmt_execute($query);
        $ok = mysqli_stmt_bind_result($query, $idPregunta);

        if ($ok == FALSE) {

            echo "Error en la ejecucion de la consulta. <br />";
            echo "error : " . mysqli_stmt_error($query);
            $this->closeFreeStatement($query);
            exit();

        } else {

            mysqli_stmt_fetch($query);
            $this->closeFreeStatement($query);
            return $idPregunta;


        }

    }

}

?>