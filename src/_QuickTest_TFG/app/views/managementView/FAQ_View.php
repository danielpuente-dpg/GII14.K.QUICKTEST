<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title><?php echo _("QuickTest"); ?></title>


    <script src="/_QuickTest_TFG/app/webroot/js/jquery-2.1.4.min.js"></script>
    <link rel="stylesheet" href="/_QuickTest_TFG/app/webroot/css/bootstrap.min.css">
    <link rel="stylesheet" href="/_QuickTest_TFG/app/webroot/css/freelancer.css">
    <script src="/_QuickTest_TFG/app/webroot/js/bootstrap.min.js"></script>
    <link href="/_QuickTest_TFG/app/webroot/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="http://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">
    <link href="http://fonts.googleapis.com/css?family=Lato:400,700,400italic,700italic" rel="stylesheet"
          type="text/css">


    <script type="text/javascript" src="/_QuickTest_TFG/app/views/default/myJS/faq.js"></script>

    <style type="text/css">


        a:link {
            text-decoration: none;
        }


    </style>

</head>

<body style="padding-top: 80px;">

<!-- Navigation -->
<nav class="navbar navbar-default navbar-fixed-top " style="height: 91px; top: -25px;">

    <div class="row">
        <div class="col-xs-2">
            <p class="navbar-text text-left">
                QuickTest</p>
        </div>

        <div class="col-xs-1 ">


        </div>

        <div class="col-xs-9 ">

            <ul class="nav navbar-nav navbar-right">
                <li>
                    <p class="navbar-text ">
                        <small><?php echo $_GET['nombreAsig']; ?> </small>
                    </p>
                </li>
                <li>

                    <a id="botonLogout" class="btn btn-primary      navbar-btn"
                       data-toggle="tooltip" title="<?php echo _("Salir QuickTest"); ?>"
                       href="<?php echo $_GET['ltiConsumerURL']; ?>"
                       type="button"
                       style="top: -5px;">

                        <span id="logout" class="glyphicon glyphicon-log-out"></span>

                    </a>
                </li>

            </ul>

        </div>


</nav>

<div class="row">
    <div class="col-lg-2  ">


        <ol class="breadcrumb" style="background-color: rgba(0, 0, 0, 0.009); ">
            <li><a href="/_QuickTest_TFG/index.php">Home</a>
            </li>
            <li class="active">FAQ</li>
        </ol>
    </div>

</div>


<!-- Page Heading/Breadcrumbs -->
<div class="row">

    <div class="col-lg-12 col-lg-push-1">

        <h1 class="page-header">FAQ
            <small>Preguntas frecuentes</small>
        </h1>

    </div>
</div>

<!-- /.row -->

<!-- Content Row -->
<div class="row">
<div class="container">
<div class="col-lg-12">
<div class="panel-group" id="accordion">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h4 class="panel-title text-lowercase" style="text-transform:initial;">
                <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion"
                   onclick="cargarTiposComodin();"
                   href="#collapseComodines"><?php echo _("Tipos de comodín y para qué sirven"); ?></a>
            </h4>
        </div>
        <div id="collapseComodines" class="panel-collapse collapse">
            <div class="panel-body">


                <div class="col-lg-1 " id="explicComodines"></div>


            </div>
        </div>
    </div>
</div>
<div class="panel panel-default">
    <div class="panel-heading">
        <h4 class="panel-title text-lowercase" style="text-transform:initial;">
            <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion"
               onclick="cargarOrdenExplicacion();"
               href="#collapseOrden"><?php echo _("¿Cómo influye la rapidez a la hora de responder?"); ?></a>
        </h4>
    </div>
    <div id="collapseOrden" class="panel-collapse collapse">
        <div class="panel-body">



            <div class="col-lg-1 container   " id="explicOrden"></div>


        </div>
    </div>
</div>

