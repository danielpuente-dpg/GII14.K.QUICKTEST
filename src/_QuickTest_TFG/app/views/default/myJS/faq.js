/**
 * @author Alejandro Martínez García <amg0098@alu.ubu.es>
 * @version 1.0.
 */

/**
 * Conjunto de funciones empleadas en la vista FAQ_View.php
 */

$(document).ready(cargarPasos);
$(document).ready(cargarTiposComodin);

/**
 * Carga en un div la seccion con la ayuda para publicar un cuestionario.
 */
function cargarPasos() {

    $("#divPasosPublicar").load('../../../../_QuickTest_TFG/app/views/default/modules/pasosParaPublicar.php');

}

/**
 * Carga en un div la seccion con la ayuda con los tipos de comodín.
 */
function cargarTiposComodin() {

    $("#explicComodines").load('../../../../_QuickTest_TFG/app/views/default/sections/m_explicacion_Comodines.php');

    $('#colapseComodines').on('shown.bs.collapse', function () {
        borrarElementos();
    });
    borrarElementos();

}

/**
 * Carga en un div la seccion con la ayuda para el orden de explicación.
 */
function cargarOrdenExplicacion() {

    $("#explicOrden").load('../../../../_QuickTest_TFG/app/views/default/sections/m_explicacion_Orden.php');
    $("#idRow").attr("class", "col-lg-pull-1");
    $('#colapseComodines').on('shown.bs.collapse', function () {
        borrarElementos();
    });
    borrarElementos();
    $("#idRow").attr("class", "col-lg-pull-1");

}

/**
 * Borra elementos cargados, que no se desean ver.
 */
function borrarElementos() {

    setTimeout(function () {

        $("#linea").hide();
        $("#volverInicio").hide();
        $("#lineaFinal").hide();

        $("#linea2").hide();
        $("#volverInicio2").hide();
        $("#lineaFinal2").hide();

    }, 200);
}