package com.stackflipick.versionmanager.model;

public class DotNetVersion extends Version {
    
    public DotNetVersion(String name, String path, String version, boolean isCurrent) {
        super(name, path, version, isCurrent);
    }
    
    @Override
    public String getTechnologyType() {
        return "DOTNET";
    }
    
    @Override
    public String getIcon() {
        return "🔷";
    }
}