<!-- /.panel -->
<div class="panel panel-default">
    <div class="panel-heading">
        <h4 class="panel-title text-lowercase" style="text-transform:initial;">
            <a class=" accordion-toggle" data-target="#collapsePasosParaPublicar" data-toggle="collapse"
               data-parent="#accordion" href="#collapsePasosParaPublicar"
               aria-expanded="false"><?php echo _("Pasos para publicar un cuestionario"); ?></a>


        </h4>
    </div>


    <div class="panel-body collapse" id="collapsePasosParaPublicar">
        <div class="col-lg-7 col-lg-push-3">
            <div id="divPasosPublicar"></div>
        </div>


    </div>
</div>

<div class="panel panel-default">
    <div class="panel-heading">
        <h4 class="panel-title text-lowercase" style="text-transform:initial;">
            <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion"
               href="#collapseThree"><?php echo _("¿Existe algún cuestionario de prueba?"); ?></a>
        </h4>
    </div>
    <div id="collapseThree" class="panel-collapse collapse">
        <div class="panel-body">
            <h8><?php echo _("Sí."); ?></h8>
            <br>


            <small id="masInfo">
                <?php echo _("Se trata de un cuestionario especial que no puede ser borrado."); ?>
            </small>
            <small id="masInfo">
                <?php echo _("Está creado para simular una situación real, en la que poder probar QuickTest."); ?>
            </small>
            <ul>
                <li><?php echo _("Tiene 100 alumnos conectados."); ?></li>
                <li><?php echo _("Las distintas respuestas, ya han sido respondidas por un determinado número de usuarios"); ?></li>
                <li><?php echo _("Así se genera un contexto, en el que poder probar los diferentes comodines."); ?></li>

            </ul>

            <h5 style="text-transform:initial;" class="text-success"><?php echo _("Para usarlo crea un actividad con idCuestionario=1."); ?></h5>




        </div>
    </div>
</div>

<div class="panel panel-default">
    <div class="panel-heading">
        <h4 class="panel-title text-lowercase" style="text-transform:initial;">
            <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion"
               href="#collapseFive"><?php echo _("¿Cómo puedo ocultar un cuestionario publicado?"); ?></a>
        </h4>
    </div>
    <div id="collapseFive" class="panel-collapse collapse">
        <div class="panel-body">

            <div class="row">
                <div class="container">
                    <label>Tienes dos opciones:</label>

                    <div class="row">
                        <div class="col-lg-8 col-lg-push-1">
                            <h6>
                                <?php echo _("1. Ocultando el cuestionario desde tu plataforma: "); ?>
                            </h6>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-8 col-lg-push-2">
                            <ul>
                                <li>
                                    <?php echo _("Accede a tu plataforma, y ve al curso en el que se encuentra publicado el cuestionario. "); ?>
                                </li>
                                <li>
                                    <?php echo _("Activa la edicion de contenido. "); ?>
                                </li>
                                <div class="row">
                                    <div class="col-lg-8 col-lg-push-2">
                                        <img class="img-responsive img-rounded"
                                             src="/_QuickTest_TFG/app/webroot/images/activarEdicion.PNG"
                                             alt="activarEdicion">
                                    </div>
                                </div>
                                <li>
                                    <?php echo _("Pon tu cuestionario como oculto. "); ?>
                                </li>
                                <div class="row">
                                    <div class="col-lg-8 col-lg-push-1">
                                        <img class="img-responsive img-rounded"
                                             src="/_QuickTest_TFG/app/webroot/images/ocultarCuestionario.PNG"
                                             alt="ocultarCuestionario">
                                    </div>
                                </div>

                            </ul>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-8 col-lg-push-1">
                            <h6>
                                <?php echo _("2. Eliminando el cuestionario, desde la página de gestión: "); ?>
                            </h6>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-12 col-lg-push-1">
                            <small class="text-danger">
                                <?php echo _("Esta opción, NO elimina el enlace en tu plataforma LTI. "); ?>
                            </small>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-12 col-lg-push-2">
                            <ul>
                                <li>
                                    <?php echo _("Abre el desplegable correspondiente al cuestionario que quieres borrar."); ?>
                                </li>
                                <li>
                                    <?php echo _("Marca la opción de eliminar."); ?>
                                </li>
                                <div class="row">
                                    <div class="col-lg-8 col-lg-pull-1">
                                        <img class="img-responsive img-rounded"
                                             src="/_QuickTest_TFG/app/webroot/images/eliminarCuestionario.PNG"
                                             alt="ocultarCuestionario">
                                    </div>
                                </div>
                            </ul>


                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>
