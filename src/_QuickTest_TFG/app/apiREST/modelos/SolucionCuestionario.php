<?php

/**
 * Created by PhpStorm.
 * User: Daniel Puente Gabarri
 * Date: 10/03/2017
 * Time: 18:17
 */

if (!empty($_SERVER["DOCUMENT_ROOT"]))
    $URL_GLOBAL = $_SERVER["DOCUMENT_ROOT"];

require_once($URL_GLOBAL . '/_QuickTest_TFG/app/model/Alumno_has_cuestionario_Model.php');
require_once($URL_GLOBAL . '/_QuickTest_TFG/app/model/Alumno_has_respuesta_Model.php');
require_once($URL_GLOBAL . '/_QuickTest_TFG/app/controller/Cuestionario_Resolver_Controller.php');
require_once($URL_GLOBAL . '/_QuickTest_TFG/app/model/Preguntas_Model.php');
require_once($URL_GLOBAL . '/_QuickTest_TFG/app/model/Respuestas_Model.php');
require_once($URL_GLOBAL . '/_QuickTest_TFG/app/controller/LTI_Controller.php');
//Importamos la librería de seguridad OAuth
require_once($URL_GLOBAL . "/_QuickTest_TFG/lib/ims-blti/OAuthBody.php");
require_once($URL_GLOBAL . '/_QuickTest_TFG/app/apiREST/test_apiRest/TablaNota.php');


/**
 *
 * Clase SolucionCuestionario encargada finalizar un cuestionario, insertar la nota y recuperarla.
 *
 * @autor Daniel Puente Gabarri.
 *
 */
class SolucionCuestionario
{

    /**
     * Constantes de estado para los mensajes de salida
     */
    const ESTADO_EXITO = 1;
    const ESTADO_NO_ENCONTRADO = 2;
    const ESTADO_CUESTIONARIO_FINALIZADA = 3;
    const ESTADO_ERROR_PARAMETROS = 4;
    const ESTADO_SOY_PROFESOR = true;

    /**
     * Método encargado de tratar la lógica de un petición GET.
     * @param $peticion , peticion.
     * @return array, resto de campos que forman la peticion.
     * @throws APIException.
     */
    public static function get($peticion)
    {
        if ($peticion[0] == 'obtenerNota') {
            return self::getGrade($peticion[1], $peticion[2]);
        } else {
            throw new APIException(self::ESTADO_ERROR_PARAMETROS,
                "Error al obtener Nota " . "<class> " . SolucionCuestionario::class . " </class>",
                APIEstados::ESTADO_UNPROCESSABLE_ENTITY);
        }

    }

