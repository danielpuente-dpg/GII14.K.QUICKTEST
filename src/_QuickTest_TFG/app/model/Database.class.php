<?php
/**
 * @author Alejandro Martínez García <amg0098@alu.ubu.es>
 * @version 1.0.
 */

/**
 * Class Database.
 * Gestiona la conexión a la base de datos.
 * De esta clase extienden los distintos modelos.
 */
class Database
{
    /**  @var $conexion . objeto conexión. */
    private $conexion;

    /** @var string. nombre del host. */
    private $host = "localhost";

    /** @var string. nombre del usuario en la base de datos */
    private $user = "root";

    /** @var string. password del usuario en la base de datos */
    private $password = "";

    /** @var string. nombre de la base de datos */
    private $database = "quicktest_tfg";


//TESTED
    /**
     * Establece la conexión con la base de datos.
     * @return mysqli. objeto mysqli con la conexión.
     */
    public function conectar()
    {

        if (!isset($this->conexion)) {
            $this->conexion = (mysqli_connect($this->host, $this->user, $this->password, $this->database)) /*or die(mysqli_error())*/
            ;

            return $this->conexion;
        }

    }

//TESTED
    /**
     * Establece la conexión con la base de datos.
     * Utiliza mysql_connect que está deprecated, pero viene impuesto por la librería LTI.
     * @return resource. objeto con la conexión.
     */
    public function conectarLTI()
    {

        if (!isset($this->conexion)) {
            $this->conexion = (mysql_connect($this->host, $this->user, $this->password))/** or die(mysqli_error())*/
            ;
            mysql_select_db($this->database);
            return $this->conexion;
        }
    }

//TESTED
    /**
     * Ejecuta una sentencia preparada.
     * @param $sql . Recibe una sentencia SQL.
     * @return bool|mysqli_stmt. Devuelve false si hay error, o un objeto mysqli_stmt si no lo hay.
     */
    public function consultaPreparada($sql)
    {

        $resultado = mysqli_prepare($this->conexion, $sql) /*or die(mysqli_error($this -> conexion))*/
        ;
        if (!$resultado) {
            //echo 'MySQL Error: ' . mysqli_error($this -> conexion);
            return false;

        }

        return $resultado;
    }

//TESTED
    /**
     * Libera el resultado alojado en la memoria del objeto sentencia.
     * Cierra el objeto sentencia.
     * @param $query. query.
     */
    public function closeFreeStatement($query)
    {
        mysqli_stmt_free_result($query);
        mysqli_stmt_close($query);

    }

}

?>