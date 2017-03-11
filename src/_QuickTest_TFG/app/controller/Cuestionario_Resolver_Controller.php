<?php
require_once $_SERVER["DOCUMENT_ROOT"] . '/_QuickTest_TFG/app/model/Cuestionario_Model.php';
require_once $_SERVER["DOCUMENT_ROOT"] . '/_QuickTest_TFG/app/model/Preguntas_Model.php';
require_once $_SERVER["DOCUMENT_ROOT"] . '/_QuickTest_TFG/app/model/Respuestas_Model.php';
require_once $_SERVER["DOCUMENT_ROOT"] . '/_QuickTest_TFG/app/model/Alumno_Model.php';
require_once $_SERVER["DOCUMENT_ROOT"] . '/_QuickTest_TFG/app/model/Alumno_has_cuestionario_Model.php';
require_once $_SERVER["DOCUMENT_ROOT"] . '/_QuickTest_TFG/app/model/Alumno_has_respuesta_Model.php';
require_once $_SERVER["DOCUMENT_ROOT"] . '/_QuickTest_TFG/app/model/Usuarios_Model.php';
/**
 * @author Alejandro Martínez García <amg0098@alu.ubu.es>
 * @version 1.0.
 */

/**
 * Class Cuestionario_Resolver_Controller.
 * Implementa un controlador.
 * Se comunica con el modelo
 * y la vista para ofrecer una pantalla en la que resolver un cuestionario, o simular
 * que aspecto tendría antes de ser publicado.
 */
class Cuestionario_Resolver_Controller
{

