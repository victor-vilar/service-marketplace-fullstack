package com.victorvilar.marketplace.fullstack.dtos;

import jakarta.validation.constraints.NotNull;

public record LoginDTO(@NotNull String username, @NotNull String password) {
}
