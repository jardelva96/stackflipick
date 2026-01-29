package com.stackflipick.versionmanager.model;

public class MavenVersion extends Version {
    
    public MavenVersion(String name, String path, String version, boolean isCurrent) {
        super(name, path, version, isCurrent);
    }
    
    @Override
    public String getTechnologyType() {
        return "MAVEN";
    }
    
    @Override
    public String getIcon() {
        return "📦";
    }
}

