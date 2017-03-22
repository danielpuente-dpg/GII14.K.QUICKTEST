<?php

if (empty($_SERVER["DOCUMENT_ROOT"])){
    $_SERVER["DOCUMENT_ROOT"] = $URL_GLOBAL;
}

require_once $_SERVER["DOCUMENT_ROOT"] . '/_QuickTest_TFG/app/model/Usuarios_Model.php';


/**
 * @author Alejandro Martínez García <amg0098@alu.ubu.es>
 * @version 1.0.
 */

/**
 * Class Usuarios_Controller.
 * Implementa el controlador.
 * Se comunica con el modelo
 * y la vista para crear un nuevo cuestionario.
 */
class Usuarios_Controller
{

    //TESTED
    /**
     * Registra un nuevo usuario de QuickTest.
     * @param $emailUsuario . Email con el que se ha registrado.
     * @param $password . Password personal.
     * @return bool. Verdadero si el registro se ha completado. Falso sino, o si el email ya existía.
     */
    function registrarNuevoUsuario($emailUsuario, $password)
    {
        $usuarios_Model = new Usuarios_Model();

        if ($usuarios_Model->checkEmailExiste($emailUsuario) == 0) {

            $consumer_key = $this->crearConsumerKey();
            $encriptedSecret = $this->encriptarPassword($password);
            $linkLTI = "localhost/_QuickTest_TFG/index.php";

            $idUsuarioLTI = $usuarios_Model->crearUsuarioLTI($emailUsuario, $consumer_key, $encriptedSecret, $linkLTI);
            $datosLTI = $usuarios_Model->getUsuarioLTI_id($idUsuarioLTI);

            echo json_encode($datosLTI, JSON_UNESCAPED_SLASHES);
            return true;

        } else {//el email ya está registrado
            echo json_encode(0);
            return false;
        }

    }

    //TESTED
    /**
     * Si las credenciales son correctas, permite acceso al usuario de QuickTest.
     * @param $emailUsuario . Email con el que se ha registrado.
     * @param $password . Password personal.
     * @return int.
     *          0 si le deniega.
     *          1 si le da acceso.
     */
    function login($emailUsuario, $password)
    {
        $usuarios_Model = new Usuarios_Model();

        $emailExiste = $usuarios_Model->checkEmailExiste($emailUsuario);

        if (($emailExiste == 0)) {

            echo json_encode(0);
            return 0;

        }

        $datosLTI = $usuarios_Model->getUsuarioLTI_Email($emailUsuario);
        $secretDB_decrypt = $this->desencriptarPassword($datosLTI['shared_secret']);
        error_reporting(E_ERROR | E_PARSE);

        if (hash_equals($secretDB_decrypt, $password)) {

            echo json_encode($datosLTI, JSON_UNESCAPED_SLASHES);
            return 1;

        }

    }


    //TESTED
    /**
     * Genera un string con el ConsumerKey que se le entregará al usuario.
     * @return string. ConsumerKey.
     */
    function crearConsumerKey()
    {

        $alphabet = "abcdefghijklmnopqrstuwxyzABCDEFGHIJKLMNOPQRSTUWXYZ0123456789";
        $pass = array();
        $alphaLength = strlen($alphabet) - 1;

        for ($i = 0; $i < 7; $i++) {

            $n = rand(0, $alphaLength);
            $pass[] = $alphabet[$n];

        }

        return implode($pass);
    }


    //TESTED
    /**
     * Función que encripta los passwords, antes de guardarlos en la base de datos.
     * @param $password . password sin encrpitar.
     * @return string. password encriptado.
     */
    function encriptarPassword($password)
    {
        # --- ENCRYPTION ---

        # la clave debería ser binaria aleatoria, use scrypt, bcrypt o PBKDF2 para
        # convertir un string en una clave
        # la clave se especifica en formato hexadecimal
        $key = pack('H*', "bcb04b7e103a0cd8b54763051cef08bc55abe029fdebae5e1d417e2ffb2a00a3");

        # mostrar el tamaño de la clave, use claves de 16, 24 o 32 bytes para AES-128, 192
        # y 256 respectivamente
        $key_size = strlen($key);

        # crear una aleatoria IV para utilizarla co condificación CBC
        $iv_size = mcrypt_get_iv_size(MCRYPT_RIJNDAEL_128, MCRYPT_MODE_CBC);
        $iv = mcrypt_create_iv($iv_size, MCRYPT_RAND);

        # crea un texto cifrado compatible con AES (tamaño de bloque Rijndael = 128)
        # para hacer el texto confidencial
        # solamente disponible para entradas codificadas que nunca finalizan con el
        # el valor  00h (debido al relleno con ceros)
        $ciphertext = mcrypt_encrypt(MCRYPT_RIJNDAEL_128, $key,
            $password, MCRYPT_MODE_CBC, $iv);

        # anteponer la IV para que esté disponible para el descifrado
        $ciphertext = $iv . $ciphertext;

        # codificar el texto cifrado resultante para que pueda ser representado por un string
        $ciphertext_base64 = base64_encode($ciphertext);

        return $ciphertext_base64;

    }

    //TESTED
    /**
     * Desencripta los parámetros que la función desencriptarPassword necesita.
     * @return array. Parámetros desencriptados.
     */
    function desencriptarParametros()
    {
        $iv_size = mcrypt_get_iv_size(MCRYPT_RIJNDAEL_128, MCRYPT_MODE_CBC);
        $key = pack('H*', "bcb04b7e103a0cd8b54763051cef08bc55abe029fdebae5e1d417e2ffb2a00a3");

        $parametros = array('iv_size' => $iv_size,
            'key' => $key);

        return $parametros;

    }

    //TESTED
    /**
     * Función que desencripta un password.
     * @param $encriptedSecret . Password encriptado.
     * @return string, Password desencriptado.
     */
    function desencriptarPassword($encriptedSecret)
    {
        $parametros = $this->desencriptarParametros();
        $ciphertext_dec = base64_decode($encriptedSecret);

        # recupera la IV, iv_size debería crearse usando mcrypt_get_iv_size()
        $iv_dec = substr($ciphertext_dec, 0, $parametros['iv_size']);

        # recupera el texto cifrado (todo excepto el $iv_size en el frente)
        $ciphertext_dec = substr($ciphertext_dec, $parametros['iv_size']);

        # podrían eliminarse los caracteres con valor 00h del final del texto puro
        $plaintext_dec = mcrypt_decrypt(MCRYPT_RIJNDAEL_128, $parametros['key'],
            $ciphertext_dec, MCRYPT_MODE_CBC, $iv_dec);

        return trim($plaintext_dec);

    }


}

?>