<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>

    <meta name="description" content="">
    <meta name="author" content="">

    <title>QuickTest</title>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>

    <link rel="stylesheet" href="/_QuickTest_TFG/app/webroot/css/bootstrap.min.css">
    <link rel="stylesheet" href="/_QuickTest_TFG/app/webroot/css/freelancer.css">

    <script src="/_QuickTest_TFG/app/webroot/js/bootstrap.min.js"></script>
    <script src="/_QuickTest_TFG/app/webroot/js/jquery.validate.min.js"></script>

    <link href="/_QuickTest_TFG/app/webroot/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="http://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">
    <link href="http://fonts.googleapis.com/css?family=Lato:400,700,400italic,700italic" rel="stylesheet"
          type="text/css">
    <style type="text/css">


        .carousel .item {
            width: 100%; /*slider width*/

            max-height: 270px; /*slider height*/
        }

        .fill {
            width: 100%;
            height: 100%;
            padding-left: -10px;
            background-position: center;
            background-size: cover;
        }

        .carousel .item img {
            width: 100%; /*img width*/
        }

        /*add some makeup*/
        .carousel .carousel-control {
            /*background: none;*/
            /*border: none;*/
            /*top: 50%;*/

        }

        .carousel-indicators li {
            background-color: #808080;
            background-color: rgba(70, 70, 70, .25);
        }

        .carousel-indicators .active {
            background-color: #000033;
        }


    </style>

    <script type="text/javascript" src="../default/myJS/start_QuickTest.js"></script>

</head>

<body>
<nav class="navbar navbar-default navbar-fixed-top " style="height: 1px; top: -25px;">


    <!--    <div class="col-xs-1">-->
    <!--        <p id="logo" class="navbar-text text-left">-->
    <!--            Logo</p> <!--    se añade el small xq esta plantilla es enorme  -->-->
    <!--    </div>-->
    <!---->
    <!--    <div class="col-xs-10" id="col10-navbar">-->
    <!---->
    <!--    </div>-->
    <!---->
    <!---->
    <!--    <ul class="nav navbar-nav navbar-right">-->
    <!---->
    <!---->
    <!--        <p id="quickTest" class="navbar-text text-left">-->
    <!--            QuickTest</p> <!--    se añade el small xq esta plantilla es enorme  -->-->
    <!---->
    <!---->
    <!--    </ul>-->
    <!--    </div>-->


</nav>


<div class="container fill">
    <br>

    <div id="myCarousel" class="carousel slide " data-ride="carousel">
        <!-- Indicators -->
        <ol class="carousel-indicators text-primary">
            <li data-target="#myCarousel" data-slide-to="0" class="  active"></li>
            <li data-target="#myCarousel" data-slide-to="1"></li>
            <li data-target="#myCarousel" data-slide-to="2"></li>
        </ol>


        <!-- Wrapper for slides -->
        <div class="carousel-inner">
            <div class="item active">
                <div class="fill">
                    <img src='../../webroot/images/responsive2.png' alt="Chania">
                    <!--                <div class="fill" style="background-image:url('http://placehold.it/1900x1080&text=Slide One');"></div>-->
                </div>
                <div class="carousel-caption">

                    <h3 class="text-primary"><br><?php echo _("Diseño adaptativo");?></h3>
                </div>
            </div>
            <div class="item ">
                <div class="fill">
                    <img src='../../webroot/images/seguridad.png' data-src='../../webroot/images/seguridad.png'
                         alt="Chania">
                    <!--                <div class="fill" style="background-image:url('http://placehold.it/1900x1080&text=Slide One');"></div>-->
                </div>
                <div class="carousel-caption">
                    <h3 class="text-primary"><br><?php echo _("Calificaciones seguras");?></h3>
                </div>
            </div>

            <div class="item ">
                <div class="fill">
                    <img src='../../webroot/images/competicion.png' data-src='../../webroot/images/competicion.png'
                         alt="Chania">
                    <!--                <div class="fill" style="background-image:url('http://placehold.it/1900x1080&text=Slide One');"></div>-->
                </div>
                <div class="carousel-caption">
                    <h3 class="text-primary"><br><?php echo _("Diviértete compitiendo"); ?> </h3>
                </div>
            </div>


        </div>

        <!-- Left and right controls -->
        <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
            <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
        </a>
        <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
            <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
        </a>
    </div>
