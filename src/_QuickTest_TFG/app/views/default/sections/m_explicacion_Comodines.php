<!--seccion explicacion de comodines-->

<style type="text/css">
    .table td {
        text-align: center;

    }

    .table th {
        text-align: center;
    }


</style>

<script>


    $(function () {

        $("#includeModalVerde").load('/_QuickTest_TFG/app/views/default/modules/modalComodinVerde.php');
        $("#includeModalAmbar").load('/_QuickTest_TFG/app/views/default/modules/modalComodinAmbar.php');
        $("#includeModalSinComodin").load('/_QuickTest_TFG/app/views/default/modules/modalComodinSinComodin.php');


    });
</script>


<!--</head>-->
<!--<body>-->

<div class="row">
    <div class="col-lg-9 col-lg-push-1">
        <div class="container marketing">

            <!-- Three columns of text below the carousel -->
            <div class="row" id="comodinesExplicacion">


                <div class="page-header">
                    <div class="col-lg-6">

                        <h2><?php echo _("Comodines"); ?> </h2>
                    </div>

                </div>


                <div class="row">
                    <div class="col-lg-8">
                        <h5
                            class="help-block text-lowercase"
                            style="text-transform:initial;"><?php echo _("Tres formas de contestar a una pregunta, tú decides cual usar."); ?>
                        </h5>
                    </div>


                </div>
            </div>


            </h5>
            <hr id="linea" class="prettyline">


            <div class="row">

                <div class="col-md-4 text-center">
                    <img class="img-circle" src="/_QuickTest_TFG/app/webroot/images/verde.png">

                    <h3><?php echo _("Verde"); ?></h3>

                    <h6 class="text-lowercase"
                        style="text-transform:initial;"><?php echo _("¿Dudas MUCHO de tu respuesta?"); ?></h6>

                    <div>
                        <small class="help-block"><?php echo _("¿Prefieres asegurarte? "); ?></small>
                        <small class="help-block"><?php echo _("La penalización será "); ?>
                            <strong><?php echo _("elevada."); ?></strong></small>

                    </div>

                    <div class="col-md-4 col-md-push-3 text-center">
                        <p><a class="btn btn-primary" href="#" data-toggle="modal"
                              data-target="#myModalVerde"> <?php echo _("Ver un ejemplo"); ?> </a></p>
                    </div>


                </div>

                <div class="col-md-4  text-center">
                    <img class="img-circle" src="/_QuickTest_TFG/app/webroot/images/ambar.png">

                    <h3><?php echo _("Ámbar"); ?></h3>

                    <h6 class="text-lowercase"
                        style="text-transform:initial;"><?php echo _("¿CASI sabes la respuesta?"); ?></h6>

                    <div>

                        <small class="help-block" style="display: inline"><?php echo _("Obtén una solución "); ?>
                            <strong style="display: inline"><?php echo _("incierta "); ?></strong></small>
                        <small class="help-block"
                               style="display: inline"><?php echo _("y despeja tus dudas."); ?></small>
                        <small class="help-block"><?php echo _("A cambio, la penalización será "); ?>
                            <strong><?php echo _("leve."); ?></strong></small>
                        <div style="padding-top: 4px;"></div>


                    </div>


                    <div class="col-md-4 col-md-push-3 text-center" style="padding-left: 20px;">
                        <p><a class="btn btn-primary" href="#" data-toggle="modal"
                              data-target="#myModalAmbar"> <?php echo _("Ver un ejemplo"); ?> </a></p>
                    </div>

                </div>
                <div class="col-md-4  text-center">
                    <img class="img-circle" src="/_QuickTest_TFG/app/webroot/images/sin.png">

                    <h3><?php echo _("Sin comodín"); ?></h3>

                    <h6 class="text-lowercase"
                        style="text-transform:initial;"><?php echo _("¿Estás SEGURO de tu respuesta?"); ?></h6>

                    <div>

                        <small class="help-block"><?php echo _("Genial, no uses comodín. "); ?></small>
                        <small class="help-block"><?php echo _("Tendrás la puntuación "); ?>
                            <strong><?php echo _("máxima."); ?></strong></small>


                    </div>


                    <div class="col-md-4  col-md-push-3 text-center" style="padding-left: 20px;">
                        <p><a class="btn btn-primary" href="#" data-toggle="modal"
                              data-target="#myModalSinComodin"> <?php echo _("Ver un ejemplo"); ?> </a></p>
                    </div>


                </div>
            </div>
        </div>
    </div>
</div>





<hr id="lineaFinal" class="featurette-divider">




<div id="includeModalVerde"></div>
<div id="includeModalAmbar"></div>
<div id="includeModalSinComodin"></div>



