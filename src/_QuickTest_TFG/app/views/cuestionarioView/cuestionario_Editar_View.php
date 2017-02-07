<!DOCTYPE html>
<!--vista para editar un usuario-->

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

    <script type="text/javascript" src="/_QuickTest_TFG/app/views/default/myJS/cuestionario_Editar.js"></script>
    <script type="text/javascript" src="/_QuickTest_TFG/app/views/default/myJS/cuestionario_CrearNuevo.js"></script>


</head>

<body style=" padding-top: 100px">
<?php
$numPreg = 0;
?>


<!-- ------------------------------------------BARRA DE NAVEGACION ---------------------------------------------------------  !-->


<div class="container">
    <div class="row">
        <nav class="navbar navbar-default navbar-fixed-top " style="height: 90px; top: -25px;">

            <div class="col-xs-1">
                <h5 class="navbar-text text-left" style="padding-top: 5px;">
                    QuickTest</h5>
            </div>

            <div class="col-xs-9" id="col10-navbar">

                <div class="btn-group" id="mayor21">
                    <?php
                    foreach ($preguntas as $p) {
                        ?>
                        <button id="casilla<?php echo $numPreg += 1; ?>" data-idPreg="<?php echo $numPreg; ?>"
                                class="btn btn-default navbar-btn"
                                onclick="gotoPregunta(this); ">&nbsp;<?php echo $numPreg; ?>&nbsp;</button>
                        <b id="nextCasilla<?php echo $numPreg + 1; ?>"></b>
                    <?php } ?>
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


                            <button type="submit" onclick="submitFormu();"
                                    class="btn btn-primary btn navbar-btn"
                                    data-toggle="tooltip" title="<?php echo _("Guardar cuestionario"); ?>"
                                    data-placement="left">
                                <span
                                    class="glyphicon glyphicon-floppy-disk">

                                 </span>

                            </button>

                            <a id="botonLogout" class="btn btn-primary      navbar-btn"
                               data-toggle="tooltip" title="<?php echo _("Salir QuickTest"); ?>"
                               href="<?php echo $ltiConsumerURL; ?>"
                               type="button"
                                >
                                <span id="logout" class="glyphicon glyphicon-log-out"></span>

                            </a>


                        </div>
                    </li>


                </ul>
            </div>


        </nav>
    </div>
</div>

<div class="row">
    <div class="col-lg-3  ">


        <ol class="breadcrumb" style="background-color: rgba(0, 0, 0, 0.009); ">
            <li><a href="/_QuickTest_TFG/index.php">Home</a>
            </li>
            <li class="active"><?php echo _("Editar cuestionario"); ?> </li>
        </ol>
    </div>

</div>

<?php
$numPreg = 0;
?>


<form id="formulario" name="formulario" method="post" action="/_QuickTest_TFG/index.php" autocomplete="on">
<div class="container" style="padding-top: 10px;">


<div class="row" hidden="true">

    <div class="form-group" id="form-group_cuestionario">
        <div class="form-horizontal form-group-sm">
            <label class="col-lg-2 control-label  ">modo editar</label>

            <div class="col-lg-2">
                <input class="form-control input-sm" name="isEditar" type="text"
                       value="<?php echo $isEditar; ?>"><br>
            </div>


            <label class="col-lg-2 control-label  ">idAsignatura</label>

            <div class="col-lg-2">
                <input class="form-control input-sm" name="idAsig" type="text" value="<?php echo $idAsig; ?>"><br>
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
                <input class="form-control input-sm" name="apeAlu" type="text" value="<?php echo $apeAlu; ?>"><br>
            </div>
            <label class="col-lg-2 control-label">id Usuario</label>

            <div class="col-lg-2">
                <input class="form-control input-sm" name="idUser" type="text" value="<?php echo $idUser; ?>"><br>
            </div>
            <label class="col-lg-2 control-label">count Preguntas</label>

            <div class="col-lg-2">
                <input id="countPreguntas" name="countPreguntas" type="text" value="<?php echo $countPreguntas ?>">
            </div>

            <label class="col-lg-2 control-label">idCuest Editar</label>

            <div class="col-lg-2">
                <input id="idCuestEditar" name="idCuestEditar" type="text" value="<?php echo $idCuestEditar ?>">
            </div>

            <label class="col-lg-2 control-label"><?php echo _("lang"); ?></label>

            <div class="col-lg-2">
                <input class="form-control input-sm" id="lang" name="lang" type="text"
                       value="<?php echo $lang; ?>"><br>
            </div>
        </div>
    </div>
