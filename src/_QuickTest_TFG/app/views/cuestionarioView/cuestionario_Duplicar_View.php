<!DOCTYPE html>
<!--vista para duplicar un usuario-->
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

    <script type="text/javascript" src="app/views/default/myJS/cuestionario_Duplicar.js"></script>

</head>

<body style=" padding-top: 100px">

<?php
$numPreg = 0;
?>


<!-- ------------------------------------------BARRA DE NAVEGACION ---------------------------------------------------------  !-->


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
                        <small><?php echo $nombreAsig; ?> </small>
                    </p>
                </li>
                <li>

                    <a id="botonLogout" class="btn btn-primary      navbar-btn"
                       data-toggle="tooltip" title="<?php echo _("Salir QuickTest"); ?>"
                       href="<?php echo $ltiConsumerURL; ?>"
                       type="button"
                       style="top: -5px;">

                        <span id="logout" class="glyphicon glyphicon-log-out"></span>

                    </a>
                </li>

            </ul>

        </div>


</nav>

</div>

<?php
$numPreg = 0;
?>

<div class="row">
    <div class="col-lg-3  ">


        <ol class="breadcrumb" style="background-color: rgba(0, 0, 0, 0.009);">
            <li><a href="/_QuickTest_TFG/index.php">Home</a>
            </li>
            <li class="active"><?php echo _("Duplicar cuestionario"); ?> </li>
        </ol>
    </div>

</div>


<form id="formDuplicar" name="formDuplicar" method="post" action="/_QuickTest_TFG/index.php" autocomplete="on">

<div class="row">
<div class="col-lg-10 col-lg-push-1 ">

    <div class="jumbotron">
        <div class="row">
            <div class="col-lg-6 col-lg-push-1">
                <h3><?php echo _("Duplicar cuestionario"); ?></h3>
                <br>
            </div>
        </div>
        <div class="row">

            <div class="col-lg-12 col-lg-push-2">
                <small><?php echo _("Está a punto de realizar una copia de"); ?>
                    <div class="row">
                        <h6 class="col-lg-8 col-lg-push-1">
                            <em id="tituloOriginal"><?php echo $cuestionarios['titulo'] ?></em></h6>
                    </div>
                </small>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-6 col-lg-push-2">
                <small><?php echo _("Si lo desea, puede"); ?>
                    <a href=# data-toggle="collapse" data-target="#mostrarTitulo">
                        <?php echo _("modificar el título"); ?>
                    </a>,<?php echo _("o mantener el actual."); ?>
                </small>

            </div>
        </div>
        <div class="row">
            <div id="mostrarTitulo" class="collapse">
                <div class="col-lg-10 col-lg-push-1">
                    <label for="Titulo" id="labelCuest"><?php echo _("Nuevo título"); ?></label>
                    <input value="<?php echo $cuestionarios['titulo'] ?>" autocomplete="on" type="text" required
                           id="tituloCuest"
                           name="cuestTitulo" class="form-control input-sm" rows="1"
                           autofocus>
                </div>
            </div>
        </div>
        <div class="row">
            <br><br>

            <div class="col-lg-2 col-lg-push-9">

                <button type="submit" onclick="addCopiaTitulo(); "
                        class="btn btn-success pull-right"><?php echo _("Duplicar"); ?></button>
            </div>
        </div>
    </div>
</div>


<button hidden id="hideButton" type="button" class="btn btn-info" data-toggle="collapse"
        data-target="#demo"> </button>
<div id="demo" class="collapse">

<div class="row">

    <div class="form-group" id="form-group_cuestionario">
        <div class="form-horizontal form-group-sm">
            <label class="col-lg-2 control-label  ">modo duplicar</label>

            <div class="col-lg-2">
                <input class="form-control input-sm" name="isDuplicar" type="text"
                       value="<?php echo $isDuplicar; ?>"><br>
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

            <label class="col-lg-2 control-label">idCuest duplicar</label>

            <div class="col-lg-2">
                <input id="idCuestDuplicar" name="idCuestDuplicar" type="text" value="<?php echo $idCuestDuplicar ?>">
            </div>

            <div class="col-lg-2">
                <input class="form-control input-sm" id="lang" name="lang" type="text"
                       value="<?php echo $lang; ?>"><br>
            </div>
        </div>
        <br>
        <br>


    </div>

</div>
<br><br>


