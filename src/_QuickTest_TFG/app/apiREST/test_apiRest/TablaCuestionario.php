<?php

/**
 * Created by PhpStorm.
 * User: Daniel
 * Date: 21/03/2017
 * Time: 13:35
 */
abstract class TablaCuestionario
{
    const NOMBRE_TABLA = "cuestionario";
    const ID_CUESTIONARIO = "idCuestionario";
    const TITULO = "titulo";
    const DURACION = "duracion";
    const CONTADOR_ALUMNOS = "contadorAlumnos";
    const ASIGNATURA_ID_ASIGNATURA = "asignatura_idAsignatura";
    const ASIGNATURA_NOMBRE_ASIGNATURA = "asignatura_nombreAsignatura";
    const CORRECTOR_VERDE_TRUE = "correctorVerdeTrue";
    const CORRECTOR_VERDE_FALSE = "correctorVerdeFalse";
    const CORRECTOR_AMARILLO_TRUE = "correctorAmarilloTrue";
    const CORRECTOR_AMARILLO_FALSE = "correctorAmarilloFalse";
}