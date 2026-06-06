package com.victorvilar.marketplace.fullstack.dtos;

import jakarta.validation.constraints.NotNull;

public record RegisterDTO(@NotNull String name, @NotNull String email, @NotNull String password) {
}
