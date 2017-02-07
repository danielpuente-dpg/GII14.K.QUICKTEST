/**
 * @author Alejandro Martínez García <amg0098@alu.ubu.es>
 * @version 1.0.
 */

/**
 * Conjunto de funciones empleadas en la vista Cuestionario_Gestionar_View.php
 */

/**
 * Borra un cuestionario.
 * @param boton. botón pulsado.
 * @returns {number}. Si se ha borrado o no.
 */
function borrarCuestionario(boton) {

    idCuest = $(boton).attr('data-idCuest');
    var lang = $("#lang").val();

    if (lang == "es") {

        borrar = "El cuestionario se borrará definitivamente , ¿desea continuar?";
        error = "Error, no se ha borrado el cuestionario";
        elCuest = "El cuestionario ";
        borrado = " ha sido borrado.";


    } else {
        borrar = "The test will be deleted definitely. Would you continue?";
        error = "Error. The test is not not removed";
        elCuest = "The test ";
        borrado = " has been removed.";


    }
    var r = confirm(borrar);
    if (r == true) {


        $.ajax({
            type: 'POST',
            url: 'index.php',


            data: {

                idCuestionario: idCuest,
                borrarCuestionario: 'borrarCuestionario'

            },
            success: function (data) {


                if (data == 1) {
                    parpadearDesaparecer("#listCuest" + idCuest);
                } else {
                    parpadearDesaparecer("#listCuest" + idCuest);


                    alert(error);
                }


            }
        });

    }
}

/**
 * Hace submit al formulario.
 */

function subForm() {
    $('#formCrearNuevo').submit();


}
/**
 * Hace que un elemento del DOM, parpadee y luego desaparezca.
 * @param selector. elemento del DOM.
 */
function parpadearDesaparecer(selector) {

    var stopBlinking = false;
    setTimeout(function () {
        stopBlinking = true;
    }, 2000);

    blink(selector);

    function blink(selector) {
        $(selector).fadeOut('fast', function () {
            $(this).fadeIn('fast', function () {
                if (!stopBlinking) {
                    blink(this);
                }
                else {

                    $(this).remove();
                }
            });
        });
    }
}


/**
 * Hace que un elemento del DOM, parpadee.
 * @param selector. elemento del DOM.
 */
function parpadear(selector) {

    var stopBlinking = false;
    setTimeout(function () {
        stopBlinking = true;
    }, 2000);

    blink(selector);

    function blink(selector) {
        $(selector).fadeOut('fast', function () {
            $(this).fadeIn('fast', function () {
                if (!stopBlinking) {
                    blink(this);
                    location.reload();
                }
                else {

                    location.reload();
                }
            });
        });
    }
}

/**
 * Resetea el modal y lo borra, para que no reaparezca despues de cerrado.
 * @param boton. boton pulsado.
 */
function clickModal(boton) {


    idBoton = $(boton).attr('data-idCuest');

    $(this).click(function () {
        $("#myModal" + idBoton).modal({backdrop: true});
    });
    $('body').on('hidden.bs.modal', '.modal', function () {
        $(this).removeData('bs.modal');
    });


}

/**
 * Hace un scroll, hasta los cuestionarios creados.
 */
function gotoCuestionariosCreados() {

    $('html, body').animate({
        scrollTop: $("#goHere").offset().top
    }, 1000);

}








