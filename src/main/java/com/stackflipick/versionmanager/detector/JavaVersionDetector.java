package com.stackflipick.versionmanager.detector;

import com.stackflipick.versionmanager.config.ConfigManager;
import com.stackflipick.versionmanager.model.JavaVersion;
import java.io.File;

/**
 * Detector concreto para versões Java.
 * Implementa Strategy pattern para detecção específica de Java.
 */
public class JavaVersionDetector extends AbstractVersionDetector<JavaVersion> {
    
    private final ConfigManager configManager;
    
    public JavaVersionDetector(ConfigManager configManager) {
        this.configManager = configManager;
    }
    
    @Override
    protected String[] getBasePaths() {
        return new String[]{
            "C:\\Program Files\\Eclipse Adoptium",
            "C:\\Program Files\\Java",
            "C:\\Program Files\\OpenJDK"
        };
    }
    
    @Override
    protected boolean isValidInstallation(File directory) {
        File javaExe = new File(directory, "bin\\java.exe");
        return javaExe.exists();
    }
    
    @Override
    protected JavaVersion createVersionInstance(File directory) {
        String version = extractVersionFromDirName(directory.getName());
        String currentPath = configManager.getGlobalJavaPath();
        boolean isCurrent = directory.getAbsolutePath().equalsIgnoreCase(currentPath);
        
        return new JavaVersion(
            directory.getName(),
            directory.getAbsolutePath(),
            version,
            isCurrent
        );
    }
    
    @Override
    public JavaVersion getCurrentVersion() {
        String currentPath = configManager.getGlobalJavaPath();
        if (currentPath == null || currentPath.isEmpty()) {
            return null;
        }
        
        for (JavaVersion version : detectVersions()) {
            if (version.getPath().equalsIgnoreCase(currentPath)) {
                return version;
            }
        }
        return null;
    }
    
    @Override
    public String getTechnologyType() {
        return "JAVA";
    }
}

