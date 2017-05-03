-- phpMyAdmin SQL Dump
-- version 4.3.11
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 08-07-2015 a las 02:45:48
-- Versión del servidor: 5.6.24
-- Versión de PHP: 5.6.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `quicktest_tfg`
--
CREATE DATABASE IF NOT EXISTS `quicktest_tfg` DEFAULT CHARACTER SET utf8 COLLATE utf8_spanish_ci;
USE `quicktest_tfg`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `alumno`
--

CREATE TABLE IF NOT EXISTS `alumno` (
  `idAlumno` varchar(45) COLLATE utf8_spanish_ci NOT NULL,
  `nombre` varchar(45) COLLATE utf8_spanish_ci DEFAULT NULL,
  `apellidos` varchar(45) COLLATE utf8_spanish_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `alumno`
--

INSERT INTO `alumno` (`idAlumno`, `nombre`, `apellidos`) VALUES
('1', NULL, NULL),
('10', NULL, NULL),
('11', NULL, NULL),
('12', NULL, NULL),
('13', NULL, NULL),
('14', NULL, NULL),
('15', NULL, NULL),
('16', NULL, NULL),
('17', NULL, NULL),
('18', NULL, NULL),
('19', NULL, NULL),
('2', NULL, NULL),
('20', NULL, NULL),
('21', NULL, NULL),
('22', NULL, NULL),
('23', NULL, NULL),
('24', NULL, NULL),
('25', NULL, NULL),
('26', NULL, NULL),
('27', NULL, NULL),
('28', NULL, NULL),
('29', NULL, NULL),
('3', NULL, NULL),
('30', NULL, NULL),
('4', NULL, NULL),
('4ryDp2o :26', 'NombreAlumno20', 'ApellidoAlumno20'),
('4ryDp2o :7', 'NombreAlumno1', 'ApellidoAlumno1'),
('4ryDp2o:26', 'NombreAlumno20', 'ApellidoAlumno20'),
('5', NULL, NULL),
('6', NULL, NULL),
('7', NULL, NULL),
('8', NULL, NULL),
('8q0sUiS :7', 'NombreAlumno1', 'ApellidoAlumno1'),
('8q0sUiS:7', 'NombreAlumno1', 'ApellidoAlumno1'),
('9', NULL, NULL),
('drQS1mE:4', 'Alumno1', 'Apellido Alumo'),
('drQS1mE:5', 'Alumno2', 'Apellido Alumno'),
('drQS1mF:4', 'Alumno1', 'Apellido Alumo'),
('eYo06DI:3', 'NombreProfesor1', 'ApellidoProfesor1'),
('eYo06DI:7', 'NombreAlumno1', 'ApellidoAlumno1'),
('eYo06DI:8', 'NombreAlumno2', 'ApellidoAlumno2'),
('hnanret:4', 'Alumno1', 'Apellido Alumo'),
('hnanret:5', 'Alumno2', 'Apellido Alumno'),
('yZAM3Fm:7', 'NombreAlumno1', 'ApellidoAlumno1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `alumno_has_cuestionario`
--

CREATE TABLE IF NOT EXISTS `alumno_has_cuestionario` (
  `alumno_idAlumno` varchar(45) COLLATE utf8_spanish_ci NOT NULL,
  `cuestionario_idCuestionario` int(11) NOT NULL,
  `orden` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `alumno_has_cuestionario`
--

INSERT INTO `alumno_has_cuestionario` (`alumno_idAlumno`, `cuestionario_idCuestionario`, `orden`) VALUES
('1', 2, 1),
('10', 2, 1),
('11', 2, 1),
('2', 2, 1),
('3', 2, 1),
('4', 2, 1),
('5', 2, 1),
('6', 2, 1),
('7', 2, 1),
('8', 2, 1),
('9', 2, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `alumno_has_respuesta`
--

CREATE TABLE IF NOT EXISTS `alumno_has_respuesta` (
  `alumno_idAlumno` varchar(20) COLLATE utf8_spanish_ci NOT NULL,
  `respuesta_idRespuesta` int(11) NOT NULL,
  `pregunta` int(11) NOT NULL,
  `puntuacion` decimal(5,2) DEFAULT NULL,
  `comodinUsado` varchar(8) COLLATE utf8_spanish_ci NOT NULL,
  `correcta` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cuestionario`
--

CREATE TABLE IF NOT EXISTS `cuestionario` (
  `idCuestionario` int(11) NOT NULL,
  `titulo` varchar(256) COLLATE utf8_spanish_ci DEFAULT NULL,
  `duracion` time DEFAULT NULL,
  `contadorAlumnos` int(11) DEFAULT '0',
  `asignatura_idAsignatura` int(11) DEFAULT NULL,
  `asignatura_nombreAsignatura` varchar(255) COLLATE utf8_spanish_ci DEFAULT NULL,
  `correctorVerdeTrue` decimal(5,2) DEFAULT NULL,
  `correctorVerdeFalse` decimal(5,2) DEFAULT NULL,
  `correctorAmarilloTrue` decimal(5,2) DEFAULT NULL,
  `correctorAmarilloFalse` decimal(5,2) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `cuestionario`
--

INSERT INTO `cuestionario` (`idCuestionario`, `titulo`, `duracion`, `contadorAlumnos`, `asignatura_idAsignatura`, `asignatura_nombreAsignatura`, `correctorVerdeTrue`, `correctorVerdeFalse`, `correctorAmarilloTrue`, `correctorAmarilloFalse`) VALUES
(1, 'cuestionario de demostracion', NULL, 102, 999, 'Programacion II', '0.33', '-0.50', '0.25', '-0.20'),
(2, 'Cuestionario 2. IntroducciÃ³n Lenguaje ProgramaciÃ³n C. GRUPO 1 MAÃ‘ANA', NULL, 0, 2, 'Programacion II', '0.33', '-0.50', '0.25', '-0.20'),
(3, 'Cuestionario  3. Datos enteros. GRUPO 1 MAÃ‘ANA', NULL, 0, 2, 'Programacion II', '0.33', '-0.50', '0.25', '-0.20'),
(4, 'Cuestionario 4.  ProgramaciÃ³n modular. GRUPO 1 MAÃ‘ANA', NULL, 0, 2, 'Programacion II', '0.33', '-0.50', '0.25', '-0.20'),
(5, 'cuestionario 5. Sentencias de control. GRUPO 1 MAÃ‘ANA', NULL, 0, 2, 'Programacion II', '0.33', '-0.50', '0.25', '-0.20'),
(6, 'Cuestionario 6. Recursividad. GRUPO 1 MAÃ‘ANA', NULL, 0, 2, 'Programacion II', '0.33', '-0.50', '0.25', '-0.20'),
(7, 'Cuestionario 7. Sentencias de control, parte 2. GRUPO 1 MAÃ‘ANA', NULL, 0, 2, 'Programacion II', '0.33', '-0.50', '0.25', '-0.20'),
(8, 'Cuestionario 8.  Archivos de texto.  GRUPO 1 MAÃ‘ANA', NULL, 0, 2, 'Programacion II', '0.33', '-0.50', '0.25', '-0.20'),
(10, 'Cuestionario 10. Prueba con 40 preguntas.', NULL, 0, 2, 'Programacion II', '0.33', '-0.50', '0.25', '-0.20'),
(11, 'Cuestionario 2. IntroducciÃ³n Lenguaje ProgramaciÃ³n C. GRUPO 2 TARDE', NULL, 0, 2, 'Programacion II', '0.33', '-0.50', '0.25', '-0.20'),
(12, 'Cuestionario  3. Datos enteros. GRUPO 2 TARDE', NULL, 0, 2, 'Programacion II', '0.33', '-0.50', '0.25', '-0.20'),
(13, 'Cuestionario 4.  ProgramaciÃ³n modular. GRUPO 2 TARDE', NULL, 0, 2, 'Programacion II', '0.33', '-0.50', '0.25', '-0.20'),
(14, 'cuestionario 5. Sentencias de control. GRUPO 1 TARDE', NULL, 0, 2, 'Programacion II', '0.33', '-0.50', '0.25', '-0.20'),
(15, 'Cuestionario 6. Recursividad. GRUPO 2 TARDE', NULL, 0, 2, 'Programacion II', '0.33', '-0.50', '0.25', '-0.20'),
(16, 'Cuestionario 7. Sentencias de control, parte 2. GRUPO 2 TARDE', NULL, 0, 2, 'Programacion II', '0.33', '-0.50', '0.25', '-0.20'),
(17, 'Cuestionario 8.  Archivos de texto.  GRUPO 2  TARDE', NULL, 0, 2, 'Programacion II', '0.33', '-0.50', '0.25', '-0.20'),
(18, 'Cuestionario 2. IntroducciÃ³n Lenguaje ProgramaciÃ³n C. COPIA PARA EDAT', NULL, 0, 2, 'Programacion II', '0.33', '-0.50', '0.25', '-0.20');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `lti_keys_users`
--

CREATE TABLE IF NOT EXISTS `lti_keys_users` (
  `id_lti_keys` int(11) NOT NULL,
  `emailUsuario` varchar(45) COLLATE utf8_spanish_ci NOT NULL,
  `oauth_consumer_key` varchar(45) COLLATE utf8_spanish_ci NOT NULL,
  `secret` varchar(45) COLLATE utf8_spanish_ci NOT NULL,
  `resource_link` varchar(45) COLLATE utf8_spanish_ci DEFAULT NULL,
  `created_at` date DEFAULT NULL,
  `updated_at` date DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `lti_keys_users`
--

INSERT INTO `lti_keys_users` (`id_lti_keys`, `emailUsuario`, `oauth_consumer_key`, `secret`, `resource_link`, `created_at`, `updated_at`) VALUES
(1, 'amg0098@alu.ubu.es', 'drQS1mF', 'SsCsq1rYpTw4g1FUC4qWe2N6rqwYDrw+R5zAk7l8zqU=', 'localhost/_QuickTest_TFG/index.php', '2015-06-08', NULL),
(2, 'profesor1@ubu.es', 'eYo06DI', 'c7FmRZoq8iMnLQi/wAUQI7eY9O4QX1f4gC7G57UPQd8=', 'localhost/_QuickTest_TFG/index.php', '2015-07-04', NULL),
(3, 'profesor2@ubu.es', '8q0sUiS', 'glN6kthW3UKjNVuCjDdZWRHaFwLEUlXg+Q7VlOS0Qos=', 'localhost/_QuickTest_TFG/index.php', '2015-07-04', NULL),
(4, 'profesor3@ubu.es', '4ryDp2o', 'gH13wEfPciO4TE0zHlkPGNdJNTK1RW5Me88SdeksgfU=', 'localhost/_QuickTest_TFG/index.php', '2015-07-04', NULL),
(5, 'profesor4@ubu.es', 'yZAM3Fm', 'Nyvv1RiRiPozJ1/i7/ObVhDnfNTzuZj1i39LNq8J4qI=', 'localhost/_QuickTest_TFG/index.php', '2015-07-04', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pregunta`
--

CREATE TABLE IF NOT EXISTS `pregunta` (
  `idPregunta` int(11) NOT NULL,
  `titulo` varchar(800) COLLATE utf8_spanish_ci DEFAULT NULL,
  `max_puntuacion` int(3) DEFAULT NULL,
  `feedBack` varchar(20) COLLATE utf8_spanish_ci DEFAULT NULL,
  `cuestionario_idCuestionario` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=730 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `pregunta`
--

INSERT INTO `pregunta` (`idPregunta`, `titulo`, `max_puntuacion`, `feedBack`, `cuestionario_idCuestionario`) VALUES
(105, 'Pregunta sin comodÃ­n.  Â¿QuÃ© sistema operativo pertenece a Microsoft?', 1, NULL, 1),
(106, 'Pregunta sin comodÃ­n. Â¿QuiÃ©n inventÃ³ Facebook?', 2, NULL, 1),
(107, 'Pregunta sin comodÃ­n. Java es lenguaje de programaciÃ³n orientado a:', 2, NULL, 1),
(108, 'Pregunta con comodÃ­n Ã¡mbar. Bootstrap permite hacer "responsive design". Verdadero o Falso:', 4, NULL, 1),
(109, 'Pregunta con comodÃ­n verde. En HTML, podemos hacer un formulario usando la tÃ©cnica de POST. Verdadero o Falso.', 5, NULL, 1),
(110, 'Pregunta con comodÃ­n verde.  Â¿QuiÃ©n inventÃ³ el lenguaje de programaciÃ³n C?', 4, NULL, 1),
(111, 'Pregunta con comodÃ­n verde.  Â¿QuÃ© operaciÃ³n NO esta permitida en JAVA?', 2, NULL, 1),
(112, 'Pregunta con comodÃ­n verde.  CuÃ¡l de estas NO es una etiqueta HTML:', 3, NULL, 1),
(113, 'Pregunta con comodÃ­n Ã¡mbar.  Â¿Para quÃ© sirve un EXTENDS en  JAVA:', 4, NULL, 1),
(114, 'Pregunta con comodÃ­n Ã¡mbar.  Â¿CuÃ¡l de las siguientes palabras NO es una palabra reservada en C?', 10, NULL, 1),
(115, 'Pregunta con comodÃ­n Ã¡mbar.  Â¿CuÃ¡l de las siguientes palabras SI es una palabra reservada en C?', 1, NULL, 1),
(116, 'Pregunta con comodÃ­n Ã¡mbar.  Â¿CuÃ¡l de las siguientes lenguajes, es del lado del servidor?', 2, NULL, 1),
(117, 'Pregunta con comodÃ­n Ã¡mbar.  Â¿CuÃ¡l de las siguientes lenguajes, es del lado del cliente?', 3, NULL, 1),
(118, 'Pregunta sin comodÃ­n .  Â¿Es JSP la evoluciÃ³n de los servlets?', 4, NULL, 1),
(119, 'Pregunta con comodÃ­n verde.  Â¿QuÃ© es APACHE?', 5, NULL, 1),
(120, 'Pregunta con comodÃ­n Ã¡mbar.  Â¿QuÃ© es MySQL?', 1, NULL, 1),
(121, 'Pregunta con comodÃ­n Ã¡mbar.  Â¿QuÃ© nos permite AJAX?', 2, NULL, 1),
(122, 'Pregunta sin comodin.  Â¿QuÃ© es LTI?', 3, NULL, 1),
(123, 'Pregunta con comodÃ­n Ã¡mbar.  Â¿QuÃ© navegador estÃ¡ creado por Google?', 4, NULL, 1),
(124, 'Pregunta con comodÃ­n verde.  Â¿QuÃ© estructura de JAVA nos permite guardar un conjunto CLAVE-VALOR?', 5, NULL, 1),
(514, 'Pregunta 1', 1, NULL, 10),
(515, 'Â Pregunta 2Â ', 1, NULL, 10),
(516, 'Â Pregunta 3Â ', 1, NULL, 10),
(517, 'Â Pregunta 4Â ', 1, NULL, 10),
(518, 'Â Pregunta 5Â ', 1, NULL, 10),
(519, 'Â Pregunta 6Â ', 1, NULL, 10),
(520, 'Â Pregunta 7Â ', 1, NULL, 10),
(521, 'Â Pregunta 8Â ', 1, NULL, 10),
(522, 'Â Pregunta 9Â ', 1, NULL, 10),
(523, 'Pregunta 10', 1, NULL, 10),
(524, 'Pregunta 11', 1, NULL, 10),
(525, 'Pregunta 12', 1, NULL, 10),
(526, 'Pregunta 13', 1, NULL, 10),
(527, 'Pregunta 14', 1, NULL, 10),
(528, 'Pregunta 15', 1, NULL, 10),
(529, 'Pregunta 16', 1, NULL, 10),
(530, 'Pregunta 17', 1, NULL, 10),
(531, 'Pregunta 18', 1, NULL, 10),
(532, 'Pregunta 19', 1, NULL, 10),
(533, 'Pregunta 20', 1, NULL, 10),
(534, 'Pregunta 21', 1, NULL, 10),
(535, 'Pregunta 22', 1, NULL, 10),
(536, 'Pregunta 23', 1, NULL, 10),
(537, 'Pregunta 24', 1, NULL, 10),
(538, 'Pregunta 25', 1, NULL, 10),
(539, 'Pregunta 26', 1, NULL, 10),
(540, 'Pregunta 27', 1, NULL, 10),
(541, 'Pregunta 29', 1, NULL, 10),
(542, 'Pregunta 30', 1, NULL, 10),
(543, 'Pregunta 31', 1, NULL, 10),
(544, 'Pregunta 32', 1, NULL, 10),
(545, 'Pregunta 33', 1, NULL, 10),
(546, 'Pregunta 34', 1, NULL, 10),
(547, 'Pregunta 35', 1, NULL, 10),
(548, 'Pregunta 36', 1, NULL, 10),
(549, 'Pregunta 37', 1, NULL, 10),
(550, 'Pregunta 38', 1, NULL, 10),
(551, 'Pregunta 39', 1, NULL, 10),
(552, 'Pregunta 40', 1, NULL, 10),
(553, 'Pregunta 41', 1, NULL, 10),
(554, 'Â¿CuÃ¡l de las siguientes frases es INCORRECTA?', 2, NULL, 2),
(555, 'Â¿QuÃ© caracterÃ­stica NO es imprescindible en un algoritmo?', 3, NULL, 2),
(556, 'Â¿CuÃ¡l de las siguientes NO es una de las caracterÃ­sticas de un lenguaje de bajo nivel?', 4, NULL, 2),
(557, 'Un compilador consiste en:', 2, NULL, 2),
(558, 'Elige la correcta', 3, NULL, 2),
(559, 'Â¿CuÃ¡l de las siguientes NO es un paso en el ciclo de vida?', 1, NULL, 2),
(560, 'Marca la correcta. Los identificadores en C:', 1, NULL, 2),
(561, 'Elige la correcta', 4, NULL, 2),
(562, 'Â¿CuÃ¡l de las siguientes frases es INCORRECTA?', 2, NULL, 11),
(563, 'Â¿QuÃ© caracterÃ­stica NO es imprescindible en un algoritmo?', 3, NULL, 11),
(564, 'Â¿CuÃ¡l de las siguientes NO es una de las caracterÃ­sticas de un lenguaje de bajo nivel?', 4, NULL, 11),
(565, 'Un compilador consiste en:', 2, NULL, 11),
(566, 'Elige la correcta', 3, NULL, 11),
(567, 'Â¿CuÃ¡l de las siguientes NO es un paso en el ciclo de vida?', 1, NULL, 11),
(568, 'Marca la correcta. Los identificadores en C:', 1, NULL, 11),
(569, 'Elige la correcta', 4, NULL, 11),
(570, 'Verdadero o falso. Los argumentos: Representan las direcciones de memoria de las variables donde escribir los valores leÃ­dos', 1, NULL, 3),
(571, 'Un valor de retorno leÃ­do despuÃ©s del fin de fichero retorna:', 4, NULL, 3),
(572, 'Una sentencia en C es:', 1, NULL, 3),
(573, 'A una variable le podemos asignar', 5, NULL, 3),
(574, 'Para quÃ© sirve el operador % en C', 3, NULL, 3),
(575, 'Los operadores de desplazamiento con signo', 1, NULL, 3),
(576, 'Los tipos de da datos NATURALES  tienen la palabra reservada:', 1, NULL, 3),
(577, 'Los tipos de da datos ENTEROS tienen la palabra reservada:', 2, NULL, 3),
(578, 'printf tiene como valor por retorno', 2, NULL, 3),
(579, 'scanf tiene como valor por retorno', 3, NULL, 3),
(580, 'ProgramaciÃ³n modular es:', 1, NULL, 4),
(581, 'En C las funciones deben de ser obligatoriamente declaradas:', 5, NULL, 4),
(582, 'EstÃ©ticamente, los prototipos de las funciones se colocan', 1, NULL, 4),
(583, 'EstÃ©ticamente, la sintaxis de una funciÃ³n prototipo:', 1, NULL, 4),
(584, 'Un parÃ¡metro, es una variable que sirve para comunicar datos entre la funciÃ³n y la unidad que lo llama', 1, NULL, 4),
(585, 'Si el valor es proporcionado a la funciÃ³n por la unidad que lo llama, el parÃ¡metro es de:', 4, NULL, 4),
(586, 'Si el valor es devuelto por el subprograma a la unidad que lo referencia, el parÃ¡metro es de:', 3, NULL, 4),
(587, 'Si el valor es proporcionado a la funciÃ³n por la unidad que lo llama, para ser modificado, el parÃ¡metro es de:', 3, NULL, 4),
(588, 'En un paso por referencia:', 1, NULL, 4),
(589, 'Cual de las siguientes NO es una caracterÃ­stica de la programaciÃ³n estructurada:', 1, NULL, 5),
(590, 'Descomponer un programa en tÃ©rminos de recursos abstractos consiste en:', 3, NULL, 5),
(591, 'SegÃºn el Teorema de la programaciÃ³n estructurada, Un programa propio puede ser escrito utilizando estructuras de control:', 4, NULL, 5),
(592, 'Un programa se define como propio si:', 4, NULL, 5),
(593, 'La sentencia alternativa nos permite', 4, NULL, 5),
(594, 'Una expresiÃ³n lÃ³gica es una expresiÃ³n', 2, NULL, 5),
(595, 'Verdadero o falso. Los operadores de igualdad y diferencia se ejecutan de izquierda a derecha', 4, NULL, 5),
(596, 'En un switch, en C', 1, NULL, 5),
(597, 'Un objeto es recursivo si:', 4, NULL, 6),
(598, 'La recursividad consiste en en la posibilidad de definir un nÃºmero infinito de objetos mediante un enunciado finito.', 1, NULL, 6),
(599, 'Existen dos tipos de recursividad:', 2, NULL, 6),
(600, 'En C, una funciÃ³n puede definirse recursivamente.', 1, NULL, 6),
(601, 'La programaciÃ³n recursiva garantiza el mejor mÃ©todo para resolver un problema', 1, NULL, 6),
(602, 'Los algoritmos recursivos son mÃ¡s lentos y requieren un mayor espacio en memoria.', 1, NULL, 6),
(621, 'Verdadero o falso. Los argumentos: Representan las direcciones de memoria de las variables donde escribir los valores leÃ­dos', 1, NULL, 12),
(622, 'Un valor de retorno leÃ­do despuÃ©s del fin de fichero retorna:', 4, NULL, 12),
(623, 'Una sentencia en C es:', 1, NULL, 12),
(624, 'A una variable le podemos asignar', 5, NULL, 12),
(625, 'Para quÃ© sirve el operador % en C', 3, NULL, 12),
(626, 'Los operadores de desplazamiento con signo', 1, NULL, 12),
(627, 'Los tipos de da datos NATURALES  tienen la palabra reservada:', 1, NULL, 12),
(628, 'Los tipos de da datos ENTEROS tienen la palabra reservada:', 2, NULL, 12),
(629, 'printf tiene como valor por retorno', 2, NULL, 12),
(630, 'scanf tiene como valor por retorno', 3, NULL, 12),
(631, 'ProgramaciÃ³n modular es:', 1, NULL, 13),
(632, 'En C las funciones deben de ser obligatoriamente declaradas:', 5, NULL, 13),
(633, 'EstÃ©ticamente, los prototipos de las funciones se colocan', 1, NULL, 13),
(634, 'EstÃ©ticamente, la sintaxis de una funciÃ³n prototipo:', 1, NULL, 13),
(635, 'Un parÃ¡metro, es una variable que sirve para comunicar datos entre la funciÃ³n y la unidad que lo llama', 1, NULL, 13),
(636, 'Si el valor es proporcionado a la funciÃ³n por la unidad que lo llama, el parÃ¡metro es de:', 4, NULL, 13),
(637, 'Si el valor es devuelto por el subprograma a la unidad que lo referencia, el parÃ¡metro es de:', 3, NULL, 13),
(638, 'Si el valor es proporcionado a la funciÃ³n por la unidad que lo llama, para ser modificado, el parÃ¡metro es de:', 3, NULL, 13),
(639, 'En un paso por referencia:', 1, NULL, 13),
(640, 'Cual de las siguientes NO es una caracterÃ­stica de la programaciÃ³n estructurada:', 1, NULL, 14),
(641, 'Descomponer un programa en tÃ©rminos de recursos abstractos consiste en:', 3, NULL, 14),
(642, 'SegÃºn el Teorema de la programaciÃ³n estructurada, Un programa propio puede ser escrito utilizando estructuras de control:', 4, NULL, 14),
(643, 'Un programa se define como propio si:', 4, NULL, 14),
(644, 'La sentencia alternativa nos permite', 4, NULL, 14),
(645, 'Una expresiÃ³n lÃ³gica es una expresiÃ³n', 2, NULL, 14),
(646, 'Verdadero o falso. Los operadores de igualdad y diferencia se ejecutan de izquierda a derecha', 4, NULL, 14),
(647, 'En un switch, en C', 1, NULL, 14),
(654, 'Un objeto es recursivo si:', 4, NULL, 15),
(655, 'La recursividad consiste en en la posibilidad de definir un nÃºmero infinito de objetos mediante un enunciado finito.', 1, NULL, 15),
(656, 'Existen dos tipos de recursividad:', 2, NULL, 15),
(657, 'En C, una funciÃ³n puede definirse recursivamente.', 1, NULL, 15),
(658, 'La programaciÃ³n recursiva garantiza el mejor mÃ©todo para resolver un problema', 1, NULL, 15),
(659, 'Los algoritmos recursivos son mÃ¡s lentos y requieren un mayor espacio en memoria.', 1, NULL, 15),
(678, 'En C hay tres sentencias de control de bucles:', 3, NULL, 7),
(679, 'En un bucle while', 1, NULL, 7),
(680, 'En un bucle while', 1, NULL, 7),
(681, 'En un bucle do... while', 4, NULL, 7),
(682, 'Un bucle for estÃ¡ formado por: ', 1, NULL, 7),
(683, 'Cuando usamos el operador coma,  ', 1, NULL, 7),
(684, 'En programaciÃ³n estructurada las sentencias de salto', 5, NULL, 7),
(685, 'En C hay tres sentencias de control de bucles:', 3, NULL, 16),
(686, 'En un bucle while', 1, NULL, 16),
(687, 'En un bucle while', 1, NULL, 16),
(688, 'En un bucle do... while', 4, NULL, 16),
(689, 'Un bucle for estÃ¡ formado por: ', 1, NULL, 16),
(690, 'Cuando usamos el operador coma,  ', 1, NULL, 16),
(691, 'En programaciÃ³n estructurada las sentencias de salto', 5, NULL, 16),
(692, 'Un fichero es', 1, NULL, 8),
(693, 'Las operaciones con archivos las realiza ', 1, NULL, 8),
(694, 'El tipo buffer de archivo en C es  ', 4, NULL, 8),
(695, 'En C, la apertura de un archivo devuelve', 3, NULL, 8),
(696, 'En C, la apertura de un archivo devuelve', 4, NULL, 8),
(697, 'El cierre de un archivo en C lo realiza', 2, NULL, 8),
(698, 'Â Pregunta 7Â ', 1, NULL, 8),
(699, 'fclose', 1, NULL, 8),
(700, 'Â Pregunta 9Â ', 1, NULL, 8),
(701, 'int fputc(int c, FILE *pf);', 4, NULL, 8),
(702, 'fprintf', 1, NULL, 8),
(703, 'Un fichero es', 1, NULL, 17),
(704, 'Las operaciones con archivos las realiza ', 1, NULL, 17),
(705, 'El tipo buffer de archivo en C es  ', 4, NULL, 17),
(706, 'En C, la apertura de un archivo devuelve', 3, NULL, 17),
(707, 'En C, la apertura de un archivo devuelve', 4, NULL, 17),
(708, 'El cierre de un archivo en C lo realiza', 2, NULL, 17),
(709, 'Â Pregunta 7Â ', 1, NULL, 17),
(710, 'fclose', 1, NULL, 17),
(711, 'Â Pregunta 9Â ', 1, NULL, 17),
(712, 'int fputc(int c, FILE *pf);', 4, NULL, 17),
(713, 'fprintf', 1, NULL, 17),
(722, 'Â¿CuÃ¡l de las siguientes frases es INCORRECTA?', 2, NULL, 18),
(723, 'Â¿QuÃ© caracterÃ­stica NO es imprescindible en un algoritmo?', 3, NULL, 18),
(724, 'Â¿CuÃ¡l de las siguientes NO es una de las caracterÃ­sticas de un lenguaje de bajo nivel?', 4, NULL, 18),
(725, 'Un compilador consiste en:', 2, NULL, 18),
(726, 'Elige la correcta', 3, NULL, 18),
(727, 'Â¿CuÃ¡l de las siguientes NO es un paso en el ciclo de vida?', 1, NULL, 18),
(728, 'Marca la correcta. Los identificadores en C:', 1, NULL, 18),
(729, 'Elige la correcta', 4, NULL, 18);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `respuesta`
--

CREATE TABLE IF NOT EXISTS `respuesta` (
  `idRespuesta` int(11) NOT NULL,
  `titulo` varchar(255) COLLATE utf8_spanish_ci DEFAULT NULL,
  `correcta` tinyint(1) DEFAULT '0',
  `contador` int(11) DEFAULT '0',
  `pregunta_idPregunta` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1347 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `respuesta`
--

INSERT INTO `respuesta` (`idRespuesta`, `titulo`, `correcta`, `contador`, `pregunta_idPregunta`) VALUES
(1, 'Windows.', 1, 30, 105),
(2, 'Apple.', 0, 30, 105),
(3, 'Linux.', 0, 30, 105),
(4, 'Todos.', 0, 30, 105),
(5, 'Ninguno de los anteriores.', 0, 30, 105),
(6, 'Mark Zuckerberg.', 1, 0, 106),
(7, 'Billl Gates.', 0, 0, 106),
(8, 'Ninguno de los anteriores.', 0, 0, 106),
(9, 'No es un lenguaje.', 0, 0, 107),
(10, 'Estructurado', 0, 20, 107),
(11, 'Objetos', 1, 21, 107),
(12, 'Verdadero', 1, 22, 108),
(13, 'Falso (comodin falla)', 0, 41, 108),
(14, 'Verdadero', 1, 34, 109),
(15, 'Falso', 0, 0, 109),
(16, 'William Wallace (comodin falla)', 0, 35, 110),
(17, 'Dennis Ritchie', 1, 40, 110),
(18, 'Steve Jobs', 0, 14, 110),
(19, 'Devolver null.', 0, 6, 111),
(20, 'Crear una variable de tipo float. (comodin falla)', 0, 40, 111),
(21, 'Instanciar una interfaz.', 1, 20, 111),
(22, 'Tener un main.', 0, 20, 111),
(23, 'p', 0, 22, 112),
(24, 'div', 0, 23, 112),
(25, 'em', 0, 20, 112),
(26, 'small', 0, 20, 112),
(27, 'delete', 1, 34, 112),
(28, 'Implementar una interfaz', 0, 5, 113),
(29, 'Alargar la capacidad de un array', 0, 34, 113),
(30, 'Para declarar la herencia', 1, 34, 113),
(31, 'Todas las anteriores.', 0, 26, 113),
(32, 'Auto', 0, 8, 114),
(33, 'Union', 0, 34, 114),
(34, 'Main', 1, 34, 114),
(35, 'Continue', 0, 26, 114),
(36, 'Volatile', 1, 26, 115),
(37, 'Method', 0, 40, 115),
(38, 'PHP', 1, 40, 116),
(39, 'JavaScript', 0, 40, 116),
(40, 'Ambos', 0, 40, 116),
(41, 'Servlets', 0, 19, 117),
(42, 'Javascript', 1, 40, 117),
(43, 'Ninguna de las anteriores', 0, 19, 117),
(44, 'SÃ­', 1, 19, 118),
(45, ' NO', 0, 1, 118),
(46, ' Un editor de textos.', 0, 33, 119),
(47, ' Un servidor web HTTP de cÃ³digo abierto', 1, 0, 119),
(48, ' es un sistema de gestiÃ³n de bases de datos relacional', 1, 75, 120),
(49, ' es un sistema de gestiÃ³n de bases de datos NO relacional', 0, 75, 120),
(50, 'Es un lenguaje de programaciÃ³n.', 0, 75, 120),
(51, 'Realizar cambios sobre las pÃ¡ginas recargÃ¡ndo la web', 0, 75, 121),
(52, 'Realizar cambios sobre las pÃ¡ginas sin necesidad de recargar la web', 1, 75, 121),
(53, 'Ninguna de las dos', 0, 0, 121),
(54, 'Un lenguaje de programaciÃ³n web.', 0, 3, 122),
(55, 'Una plataforma similar a Moodle.', 0, 15, 122),
(56, 'Un estÃ¡ndar desarrollado por la IMS.', 1, 15, 122),
(57, 'Firefox', 0, 15, 123),
(58, 'Chrome', 1, 41, 123),
(59, 'Un mapa', 1, 0, 124),
(60, 'Un string (comodin falla)', 0, 38, 124),
(895, 'Respuesta 1                            ', 1, 0, 514),
(896, 'Respuesta 1', 1, 0, 515),
(897, 'Respuesta 1', 1, 0, 516),
(898, 'Respuesta 1', 1, 0, 517),
(899, 'Respuesta 1', 1, 0, 518),
(900, 'Respuesta 1', 1, 0, 519),
(901, 'Respuesta 1', 1, 0, 520),
(902, 'Respuesta 1', 1, 0, 521),
(903, 'Respuesta 1', 1, 0, 522),
(904, 'Respuesta 1', 1, 0, 523),
(905, 'Respuesta 1', 1, 0, 524),
(906, 'Respuesta 1', 1, 0, 525),
(907, 'Respuesta 1', 1, 0, 526),
(908, 'Respuesta 1', 1, 0, 527),
(909, 'Respuesta 1', 1, 0, 528),
(910, 'Respuesta 1', 1, 0, 529),
(911, 'Respuesta 1', 1, 0, 530),
(912, 'Respuesta 1', 1, 0, 531),
(913, 'Respuesta 1', 1, 0, 532),
(914, 'Respuesta 1', 1, 0, 533),
(915, 'Respuesta 1', 1, 0, 534),
(916, 'Respuesta 1', 1, 0, 535),
(917, 'Respuesta 1', 1, 0, 536),
(918, 'Respuesta 1', 1, 0, 537),
(919, 'Respuesta 1', 1, 0, 538),
(920, 'Respuesta 1', 1, 0, 539),
(921, 'Respuesta 1', 1, 0, 540),
(922, 'Respuesta 1', 1, 0, 541),
(923, 'Respuesta 1', 1, 0, 542),
(924, 'Respuesta 1', 1, 0, 543),
(925, 'Respuesta 1', 1, 0, 544),
(926, 'Respuesta 1', 1, 0, 545),
(927, 'Respuesta 1', 1, 0, 546),
(928, 'Respuesta 1', 1, 0, 547),
(929, 'Respuesta 1', 1, 0, 548),
(930, 'Respuesta 1', 1, 0, 549),
(931, 'Respuesta 1', 1, 0, 550),
(932, 'Respuesta 1', 1, 0, 551),
(933, 'Respuesta 1', 1, 0, 552),
(934, 'Respuesta 1', 1, 0, 553),
(935, 'El hardware es la parte lÃ³gica del ordenador y el software es la parte fÃ­sica del ordenador.', 0, 0, 554),
(936, 'El hardware es la parte fÃ­sica del ordenador y el software es la parte lÃ³gica del ordenador.', 1, 0, 554),
(937, 'Bien definido', 0, 0, 555),
(938, 'Finito', 0, 0, 555),
(939, 'Preciso', 0, 0, 555),
(940, 'Modular', 1, 0, 555),
(941, 'Son independientes de la mÃ¡quina.', 0, 0, 556),
(942, 'Se escribe con cÃ³digos nemotÃ©cnicos.', 0, 0, 556),
(943, 'Se denominan lenguajes ensambladores', 0, 0, 556),
(944, 'Son instrucciones directamente entendidas por la CPU.', 1, 0, 556),
(945, 'Programa para traducir un programa objeto a fuente.', 0, 0, 557),
(946, 'Programa para traducir lenguaje mÃ¡quina a no mÃ¡quina y viceversa.', 0, 0, 557),
(947, 'Todas son mentira.', 0, 0, 557),
(948, 'Programa para traducir instrucciones de lenguaje no mÃ¡quina (C) a lenguaje mÃ¡quina.', 1, 0, 557),
(949, 'Un compilador obtiene cÃ³digo objeto despuÃ©s del ejecutable.', 0, 0, 558),
(950, 'La ejecuciÃ³n de un cÃ³digo ejecutable obtenido con un compilador debe ser supervisada por Ã©ste.', 0, 0, 558),
(951, 'Para ejecutar un programa interpretado no hace falta el intÃ©rprete.', 0, 0, 558),
(952, 'Las anteriores son ciertas.', 0, 0, 558),
(953, 'La ejecuciÃ³n de un programa interpretado es supervisada por el intÃ©rprete.', 1, 0, 558),
(954, 'DefiniciÃ³n del problema.', 0, 0, 559),
(955, 'Desarrollo de la soluciÃ³n.', 0, 0, 559),
(956, 'ExplotaciÃ³n del programa', 0, 0, 559),
(957, 'Compra del programa', 1, 0, 559),
(958, 'Se pueden formar usando cualquier conjunto de nÃºmeros y letras del alfabeto espaÃ±ol.', 0, 0, 560),
(959, 'Deben comenzar con una letra.', 0, 0, 560),
(960, 'Se puede usar utilizar una palabra  reservada como identificador.', 0, 0, 560),
(961, 'Distinguen mayÃºsculas y minÃºsculas.', 1, 0, 560),
(962, 'Los identificadores son sentencias ejecutables.', 0, 0, 561),
(963, 'Las directivas al compilador son sentencias ejecutables.', 0, 0, 561),
(964, 'Las declaraciones de variables son sentencias ejecutables.', 0, 0, 561),
(965, 'Las sentencias ejecutables especifican operaciones de cÃ¡lculo, de entrada salida o de control de flujo', 1, 0, 561),
(966, 'El hardware es la parte lÃ³gica del ordenador y el software es la parte fÃ­sica del ordenador.', 1, 0, 562),
(967, 'El hardware es la parte fÃ­sica del ordenador y el software es la parte lÃ³gica del ordenador.', 0, 0, 562),
(968, 'Bien definido', 0, 0, 563),
(969, 'Modular', 1, 0, 563),
(970, 'Finito ', 0, 0, 563),
(971, 'Preciso', 0, 0, 563),
(972, 'Son independientes de la mÃ¡quina.', 0, 0, 564),
(973, 'Se escribe con cÃ³digos nemotÃ©cnicos.', 0, 0, 564),
(974, 'Son instrucciones directamente entendidas por la CPU.', 1, 0, 564),
(975, 'Se denominan lenguajes ensambladores.', 0, 0, 564),
(976, 'Programa para traducir un programa objeto a fuente.', 0, 0, 565),
(977, 'Programa para traducir lenguaje mÃ¡quina a no mÃ¡quina y viceversa.', 0, 0, 565),
(978, 'Todas son mentira.', 0, 0, 565),
(979, 'Programa para traducir instrucciones de lenguaje no mÃ¡quina (C) a lenguaje mÃ¡quina.', 1, 0, 565),
(980, 'Un compilador obtiene cÃ³digo objeto despuÃ©s del ejecutable.', 0, 0, 566),
(981, 'La ejecuciÃ³n de un cÃ³digo ejecutable obtenido con un compilador debe ser supervisada por Ã©ste.', 0, 0, 566),
(982, 'Para ejecutar un programa interpretado no hace falta el intÃ©rprete.', 0, 0, 566),
(983, 'Las anteriores son ciertas.', 0, 0, 566),
(984, 'La ejecuciÃ³n de un programa interpretado es supervisada por el intÃ©rprete.', 1, 0, 566),
(985, 'DefiniciÃ³n del problema.', 0, 0, 567),
(986, 'Desarrollo de la soluciÃ³n.', 0, 0, 567),
(987, 'ExplotaciÃ³n del programa', 0, 0, 567),
(988, 'Compra del programa', 1, 0, 567),
(989, 'Se pueden formar usando cualquier conjunto de nÃºmeros y letras del alfabeto espaÃ±ol.', 0, 0, 568),
(990, 'Deben comenzar con una letra.', 0, 0, 568),
(991, 'Se puede usar utilizar una palabra  reservada como identificador.', 0, 0, 568),
(992, 'Distinguen mayÃºsculas y minÃºsculas.', 1, 0, 568),
(993, 'Los identificadores son sentencias ejecutables.', 0, 0, 569),
(994, 'Las directivas al compilador son sentencias ejecutables.', 0, 0, 569),
(995, 'Las declaraciones de variables son sentencias ejecutables.', 0, 0, 569),
(996, 'Las sentencias ejecutables especifican operaciones de cÃ¡lculo, de entrada salida o de control de flujo', 1, 0, 569),
(997, 'Falso', 0, 0, 570),
(998, 'Verdadero', 1, 0, 570),
(999, 'True', 0, 0, 571),
(1000, 'False', 0, 0, 571),
(1001, 'EOF', 1, 0, 571),
(1002, 'Un conjunto de variables', 1, 0, 572),
(1003, 'Un conjunto de funciones.', 0, 0, 572),
(1004, 'Cualquier expresiÃ³n aritmÃ©tica acabada en ";"', 0, 0, 572),
(1005, 'Un literal Ãºnicamente.', 0, 0, 573),
(1006, 'El valor de un identifcador de constante Ãºnicamente.', 0, 0, 573),
(1007, 'El valor que devuelva una funciÃ³n', 0, 0, 573),
(1008, 'Todas las anteriores', 1, 0, 573),
(1009, 'Calcula el tanto por ciento de una variable', 0, 0, 574),
(1010, 'DivisiÃ³n', 0, 0, 574),
(1011, 'DivisiÃ³n sin resto', 0, 0, 574),
(1012, 'MÃ³dulo', 1, 0, 574),
(1013, 'Son operadores de alto nivel', 0, 0, 575),
(1014, 'Son operadores de bajo nivel', 1, 0, 575),
(1015, 'float', 0, 0, 576),
(1016, 'signed', 0, 0, 576),
(1017, 'unsigned', 1, 0, 576),
(1018, 'unsigned', 0, 0, 577),
(1019, 'float', 0, 0, 577),
(1020, 'signed', 1, 0, 577),
(1021, 'True si se imprime algo', 0, 0, 578),
(1022, 'False si no imprime', 0, 0, 578),
(1023, 'Entero con el nÃºmero de datos escritos.', 1, 0, 578),
(1024, 'True si estÃ¡ vacÃ­o', 0, 0, 579),
(1025, 'False si estÃ¡ lleno', 0, 0, 579),
(1026, 'NÃºmero de campos leÃ­dos y almacenados en variables.', 1, 0, 579),
(1027, 'TÃ©cnica de programaciÃ³n en la que cada mÃ³dulo, lleva a a cabo VARIAS actividades.', 0, 0, 580),
(1028, 'TÃ©cnica de programaciÃ³n en la que cada mÃ³dulo, lleva a a cabo UNA actividad.', 1, 0, 580),
(1030, 'ANTES de ser invocadas en el cuerpo de la que llama', 1, 0, 581),
(1031, 'Al final del programa', 0, 0, 582),
(1032, 'Al principio del programa', 1, 0, 582),
(1033, 'Coincide con la sintaxis de la cabecera de una definiciÃ³n de la funciÃ³n', 0, 0, 583),
(1034, 'Coincide con la sintaxis de la cabecera de una definiciÃ³n de la funciÃ³n, excepto por el punto y coma', 1, 0, 583),
(1035, 'Falso', 0, 0, 584),
(1036, 'Verdadero', 1, 0, 584),
(1037, 'Entrada/Salida', 0, 0, 585),
(1038, 'Salida', 0, 0, 585),
(1039, 'Entrada', 1, 0, 585),
(1040, 'Entrada/Salida', 0, 0, 586),
(1041, 'Entrada', 0, 0, 586),
(1042, 'Salida', 1, 0, 586),
(1043, 'Entrada', 0, 0, 587),
(1044, 'Salida', 0, 0, 587),
(1045, 'Entrada/Salida', 1, 0, 587),
(1046, 'Lo transferido son valores', 0, 0, 588),
(1047, 'Lo transferido son direcciones de las variables  que contienen esos valores.', 1, 0, 588),
(1048, 'El programa tiene un diseÃ±o modular.', 0, 0, 589),
(1049, 'Cada mÃ³dulo se codifica utilizando las tres estructuras de control bÃ¡sicas: secuencia, selecciÃ³n y repeticiÃ³n.', 0, 0, 589),
(1050, 'Los mÃ³dulos son diseÃ±ados de modo ascendente.', 1, 0, 589),
(1051, 'descomponer una determinada acciÃ³n compleja en tÃ©rminos de un nÃºmero de acciones mÃ¡s compleja capaces de ejecutarlas o que constituyan instrucciones de ordenador disponibles', 0, 0, 590),
(1052, 'descomponer una determinada acciÃ³n compleja en tÃ©rminos de un nÃºmero de acciones mÃ¡s simples capaces de ejecutarlas o que constituyan instrucciones de ordenador disponibles', 1, 0, 590),
(1053, 'Iterativas y selectivas', 0, 0, 591),
(1054, 'Secuenciales, selectivas y repetitivas.', 1, 0, 591),
(1055, 'Posee un solo punto de entrada y un solo punto de salida o fin para el control del programa.', 0, 0, 592),
(1056, 'Existen caminos desde la entrada hasta la salida que se pueden seguir y que pasan por todas las partes del programa.', 0, 0, 592),
(1057, 'Todas las instrucciones son ejecutables y no existen lazos o bucles infinitos.', 0, 0, 592),
(1058, 'Las tres anteriores son correctas', 1, 0, 592),
(1059, 'que el flujo del programa sea secuencial', 0, 0, 593),
(1060, 'que no  existan sentencias que se ejecuten o no en funciÃ³n de condiciones', 0, 0, 593),
(1061, 'Las dos anteriores son falsas', 1, 0, 593),
(1062, 'que sÃ³lo puede tomar mÃ¡s de dos valores', 1, 0, 594),
(1063, 'que sÃ³lo puede tomar dos valores, puede ser verdadera o falsa.', 0, 0, 594),
(1064, 'Falso', 0, 0, 595),
(1065, 'Verdadero', 1, 0, 595),
(1066, 'Cuando las sentencias estÃ¡n anidadas, la sentencia break finaliza la ejecuciÃ³n de todas  las sentencias.', 0, 0, 596),
(1067, 'Cuando las sentencias estÃ¡n anidadas, la sentencia break solamente finaliza la ejecuciÃ³n de la sentencia donde esta incluida.', 1, 0, 596),
(1068, 'no forma parte de sÃ­ mismo o no se define en funciÃ³n de sÃ­ mismo', 0, 0, 597),
(1069, 'forma parte de sÃ­ mismo o se define en funciÃ³n de sÃ­ mismo', 1, 0, 597),
(1070, 'Falso', 0, 0, 598),
(1071, 'Verdadero', 1, 0, 598),
(1072, 'Formal e informal', 0, 0, 599),
(1073, 'Directa e indirecta', 1, 0, 599),
(1074, 'No, no es posible', 0, 0, 600),
(1075, 'SÃ­, pueden llamarse a sÃ­ mismas', 1, 0, 600),
(1076, 'Falso', 0, 0, 601),
(1077, 'Verdadero', 1, 0, 601),
(1078, 'Falso', 0, 0, 602),
(1079, 'Verdero', 1, 0, 602),
(1123, 'Falso', 0, 0, 621),
(1124, 'Verdadero', 1, 0, 621),
(1125, 'True', 0, 0, 622),
(1126, 'False', 0, 0, 622),
(1127, 'EOF', 1, 0, 622),
(1128, 'Un conjunto de variables', 1, 0, 623),
(1129, 'Un conjunto de funciones.', 0, 0, 623),
(1130, 'Cualquier expresiÃ³n aritmÃ©tica acabada en ";"', 0, 0, 623),
(1131, 'Un literal Ãºnicamente.', 0, 0, 624),
(1132, 'El valor de un identifcador de constante Ãºnicamente.', 0, 0, 624),
(1133, 'El valor que devuelva una funciÃ³n', 0, 0, 624),
(1134, 'Todas las anteriores', 1, 0, 624),
(1135, 'Calcula el tanto por ciento de una variable', 0, 0, 625),
(1136, 'DivisiÃ³n', 0, 0, 625),
(1137, 'DivisiÃ³n sin resto', 0, 0, 625),
(1138, 'MÃ³dulo', 1, 0, 625),
(1139, 'Son operadores de alto nivel', 0, 0, 626),
(1140, 'Son operadores de bajo nivel', 1, 0, 626),
(1141, 'float', 0, 0, 627),
(1142, 'signed', 0, 0, 627),
(1143, 'unsigned', 1, 0, 627),
(1144, 'unsigned', 0, 0, 628),
(1145, 'float', 0, 0, 628),
(1146, 'signed', 1, 0, 628),
(1147, 'True si se imprime algo', 0, 0, 629),
(1148, 'False si no imprime', 0, 0, 629),
(1149, 'Entero con el nÃºmero de datos escritos.', 1, 0, 629),
(1150, 'True si estÃ¡ vacÃ­o', 0, 0, 630),
(1151, 'False si estÃ¡ lleno', 0, 0, 630),
(1152, 'NÃºmero de campos leÃ­dos y almacenados en variables.', 1, 0, 630),
(1153, 'TÃ©cnica de programaciÃ³n en la que cada mÃ³dulo, lleva a a cabo VARIAS actividades.', 0, 0, 631),
(1154, 'TÃ©cnica de programaciÃ³n en la que cada mÃ³dulo, lleva a a cabo UNA actividad.', 1, 0, 631),
(1156, 'ANTES de ser invocadas en el cuerpo de la que llama', 1, 0, 632),
(1157, 'Al final del programa', 0, 0, 633),
(1158, 'Al principio del programa', 1, 0, 633),
(1159, 'Coincide con la sintaxis de la cabecera de una definiciÃ³n de la funciÃ³n', 0, 0, 634),
(1160, 'Coincide con la sintaxis de la cabecera de una definiciÃ³n de la funciÃ³n, excepto por el punto y coma', 1, 0, 634),
(1161, 'Falso', 0, 0, 635),
(1162, 'Verdadero', 1, 0, 635),
(1163, 'Entrada/Salida', 0, 0, 636),
(1164, 'Salida', 0, 0, 636),
(1165, 'Entrada', 1, 0, 636),
(1166, 'Entrada/Salida', 0, 0, 637),
(1167, 'Entrada', 0, 0, 637),
(1168, 'Salida', 1, 0, 637),
(1169, 'Entrada', 0, 0, 638),
(1170, 'Salida', 0, 0, 638),
(1171, 'Entrada/Salida', 1, 0, 638),
(1172, 'Lo transferido son valores', 0, 0, 639),
(1173, 'Lo transferido son direcciones de las variables  que contienen esos valores.', 1, 0, 639),
(1174, 'El programa tiene un diseÃ±o modular.', 0, 0, 640),
(1175, 'Cada mÃ³dulo se codifica utilizando las tres estructuras de control bÃ¡sicas: secuencia, selecciÃ³n y repeticiÃ³n.', 0, 0, 640),
(1176, 'Los mÃ³dulos son diseÃ±ados de modo ascendente.', 1, 0, 640),
(1177, 'descomponer una determinada acciÃ³n compleja en tÃ©rminos de un nÃºmero de acciones mÃ¡s compleja capaces de ejecutarlas o que constituyan instrucciones de ordenador disponibles', 0, 0, 641),
(1178, 'descomponer una determinada acciÃ³n compleja en tÃ©rminos de un nÃºmero de acciones mÃ¡s simples capaces de ejecutarlas o que constituyan instrucciones de ordenador disponibles', 1, 0, 641),
(1179, 'Iterativas y selectivas', 0, 0, 642),
(1180, 'Secuenciales, selectivas y repetitivas.', 1, 0, 642),
(1181, 'Posee un solo punto de entrada y un solo punto de salida o fin para el control del programa.', 0, 0, 643),
(1182, 'Existen caminos desde la entrada hasta la salida que se pueden seguir y que pasan por todas las partes del programa.', 0, 0, 643),
(1183, 'Todas las instrucciones son ejecutables y no existen lazos o bucles infinitos.', 0, 0, 643),
(1184, 'Las tres anteriores son correctas', 1, 0, 643),
(1185, 'que el flujo del programa sea secuencial', 0, 0, 644),
(1186, 'que no  existan sentencias que se ejecuten o no en funciÃ³n de condiciones', 0, 0, 644),
(1187, 'Las dos anteriores son falsas', 1, 0, 644),
(1188, 'que sÃ³lo puede tomar mÃ¡s de dos valores', 1, 0, 645),
(1189, 'que sÃ³lo puede tomar dos valores, puede ser verdadera o falsa.', 0, 0, 645),
(1190, 'Falso', 0, 0, 646),
(1191, 'Verdadero', 1, 0, 646),
(1192, 'Cuando las sentencias estÃ¡n anidadas, la sentencia break finaliza la ejecuciÃ³n de todas  las sentencias.', 0, 0, 647),
(1193, 'Cuando las sentencias estÃ¡n anidadas, la sentencia break solamente finaliza la ejecuciÃ³n de la sentencia donde esta incluida.', 1, 0, 647),
(1206, 'no forma parte de sÃ­ mismo o no se define en funciÃ³n de sÃ­ mismo', 0, 0, 654),
(1207, 'forma parte de sÃ­ mismo o se define en funciÃ³n de sÃ­ mismo', 1, 0, 654),
(1208, 'Falso', 0, 0, 655),
(1209, 'Verdadero', 1, 0, 655),
(1210, 'Formal e informal', 0, 0, 656),
(1211, 'Directa e indirecta', 1, 0, 656),
(1212, 'No, no es posible', 0, 0, 657),
(1213, 'SÃ­, pueden llamarse a sÃ­ mismas', 1, 0, 657),
(1214, 'Falso', 0, 0, 658),
(1215, 'Verdadero', 1, 0, 658),
(1216, 'Falso', 0, 0, 659),
(1217, 'Verdero', 1, 0, 659),
(1261, 'do ... while, for ', 0, 0, 678),
(1262, 'do ... while, if y for', 0, 0, 678),
(1263, 'do ... while, while y for', 1, 0, 678),
(1264, 'La condiciÃ³n se verifica al final de cada iteraciÃ³n', 0, 0, 679),
(1265, 'La condiciÃ³n se verifica al principio de cada iteraciÃ³n', 1, 0, 679),
(1266, 'Todas las variables usadas en  la evaluaciÃ³n inicial no tienen por quÃ© tener valor antes de llegar al bucle', 0, 0, 680),
(1267, 'Todas las variables usadas en  la evaluaciÃ³n inicial deben tener valor antes de llegar al bucle', 1, 0, 680),
(1268, 'La condiciÃ³n se verifica al principio de cada iteraciÃ³n', 0, 0, 681),
(1269, 'La condiciÃ³n se verifica al final de cada iteraciÃ³n', 1, 0, 681),
(1270, 'una expresiÃ³n de IniciaciÃ³n', 0, 0, 682),
(1271, 'una expresiÃ³n de CondiciÃ³n,', 0, 0, 682),
(1272, 'una expresiÃ³n de IniciaciÃ³n', 0, 0, 682),
(1273, 'La suma de las tres ActualizaciÃ³n,', 1, 0, 682),
(1274, 'Todos los efectos del operador de la izquierda son ejecutados antes de evaluar la expresiÃ³n de la derecha', 0, 0, 683),
(1275, 'Devuelve el resultado de la Ãºltima expresiÃ³n.', 0, 0, 683),
(1276, 'El operador evalÃºa las expresiones de izquierda a derecha,', 0, 0, 683),
(1277, 'Todas las anteriores son correctas', 1, 0, 683),
(1278, 'No estÃ¡n permitidas', 0, 0, 684),
(1279, 'estÃ¡n permitidas y son aconsejables', 0, 0, 684),
(1280, 'es mejor usar banderas que sentencias de salto', 1, 0, 684),
(1281, 'do ... while, for ', 0, 0, 685),
(1282, 'do ... while, if y for', 0, 0, 685),
(1283, 'do ... while, while y for', 1, 0, 685),
(1284, 'La condiciÃ³n se verifica al final de cada iteraciÃ³n', 0, 0, 686),
(1285, 'La condiciÃ³n se verifica al principio de cada iteraciÃ³n', 1, 0, 686),
(1286, 'Todas las variables usadas en  la evaluaciÃ³n inicial no tienen por quÃ© tener valor antes de llegar al bucle', 0, 0, 687),
(1287, 'Todas las variables usadas en  la evaluaciÃ³n inicial deben tener valor antes de llegar al bucle', 1, 0, 687),
(1288, 'La condiciÃ³n se verifica al principio de cada iteraciÃ³n', 0, 0, 688),
(1289, 'La condiciÃ³n se verifica al final de cada iteraciÃ³n', 1, 0, 688),
(1290, 'una expresiÃ³n de IniciaciÃ³n', 0, 0, 689),
(1291, 'una expresiÃ³n de CondiciÃ³n,', 0, 0, 689),
(1292, 'una expresiÃ³n de IniciaciÃ³n', 0, 0, 689),
(1293, 'La suma de las tres ActualizaciÃ³n,', 1, 0, 689),
(1294, 'Todos los efectos del operador de la izquierda son ejecutados antes de evaluar la expresiÃ³n de la derecha', 0, 0, 690),
(1295, 'Devuelve el resultado de la Ãºltima expresiÃ³n.', 0, 0, 690),
(1296, 'El operador evalÃºa las expresiones de izquierda a derecha,', 0, 0, 690),
(1297, 'Todas las anteriores son correctas', 1, 0, 690),
(1298, 'No estÃ¡n permitidas', 0, 0, 691),
(1299, 'estÃ¡n permitidas y son aconsejables', 0, 0, 691),
(1300, 'es mejor usar banderas que sentencias de salto', 1, 0, 691),
(1301, 'Una estructura dinÃ¡mica heterogenea y lineal de datos', 0, 0, 692),
(1302, 'Una estructura dinÃ¡mica homogÃ©nea y lineal de datos', 1, 0, 692),
(1303, 'el compilador', 0, 0, 693),
(1304, 'el sistema operativo.', 1, 0, 693),
(1305, 'input', 0, 0, 694),
(1306, 'FILE', 1, 0, 694),
(1307, 'False si hay error', 0, 0, 695),
(1308, 'Null si hay error', 1, 0, 695),
(1309, 'un identificador del archivo', 0, 0, 696),
(1310, 'una referencia a la zona de memoria del S.O.', 1, 0, 696),
(1311, 'fexit', 0, 0, 697),
(1312, 'fclose', 1, 0, 697),
(1313, 'respuesta1-> p7', 0, 0, 698),
(1314, 'respuesta2-> p7', 1, 0, 698),
(1315, 'Si ocurre algÃºn error devuelve NULL.', 0, 0, 699),
(1316, 'Si ocurre algÃºn error devuelve EOF.', 1, 0, 699),
(1317, 'respuesta1-> p9', 0, 0, 700),
(1318, 'respuesta2-> p9', 1, 0, 700),
(1319, 'escribe el carÃ¡cter c en la posiciÃ³n indicada por el puntero de L/E del archivo al que referencia pf.', 0, 0, 701),
(1320, 'Devuelve el carÃ¡cter escrito o un EOF si ocurre algÃºn error.', 0, 0, 701),
(1321, 'Ambas son correctas', 1, 0, 701),
(1322, 'Devuelve el nÃºmero de caracteres escritos o NULL si hay algÃºn error', 1, 0, 702),
(1323, 'Devuelve el nÃºmero de caracteres escritos o un valor negativo si hay algÃºn error.', 0, 0, 702),
(1324, 'Una estructura dinÃ¡mica heterogenea y lineal de datos', 0, 0, 703),
(1325, 'Una estructura dinÃ¡mica homogÃ©nea y lineal de datos', 1, 0, 703),
(1326, 'el compilador', 0, 0, 704),
(1327, 'el sistema operativo.', 1, 0, 704),
(1328, 'input', 0, 0, 705),
(1329, 'FILE', 1, 0, 705),
(1330, 'False si hay error', 0, 0, 706),
(1331, 'Null si hay error', 1, 0, 706),
(1332, 'un identificador del archivo', 0, 0, 707),
(1333, 'una referencia a la zona de memoria del S.O.', 1, 0, 707),
(1334, 'fexit', 0, 0, 708),
(1335, 'fclose', 1, 0, 708),
(1336, 'respuesta1-> p7', 0, 0, 709),
(1337, 'respuesta2-> p7', 1, 0, 709),
(1338, 'Si ocurre algÃºn error devuelve NULL.', 0, 0, 710),
(1339, 'Si ocurre algÃºn error devuelve EOF.', 1, 0, 710),
(1340, 'respuesta1-> p9', 0, 0, 711),
(1341, 'respuesta2-> p9', 1, 0, 711),
(1342, 'escribe el carÃ¡cter c en la posiciÃ³n indicada por el puntero de L/E del archivo al que referencia pf.', 0, 0, 712),
(1343, 'Devuelve el carÃ¡cter escrito o un EOF si ocurre algÃºn error.', 0, 0, 712),
(1344, 'Ambas son correctas', 1, 0, 712),
(1345, 'Devuelve el nÃºmero de caracteres escritos o NULL si hay algÃºn error', 1, 0, 713),
(1346, 'Devuelve el nÃºmero de caracteres escritos o un valor negativo si hay algÃºn error.', 0, 0, 713);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `alumno`
--
ALTER TABLE `alumno`
  ADD PRIMARY KEY (`idAlumno`);

--
-- Indices de la tabla `alumno_has_cuestionario`
--
ALTER TABLE `alumno_has_cuestionario`
  ADD PRIMARY KEY (`alumno_idAlumno`,`cuestionario_idCuestionario`), ADD KEY `fk_alumno_has_cuestionario_cuestionario1_idx` (`cuestionario_idCuestionario`), ADD KEY `fk_alumno_has_cuestionario_alumno1_idx` (`alumno_idAlumno`);

--
-- Indices de la tabla `alumno_has_respuesta`
--
ALTER TABLE `alumno_has_respuesta`
  ADD PRIMARY KEY (`alumno_idAlumno`,`respuesta_idRespuesta`), ADD KEY `fk_alumno_has_respuesta_respuesta1_idx` (`respuesta_idRespuesta`), ADD KEY `fk_alumno_has_respuesta_alumno1_idx` (`alumno_idAlumno`);

--
-- Indices de la tabla `cuestionario`
--
ALTER TABLE `cuestionario`
  ADD PRIMARY KEY (`idCuestionario`);

--
-- Indices de la tabla `lti_keys_users`
--
ALTER TABLE `lti_keys_users`
  ADD PRIMARY KEY (`id_lti_keys`);

--
-- Indices de la tabla `pregunta`
--
ALTER TABLE `pregunta`
  ADD PRIMARY KEY (`idPregunta`), ADD KEY `fk_pregunta_cuestionario1_idx` (`cuestionario_idCuestionario`);

--
-- Indices de la tabla `respuesta`
--
ALTER TABLE `respuesta`
  ADD PRIMARY KEY (`idRespuesta`), ADD KEY `fk_respuesta_pregunta1_idx` (`pregunta_idPregunta`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cuestionario`
--
ALTER TABLE `cuestionario`
  MODIFY `idCuestionario` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=19;
--
-- AUTO_INCREMENT de la tabla `lti_keys_users`
--
ALTER TABLE `lti_keys_users`
  MODIFY `id_lti_keys` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT de la tabla `pregunta`
--
ALTER TABLE `pregunta`
  MODIFY `idPregunta` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=730;
--
-- AUTO_INCREMENT de la tabla `respuesta`
--
ALTER TABLE `respuesta`
  MODIFY `idRespuesta` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=1347;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `alumno_has_cuestionario`
--
ALTER TABLE `alumno_has_cuestionario`
ADD CONSTRAINT `fk_alumno_has_cuestionario_alumno1` FOREIGN KEY (`alumno_idAlumno`) REFERENCES `alumno` (`idAlumno`) ON DELETE CASCADE ON UPDATE CASCADE,
ADD CONSTRAINT `fk_alumno_has_cuestionario_cuestionario1` FOREIGN KEY (`cuestionario_idCuestionario`) REFERENCES `cuestionario` (`idCuestionario`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `alumno_has_respuesta`
--
ALTER TABLE `alumno_has_respuesta`
ADD CONSTRAINT `fk_alumno_has_respuesta_alumno1` FOREIGN KEY (`alumno_idAlumno`) REFERENCES `alumno` (`idAlumno`) ON DELETE CASCADE ON UPDATE CASCADE,
ADD CONSTRAINT `fk_alumno_has_respuesta_respuesta1` FOREIGN KEY (`respuesta_idRespuesta`) REFERENCES `respuesta` (`idRespuesta`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `pregunta`
--
ALTER TABLE `pregunta`
ADD CONSTRAINT `fk_pregunta_cuestionario1` FOREIGN KEY (`cuestionario_idCuestionario`) REFERENCES `cuestionario` (`idCuestionario`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `respuesta`
--
ALTER TABLE `respuesta`
ADD CONSTRAINT `fk_respuesta_pregunta1` FOREIGN KEY (`pregunta_idPregunta`) REFERENCES `pregunta` (`idPregunta`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
