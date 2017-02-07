<!--seccion tras login-->



<div class="row">
    <div id="bienvenida" class="col-lg-12">
        <h1 class="page-header">
            <?php
            if ($_POST['loginRegistro'] == 0) {
                echo _("Bienvenido de nuevo a QuickTest");
            } else {
                echo _("Gracias por registrarte, ya puedes empezar a usar QuickTest");
            }
            ?>
        </h1>
    </div>
</div>
<div class="row">

<div class="col-md-4">
    <div id="panelLTI" class="panel panel-primary">
        <div class="panel-heading">
            <h5 class="text-center">LTI &reg;
                <small class="text-center help-block" style="display:inline">Learning tools interoperability
                </small>
            </h5>


        </div>
        <div class="panel-body">
            <h8><?php echo _("Se trata de un estándar creado por la IMS."); ?></h8>
            <br>
            <br>

            <a href="#" data-toggle="collapse" data-target="#masInfo"><?php echo _("Más información") ?></a>
            <br>
            <small id="masInfo" class="collapse">
                <?php echo _("Tiene como objetivo ofrecer una estructura uniforme");?>
                <?php echo _("para integrar cualquier producto que cumpla con el estándar en una plataforma educativa."); ?>
            </small>

            <br>
            <hr>
            <small><?php echo _("Visita su web oficial para más información:"); ?></small>
            <br>
            <a href="http://www.imsglobal.org/toolsinteroperability2.cfm">
                <small>IMS GLOBAL Learning Consortium</small>
            </a>
        </div>


    </div>
</div>

<div class="col-md-4">
    <div id="panelPrimerosPasos" class="panel panel-success">
        <div class="panel-heading">

            <h4 class="text-center"> <?php echo _("Primeros pasos"); ?></h4>
        </div>
        <div class="panel-body">
            <h6><?php echo _("Ya tienes una cuenta en QuickTest, haz lo siguiente: "); ?></h6>
            <br>

            <h8><?php echo _("En primer lugar, accede con tu usuario y contraseña a una plataforma "); ?>
                <a href="#"
                   onclick="parpadear(panelLTI)"
                   class="text-primary"><b> LTI.</a> </b></h8>
            <br>
            <br>


            <h8><?php echo _("Una vez allí, entra a la asignatura en la que deseas publicar un cuestionario."); ?></h8>
            <br>
            <br>


            <h8><?php echo _("Necesitarás unos parámetros para conectarte con"); ?><b>
                    QuickTest.</b></h8>
            <br>
            <br>


            <h8><?php echo _("Haz click en el siguiente botón para conocerlos: "); ?>
            </h8>
            <br><br>

            <button type="button" class="btn btn-success btn-block" data-toggle="modal"
                    data-target="#myModal2"><?php echo _("Publicar cuestionario"); ?></button>


            <!-- Modal -->
            <div class="modal fade" id="myModal2" role="dialog">
                <div class="modal-dialog modal-lg">


                    <!-- Modal content-->
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close"
                                    data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">
                                <?php echo _("Para publicar, sigue estos pasos:"); ?></h4>
                        </div>
                        <div class="modal-body">

                            <label
                                class="text-muted"><?php echo _("Crea una actividad desde tu plataforma LTI de tipo:"); ?>
                                &nbsp;&nbsp;</label>

                            <h5 class="text-success"
                                style="display:inline">
                                <?php echo _("Actividad externa"); ?></h5>
                            <br><br><br>
                            <label
                                class="help-block "><?php echo _("Y copia estos datos en las casillas correspondientes"); ?>
                                &nbsp;&nbsp;</label>

                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <th><?php echo _("URL de inicio"); ?></th>
                                    <th><?php echo _("Clave de cliente"); ?></th>
                                    <th><?php echo _("Shared secret"); ?></th>

                                </tr>

                                </thead>
                                <tbody>
                                <tr>
                                    <td>
                                        <small><?php echo $_POST['resource_link']; ?>
                                        </small>
                                    </td>
                                    <td>
                                        <small><?php echo $_POST['consumer_key']; ?>
                                    </td>
                                    <td>
                                        <small>
                                            <?php echo _("Tu contraseña de QuickTest"); ?> </small>
                                    </td>

                                </tr>
                                </tbody>
                            </table>

                            <h5 class="text-danger text-center"><?php echo _("IMPORTANTE"); ?>:</h5>
                            <label
                                class="help-block "><?php echo _("En la pestaña 'privacidad', no olvides marcar:"); ?>
                                &nbsp;&nbsp;</label>

                            <div class="checkbox col-lg-6 col-lg-push-1">
                                <label>
                                    <input type="checkbox" checked="true"
                                           disabled><?php echo _("Aceptar calificaciones desde la herramienta"); ?>
                                </label>
                                <br>
                                <label>
                                    <input type="checkbox" checked="true"
                                           disabled><?php echo _("Compartir el e-mail del usuario con la herramienta"); ?>
                                </label>
                                <br>

                                <label>
                                    <input type="checkbox" checked="true"
                                           disabled><?php echo _("Aceptar calificaciones desde la herramienta"); ?>
                                </label>
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


        </div>


    </div>
</div>

<div class="col-md-4">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h4 class="text-center"><?php echo _("¿Por qué usar"); ?>
                <p
                    class="text-center help-block" style="display:inline">QuickTest
                </p>
                ?
            </h4>
        </div>
        <div class="panel-body">


            <dl class="dl-horizontal">
                <dt><?php echo _("Seguridad"); ?></dt>
                <dd><?php echo _("El"); ?>
                    <em>LTI_consumer</em><?php echo _(" es el encargado de la autenticación."); ?></dd>
                <br>
                <dt class=""><?php echo _("Amplia comunidad "); ?></dt>
                <dt class=""><?php echo _("de usuarios"); ?></dt>

                <dd><?php echo _("Utilizable en todas los centros educativos que empleen Moodle u otra plataforma LTI."); ?></dd>
                <br>
                <dt><?php echo _("Divertido"); ?></dt>
                <dt><?php echo _(" para el alumno"); ?></dt>

                <dd><?php echo _("Favorece la competición entre los alumnos, en el ámbito del juego."); ?></dd>
                <br>
                <dt><?php echo _("Útil para el docente"); ?></dt>
                <dd><?php echo _("El profesor puede evaluar el grado de atención de los alumnos durante la clase, así como la asimilación de los conceptos explicados "); ?>
                </dd>
                <br>
            </dl>


        </div>
    </div>
</div>
</div>