</div>


<div class="container" style="padding-top: 10px;">
    <div class="row">


        <div id="col-lg-9_cuestionario" class="col-lg-9">
            <label for="Titulo" id="labelCuest"><?php echo _("Título del cuestionario"); ?></label>
            <input value="<?php echo $cuestionarios['titulo'] ?>" autocomplete="on" type="text" required
                   id="tituloCuest"
                   name="cuestTitulo" class="form-control input-sm" rows="1"
                   autofocus>


        </div>
    </div>

</div>

<div style="padding-top: 20px;"></div>
<div class="row">
    <div id="col-lg-6_comodinesVerdes" class="col-lg-6">

        <div class="panel panel-default">
            <div class="panel-heading">
                <label for="PenalizarVerdeTrue" id="PenalizarVerdeTrue">
                    &nbsp;&nbsp;<?php echo _("Configurar comodín verde"); ?> <span
                        style="font-size: 19px;" class="glyphicon glyphicon-cog  pull-left"></span></label>
                <span class="pull-right clickable collapsed" data-toggle="collapse" data-target="#collapseVerde"
                      href="#collapseVerde"><a href="#" style="text-decoration:none;"
                                               class="glyphicon glyphicon-chevron-down text-primary"></a></span>
            </div>

        </div>


        <div id="collapseVerde" class="panel-collapse collapse">

            <div class="panel-body">

                <div id="col-lg-5_comodinVerdeTrue" class="col-lg-5">
                    <label for="PenalizarVerdeTrue"
                           id="PenalizarVerdeTrue"><?php echo _("Penalizar respuesta correcta"); ?> </label>
                    <input hidden id="idCorrVerdeTrue" type="text"
                           value="<?php echo $cuestionarios['correctorVerdeTrue'] ?>">

                    <select class="form-control input-sm" name="correctorVerdeTrue">
                        <option id="opCorrVerdeT020" value="0.20"><?php echo _("Muy poco"); ?></option>

                        <option id="opCorrVerdeT025" value="0.25"><?php echo _("Poco"); ?></option>

                        <option id="opCorrVerdeT033" value="0.33"><?php echo _("Normal (recomendado)"); ?></option>

                        <option id="opCorrVerdeT05" value="0.5"><?php echo _("Mucho"); ?></option>


                    </select>
                <span
                    class="help-block"><?php echo _("La calificación se verá reducida, pero la nota final será  "); ?>
                    <b><?php echo _("positiva"); ?></b></span>

                </div>

                <div id="col-lg-5_comodinVerdeFalse" class="col-lg-5 pull-right   ">

                    <label for="PenalizarVerdeFalse"
                           id="PenalizarVerdeFalse"><?php echo _("Penalizar respuesta incorrecta"); ?></label>
                    <input hidden id="idCorrVerdeFalse" type="text"
                           value="<?php echo $cuestionarios['correctorVerdeFalse'] ?>">

                    <select class="form-control input-sm " name="correctorVerdeFalse">

                        <option id="opCorrVerdeF020" value="-0.20"><?php echo _("Muy poco"); ?></option>
                        <option id="opCorrVerdeF025" value="-0.25"><?php echo _("Poco"); ?></option>
                        <option id="opCorrVerdeF033" value="-0.33"><?php echo _("Normal"); ?></option>
                        <option id="opCorrVerdeF05" value="-0.5"><?php echo _("Mucho (recomendado)"); ?></option>

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
                    <input hidden id="idCorrAmarilloTrue" type="text"
                           value="<?php echo $cuestionarios['correctorAmarilloTrue'] ?>">

                    <select class="form-control input-sm " name="correctorAmarilloTrue">

                        <option id="opCorrAmarilloT020" value="0.2"><?php echo _("Muy poco"); ?></option>
                        <option id="opCorrAmarilloT025" value="0.25"><?php echo _("Poco (recomendado)"); ?></option>
                        <option id="opCorrAmarilloT033" value="0.33"><?php echo _("Normal"); ?></option>
                        <option id="opCorrAmarilloT05" type="number"
                                value="0.5"><?php echo _("Mucho"); ?></option>


                    </select>
                <span
                    class="help-block"><?php echo _("La calificación se verá reducida, pero la nota final será  "); ?>
                    <b><?php echo _("positiva"); ?></b>
                </span>

                </div>

                <div id="col-lg-5_comodinAmarilloFalse" class="col-lg-5 pull-right   ">

                    <label for="PenalizarAmarilloFalse"
                           id="PenalizarAmarilloFalse"><?php echo _("Penalizar respuesta incorrecta"); ?></label>
                    <input hidden id="idCorrAmarilloFalse" type="text"
                           value="<?php echo $cuestionarios['correctorAmarilloFalse'] ?>">

                    <select class="form-control input-sm " name="correctorAmarilloFalse">

                        <option id="opCorrAmarilloF020"
                                value="-0.20"><?php echo _("Muy poco (recomendado)"); ?></option>
                        <option id="opCorrAmarilloF025" value="-0.25"><?php echo _("Poco"); ?></option>
                        <option id="opCorrAmarilloF033" value="-0.33"><?php echo _("Normal"); ?></option>
                        <option id="opCorrAmarilloF05" value="-0.5"><?php echo _("Mucho"); ?></option>

                    </select>
                    <span
                        class="help-block"><?php echo _("La calificación se verá reducida, pero la nota final será  "); ?>
                        <b><?php echo _("negativa"); ?></b>
                </span>
                </div>
            </div>
        </div>

    </div>
