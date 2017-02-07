/**
 * @author Alejandro Martínez García <amg0098@alu.ubu.es>
 * @version 1.0.
 */

/**
 * Conjunto de funciones empleadas en la vista Cuestionario_Duplicar_View.php
 */


/*VARIABLES GLOBALES*/
var idPreg;

$(document).ready(seleccionarCorrectorVerdeTrue);
$(document).ready(seleccionarCorrectorVerdeFalse);
$(document).ready(seleccionarCorrectorAmarilloTrue);
$(document).ready(seleccionarCorrectorAmarilloFalse);
$(document).ready(cuentaPreguntasInicial);

/**
 * Añade al titulo, la palabra "copia", si no se modifica el titulo.
 */
function addCopiaTitulo(){

    $('#formDuplicar').submit(function () {


        if ($('#tituloCuest').val() == $('#tituloOriginal').text() ){

            tituloOriginal= $('#tituloOriginal').text();
            nuevoTitulo = tituloOriginal +" (copia)";
            $('#tituloCuest').val(tituloOriginal+" (copia)");

        }
    });
}


/**
 * Cuenta cuantas preguntas hay en un principio
 */
function cuentaPreguntasInicial() {
    $cuantasPreg = $("#countPreguntas").val();
    idPreg = $cuantasPreg;

}


/**
 * Establece un valor por defecto en el select,
 * en función de lo que vale el corrector en el cuestionario asociado.
 */

function seleccionarCorrectorVerdeTrue() {

    value = $("#idCorrVerdeTrue").val();
    if (value == 0.20) {
        $("#opCorrVerdeT020").attr('selected', 'selected');


    } else if (value == 0.25) {
        $("#opCorrVerdeT025").attr('selected', 'selected');
    } else if (value == 0.33) {
        $("#opCorrVerdeT033").attr('selected', 'selected');
    } else if (value == 0.5) {
        $("#opCorrVerdeT05").attr('selected', 'selected');
    }
}

/**
 * Establece un valor por defecto en el select,
 * en función de lo que vale el corrector en el cuestionario asociado.
 */
function seleccionarCorrectorVerdeFalse() {

    value = $("#idCorrVerdeFalse").val();
    if (value == -0.20) {
        $("#opCorrVerdeF020").attr('selected', 'selected');

    } else if (value == -0.25) {
        $("#opCorrVerdeF025").attr('selected', 'selected');
    } else if (value == -0.33) {
        $("#opCorrVerdeF033").attr('selected', 'selected');
    } else if (value == -0.5) {
        $("#opCorrVerdeF05").attr('selected', 'selected');
    }
}

/**
 * Establece un valor por defecto en el select,
 * en función de lo que vale el corrector en el cuestionario asociado.
 */
function seleccionarCorrectorAmarilloTrue() {

    value = $("#idCorrAmarilloTrue").val();

    if (value == 0.20) {
        $("#opCorrAmarilloT020").attr('selected', 'selected');
    } else if (value == 0.25) {
        $("#opCorrAmarilloT025").attr('selected', 'selected');
    } else if (value == 0.33) {
        $("#opCorrAmarilloT033").attr('selected', 'selected');
    } else if (value == 0.5) {
        $("#opCorrAmarilloT05").attr('selected', 'selected');
    }
}

/**
 * Establece un valor por defecto en el select,
 * en función de lo que vale el corrector en el cuestionario asociado.
 */
function seleccionarCorrectorAmarilloFalse() {

    value = $("#idCorrAmarilloFalse").val();

    if (value == -0.20) {
        $("#opCorrAmarilloF020").attr('selected', 'selected');


    } else if (value == -0.25) {
        $("#opCorrAmarilloF025").attr('selected', 'selected');
    } else if (value == -0.33) {
        $("#opCorrAmarilloF033").attr('selected', 'selected');
    } else if (value == -0.5) {
        $("#opCorrAmarilloF05").attr('selected', 'selected');
    }
}


$(document).ready(hideContent);
/**
 * Oculta un botón.
 */
function hideContent() {
    $("#hideButton").hide();
}

