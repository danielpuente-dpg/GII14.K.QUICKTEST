<!DOCTYPE html>
<!--vista para crear un nuevo cuestionario-->
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

    <script type="text/javascript" src="../default/myJS/cuestionario_CrearNuevo.js"></script>

</head>

<body style=" padding-top: 100px">


<!-- ------------------------------------------BARRA DE NAVEGACION ---------------------------------------------------------  !-->


<nav class="navbar navbar-default navbar-fixed-top " style="height: 90px; top: -25px;">


    <div class="col-xs-1">
        <div>
            <h6 id="logo" class="navbar-text text-left" style="padding-top: 5px;">
                QuickTest</h6>

        </div>


    </div>

    <div class="col-xs-9" id="col10-navbar">


        <div class="btn-group" id="mayor21">
            <button id="casilla1" data-idPreg="1" class="btn btn-default navbar-btn "
                    onclick="gotoSolution(this); ">&nbsp;1&nbsp;</button>
            <b id="nextCasilla2"></b>
        </div>

    </div>


    <div class="col-xs-2 ">
        <ul class="nav navbar-nav navbar-right">


            <li>
                <div class="btn-group  pull-right">

                    <button id="AddPregunta" onclick="addPregunta(this); gotoNewPreg(); buttonsLess10(this);"
                            type="button"
                            class="btn btn-primary btn navbar-btn  "
                            data-toggle="tooltip" title="<?php echo _("Añadir pregunta"); ?>">
                        <span class="glyphicon glyphicon-plus-sign"></span>
                    </button>


                    <button   onclick="submitForm();" class="btn btn-primary btn navbar-btn  "
                            data-toggle="tooltip" title="<?php echo _("Guardar cuestionario"); ?>"
                            data-placement="left">
                        <span
                            class="glyphicon glyphicon-floppy-disk">

                           </span>
                    </button>

                    <a id="botonLogout" class="btn btn-primary      navbar-btn"
                       data-toggle="tooltip" title="<?php echo _("Salir QuickTest"); ?>"
                       href="<?php echo $_POST['launch_presentation_return_url']; ?>"
                       type="button"
                        >
                        <span id="logout" class="glyphicon glyphicon-log-out"></span>

                    </a>
                </div>
            </li>


        </ul>
    </div>


</nav>

<div class="row">
    <div class="col-lg-3  ">


        <ol class="breadcrumb" style="background-color: rgba(0, 0, 0, 0.009); ">
            <li><a href="/_QuickTest_TFG/index.php">Home</a>
            </li>
            <li class="active"><?php echo _("Crear nuevo cuestionario"); ?> </li>
        </ol>
    </div>

</div>


<form id="formulario" name="formulario" method="post" action="/_QuickTest_TFG/index.php" autocomplete="on">
<div class="container">


<div class="row">

    <div class="form-group" id="form-group_cuestionario">

        <div id="col-lg-9_cuestionario" class="col-lg-9">
            <label for="Titulo" id="labelCuest"><?php echo _("Título del cuestionario"); ?></label>
            <input value="cuestionario 1 " autocomplete="on" type="text" required
                   id="tituloCuest"
                   name="cuestTitulo" class="form-control input-sm" rows="1"
                   placeholder="<?php echo _("Escribe un título para el cuestionario"); ?>" autofocus>


        </div>
    </div>

</div>
<br><br>


