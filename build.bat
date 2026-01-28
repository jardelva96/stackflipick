@echo off
echo ==========================================
echo   Compilando Gerenciador de Versoes
echo ==========================================
echo.

cd /d "%~dp0"

if exist "C:\Program Files\Eclipse Adoptium\jdk-17.0.17.10-hotspot\bin\mvn.cmd" (
    "C:\Program Files\Eclipse Adoptium\jdk-17.0.17.10-hotspot\bin\mvn.cmd" clean package
) else (
    call mvn clean package
)

echo.
echo ==========================================
echo   Compilacao concluida!
echo ==========================================
echo.
echo Execute run.bat para iniciar o programa
pause
