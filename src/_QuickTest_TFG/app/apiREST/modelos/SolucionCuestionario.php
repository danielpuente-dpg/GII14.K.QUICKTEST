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
 * Class Alumno
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

    public static function post($peticion)
    {
        // Si el alumno decide finalizar el cuestionario
        if ($peticion[0] == 'finalizar') {
            return self::finalizarCuestionario();

        } else if ($peticion[0] == 'mostrar') {
            return self::mostrarResultados();

        } else if ($peticion[0] == 'resolver') {
            return self::iniciarCuestionario();
        } else {
            throw new APIException(self::ESTADO_ERROR_PARAMETROS,
                "Error al finalizar un cuestionario " . "<class> " . SolucionCuestionario::class . " </class>",
                APIEstados::ESTADO_UNPROCESSABLE_ENTITY);
        }
    }

    private
    static function iniciarCuestionario()
    {
        $cuerpo = file_get_contents('php://input');
        $datos = json_decode($cuerpo, true);

        $idCuestionario = $datos['data-idCuestVisualizar'];
        $courseName = $datos['nombreAsig'];
        $activityName = $datos['nombreCuest'];
        $isProfesor = $datos['isProfesor'];
        $idUser = $datos['idUser'];
        $nombreAlu = $datos['nombreAlu'];
        $apeAlu = $datos['apeAlu'];
        $lang = $datos['lang'];
        $idAsig = $datos['idAsig'];
        $ltiConsumerURL = $datos['ltiConsumerURL'];

        $alumno_has_cuestionarioModel = new Alumno_has_cuestionario_Model();
        $cuestionarioResolverController = new Cuestionario_Resolver_Controller();
        $preguntasModel = new Preguntas_Model();
        $respuestasModel = new Respuestas_Model();
        $preguntasVerde = null;
        $preguntasAmarillo = null;

        // Comprobar si es profesor
        if ($isProfesor == 1) {
            // Lógica a seguir si eres profesor
            // Devolvemos la información
            http_response_code(APIEstados::ESTADO_OK);
            // Retornamos la informacion
            return
                [
                    "estado" => self::ESTADO_EXITO,
                    "mensaje" => self::ESTADO_SOY_PROFESOR,
                    "datos" => null
                ];

        } else {
            // Comprobar si ese cuestionario ha sido ya resuelto por ese alumno.
            // Si no esta resuelto, registramos la conexión

            if ($alumno_has_cuestionarioModel->estaResuelto($idUser, $idCuestionario) != 1) {
                // Comprobamos si existe un estado previo de ese cuestionario para dicho alumno
                $resultado = $cuestionarioResolverController->checkSegundoIntento($idUser, $idCuestionario);
                if ($resultado == NULL) {

                    // Registramos la conexión para incrementar el número de alumnos que resuelven
                    // el cuestionario
                    $cuestionarioResolverController->registrarConexion($idCuestionario);

                    // Registramos tambien al usuario
                    $cuestionarioResolverController->registrarAlumno($idUser, $nombreAlu, $apeAlu);

                    // Si es la primera vez que intenta resolver el cuestionario se le permite el
                    // uso de comodines
                    $preguntasVerde = $cuestionarioResolverController->hasComodinVerde($idCuestionario);
                    $preguntasAmarillo = $cuestionarioResolverController->hasComodinAmbar($idCuestionario);

                    // Obtenemos las preguntas para dicho Custionario
                    $preguntasModel->getIDPreguntaTitulo_Cuestionario($idCuestionario);
                    // Para cada pregunta obtenemos sus posibles respuestas
                    foreach ($preguntasModel as $p) {
                        $respuestasPregunta[] = $respuestasModel->getRespuestas($p);
                    }

                    // Sino, devuelve un array con las preguntas ya contestadas
                } else {

                    // Obtenemos las preguntas para dicho Custionario
                    $preguntasModel->getIDPreguntaTitulo_Cuestionario($idCuestionario);

                    // Para cada pregunta obtenemos sus posibles respuestas
                    foreach ($preguntasModel as $p) {
                        $respuestasPregunta[] = $respuestasModel->getRespuestas($p);
                    }

                }

            } else {
                throw new APIException(self::ESTADO_CUESTIONARIO_FINALIZADA,
                    "Error al iniciar un cuestionario " . "<class> " . SolucionCuestionario::class . " </class>
                    el cuestionario ya esta resuelto", APIEstados::ESTADO_UNPROCESSABLE_ENTITY);

            }

            $datos = array(
                "preguntas" => $preguntasModel,
                "respuestas" => $respuestasModel,
                "comodinVerde" => $preguntasVerde,
                "comodinAmarillo" => $preguntasAmarillo
            );

            // Devolvemos la información
            http_response_code(APIEstados::ESTADO_OK);
            // Retornamos la informacion
            return
                [
                    "estado" => self::ESTADO_EXITO,
                    "mensaje" => !self::ESTADO_SOY_PROFESOR,
                    "datos" => $datos
                ];
        }

    }

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

    public
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

    private
    static function mostrarResultados()
    {
        // Obtenemos la informacion necesaria sobre el cuestionario ya resuelto
        $body = file_get_contents('php://input');
        $cuestionario = json_decode($body, true);

        $idCuestionario = $cuestionario['idCuestionario'];
        $idAlu = $cuestionario['idAlu'];
        $idAsig = $cuestionario['idAsig'];

        $cuestionarioResolverController = new Cuestionario_Resolver_Controller();
        $datos = $cuestionarioResolverController->mostarResultados($idCuestionario, $idAlu, $idAsig);
        // Devolvemos la información
        http_response_code(APIEstados::ESTADO_OK);
        // Retornamos la informacion
        return
            [
                "estado" => self::ESTADO_EXITO,
                "mensaje" => $datos
            ];
    }

    private static function getGrade($idAlumno, $idCuestionario)
    {
        $retorno = self::obtenerNota($idAlumno, $idCuestionario);
        if($retorno == null){
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