<div class="row">
    <div id="col-lg-6_comodinesVerdes" class="col-lg-6">

        <div class="panel panel-default">
            <div class="panel-heading">
                <label for="PenalizarVerdeTrue" id="PenalizarVerdeTrue">
                    &nbsp;&nbsp;<?php echo _("Configurar comodín verde"); ?> <span
                        style="font-size: 19px;" class="glyphicon glyphicon-cog  pull-left"></span></label>
                <span class="pull-right clickable collapsed" data-toggle="collapse" data-target="#collapseVerde"
                      href="#collapseVerde"><a href="#" role="button" style="text-decoration:none;"
                                               class="glyphicon glyphicon-chevron-down text-primary"></a></span>
            </div>

        </div>


        <div id="collapseVerde" class="panel-collapse collapse">

            <div class="panel-body">

                <div id="col-lg-5_comodinVerdeTrue" class="col-lg-5">
                    <label for="PenalizarVerdeTrue"
                           id="PenalizarVerdeTrue"><?php echo _("Penalizar respuesta correcta"); ?> </label>
                    <select class="form-control input-sm" name="correctorVerdeTrue">
                        <option value="0.20"><?php echo _("Muy poco"); ?></option>

                        <option value="0.25"><?php echo _("Poco"); ?></option>

                        <option selected="selected" value="0.33"><?php echo _("Normal (recomendado)"); ?></option>

                        <option value="0.5"><?php echo _("Mucho"); ?></option>


                    </select>
                 <span
                     class="help-block"><?php echo _("La calificación se verá reducida, pero la nota final será  "); ?>
                     <b><?php echo _("positiva"); ?></b></span>

                </div>

                <div id="col-lg-5_comodinVerdeFalse" class="col-lg-5 pull-right   ">

                    <label for="PenalizarVerdeFalse"
                           id="PenalizarVerdeFalse"><?php echo _("Penalizar respuesta incorrecta"); ?></label>
                    <select class="form-control input-sm " name="correctorVerdeFalse">
                        <option value="-0.20"><?php echo _("Muy poco"); ?></option>
                        <option value="-0.25"><?php echo _("Poco"); ?></option>
                        <option value="-0.33"><?php echo _("Normal"); ?></option>
                        <option selected="selected" value="-0.5"><?php echo _("Mucho (recomendado)"); ?></option>

                    </select>
               <span
                   class="help-block"><?php echo _("La calificación se verá reducida, pero la nota final será  "); ?>
                   <b><?php echo _("negativa"); ?></b></span>

                </div>
            </div>
        </div>
    </div>


    <div id="col-lg-6_comodinesAmarillo" class="col-lg-6">

        <div class="panel panel-default   ">
            <div class="panel-heading">
                <label for="ComodinAmarillo" id="ComodinAmarillo">
                    &nbsp;&nbsp;<?php echo _("Configurar comodín ámbar"); ?><span
                        style="font-size: 19px;" class="glyphicon glyphicon-cog   pull-left"></span></label>
                <span class="pull-right clickable collapsed" data-toggle="collapse"
                      data-target="#collapseAmarillo"
                      href="#collapseAmarillo"><a href="#" style="text-decoration:none;"
                                                  class="glyphicon glyphicon-chevron-down text-primary"></a></span>
            </div>

        </div>

        <div id="collapseAmarillo" class="panel-collapse collapse">
            <div class="panel-body">

                <div id="col-lg-5_comodinAmarilloTrue" class="col-lg-5">
                    <label for="PenalizarAmarilloTrue"
                           id="PenalizarAmarilloTrue"><?php echo _("Penalizar respuesta correcta"); ?></label>
                    <select class="form-control input-sm " name="correctorAmarilloTrue">
                        <option value="0.2"><?php echo _("Muy poco"); ?></option>
                        <option selected="selected" value="0.25"><?php echo _("Poco (recomendado)"); ?></option>
                        <option value="0.33"><?php echo _("Normal"); ?></option>
                        <option value="0.5"><?php echo _("Mucho"); ?> </option>


                    </select>
                <span
                    class="help-block"><?php echo _("La calificación se verá reducida, pero la nota final será  "); ?>
                    <b><?php echo _("positiva"); ?></b></span>

                </div>

                <div id="col-lg-5_comodinAmarilloFalse" class="col-lg-5 pull-right   ">

                    <label for="PenalizarAmarilloFalse"
                           id="PenalizarAmarilloFalse"><?php echo _("Penalizar respuesta incorrecta"); ?></label>
                    <select class="form-control input-sm " name="correctorAmarilloFalse">
                        <option selected="selected" value="-0.20"><?php echo _("Muy poco (recomendado)"); ?></option>
                        <option value="-0.25"><?php echo _("Poco"); ?></option>
                        <option value="-0.33"><?php echo _("Normal"); ?></option>
                        <option value="-0.5"><?php echo _("Mucho"); ?></option>

                    </select>
                     <span
                         class="help-block"><?php echo _("La calificación se verá reducida, pero la nota final será  "); ?>
                         <b><?php echo _("negativa"); ?></b></span>

                </div>
            </div>
        </div>

    </div>
