<!--pasos para publicar-->
<div class="container">
    <h4 class="page-header"><?php echo _("Para publicar, sigue estos pasos:"); ?></h4>
</div>


    <label
        class="text-muted"><?php echo _("Crea una actividad desde tu plataforma LTI de tipo:"); ?>
        &nbsp;&nbsp;</label>

    <h5 class="text-success"
        style="display:inline"><?php echo _("Actividad externa"); ?></h5>
    <br><br><br>

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
                <small><?php echo _("URL proporcionada al crear el test"); ?>
                </small>
            </td>
            <td>
                <small><?php echo _("Identificador que recibiste cuando te registraste"); ?>
            </td>
            <td>
                <small><?php echo _("Tu contraseña personal de QuickTest"); ?> </small>
            </td>
            <td>
                <small>
                    <?php echo _("Este parametro lo obtendrás cada vez que crees un cuestionario"); ?></small>
            </td>
        </tr>
        </tbody>
    </table>
    <label
        class="help-block "><?php echo _("Y copia estos datos en las casillas correspondientes de tu plataforma LTI"); ?>
        </label>
        <img class="img-responsive" src="/_QuickTest_TFG/app/webroot/images/configurarLTI.PNG" alt="configurarLTI">

    <br>


</div>
