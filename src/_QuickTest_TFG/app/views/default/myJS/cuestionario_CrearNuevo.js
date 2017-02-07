/**
 * @author Alejandro Martínez García <amg0098@alu.ubu.es>
 * @version 1.0.
 */

/**
 * Conjunto de funciones empleadas en la vista Cuestionario_CrearNuevo_View.php
 */


/*VARIABLES GLOBALES*/
//var idRes = 1;
var idPreg = 1;


/**
 * Dibuja una nueva respuesta en la vista.
 * @param boton. botón pulsado.
 */
function addRespuesta(boton) {
    var lang = $("#lang").val();
    if (lang == "es") {
        respuesta = "Respuesta";
        placeEscribeResp = "Escribe una respuesta";


    } else {
        respuesta = "Answer";
        placeEscribeResp = "Write an answer";


    }
    pregBoton = $(boton).attr('data-idPreg');
    respBoton = $(boton).attr('data-idRes');
    respBoton++;

    $(boton).attr('data-idRes', respBoton);

    jQuery('<label/>', {
        id: 'labelRes' + pregBoton + '_' + respBoton,
        name: 'labelRes' + pregBoton,
        for: 'titulo Respuesta',
        text: respuesta + respBoton,
        style: 'font-size: small'
    }).insertAfter("#col-lg-1col-lg-push-1radiocontrol-label" + pregBoton + '_' + (respBoton - 1));


    jQuery('<textarea/>', {
        id: 'resp' + pregBoton + '_' + respBoton,
        name: 'pregRes' + pregBoton + '[res][]',
        class: "form-control input-sm",
        rows: "1",
        required: "true",

        placeholder: placeEscribeResp

    }).insertAfter('#labelRes' + pregBoton + '_' + respBoton);
    $('#resp' + pregBoton + '_' + respBoton).html('Respuesta ' + respBoton);


    jQuery('<input/>', {
        id: 'correcta' + pregBoton + '_' + respBoton,
        name: 'pregRes' + pregBoton + '[correcta]',
        type: 'radio',
        value: respBoton,
        style: 'margin-left: 30px'
    }).insertAfter('#resp' + pregBoton + '_' + (respBoton));


    $("#labelRes" + pregBoton + '_' + respBoton).wrapAll("<div  class='col-lg-9 col-lg-push-1 control-label' name='col-lg-9col-lg-push-1control-label" + pregBoton + "'  id='col-lg-9col-lg-push-1control-label" + pregBoton + '_' + respBoton + "'> </div>");
    $("#resp" + pregBoton + '_' + respBoton).wrapAll("<div  class='col-lg-9 col-lg-push-1' name='col-lg-9col-lg-push-1" + pregBoton + "' id='col-lg-9col-lg-push-1" + pregBoton + '_' + respBoton + "'> </div>");
    $("#correcta" + pregBoton + '_' + respBoton).wrapAll("<div  class='col-lg-1 col-lg-push-1 radio control-label' name='col-lg-1col-lg-push-1radiocontrol-label" + pregBoton + "'    id='col-lg-1col-lg-push-1radiocontrol-label" + pregBoton + '_' + respBoton + "'> </div>");


}

/**
 * Dibuja una nueva pregunta en la vista.
 * @param boton. botón pulsado.
 */
