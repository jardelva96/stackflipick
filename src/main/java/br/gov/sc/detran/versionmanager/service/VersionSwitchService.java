package br.gov.sc.detran.versionmanager.service;

import br.gov.sc.detran.versionmanager.config.ConfigManager;
import br.gov.sc.detran.versionmanager.model.*;

/**
 * Service layer - Responsável por aplicar mudanças de versão.
 * Separa lógica de negócio da UI.
 */
public class VersionSwitchService {
    
    private final ConfigManager configManager;
    private final PathConfigService pathConfigService;
    
    public VersionSwitchService(ConfigManager configManager, PathConfigService pathConfigService) {
        this.configManager = configManager;
        this.pathConfigService = pathConfigService;
    }
    
    /**
     * Aplica mudança de versão Java.
     * @return true se sucesso, false caso contrário
     */
    public boolean switchJavaVersion(JavaVersion version) {
        if (version == null) {
            return false;
        }
        
        try {
            // 1. Salva configuração global para shims
            configManager.setGlobalJavaPath(version.getPath());
            
            // 2. Configura JAVA_HOME e PATH do usuário
            pathConfigService.configureJavaEnvironment(version.getPath());
            
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao trocar versão Java: " + e.getMessage());
            return false;
        }
    }
    
    public boolean switchNodeVersion(NodeVersion version) {
        if (version == null) {
            return false;
        }
        
        try {
            configManager.setGlobalNodePath(version.getPath());
            pathConfigService.configureNodeEnvironment(version.getPath());
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao trocar versão Node: " + e.getMessage());
            return false;
        }
    }
    
    public boolean switchPythonVersion(PythonVersion version) {
        if (version == null) {
            return false;
        }
        
        try {
            configManager.setGlobalPythonPath(version.getPath());
            pathConfigService.configurePythonEnvironment(version.getPath());
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao trocar versão Python: " + e.getMessage());
            return false;
        }
    }
    
    public boolean switchDotNetVersion(DotNetVersion version) {
        if (version == null) {
            return false;
        }
        
        try {
            pathConfigService.configureDotNetEnvironment(version.getPath());
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao trocar versão .NET: " + e.getMessage());
            return false;
        }
    }
    
    public boolean switchMavenVersion(MavenVersion version) {
        if (version == null) {
            return false;
        }
        
        try {
            pathConfigService.configureMavenEnvironment(version.getPath());
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao trocar versão Maven: " + e.getMessage());
            return false;
        }
    }
}
