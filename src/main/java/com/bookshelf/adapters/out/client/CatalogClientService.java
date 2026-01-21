package com.bookshelf.adapters.out.client;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Client service responsible for communicating with the Catalog service.
 *
 * This class uses RabbitMQ to request book information and retrieve
 * the book title based on its identifier.
 *
 * Communication follows a request-reply pattern using messaging,
 * providing loose coupling between services.
 */
@Service
public class CatalogClientService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * Requests the book title from the Catalog service using the book ID.
     *
     * @param bookId identifier of the book
     * @return book title or a fallback message if unavailable
     */
    public String buscarTituloLivroNoCatalog(Long bookId) {
        System.out.println("📤 BOOKSHELF: Enviando pedido para Catalog... ID: " + bookId);

        try {
            Object responseObj = rabbitTemplate.convertSendAndReceive(
                    "",
                    "pedidos.catalog",
                    bookId.toString()
            );

            if (responseObj == null) {
                System.out.println("⚠️  BOOKSHELF: Catalog não respondeu (timeout)");
                return "Título não disponível";
            }

            String response = responseObj.toString();
            System.out.println("✅ BOOKSHELF: Resposta recebida: " + response);

            return response;

        } catch (Exception e) {
            System.out.println("❌ BOOKSHELF: Erro na comunicação: " + e.getMessage());
            return "Erro ao buscar no Catalog";
        }
    }
}