function addPregunta(boton) {
    idPreg++;

    idRes = 1;

    pregBoton = $(boton).attr('data-idPreg');
    respBoton = $(boton).attr('data-idRes');
    respBoton++;

    $(boton).attr('data-idRes', respBoton);


    var lang = $("#lang").val();
    if (lang == "es") {
        respuesta = "Respuesta";
        pregunta = "Pregunta ";
        placeEnuncPreg = "Escribe el enunciado de la pregunta";
        esCorrecta = "Es correcta";
        placeEscribeResp = "Escribe una respuesta";
        pmax = "Puntuacion max";
        annadirRes = "Añadir respuesta";


    } else {
        respuesta = "Answer";
        pregunta = "Question ";
        placeEnuncPreg = "Write the question's title";
        esCorrecta = "It's correct";
        placeEscribeResp = "Write an answer";
        pmax = "High score";
        annadirRes = "Add answer";


    }
//------------------------------- PREGUNTAS --------------------------------------------


    jQuery('<label/>', {
        id: "labelPreg" + idPreg,
        for: "titulo Pregunta",
        text: pregunta + idPreg

    }).insertBefore("#newPreg");


    jQuery('<textarea/>', {
        id: 'preg' + idPreg,
        name: 'pregRes' + idPreg + '[title]',
        'data-idPreg': idPreg,
        class: "form-control input-sm",
        rows: "1",
        required: "true",
        placeholder: placeEnuncPreg

    }).insertAfter("#labelPreg" + idPreg);

    $("#preg" + idPreg).html("Pregunta " + idPreg);

    //GLOBAL
    $("#labelPreg" + idPreg + ",#preg" + idPreg).wrapAll("<div class='panel panel-info' id='panelpanel-info" + idPreg + "' > </div>");
    $("#labelPreg" + idPreg).wrapAll("<div class='panel-heading' id='panel-heading" + idPreg + "' > </div>");

//------------------------------- RESPUESTAS--------------------------------------------

    jQuery('<label/>', { //OK
        id: 'labelRes' + idPreg + '_' + idRes,
        name: 'labelRes' + idPreg,
        for: 'titulo Respuesta',
        text: respuesta + idRes,
        style: 'font-size: small'
    }).insertAfter("#panelpanel-info" + idPreg);



    jQuery('<label/>', {
        id: 'esCorrecta' + idPreg + '_' + idRes,
        for: 'esCorrecta',
        text: esCorrecta,
        style: 'font-size: small'
    }).insertAfter("#labelRes" + idPreg + '_' + idRes);


    jQuery('<textarea/>', {
        id: 'resp' + idPreg + '_' + idRes,
        name: 'pregRes' + idPreg + '[res][]',
        class: "form-control input-sm",
        rows: "1",
        required : "true",
        placeholder: placeEscribeResp
    }).insertAfter('#esCorrecta' + idPreg + '_' + idRes);
    $('#resp' + idPreg + '_' + idRes).html('Respuesta ' + idRes );

    jQuery('<input/>', {
        id: 'correcta' + idPreg + '_' + idRes,
        name: 'pregRes' + idPreg + '[correcta]',
        type: 'radio',
        checked: "true",
        style: 'margin-left: 30px',
        value: 1
    }).insertAfter('#resp' + idPreg + '_' + idRes);

    $("#labelRes" + idPreg + '_' + idRes + "," +
    "#preg" + idPreg + "," +  // PREG A PANEL-BODY
    "#esCorrecta" + idPreg + '_' + idRes + "," +
    "#resp" + idPreg + '_' + idRes + "," +
    "#correcta" + idPreg + '_' + idRes).wrapAll("<div class='panel-body' id='panel-body" + idPreg + "' > </div>");
    // PREG A PANEL-BODY
    $("#preg" + idPreg).wrapAll("<div class='col-lg-12' id='Acol-lg-12" + idPreg + "' > </div>");


    $("#labelRes" + idPreg + '_' + idRes + "," +
    "#esCorrecta" + idPreg + '_' + idRes + "," +
    "#resp" + idPreg + '_' + idRes + "," +
    "#correcta" + idPreg + '_' + idRes).wrapAll("<div  class='container' " + "id='container" + idPreg + '_' + idRes + "'> </div>");

    $("#labelRes" + idPreg + '_' + idRes + "," +
    "#esCorrecta" + idPreg + '_' + idRes + "," +
    "#resp" + idPreg + '_' + idRes + "," +
    "#correcta" + idPreg + '_' + idRes).wrapAll("<div  class='row' id='row" + idPreg + '_' + idRes + "' id='row" + idPreg + '_' + idRes + "'> </div>");

    $("#labelRes" + idPreg + '_' + idRes + "," +
    "#esCorrecta" + idPreg + '_' + idRes + "," +
    "#resp" + idPreg + '_' + idRes + "," +
    "#correcta" + idPreg + '_' + idRes).wrapAll("<div  class='form-group' " + "id='form-group" + idPreg + '_' + idRes + "'> </div>");


    $("#labelRes" + idPreg + '_' + idRes).wrapAll("<div  class='col-lg-9 col-lg-push-1 control-label' name='col-lg-9col-lg-push-1control-label" + idPreg + "' id='col-lg-9col-lg-push-1control-label" + idPreg + '_' + idRes + "'> </div>");
    $("#esCorrecta" + idPreg + '_' + idRes).wrapAll("<div  class='col-lg-1 col-lg-push-1' " + "id='col-lg-1col-lg-push-1" + idPreg + '_' + idRes + "'> </div>");
    $("#resp" + idPreg + '_' + idRes).wrapAll("<div  class='col-lg-9 col-lg-push-1' name='col-lg-9col-lg-push-1" + idPreg + "' id='col-lg-9col-lg-push-1" + idPreg + '_' + idRes + "' > </div>");
    $("#correcta" + idPreg + '_' + idRes).wrapAll("<div  class='col-lg-1 col-lg-push-1 radio control-label' name='col-lg-1col-lg-push-1radiocontrol-label" + idPreg + "' id='col-lg-1col-lg-push-1radiocontrol-label" + idPreg + '_' + idRes + "' > </div>");

    jQuery('<p/>', {
        id: "new" + idPreg
    }).insertAfter("#cuestionario_CrearNuevo_View.php" + idPreg + '_' + idRes);

    jQuery('<span/>', {

        id: "iconoPlus" + idPreg,
        class: "glyphicon glyphicon-plus-sign"

    }).insertAfter("#col-lg-1col-lg-push-1radiocontrol-label" + idPreg + '_' + idRes);

//convierte el icono, en boton
    $("#iconoPlus" + idPreg).wrapAll("<button  type='button' class='btn btn-info btn-xs pull-right' onclick = 'addRespuesta(this);' data-idPreg='" + idPreg + "' data-idRes='1' id='addRespuestaP" + idPreg + "' ></button>");
    $("#addRespuestaP" + idPreg).append(annadirRes);
    $("#addRespuestaP" + idPreg).wrapAll("<div  class='col-lg-9 col-lg-push-1' id='AddCol-lg-9col-lg-push-1" + idPreg + "' > </div>");
    $("<p> </p>").insertBefore("#addRespuestaP" + idPreg);


    //--------------------------------------ESTABLECER PUNTUACION (se ha hecho despues de todo) -------------------------
    jQuery('<label/>', {
        id: "labelpmax" + idPreg,
        for: "pmax",
        class: "label",
        text: pmax

    }).insertAfter("#labelPreg" + idPreg);

    jQuery('<input/>', {
        id: "pmax" + idPreg,
        type: "number",
        style: "width: 65px",
        value: "1",
        class: "input-sm text-primary text-left",
        min: "1",
        max: "10",
        step: "1",
        name: "pregRes" + idPreg + "[pmax]",
        for: "pmax",
        text: "Puntuacion max"

    }).insertAfter("#labelpmax" + idPreg);
    $("#labelpmax" + idPreg + ", #pmax" + idPreg).wrapAll("<div class='pull-right' id='pull-rightpmax" + idPreg + "'></div>");


    jQuery('<h5/>', {
        id: "papelera" + idPreg,

        class: "text-primary glyphicon glyphicon-trash pull-right"


    }).insertAfter("#pmax" + idPreg);

    $("#papelera" + idPreg).wrapAll("<a type='button' onclick= 'deletePregunta(this);' href='#' id='borrar" + idPreg + "' data-idPreg='" + idPreg + "' ></a>");


    addCasilla();
    buttonsLess10(idPreg);
}