    // @codeCoverageIgnoreStart
    /**
     * Función que proporciona a la vista, los datos necesarios para
     * crear una pantalla en la que resolver un cuestionario.
     * @param $idCuestionario . Identificador del cuestionario a resolver.
     * @param $courseName . Nombre que tiene la asignatura en el LTI_consumer, desde la que se ha accedido a QuickTest.
     * @param $activityName . Nombre que tiene la tarea en el LTI_consumer, desde la que se ha accedido a QuickTest.
     * @param $isProfesor . Indicador de si es o no profesor.
     * @param $idUser . Identificador del usuario.
     * @param $nombreAlu . Nombre del usuario.
     * @param $apeAlu . Apellidos del usuario.
     * @param $lang . Idioma en el que se sirve la página.
     * @param $idAsig . Identificador de la asignatura.
     * @param $ltiConsumerURL . URL de la plataforma LTI desde la que se ha accedido.
     * Invoca la vista Cuestionario_Resolver_Visualizar_View
     */
    function cuestionario_Resolver_Visualizar($idCuestionario, $courseName, $activityName, $isProfesor, $idUser, $nombreAlu, $apeAlu, $lang, $idAsig, $ltiConsumerURL)
    {


        $cuestionarioModel = new Cuestionario_Model();
        $preguntasModel = new Preguntas_Model();
        $respuestasModel = new Respuestas_Model();
        $alumno_has_cuestionarioModel = new Alumno_has_cuestionario_Model();

        $estaResuelto = $alumno_has_cuestionarioModel->estaResuelto($idUser, $idCuestionario);
//        registramos la conexion solo si es alumno
        if (($isProfesor == 0)) {
            if ($estaResuelto != 1) {
                $this->registrarConexion($idCuestionario);
            }
        }

        $preguntas = $preguntasModel->getIDPreguntaTitulo_Cuestionario($idCuestionario);

        foreach ($preguntas as $p) {
            $respuestasPregunta[] = $respuestasModel->getRespuestas($p);
        }

        if (($isProfesor == 0)) {
            if ($estaResuelto != 1) { //solo miramos comodines si no está resuelto ya
                $preguntasVerde = $this->hasComodinVerde($idCuestionario);
                $preguntasAmarillo = $this->hasComodinAmbar($idCuestionario);
            }
        }

        $name = 0;
        $numPreg = 0;
        $valor = 0;
        $tam = sizeof($preguntas);

        require_once("app/views/cuestionarioView/cuestionario_Resolver_View.php");

    }
    // @codeCoverageIgnoreEnd

//TESTED
    /**
     * Función que incrementa el contador de alumnos participantes, al acceder cuestionario.
     * @param $idCuestionario . id del cuestionario al que acceden.
     * @return bool. Verdadero o falso si se ha registrado la conexión.
     */
    function registrarConexion($idCuestionario)
    {

        //echo $idCuestionario;
        $cuestionarioModel = new Cuestionario_Model();
        $alumno_has_respuestaModel = new Alumno_has_respuesta_Model();
        $preguntasModel = new Preguntas_Model();

        echo "<p id='postComodines' hidden>";
        if ($idCuestionario != 1) {
            $cuestionarioModel->incAlumnosConectados($idCuestionario);
            return true;
        }


    }

//TESTED
    /**
     * Función que recibe los datos de la respuesta que ha marcado el alumno
     * y la guarda como ya respondida.
     * También registra información extra como el tipo de comodín usado.
     * @param $idRespuesta . id de la respuesta que queremos guardar.
     * @param $tipoComUsado . Comodín empleado al elegir la respuesta.
     * @param $idAlumno . id del alumno que ha elegido esa respuesta.
     * @param $pregResuelta . id de la pregunta asociada a esa respuesta.
     * @return bool. Verdadero o falso, si se ha guardado la respuesta.
     */
    function guardarCadaRespuesta($idRespuesta, $tipoComUsado, $idAlumno, $pregResuelta)
    {

        $respuestasModel = new Respuestas_Model();
        $preguntasModel = new Preguntas_Model();
        $alumno_has_respuesta = new Alumno_has_respuesta_Model();
        $cuestionarioModel = new Cuestionario_Model();

        //incrementamos contador de respuesta
        $idCuestionario = $cuestionarioModel->getIdCuest_respuesta($idRespuesta);

        if ($idCuestionario != 1) { //miramos que no sea el cuestionario de demostracion.
            $respuestasModel->incContRespu($idRespuesta);
        }

        $idCuestionario = $cuestionarioModel->getIdCuest_respuesta($idRespuesta);
        $correctores = $cuestionarioModel->getCorrectores($idCuestionario);
        $idPregunta = $preguntasModel->getIdPregunta_respuesta($idRespuesta);
        $max = $preguntasModel->get_maxPunt_pregunta($idPregunta);

        $correctorVerdeTrue = (double)$correctores['correctorVerdeTrue'];
        $correctorVerdeFalse = (double)$correctores['correctorVerdeFalse'];
        $correctorAmarilloTrue = (double)$correctores['correctorAmarilloTrue'];
        $correctorAmarilloFalse = (double)$correctores['correctorAmarilloFalse'];

        //se guardan las respuestas y en funcion del tipo de comodin usadado se asigna una puntuación
        if ($tipoComUsado == "") {
            if ($respuestasModel->isCorrect($idRespuesta)) {
                $puntuacion = $max;
            } else {
                $puntuacion = 0;
            }

        } else if ($tipoComUsado == "bg-success") {
            if ($respuestasModel->isCorrect($idRespuesta)) {
                $puntuacion = $max * $correctorVerdeTrue;
            } else {
                $puntuacion = $max * $correctorVerdeFalse;
            }

        } else if ($tipoComUsado == "bg-warning") {

            if ($respuestasModel->isCorrect($idRespuesta)) {
                $puntuacion = $max * $correctorAmarilloTrue;
            } else {
                $puntuacion = $max * $correctorAmarilloFalse;
            }
        }
        if ($tipoComUsado == "bg-success") {
            $tipoComUsado = "Verde";
        } else if ($tipoComUsado == "bg-warning") {
            $tipoComUsado = "Amarillo";
        } else {
            $tipoComUsado = "Ninguno";
        }

        $correcta = $respuestasModel->isCorrect($idRespuesta);
        $alumno_has_respuesta->setPuntuacion($puntuacion, $idAlumno, $idRespuesta, $tipoComUsado, $pregResuelta, $correcta);

        return true;
    }

//TESTED

    /**
     *  * Función que calcula una tasa a partir de la cual, para un cuestionario,
     * se pueden ofrecer o no comodines.
     * @param $idCuest . id del cuestionario, del que se calcula su tasa.
     * @return float. Tasa calculada
     */
    function getTasaFiabilidad($idCuest)
    {

        $cuestionarioModel = new Cuestionario_Model();

        $alumnosConectados = $cuestionarioModel->getAlumnosConectados($idCuest);
        $tasaFiabilidad = round($alumnosConectados / 3);

        return $tasaFiabilidad;
    }


