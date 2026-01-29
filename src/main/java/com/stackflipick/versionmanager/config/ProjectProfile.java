package com.stackflipick.versionmanager.config;

/**
 * Data class - Representa um perfil de projeto.
 */
public class ProjectProfile {
    public String projectName;
    public String projectPath;
    public String techType;
    public String versionName;
    public String versionPath;
    
    public ProjectProfile() {
    }
    
    public ProjectProfile(String projectName, String projectPath, String techType, 
                         String versionName, String versionPath) {
        this.projectName = projectName;
        this.projectPath = projectPath;
        this.techType = techType;
        this.versionName = versionName;
        this.versionPath = versionPath;
    }
}

