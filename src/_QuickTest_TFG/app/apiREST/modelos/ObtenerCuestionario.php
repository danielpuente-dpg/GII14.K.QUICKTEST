<?php

/**
 * Created by PhpStorm.
 * User: Daniel
 * Date: 24/04/2017
 * Time: 16:40
 */

if (!empty($_SERVER["DOCUMENT_ROOT"]))
    $URL_GLOBAL = $_SERVER["DOCUMENT_ROOT"];

require_once($URL_GLOBAL . '/_QuickTest_TFG/app/model/Preguntas_Model.php');
require_once($URL_GLOBAL . '/_QuickTest_TFG/app/model/Respuestas_Model.php');
require_once($URL_GLOBAL . '/_QuickTest_TFG/app/model/Alumno_has_cuestionario_Model.php');
require_once($URL_GLOBAL . '/_QuickTest_TFG/app/model/Alumno_has_respuesta_Model.php');
require_once($URL_GLOBAL . '/_QuickTest_TFG/app/controller/Cuestionario_Resolver_Controller.php');

/**
 * Clase ObtenerCuestionario encargada de obtener toda la informacion necesaria para resolver un
 * cuestionario y la tabla de retroalimentacion del mismo.
 *
 * @autor Daniel Puente Gabarri.
 */
class ObtenerCuestionario
{

    /**
     * Constantes de estado para los mensajes de salida
     */
    const ESTADO_EXITO = 1;
    const ESTADO_NO_ENCONTRADO = 2;
    const ESTADO_ERROR_PARAMETROS = 4;

    /**
     * Método encargado de tratar la lógica de un petición GET.
     * @param $peticion , peticion.
     * @return array, resto de campos que forman la peticion.
     * @throws APIException
     */
    public static function get($peticion)
    {
        if ($peticion[0] == 'obtener') {
            return self::getCuestionario($peticion[1]);
        } else if ($peticion[0] == 'estado') {
            return self::getEstadoCuestionario($peticion[1], $peticion[2]);
        } else if ($peticion[0] == 'obtenerComodin') {
            if ($peticion[1] == 'verde') {
                return self::getComodinVerde($peticion[2]);
            } else if ($peticion[1] == 'ambar') {
                return self::getComodinAmbar($peticion[2]);
            }
        } else if ($peticion[0] = 'feedback') {
            return self::getFeedBack($peticion[1], $peticion[2]);
        } else {
            throw new APIException(self::ESTADO_ERROR_PARAMETROS,
                "Error al obtener. Falta el ID de Cuestionario " . "<class> " . ObtenerCuestionario::class . " </class>",
                APIEstados::ESTADO_UNPROCESSABLE_ENTITY);
        }

    }