function addCasilla() {


    jQuery('<button/>', {
        id: 'casilla' + idPreg,
        class: "btn btn-default navbar-btn",
        html: idPreg,
        'data-idPreg': idPreg,
        onclick: "gotoSolution(this);"
    }).insertAfter("#nextCasilla" + idPreg);

    jQuery('<b/>', {
        id: 'nextCasilla' + (idPreg + 1)
    }).insertAfter("#casilla" + idPreg);

    $('#nextCasilla2').hide();

    $('#nextCasilla' + (idPreg + 1)).hide();

    if (idPreg == "21") {


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
        parpadear(barrasCaret);
    }
}

$(document).ready(buttonsLess10);
/**
 * Añade dos espacios a los botones de pregunta, cuyo número es menor que 10.
 * @param idPreg
 */
function buttonsLess10(idPreg) {

    if (idPreg < 10) {
        $('*[data-idPreg=' + idPreg + ']').prepend('&nbsp;');
        $('*[data-idPreg=' + idPreg + ']').append('&nbsp;');
    }

}


/**
 * Al pulsar sobre el boton del navbar, hace scroll hasta la pregunta correspondiente
 * @param buttonPreg. botón pulsado
 */
function gotoSolution(buttonPreg) {

    idPregNavBar = $(buttonPreg).attr('data-idPreg') - 1;

    if (idPregNavBar == 0) {
        var solutionGoTo = "#panel-heading1";
    } else {
        solutionGoTo = "#addRespuestaP" + idPregNavBar;
    }

    $('html, body').animate({
        scrollTop: $(solutionGoTo).offset().top
    }, 1000);

}

