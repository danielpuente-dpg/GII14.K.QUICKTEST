/**
 * @author Alejandro Martínez García <amg0098@alu.ubu.es>
 * @version 1.0.
 */

/**
 * Conjunto de funciones empleadas en la vista Cuestionario_Resolver_View.php
 */


$(document).ready(tamPantalla);

/*VARIABLES GLOBALES*/
var segundos = 15;
var milisegundos = 15000;
var milisegundosRapidos = 1500;

/**
 * Realiza una petición AJAX periodicamente, para comprobar si hay comodines.
 */
function autoRefresh() {

    // ACTIVAR ESTO PARA REFRESCO CONTINUADO
    var auto_refresh = setInterval(function () {

        hasComodinAmarillo();
        hasComodinVerde();

    }, 5000);

    /**
     * Comprueba comodines verdes mediante AJAX.
     */
    function hasComodinVerde() {

        $.ajax({
            type: 'POST',
            url: 'index.php',

            data: {

                hasComodinVerde: 'hasComodinVerde',
                idCuestLTI: $('#idCuestLTI').val()

            },


            dataType: 'json',
            success: function (data) {




                printComodinVerde(data);


            }
        });
    }

    /**
     * Comprueba comodines ámbar mediante AJAX.
     */
    function hasComodinAmarillo() {

        $.ajax({
            type: 'post',
            url: 'index.php',

            data: {

                hasComodinAmarillo: 'hasComodinAmarillo',
                idCuestLTI: $('#idCuestLTI').val()

            },

            dataType: 'json',
            success: function (data) { //este data es lo que devuelve la peticion ajax ($preguntasAmarilla)
                //alert(data);
                printComodinAmarillo(data);


            }
        });

    }

}


/**
 *Funcion que colorea en verde aquellas preguntas que poseen un comodín verde.
 *
 * @param preguntasVerde. recibe un arrayJSON con las preguntas que poseen un comodín
 *
 */
function printComodinVerde(preguntasVerde) {

    if (preguntasVerde != null) {

        for (var i = 0; i < preguntasVerde.length; i++) {

            var pregVerde = preguntasVerde[i]['pregunta_idPregunta'];

            //Hacemos que parpadeen al cambiar de amarillo o nada, a verde
            $('.btn-default').each(function () {
                if ($(this).attr('id') == pregVerde) {

                    if (($('.navbar-btn').length - 2) > "21") {
                        parpadear(barrasCaret, 3000);
                    }

                }

            });

            $('.btn-warning').each(function () {
                if ($(this).attr('id') == pregVerde) {

                    if (($('.navbar-btn').length - 2) > "21") {
                        parpadear(barrasCaret, 3000);
                    }
                }

            });
            document.getElementById(pregVerde).setAttribute("class", "btn btn-success navbar-btn");

        }

    }

}

/**
 *Funcion que colorea en ámbar aquellas preguntas que poseen un comodín ámbar.
 *
 * @param preguntasAmarilloJSON. recibe un arrayJSON con las preguntas que poseen un comodín
 *
 */
function printComodinAmarillo(preguntasAmarillo) {

    if (preguntasAmarillo != null) {

        for (var i = 0; i < preguntasAmarillo.length; i++) {

            var pregAmarilla = preguntasAmarillo[i]['pregunta_idPregunta'];
            //Hacemos que parpadeen al cambiar de amarillo o nada, a verde
            $('.btn-default').each(function () {
                if ($(this).attr('id') == pregAmarilla) {
                         if (($('.navbar-btn').length - 2) > "21") {
                        parpadear(barrasCaret, 3000);
                    }
                }

            });

            $('.btn-success').each(function () {
                if ($(this).attr('id') == pregAmarilla) {

                    if (($('.navbar-btn').length - 2) > "21") {
                        parpadear(barrasCaret, 3000);
                    }
                }

            });


            document.getElementById(pregAmarilla).setAttribute("class", "btn btn-warning navbar-btn");
        }

    } else {

    }

}

/**
 * Muestra por pantalla una cuenta atrás.
 */
function cuentaAtras() {
    // Initializing timer variable.
    var x = segundos;
    var y = document.getElementById("timer");
    // Display count down for 20 seconds
    setInterval(function () {
        if (x <= segundos + 1 && x >= 1) {
            x--;
            y.innerHTML = '' + x + '';
            if (x == 1) {
                x = segundos;
            }
        }
    }, 1000);

    }

