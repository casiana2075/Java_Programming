package com.example.project;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class ApiService {
    private final WebClient webClient;

    public ApiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8082/api").build();
    }

    public Flux<Book> getAllBooks() {
        return this.webClient.get().uri("/books")
                .retrieve()
                .bodyToFlux(Book.class);
    }

    public Flux<Author> getAllAuthors() {
        return this.webClient.get().uri("/authors")
                .retrieve()
                .bodyToFlux(Author.class);
    }

    public Mono<Book> addBook(Book book) {
        return this.webClient.post().uri("/books")
                .bodyValue(book)
                .retrieve()
                .bodyToMono(Book.class);
    }

    public Mono<Void> updateBookTitle(int id, String newTitle) {
        return this.webClient.put().uri("/books/{id}", id)
                .bodyValue(Map.of("title", newTitle))
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<Void> deleteBook(int id) {
        return this.webClient.delete().uri("/books/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }
}