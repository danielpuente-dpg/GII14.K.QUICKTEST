<?php
require_once $_SERVER["DOCUMENT_ROOT"] . '/_QuickTest_TFG/app/model/Cuestionario_Model.php';
require_once $_SERVER["DOCUMENT_ROOT"] . '/_QuickTest_TFG/app/model/Preguntas_Model.php';
require_once $_SERVER["DOCUMENT_ROOT"] . '/_QuickTest_TFG/app/model/Respuestas_Model.php';
require_once $_SERVER["DOCUMENT_ROOT"] . '/_QuickTest_TFG/app/model/Usuarios_Model.php';

/**
 * @author Alejandro Martínez García <amg0098@alu.ubu.es>
 * @version 1.0.
 */

/**
 * Class Cuestionario_Gestionar_Controller.
 * Implementa un controlador.
 * Se comunica con el modelo
 * y la vista para gestionar la administración de la aplicación web.
 * Contiene diferentes funciones, usadas en base a la selección que realice el usuario.
 *
 */
class Cuestionario_Gestionar_Controller
{

    /**
     * Función que proporciona a la vista, los datos necesarios para
     * crear una pantalla en la que gestionar QuickTest.
     * @param $idAsig . Identificador de la asignatura.
     * @param $nombreAsig . Nombre de la asignatura.
     * @param $isProfesor . Indicador de si es o no profesor.
     * @param $nombreAlu . Nombre del usuario.
     * @param $apeAlu . Apellidos del usuario.
     * @param $idUser . Identificador del usuario.
     * @param $lang . Idioma en el que se sirve la página.
     * @param $emailLTI . Email que el usuario emplea en su plataforma LTI.
     * @param $ltiConsumerURL . URL de la plataforma LTI desde la que se ha accedido.
     * Invoca la vista Cuestionario_Gestionar_View.
     */
    // @codeCoverageIgnoreStart
    function cuestionario_Gestionar($idAsig, $nombreAsig, $isProfesor, $nombreAlu, $apeAlu, $idUser, $lang, $emailLTI, $ltiConsumerURL)
    {
        $cuestionarioModel = new Cuestionario_Model();
        $usuarios_Model = new Usuarios_Model();

        $datosLTI = $usuarios_Model->getUsuarioLTI_Email($emailLTI);
        $cuestAsig = $cuestionarioModel->getCuestionariosAsignatura($idAsig);

        return require_once($_SERVER["DOCUMENT_ROOT"] . "/_QuickTest_TFG/app/views/cuestionarioView/cuestionario_Gestionar_View.php");

    }

    /**
     * Función usada para que cuando el usuario clica sobre el botón de editar,
     * el cuestionario asociado a dicho botón, pueda ser editado.
     * @param $datos . Datos del cuestionario a editar.
     * @param $ltiConsumerURL . URL de la plataforma LTI desde la que se ha accedido.
     * Invoca la vista Cuestionario_Editar_View.
     */
    function editarCuestionario($datos, $ltiConsumerURL)
    {

        $cuestionarioModel = new Cuestionario_Model();
        $preguntasModel = new Preguntas_Model();
        $respuestasModel = new Respuestas_Model();

        $cuestionarios = $cuestionarioModel->getInfoCuestionarios($datos['data-idCuestEditar']);
        $preguntas = $preguntasModel->get_infoPreguntas($datos['data-idCuestEditar']);


        foreach ($preguntas as $p) {
            $respuestasPregunta[] = $respuestasModel->getRespuestas($p);
        }

        $countPreguntas = $preguntasModel->countPreguntasCuestionario($datos['data-idCuestEditar']);
        $idAsig = $datos['idAsig'];
        $nombreAsig = $datos['nombreAsig'];
        $isProfesor = 1;
        $nombreAlu = $datos['nombreAlu'];
        $apeAlu = $datos['apeAlu'];
        $idUser = $datos['idUser'];
        $isEditar = $datos ['isEditar'];
        $idCuestEditar = $datos['data-idCuestEditar'];
        $lang = $datos['lang'];

        require_once($_SERVER["DOCUMENT_ROOT"] . "/_QuickTest_TFG/app/views/cuestionarioView/cuestionario_Editar_View.php");
    }

    /**
     * Función usada para que cuando el usuario clica sobre el botón de editar,
     * el cuestionario asociado a dicho botón, pueda ser editado.
     * @param $datos . Datos del cuestionario a editar.
     * @param $ltiConsumerURL . URL de la plataforma LTI desde la que se ha accedido.
     * Invoca la vista Cuestionario_Editar_View.
     */
    function duplicarCuestionario($datos, $ltiConsumerURL)
    {

        $cuestionarioModel = new Cuestionario_Model();
        $preguntasModel = new Preguntas_Model();
        $respuestasModel = new Respuestas_Model();


        $cuestionarios = $cuestionarioModel->getInfoCuestionarios($datos['data-idCuestDuplicar']);
        $preguntas = $preguntasModel->get_infoPreguntas($datos['data-idCuestDuplicar']);

        foreach ($preguntas as $p) {
            $respuestasPregunta[] = $respuestasModel->getRespuestas($p);
        }

        $countPreguntas = $preguntasModel->countPreguntasCuestionario($datos['data-idCuestDuplicar']);
        $idAsig = $datos['idAsig'];
        $nombreAsig = $datos['nombreAsig'];
        $isProfesor = 1;
        $nombreAlu = $datos['nombreAlu'];
        $apeAlu = $datos['apeAlu'];
        $idUser = $datos['idUser'];
        $isDuplicar = $datos ['isDuplicar'];
        $idCuestDuplicar = $datos['data-idCuestDuplicar'];

        require_once($_SERVER["DOCUMENT_ROOT"] . "/_QuickTest_TFG/app/views/cuestionarioView/cuestionario_Duplicar_View.php");
    }
    // @codeCoverageIgnoreEnd

    //TESTED
    /**
     * Función usada para que cuando el usuario clica sobre el botón de eliminar un cuestionario,
     * el cuestionario asociado a dicho botón, pueda ser borrado.
     * @param $idCuestionario. id del cuestionario a borrar.
     * @return bool. Verdadero o falso si el cuestionario se ha borrado o no.
     */
    function borrarCuestionario($idCuestionario)
    {
        $cuestionarioModel = new Cuestionario_Model();
        $cuestBorrado = $cuestionarioModel->borrarCuestionario($idCuestionario);

        if (isset($cuestBorrado)) {
            echo json_encode(1);
            return true;
        }
        return false;
    }
}

?>