<?php

session_start();
require_once($_SERVER["DOCUMENT_ROOT"] . "/_QuickTest_TFG/app/controller/Cuestionario_Resolver_Controller.php");
require_once($_SERVER["DOCUMENT_ROOT"] . "/_QuickTest_TFG/app/controller/LTI_Controller.php");
require_once($_SERVER["DOCUMENT_ROOT"] . "/_QuickTest_TFG/app/controller/Cuestionario_Gestionar_Controller.php");
require_once($_SERVER["DOCUMENT_ROOT"] . "/_QuickTest_TFG/app/controller/Cuestionario_CrearNuevo_Controller.php");
require_once($_SERVER["DOCUMENT_ROOT"] . "/_QuickTest_TFG/app/controller/Usuarios_Controller.php");

/**
 * @author Alejandro Martínez García <amg0098@alu.ubu.es>
 * @version 1.0.
 */

/**
 * Index, recibe peticiones AJAX desde las vistas, e invoca métodos del controlador.
 */


$cuestionario_Resolver_Controller = new Cuestionario_Resolver_Controller();
$cuestionario_Gestionar_Controller = new Cuestionario_Gestionar_Controller();
$cuestionario_CrearNuevo_Controller = new Cuestionario_CrearNuevo_Controller();
$lti_controller = new LTI_Controller();

$contextValid = false;


$lang = $lti_controller->getLanguage();
$_SESSION['lang'] = $lang;

//internacionalización
if ($_SESSION['lang'] == "es" || isset($_POST['registrar']) || isset($_POST['login']) ) {

     index();

} else if ($_SESSION['lang'] == "en" || isset($_POST['registrar']) || isset($_POST['login']) ) {

    require_once($_SERVER["DOCUMENT_ROOT"] . "/_QuickTest_TFG/locale/traductor.php");

    index();

}else{//verificar acceso

        index();
}



function index()
{

    $cuestionario_Controller = new Cuestionario_Resolver_Controller();
    $cuestionario_Gestionar_Controller = new Cuestionario_Gestionar_Controller();
    $cuestionario_CrearNuevo_Controller = new Cuestionario_CrearNuevo_Controller();

    if (isset($_POST['checkSegundoIntento'])) {

        $cuestionario_Controller->checkSegundoIntento($_POST['idCuestLTI'], $_POST['idUser']);


    } else if (isset($_POST['registrar'])) {

        $usuarios_Controller = new Usuarios_Controller();
        $usuarios_Controller->registrarNuevoUsuario($_POST['nuevoEmailUsuario'], $_POST['nuevoPasswordUsuario']);

    } else if (isset($_POST['login'])) {

        $usuarios_Controller = new Usuarios_Controller();
        $usuarios_Controller->login($_POST['emailUsuario'], $_POST['passwordUsuario']);

    } else if (isset($_GET['data-idCuestEditar'])) { //si edito un  cuestionario

        $cuestionario_Gestionar_Controller->editarCuestionario($_GET, $_SESSION['ltiConsumerURL']);

    } else if (isset($_GET['data-idCuestDuplicar'])) { //si duplico un  cuestionario

        $cuestionario_Gestionar_Controller->duplicarCuestionario($_GET,  $_SESSION['ltiConsumerURL']);

    } else if (isset($_POST['borrarCuestionario'])) { //si borro un  cuestionario

        $cuestionario_Gestionar_Controller->borrarCuestionario($_POST['idCuestionario']);

    } else if (isset($_POST['cuestTitulo'])) {//si creo un nuevo cuestionario

        $cuestionario_CrearNuevo_Controller->cuestionario_CrearNuevo($_POST);

    } else if (isset ($_GET['data-idCuestVisualizar'])) {//si selecciono un cuestionario ya creado

        $cuestionario_Controller->cuestionario_Resolver_Visualizar($_GET['data-idCuestVisualizar'], $_GET['nombreAsig'], $_GET['nombreCuest'], $_GET['isProfesor'], $_GET['idUser'], $_GET['nombreAlu'], $_GET['apeAlu'], $_GET['lang'], $_GET['idAsig'], $_SESSION['ltiConsumerURL']);

    } else if (isset($_POST['respuestaElegida']) && isset($_POST['tipoComUsado']) && isset($_POST['idUser'])) {//si grabo respuesta
        print_r($_SESSION);
        $cuestionario_Controller->guardarCadaRespuesta($_POST['respuestaElegida'], $_POST['tipoComUsado'], $_POST['idUser'],$_POST['preguntaResuelta']);


    } else if (isset($_POST["hasComodinVerde"])) {

             $cuestionario_Controller->hasComodinVerde($_POST['idCuestLTI']);

    } else if (isset($_POST["hasComodinAmarillo"])) {

        $cuestionario_Controller->hasComodinAmbar($_POST['idCuestLTI']);

    } else if (isset($_POST['saveGradeMoodle'])) {

        $lti_controller = new LTI_Controller();
        $cuestionario_Controller = new Cuestionario_Resolver_Controller();

        $lti_controller->prepare_Grade_toMoodle($_POST['idCuestLTI'], $_POST['idUser']);
        $cuestionario_Controller->mostarResultados($_POST['idCuestLTI'], $_POST['idUser'], $_POST['idAsig']);

    }else if (isset($_POST['mostrarResultados'])) {

        $cuestionario_Controller->mostarResultados($_POST['idCuestLTI'], $_POST['idUser'], $_POST['idAsig']);

    }
    else { //LA PRIMERA VEZ QUE ENTRO

        $lti_controller = new LTI_Controller();

        //Comprueba que el LTI_Consumer es válido
        if ($lti_controller->LTI_valid()) {

            $idCuestionario = $lti_controller->get_custom_idcuestionario();
            $isProfesor = $lti_controller->isProfesor();
            $idUser = $lti_controller->getUserId();
            $nombreAlu = $lti_controller->getNombreUser();
            $apeAlu = $lti_controller->getApeUser();
            $nombreAsig = $lti_controller->getCourseName();
            $lang = $lti_controller->getLanguage();
            $emailLTI = $lti_controller->getEmail();
            $idAsig = $lti_controller->getContextID();
            $ltiConsumerURL = $lti_controller->getConsumerURL();
            $_SESSION['ltiConsumerURL'] = $ltiConsumerURL;

            if ($isProfesor) {

                $result=$cuestionario_Gestionar_Controller->cuestionario_Gestionar($idAsig, $nombreAsig, $isProfesor, $nombreAlu, $apeAlu, $idUser, $lang, $emailLTI, $ltiConsumerURL );

            } else {

                $cuestionario_Controller->registrarAlumno($idUser, $nombreAlu, $apeAlu);
                $activityTitle = $lti_controller->getActivityTitle();
                $cuestionario_Controller->cuestionario_Resolver_Visualizar($idCuestionario, $nombreAsig, $activityTitle, $isProfesor, $idUser, $nombreAlu, $apeAlu, $lang, $idAsig, $ltiConsumerURL);
            }

        } else {

            $lti_controller->LTI_valid();
            exit();
        }

    }
}


?>
