@echo off
echo ==========================================
echo   Gerenciador de Versoes Desktop
echo ==========================================
echo.

set JAVA_17=C:\Program Files\Eclipse Adoptium\jdk-17.0.17.10-hotspot\bin\java.exe

if exist "%JAVA_17%" (
    echo Usando Java 17 para executar...
    "%JAVA_17%" --module-path "C:\Program Files\Java\javafx-sdk-21.0.1\lib" --add-modules javafx.controls,javafx.fxml -jar target\version-manager-1.0.0.jar
) else (
    echo Java 17 nao encontrado!
    echo Por favor, instale o Java 17 ou ajuste o caminho no script.
    pause
)
