package entities;

import java.util.UUID;

public record Client(String id, String nom, String email) {

    public Client {
        if (id == null || id.isBlank()) {
            id = UUID.randomUUID().toString();
        }
    }

    public Client(String nom, String email){
        this(UUID.randomUUID().toString(), nom, email);
    }
}