    /**
     * Funcion que calcula qué preguntas tienen comodín verde y sobre qué respuesta.
     *
     * @param $idCuest . id del cuestionario que queremos analizar.
     * @return false. si ninguna pregunta tiene comodín.
     * @return $preguntasConComodin array que contiene qué pregunta tiene comodín verde y la respuesta que lo posee.
     */
//TESTED
    function hasComodinVerde($idCuest)
    {


        $cuestionarioModel = new Cuestionario_Model();
        $respuestasModel = new Respuestas_Model();
        $preguntasModel = new Preguntas_Model();

        $preguntas = $preguntasModel->getIDPreguntaTitulo_Cuestionario($idCuest);
        $tasa = $this->getTasaFiabilidad($idCuest);
        $preguntasVerde = "";

        foreach ($preguntas as $p) {

            $respuestasPregunta[] = $respuestasModel->getRespuestas($p);
        }

        $mayorIguTasa = false;
        // flag para ver si es mayor o igual tasa

        $arrlength = count($respuestasPregunta);

        for ($i = 0; $i < $arrlength; $i++) {//por cada pregunta

            foreach ($respuestasPregunta[$i] as $r) {//recorro las respuestas

                $hasComodin = true;
                if ($r['contador'] >= $tasa) {//el numero de alumnos que ha respondido a esa respuesta es >= tasa

                    $mayorIguTasa = true;
                    break; //si encuentro uno que lo cumple salgo

                } else {
                    $mayorIguTasa = false;
                }

            }

            if ($mayorIguTasa == true) {//puede haber comodin verde, pero es necesario que coincidan en sus respuestas.

                $contMaximo = 0;
                $j = 0;
                $pos = 0;

                foreach ($respuestasPregunta[$i] as $r) {//obtengo el contadorMaximo y a qué respuesta pertenece

                    if ($r['contador'] > $contMaximo) {
                        $contMaximo = $r['contador'];
                        $pos = $j;
                    }
                    $j++;
                }

                //pongo a 0, el que era el máximo, para que no se cuente a sí mismo.
                $respuestasPregunta[$i][$pos]['contador'] = 0;

                foreach ($respuestasPregunta[$i] as $r) {

                    $cuantosRespondido = $respuestasModel->cuantosHanRespondido($r['pregunta_idPregunta']);
                    $tasaError = (1 / 4) * $cuantosRespondido;

                    if ($r['contador'] >= $tasaError) {
                        $hasComodin = false;
                        break;
                    }
                }

            } else {//ninguna respuesta ha superado la tasa.

                $hasComodin = false;

            }
            if ($hasComodin == true) {

                $preguntasVerde[] = array("pregunta_idPregunta" => $respuestasPregunta[$i][$pos]['pregunta_idPregunta'], "idRespuesta" => $respuestasPregunta[$i][$pos]['idRespuesta']);

            }

        }//for

        //es necesario transformarlas a JSON para que javascript trabaje con ellas
        echo json_encode($preguntasVerde);
        $preguntasVerde = json_encode($preguntasVerde);

        return $preguntasVerde;

    }

