package com.victorvilar.marketplace.fullstack.dtos;

import java.util.UUID;

public record UserDTO(UUID id, String name, String email, String phoneNumber) {
}
