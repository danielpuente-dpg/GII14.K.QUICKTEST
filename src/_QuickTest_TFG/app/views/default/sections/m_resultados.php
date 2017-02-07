<!--seccion resultados-->

<style type="text/css">
    .table td {
        text-align: center;
    }

    .table th {
        text-align: center;
    }

</style>
<!-- Marketing Icons Section -->


<div class="row">
    <div class="container">
        <div class="col-md-7  ">
            <div class="panel panel-primary">
                <div class="panel-heading"><h4 class="text-lowercase" style="text-transform:initial;">
                        <?php echo _("Resultados obtenidos"); ?></h4>
                </div>
                <!-- /.panel-heading -->
                <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table ">
                            <thead>

                            <tr class="table-bordered active ">
                                <th><h5 class="text-lowercase"
                                        style="text-transform:initial;"><?php echo _("Modo respuesta"); ?></h5></th>
                                <th><h5 class="text-lowercase"
                                        style="text-transform:initial;"><?php echo _("Preguntas contestadas"); ?></h5>
                                </th>
                                <th><h5 class="text-lowercase"
                                        style="text-transform:initial;"><?php echo _("Aciertos"); ?></h5>
                                </th>
                                <th><h5 class="text-lowercase"
                                        style="text-transform:initial;"><?php echo _("Fallos"); ?> </h5>
                                </th>
                                <th><h5 class="text-lowercase"
                                        style="text-transform:initial;"><?php echo _("Puntuación"); ?> </h5></th>

                            </tr>

                            </thead>

                            <tbody>
                            <tr class="">
                                <td><h6><?php echo _("Sin comodín"); ?></h6></td>
                                <td><h6><?php echo $_POST['cSin']; ?></h6></td>
                                <td><h6><?php echo $_POST['aciertoSin']; ?></h6></td>
                                <td><h6> <?php echo $_POST['fallosSin']; ?></h6></td>
                                <td id="puntSin"><h5><?php echo $_POST['puntSin']; ?></h5></td>
                            </tr>
                            <tr class="success">
                                <td><h6><?php echo _("Comodín verde"); ?></h6></td>
                                <td><h6><?php echo $_POST['cVerde']; ?></h6></td>
                                <td><h6><?php echo $_POST['aciertoVerde']; ?></h6></td>
                                <td><h6><?php echo $_POST['fallosVerde']; ?></td>
                                <td id="puntVerde"><h5><?php echo $_POST['puntVerde']; ?></h5></td>
                            </tr>
                            <tr class="warning">
                                <td><h6><?php echo _("Comodín ámbar"); ?></h6></td>
                                <td><h6><?php echo $_POST['cAmarillo']; ?> </h6></td>
                                <td><h6><?php echo $_POST['aciertoAmarillo']; ?> </h6></td>
                                <td><h6><?php echo $_POST['fallosAmarillo']; ?> </h6></td>
                                <td id="puntAmarillo"><h5><?php echo $_POST['puntAmarillo'];; ?></h5></td>
                            </tr>

                            </tbody>
                            <tbody>

                            <tr id="premioTable" class="<?php echo $_POST['tipoText']; ?>">
                                <td class=""><h6><?php echo $_POST['ordenRespuesta']; ?></h6></td>
                                <td>-</td>
                                <td>-</td>
                                <td>-</td>
                                <td class="<?php echo $_POST['tipoText']; ?> ">
                                    <h5> <?php echo $_POST['premio']; ?></h5></td>
                            </tr>

                            </tbody>

                            <thead>
                            <tr class="table-bordered ">
                                <th class="small text-center"><h6
                                        class="  pull-center"><?php echo _("Total"); ?></h6>
                                </th>
                                <th class="text-center">
                                    <h6><?php echo($_POST['cSin'] + $_POST['cVerde'] + $_POST['cAmarillo']); ?>
                                        / <?php echo $_POST['totalPreguntas']; ?></th>
                                <th class="text-center">
                                    <h6><?php echo($_POST['aciertoSin'] + $_POST['aciertoVerde'] + $_POST['aciertoAmarillo']); ?>
                                </th>
                                <th class="text-center">
                                    <h6><?php echo($_POST['fallosSin'] + $_POST['fallosVerde'] + $_POST['fallosAmarillo']); ?>
                                </th>
                                <th id="puntTot" class="text-center">
                                    <h5 class="help-block">

                                        <?php echo $_POST['puntTotal']; ?>
                                        <small> / <?php echo $_POST['puntMaxPosible']; ?>.00</small>
                                    </h5>
                                </th>
                            </tr>
                            </thead>

                        </table>
                    </div>
                    <!-- /.table-responsive -->
                </div>
                <!-- /.panel-body -->
            </div>
            <!-- /.panel -->
        </div>


        <div class="col-lg-4 col-lg-push-2 ">


            <div class="row">

                <div class="col-md-11 col-md-pull-1  ">
                    <div class="panel <?php echo $_POST['tipoPanel']; ?>">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">

                                    <i class="fa fa-clock-o fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div
                                        class="huge"><?php echo $_POST['mensaje']; ?></div>
                                </div>
                            </div>
                        </div>
                        <div class="panel-body">

                            <div class="col-xs-12 text-center">
                                <div><h1 id="premioPanel" style="display: inline"
                                         class="<?php echo $_POST['tipoText']; ?>"> <?php echo $_POST['premio']; ?></h1>
                                    <h4 style="display: inline"><?php echo _("puntos"); ?></h4></div>
                            </div>

                        </div>
                        <a href="#" onclick="gotoOrdenExplicacion();">
                            <div class="panel-footer">
                                <span class="pull-left <?php echo $_POST['tipoText']; ?>"><?php echo _("Más informacion"); ?></span>
                                <span class="pull-right <?php echo $_POST['tipoText']; ?>"><i class="fa fa-info <?php echo $_POST['tipoText']; ?>"></i></span>

                                <div class="clearfix"></div>
                            </div>
                        </a>
                    </div>

                </div>
            </div>


            <div class="row">
                <div class="col-lg-11 col-lg-pull-1">
                    <button type="button" id="showContentButton" class="btn btn-block  " data-toggle="collapse"
                            data-target="#demo"><span
                            class="pull-left glyphicon glyphicon-collapse-up"></span> <?php echo _("Revisar cuestionario"); ?>



                        <div class="row">


                            <button type="button" id="showContentButton" class="btn   btn-block  "
                                    onclick="gotoComodinesExplicacion();"
                                ><span
                                    class="pull-left glyphicon glyphicon-info-sign"></span> <?php echo _("Funcionamiento comodines"); ?>
                            </button>



                        </div>
                </div>


            </div>
        </div>
    </div>
</div>
</div>



</body>

</html>