</div>
<div class="row">
    <div id="col-lg-6_comodinesVerdes" class="col-lg-7 col-lg-push-2">
        <div class="form-horizontal form-group-sm" hidden="true">

            <label class="col-lg-2 control-label  "><?php echo _("idAsignatura"); ?></label>

            <div class="col-lg-2">
                <input class="form-control input-sm" name="idAsig" type="text"
                       value="<?php echo $_POST['idAsig']; ?>"><br>
            </div>
            <label class="col-lg-2 control-label"><?php echo _("Nombre asignatura"); ?></label>

            <div class="col-lg-2">
                <input class="form-control input-sm" name="nombreAsig" type="text"
                       value="<?php echo $_POST['nombreAsig']; ?>"><br>
            </div>
            <label class="col-lg-2 control-label"><?php echo _("Es profesor"); ?></label>

            <div class="col-lg-2">
                <input class="form-control input-sm" name="isProfesor" type="text"
                       value="<?php echo $_POST['isProfesor']; ?>"><br>
            </div>
            <label class="col-lg-2 control-label"><?php echo _("Nombre profesor"); ?></label>

            <div class="col-lg-2">
                <input class="form-control input-sm " name="nombreAlu" type="text"
                       value="<?php echo $_POST['nombreAlu']; ?>"><br>
            </div>
            <label class="col-lg-2 control-label"><?php echo _("Apellido Profesor"); ?></label>

            <div class="col-lg-2">
                <input class="form-control input-sm" name="apeAlu" type="text"
                       value="<?php echo $_POST['apeAlu']; ?>"><br>
            </div>
            <label class="col-lg-2 control-label"><?php echo _("id Usuario"); ?></label>

            <div class="col-lg-2">
                <input class="form-control input-sm" name="idUser" type="text"
                       value="<?php echo $_POST['idUser']; ?>"><br>
            </div>

            <label class="col-lg-2 control-label"><?php echo _("lang"); ?></label>

            <div class="col-lg-2">
                <input class="form-control input-sm" id="lang" name="lang" type="text"
                       value="<?php echo $_POST['lang']; ?>"><br>
            </div>

            <label class="col-lg-2 control-label"><?php echo _("email"); ?></label>

            <div class="col-lg-2">
                <input class="form-control input-sm" id="emailLTI" name="emailLTI" type="text"
                       value="<?php echo $_POST['emailLTI']; ?>"><br>
            </div>

            <label class="col-lg-2 control-label"><?php echo _("launch_presentation_return_url"); ?></label>

            <div class="col-lg-2">
                <input class="form-control input-sm" id="launch_presentation_return_url"
                       name="launch_presentation_return_url" type="text"
                       value="<?php echo $_POST['launch_presentation_return_url']; ?>"><br>
            </div>
        </div>
    </div>
</div>