/**
 *Funcion que oculta el botón collapsible
 */
function hideContent() {
    $("#showContentButton2").hide();
}


/**
 *Funcion que "desvanece" lentamente  el saludo de Bienvenida
 */
function ocultarSaludo() {
    setTimeout(function () {

        $("#welcomeMessage").fadeOut(3000);
        $("#welcomeMessage2").fadeOut(3000);
        $("#timer").fadeOut(3000);

    }, milisegundos - 3000);
}

/**
 * Funcion que elimina del DOM los elementos del saludo
 */
function suprimirSaludoSinResolver() {
    setTimeout(function () {

        $("#welcomeMessage").remove();
        $("#welcomeMessage2").remove();
        $("#timer").remove();

    }, milisegundos + 3000);
}

/**
 * Funcion que elimina del DOM los elementos del saludo
 */
function suprimirSaludoResuelto() {


    $("#welcomeMessage").remove();
    $("#welcomeMessage2").remove();
    $("#timer").remove();
}

/**
 *Funcion que despliega (colapsable) el cuestionario
 */
function mostrarCuestionario() {
    setTimeout(function () {
        document.getElementById("showContentButton2").click();

    }, milisegundos - 100); //NORMAL
    //}, milisegundosRapidos - 100); // VERSION RAPIDA
}

/**
 * Muestra u oculta los resultados.
 */
$(document).ready(function () {

    $().ajaxStart(function () {
        $('#loading').show();
        $('#result').hide();
    }).ajaxStop(function () {
        $('#loading').hide();
        $('#result').fadeIn('slow');

    });
});

/**
 * Desactiva el botón de grabar cada respuesta cuando ya ha sido pulsado.
 */
function disableButtonSaveAnswer(enlace) {

    $('#form, #fat, #myForm').submit(function () {
        enlace.disabled = 'disabled';
    });
}

/**
 * Desactiva todos los botones.
 */
function desactivarTodosBotones() {

    $('button[name=ButtonSaveAnswer]').prop('disabled', true);
}


/*
 * desactiva las opciones del radio button después de pulsar el botón de guardar
 */
function disableRadioButtons() {

    $('#form, #fat, #myForm').submit(function () {
        var pregID = $(this).data("preguntaid");
        $("input[name=radioAnswer" + pregID + "]").attr("disabled", true);

    });
}

/**
 *Evita que cambie de pagina al grabar respuesta.
 */
function saveAnswerNoRefresh() {

    // Interceptamos el evento submit
    $('#form, #fat, #myForm').submit(function () {

        var pregID = $(this).data("preguntaid");
        var tipoComUsado = $("input[name=radioAnswer" + pregID + "]:checked").attr('class');
        var respElegida = $("input[name=radioAnswer" + pregID + "]:checked").val();

        // Enviamos el formulario usando AJAX
        $.ajax({
            type: 'POST',
            url: 'index.php',

            data: {
                idUser: $("#idUser").val(),

                tipoComUsado: tipoComUsado,
                respuestaElegida: respElegida,
                preguntaResuelta: pregID

            },
            // Mostramos un mensaje con la respuesta de PHP
            success: function (data) {

            }
        });
        return false;
    });

}


/**
 *Se activa al pulsar el botón de Cerrar y enviar para calificación.
 * Comprueba si quedan preguntas por responder
 *        Si quedan: pregunta si desea continuar
 *            Elige SI: continua respondiendo
 *            Elige NO: se envia para calificar
 *        Si no quedan: se envia para calificar
 */
function checkEmptyAnswers() {

    cuantos = $('*[name="ButtonSaveAnswer"]').not(':disabled').length;

    if (cuantos > 0) {// queda alguna sin responder,
        var r = confirm("Quedan preguntas sin responder ¿Desea cerrar y enviar para calificación?");
        if (r == true) {
            saveGrades();
            finalizarCuestionario();

        } else {



        }
    } else {
        var r2 = confirm("Gracias por participar, el cuestionario ha finalizado, su calificación va a ser guardada");
        if (r2 == true) {
            finalizarCuestionario();
            saveGrades();


        } else {

        }
    }

}