<!-- /.panel -->

<!-- /.panel -->
<div class="panel panel-default">
    <div class="panel-heading">
        <h4 class="panel-title text-lowercase" style="text-transform:initial;">
            <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion"
               href="#collapseThree"><?php echo _("¿Qué es LTI?"); ?></a>
        </h4>
    </div>
    <div id="collapseThree" class="panel-collapse collapse">
        <div class="panel-body">
            <h8><?php echo _("Se trata de un estándar creado por la IMS."); ?></h8>
            <br>


            <small id="masInfo">
                <?php echo _("Tiene como objetivo ofrecer una estructura uniforme");?>
                <?php echo _("para integrar cualquier producto que cumpla con el estándar en una plataforma educativa."); ?>
            </small>


            <hr>
            <small><?php echo _("Visita su web oficial para más información:"); ?></small>
            <br>
            <a href="http://www.imsglobal.org/toolsinteroperability2.cfm">
                <small>IMS GLOBAL Learning Consortium</small>
            </a>
        </div>
    </div>
</div>
<!-- /.panel -->
<div class="panel panel-default">
    <div class="panel-heading">
        <h4 class="panel-title text-lowercase" style="text-transform:initial;">
            <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion"
               href="#collapseFour"><?php echo _("¿Es imprescindible acceder desde una plataforma LTI para usar QuickTest?"); ?></a>
        </h4>
    </div>
    <div id="collapseFour" class="panel-collapse collapse">
        <div class="panel-body">
            <h5 class="text-success" style="display: inline;"><?php echo _("Sí, "); ?></h5>

            <label style="display: inline;"><?php echo _("por varios motivos:"); ?></label>
            <ul>
                <li><?php echo _("Aumenta la seguridad al guardar tus calificaciones, en la plataforma desde la que accedes."); ?></li>
                <li><?php echo _("Hace que QuickTest sea compatible para cualquier plataforma que cumpla con el estándar LTI."); ?></li>

            </ul>
            <label><?php echo _("Si accedes por otro cauce, verás una pantalla como la siguiente:"); ?></label>

            <div class="col-lg-8 col-lg-push-2">
                <img class="img-responsive img-rounded"
                     src="/_QuickTest_TFG/app/webroot/images/accesoDenegado.PNG"
                     alt="accesoDenegado">
            </div>
        </div>
    </div>
</div>
<!-- /.panel -->

<div class="panel panel-default">
    <div class="panel-heading">
        <h4 class="panel-title text-lowercase" style="text-transform:initial; ">
            <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion"
               href="#collapseSix"><?php echo _("He olvidado mi 'clave de cliente' o mi 'URL de inicio'."); ?></a>
        </h4>
    </div>
    <div id="collapseSix" class="panel-collapse collapse">
        <div class="panel-body">
            <div class="row">
                <div class="container">
                    <label><?php echo _("No hay problema, sólo necesitas: "); ?></label>

                    <div class="row">
                        <div class="col-lg-8 col-lg-push-1">
                            <ul>
                                <li>
                                    <?php echo _("Tu email. "); ?>
                                </li>
                                <li>
                                    <?php echo _("Tu clave personal. "); ?>
                                </li>
                            </ul>


                        </div>
                    </div>
                    <div class="row">
                        <div class="container">
                            <label><?php echo _("Clica "); ?></label>
                            <a href="/_QuickTest_TFG/app/views/managementView/startQuickTest_View.php"><?php echo _("aquí "); ?></a>
                            <label><?php echo _("para recuperar tus datos."); ?></label>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>


<hr>

<!-- Footer -->
<footer>
    <div class="row">
        <div class="col-lg-2">
            <p>QuickTest 2015</p>
        </div>
    </div>
</footer>

</div>
</div>

</body>

</html>