    /**
     * Metodo que permite obtener un cuestionario.
     * @param $peticion , identificador del cuestionario.
     * @return array, respuesta.
     */
    public static function getCuestionario($peticion)
    {
        $preguntasModel = new Preguntas_Model();
        $respuestasModel = new Respuestas_Model();
        $retorno = array();

        // Obtenemos el Identificador del cuestionario
        $idCuestionario = $peticion[0];
        // Obtenemos todas las preguntas de ese cuestionario
        $preguntas = $preguntasModel->getIDPreguntaTitulo_Cuestionario($idCuestionario);
        // Para cada pregunta de ese cuestionario obtenemos sus respuestas
        foreach ($preguntas as $p) {

            // Obtenemos las respuestas
            $respuestas = $respuestasModel->getRespuestas($p);
            $datos = array();
            $datos["pregunta"] = $p;
            $datos["respuesta"] = $respuestas;
            $retorno[] = $datos;

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
     * Metodo que nos permite saber si el cuestionario se encuentra o no resuelto.
     * @param $idUser , identificador de usuario.
     * @param $idCuestionario , identificador de cuestionario.
     * @return array, respuesta.
     */
    public static function getEstadoCuestionario($idUser, $idCuestionario)
    {

        $alumno_has_cuestionarioModel = new Alumno_has_cuestionario_Model();

        if ($alumno_has_cuestionarioModel->estaResuelto($idUser, $idCuestionario)) {
            $retorno = 1;
        } else {
            $retorno = 0;
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
     * Metodo encargado de proporcionar para un cuestionario que preguntas tienen comodin verde.
     * @param $idCuestionario , identificador del cuestionario.
     * @return array, respuesta.
     */
    public static function getComodinVerde($idCuestionario)
    {
        $preguntasVerde = self::getInfoComodinVerde($idCuestionario);

        // Establecemos la respuesta
        http_response_code(APIEstados::ESTADO_OK);
        // Retornamos la informacion
        return
            [
                "estado" => self::ESTADO_EXITO,
                "mensaje" => json_decode($preguntasVerde)

            ];

    }

    /**
     * Metodo encargado de obtener la informacion sobre las preguntas que tienen comodin
     * verde dado un cuestionario.
     * @param $idCuest , identificador del cuestionario.
     * @return array|string, respuesta.
     */
    private static function getInfoComodinVerde($idCuest)
    {
        $cuestionarioModel = new Cuestionario_Model();
        $respuestasModel = new Respuestas_Model();
        $preguntasModel = new Preguntas_Model();

        $preguntas = $preguntasModel->getIDPreguntaTitulo_Cuestionario($idCuest);

        $cuestionario_Resolver_Controller = new Cuestionario_Resolver_Controller();
        $tasa = $cuestionario_Resolver_Controller->getTasaFiabilidad($idCuest);

        $preguntasVerde = array();

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

        $preguntasVerde = json_encode($preguntasVerde);

        return $preguntasVerde;

    }

    /**
     * Metodo encargado de proporcionar para un cuestionario que preguntas tienen comodin ammabr.
     * @param $idCuestionario , identificador del cuestionario.
     * @return array, respuesta.
     */
    public static function getComodinAmbar($idCuestionario)
    {
        $preguntasAmbar = self::getInfoComodinAbar($idCuestionario);

        // Establecemos la respuesta
        http_response_code(APIEstados::ESTADO_OK);
        // Retornamos la informacion
        return
            [
                "estado" => self::ESTADO_EXITO,
                "mensaje" => $preguntasAmbar

            ];
    }

    /**
     * Metodo encargado de obtener la informacion sobre las preguntas que tienen comodin
     * ambar dado un cuestionario.
     * @param $idCuest , identificador del cuestionario.
     * @return array|string, respuesta.
     */
    private static function getInfoComodinAbar($idCuest)
    {
        $respuestasModel = new Respuestas_Model();
        $preguntasModel = new Preguntas_Model();

        $preguntasAmbar = array();
        $pos = 0;
        $preguntas = $preguntasModel->getIDPreguntaTitulo_Cuestionario($idCuest);

        //calcula 1/3 de los alumnos conectados
        //se exige que una respuesta haya sido respondida por al menos 1/3 de los conectados,
        //para considerar que hay comodin
        //de esta forma quitas a las que no han sido respondidas, o lo son con poca gente
        $cuestionario_Resolver_Controller = new Cuestionario_Resolver_Controller();
        $tasa = $cuestionario_Resolver_Controller->getTasaFiabilidad($idCuest);

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

        return ($preguntasAmbar);

    }

    /**
     * Metodo encargado de obtener la informacion sobre cuestionario. Numero de preguntas contestadas,
     * numero de comodines usador, etc.
     * @param $idCuestionario , identificador del cuestionario.
     * @param $idAlu , identificador del alumno.
     * @return array, respuesta.
     */
    public static function getFeedBack($idCuestionario, $idAlu)
    {
        $feedback = self::getInfoFeedBack($idCuestionario, $idAlu);

        // Establecemos la respuesta
        http_response_code(APIEstados::ESTADO_OK);
        // Retornamos la informacion
        return
            [
                "estado" => self::ESTADO_EXITO,
                "mensaje" => $feedback

            ];
    }

    /**
     * Metodo encargado de obtener la informacion sobre un cuestionario.
     * @param $idCuestionario , identificador del cuestionario.
     * @param $idAlu , identificador del alumno.
     * @return array, respuesta.
     */
    private static function getInfoFeedBack($idCuestionario, $idAlu)
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
        $orden = $alumno_has_cuestionario_Model->selectOrdenRespuesta($idCuestionario, $idAlu);
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

        return $resultados;

    }

}