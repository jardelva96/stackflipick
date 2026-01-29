package br.gov.sc.detran.versionmanager.model;

/**
 * Classe abstrata base para todas as versões de tecnologias.
 * Segue o princípio Open/Closed e Template Method pattern.
 */
public abstract class Version {
    protected String name;
    protected String path;
    protected String version;
    protected boolean isCurrent;
    
    public Version(String name, String path, String version, boolean isCurrent) {
        this.name = name;
        this.path = path;
        this.version = version;
        this.isCurrent = isCurrent;
    }
    
    public String getName() {
        return name;
    }
    
    public String getPath() {
        return path;
    }
    
    public String getVersion() {
        return version;
    }
    
    public boolean isCurrent() {
        return isCurrent;
    }
    
    public void setCurrent(boolean current) {
        this.isCurrent = current;
    }
    
    /**
     * Template method para obter o tipo de tecnologia.
     * Cada subclasse implementa seu próprio tipo.
     */
    public abstract String getTechnologyType();
    
    /**
     * Template method para obter o ícone da tecnologia.
     */
    public abstract String getIcon();
    
    @Override
    public String toString() {
        return name + " (" + version + ")";
    }
}
