package com.stackflipick.versionmanager.model;

public class PythonVersion extends Version {
    
    public PythonVersion(String name, String path, String version, boolean isCurrent) {
        super(name, path, version, isCurrent);
    }
    
    @Override
    public String getTechnologyType() {
        return "PYTHON";
    }
    
    @Override
    public String getIcon() {
        return "🐍";
    }
}

