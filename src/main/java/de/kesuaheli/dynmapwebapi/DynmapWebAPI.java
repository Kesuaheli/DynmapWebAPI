package de.kesuaheli.dynmapwebapi;

import de.kesuaheli.dynmapwebapi.commands.DynmapWebAPICommand;
import de.kesuaheli.dynmapwebapi.commands.DynmapWebAPITab;
import de.kesuaheli.dynmapwebapi.webserver.Auth;
import de.kesuaheli.dynmapwebapi.webserver.Webserver;
import org.bukkit.plugin.java.JavaPlugin;
import org.dynmap.DynmapCommonAPIListener;

import java.util.Objects;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public final class DynmapWebAPI extends JavaPlugin {

    public Logger LOG;
    public DMapApi dMapApi;
    public Webserver web;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.LOG = this.getLogger();

        Objects.requireNonNull(this.getCommand("dynmapwebapi")).setExecutor(new DynmapWebAPICommand(this));
        Objects.requireNonNull(this.getCommand("dynmapwebapi")).setTabCompleter(new DynmapWebAPITab());

        this.dMapApi = new DMapApi(this);
        DynmapCommonAPIListener.register(dMapApi);
        this.LOG.info("Hooked into Dynmap");

        Auth.reloadUsers(this);
        this.web = new Webserver(this);
    }

    @Override
    public void onDisable() {
        this.web.stop();
    }
}
