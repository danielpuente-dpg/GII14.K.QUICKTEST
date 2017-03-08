<?php
require_once "Database.class.php";

/**
 * @author Alejandro Martínez García <amg0098@alu.ubu.es>
 * @version 1.0.
 */

/**
 * Class Cuestionario_Model.
 * Implementa el modelo.
 * Envía datos de la BD al controlador, para que éste los envíe a la vista.
 * Extiende de la clase Database.
 */
class Cuestionario_Model extends Database
{

    //TESTED
    /**
     * Devuelve la información de un cuestionario.
     * @param $idCuest . id del cuestionario.
     * @return array. Datos del cuestionario.
     */
    function getInfoCuestionarios($idCuest)
    {
        $this->conectar();
        $query = $this->consultaPreparada("SELECT idCuestionario, titulo ,  duracion ,  contadorAlumnos ,  asignatura_idAsignatura ,
                                            asignatura_nombreAsignatura ,  correctorVerdeTrue ,  correctorVerdeFalse ,
                                             correctorAmarilloTrue ,  correctorAmarilloFalse
                                           FROM cuestionario
                                           where idCuestionario= ?    ");

        $ok = mysqli_stmt_bind_param($query, 'i', $idCuestionario);
        $idCuestionario = $idCuest;

        $ok = mysqli_stmt_execute($query);
        $ok = mysqli_stmt_bind_result($query, $idCuestionario, $titulo, $duracion, $contadorAlumnos,
            $idAsignatura, $nombreAsignatura,
            $correctorVerdeTrue, $correctorVerdeFalse, $correctorAmarilloTrue, $correctorAmarilloFalse);

        if ($ok == FALSE) {

            echo "Error en la ejecucion de la consulta. <br />";
            echo "error : " . mysqli_stmt_error($query);
            $this->closeFreeStatement($query);
            exit();

        } else {

            while (mysqli_stmt_fetch($query)) {

                $cuestionario = array(
                    'idCuestionario' => $idCuestionario,
                    'titulo' => $titulo,
                    'duracion' => $duracion,
                    'contadorAlumnos' => $contadorAlumnos,
                    'idAsignatura' => $idAsignatura,
                    'nombreAsignatura' => $nombreAsignatura,
                    'correctorVerdeTrue' => $correctorVerdeTrue,
                    'correctorVerdeFalse' => $correctorVerdeFalse,
                    'correctorAmarilloTrue' => $correctorAmarilloTrue,
                    'correctorAmarilloFalse' => $correctorAmarilloFalse
                );
            }

            $this->closeFreeStatement($query);
            return $cuestionario;

        }

    }

    //TESTED
    /**
     * Devuelve cuántos alumnos hay registrados.
     * @param $idCuest . id del cuestionario.
     * @return mixed.Cuantos. False si hay error.
     */
    function getAlumnosConectados($idCuest)
    {

        $this->conectar();
        $query = $this->consultaPreparada("SELECT contadorAlumnos
											 FROM cuestionario
		 									 WHERE idCuestionario = ? ");

        $ok = mysqli_stmt_bind_param($query, 'i', $idCuestionario);
        $idCuestionario = $idCuest;

        $ok = mysqli_stmt_execute($query);
        $ok = mysqli_stmt_bind_result($query, $contadorAlumnos);

        if ($ok == FALSE) {

            echo "Error en la ejecucion de la consulta. <br />";
            echo "error : " . mysqli_stmt_error($query);
            $this->closeFreeStatement($query);
            exit();

        } else {

            while (mysqli_stmt_fetch($query)) {
                $contadorAlumnos = $contadorAlumnos;
            }

            $this->closeFreeStatement($query);
            return $contadorAlumnos;

        }

    }

//TESTED
    /**
     * Incrementa el contador de alumnos  registrados.
     * @param $idCuest . id del cuestionario.
     * @return bool. True o False si hay error.
     */
    function incAlumnosConectados($idCuest)
    {

        $this->conectar();
        $query = $this->consultaPreparada("UPDATE cuestionario
											 SET  contadorAlumnos = contadorAlumnos + 1
											 WHERE idCuestionario = ?
		 									 ");

        $ok = mysqli_stmt_bind_param($query, 'i', $idCuestionario);
        $idCuestionario = $idCuest;

        $ok = mysqli_stmt_execute($query);

        if ($ok == FALSE) {

            echo "Error en la ejecucion de la consulta. <br />";
            echo "error : " . mysqli_stmt_error($query);
            $this->closeFreeStatement($query);
            exit();

        } else {

            $this->closeFreeStatement($query);
            return true;

        }

    }

    //TESTED
    /**
     * Crea un nuevo cuestionario.
     * @param $cuestTit . título.
     * @param $idAsig . id asignatura.
     * @param $nombreAsig . nombre asignatura.
     * @param $corrVerdT . Valor del corrector comodín verde. Cuando es correcta.
     * @param $corrVerdF . Valor del corrector comodín verde. Cuando es incorrecta.
     * @param $corrAmT . Valor del corrector comodín ámbar. Cuando es correcta.
     * @param $corrAmF Valor del corrector comodín ámbar. Cuando es incorrecta.
     * @return mixed. id del cuestionario creado.  False si hay error.
     */
    function createCuestionario($cuestTit, $idAsig, $nombreAsig, $corrVerdT, $corrVerdF, $corrAmT, $corrAmF)
    {

        $corrVerdT = (double)$corrVerdT;
        $corrVerdF = (double)$corrVerdF;
        $corrAmT = (double)$corrAmT;
        $corrAmF = (double)$corrAmF;

        $link = $this->conectar();
        $query = $this->consultaPreparada("INSERT INTO  cuestionario
                                            ( titulo, contadorAlumnos, asignatura_idAsignatura, asignatura_nombreAsignatura,
                                            correctorVerdeTrue, correctorVerdeFalse,
                                            correctorAmarilloTrue,correctorAmarilloFalse)
                                             VALUES (? , 0, ?, ? ,? ,? ,? , ?); ");


        $ok = mysqli_stmt_bind_param($query, 'sisdddd',
            $titulo, $idAsignatura, $nombreAsignatura,
            $correctorVerdeTrue, $correctorVerdeFalse,
            $correctorAmarilloTrue, $correctorAmarilloFalse);


        $titulo = $cuestTit;
        $idAsignatura = $idAsig;
        $nombreAsignatura = $nombreAsig;
        $correctorVerdeTrue = (double)$corrVerdT;
        $correctorVerdeFalse = (double)$corrVerdF;
        $correctorAmarilloTrue = (double)$corrAmT;
        $correctorAmarilloFalse = (double)$corrAmF;

        $ok = mysqli_stmt_execute($query);
        $query2 = $this->consultaPreparada("SELECT LAST_INSERT_ID(); ");

        $ok = mysqli_stmt_execute($query2);
        $ok = mysqli_stmt_bind_result($query2, $idCuestionario);

        if ($ok == FALSE) {

            echo "Error en la ejecucion de la consulta. <br />";
            echo "error : " . mysqli_stmt_error($query);
            $this->closeFreeStatement($query);
            exit();

        } else {

            mysqli_stmt_fetch($query2);
            $this->closeFreeStatement($query);
            return $idCuestionario;

        }

    }

    //TESTED
    /**
     * Edita un cuestionario ya existente.
     * @param $idCuest . id del cuestionario a editar.
     * @param $cuestTit . título.
     * @param $idAsig . id asignatura.
     * @param $nombreAsig . nombre asignatura.
     * @param $corrVerdT . Valor del corrector comodín verde. Cuando es correcta.
     * @param $corrVerdF . Valor del corrector comodín verde. Cuando es incorrecta.
     * @param $corrAmT . Valor del corrector comodín ámbar. Cuando es correcta.
     * @param $corrAmF. Valor del corrector comodín ámbar. Cuando es incorrecta.
     * @return bool. True o False, si se ha creado o no.
     */
    function createCuestionarioEditado($idCuest, $cuestTit, $idAsig, $nombreAsig, $corrVerdT, $corrVerdF, $corrAmT, $corrAmF)
    {

        $corrVerdT = (double)$corrVerdT;
        $corrVerdF = (double)$corrVerdF;
        $corrAmT = (double)$corrAmT;
        $corrAmF = (double)$corrAmF;

        $link = $this->conectar();
        $query = $this->consultaPreparada("INSERT INTO  cuestionario
                                            (idCuestionario, titulo, contadorAlumnos, asignatura_idAsignatura, asignatura_nombreAsignatura,
                                            correctorVerdeTrue, correctorVerdeFalse,
                                            correctorAmarilloTrue,correctorAmarilloFalse)
                                             VALUES (?, ? , 0, ?, ? ,? ,? ,? , ?); ");

        $ok = mysqli_stmt_bind_param($query, 'isisdddd',
            $idCuestionario, $titulo, $idAsignatura, $nombreAsignatura,
            $correctorVerdeTrue, $correctorVerdeFalse,
            $correctorAmarilloTrue, $correctorAmarilloFalse);

        $idCuestionario = $idCuest;
        $titulo = $cuestTit;
        $idAsignatura = $idAsig;
        $nombreAsignatura = $nombreAsig;
        $correctorVerdeTrue = (double)$corrVerdT;
        $correctorVerdeFalse = (double)$corrVerdF;
        $correctorAmarilloTrue = (double)$corrAmT;
        $correctorAmarilloFalse = (double)$corrAmF;

        $ok = mysqli_stmt_execute($query);

        if ($ok == FALSE) {

            echo "Error en la ejecucion de la consulta. <br />";
            echo "error : " . mysqli_stmt_error($query);
            $this->closeFreeStatement($query);
            exit();

        } else {

            $this->closeFreeStatement($query);
            return true;

        }

    }

    //TESTED
    /**
     * Devuelve los cuestionarios, de una determinada asignatura.
     * @param $idAsig. id asignatura.
     * @return array. Array con los cuestionarios.
     */
    function getCuestionariosAsignatura($idAsig)
    {
        $this->conectar();
        $query = $this->consultaPreparada("SELECT idCuestionario, titulo
                                           FROM cuestionario
                                           where asignatura_idAsignatura= ?    ");

        $ok = mysqli_stmt_bind_param($query, 'i', $asignatura_idAsignatura);

        $asignatura_idAsignatura = $idAsig;

        $ok = mysqli_stmt_execute($query);
        $ok = mysqli_stmt_bind_result($query, $idCuestionario, $tituloCuest);

        if ($ok == FALSE) {

            echo "Error en la ejecucion de la consulta. <br />";
            echo "error : " . mysqli_stmt_error($query);
            $this->closeFreeStatement($query);
            exit();

        } else {

            while (mysqli_stmt_fetch($query)) {
                $tituloCuestionarios[] = array('idCuestionario' => $idCuestionario, 'nombreCuestionario' => $tituloCuest);
            }


            $this->closeFreeStatement($query);

            return $tituloCuestionarios;

        }

    }

    //TESTED
    /**
     * Devuelve los valores de los correctores que tiene el cuestionario.
     * @param $idCuest. id del cuestionario.
     * @return array|null. Valores o null.
     */
    function getCorrectores($idCuest)
    {
        $this->conectar();

        $query = $this->consultaPreparada("SELECT correctorVerdeTrue, correctorVerdeFalse,
                                                  correctorAmarilloTrue,correctorAmarilloFalse
                                           FROM cuestionario
                                           where idCuestionario= ?    ");

        $ok = mysqli_stmt_bind_param($query, 'i', $idCuestionario);

        $idCuestionario = $idCuest;

        $ok = mysqli_stmt_execute($query);
        $ok = mysqli_stmt_bind_result($query, $correctorVerdeTrue, $correctorVerdeFalse, $correctorAmarilloTrue, $correctorAmarilloFalse);

        $correctores = null;

        if ($ok == FALSE) {

            echo "Error en la ejecucion de la consulta. <br />";
            echo "error : " . mysqli_stmt_error($query);
            $this->closeFreeStatement($query);
            exit();

        } else {

            while (mysqli_stmt_fetch($query)) {
                $correctores = array('correctorVerdeTrue' => $correctorVerdeTrue, 'correctorVerdeFalse' => $correctorVerdeFalse,
                    'correctorAmarilloTrue' => $correctorAmarilloTrue, 'correctorAmarilloFalse' => $correctorAmarilloFalse);
            }

            $this->closeFreeStatement($query);

            return $correctores;

        }


    }

    //TESTED
    /**
     * Borra un cuestionario.
     * @param $idCuest. id cuestionario.
     * @return mixed. idCuestionario borrado o False si hay error.
     */
    function borrarCuestionario($idCuest)
    {

        $this->conectar();
        $query = $this->consultaPreparada("DELETE  FROM cuestionario
                                            WHERE idCuestionario=? ");

        $ok = mysqli_stmt_bind_param($query, 'i', $idCuestionario);

        $idCuestionario = $idCuest;

        $ok = mysqli_stmt_execute($query);


        if ($ok == FALSE) {

            echo "Error en la ejecucion de la consulta. <br />";
            echo "error : " . mysqli_stmt_error($query);
            $this->closeFreeStatement($query);
            exit();

        } else {

            mysqli_stmt_fetch($query);
            $this->closeFreeStatement($query);
            return $idCuest;

        }

    }

    //TESTED
    /**
     * Realiza una copia de un cuestionario existente.
     * @param $idCuest. id cuestionario a duplicar.
     * @return mixed. idCuestionario duplicado o False si hay error
     */
    function duplicarCuestionario($idCuest)
    {

        $this->conectar();
        $query = $this->consultaPreparada("insert into cuestionario
                                           (  titulo ,  duracion ,  contadorAlumnos ,  asignatura_idAsignatura ,
                                            asignatura_nombreAsignatura ,  correctorVerdeTrue ,  correctorVerdeFalse ,
                                             correctorAmarilloTrue ,  correctorAmarilloFalse )
                                            select  concat(titulo,' (copia)') ,  duracion ,  0 ,  asignatura_idAsignatura ,
                                              asignatura_nombreAsignatura ,  correctorVerdeTrue ,  correctorVerdeFalse ,
                                               correctorAmarilloTrue ,  correctorAmarilloFalse 
                                               from cuestionario
                                               where  idCuestionario  = ?   ");

        $ok = mysqli_stmt_bind_param($query, 'i', $idCuestionario);
        $idCuestionario = $idCuest;

        $ok = mysqli_stmt_execute($query);

        if ($ok == FALSE) {
            echo "Error en la ejecucion de la consulta. <br />";
            echo "error : " . mysqli_stmt_error($query);
            $this->closeFreeStatement($query);
            exit();
        } else {

            $query2 = $this->consultaPreparada("SELECT LAST_INSERT_ID(); ");

            $ok = mysqli_stmt_execute($query2);
            $ok = mysqli_stmt_bind_result($query2, $idCuestionarioClonado);

            if ($ok == FALSE) {
                echo "Error en la ejecucion de la consulta. <br />";
                echo "error : " . mysqli_stmt_error($query);
                $this->closeFreeStatement($query);
                exit();
            } else {
                mysqli_stmt_fetch($query2);
                $this->closeFreeStatement($query);
                $this->closeFreeStatement($query2);

                return $idCuestionarioClonado;
            }

        }


    }

    //TESTED
    /**
     * Selecciona el cuestionario, al que pertence una respuesta.
     * @param $idRes. id respuesta.
     * @return mixed. idCuestionario o False si hay error.
     */
    function getIdCuest_respuesta($idRes)
    {

        $this->conectar();
        $query = $this->consultaPreparada("SELECT idCuestionario
                                                FROM cuestionario
                                                JOIN pregunta ON (cuestionario.idCuestionario = pregunta.cuestionario_idCuestionario)
                                                JOIN respuesta ON (pregunta.idPregunta = respuesta.pregunta_idPregunta)
                                                WHERE idRespuesta=?; ");

        $ok = mysqli_stmt_bind_param($query, 'i', $idRespuesta);
        $idRespuesta = $idRes;

        $ok = mysqli_stmt_execute($query);
        $ok = mysqli_stmt_bind_result($query, $idCuestionario);

        if ($ok == FALSE) {

            echo "Error en la ejecucion de la consulta. <br />";
            echo "error : " . mysqli_stmt_error($query);
            $this->closeFreeStatement($query);
            exit();

        } else {

            mysqli_stmt_fetch($query);
            $this->closeFreeStatement($query);
            return $idCuestionario;

        }
    }
}

?>