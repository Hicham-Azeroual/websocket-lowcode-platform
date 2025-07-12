package com.hicham.backend.model;

/**
 * Énumération représentant les différents statuts d'une opération
 */
public enum OperationStatus {
    STARTED("Opération démarrée"),
    PROCESSING("Traitement en cours..."),
    DONE("Opération terminée");

    private final String defaultMessage;

    OperationStatus(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}