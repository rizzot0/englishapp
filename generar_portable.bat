@echo off
echo =========================================
echo  Generando ejecutable portable EnglishApp
echo =========================================

REM Ruta al SDK de JavaFX (ajusta si cambia en tu sistema)
set JAVAFX_PATH=C:\Users\Basti\javafx-sdk-24.0.1\lib

REM Nombre de salida del ejecutable y carpeta
set APP_NAME=EnglishAppPortable

REM Ejecutar jpackage para generar carpeta portable
jpackage ^
  --name %APP_NAME% ^
  --input target ^
  --main-jar englishapp1-1.0-SNAPSHOT.jar ^
  --main-class com.englishapp.App ^
  --type app-image ^
  --java-options "--module-path %JAVAFX_PATH% --add-modules javafx.controls,javafx.fxml"

echo.
echo ✅ Ejecutable generado en carpeta: %APP_NAME%
echo.
pause
