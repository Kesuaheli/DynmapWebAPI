package de.kesuaheli.dynmapwebapi.webserver;

import io.javalin.security.RouteRole;

import java.util.List;

public enum Role implements RouteRole {
    PUBLIC("public"),
    READ("read"),
    WRITE("write");

    final String id;

    Role(String id) {
        this.id = id;
    }

    public static Role fromString(String string) {
        return switch (string) {
            case "read" -> Role.READ;
            case "write" -> Role.WRITE;
            default -> Role.PUBLIC;
        };
    }

    public static List<Role> fromString(List<String> strings) {
        return strings.stream().map(Role::fromString).toList();
    }

    public String toString() {
        return this.id;
    }
    public static String roleToString(Role role) {
        return role.id;
    }
    public static List<String> toString(List<Role> roles) {
        return roles.stream().map(Role::roleToString).toList();
    }
}