<!DOCTYPE html>
<!--vista para gestionar un usuario-->

<html lang="es">

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

    <script type="text/javascript" src="app/views/default/myJS/cuestionario_Gestionar.js"></script>

</head>

<body style=" padding-top: 100px">

<!-- ------------------------------------------BARRA DE NAVEGACION ---------------------------------------------------------  !-->

<nav class="navbar navbar-default navbar-fixed-top " style="height: 93px; top: -25px;">

    <div class="row">
        <div class="col-xs-11">

            <p class="navbar-text ">
                <small><?php echo $nombreAsig; ?> </small>
            </p>

        </div>

        <div class="col-xs-1 ">

            <ul class="nav navbar-nav navbar-right">

                <li>

                    <a id="botonLogout" class="btn btn-primary pull-right navbar-btn"
                       data-toggle="tooltip" title="<?php echo _("Salir QuickTest"); ?>"
                       href="<?php echo $ltiConsumerURL; ?>"
                       type="button"
                       style="top: -5px;  ">

                        <span id="logout" class="glyphicon glyphicon-log-out"></span>

                    </a>
                </li>

            </ul>

        </div>

</nav>


<div class="container" id="page-wrapper">
    <div class="row">
        <div class="col-lg-11">
            <h1 class="page-header">QuickTest</h1>
        </div>

    </div>
</div>

<div class="row">
<div class="col-lg-10 col-lg-push-1">
<div class="col-lg-3 col-md-6">
    <div class="panel panel-success">
        <div class="panel-heading">
            <div class="row">
                <div class="col-xs-3">
                    <i class="fa fa-user fa-5x"></i>
                </div>
                <div class="col-xs-9 text-right">
                    <div class="huge"><?php echo _("Usuario"); ?></div>
                    <div><?php echo $nombreAlu; ?></div>
                </div>
            </div>
        </div>

        <a href="#" class="dropdown-toggle" data-toggle="dropdown" id="detallesUsuario">
            <div class="panel-footer">
                <span class="pull-left text-success"><?php echo _("Más detalles"); ?></span>
                        <span class="pull-right text-success"><i
                                class="fa fa-arrow-circle-right text-success"></i></span>

                <div class="clearfix"></div>
            </div>
        </a>

        <ul class="dropdown-menu dropdown-alerts" aria-labelledby="detallesUsuario">

            <li role="presentation"
                class="dropdown-header text-primary"><h5><?php echo _("Clave del cliente"); ?></h5></li>
            <li role="presentation">
                <h5 class="text-muted text-center text-success" tabindex="-1"
                    role="menuitem"><?php echo $datosLTI['consumer_key']; ?></h5>

            </li>

            <li class="divider"></li>

            <div class="panel-body">


                <dl class="dl-horizontal">
                    <dt>
                    <h5><?php echo _("Nombre"); ?></h5></dt>
                    <dd><h6 class="text-muted"><?php echo $nombreAlu; ?></h6></dd>


                    <dt>
                    <h5> <?php echo _("Apellidos"); ?> </h5> </dt>
                    <dd><h6 class="text-muted"><?php echo $apeAlu; ?></h6></dd>


                    <dt>
                    <h5><?php echo _("Email"); ?></h5></dt>
                    <dd class="  help-block"><?php echo $emailLTI; ?>  </dd>


                    <dt>
                    <h5><?php echo _("Rol"); ?></dt>
                    <dd><h6 class="text-muted"><?php echo _("Profesor"); ?></h6></dd>


                    <dt>
                    <h5><?php echo _("Idioma"); ?></dt>
                    <dd><h6 class="text-muted">
                            <?php if ($lang == "en") {
                                echo "English";
                            } else {
                                echo "Español";
                            }?>
                        </h6>
                    </dd>


                </dl>

            </div>
        </ul>
    </div>
</div>

<div class="col-lg-3 col-md-6">
    <div class="panel panel-info">
        <div class="panel-heading">
            <div class="row">
                <div class="col-xs-3">
                    <i class="fa fa-tasks fa-5x"></i>
                </div>
                <div class="col-xs-9 text-right">

                    <div><?php echo _("Cuestionarios"); ?></div>
                    <h3 style = "display:inline;  "><?php echo(sizeof($cuestAsig)); ?></h3>
                </div>
            </div>
        </div>
        <a href="#" data-target="#cuestionariosCreados" data-toggle="collapse" onclick="gotoCuestionariosCreados();">
            <div class="panel-footer">
                <span class="pull-left text-info"><?php echo _("Ver cuestionarios"); ?></span>
                <span class="pull-right text-info"><i class="fa fa-arrow-circle-right text-info"></i></span>

                <div class="clearfix"></div>
            </div>
        </a>
    </div>
