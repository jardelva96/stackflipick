package com.stackflipick.versionmanager.model;

public class JavaVersion extends Version {
    
    public JavaVersion(String name, String path, String version, boolean isCurrent) {
        super(name, path, version, isCurrent);
    }
    
    @Override
    public String getTechnologyType() {
        return "JAVA";
    }
    
    @Override
    public String getIcon() {
        return "☕";
    }
}

