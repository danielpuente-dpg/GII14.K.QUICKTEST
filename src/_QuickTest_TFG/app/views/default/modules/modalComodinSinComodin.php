<!-- Modal Sin comodin -->
<div class="modal fade" id="myModalSinComodin" role="dialog">
    <div class="modal-dialog modal-lg">


        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal">&times;</button>
                <h4 class="modal-title" style="display: inline">
                    <?php echo _("Respuesta:"); ?> <h4 style="display: inline"
                                                       class="help-block"><?php echo _("SIN comodín"); ?> </h4></h4>
            </div>
            <div class="modal-body">

                <small
                    ><?php echo _("Supongamos una pregunta, cuya puntuación máxima es:"); ?>
                    &nbsp;&nbsp;</small>


                <h5 class="help-block"
                    style="display:inline">
                    <?php echo _("10"); ?></h5>
                <br>
                <small
                    ><?php echo _("Responder sin comodín tiene sus ventajas:"); ?>
                    &nbsp;&nbsp;</small>
                <br><br>

                <div class="row">
                    <div class="col-lg-6 col-lg-push-3">
                        <table class="table table-striped ">
                            <thead>
                            <tr class="active">
                                <th> <?php echo _("Es correcta"); ?>  </th>
                                <th><?php echo _("Puntuación obtenida"); ?></th>
                            </tr>

                            </thead>
                            <tbody>
                            <tr>
                                <td>
                                    <small class="text-success"><?php echo _("SÍ"); ?>
                                    </small>
                                </td>
                                <td>
                                    <small><?php echo _("Máxima puntuación 100%"); ?></small>
                                </td>


                            </tr>

                            <tr>
                                <td>
                                    <small class="text-danger"><?php echo _("NO"); ?>
                                    </small>
                                </td>
                                <td>

                                    <small><?php echo _("0 puntos"); ?></small>


                                </td>


                            </tr>
                            </tbody>

                        </table>

                        <small
                            class="help-block pull-right"><?php echo _("Observa que si te equivocas sin usar un comodín, NO obtienes una puntuación negativa"); ?>
                            &nbsp;&nbsp;</small>
                    </div>
                </div>
                <br>

                <div class="row">
                    <div class="container">

                        <h5>
                            <?php echo _("Ejemplo 1. "); ?>
                            <small class="help-block"><?php echo _("Respuesta correcta."); ?>
                            </small>
                        </h5>

                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-8 col-lg-push-2">
                        <img class="img-responsive img-rounded" src="/_QuickTest_TFG/app/webroot/images/correctaSin.PNG" alt="correctaAmbar">
                    </div>
                </div>
                <div class="text-center">
                    <h6 class=" text-lowercase"
                        style="text-transform:initial;"><?php echo _("Puntuación total= Puntuación máxima"); ?></h6>
                    <h5 class=" help-block " style="display: inline; "><?php echo _("100 %"); ?></h5>
                    <h6 class=" text-lowercase" style="display: inline; text-transform:initial;"> de 10p = </h6>
                    <h4 class=" help-block " style="display: inline"> 10</h4>
                    <h6 class=" text-lowercase"
                        style="display: inline; text-transform:initial;"><?php echo _("puntos"); ?></h6>
                </div>

                <div class="row">
                    <div class="container">

                        <h5>
                            <?php echo _("Ejemplo 2. "); ?>
                            <small class="help-block"><?php echo _("Respuesta incorrecta."); ?>
                            </small>
                        </h5>

                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-8 col-lg-push-2">
                        <img class="img-responsive" src="/_QuickTest_TFG/app/webroot/images/incorrectaSin.PNG" alt="incorrectaAmbar">
                    </div>
                </div>

                <div class="text-center">
                    <h6 class=" text-lowercase"
                        style="text-transform:initial;"><?php echo _("Puntuación total= 0 puntos "); ?></h6>
                    <h5 class=" text-danger " style="display: inline; "><?php echo _("0 %"); ?></h5>
                    <h6 class=" text-lowercase" style="display: inline; text-transform:initial;"> de 10p = </h6>
                    <h4 class=" text-danger " style="display: inline"> 0 </h4>
                    <h6 class=" text-lowercase"
                        style="display: inline; text-transform:initial;"><?php echo _("puntos"); ?></h6>
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



