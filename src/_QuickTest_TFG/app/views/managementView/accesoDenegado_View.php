<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title><?php echo _("Acceso denegado"); ?> </title>



    <script src="/_QuickTest_TFG/app/webroot/js/jquery-2.1.4.min.js"></script>


    <link rel="stylesheet" href="/_QuickTest_TFG/app/webroot/css/bootstrap.min.css">

    <link rel="stylesheet" href="/_QuickTest_TFG/app/webroot/css/freelancer.css">

    <script src="/_QuickTest_TFG/app/webroot/js/bootstrap.min.js"></script>


    <link href="/_QuickTest_TFG/app/webroot/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="http://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">
    <link href="http://fonts.googleapis.com/css?family=Lato:400,700,400italic,700italic" rel="stylesheet"
          type="text/css">

</head>

<body style="padding-top: 70px;">


<!-- Navigation -->
<!-- Navigation -->
<nav class="navbar navbar-default navbar-fixed-top " style="height: 80px; top: -20px;  ">


    <p class="navbar-text pull-right"><?php echo _("QuickTest"); ?></p>
</nav>
<!-- Page Content -->
<div class="container">


    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header"><?php echo _("Error"); ?>
                <small class="text-danger"><?php echo _("Acceso denegado"); ?></small>
                <hr id="line" class="prettyline">
            </h1>

        </div>
    </div>

    <!-- /.row -->

    <div class="row">

        <div class="col-lg-12">
            <div class="jumbotron">
                <p class="text-danger"><?php echo $mensaje; ?></p>
                <h6 class="text-lowercase" style="text-transform:initial;"><?php echo _("Probablemente:"); ?>
                </h6>
                <ul>
                    <li>
                        <?php echo _("No estés accediendo a través de una plataforma LTI."); ?>
                    </li>
                    <li>
                        <?php echo _("Tu clave de cliente (oauth_consumer_key) no sea la correcta."); ?>
                    </li>
                    <li>
                        <?php echo _("Se halla producido un error en la firma del documento, debido a que tu clave personal (Shared secret) no es correcta."); ?>
                    </li>
                </ul>
                <label><?php echo _("Revisa estos datos en tu plataforma LTI y vuelve a intentarlo."); ?></label>
                <div class="row">
                    <div class="col-lg-8 col-lg-push-2">
                        <img class="img-responsive img-rounded" src="/_QuickTest_TFG/app/webroot/images/configurarLTI.PNG" alt="correctaAmbar">
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>



</body>

</html>