<div style="padding-top: 10px;"></div>
<div class="row">


    <!--------------------------------------------------- PREGUNTA ------------------------------------------------->
    <div id="panelpanel-info1" class="panel panel-info ">

        <div id="panel-heading1" class="panel-heading  ">
            <label id="labelPreg1" for="titulo Pregunta"><?php echo _("Pregunta 1"); ?></label>

            <div class=" pull-right " id="pull-rightpmax1">
                <label class="label" id="labelpmax1"
                       for="pmax"><?php echo _("Puntuacion max"); ?></label>

                <input
                    id="pmax1"
                    type="number" style="width: 65px" value="1"
                    class=" input-sm text-primary text-left " min="1"
                    max="10" step="1" name="pregRes1[pmax]">

                <a id="borrar1" type="button" href="#" class="  " data-idPreg="1" onclick="deletePregunta(this)">&nbsp;&nbsp;
                    <h5 id="papelera1" class="text-primary glyphicon glyphicon-trash pull-right"></h5>
                </a>
            </div>
        </div>

        <div id="panel-body1" class="panel-body">
            <div id="Acol-lg-121" class="col-lg-12">
                <textarea autocomplete="on" id="preg1" required="true"
                          data-idPreg="1" name="pregRes1[title]"
                          class="form-control input-sm" rows="1"
                          placeholder="<?php echo _("Escribe el enunciado de la pregunta"); ?>"><?php echo _("Pregunta 1"); ?></textarea>

            </div>


            <!--------------------------------------------------- RESPUESTA ------------------------------------------------->

            <div id="container1_1" class="container">
                <div id="row1_1" class="row">
                    <div id="form-group1_1" class="form-group">
                        <div class="col-lg-9 col-lg-push-1 control-label  "
                             id="col-lg-9col-lg-push-1control-label1_1"
                             name="col-lg-9col-lg-push-1control-label1">

                            <label id="labelRes1_1"
                                   name="labelRes1" for="titulo Respuesta">
                                <small><?php echo _("Respuesta"); ?> 1</small>
                            </label>

                        </div>
                        <div class="col-lg-1 col-lg-push-1" id="col-lg-1col-lg-push-11_1">
                            <label id="esCorrecta1_1" for="esCorrecta">
                                <small><?php echo _("Es correcta"); ?></small>
                            </label>
                        </div>
                        <div class="col-lg-9 col-lg-push-1"
                             id="col-lg-9col-lg-push-11_1"
                             name="col-lg-9col-lg-push-11">

                            <textarea autocomplete="on" id="resp1_1" name="pregRes1[res][]"
                                      class="form-control input-sm "
                                      rows="1"
                                      required
                                      placeholder="<?php echo _("Escribe una respuesta"); ?>"><?php echo _("Respuesta 1"); ?>
                            </textarea>
                        </div>

                        <div class="col-lg-1  col-lg-push-1 radio control-label"
                             id="col-lg-1col-lg-push-1radiocontrol-label1_1"
                             name="col-lg-1col-lg-push-1radiocontrol-label1">
                            <input style="margin-left: 30px" type="radio" name="pregRes1[correcta]"
                                   checked="true"
                                   id="correcta1_1"
                                   value="1">
                        </div>


                        <!--------------------------------------------------- BOTON AÑADIR RESPUESTA ------------------------------------------------->

                        <div class="col-lg-9  col-lg-push-1 " id="AddCol-lg-9col-lg-push-11">
                            <p></p>
                            <button id="addRespuestaP1"
                                    data-idPreg="1"
                                    data-idRes="1"
                                    type="button"
                                    class="btn btn-info btn-xs pull-right"
                                    onclick="addRespuesta(this);">
                                <span id="iconoPlus1"
                                      class="glyphicon glyphicon-plus-sign"></span>
                                <?php echo _("Añadir Respuesta"); ?>


                            </button>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>


    <p id="newPreg"></p>
    <br>

    <div class="row">
        <button id="AddPregunta" onclick="addPregunta(this);" type="button"
                class="btn btn-info btn-xs pull-left">
            <span class="glyphicon glyphicon-plus-sign"></span>&nbsp;<?php echo _("Nueva pregunta"); ?>
        </button>
    </div>
    <div class="row">
        <div class="col-lg-12">
            <button  id="botonGuardarCuestionario" type="submit" class="btn btn-success   pull-right"><span
                    class="glyphicon glyphicon-floppy-disk"></span>&nbsp;<?php echo _("Guardar"); ?>
            </button>
        </div>
        <div style="padding-top: 10px;"></div>

    </div>

</div>

</form>


</body>

</html>
