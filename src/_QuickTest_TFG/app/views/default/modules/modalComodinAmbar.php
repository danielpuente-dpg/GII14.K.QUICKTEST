<!-- Modal AMBAR -->
<div class="modal fade" id="myModalAmbar" role="dialog">
<div class="modal-dialog modal-lg">


<!-- Modal content-->
<div class="modal-content">
    <div class="modal-header">
        <button type="button" class="close"
                data-dismiss="modal">&times;</button>
        <h4 class="modal-title" style="display: inline">
            <?php echo _("Comodín usado:"); ?> <h4 style="display: inline"
                                                   class="text-warning"><?php echo _("Ámbar"); ?> </h4></h4>
    </div>
    <div class="modal-body">

        <small
            ><?php echo _("Supongamos una pregunta, cuya puntuación máxima es:"); ?>
            &nbsp;&nbsp;</small>


        <h5 class="text-warning"
            style="display:inline">
            <?php echo _("10"); ?></h5>
        <br>
        <small
            ><?php echo _("Siempre que utilizas un comodín, se aplican unos correctores, previamente configurados por el profesor:"); ?>
            &nbsp;&nbsp;</small>
        <br><br>
        <div class="row">
            <div class="col-lg-6 col-lg-push-3">
                <table class="table table-striped ">
                    <thead>
                    <tr class="active">
                        <th> <?php echo _("Es correcta"); ?>  </th>
                        <th><?php echo _("Muy poco"); ?></th>
                        <th><?php echo _("Poco"); ?></th>
                        <th><?php echo _("Normal"); ?></th>
                        <th><?php echo _("Mucho"); ?>  </th>


                    </tr>

                    </thead>
                    <tbody>
                    <tr class="warning">
                        <td>
                            <small><?php echo _("SÍ"); ?>
                            </small>
                        </td>
                        <td>
                            <small><?php echo _("20%"); ?></small>
                        </td>
                        <td>
                            <small>
                                <?php echo _("25%"); ?> </small>
                            <br>
                            <small>
                                <?php echo _("recomendado"); ?> </small>
                        </td>
                        <td>
                            <small>
                                <?php echo _("33%"); ?> </small>

                        </td>

                        <td>
                            <small>
                                <?php echo _("50%"); ?> </small>
                        </td>

                    </tr>

                    <tr class="danger">
                        <td>
                            <small><?php echo _("NO"); ?>
                            </small>
                        </td>
                        <td>
                            <h5 style="display: inline">-</h5>
                            <small><?php echo _("20%"); ?></small>
                            <br>
                            <small>
                                <?php echo _("recomendado"); ?> </small>
                        </td>
                        <td>
                            <h5 style="display: inline">-</h5>

                            <small>
                                <?php echo _("25%"); ?> </small>
                        </td>
                        <td>
                            <h5 style="display: inline">-</h5>

                            <small>
                                <?php echo _("33%"); ?> </small>
                        </td>

                        <td>
                            <h5 style="display: inline">-</h5>

                            <small>
                                <?php echo _("50%"); ?> </small>

                        </td>

                    </tr>
                    </tbody>

                </table>

                <small
                    class="help-block pull-right"><?php echo _("Observa que si te equivocas usando un comodín, los correctores tienen un valor negativo"); ?>
                    &nbsp;&nbsp;</small>
            </div>
        </div>
        <br>

        <div class="row">
            <div class="container">

                <h5>
                    <?php echo _("Ejemplo 1. "); ?>
                    <small class="help-block"><?php echo _("Respuesta correcta."); ?> <em
                            class="text-muted"><?php echo _("(valores de los correctores por defecto):"); ?></em>
                    </small>
                </h5>

            </div>
        </div>
        <div class="row">
            <div class="col-lg-8 col-lg-push-2">
                <img class="img-responsive img-rounded" src="/_QuickTest_TFG/app/webroot/images/correctaAmbar.PNG" alt="correctaAmbar">
            </div>
        </div>
        <div class="text-center">
            <h6 class=" text-lowercase"
                style="text-transform:initial;"><?php echo _("Puntuación total= Puntuación máxima X corrector "); ?></h6>
            <h5 class=" text-warning " style="display: inline; "><?php echo _("25 %"); ?></h5>
            <h6 class=" text-lowercase" style="display: inline; text-transform:initial;"> de 10p = </h6>
            <h4 class=" text-warning " style="display: inline"> 2.50</h4>
            <h6 class=" text-lowercase"
                style="display: inline; text-transform:initial;"><?php echo _("puntos"); ?></h6>
        </div>

        <div class="row">
            <div class="container">

                <h5>
                    <?php echo _("Ejemplo 2. "); ?>
                    <small class="help-block"><?php echo _("Respuesta incorrecta."); ?> <em
                            class="text-muted"><?php echo _("(valores de los correctores por defecto):"); ?></em>
                    </small>
                </h5>

            </div>
        </div>
        <div class="row">
            <div class="col-lg-8 col-lg-push-2">
                <img class="img-responsive" src="/_QuickTest_TFG/app/webroot/images/incorrectaAmbar.PNG" alt="incorrectaAmbar">
            </div>
        </div>

        <div class="text-center">
            <h6 class=" text-lowercase"
                style="text-transform:initial;"><?php echo _("Puntuación total= Puntuación máxima X corrector "); ?></h6>
            <h5 class=" text-danger " style="display: inline; "><?php echo _("-20 %"); ?></h5>
            <h6 class=" text-lowercase" style="display: inline; text-transform:initial;"> de 10p = </h6>
            <h4 class=" text-danger " style="display: inline"> -0.20</h4>
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