/**
 * Realiza una petición AJAX, para comprobar si hay nuevos comodines.
 * @param buttonPreg. botón pulsado.
 */
function printSolution(buttonPreg) {
   
    $.ajax({
        type: 'POST',
        url: 'index.php',
        data: {

            hasComodinAmarillo: 'hasComodinAmarillo',
            idCuestLTI: $('#idCuestLTI').val()

        },
        success: function (data) {

            printSolAmarillo(buttonPreg, data);

        }
    });

    $.ajax({
        type: 'POST',
        url: 'index.php',
        data: {

            hasComodinVerde: 'hasComodinVerde',
            idCuestLTI: $('#idCuestLTI').val()

        },
        success: function (data) {//este data es lo que devuelve la peticion ajax ($preguntasVerde)

            printSolVerde(buttonPreg, data);

        }
    });
}

/**
 * Colorea la respuesta perteneciente a la pregunta, sobre la que se ha pedido el comodín.
 * @param buttonPreg. botón pulsado.
 */
function printSolAmarillo(buttonPreg, preguntasAmarillo) {
    preguntasAmarillo = JSON.parse(preguntasAmarillo);
    buttonPreg.disabled = 'disabled';
    var idPregNavBar = buttonPreg.id;

    if (preguntasAmarillo != null) {

        for (var i = 0; i < preguntasAmarillo.length; i++) {

            if (preguntasAmarillo[i]['pregunta_idPregunta'] == idPregNavBar) {

                var idRespPintar = preguntasAmarillo[i]['idRespuesta'];
                var solution = "textoPregunta" + idRespPintar;
                var radioSol = "optionRadio" + idRespPintar;

                $('#' + radioSol).addClass("bg-warning"); //el radio button
                $('#' + solution).addClass("bg-warning"); // el texto que acompaña al radio button

            }
        }
    }


}
/**
 * Colorea la respuesta perteneciente a la pregunta, sobre la que se ha pedido el comodín.
 * @param buttonPreg. botón pulsado.
 */
function printSolVerde(buttonPreg, preguntasVerde) {

    preguntasVerde = JSON.parse(preguntasVerde);
    buttonPreg.disabled = 'disabled';
    var idPregNavBar = buttonPreg.id;

    if (preguntasVerde != null) {

        for (var i = 0; i < preguntasVerde.length; i++) {
            if (preguntasVerde[i]['pregunta_idPregunta'] == idPregNavBar) {

                var idRespPintar = preguntasVerde[i]['idRespuesta'];
                var solution = "textoPregunta" + idRespPintar;
                var radioSol = "optionRadio" + idRespPintar;
                $('#' + solution).addClass("bg-success"); //el radio button
                $('#' + radioSol).addClass("bg-success"); // el texto que acompaña al radio button
                break;
            }
        }
    }


}


/**
 * al pulsar sobre el boton del navbar, hace scroll hasta la pregunta correspondiente
 * @param buttonPreg
 */
function gotoSolution(buttonPreg) {

    idPregNavBar = $(buttonPreg).attr('data-valor');

    if (idPregNavBar == 0) {
        var solutionGoTo = "#activityName";
    } else {
        solutionGoTo = "#linea" + idPregNavBar;
    }

    $('html, body').animate({
        scrollTop: $(solutionGoTo).offset().top
    }, 1000);

}


/**
 * Redirige a la pagina que calcula la nota y la envía para calificación.
 */
function saveGrades() {


    $.ajax({
        type: 'POST',
        url: 'index.php',


        data: {

            saveGradeMoodle: 'saveGradeMoodle',
            idCuestLTI: $('#idCuestLTI').val(),
            idUser: $('#idUser').val(),
            idAsig: $('#idAsig').val()

        },


        success: function (data) {
            cargarResultados(data);


            $("#showContentButton").show();


            $("#showContentButton").click();




        }
    });


}

/**
 * Desactiva los botones y muestra los resultados, cuando se ha finalizado el cuestionario.
 */
function finalizarCuestionario() {
    $('button[name=buttonCalificarCuestionario]').hide();
    $("#buttonCalificarCuest").hide();

    desactivarTodosBotones();
    $("#showContentButton2").click();

}


/**
 * añade 2 espacios a los botones menores que 10
 */
