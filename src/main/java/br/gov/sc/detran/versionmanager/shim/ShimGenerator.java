package br.gov.sc.detran.versionmanager.shim;

import br.gov.sc.detran.versionmanager.config.ConfigManager;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Factory pattern - Gera e instala shims para interceptar comandos.
 */
public class ShimGenerator {
    
    private final ConfigManager configManager;
    
    public ShimGenerator(ConfigManager configManager) {
        this.configManager = configManager;
    }
    
    /**
     * Instala todos os shims necessários.
     */
    public void installAllShims() {
        try {
            Path shimsDir = configManager.getShimsDir();
            
            // Java shims
            installJavaShims(shimsDir);
            
            // TODO: Node, Python shims
            
        } catch (IOException e) {
            System.err.println("Erro ao instalar shims: " + e.getMessage());
        }
    }
    
    private void installJavaShims(Path shimsDir) throws IOException {
        // java.bat
        String javaBat = generateJavaShim();
        Files.writeString(shimsDir.resolve("java.bat"), javaBat);
        
        // javac.bat
        String javacBat = javaBat.replace("java.exe", "javac.exe");
        Files.writeString(shimsDir.resolve("javac.bat"), javacBat);
        
        // javaw.bat
        String javawBat = javaBat.replace("java.exe", "javaw.exe");
        Files.writeString(shimsDir.resolve("javaw.bat"), javawBat);
    }
    
    private String generateJavaShim() {
        return "@echo off\n" +
               "setlocal enabledelayedexpansion\n" +
               "\n" +
               "REM StackFlipick Java Shim - Intercepta java.exe\n" +
               "REM 1. Procura .java-version local\n" +
               "REM 2. Usa config global\n" +
               "REM 3. Fallback para JAVA_HOME\n" +
               "\n" +
               "set \"config_dir=%USERPROFILE%\\.stackflipick\"\n" +
               "set \"global_config=%config_dir%\\global-java.txt\"\n" +
               "set \"java_path=\"\n" +
               "\n" +
               "REM 1. Procura .java-version\n" +
               "set \"current_dir=%CD%\"\n" +
               ":search\n" +
               "if exist \"%current_dir%\\.java-version\" (\n" +
               "    set /p version=<\"%current_dir%\\.java-version\"\n" +
               "    call :find_java_by_version !version!\n" +
               "    if defined java_path goto run_java\n" +
               ")\n" +
               "for %%I in (\"%current_dir%\\..\") do set \"parent=%%~fI\"\n" +
               "if \"%current_dir%\"==\"%parent%\" goto check_global\n" +
               "set \"current_dir=%parent%\"\n" +
               "goto search\n" +
               "\n" +
               ":check_global\n" +
               "REM 2. Config global\n" +
               "if exist \"%global_config%\" (\n" +
               "    set /p java_path=<\"%global_config%\"\n" +
               "    if exist \"!java_path!\\bin\\java.exe\" goto run_java\n" +
               ")\n" +
               "\n" +
               "REM 3. Fallback JAVA_HOME\n" +
               "if defined JAVA_HOME (\n" +
               "    set \"java_path=%JAVA_HOME%\"\n" +
               "    goto run_java\n" +
               ")\n" +
               "\n" +
               "echo ERRO: Java nao encontrado. Configure usando StackFlipick.\n" +
               "exit /b 1\n" +
               "\n" +
               ":run_java\n" +
               "\"!java_path!\\bin\\java.exe\" %*\n" +
               "exit /b !ERRORLEVEL!\n" +
               "\n" +
               ":find_java_by_version\n" +
               "set \"ver=%~1\"\n" +
               "for %%D in (\n" +
               "    \"C:\\Program Files\\Eclipse Adoptium\\jdk-%ver%*\"\n" +
               "    \"C:\\Program Files\\Eclipse Adoptium\\jre-%ver%*\"\n" +
               "    \"C:\\Program Files\\Java\\jdk-%ver%*\"\n" +
               ") do (\n" +
               "    if exist %%D\\bin\\java.exe (\n" +
               "        set \"java_path=%%~D\"\n" +
               "        exit /b 0\n" +
               "    )\n" +
               ")\n" +
               "exit /b 1\n";
    }
    
    public boolean areShimsInstalled() {
        return configManager.areShimsInstalled();
    }
}