/**
 * Al añadir una nueva pregunta, hace scroll hasta ella.
  */
function gotoNewPreg() {

    solutionGoTo = "#addRespuestaP" + (idPreg - 1);
    $('html, body').animate({
        scrollTop: $(solutionGoTo).offset().top
    }, 1000);

}

/**
 * Hace que un elemento del DOM parpadee.
 * @param selector. elemento.
 */
function parpadear(selector) {

    var stopBlinking = false;
    setTimeout(function () {
        stopBlinking = true;
    }, 4000);

    blink(selector);

    function blink(selector) {
        $(selector).fadeOut('fast', function () {
            $(this).fadeIn('fast', function () {
                if (!stopBlinking)
                    blink(this);

            });
        });
    }
}

/**
 * Hace submit del formulario
 */
function submitForm() {
    $('#botonGuardarCuestionario').click();


}

/**
 * Borra una pregunta.
 * @param botonBorrar. botón pulsado.
 */
function deletePregunta(botonBorrar) {


    preguntaBorrada = parseInt($(botonBorrar).attr('data-idPreg'));


    $('#panelpanel-info' + preguntaBorrada).remove();
    $('#casilla' + preguntaBorrada).remove();
    $('#nextCasilla' + preguntaBorrada).remove();


    totalPregDespuesBorrar = parseInt($('.navbar-btn').length - 3);


    for (i = preguntaBorrada; i <= (totalPregDespuesBorrar + 1); i++) {

        $("#casilla" + i).text((i - 1));
        $("#casilla" + i).attr('data-idPreg', (i - 1));
        $("#casilla" + i).attr('id', ('casilla' + (i - 1)));


        $("#borrar" + i).attr('data-idPreg', (i - 1));
        $("#borrar" + i).attr('id', ('borrar' + (i - 1)));

        $("#labelPreg" + i).text("Pregunta" + (i - 1));
        $("#labelPreg" + i).attr('id', ('labelPreg' + (i - 1)));


        $("#nextCasilla" + (i + 1)).attr('id', ('nextCasilla' + (i)));

        $("#panelpanel-info" + i).attr('id', ('panelpanel-info' + (i - 1)));
        $("#pull-rightpmax" + i).attr('id', ('pull-rightpmax' + (i - 1)));

        $("#pmax" + i).attr('name', ('pregRes' + (i - 1) + '[pmax]'));
        $("#pmax" + i).attr('id', ('pmax' + (i - 1)));

        $("#labelpmax" + i).attr('id', ('labelpmax' + (i - 1)));

        $("#papelera" + i).attr('id', ('papelera' + (i - 1)));
        $("#panel-body" + i).attr('id', ('panel-body' + (i - 1)));

        $("#preg" + i).attr('name', ('pregRes' + (i - 1) + '[title]'));
        $("#preg" + i).attr('data-idPreg', (i - 1));
        $("#preg" + i).attr('id', ('preg' + (i - 1)));

        $("#addRespuestaP" + i).attr('data-idPreg', (i - 1));
        $("#addRespuestaP" + i).attr('id', ('addRespuestaP' + (i - 1)));


        $("#panel-heading" + i).attr('id', ('panel-heading' + (i - 1)));

        $("#labelRes" + i + "_1").attr('id', ('labelRes' + (i - 1) + "_1"));

        z = 0;
        $('label[name="labelRes' + i + '"]').each(function () {

                z++;
                $(this).attr('id', ('labelRes' + (i - 1) + "_" + z));
            }
        );
        $('label[name="labelRes' + i + '"]').attr('name', ('labelRes' + (i - 1) ));


        $("#esCorrecta" + i + "_1").attr('id', ('esCorrecta' + (i - 1) + "_1"));


        z = 0;
        $('input[name="pregRes' + (i) + '[correcta]"]').each(function () {
            z++;
            $(this).attr('id', ('correcta' + (i - 1) + "_" + z));
        });
        $('input[name="pregRes' + (i) + '[correcta]"]').attr('name', ('pregRes' + (i - 1) + '[correcta]'));

        z = 0;

        $('textarea[name="pregRes' + (i) + '[res][]"]').each(function () {
            z++;

            $(this).attr('id', ('resp' + (i - 1) + "_" + z));
        });
        $('textarea[name="pregRes' + (i) + '[res][]"]').attr('name', ('pregRes' + (i - 1) + '[res][]'));


//addrespuesta
        z = 0;
        $('div[name="col-lg-1col-lg-push-1radiocontrol-label' + i + '"]').each(function () {
                z++;
                $(this).attr('id', ('col-lg-1col-lg-push-1radiocontrol-label' + (i - 1) + "_" + z));
            }
        );
        $('div[name="col-lg-1col-lg-push-1radiocontrol-label' + i + '"]').attr('name', ('col-lg-1col-lg-push-1radiocontrol-label' + (i - 1) ));


        $("#Acol-lg-12" + i).attr('id', ('Acol-lg-12' + (i - 1)));
        $("#container" + i + "_1").attr('id', ('container' + (i - 1) + "_1"));
        $("#row" + i + "_1").attr('id', ('row' + (i - 1) + "_1"));
        $("#form-group" + i + "_1").attr('id', ('form-group' + (i - 1) + "_1"));

        z = 0;
        $('div[name="col-lg-9col-lg-push-1control-label' + i + '"]').each(function () {
                z++;
                $(this).attr('id', ('col-lg-9col-lg-push-1control-label' + (i - 1) + "_" + z));
            }
        );
        $('div[name="col-lg-9col-lg-push-1control-label' + i + '"]').attr('name', ('col-lg-9col-lg-push-1control-label' + (i - 1) ));


        $("#col-lg-1col-lg-push-1" + i + "_1").attr('id', ('col-lg-1col-lg-push-1' + (i - 1) + "_1"));
        z = 0;
        $('div[name="col-lg-9col-lg-push-1' + i + '"]').each(function () {
                z++;
                $(this).attr('id', ('col-lg-9col-lg-push-1' + (i - 1) + "_" + z));
            }
        );
        $('div[name="col-lg-9col-lg-push-1' + i + '"]').attr('name', ('col-lg-9col-lg-push-1' + (i - 1) ));

        $("#AddCol-lg-9col-lg-push-1" + i).attr('id', ('AddCol-lg-9col-lg-push-1' + (i - 1)));


        $("#iconoPlus" + i).attr('id', ('iconoPlus' + (i - 1)));


    }
    idPreg--;
    gotoNewPreg();


}





