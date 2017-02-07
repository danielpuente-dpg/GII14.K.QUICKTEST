<!DOCTYPE html>
<!--vista para resolver un usuario-->

<html lang="es">

<head>
    </p>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title><?php echo $activityName ?> </title>

    <script src="/_QuickTest_TFG/app/webroot/js/jquery-2.1.4.min.js"></script>
    <link rel="stylesheet" href="/_QuickTest_TFG/app/webroot/css/bootstrap.min.css">
    <link rel="stylesheet" href="/_QuickTest_TFG/app/webroot/css/freelancer.css">
    <script src="/_QuickTest_TFG/app/webroot/js/bootstrap.min.js"></script>
    <link href="/_QuickTest_TFG/app/webroot/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="http://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">
    <link href="http://fonts.googleapis.com/css?family=Lato:400,700,400italic,700italic" rel="stylesheet"
          type="text/css">

    <style type="text/css">
        input[type="radio"] {
            /*margin-top: 12px;*/
            vertical-align: middle;
        }

        body {
            padding-top: 60px;
        }


    </style>


    <script type="text/javascript" src="app/views/default/myJS/cuestionario_Resolver.js"></script>


</head>

<body>


<nav class="navbar navbar-default navbar-fixed-top "
     style="padding-top:1px;  padding-bottom:1px;  height: 72px; position: fixed; top: -2px; ">


    <div class="col-xs-1" style="top: 6px;  ">
        <h5 id="logo" class="navbar-text text-left" style="display: inline">
            QuickTest</h5> <!--    se añade el small xq esta plantilla es enorme  -->
    </div>

    <div class="col-xs-9" id="col10-navbar">


        <div class="btn-group" id="mayor21">

            <?php


            foreach ($preguntas as $p) {


                ?>

                <button type="button" id="<?php echo $p['idPregunta']; ?>"
                        data-valor="<?php echo $valor += 1; ?>"

                        class="btn btn-default navbar-btn"
                        onclick="printSolution(this); gotoSolution(this);"><?php echo $numPreg += 1; ?></button>

            <?php
            }
            $numPreg = 0;
            ?>
        </div>
    </div>


    <div class="col-xs-2 ">
        <ul class="nav navbar-nav navbar-right">

            <li>
                <a id="botonLogout" class="btn btn-primary    navbar-btn"
                   data-toggle="tooltip" title="<?php echo _("Salir QuickTest"); ?>"
                   href="<?php echo $ltiConsumerURL; ?>"
                   type="button"
                   style="top: -6px;">
                    <span id="logout" class="glyphicon glyphicon-log-out"></span>

                </a>
            </li>
        </ul>

    </div>


</nav>
<input hidden="true" id="idCuestLTI" type="text" value="<?php echo $idCuestionario; ?>">

<button hidden="true" style="display: none" type="button" id="showContentButton2" class="btn btn-xs btn-block  "
        data-toggle="collapse"
        data-target="#demo"><span
        class="pull-left "></span> Boton mostrar
</button>


<div class="row">
    <div class="col-md-12">
        <br>

        <h3 class="text-center" id="activityName"><?php echo $activityName ?></h3>


        <h5 id="welcomeMessage" class="text-center help-block">
            <?php echo _("El cuestionario comenzará en :"); ?>
        </h5>

        <h3 id="timer" class="text-center help-block"></h3>

        <h5 id="welcomeMessage2" class="text-center help-block"><?php echo _("segundos"); ?></h5>
    </div>
</div>


<!-- ------------------------------------------PREGUNTAS ---------------------------------------------------------  !-->

