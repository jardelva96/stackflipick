package com.stackflipick.versionmanager.service;

import com.stackflipick.versionmanager.config.ConfigManager;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Service para configuração de variáveis de ambiente PATH.
 * Centraliza toda lógica de manipulação de PATH do Windows.
 */
public class PathConfigService {
    
    private final ConfigManager configManager;
    
    public PathConfigService(ConfigManager configManager) {
        this.configManager = configManager;
    }
    
    public void configureJavaEnvironment(String javaPath) {
        StringBuilder command = new StringBuilder("powershell -Command \"");
        
        // 1. Garantir que shims estão no PATH (primeiro lugar)
        command.append(
            "$shimsPath = \"$env:USERPROFILE\\.stackflipick\\shims\"; " +
            "$userPath = [System.Environment]::GetEnvironmentVariable('Path', 'User'); " +
            "if ($userPath -notlike \"*$shimsPath*\") { " +
            "    $userPath = \"$shimsPath;$userPath\"; " +
            "}; ");
        
        // 2. Configurar JAVA_HOME
        command.append(String.format(
            "[System.Environment]::SetEnvironmentVariable('JAVA_HOME', '%s', 'User'); ",
            javaPath));
        
        // 3. Limpar outros caminhos Java do PATH
        command.append(
            "$cleanPath = ($userPath -split ';' | Where-Object { " +
            "$_ -and $_ -notmatch '(?i)Eclipse Adoptium.*?bin|Java.*?bin' " +
            "} | Select-Object -Unique) -join ';'; " +
            "[System.Environment]::SetEnvironmentVariable('Path', $cleanPath, 'User'); ");
        
        command.append("\"");
        
        executeCommand(command.toString());
    }
    
    public void configureNodeEnvironment(String nodePath) {
        StringBuilder command = new StringBuilder("powershell -Command \"");
        
        command.append(String.format(
            "$userPath = [System.Environment]::GetEnvironmentVariable('Path', 'User'); " +
            "$cleanPath = ($userPath -split ';' | Where-Object { $_ -notmatch '(?i)nodejs' }) -join ';'; " +
            "$newPath = '%s;' + $cleanPath; " +
            "[System.Environment]::SetEnvironmentVariable('Path', $newPath, 'User'); ",
            nodePath.replace("\\", "\\\\")));
        
        command.append("\"");
        executeCommand(command.toString());
    }
    
    public void configurePythonEnvironment(String pythonPath) {
        StringBuilder command = new StringBuilder("powershell -Command \"");
        
        command.append(String.format(
            "[System.Environment]::SetEnvironmentVariable('PYTHON_HOME', '%s', 'User'); ",
            pythonPath));
        
        command.append(String.format(
            "$userPath = [System.Environment]::GetEnvironmentVariable('Path', 'User'); " +
            "$cleanPath = ($userPath -split ';' | Where-Object { $_ -notmatch '(?i)Python' }) -join ';'; " +
            "$newPath = '%s;%s\\Scripts;' + $cleanPath; " +
            "[System.Environment]::SetEnvironmentVariable('Path', $newPath, 'User'); ",
            pythonPath.replace("\\", "\\\\"),
            pythonPath.replace("\\", "\\\\")));
        
        command.append("\"");
        executeCommand(command.toString());
    }
    
    public void configureDotNetEnvironment(String dotnetPath) {
        // .NET geralmente é fixo em C:\Program Files\dotnet
        StringBuilder command = new StringBuilder("powershell -Command \"");
        command.append("[System.Environment]::SetEnvironmentVariable('DOTNET_ROOT', 'C:\\Program Files\\dotnet', 'User'); \"");
        executeCommand(command.toString());
    }
    
    public void configureMavenEnvironment(String mavenPath) {
        StringBuilder command = new StringBuilder("powershell -Command \"");
        
        command.append(String.format(
            "[System.Environment]::SetEnvironmentVariable('MAVEN_HOME', '%s', 'User'); ",
            mavenPath));
        
        command.append(String.format(
            "[System.Environment]::SetEnvironmentVariable('M2_HOME', '%s', 'User'); ",
            mavenPath));
        
        command.append(String.format(
            "$userPath = [System.Environment]::GetEnvironmentVariable('Path', 'User'); " +
            "$cleanPath = ($userPath -split ';' | Where-Object { $_ -notmatch '(?i)maven' }) -join ';'; " +
            "$newPath = '%s\\bin;' + $cleanPath; " +
            "[System.Environment]::SetEnvironmentVariable('Path', $newPath, 'User'); ",
            mavenPath.replace("\\", "\\\\")));
        
        command.append("\"");
        executeCommand(command.toString());
    }
    
    private void executeCommand(String command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
            
            // Ler output para debugging
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            System.err.println("Erro ao executar comando: " + e.getMessage());
        }
    }
}