    /**
     * Método encargado de tratar la lógica de un petición POST.
     * @param $peticion , peticion.
     * @return array, resto de campos que forman la peticion.
     * @throws APIException
     */
    public static function post($peticion)
    {
        // Si el alumno decide finalizar el cuestionario
        if ($peticion[0] == 'finalizar') {
            return self::finalizarCuestionario();

        } else {
            throw new APIException(self::ESTADO_ERROR_PARAMETROS,
                "Error al finalizar un cuestionario " . "<class> " . SolucionCuestionario::class . " </class>",
                APIEstados::ESTADO_UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * Metodo ecnargado de finalizar un cuestionario almacenando las respuestas.
     * @return array, respuesta a la peticion.
     * @throws APIException
     */
    private
    static function finalizarCuestionario()
    {
        // Obtenemos la informacion necesaria sobre el cuestionario
        $body = file_get_contents('php://input');
        $cuestionario = json_decode($body, true);

        $cuestionarioResolverController = new Cuestionario_Resolver_Controller();

        $idCuestionario = $cuestionario['idCuestionario'];

        // Obtenemos el id del usuario
        $idAlumno = $cuestionario['idAlumno'];
        $nombreAlu = $cuestionario['nombreAlu'];
        $apeAlu = $cuestionario['apeAlu'];

        // Registramos al usuario que acaba de finalizar el cuestionario

        $cuestionarioResolverController->registrarAlumno($idAlumno, $nombreAlu, $apeAlu);

        // Obtenemos las respuestas
        $respuestas = $cuestionario['respuestas'];

        foreach ($respuestas as $r) {
            $pregResuelta = $r['idPregunta'];
            $idRespuesta = $r['idRespuesta'];
            $tipoComUsado = $r['tipoComUsado'];
            $idAlumno = $r['idAlumno'];

            $cuestionarioResolverController->guardarCadaRespuesta($idRespuesta, $tipoComUsado, $idAlumno, $pregResuelta);
        }


        // Calculamos nota y insertamos al alumno en que a realizado el cuestionario
        $retorno = self::calcularNotaMoodle($idCuestionario, $idAlumno);

        if ($retorno >= 0) {
            // Incluimos la nota
            if (self::insertarNota($idAlumno, $idCuestionario, $retorno)) {
                // Establecemos la respuesta indicando que se creo un recurso
                http_response_code(APIEstados::ESTADO_OK);
                return
                    [
                        "estado" => self::ESTADO_EXITO,
                        "mensaje" => $retorno
                    ];
            } else {
                throw new APIException(self::ESTADO_ERROR_PARAMETROS,
                    "Error al insertar un nota " . "<class> " . SolucionCuestionario::class . " </class>",
                    APIEstados::ESTADO_UNPROCESSABLE_ENTITY);
            }


        } else {
            throw new APIException(self::ESTADO_ERROR_PARAMETROS,
                "Error al finalizar un cuestionario " . "<class> " . SolucionCuestionario::class . " </class>",
                APIEstados::ESTADO_UNPROCESSABLE_ENTITY);
        }

    }

    /**
     * Metodo encargado de calcular la nota de un cuestionario en escala 0-10.
     * @param $idCuestionario , identificador del cuestionario.
     * @param $idAlumno , identificador del alumno.
     * @return float|int, nota.
     */
    private
    static function calcularNotaMoodle($idCuestionario, $idAlumno)
    {

        $preguntasModel = new Preguntas_Model();
        $alumno_has_cuestionario = new Alumno_has_cuestionario_Model();
        $cuestionario_Controller = new Cuestionario_Resolver_Controller();
        $alumno_has_respuestaModel = new Alumno_has_respuesta_Model();

        //Miramos en que orden ha contestado (los primeros, los del medio, los ultimos)
        $orden = $cuestionario_Controller->calculaOrdenPrimeraVez($idCuestionario);

        /*// obtenemos el idAlumno
        list($ini, $fin) = split(':', $idAlumno);*/


        //Se registra que el alumno ha finalizado ese cuestionario.
        $alumno_has_cuestionario->insertarAlumno_Cuestionario($idAlumno, $idCuestionario, $orden);
        $calificObtenida = $alumno_has_respuestaModel->getSumaPuntTotal_Alumno($idCuestionario, $idAlumno);
        $calificMaxPosible = $preguntasModel->getPuntMaxima_Cuestionario($idCuestionario);

        //Se calcula una bonificación en función de cuando se ha resuelto el cuestionario.

        if ($orden == 1) { // se bonifica con 1 punto a los primeros en responder

            $premio = $calificMaxPosible / 10;

        } else if ($orden == 2) {// se bonifica con 1/2 punto al grupo central

            $premio = ($calificMaxPosible / 10) / 2;

        } else if ($orden == 3) { // se PENALIZA con -1/2 punto al grupo final

            $premio = -(($calificMaxPosible / 10) / 2);

        }

        //se añade la bonificación, y se convierte a formato moodle.
        $grade_moodle_format = ($calificObtenida + $premio) / $calificMaxPosible;

        if ($grade_moodle_format < 0) {
            $grade_moodle_format = 0; // moodle no permite calificaciones negativas
        }

        if ($grade_moodle_format > 1) {
            $grade_moodle_format = 1; // moodle no permite calificaciones mayores que 1
        }

        //$retorno = self::enviarNotaAMoodle($grade_moodle_format, $context);

        return $grade_moodle_format;

    }

    /**
     * Metodo encargado de insertar la nota de un cuestionario resuelto desde
     * la app Android en QuickTest.
     * @param $idAlumno , identificador del alumno.
     * @param $idCuestionario , identificador del cuestionario.
     * @param $nota , nota
     * @return bool, respuesta.
     */
    public
    static function insertarNota($idAlumno, $idCuestionario, $nota)
    {
        $db = new Database();
        $db->conectar();

        $query = $db->consultaPreparada("INSERT INTO " . TablaNota::NOMBRE_TABLA .
            " (" . TablaNota::ID_ALUMNO . " , " . TablaNota::ID_CUESTIONARIO . " , " .
            TablaNota::NOTA . " ) VALUES (?, ?, ?)");


        $ok = mysqli_stmt_bind_param($query, 'ssd', $idAlumno, $idCuestionario, $nota);
        mysqli_stmt_execute($query);
        $db->closeFreeStatement($query);

        if ($ok) {
            return true;
        } else {
            return false;
        }


    }

    /**
     * Metodo encargado de obtener la nota de un cuestionario resuelto desde la App Android.
     * @param $idAlumno , identificador del alumno.
     * @param $idCuestionario , identificador del cuestionario.
     * @return array, respuesta.
     */
    private static function getGrade($idAlumno, $idCuestionario)
    {
        $retorno = self::obtenerNota($idAlumno, $idCuestionario);
        if ($retorno == null) {
            $retorno = -1;
        }
        // Establecemos la respuesta
        http_response_code(APIEstados::ESTADO_OK);
        // Retornamos la informacion
        return
            [
                "estado" => self::ESTADO_EXITO,
                "mensaje" => $retorno

            ];

    }

    /**
     * Metodo encargado de obtener la nota de un cuestionario en la base de datos.
     * @param $idAlumno , identificador del usuario.
     * @param $idCuestionario , identificador del cuestionario.
     * @return int, calificacion.
     */
    public static function obtenerNota($idAlumno, $idCuestionario)
    {
        $db = new Database();
        $db->conectar();
        $query = $db->consultaPreparada("SELECT " . TablaNota::NOTA . " FROM "
            . TablaNota::NOMBRE_TABLA . " WHERE "
            . TablaNota::ID_ALUMNO . "=?" . " AND "
            . TablaNota::ID_CUESTIONARIO . "=?");


        $ok = mysqli_stmt_bind_param($query, 'si', $idAlumno, $idCuestionario);
        mysqli_stmt_execute($query);
        $ok = mysqli_stmt_bind_result($query, $grade);


        if ($ok) {
            mysqli_stmt_fetch($query);
            $db->closeFreeStatement($query);
            return $grade;
        } else {
            return -1;
        }
    }


}