    /**
     * Funcion que calcula qué preguntas tienen comodín ámbar y sobre qué respuesta.
     *
     * @param $idCuest . id del cuestionario que queremos analizar
     * @return false. si ninguna pregunta tiene comodín
     * @return $preguntasConComodin. array que contiene qué pregunta tiene comodín ámbar y la respuesta que lo posee
     */
//TESTED
    function hasComodinAmbar($idCuest)
    {
        $respuestasModel = new Respuestas_Model();
        $preguntasModel = new Preguntas_Model();

        $preguntasAmbar = "";
        $pos = 0;
        $preguntas = $preguntasModel->getIDPreguntaTitulo_Cuestionario($idCuest);

        //calcula 1/3 de los alumnos conectados
        //se exige que una respuesta haya sido respondida por al menos 1/3 de los conectados,
        //para considerar que hay comodin
        //de esta forma quitas a las que no han sido respondidas, o lo son con poca gente
        $tasa = $this->getTasaFiabilidad($idCuest);

        foreach ($preguntas as $p) {

            $respuestasPregunta[] = $respuestasModel->getRespuestas($p);
        }


        $mayorIguTasa = false; // flag para ver si es mayor o igual tasa
        $arrlength = count($respuestasPregunta);

        for ($i = 0; $i < $arrlength; $i++) {//por cada pregunta

            foreach ($respuestasPregunta[$i] as $r) {//recorro las respuestas
                $hasComodin = false;
                if ($r['contador'] >= $tasa) { //el numero de alumnos que ha respondido a esa respuesta es >= tasa

                    $mayorIguTasa = true;
                    break;

                } else {

                    $mayorIguTasa = false;
                }

            }

            if ($mayorIguTasa == true) { //puede haber comodin ambar,
                $contMaximo = 0;
                $j = 0;
                foreach ($respuestasPregunta[$i] as $r) { //obtengo el contadorMaximo y a qué respuesta pertenece

                    if ($r['contador'] > $contMaximo) {

                        $contMaximo = $r['contador'];
                        $pos = $j;

                    }
                    $j++;
                }

                //contiene la respuesta que más veces ha sido elegida
                $masVotada = $respuestasPregunta[$i][$pos]['idRespuesta'];

                foreach ($respuestasPregunta[$i] as $r) {//miro cada respuesta

                    //cuantos han respondido en total a esa pregunta:
                    $cuantosRespondido = $respuestasModel->cuantosHanRespondido($r['pregunta_idPregunta']);
                    //si otra respuesta diferente a la maxima,
                    //tiene mas de 1/4 de votaciones
                    //se CONSIDERA como comodin AMBAR
                    $tasaError = (1 / 4) * $cuantosRespondido;

                    if ($r['contador'] > $tasaError && $r['idRespuesta'] != $masVotada) {
                        $hasComodin = true;
                        break;
                    }
                }

            } else {//ninguna respuesta ha superado la tasa.
                $hasComodin = false;
            }

            if ($hasComodin == true) {

                $hayRepetidas = false;
                //tambien se muestran si están dentro de intervalo de tolerancia
                $tasaTolerencia = $contMaximo - ((1 / 10) * $cuantosRespondido);

                foreach ($respuestasPregunta[$i] as $r) {
                    //controlamos que si hay respuestas repetidas también se muestren
                    if ($r['contador'] == $respuestasPregunta[$i][$pos]['contador'] && //tan grande como contMax
                        $r['idRespuesta'] != $respuestasPregunta[$i][$pos]['idRespuesta'] || // y sin contar a contMax
                        $r['contador'] >= $tasaTolerencia // o si esta dentro de la tolerancia
                    ) {
                        $repetidas[] = $respuestasPregunta[$i][$pos]; //añado la que tenia el mayor contador
                        $repetidas[] = $r; //y las que tienen el contador igual de grande
                        $hayRepetidas = true;

                    } else {

                    }
                }
                if ($hayRepetidas) {

                    //almaceno las repetidas de una pregunta
                    foreach ($repetidas as $r) {
                        $preguntasAmbar[] = array("pregunta_idPregunta" => $r['pregunta_idPregunta'], "idRespuesta" => $r['idRespuesta']);
                    }

                } else {
                    //sino pongo directamente la respuesta.
                    $preguntasAmbar[] = array("pregunta_idPregunta" => $respuestasPregunta[$i][$pos]['pregunta_idPregunta'], "idRespuesta" => $respuestasPregunta[$i][$pos]['idRespuesta']);

                }
            }
        }//for

        echo json_encode($preguntasAmbar); //es necesario transformarlas a JSON para que javascript trabaje con ellas
        return ($preguntasAmbar);

    }

