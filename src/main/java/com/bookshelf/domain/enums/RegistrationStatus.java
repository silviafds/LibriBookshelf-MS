package com.bookshelf.domain.enums;

public enum RegistrationStatus {
    SUCCESS("Resenha registrada com sucesso"),
    MISSING_DATA("Dados obrigatórios faltando"),
    VALIDATION_ERROR("Erro de validação nos dados"),
    DATABASE_ERROR("Erro ao salvar no banco de dados"),
    DELETE_ERROR("Erro ao deletar review"),
    UNKNOWN_ERROR("Erro desconhecido OU Erro interno");

    private final String defaultMessage;

    RegistrationStatus(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}
