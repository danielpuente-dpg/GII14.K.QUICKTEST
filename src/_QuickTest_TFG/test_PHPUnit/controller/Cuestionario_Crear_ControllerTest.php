<?php

require_once $_SERVER["DOCUMENT_ROOT"] . "/app/controller/Cuestionario_CrearNuevo_Controller.php";

/**
 * @author Alejandro Martínez García
 * Class Cuestionario_Crear_ControllerTest
 * Clase que contiene los test que comprueban la clase Cuestionario_Crear_Controller.
 */
class Cuestionario_Crear_ControllerTest extends PHPUnit_Framework_TestCase
{


    /**
     * Test que comprueba si un cuestionario ha sido creado.
     */
    public function testCuestionario_CrearNuevo()
    {


        $datos=array(
             "cuestTitulo" => "cuestionario 1 ",
             "correctorVerdeTrue" =>"0.33",
             "correctorVerdeFalse" =>  "-0.5",
             "correctorAmarilloTrue" =>  "0.25",
             "correctorAmarilloFalse" => "-0.20",
           'idCuestDuplicar' => "7",
            'idCuestEditar' => "7",
            'isEditar' => "7",
            'isDuplicar' => "7",
            'countPreguntas' => "7",


            "idAsig" => "4",
             "nombreAsig" => "EDAT" ,
             "isProfesor" =>  "1",
             "nombreAlu" => "Profesor1" ,
             "apeAlu" =>  "Apellido Profesor1",
             "idUser" =>  "drQS1mE:6",
             "lang" =>  "es",
             "emailLTI" =>  "profesor10098@gmail.com",
             "launch_presentation_return_url" => "http://localhost/moodle/mod/lti/return.php?course=4&launch_container=4&instanceid=9&sesskey=9HmZHKKsmk",
             "pregRes1" => array(  "pmax" =>  "1",
                 "title" =>  "Pregunta 1",
                 "res" => array(" Respuesta 1 preg1"),
                 "correcta" => "1"));


        $cuestionario_CrearNuevo_Controller = new Cuestionario_CrearNuevo_Controller();
        $result = $cuestionario_CrearNuevo_Controller->unsetVariables($datos);
        $this->assertNotEmpty($result);



    }

}