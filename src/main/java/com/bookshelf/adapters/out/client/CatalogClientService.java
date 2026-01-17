package com.bookshelf.adapters.out.client;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatalogClientService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public String buscarTituloLivroNoCatalog(Long bookId) {
        System.out.println("📤 BOOKSHELF: Enviando pedido para Catalog... ID: " + bookId);

        try {
            // Envia e AGUARDA resposta (string)
            Object respostaObj = rabbitTemplate.convertSendAndReceive(
                    "", // Exchange padrão
                    "pedidos.catalog", // Nome da fila
                    bookId.toString() // ID como string
            );

            if (respostaObj == null) {
                System.out.println("⚠️  BOOKSHELF: Catalog não respondeu (timeout)");
                return "Título não disponível";
            }

            String resposta = respostaObj.toString();
            System.out.println("✅ BOOKSHELF: Resposta recebida: " + resposta);

            return resposta;

        } catch (Exception e) {
            System.out.println("❌ BOOKSHELF: Erro na comunicação: " + e.getMessage());
            return "Erro ao buscar no Catalog";
        }
    }
}