package com.stackflipick.versionmanager.detector;

import com.stackflipick.versionmanager.model.Version;
import java.util.List;

/**
 * Strategy pattern - Interface para detecção de versões.
 * Cada tecnologia implementa sua própria estratégia de detecção.
 */
public interface VersionDetector<T extends Version> {
    
    /**
     * Detecta todas as versões instaladas da tecnologia.
     * @return Lista de versões encontradas
     */
    List<T> detectVersions();
    
    /**
     * Obtém a versão atual configurada.
     * @return Versão atual ou null se não configurada
     */
    T getCurrentVersion();
    
    /**
     * Retorna o tipo de tecnologia que este detector gerencia.
     */
    String getTechnologyType();
    
    /**
     * Valida se uma versão existe no sistema.
     */
    boolean validateVersion(String versionPath);
}