</div>

<div class="col-lg-3 col-md-6">
    <div class="panel panel-warning">
        <div class="panel-heading">
            <div class="row">
                <div class="col-xs-3">
                    <i class="fa fa-plus-square fa-5x"></i>
                </div>
                <div class="col-xs-9 text-right">

                    <div><?php echo _("¿Quieres crear un nuevo cuestionario?"); ?></div>
                </div>
            </div>
        </div>


        <div>
            <form style="display:none" id="formCrearNuevo" name="formCrearNuevo" method="post"
                  action="app/views/cuestionarioView/cuestionario_CrearNuevo_View.php">
                <div hidden="true">

                    <label class="col-lg-2 control-label  ">idAsignatura</label>

                    <div class="col-lg-2">
                        <input class="form-control input-sm" name="idAsig" type="text"
                               value="<?php echo $idAsig; ?>"><br>
                    </div>
                    <label class="col-lg-2 control-label">Nombre asignatura</label>

                    <div class="col-lg-2">
                        <input class="form-control input-sm" name="nombreAsig" type="text"
                               value="<?php echo $nombreAsig; ?>"><br>
                    </div>
                    <label class="col-lg-2 control-label">Es profesor</label>

                    <div class="col-lg-2">
                        <input class="form-control input-sm" name="isProfesor" type="text"
                               value="<?php echo $isProfesor; ?>"><br>
                    </div>
                    <label class="col-lg-2 control-label">Nombre profesor</label>

                    <div class="col-lg-2">
                        <input class="form-control input-sm " name="nombreAlu" type="text"
                               value="<?php echo $nombreAlu; ?>"><br>
                    </div>
                    <label class="col-lg-2 control-label">Apellido Profesor</label>

                    <div class="col-lg-2">
                        <input class="form-control input-sm" name="apeAlu" type="text"
                               value="<?php echo $apeAlu; ?>"><br>
                    </div>
                    <label class="col-lg-2 control-label">id Usuario</label>

                    <div class="col-lg-2">
                        <input class="form-control input-sm" name="idUser" type="text"
                               value="<?php echo $idUser; ?>"><br>
                    </div>

                    <label class="col-lg-2 control-label"><?php echo _("lang"); ?></label>

                    <div class="col-lg-2">
                        <input class="form-control input-sm" id="lang" name="lang" type="text"
                               value="<?php echo $lang; ?>"><br>
                    </div>

                    <label class="col-lg-2 control-label"><?php echo _("email"); ?></label>

                    <div class="col-lg-2">
                        <input class="form-control input-sm" id="emailLTI" name="emailLTI" type="text"
                               value="<?php echo $emailLTI; ?>"><br>
                    </div>

                    <label class="col-lg-2 control-label"><?php echo _("Clave de cliente"); ?></label>

                    <div class="col-lg-2">
                        <input class="form-control input-sm" id="consumer_key" name="consumer_key" type="text"
                               value="<?php echo $datosLTI['consumer_key']; ?>"><br>
                    </div>

                    <label class="col-lg-2 control-label"><?php echo _("URL de inicio"); ?></label>

                    <div class="col-lg-2">
                        <input class="form-control input-sm" id="resource_link" name="resource_link" type="text"
                               value="<?php echo $datosLTI['resource_link']; ?>"><br>
                    </div>

                    <label class="col-lg-2 control-label"><?php echo _("launch_presentation_return_url"); ?></label>

                    <div class="col-lg-2">
                        <input class="form-control input-sm" id="launch_presentation_return_url"
                               name="launch_presentation_return_url" type="text"
                               value="<?php echo $ltiConsumerURL; ?>"><br>
                    </div>
                </div>
                <br><br>
        </div>


        <a href="#" id="CrearNuevo" style="text-decoration:none;" onclick="subForm();">
            <div class="panel-footer">


                <span class="text-warning"><?php echo _("Click para crear uno nuevo"); ?></span>
                <span class="pull-right text-warning"><i class="fa fa-arrow-circle-right text-alert"></i></span>

                </form>

                <div class="clearfix"></div>
            </div>
        </a>

    </div>