<div id="demo" class="collapse">
    <div class="row">
        <div class="col-xs-2 col-xs-push-9  ">
            <button id="buttonCalificarCuest" class="btn btn-success btn-sm pull-right "
                    onclick="checkEmptyAnswers();">
                <?php echo _("Guardar y enviar"); ?>

            </button>
        </div>
    </div>
    <div class="row">
        <!--    <div class="container-fluid">-->

        <?php
        foreach ($preguntas as $p) {
            ?>

            <div class="form-group">
                <hr id="linea<?php echo $numPreg + 1; ?>" class="prettyline">

                <div class="col-xs-8 ">
                    <div class="container">

                        <!------------------------ numero de pregunta------------------ -->
                        <h4 class="help-block" data-textoPregunta="<?php echo $p['idPregunta']; ?>">
                            <?php echo _("Pregunta"); ?>&nbsp;<?php echo $numPreg += 1, '<br>'; ?>
                        </h4>
                    </div>
                </div>

                <div class="col-md-2  pull-left ">
                    <div style="display: inline; margin-top: -3.3em;    ">
                    <h6 class="text-capitalize text-right help-block pull-right"
                        style="display: inline; top:23px;  "><?php echo _("Punt max "); ?>&nbsp;&nbsp;

                            <span style="display: inline;   "
                              class="badge  pull-right"><?php echo $p['max_puntuacion']; ?></span>
                        </div>
                    </h6>

                </div>

                <div class="col-xs-10 ">
                    <div class="container">

                        <!-----------------------------------ENUNCIADO PREGUNTA------------------------------------>
                        <div class="form-control-static" id="enunciadoP<?php echo $p['idPregunta']; ?>">
                            <h5 style="text-transform:initial;" data-textoPregunta="<?php echo $p['idPregunta']; ?>"
                                class="text-lowercase">
                                <?php echo $p['tituloPreg'] ?></h5>
                        </div>
                    </div>
                </div>


                <?php

                $name += 1;
                ?>

                <form role="form" id="myForm" name="myForm" method="post" action="index.php"
                      data-preguntaid="<?php echo $p['idPregunta']; ?>">


                    <?php

                    foreach ($respuestasPregunta[$numPreg - 1] as $r) {

                        ?>
                        <div class="col-md-12 col-md-push-1 ">
                            <div class="radio">
                                 <label>


                                    <input type="radio" required="true"
                                           class=""
                                           name="radioAnswer<?php echo $r['pregunta_idPregunta']; ?>"
                                           id="optionRadio<?php echo $r['idRespuesta']; ?>"
                                           value="<?php echo $r['idRespuesta'] ?>">

                                    <h8 style="display:inline"
                                        id="textoPregunta<?php echo $r['idRespuesta']; ?>"
                                        data-textoPregunta="<?php echo $r['pregunta_idPregunta']; ?>">
                                        <small
                                            class="">
                                            <?php echo $r['tit_Respuesta'];

                                            ?></small>
                                        &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
                                    </h8>



                                </label>
                            </div>
                        </div>


                    <?php
                    } //  respuesta

                    ?>

            </div>


            <div class="row">
                <div class="col-xs-9"></div>
                <div class="col-xs-2  pull-left">
                    <button type="submit"
                            onclick="disableButtonSaveAnswer(this); "
                            class="btn btn-info btn-sm " name="ButtonSaveAnswer"
                            data-botonGuardar="<?php echo $r['pregunta_idPregunta']; ?>"
                            id="ButtonSaveAnswer<?php echo $o += 1; ?>">
                        <?php echo _("Guardar"); ?></button>
                </div>
            </div>

            </form>



        <?php

        }//foreach pregunta
        ?>

    </div>

    <br><br><br>

    <div class="row">
        <div class="col-md-8 col-md-push-2 ">
            <button id="buttonCalificarCuest" name="buttonCalificarCuestionario" class="btn btn-success btn-block"
                    onclick="checkEmptyAnswers();">
                <?php echo _("Finalizar cuestionario y enviar para calificación"); ?>

            </button>
        </div>
    </div>
    <!--    collapse-->
</div>

<div hidden="true">
    <input id="idUser" name="idUser" type="text" value="<?php echo $idUser; ?>">
    <input id="isProfesor" name="isProfesor" type="text" value="<?php echo $isProfesor; ?>">
    <input id="lang" name="lang" type="text" value="<?php echo $lang; ?>">
    <input id="idCuestLTI" type="text" value="<?php echo $idCuestionario; ?>">
    <input id="idAsig" type="text" value="<?php echo $idAsig; ?>">
    <input id="estaResuelto" type="text" value="<?php echo $estaResuelto; ?>">
    <input id="cuantasPreg" type="text" value="<?php echo $tam; ?>">

</div>

<div class="row">
    <hr id="line" class="prettyline">
</div>

<div class="row">


    <div class="col-lg-12">
        <div id="divResultados"></div>
        <div id="explicComodines"></div>
        <div id="explicOrden"></div>


    </div>


</div>

</body>
</html>

