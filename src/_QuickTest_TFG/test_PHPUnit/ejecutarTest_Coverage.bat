:Ejecuta un test de cobertura, generando un fichero html en el que visualizar los resultados
@echo off
cd C:\xampp\htdocs\_QuickTest_TFG
call phpunit --coverage-html ./test_PHPUnit/coverageResults test_PHPUnit

echo.
echo   ------------------------------------TEST COBERTURA -----------------------------------
echo   VER RESULTADOS: Copiar esta ruta en el navegador de archivos.
echo.
echo    C:/xampp/htdocs/_QuickTest_TFG/test_PHPUnit/coverageResults/index.html
echo. 
 