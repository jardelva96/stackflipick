package br.gov.sc.detran.versionmanager.detector;

import br.gov.sc.detran.versionmanager.model.Version;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Template Method pattern - Classe abstrata base para detecção de versões.
 * Define o esqueleto do algoritmo de detecção.
 */
public abstract class AbstractVersionDetector<T extends Version> implements VersionDetector<T> {
    
    protected List<T> cachedVersions = null;
    protected boolean cacheValid = false;
    
    @Override
    public List<T> detectVersions() {
        if (cacheValid && cachedVersions != null) {
            return new ArrayList<>(cachedVersions);
        }
        
        cachedVersions = new ArrayList<>();
        String[] basePaths = getBasePaths();
        
        for (String basePath : basePaths) {
            File baseDir = new File(basePath);
            if (!baseDir.exists() || !baseDir.isDirectory()) {
                continue;
            }
            
            File[] dirs = baseDir.listFiles(File::isDirectory);
            if (dirs == null) {
                continue;
            }
            
            for (File dir : dirs) {
                if (isValidInstallation(dir)) {
                    T version = createVersionInstance(dir);
                    if (version != null) {
                        cachedVersions.add(version);
                    }
                }
            }
        }
        
        cacheValid = true;
        return new ArrayList<>(cachedVersions);
    }
    
    @Override
    public boolean validateVersion(String versionPath) {
        return versionPath != null && !versionPath.isEmpty() && new File(versionPath).exists();
    }
    
    /**
     * Invalida o cache forçando nova detecção.
     */
    public void invalidateCache() {
        cacheValid = false;
        cachedVersions = null;
    }
    
    /**
     * Retorna os caminhos base onde procurar instalações.
     */
    protected abstract String[] getBasePaths();
    
    /**
     * Verifica se um diretório contém uma instalação válida.
     */
    protected abstract boolean isValidInstallation(File directory);
    
    /**
     * Cria uma instância de Version a partir de um diretório.
     */
    protected abstract T createVersionInstance(File directory);
    
    /**
     * Extrai versão do nome do diretório.
     */
    protected String extractVersionFromDirName(String dirName) {
        java.util.regex.Pattern p = java.util.regex.Pattern.compile("(\\d+\\.\\d+\\.\\d+)");
        java.util.regex.Matcher m = p.matcher(dirName);
        if (m.find()) {
            return m.group(1);
        }
        p = java.util.regex.Pattern.compile("(\\d+)");
        m = p.matcher(dirName);
        if (m.find()) {
            return m.group(1);
        }
        return "Desconhecida";
    }
}
