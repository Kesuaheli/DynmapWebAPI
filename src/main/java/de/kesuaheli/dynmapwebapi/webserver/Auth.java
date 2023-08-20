package de.kesuaheli.dynmapwebapi.webserver;

import de.kesuaheli.dynmapwebapi.DynmapWebAPI;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.Header;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.security.RouteRole;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Auth {

    public record Pair(String user, String password) {}
    @SuppressWarnings("FieldMayBeFinal")
    private static HashMap<Pair, List<Role>> userRoles = new HashMap<>();


    public static boolean reloadUsers(@NotNull DynmapWebAPI plugin) {
        ConfigurationSection users = plugin.getConfig().getConfigurationSection("users");
        if (users == null) {
            plugin.LOG.severe("Could not read \"users\" from config. Please check your config!");
            return false;
        }

        userRoles.clear();
        users.getKeys(false).forEach(name -> userRoles.put(
            new Pair(name, users.getString(name+".password", "PleaseChange")),
            Role.fromString(users.getStringList(name+".roles"))
        ));

        plugin.LOG.info("Updated user. There is/are " + userRoles.size() + " user(s) now: " + userRoles.keySet().stream().map(u -> u.user).toList());
        return true;
    }

    public static void accessManager(@NotNull Handler handler, @NotNull Context ctx, @NotNull Set<? extends RouteRole> permittedRoles) throws Exception {
        if (permittedRoles.contains(Role.PUBLIC)) {
            handler.handle(ctx);
            return;
        }
        if (getUserRoles(ctx).stream().anyMatch(permittedRoles::contains)) {
            handler.handle(ctx);
            return;
        }
        ctx.header(Header.WWW_AUTHENTICATE, "Basic");
        throw new UnauthorizedResponse();
    }

    private static List<Role> getUserRoles(Context ctx) {
        return Optional.ofNullable(ctx.basicAuthCredentials())
                .map(credentials -> userRoles.getOrDefault(new Pair(credentials.getUsername(), credentials.getPassword()), List.of()))
                .orElse(List.of());
    }
}

record ConfigUser(@NotNull String username, @NotNull String password, @NotNull List<Role> roles) {
}