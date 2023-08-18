package dev.hugofaria.metaapi.controller.request;

import dev.hugofaria.metaapi.domain.model.enums.UserRole;

public record RegisterRequestDTO(String login, String password, UserRole role) {
}