</div>


<div class="row">


    <!--------------------------------------------------- PREGUNTA ------------------------------------------------->

    <?php

    foreach ($preguntas as $p) {


    ?>


    <div id="panelpanel-info<?php echo $numPreg += 1 ?>" class="panel panel-info ">

        <div id="panel-heading<?php echo $numPreg ?>" class="panel-heading  ">
            <label id="labelPreg<?php echo $numPreg ?>"
                   for="titulo Pregunta"><?php echo _("Pregunta"); ?> <?php echo $numPreg ?></label>

            <div class=" pull-right " id="pull-rightpmax<?php echo $numPreg ?>">
                <label class="label" id="labelpmax<?php echo $numPreg ?>"
                       for="pmax"><?php echo _("Puntuacion max"); ?></label>

                <input id="pmax<?php echo $numPreg ?>"
                       type="number" style="width: 65px" value="<?php echo $p['max_puntuacion']; ?>"
                       class=" input-sm text-primary text-left " min="1"
                       max="10" step="1" name="pregRes<?php echo $numPreg ?>[pmax]">

                <a id="borrar<?php echo $numPreg ?>" type="button" href="#" class="  "
                   data-idPreg="<?php echo $numPreg ?>" onclick="deletePregunta(this)">&nbsp;&nbsp;
                    <h5 id="papelera<?php echo $numPreg ?>"
                        class="text-primary glyphicon glyphicon-trash pull-right"></h5>
                </a>
            </div>
        </div>

        <div id="panel-body<?php echo $numPreg ?>" class="panel-body">
            <div id="Acol-lg-12<?php echo $numPreg ?>" class="col-lg-12">
                <textarea autocomplete="on" id="preg<?php echo $numPreg ?>" required
                          data-idPreg="<?php echo $numPreg ?>" name="pregRes<?php echo $numPreg ?>[title]"
                          class="form-control input-sm" rows="1"
                          placeholder="<?php echo _("Escribe el enunciado de la pregunta"); ?>"><?php echo $p['titulo'] ?></textarea>

            </div>


            <!--------------------------------------------------- RESPUESTA ------------------------------------------------->
            <!--   <article>-->
            <div id="container<?php echo $numPreg ?>_1" class="container">
                <div id="row<?php echo $numPreg ?>_1" class="row">
                    <div id="form-group<?php echo $numPreg ?>_1" class="form-group">

                        <?php
                        $numRes = 1;

                        foreach ($respuestasPregunta[$numPreg - 1] as $r) {
                        ?>


                            <div class="col-lg-9 col-lg-push-1 control-label"
                                 id="col-lg-9col-lg-push-1control-label<?php echo $numPreg ?>_<?php echo $numRes ?>"
                                 name="col-lg-9col-lg-push-1control-label<?php echo $numPreg ?>">

                                <label id="labelRes<?php echo $numPreg ?>_<?php echo $numRes ?>"
                                       name="labelRes<?php echo $numPreg ?>"
                                       for="titulo Respuesta">
                                    <small><?php echo _("Respuesta"); ?> <?php echo $numRes; ?></small>
                                </label>

                            </div>
                            <?php if ($numRes == 1) { ?>
                                <div class="col-lg-1 col-lg-push-1"
                                     id="col-lg-1col-lg-push-1<?php echo $numPreg ?>_1">
                                    <label id="esCorrecta<?php echo $numPreg ?>_1" for="esCorrecta">
                                        <small><?php echo _("Es correcta"); ?></small>
                                    </label>
                                </div>
                            <?php } ?>
                            <div class="col-lg-9 col-lg-push-1"
                                 id="col-lg-9col-lg-push-1<?php echo $numPreg ?>_1"
                                 name="col-lg-9col-lg-push-1<?php echo $numPreg ?>">

                                <textarea autocomplete="on" id="resp<?php echo $numPreg ?>_1"
                                          name="pregRes<?php echo $numPreg ?>[res][]"
                                          class="form-control input-sm"
                                          required
                                          rows="1"><?php echo $r['tit_Respuesta']; ?></textarea>
                            </div>


                            <div class="col-lg-1  col-lg-push-1 radio control-label"
                                 id="col-lg-1col-lg-push-1radiocontrol-label<?php echo $numPreg ?>_<?php echo $numRes ?>"
                                 name="col-lg-1col-lg-push-1radiocontrol-label<?php echo $numPreg ?>">
                                <input style="margin-left: 30px" type="radio"
                                       name="pregRes<?php echo $numPreg ?>[correcta]"
                                    <?php
                                    if ($r['correcta'] == 1) {
                                        ?>
                                        checked="checked"
                                    <?php } ?>
                                       id="correcta<?php echo $numPreg ?>_<?php echo $numRes ?>"
                                       value="<?php echo $numRes ?>">
                            </div>


                            <!--------------------------------------------------- BOTON AÑADIR RESPUESTA ------------------------------------------------->
                            <?php
                            if ($numRes == sizeof($respuestasPregunta[$numPreg - 1])) {
                                ?>
                                <div class="col-lg-9  col-lg-push-1 "
                                     id="AddCol-lg-9col-lg-push-1<?php echo $numPreg ?>">
                                    <p></p>
                                    <button id="addRespuestaP<?php echo $numPreg ?>"
                                            data-idPreg="<?php echo $numPreg ?>"
                                            data-idRes="<?php echo $numRes ?>"
                                            type="button"
                                            class="btn btn-info btn-xs pull-right"
                                            onclick="addRespuesta(this);">
                                        <span id="iconoPlus<?php echo $numPreg ?>"
                                              class="glyphicon glyphicon-plus-sign"></span>
                                        <?php echo _("Añadir Respuesta"); ?>

                                    </button>
                                </div>
                            <?php } ?>



                            <?php
                            $numRes++;
                        } ?>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <p id="newP<?php echo $numPreg + 1 ?>">
        <?php } ?>

    <p id="newPreg"></p>


    <div class="row">
        <button id="AddPregunta" onclick="addPregunta(this);" type="button"
                class="btn btn-info btn-xs pull-left">
            <span class="glyphicon glyphicon-plus-sign"></span> <?php echo _("Nueva pregunta"); ?>
        </button>
    </div>
    <br>

    <div class="row">
        <div class="col-lg-9 col-lg-push-1">
            <button  id="botonGuardarCuestionario" type="submit"
                    class="btn   btn-success btn-block  pull-right"><?php echo _("Guardar cambios"); ?></button>
        </div>
    </div>

</div>

</form>
<div class="row">
    <a type="button" href="/_QuickTest_TFG/index.php"
       class="btn btn-sm btn-primary    pull-right"><?php echo _("Descartar cambios"); ?></a>
</div>


</body>

</html>
