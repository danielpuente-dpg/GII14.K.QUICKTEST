/**
 * @author Alejandro Martínez García <amg0098@alu.ubu.es>
 * @version 1.0.
 */

/**
 * Conjunto de funciones empleadas en la vista Cuestionario_Editar_View.php
 */

/*Variables globales*/
var idPreg;

$(document).ready(encapsularBotones);
$(document).ready(seleccionarCorrectorVerdeTrue);
$(document).ready(seleccionarCorrectorVerdeFalse);
$(document).ready(seleccionarCorrectorAmarilloTrue);
$(document).ready(seleccionarCorrectorAmarilloFalse);


$(document).ready(cuentaPreguntasInicial);
/**
 * Cuenta cuántas preguntas había en un principio.
 */
function cuentaPreguntasInicial() {
    $cuantasPreg = $("#countPreguntas").val();
    idPreg = $cuantasPreg;

}

/**
 * Si hay muchas preguntas, las introduce en un dropdown.
 */
function encapsularBotones (){

    if (($('.navbar-btn').length -2) >= "19") {

        jQuery('<span/>', {
            id: 'iconBarras',
            class: "glyphicon glyphicon-align-justify"


        }).insertAfter("#col10-navbar");

        jQuery('<span/>', {
            id: 'iconCaret',
            class: "caret"
        }).insertAfter("#iconBarras");

        $("#iconBarras, #iconCaret").wrapAll("<button id='barrasCaret' class='btn btn-primary dropdown-toggle navbar-btn' type= 'button'  id= 'menu1'   data-toggle= 'dropdown' > </button>");


        $("#mayor21").wrapAll("<ul id='ulbtngroup' class='dropdown-menu'  role='menu' aria-labelledby='menu1' ></ul>");
        $("#barrasCaret, #ulbtngroup").wrapAll("<ul id='barrasCaret' class='dropdown-menu-left pull-right' > </button>");
        parpadear (barrasCaret);
    }

}



/**
 * al pulsar sobre el boton del navbar, hace scroll hasta la pregunta correspondiente
 * @param buttonPreg. boton pulsado.
 */
function gotoPregunta(buttonPreg) {

    idPregNavBar = $(buttonPreg).attr('data-idPreg') ;

    if (idPregNavBar == 1) {
        var solutionGoTo = "#labelpmax1";

    } else {

        solutionGoTo = "#addRespuestaP" + (idPregNavBar-1);
    }

    $('html, body').animate({
        scrollTop: $(solutionGoTo).offset().top
    }, 1000);

}
/**
 * al pulsar sobre el boton del navbar, hace scroll hasta la nueva pregunta.
  */
function gotoNewPreg() {

    solutionGoTo = "#panel-heading" + idPreg;
    $('html, body').animate({
        scrollTop: $(solutionGoTo).offset().top
    }, 1000);

}
/**
 * Hace submit al formulario.
 */
function submitFormu() {
    $('#botonGuardarCuestionario').click();

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








