package de.kesuaheli.dynmapwebapi.commands;

import de.kesuaheli.dynmapwebapi.DynmapWebAPI;
import de.kesuaheli.dynmapwebapi.webserver.Auth;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class DynmapWebAPICommand implements CommandExecutor {
    private final DynmapWebAPI plugin;

    public DynmapWebAPICommand(DynmapWebAPI plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            return false;
        }
        List<String> params = Arrays.stream(args).skip(1).toList();

        return switch (args[0]) {
            case "config" -> handleConfigCommand(commandSender, params);
            case "webserver" -> handleWebserverCommand(commandSender, params);
            default -> false;
        };
    }

    private boolean handleConfigCommand(@NotNull CommandSender commandSender, @NotNull List<String> params) {
        if (params.size() == 0) {
            return false;
        }

        //noinspection SwitchStatementWithTooFewBranches
        switch (params.get(0)) {
            case "reload":
                this.plugin.reloadConfig();
                if (!Auth.reloadUsers(this.plugin)) {
                    commandSender.sendMessage("Failed to reload API users! Please check the config!");
                } else {
                    commandSender.sendMessage("Successfully reload config!");
                }
                break;
            default:
                return false;
        }
        return true;
    }

    private boolean handleWebserverCommand(@NotNull CommandSender commandSender, @NotNull List<String> params) {
        if (params.size() == 0) {
            return false;
        }

        //noinspection SwitchStatementWithTooFewBranches
        switch (params.get(0)) {
            case "restart":
                this.plugin.reloadConfig();
                this.plugin.web.restart();
                commandSender.sendMessage("Restarted Webserver!");
                break;
            default:
                return false;
        }
        return true;
    }
}