function buttonsLess10() {

    for (i = 0; i < 10; i++) {

        $('*[data-valor=' + i + ']').prepend('&nbsp;');
        $('*[data-valor=' + i + ']').append('&nbsp;');

    }
}

/**
 * Muestra en la página elementos nuevos, si en lugar de un alumno, es un profesor el que la visualiza.
 */
function modeProfesor() {

    esProfesor = $('#isProfesor').val();
    var lang = $("#lang").val();
    id = $("#cuantasPreg").val();
    ultimaPregunta = $('*[data-valor=' + id + ']').attr("id");

    if (lang == "es") {
        vistaModo = "Vista profesor"

    } else {

        vistaModo = "Teacher's view"
    }

    jQuery('<span/>', {
        id: 'avisoModoProfesor',
        class: 'label label-danger col-lg-push-2 navbar-text  ',
        text: vistaModo

    }).insertAfter("#" + ultimaPregunta);

    if (($('.navbar-btn').length - 1) >= 20 || tamPantalla()) {

        $('#avisoModoProfesor').remove();
        jQuery('<span/>', {
            id: 'avisoModoProfesor',
            class: 'label label-danger col-lg-push-2 navbar-text  ',
            text: vistaModo

        }).insertAfter("#logo");
        $('#logo').remove();

    }

}

/**
 * Detecta el tamaño de la pantalla.
 * @returns {boolean}. True si es menor de 770px. False si no.
 */
function tamPantalla() {

    if ($(window).width() < 770) {

        $("#logo").hide();

        if($(window).width() < 340){
            $("#botonLogout").hide();
        }

        return true;

    } else {

        return false;
    }
}

/**
 * Hace que un elemento del DOM parpadea durante un intervalo de tiempo.
 * @param selector. elemento del DOM
 * @param tiempo. intervalo de tiempo.
 */
function parpadear(selector, tiempo) {

    var stopBlinking = false;
    setTimeout(function () {
        stopBlinking = true;
    }, tiempo);

    blink(selector);

    function blink(selector) {
        $(selector).fadeOut('slow', function () {
            $(this).fadeIn('slow', function () {
                if (!stopBlinking) {
                    blink(this);
                }
                else {
                }
            });
        });
    }
}


$(document).ready(encapsularBotones);

/**
 * Encapsula los botones en un dropdown, si hay muchos.
 */
function encapsularBotones() {


    if (($('.navbar-btn').length - 1) >= 21 || tamPantalla()) {


        jQuery('<h4/>', {
            id: 'iconBarras',
            class: "glyphicon glyphicon-align-justify",
            style: 'top: -6px; '


        }).insertAfter("#mayor21");

        jQuery('<h4/>', {
            id: 'iconCaret',
            class: "caret"

        }).insertAfter("#iconBarras");

        $("#iconBarras, #iconCaret").wrapAll("<button id='barrasCaret'   class=' pull-right btn btn-primary   dropdown-toggle navbar-btn' type= 'button'  id= 'menu1'   data-toggle= 'dropdown' > </button>");
        $("#mayor21").wrapAll("<li id='ulbtngroup' class='dropdown-menu'  role='menu' aria-labelledby='menu1' ></li>");
        $("#barrasCaret, #ulbtngroup").wrapAll("<ul   class='dropdown-menu-left pull-right ' > </ul>");

        parpadear(barrasCaret, 2000);
    }


}

$(document).ready(checkSegundoIntento);
/**
 * Verifica si es la primera vez que accede al cuestionario.
 */
function checkSegundoIntento() {

    $.ajax({
        type: 'POST',
        url: 'index.php',

        data: {

            checkSegundoIntento: 'checkSegundoIntento',
            idCuestLTI: $('#idCuestLTI').val(),
            idUser: $("#idUser").val()

        },


        dataType: 'json',
        success: function (data) {

            desactivarSegundoIntento(data);

        }
    });


}

/**
 *Desactiva las preguntas ya contestadas.
 */
function desactivarSegundoIntento(pregYaRespondidas) {
    for (var i = 0; i < pregYaRespondidas.length; i++) {

        var pregID = pregYaRespondidas[i];

        $("input[name=radioAnswer" + pregID + "]").attr("disabled", true);
        $('*[data-textoPregunta=' + pregID + ']').attr("class", "text-muted");
        $("div[id=enunciado" + pregID + "]").attr("class", "text-muted");
        $('*[data-botonGuardar=' + pregID + ']').attr("disabled", true);


    }
}

