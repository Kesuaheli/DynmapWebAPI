package de.kesuaheli.dynmapwebapi.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DynmapWebAPITab implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        List<String> arguments = new ArrayList<>();

        if (args.length == 1) {
            arguments.add("config");
            arguments.add("webserver");
        } if (args.length == 2) {
            switch (args[0]) {
                case "config" -> arguments.add("reload");
                case "webserver" -> arguments.add("restart");
            }
        }

        return arguments.stream()
                .filter(a -> a.toLowerCase().startsWith(args[args.length-1].toLowerCase()))
                .sorted()
                .collect(Collectors.toList());
    }
}
