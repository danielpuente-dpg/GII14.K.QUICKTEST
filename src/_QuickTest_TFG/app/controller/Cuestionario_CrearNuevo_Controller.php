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
 * Class Cuestionario_CrearNuevo_Controller.
 * Implementa el controlador.
 * Se comunica con el modelo
 * y la vista para crear un nuevo cuestionario.
 *
 */
class Cuestionario_CrearNuevo_Controller
{
    // @codeCoverageIgnoreStart
    /**
     * Función que crea un nuevo cuestionario,
     * empleando los datos que le llegan desde un formulario.
     * @param $datos .Datos con los que se crea el nuevo cuestionario.
     * @return bool $creado. Verdadero o falso si el cuestionario se ha creado o no.
     */
    function cuestionario_CrearNuevo($datos)
    {

        $cuestionarioModel = new Cuestionario_Model();
        $preguntasModel = new Preguntas_Model();
        $respuestasModel = new Respuestas_Model();

        $creado = false;

        if (isset ($datos['isEditar']) && $datos['isEditar'] == 1) {//si voy a editar

            $cuestionarioModel->borrarCuestionario($datos['idCuestEditar']);

            $cuestionarioModel->createCuestionarioEditado(
                $datos['idCuestEditar'], $datos['cuestTitulo'], $datos['idAsig'], $datos['nombreAsig'],
                $datos['correctorVerdeTrue'], $datos['correctorVerdeFalse'],
                $datos['correctorAmarilloTrue'], $datos['correctorAmarilloFalse']
            );
            $creado = true;


            $idCuestionario = $datos['idCuestEditar'];

        } else {


            $idCuestionario = $cuestionarioModel->createCuestionario($datos['cuestTitulo'], $datos['idAsig'], $datos['nombreAsig'],
                $datos['correctorVerdeTrue'], $datos['correctorVerdeFalse'],
                $datos['correctorAmarilloTrue'], $datos['correctorAmarilloFalse']
            );
            $creado = true;


        }

        $isProfesor = $datos['isProfesor'];
        $apeAlu = $datos['apeAlu'];
        $nombreAlu = $datos['nombreAlu'];
        $idUser = $datos['idUser'];
        $idAsig = $datos['idAsig'];
        $nombreAsig = $datos['nombreAsig'];
        $lang = $datos['lang'];
        $emailLTI = $datos['emailLTI'];
        $ltiConsumerURL = $datos['launch_presentation_return_url'];

        $datos = $this->unsetVariables($datos);

        foreach ($datos as $preg) {

            $idPregunta = $preguntasModel->createPregunta($preg['title'], $idCuestionario, $preg['pmax']);
            $posCorr = 1;

            foreach ($preg['res'] as $resVal) {

                if ($posCorr == $preg['correcta']) {

                    $idRespuesta = $respuestasModel->createRespuestas($idPregunta, $resVal, 1);

                } else {

                    $idRespuesta = $respuestasModel->createRespuestas($idPregunta, $resVal, 0);

                }
                $posCorr++;

            }

        }
        $cuestionario_Gestionar_Controller = new Cuestionario_Gestionar_Controller();
        $cuestionario_Gestionar_Controller->cuestionario_Gestionar($idAsig, $nombreAsig, $isProfesor, $nombreAlu, $apeAlu, $idUser, $lang, $emailLTI, $ltiConsumerURL);
        return $creado;

    }
    // @codeCoverageIgnoreEnd


//TESTED
    /**
     * Función unsetVariables.
     * Elimina del array de datos, aquellos que ya no son necesarios.
     * @test asdfasdf
     * @param $datos .Datos con los que se crea el nuevo cuestionario.
     * @return bool $flag. Verdadero o falso si el dato ha sido eliminado.
     *
     */
    function unsetVariables($datos)
    {
        $flag = false;
        if (isset ($datos['launch_presentation_return_url'])) {
            unset($datos['launch_presentation_return_url']);
            $flag = true;

        }
        if (isset ($datos['emailLTI'])) {
            unset($datos['emailLTI']);
            $flag = true;

        }
        if (isset ($datos['lang'])) {
            unset($datos['lang']);
            $flag = true;

        }
        if (isset ($datos['idCuestDuplicar'])) {
            unset($datos['idCuestDuplicar']);
            $flag = true;

        }

        if (isset ($datos['idCuestEditar'])) {
            unset($datos['idCuestEditar']);
            $flag = true;

        }
        if (isset ($datos['isEditar'])) {
            unset($datos['isEditar']);
            $flag = true;

        }
        if (isset ($datos['isDuplicar'])) {
            unset($datos['isDuplicar']);
            $flag = true;

        }
        if (isset ($datos['isDuplicar'])) {
            unset($datos['isDuplicar']);
            $flag = true;

        }
        if (isset ($datos['cuestTitulo'])) {
            unset($datos['cuestTitulo']);
            $flag = true;

        }
        if (isset ($datos['isProfesor'])) {
            unset($datos['isProfesor']);
            $flag = true;

        }
        if (isset ($datos['apeAlu'])) {
            unset($datos['apeAlu']);
            $flag = true;

        }
        if (isset ($datos['nombreAlu'])) {
            unset($datos['nombreAlu']);
            $flag = true;

        }
        if (isset ($datos['idUser'])) {
            unset($datos['idUser']);
            $flag = true;

        }
        if (isset ($datos['idAsig'])) {
            unset($datos['idAsig']);
            $flag = true;

        }
        if (isset ($datos['nombreAsig'])) {
            unset($datos['nombreAsig']);
            $flag = true;

        }
        if (isset ($datos['correctorVerdeTrue'])) {
            unset($datos['correctorVerdeTrue']);
            $flag = true;

        }
        if (isset ($datos['correctorVerdeFalse'])) {
            unset($datos['correctorVerdeFalse']);
            $flag = true;

        }
        if (isset ($datos['correctorAmarilloTrue'])) {
            unset($datos['correctorAmarilloTrue']);
            $flag = true;

        }
        if (isset ($datos['correctorAmarilloFalse'])) {
            unset($datos['correctorAmarilloFalse']);
            $flag = true;


        }
        if (isset ($datos['countPreguntas'])) {
            unset($datos['countPreguntas']);
            $flag = true;

        }
        return $datos;
    }


}

?>