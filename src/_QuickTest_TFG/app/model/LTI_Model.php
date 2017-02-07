<?php
require_once "Database.class.php";

/**
 * @author Alejandro Martínez García <amg0098@alu.ubu.es>
 * @version 1.0.
 */

/**
 * Class LTI_Model.
 * Implementa el modelo.
 * Envía datos de la BD al controlador, para que éste los envíe a la vista.
 * Extiende de la clase Database.
 */
class LTI_Model extends Database
{
    /**
     * Establece un objeto, necesario para poder usar la librería LTI.
     * @return BLTI objeto BLTI.
     */
    function establish_LTI_Context()
    {

        $this->conectarLTI();

        // Load up the Basic LTI Support code

        require_once($_SERVER["DOCUMENT_ROOT"] . "/_QuickTest_TFG/lib/ims-blti/blti.php");

        //header('P3P:CP="IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT"');
        ini_set('session.use_cookies', '0');

        // Establish the context
        $context = new BLTI(array('table' => 'lti_keys_users'));

        return $context;

    }

}

?>