<div class="row">
    <div id="col-lg-6_comodinesVerdes" class="col-lg-6">

        <div class="panel panel-default">
            <div class="panel-heading">
                <label for="PenalizarVerdeTrue" id="PenalizarVerdeTrue">&nbsp;&nbsp;Configurar comodín verde <span
                        style="font-size: 19px;" class="glyphicon glyphicon-cog  pull-left"></span></label>
                <span class="pull-right clickable collapsed" data-toggle="collapse" data-target="#collapseVerde"
                      href="#collapseVerde"><i class="glyphicon glyphicon-chevron-down"></i></span>
            </div>

        </div>


        <div id="collapseVerde" class="panel-collapse collapse">

            <div class="panel-body">

                <div id="col-lg-5_comodinVerdeTrue" class="col-lg-5">
                    <label for="PenalizarVerdeTrue" id="PenalizarVerdeTrue">Penalizar respuesta correcta </label>
                    <input hidden id="idCorrVerdeTrue" type="text"
                           value="<?php echo $cuestionarios['correctorVerdeTrue'] ?>">

                    <select class="form-control input-sm" name="correctorVerdeTrue">
                        <option id="opCorrVerdeT020" value="0.20">Muy poco</option>

                        <option id="opCorrVerdeT025" value="0.25">Poco</option>

                        <option id="opCorrVerdeT033" value="0.33">Normal (recomendado)</option>

                        <option id="opCorrVerdeT05" value="0.5">Mucho</option>


                    </select>
                <span
                    class="help-block">La calificación se verá reducida, pero la nota final será <b>positiva</b> </span>
                </div>

                <div id="col-lg-5_comodinVerdeFalse" class="col-lg-5 pull-right   ">

                    <label for="PenalizarVerdeFalse" id="PenalizarVerdeFalse">Penalizar respuesta
                        incorrecta</label>
                    <input hidden id="idCorrVerdeFalse" type="text"
                           value="<?php echo $cuestionarios['correctorVerdeFalse'] ?>">

                    <select class="form-control input-sm " name="correctorVerdeFalse">

                        <option id="opCorrVerdeF020" value="-0.20">Muy poco</option>
                        <option id="opCorrVerdeF025" value="-0.25">Poco</option>
                        <option id="opCorrVerdeF033" value="-0.33">Normal</option>
                        <option id="opCorrVerdeF05" value="-0.5">Mucho (recomendado)</option>

                    </select>
                <span
                    class="help-block">La calificación se verá reducida, pero la nota final será <b>negativa</b> </span>

                </div>
            </div>
        </div>
    </div>


    <div id="col-lg-6_comodinesAmarillo" class="col-lg-6">

        <div class="panel panel-default   ">
            <div class="panel-heading">
                <label for="ComodinAmarillo" id="ComodinAmarillo">&nbsp;&nbsp;Configurar comodín ámbar<span
                        style="font-size: 19px;" class="glyphicon glyphicon-cog   pull-left"></span></label>
                <span class="pull-right clickable collapsed" data-toggle="collapse"
                      data-target="#collapseAmarillo"
                      href="#collapseAmarillo"><i class="glyphicon glyphicon-chevron-down"></i></span>
            </div>

        </div>

        <div id="collapseAmarillo" class="panel-collapse collapse">
            <div class="panel-body">

                <div id="col-lg-5_comodinAmarilloTrue" class="col-lg-5">
                    <label for="PenalizarAmarilloTrue" id="PenalizarAmarilloTrue">Penalizar respuesta
                        correcta <b>Positivo</b></label>
                    <input hidden id="idCorrAmarilloTrue" type="text"
                           value="<?php echo $cuestionarios['correctorAmarilloTrue'] ?>">

                    <select class="form-control input-sm " name="correctorAmarilloTrue">

                        <option id="opCorrAmarilloT020" value="0.2">Muy poco</option>
                        <option id="opCorrAmarilloT025" value="0.25">Poco</option>
                        <option id="opCorrAmarilloT033" value="0.33">Normal</option>
                        <option id="opCorrAmarilloT05" type="number" value="0.5">Mucho (recomendado)</option>


                    </select>
                <span
                    class="help-block">La calificación se verá reducida, pero la nota final será <b>positiva</b> </span>

                </div>

                <div id="col-lg-5_comodinAmarilloFalse" class="col-lg-5 pull-right   ">

                    <label for="PenalizarAmarilloFalse" id="PenalizarAmarilloFalse">Penalizar respuesta
                        incorrecta <b>Negativo</b></label>
                    <input hidden id="idCorrAmarilloFalse" type="text"
                           value="<?php echo $cuestionarios['correctorAmarilloFalse'] ?>">

                    <select class="form-control input-sm " name="correctorAmarilloFalse">

                        <option id="opCorrAmarilloF020" value="-0.20">Muy poco (recomendado)</option>
                        <option id="opCorrAmarilloF025" value="-0.25">Poco</option>
                        <option id="opCorrAmarilloF033" value="-0.33">Normal</option>
                        <option id="opCorrAmarilloF05" value="-0.5">Mucho</option>

                    </select>
                    <span class="help-block">La calificación se verá reducida, pero la nota final será <b>negativa</b> </span>

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
                       for="titulo Pregunta">Pregunta <?php echo $numPreg ?></label>

                <div class=" pull-right " id="pull-rightpmax<?php echo $numPreg ?>">
                    <label class="label" id="labelpmax<?php echo $numPreg ?>" for="pmax">Puntuacion max</label>

                    <input type="number" style="width: 65px" value="<?php echo $p['max_puntuacion']; ?>"
                           class=" input-sm text-primary text-left " min="1"
                           max="10" step="1" name="pregRes<?php echo $numPreg ?>[pmax]">

                    <a class="text-primary text-capitalize" data-idPreg="borrar<?php echo $numPreg ?>">&nbsp;&nbsp;
                    <span class="glyphicon glyphicon-remove-circle pull-right" onclick="deletePreg(this)"
                          style="font-size: 26px;"></span></a>
                </div>
            </div>
            <div id="panel-body<?php echo $numPreg ?>" class="panel-body">
                <div id="col-lg-12<?php echo $numPreg ?>" class="col-lg-12">
                    <textarea autocomplete="on" id="preg<?php echo $numPreg ?>" required
                              data-idPreg="<?php echo $numPreg ?>" name="pregRes<?php echo $numPreg ?>[title]"
                              class="form-control input-sm" rows="1"
                              placeholder="Escribe el enunciado de la pregunta"><?php echo $p['titulo'] ?></textarea>

                </div>


                <!--------------------------------------------------- RESPUESTA ------------------------------------------------->

                <?php
                $numRes = 1;


                foreach ($respuestasPregunta[$numPreg - 1] as $r) {

                    ?>
                    <div id="container<?php echo $numPreg ?>_<?php echo $numRes ?>" class="container">
                        <div id="row<?php echo $numPreg ?>_<?php echo $numRes ?>" class="row">
                            <div id="form-group<?php echo $numPreg ?>_<?php echo $numRes ?>" class="form-group">
                                <div class="col-lg-9 col-lg-push-1 control-label"
                                     id="col-lg-9col-lg-push-1control-label<?php echo $numPreg ?>_<?php echo $numRes ?>">
                                    <label id="labelRes<?php echo $numPreg ?>_<?php echo $numRes ?>"
                                           for="titulo Respuesta">
                                        <small>Respuesta <?php echo $numRes; ?></small>
                                    </label>

                                </div>
                                <?php if ($numRes == 1) { ?>
                                    <div class="col-lg-1 col-lg-push-1"
                                         id="col-lg-1col-lg-push-1<?php echo $numPreg ?>_1">
                                        <label id="esCorrecta<?php echo $numPreg ?>_1" for="esCorrecta">
                                            <small>Es correcta</small>
                                        </label>
                                    </div>
                                <?php } ?>
                                <div class="col-lg-9 col-lg-push-1" id="col-lg-9col-lg-push-1<?php echo $numPreg ?>_1">

                                    <textarea autocomplete="on" id="resp<?php echo $numPreg ?>_1"
                                              name="pregRes<?php echo $numPreg ?>[res][]"
                                              class="form-control input-sm"
                                              rows="1"><?php echo $r['tit_Respuesta']; ?></textarea>
                                </div>


                                <div class="col-lg-1  col-lg-push-1 radio control-label"
                                     id="col-lg-1col-lg-push-1radiocontrol-label<?php echo $numPreg ?>_<?php echo $numRes ?>">
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

                                <div class="col-lg-9  col-lg-push-1 " id="col-lg-9col-lg-push-11">
                                    <p></p>
                                    <button id="addRespuestaP<?php echo $numPreg ?>"
                                            data-idPreg="<?php echo $numPreg ?>" data-idRes="<?php echo $numRes ?>"
                                            type="button"
                                            class="btn btn-info btn-xs pull-right"
                                            onclick="addRespuesta(this);">
                                        <span id="iconoPlus<?php echo $numPreg ?>"
                                              class="glyphicon glyphicon-plus-sign"></span>
                                        Añadir
                                        Respuesta

                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <?php
                    $numRes++;
                } ?>
            </div>
        </div>
        <p id="newP<?php echo $numPreg + 1 ?>"><br><br><br><br></p>
    <?php } ?>

    <p id="newPreg">aa</p>
    <br>

    <div class="row">
        <button id="AddPregunta" onclick="addPregunta(this);" type="button"
                class="btn btn-info btn-sm pull-left">
            <span class="glyphicon glyphicon-plus-sign"></span> Nueva pregunta
        </button>
    </div>
    <div class="row">
    </div>
</div>
</div>


</form>


</body>

</html>