</div>
<div class="col-lg-3 col-md-6">
    <div class="panel panel-danger">
        <div class="panel-heading">
            <div class="row">
                <div class="col-xs-3">
                    <i class="fa fa-support fa-5x"></i>
                </div>
                <div class="col-xs-9 text-right">
                    <div class="huge"><?php echo _("¿Necesitas ayuda para publicar un cuestionario?"); ?></div>
                </div>
            </div>
        </div>
        <a href="app/views/managementView/FAQ_View.php?nombreAsig=<?php echo $nombreAsig; ?>&ltiConsumerURL=<?php echo urlencode($ltiConsumerURL); ?>">
            <div class="panel-footer">
                <span class="pull-left text-danger"><?php echo _("FAQ"); ?></span>
                <span class="pull-right"><i class="fa fa-arrow-circle-right text-danger"></i></span>

                <div class="clearfix"></div>
            </div>
        </a>


    </div>
</div>
</div>
</div>

<div class="container">
<div class="well" style="max-width: 900px; margin: 0 auto 10px;">


<button type="button" class="btn btn-info btn-default btn-block" data-toggle="collapse"
        data-target="#cuestionariosCreados"><?php echo _("Ver cuestionarios ya creados"); ?>
</button>


<div id="cuestionariosCreados" class="collapse">
    <div class="well" style="max-width: 900px; margin: 0 auto 10px; ">
        <ul class="list-group">
        <li id="listCuest<?php echo 1; ?>" class="list-group-item">
            <a type="submit"
               class="text-info"
               data-idCuestVisualizar="1"
               href="index.php?data-idCuestVisualizar=1&idAsig=<?php echo $idAsig; ?>&nombreAsig=<?php echo $nombreAsig; ?>&nombreCuest=Cuestionario de prueba&isProfesor=1 &idUser=<?php echo $idUser; ?> &nombreAlu=<?php echo $nombreAlu; ?> &apeAlu=<?php echo $apeAlu; ?>&lang=<?php echo $lang; ?>">
                <em>Cuestionario de demostración</em>
            </a>

                                    <span class="pull-right" type="button">
                                         <a href="#"

                                            style="text-decoration:none;"
                                            class="glyphicon glyphicon-upload pull-right text-primary"
                                            data-toggle="tooltip modal"
                                            title="<?php echo _("Publicar cuestionario"); ?>"
                                            data-target="#myModal1"
                                            data-idCuest="1"
                                            onclick="clickModal(this);"
                                            id="myBtn1">&nbsp;
                                         </a>
                                    </span>


            <!-- Modal pasos para publicar -->
            <div class="modal fade" id="myModal1"
                 name="myModal"
                 role="dialog">
                <div class="modal-dialog modal-lg">

                    <!-- Modal content-->
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close"
                                    data-dismiss="modal">&times;</button>
                            <h4 class="modal-title"><?php echo _("Para publicar, sigue estos pasos:"); ?></h4>
                        </div>
                        <div class="modal-body">

                            <label
                                class="text-muted"><?php echo _("Crea una actividad desde tu plataforma LTI de tipo:"); ?>
                                &nbsp;&nbsp;</label>

                            <h5 class="text-success"
                                style="display:inline"><?php echo _("Actividad externa"); ?></h5>
                            <br><br><br>

                            <label
                                class="help-block "><?php echo _("Y copia estos datos en las casillas correspondientes"); ?>
                                &nbsp;&nbsp;</label>

                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <th><?php echo _("URL de inicio"); ?></th>
                                    <th><?php echo _("Clave de cliente"); ?></th>
                                    <th><?php echo _("Shared secret"); ?></th>
                                    <th><?php echo _("Parámetros personalizados"); ?></th>
                                </tr>

                                </thead>
                                <tbody>
                                <tr>
                                    <td>
                                        <small><?php echo $datosLTI['resource_link']; ?>
                                        </small>
                                    </td>
                                    <td>
                                        <small><?php echo $datosLTI['consumer_key']; ?>
                                    </td>
                                    <td>
                                        <small><?php echo _("Tu contraseña de QuickTest"); ?> </small>
                                    </td>
                                    <td>
                                        <small>
                                            idCuestionario=<?php echo 1; ?></small>
                                    </td>
                                </tr>
                                </tbody>
                            </table>

                            <h5 class="text-danger text-center"><?php echo _("IMPORTANTE"); ?>:</h5>
                            <label
                                class="help-block "><?php echo _("En la pestaña 'privacidad', no olvides marcar:"); ?>
                                &nbsp;&nbsp;</label>

                            <div class="row">
                                <div class="checkbox col-lg-6 col-lg-push-1">
                                    <label>
                                        <input type="checkbox" checked="true"
                                               disabled><?php echo _("Aceptar calificaciones desde la herramienta"); ?>
                                    </label>
                                    <br>
                                    <label>
                                        <input type="checkbox" checked="true"
                                               disabled><?php echo _("Compartir el e-mail del usuario con la herramienta"); ?>
                                    </label>
                                    <br>

                                    <label>
                                        <input type="checkbox" checked="true"
                                               disabled><?php echo _("Aceptar calificaciones desde la herramienta"); ?>
                                    </label>


                                </div>
                            </div>

                            <label
                                class="help-block "><?php echo _("Tras seguir estos pasos, deberías tener algo similar a esto:"); ?>
                            </label>

                            <div class="row">
                                <div class="checkbox col-lg-7 col-lg-push-2">


                                    <img class="img-responsive"
                                         src="/_QuickTest_TFG/app/webroot/images/configurarLTI.PNG"
                                         alt="configurarLTI">
                                </div>
                            </div>

                        </div>
                        <br>
                        <br>
                        <br>

                        <div class="modal-footer">
                            <button type="button" class="btn btn-default"
                                    data-dismiss="modal">Close
                            </button>
                        </div>
                    </div>

                </div>
            </div>


             </li>

            <?php
            if (!isset ($cuestAsig)) {
                ?>
                <li id="noCuestionarios" class="list-group-item">
                    <p class="text-muted text-center">
                        <small><?php echo _("Aún no existen cuestionarios creados"); ?></small>
                    </p>
                </li>
            <?php
            } else {

                foreach ($cuestAsig as $cuest) {
                    ?>


                    <li id="listCuest<?php echo $cuest['idCuestionario']; ?>" class="list-group-item">
                        <a type="submit"
                           class="text-info"
                           data-idCuestVisualizar="<?php echo $cuest['idCuestionario']; ?>"
                           href="index.php?data-idCuestVisualizar=<?php echo $cuest['idCuestionario']; ?>&idAsig=<?php echo $idAsig; ?>&nombreAsig=<?php echo $nombreAsig; ?>&nombreCuest=<?php echo $cuest['nombreCuestionario']; ?> &isProfesor=1 &idUser=<?php echo $idUser; ?> &nombreAlu=<?php echo $nombreAlu; ?> &apeAlu=<?php echo $apeAlu; ?>&lang=<?php echo $lang; ?>">
                            <em><?php echo $cuest['nombreCuestionario']; ?></em>
                        </a>


                                <span class="pull-right" type="button" data-toggle="dropdown">

                                    <a href="#"
                                       style="text-decoration:none;"
                                       class="glyphicon glyphicon-chevron-down pull-right text-primary"></a>
                                </span>

                                    <span class="pull-right" type="button">
                                         <a href="#"

                                            style="text-decoration:none;"
                                            class="glyphicon glyphicon-upload pull-right text-primary"
                                            data-toggle="tooltip modal"
                                            title="<?php echo _("Publicar cuestionario"); ?>"
                                            data-target="#myModal<?php echo $cuest['idCuestionario']; ?>"
                                            data-idCuest="<?php echo $cuest['idCuestionario']; ?>"
                                            onclick="clickModal(this);"
                                            id="myBtn<?php echo $cuest['idCuestionario']; ?>">&nbsp;
                                         </a>
                                    </span>


                        <!-- Modal pasos para publicar -->
                        <div class="modal fade" id="myModal<?php echo $cuest['idCuestionario']; ?>"
                             name="myModal"
                             role="dialog">
                            <div class="modal-dialog modal-lg">

                                <!-- Modal content-->
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close"
                                                data-dismiss="modal">&times;</button>
                                        <h4 class="modal-title"><?php echo _("Para publicar, sigue estos pasos:"); ?></h4>
                                    </div>
                                    <div class="modal-body">

                                        <label
                                            class="text-muted"><?php echo _("Crea una actividad desde tu plataforma LTI de tipo:"); ?>
                                            &nbsp;&nbsp;</label>

                                        <h5 class="text-success"
                                            style="display:inline"><?php echo _("Actividad externa"); ?></h5>
                                        <br><br><br>

                                        <label
                                            class="help-block "><?php echo _("Y copia estos datos en las casillas correspondientes"); ?>
                                            &nbsp;&nbsp;</label>

                                        <table class="table table-striped">
                                            <thead>
                                            <tr>
                                                <th><?php echo _("URL de inicio"); ?></th>
                                                <th><?php echo _("Clave de cliente"); ?></th>
                                                <th><?php echo _("Shared secret"); ?></th>
                                                <th><?php echo _("Parámetros personalizados"); ?></th>
                                            </tr>

                                            </thead>
                                            <tbody>
                                            <tr>
                                                <td>
                                                    <small><?php echo $datosLTI['resource_link']; ?>
                                                    </small>
                                                </td>
                                                <td>
                                                    <small><?php echo $datosLTI['consumer_key']; ?>
                                                </td>
                                                <td>
                                                    <small><?php echo _("Tu contraseña de QuickTest"); ?> </small>
                                                </td>
                                                <td>
                                                    <small>
                                                        idCuestionario=<?php echo $cuest['idCuestionario']; ?></small>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>

                                        <h5 class="text-danger text-center"><?php echo _("IMPORTANTE"); ?>:</h5>
                                        <label
                                            class="help-block "><?php echo _("En la pestaña 'privacidad', no olvides marcar:"); ?>
                                            &nbsp;&nbsp;</label>

                                        <div class="row">
                                            <div class="checkbox col-lg-6 col-lg-push-1">
                                                <label>
                                                    <input type="checkbox" checked="true"
                                                           disabled><?php echo _("Aceptar calificaciones desde la herramienta"); ?>
                                                </label>
                                                <br>
                                                <label>
                                                    <input type="checkbox" checked="true"
                                                           disabled><?php echo _("Compartir el e-mail del usuario con la herramienta"); ?>
                                                </label>
                                                <br>

                                                <label>
                                                    <input type="checkbox" checked="true"
                                                           disabled><?php echo _("Aceptar calificaciones desde la herramienta"); ?>
                                                </label>


                                            </div>
                                        </div>

                                        <label
                                            class="help-block "><?php echo _("Tras seguir estos pasos, deberías tener algo similar a esto:"); ?>
                                        </label>

                                        <div class="row">
                                            <div class="checkbox col-lg-7 col-lg-push-2">


                                                <img class="img-responsive"
                                                     src="/_QuickTest_TFG/app/webroot/images/configurarLTI.PNG"
                                                     alt="configurarLTI">
                                            </div>
                                        </div>

                                    </div>
                                    <br>
                                    <br>
                                    <br>

                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-default"
                                                data-dismiss="modal">Close
                                        </button>
                                    </div>
                                </div>

                            </div>
                        </div>


                        <ul class=" dropdown-menu  pull-right" style=" width: 180px ;" role="menu">
                            <li role="presentation">
                                <a href="index.php?data-idCuestEditar=<?php echo $cuest['idCuestionario']; ?>&isEditar=1&idAsig=<?php echo $idAsig; ?>&nombreAsig=<?php echo $nombreAsig; ?>&nombreCuest=<?php echo $cuest['nombreCuestionario']; ?>&isProfesor=1&idUser=<?php echo $idUser; ?>&nombreAlu=<?php echo $nombreAlu; ?>&apeAlu=<?php echo $apeAlu; ?>&lang=<?php echo $lang; ?>"
                                   data-idCuestEditar="<?php echo $cuest['idCuestionario']; ?>"
                                   onclick="editarCuestionario(this)">
                                    <i class="glyphicon glyphicon-edit pull-right"></i><?php echo _("Editar"); ?>
                                </a>
                            </li>
                            <li role="presentation">
                                <a href="index.php?data-idCuestDuplicar=<?php echo $cuest['idCuestionario']; ?>&isDuplicar=1&idAsig=<?php echo $idAsig; ?>&nombreAsig=<?php echo $nombreAsig; ?>&nombreCuest=<?php echo $cuest['nombreCuestionario']; ?>&isProfesor=1&idUser=<?php echo $idUser; ?>&nombreAlu=<?php echo $nombreAlu; ?>&apeAlu=<?php echo $apeAlu; ?>&lang=<?php echo $lang; ?>"
                                   data-idCuestDuplicar="<?php echo $cuest['idCuestionario']; ?>"
                                   onclick="duplicarCuestionario(this)">
                                    <i class="glyphicon glyphicon-book pull-right"></i><?php echo _("Duplicar"); ?>
                                </a>
                            </li>


                            <li role="presentation">
                                <a href="#" data-idCuest="<?php echo $cuest['idCuestionario']; ?>"
                                   onclick="borrarCuestionario(this)">
                                    <i class="glyphicon glyphicon-remove-circle pull-right"></i><?php echo _("Eliminar"); ?>
                                </a>
                            </li>
                        </ul>

                    </li>

                <?php
                }
            }

            ?>
        </ul>

    </div>

    <br>
</div>


</div>

</div>

</body>

</html>