$(document).ready(estaResuelto);
/**
 * Comprueba si el cuestionario está resuelto, y oculta o muestra elementos en función de ello.
 */
function estaResuelto() {

    if ($("#estaResuelto").val() == 1) {

        $("#buttonCalificarCuest").hide();
        $("#showContentButton2").hide();

        buttonsLess10();
        suprimirSaludoResuelto();
        mostrarResultados();
        desactivarTodosBotones();
        $(".btn-success").hide();
        $("buttonCalificarCuestionario").hide();


    } else {
        if ($("#isProfesor").val() == 0) {

            buttonsLess10();
            mostrarCuestionario();
            cuentaAtras();
            suprimirSaludoSinResolver();
            autoRefresh();
            saveAnswerNoRefresh();
            disableRadioButtons();
            $("#showContentButton2").hide();
            ocultarSaludo();
            hideContent();

        } else { //es profesor

            // modeProfesor();
            vistaProfesor();
            $("#showContentButton2").hide();
            $(".btn-success").attr("disabled", "true");

        }

    }
    $("#postComodines").remove();
}


/**
 * Comprueba si el usuario es un profesor y oculta o muestra información.
 */
function vistaProfesor() {
    if ($("#isProfesor").val() == 1) {
        modeProfesor();

        $("#buttonCalificarCuest").hide();
        $("#showContentButton2").hide();
        $("#showContentButton2").click();
        buttonsLess10();
        suprimirSaludoResuelto();
       desactivarTodosBotones();

    }
}

/**
 * Realiza una petición AJAX, para recoger los resultados.
 */
function mostrarResultados() {

    $.ajax({
        type: 'POST',
        url: 'index.php',


        data: {

            idCuestLTI: $('#idCuestLTI').val(),
            idUser: $('#idUser').val(),
            idAsig: $('#idAsig').val(),
            mostrarResultados: 'mostrarResultados'

        },

        success: function (data) {
            cargarResultados(data);
            $("#showContentButton").show();


        }
    });

}

/**
 * Dibuja los resultados dentro de un div.
 * @param data. resultados.
 */
function cargarResultados(data) {

    result = JSON.parse(data);
    $("#divResultados").load('../../../../_QuickTest_TFG/app/views/default/sections/m_resultados.php', {


        cSin: result['cSin'],

        cAmarillo: result['cAmarillo'],
        cVerde: result['cVerde'],
        cPreguntasRespondidas: result['cPreguntasRespondidas'],

        puntAmarillo: result['puntAmarillo'],
        puntVerde: result['puntVerde'],
        puntSin: result['puntSin'],

        aciertoAmarillo: result['aciertoAmarillo'],
        aciertoVerde: result['aciertoVerde'],
        aciertoSin: result['aciertoSin'],

        fallosAmarillo: result['fallosAmarillo'],
        fallosVerde: result['fallosVerde'],
        fallosSin: result['fallosSin'],

        puntTotal: result['puntTotal'],
        puntMaxPosible: result['puntMaxPosible'],
        totalPreguntas: result['totalPreguntas'],

        orden: result['orden'],
        mensaje: result['mensaje'],
        tipoPanel: result['tipoPanel'],
        premio: result['premio'],
        tipoText: result['tipoText'],
        ordenRespuesta: result['ordenRespuesta']


    }, function () {

        parpadear("#premioTable", 10000);
        parpadear("#premioPanel", 10000);


    });

    $("#explicComodines").load('../../../../_QuickTest_TFG/app/views/default/sections/m_explicacion_Comodines.php');
    $("#explicOrden").load('../../../../_QuickTest_TFG/app/views/default/sections/m_explicacion_Orden.php');


}

/**
 * Carga la sección en la que se explican los comodines.
 */
function gotoComodinesExplicacion() {

    $('html, body').animate({
        scrollTop: $("#comodinesExplicacion").offset().top
    }, 1000);

}

/**
 * Carga la sección en la que se explican el orden en la respuesta.
 */
function gotoOrdenExplicacion() {


    $('html, body').animate({
        scrollTop: $("#ordenExplicacion").offset().top
    }, 1000);

}