</div>
<!-- Page Content -->
<div id="contenidoLogin" class="container">

    <!-- Marketing Icons Section -->
    <div class="row">
        <div class="col-lg-12">

            <hr id="line" class="prettyline">

            <center>
                <h1><b>QuickTest</b></h1>

                <h4><?php echo _("Accede a tu cuenta o regístrate para poder usar QuickTest"); ?></h4>
                <br>
                <br>
                <button class="btn btn-primary btn-lg" href="#registrarse" data-toggle="modal"
                        data-target=".bs-modal-sm">
                    <?php echo _("Acceder/Registrarse"); ?>
                </button>
            </center>
            <br>

            <div id="newContent">

            </div>
            <hr class="prettyline">


        </div>
    </div>

    <!-- Modal -->
    <div class="modal fade bs-modal-sm" id="myModal" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel"
         aria-hidden="true">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <br>

                <div class="bs-example bs-example-tabs">
                    <ul id="myTab" class="nav nav-tabs">
                        <li class="active"><a href="#login" data-toggle="tab"><?php echo _("Entrar"); ?></a></li>
                        <li class=""><a href="#registrarse" data-toggle="tab"><?php echo _("Registrarse"); ?></a></li>
                        <li class=""><a href="#why" data-toggle="tab"><?php echo _("¿Por qué?"); ?></a></li>
                    </ul>
                </div>
                <div class="modal-body">
                    <div id="myTabContent" class="tab-content">
                        <div class="tab-pane fade in" id="why">
                            <h9><?php echo _("Necesitamos esta información para poder ofrecerte un acceso seguro a QuickTest."); ?></h9>


                        </div>
                        <div class="tab-pane fade active in" id="login">
                            <form id="formLoginUsuarioLTI" method="post" action="../../../index.php">
                                <form class="form-horizontal">
                                    <fieldset>
                                        <!-- ----------------------LOGIN ----------------------------------------- -->
                                        <!-- Text input-->
                                        <div class="control-group">
                                            <label class="control-label" for="emailUsuario">Email:</label>

                                            <div class="controls">
                                                <input required="" id="emailUsuario" name="emailUsuario"
                                                       type="email"
                                                       class="form-control"
                                                       placeholder="<?php echo _("Introduce tu email"); ?>"
                                                       required="true"
                                                       class="input-medium" required="true">
                                            </div>

                                        </div>

                                        <!-- Password input-->
                                        <div class="control-group">
                                            <label class="control-label"
                                                   for="passwordUsuario"><?php echo _("Contraseña: "); ?></label>

                                            <div class="controls">
                                                <input required="" id="passwordUsuario" name="passwordUsuario"
                                                       class="form-control" type="password"
                                                       required="true"
                                                       placeholder="<?php echo _("5 a 10 caracteres"); ?>"
                                                       class="input-medium">
                                            </div>
                                        </div>
                                        <div id="errorLogin"></div>

                                        <!-- Button -->
                                        <div class="control-group">
                                            <label class="control-label" for="login"></label>

                                            <div class="controls">
                                                <button type="submit" onclick="loginQuickTest();" id="login"
                                                        name="login"

                                                        class="btn btn-success "
                                                        value="login"><?php echo _("Entrar"); ?>
                                                </button>
                                            </div>
                                        </div>
                                    </fieldset>
                                </form>
                            </form>
                        </div>
                        <!----------------------------------------------------REGISTRARSE---------------------------------------->
                        <div class="tab-pane fade" id="registrarse">
                            <form id="formRegisterUsuarioLTI" method="post" action="../../../index.php">
                                <form class="form-horizontal">
                                    <fieldset>
                                        <!-- Registrarse Form -->
                                        <!-- Text input-->
                                        <div class="control-group">
                                            <label class="control-label" for="Email">Email:</label>

                                            <div class="controls">
                                                <input id="nuevoEmailUsuario" name="nuevoEmailUsuario"
                                                       class="form-control"
                                                       type="email"
                                                       placeholder="<?php echo _("nombreUsuario"); ?>@ubu.es"
                                                       class="input-large"
                                                       autocomplete="on"

                                                       required="true">
                                            </div>
                                            <small class="pull-right text-warning">
                                                <em><?php echo _("Muy importante: Utiliza el mismo email, con el que accedes a tu plataforma LTI "); ?></em>
                                            </small>
                                        </div>


                                        <!-- Password input-->
                                        <div class="control-group">
                                            <label class="control-label"
                                                   for="password"><?php echo _("Contraseña: "); ?></label>

                                            <div class="controls">
                                                <input id="nuevoPasswordUsuario" name="nuevoPasswordUsuario"
                                                       class="form-control" type="password"
                                                       required="true"
                                                       placeholder="5 a 10 caracteres" class="input-large">

                                            </div>
                                        </div>

                                        <!-- Text input-->
                                        <div class="control-group">
                                            <label id="confirmNuevoPasswordUsuario"
                                                   name="confirmNuevoPasswordUsuario"
                                                   class="control-label"
                                                   for="reenterpassword"><?php echo _("Confirmar contraseña: "); ?></label>

                                            <div class="controls">
                                                <input id="confirmNuevoPasswordUsuario" class="form-control"
                                                       name="confirmNuevoPasswordUsuario" type="password"
                                                       required="true"
                                                       placeholder="********" class="input-large">
                                            </div>
                                        </div>
                                        <div id="errorRegistro"></div>

                                        <!-- Button -->
                                        <div class="control-group">
                                            <label class="control-label" for="registrarse"></label>

                                            <div class="controls">
                                                <input type="submit" id="buttonRegistrarse" name="registrar"
                                                       onclick="registrarQuickTest();"
                                                       class="btn btn-success" value="<?php echo _("Registrarse"); ?>">
                                                </button>
                                            </div>


                                        </div>

                                    </fieldset>
                                </form>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <center>
                        <button id="botonCerrar" type="button" class="btn btn-default"
                                data-dismiss="modal"><?php echo _("Cerrar"); ?>
                        </button>
                    </center>
                </div>
            </div>
        </div>
    </div>
</div>


<div id="contenidoDespuesLogin" class="container">

</div>


<!-- Footer -->
<footer>

    <hr id="line" class="prettyline">


    <small><em>Universidad de Burgos. QuickTest 2015</em></small>


</footer>

<!-- /.container -->


</body>

</html>