    //TESTED
    /**
     * Función que registra a un alumno, la primera vez que accede a QuickTest.
     * @param $idAlu . id del usuario.
     * @param $nombre . nombre del usuario.
     * @param $apellidos . apellidos del usuario.
     * @return bool. Verdadero o falso si se ha registrado.
     */
    function registrarAlumno($idAlu, $nombre, $apellidos)
    {
        $alumnoModel = new Alumno_Model();
        $alumnoModel->insertarAlumno($idAlu, $nombre, $apellidos);
        return true;
    }

//TESTED
    /**
     * Función que comprueba si es la primera vez que el alumno accede a un cuestionario.
     * Si es que sí todas las preguntas podrán ser respondidas.
     * Si ya había entrado, las respuestas que ya había guardado, aparecerán desactivadas.
     * @param $idCuestionario . id del cuestionario a revisar.
     * @param $idUser . id del usuario.
     * @return array|null. Array con las preguntas ya contestadas, o null si no ha contestado aún.
     */
    function checkSegundoIntento($idCuestionario, $idUser)
    {

        $respuestasModel = new Respuestas_Model();
        $alumnoModel = new Alumno_Model();
        $preguntasModel = new Preguntas_Model();
        $alumno_has_respuestaModel = new Alumno_has_respuesta_Model();

        $preguntasRespondidas = null;
        $respuestasRespondidas = $alumno_has_respuestaModel->getRespuestasRespondidas_Cuestionario($idUser, $idCuestionario);

        if (isset($respuestasRespondidas)) {

            foreach ($respuestasRespondidas as $r) {

                $preguntasRespondidas[] = $preguntasModel->getIdPregunta_respuesta($r['idRespuesta']);

            }

            echo json_encode($preguntasRespondidas);

            return $preguntasRespondidas;
        }
        return $preguntasRespondidas;

    }



//TESTED
    /**
     * Función que una vez que el alumno ha finalizado el cuestionario,
     * devuelve a la vista los datos necesarios, para mostrarle los resultados obtenidos.
     * sobre el cuestionario.
     * @param $idCuestionario . id del cuestionario del que se muestran los resultados..
     * @param $idAlu . id del alumno que ha resuelto el cuestionario.
     * @param $idAsig . ide de la asignatura a la que pertenece la asignatura.
     * @return array. Resultados.
     */
    function mostarResultados($idCuestionario, $idAlu, $idAsig)
    {

        $alumno_has_respuesta_Model = new Alumno_has_respuesta_Model();
        $preguntasModel = new Preguntas_Model();
        $alumno_has_respuestaModel = new Alumno_has_respuesta_Model();
        $alumno_has_cuestionario_Model = new Alumno_has_cuestionario_Model();

        //cantidad comodines usados
        $cSin = $alumno_has_respuesta_Model->getCuantos_Comodin($idAlu, $idCuestionario, "ninguno");
        $cAmarillo = $alumno_has_respuesta_Model->getCuantos_Comodin($idAlu, $idCuestionario, "amarillo");
        $cVerde = $alumno_has_respuesta_Model->getCuantos_Comodin($idAlu, $idCuestionario, "verde");
        $cPreguntasRespondidas = $alumno_has_respuesta_Model->getCuantasPreguntasRespondidas_Alumno($idAlu, $idCuestionario);

        //puntuaciones por comodin
        $puntAmarillo = $alumno_has_respuesta_Model->getSumaPuntComodin_Alumno($idAlu, $idCuestionario, "Amarillo");
        $puntVerde = $alumno_has_respuesta_Model->getSumaPuntComodin_Alumno($idAlu, $idCuestionario, "Verde");
        $puntSin = $alumno_has_respuesta_Model->getSumaPuntComodin_Alumno($idAlu, $idCuestionario, "Ninguno");

        //aciertos por comodin
        $aciertoAmarillo = $alumno_has_respuesta_Model->getCuantosAciertos_Comodin($idAlu, $idCuestionario, "Amarillo", 1);
        $aciertoVerde = $alumno_has_respuesta_Model->getCuantosAciertos_Comodin($idAlu, $idCuestionario, "Verde", 1);
        $aciertoSin = $alumno_has_respuesta_Model->getCuantosAciertos_Comodin($idAlu, $idCuestionario, "Ninguno", 1);

        //aciertos por comodin
        $fallosAmarillo = $alumno_has_respuesta_Model->getCuantosAciertos_Comodin($idAlu, $idCuestionario, "Amarillo", 0);
        $fallosVerde = $alumno_has_respuesta_Model->getCuantosAciertos_Comodin($idAlu, $idCuestionario, "Verde", 0);
        $fallosSin = $alumno_has_respuesta_Model->getCuantosAciertos_Comodin($idAlu, $idCuestionario, "Ninguno", 0);


        //Totales
        $puntTotal = $alumno_has_respuestaModel->getSumaPuntTotal_Alumno($idCuestionario, $idAlu);
        $puntMaxPosible = $preguntasModel->getPuntMaxima_Cuestionario($idCuestionario);
        $totalPreguntas = $preguntasModel->countPreguntasCuestionario($idCuestionario);

        //Calcular premio
        $calificMaxPosible = $preguntasModel->getPuntMaxima_Cuestionario($idCuestionario);

        //Orden de respuesta
        $orden = $alumno_has_cuestionario_Model->selectOrdenRespuesta($idCuestionario , $idAlu);
        $premio = 0;
        $mensaje = "";
        $tipoPanel = "";
        $ordenRespuesta = 0;
        $tipoText = "";
        if ($orden == 1) {

            $mensaje = _("Has sido MUY RÁPIDO respondiendo, has conseguido");
            $ordenRespuesta = _("MUY RÁPIDO");
            $tipoPanel = "panel-success";
            $premio = "+" . ($calificMaxPosible / 10);
            $tipoText = "text-success";

        } else if ($orden == 2) {

            $mensaje = _("Has sido RÁPIDO respondiendo, has conseguido");
            $tipoPanel = "panel-info";
            $premio = "+" . (($calificMaxPosible / 10) / 2);
            $tipoText = "text-info";
            $ordenRespuesta = _("RÁPIDO");

        } else if ($orden == 3) {

            $mensaje = _("Has sido de los ÚLTIMOS en responder, has obtenido una penalización de:");
            $tipoPanel = "panel-danger";
            $premio = -(($calificMaxPosible / 10) / 2);
            $tipoText = "text-danger";
            $ordenRespuesta = _("LENTO");

        }

        if (($puntTotal + $premio) > $calificMaxPosible) {

            $puntTotal = $calificMaxPosible;

        } else if (($puntTotal + $premio) <= 0 || $puntTotal == 0) {

            $puntTotal = "0.00";

        } else {

            $puntTotal = ($puntTotal + $premio);

        }

        $resultados = array(
            'cSin' => $cSin,
            'cAmarillo' => $cAmarillo,
            'cVerde' => $cVerde,
            'cPreguntasRespondidas' => $cPreguntasRespondidas,

            'puntAmarillo' => $puntAmarillo,
            'puntVerde' => $puntVerde,
            'puntSin' => $puntSin,

            'aciertoAmarillo' => $aciertoAmarillo,
            'aciertoVerde' => $aciertoVerde,
            'aciertoSin' => $aciertoSin,

            'fallosAmarillo' => $fallosAmarillo,
            'fallosVerde' => $fallosVerde,
            'fallosSin' => $fallosSin,

            'puntTotal' => $puntTotal,
            'puntMaxPosible' => $puntMaxPosible,
            'totalPreguntas' => $totalPreguntas,
            'orden' => $orden,
            'mensaje' => $mensaje,
            'tipoPanel' => $tipoPanel,
            'premio' => $premio,
            'ordenRespuesta' => $ordenRespuesta,
            'tipoText' => $tipoText
        );

        echo json_encode($resultados);
        return $resultados;

    }

//TESTED
    /**
     * Funcion que calcula el orden de respuesta del alumno.
     * @param $idCuest . id del cuestionario a revisar.
     * @return int
     *              1 si ha respondido entre los más rápidos.
     *              2 si ha respondido  ni muy rápido ni muy lento.
     *              3 si ha sido de los últimos en responder.
     */
    function calculaOrdenPrimeraVez($idCuest)
    {
        $cuestionarioModel = new Cuestionario_Model();
        $alumno_has_cuestionarioModel = new Alumno_has_cuestionario_Model();

        $alumnosConectados = $cuestionarioModel->getAlumnosConectados($idCuest);
        $cuantosHanAcabado = $alumno_has_cuestionarioModel->contarAlumnosFinalizado_Cuestionario($idCuest);

        $franja1 = $alumnosConectados / 3; // p.ej de 0 a 10
        $franja2 = 2 * $franja1; //p.ej de 10 a 20
        $franja3 = $alumnosConectados;  // de 20 a total conectados

        if ($cuantosHanAcabado >= 0 && $cuantosHanAcabado <= $franja1) { //grupo Más RÁPIDOS
            $orden = 1;
        } else if ($cuantosHanAcabado > $franja1 && $cuantosHanAcabado < $franja2) { //grupo CENTRAL
            $orden = 2;
        } else if ($cuantosHanAcabado >= $franja2) { // grupo más LENTOS
            $orden = 3;
        }

        return $orden;
    }

}

?>