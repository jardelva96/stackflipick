package br.gov.sc.detran.versionmanager.model;

public class NodeVersion extends Version {
    
    public NodeVersion(String name, String path, String version, boolean isCurrent) {
        super(name, path, version, isCurrent);
    }
    
    @Override
    public String getTechnologyType() {
        return "NODE";
    }
    
    @Override
    public String getIcon() {
        return "🟢";
    }
}
