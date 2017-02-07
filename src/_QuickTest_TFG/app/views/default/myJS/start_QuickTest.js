/**
 * @author Alejandro Martínez García <amg0098@alu.ubu.es>
 * @version 1.0.
 */

/**
 * Conjunto de funciones empleadas en la vista start_QuickTest_View.php
 */

$(document).ready(loginQuickTest);
$(document).ready(registrarQuickTest);
$(document).ready(carousel());

/**
 * Valida y envía el formulario login.
 */
function loginQuickTest() {

    $('#formLoginUsuarioLTI').validate({

        rules: {

            emailUsuario: {
                required: true
            },
            passwordUsuario: {
                required: true,
                minlength: 5,
                maxlength: 15

            }
        },
        messages: {},
        highlight: function (element) {
            $(element).closest('.control-group').removeClass('has-success');
            $(element).closest('.control-group').addClass('has-error');


        },
        unhighlight: function (element) {
            $(element).closest('.control-group').removeClass('has-error');
            $(element).closest('.control-group').addClass('has-success');
        },
        errorElement: 'span',
        errorClass: 'help-block',
        errorPlacement: function (error, element) {
            if (element.parent('.input-group').length) {
                error.insertAfter(element.parent());
            } else {
                error.insertAfter(element);
            }
        },
        submitHandler: function (form) {
            $.ajax({
                type: "POST",
                url: '../../../index.php',
                data: {

                    login: 'login',
                    emailUsuario: $("#emailUsuario").val(),
                    passwordUsuario: $("#passwordUsuario").val()


                },
                success: function (data) {
                    if (data == 0) {
                       loginIncorrecto();

                    } else {


                        bienvenidaLogin(data);
                    }
                }
            });
        }
    });

}
/**
 * Valida y envía el formulario registro.
 */
function registrarQuickTest() {


    $('#formRegisterUsuarioLTI').validate({
        rules: {
            nuevoEmailUsuario: {

                required: true

            },
            nuevoPasswordUsuario: {
                minlength: 5,
                maxlength: 15,
                required: true

            },
            confirmNuevoPasswordUsuario: {
                required: true,
                equalTo: "#nuevoPasswordUsuario"

            }

        },
        messages: {},
        highlight: function (element) {
            $(element).closest('.control-group').removeClass('has-success');
            $(element).closest('.control-group').addClass('has-error');


        },
        unhighlight: function (element) {
            $(element).closest('.control-group').removeClass('has-error');
            $(element).closest('.control-group').addClass('has-success');
        },
        errorElement: 'span',
        errorClass: 'help-block',
        errorPlacement: function (error, element) {
            if (element.parent('.input-group').length) {
                error.insertAfter(element.parent());
            } else {
                error.insertAfter(element);
            }
        },
        submitHandler: function (form) {
            $.ajax({
                type: "POST",
                url: '../../../index.php',
                data: {

                    registrar: 'registrar',
                    nuevoEmailUsuario: $("#nuevoEmailUsuario").val(),
                    nuevoPasswordUsuario: $("#nuevoPasswordUsuario").val()


                },
                success: function (data) {


                    if (data == 0) {

                        registroIncorrecto();

                    } else {


                        bienvenidaRegistro(data);
                    }
                }
            });
        }

    });


}
jQuery.extend(jQuery.validator.messages, {
    required: "Obligatorio.",

    email: "Email no válido.",
    equalTo: "Contraseña no coincide",
    minlength: "5 a 15 caracteres.",
    maxlength: "5 a 15 caracteres"
});
/**
 * Informa de que el las credenciales son erroneas.
 */
function loginIncorrecto() {


    msgError = "La dirección de correo electrónico y la contraseña que has introducido no coinciden.";
    if ($('#errorMessage').length > 0) {

        $('#errorMessage').remove();


    }
    jQuery('<h10/>', {
        id: "errorMessage",
        class: "text-danger ",
        text: msgError,
        style: "font-size:small; font-style: italic;"
    }).insertAfter('#errorLogin');

}

/**
 * Dibuja una nueva pantalla, tras superar el login.
 * @param data. datos.
 */
function bienvenidaLogin(data) {


    $("#botonCerrar").click();
    $('#contenidoLogin').fadeOut(1000);
    $('#contenido').remove();

    ltiInfo = JSON.parse(data);

    consumer_key = ltiInfo['consumer_key'];
    link = ltiInfo['resource_link'];


    $("#contenidoDespuesLogin").load('../../../app/views/default/sections/m.logued.php', {
            consumer_key: ltiInfo['consumer_key'],
            resource_link: ltiInfo['resource_link'],
            loginRegistro: 0
        }, function () {

            parpadear(panelPrimerosPasos);


        }
    );


}

/**
 * Informa de que el registro no se ha producido.
  */
function registroIncorrecto() {

    msgError = "La dirección de correo electrónico ya existe.";

    jQuery('<h10/>', {
        id: "errorRegistro",
        class: "text-danger ",
        text: msgError,
        style: "font-size:small; font-style: italic;"
    }).insertAfter('#errorRegistro');

    if ($('#errorRegistro').length > 0) {

        $('#errorRegistro').remove();

    }
}

/**
 * Dibuja una nueva pantalla, tras registrarse.
 * @param data. datos.
 */
function bienvenidaRegistro(data) {

    $("#botonCerrar").click();
    $('#contenidoLogin').fadeOut(1000);
    $('#contenido').remove();

    ltiInfo = JSON.parse(data);

    consumer_key = ltiInfo['consumer_key'];

    link = ltiInfo['resource_link'];


    $("#contenidoDespuesLogin").load('../../../app/views/default/sections/m.logued.php', {
        consumer_key: ltiInfo['consumer_key'],
        resource_link: ltiInfo['resource_link'],
        loginRegistro: 1
    }, function () {

        parpadear(panelPrimerosPasos);


    });

}

/**
 * Fija el tiempo que permanece una imagen ene el carousel.
 */
function carousel() {

    $('.carousel').carousel({
        interval: 5000 //changes the speed
    });


}


/**
 * Hace que un elemento del DOM parpadee.
 * @param selector. elemento del DOM
 */
function parpadear(selector) {

    var stopBlinking = false;
    setTimeout(function () {
        stopBlinking = true;
    }, 2000);

    blink(selector);

    function blink(selector) {
        $(selector).fadeOut('slow', function () {
            $(this).fadeIn('slow', function () {
                if (!stopBlinking) {
                    blink(this);

                }

            });
        });
    }
}

