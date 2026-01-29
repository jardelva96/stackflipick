package br.gov.sc.detran.versionmanager.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Singleton pattern - Gerencia configurações globais da aplicação.
 * Responsável por ler/escrever arquivos de configuração.
 */
public class ConfigManager {
    
    private static ConfigManager instance;
    private final Path configDir;
    private final Path globalJavaConfig;
    private final Path globalNodeConfig;
    private final Path globalPythonConfig;
    private final Path shimsDir;
    
    private ConfigManager() {
        String userHome = System.getProperty("user.home");
        this.configDir = Paths.get(userHome, ".stackflipick");
        this.globalJavaConfig = configDir.resolve("global-java.txt");
        this.globalNodeConfig = configDir.resolve("global-node.txt");
        this.globalPythonConfig = configDir.resolve("global-python.txt");
        this.shimsDir = configDir.resolve("shims");
        
        ensureConfigDirExists();
    }
    
    public static synchronized ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }
    
    private void ensureConfigDirExists() {
        try {
            if (!Files.exists(configDir)) {
                Files.createDirectories(configDir);
            }
            if (!Files.exists(shimsDir)) {
                Files.createDirectories(shimsDir);
            }
        } catch (IOException e) {
            System.err.println("Erro ao criar diretório de configuração: " + e.getMessage());
        }
    }
    
    // Java Configuration
    public String getGlobalJavaPath() {
        return readConfigFile(globalJavaConfig);
    }
    
    public void setGlobalJavaPath(String path) {
        writeConfigFile(globalJavaConfig, path);
    }
    
    // Node Configuration
    public String getGlobalNodePath() {
        return readConfigFile(globalNodeConfig);
    }
    
    public void setGlobalNodePath(String path) {
        writeConfigFile(globalNodeConfig, path);
    }
    
    // Python Configuration
    public String getGlobalPythonPath() {
        return readConfigFile(globalPythonConfig);
    }
    
    public void setGlobalPythonPath(String path) {
        writeConfigFile(globalPythonConfig, path);
    }
    
    // Shims
    public Path getShimsDir() {
        return shimsDir;
    }
    
    public boolean areShimsInstalled() {
        File javaShim = shimsDir.resolve("java.bat").toFile();
        return javaShim.exists();
    }
    
    // Helper methods
    private String readConfigFile(Path configFile) {
        try {
            if (Files.exists(configFile)) {
                return Files.readString(configFile).trim();
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler configuração: " + e.getMessage());
        }
        return null;
    }
    
    private void writeConfigFile(Path configFile, String content) {
        try {
            Files.writeString(configFile, content);
        } catch (IOException e) {
            System.err.println("Erro ao escrever configuração: " + e.getMessage());
        }
    }
    
    public Path getConfigDir() {
        return configDir;
    }
}
