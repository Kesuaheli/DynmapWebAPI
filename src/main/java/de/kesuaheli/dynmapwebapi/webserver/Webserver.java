package de.kesuaheli.dynmapwebapi.webserver;

import de.kesuaheli.dynmapwebapi.DynmapWebAPI;
import io.javalin.Javalin;

import java.util.logging.Level;

public class Webserver {

    private final DynmapWebAPI plugin;
    private Javalin server;

    @SuppressWarnings("unused")
    public Webserver(DynmapWebAPI plugin) {
        this.plugin = plugin;
        int port = this.plugin.getConfig().getInt("web.port");
        this.start(port);
    }

    private void start(int port) {
        if (this.plugin == null) {
            throw new IllegalStateException("Plugin is not set");
        }

        if (this.server != null) {
            this.plugin.LOG.log(Level.SEVERE, "Webserver already running");
            return;
        }

        Handler handler = new Handler(plugin);

        this.plugin.LOG.info("Starting Javalin webserver...");
        this.server = Javalin.create(c -> {
            c.showJavalinBanner = false;
            c.accessManager(Auth::accessManager);
        })
                .get("/markers", handler::getMarkersDefaultSet, Role.PUBLIC)
                .get("/markers/{set}", handler::getMarkers, Role.PUBLIC)
                .get("/marker/{marker}", handler::getMarkerDefaultSet, Role.READ)
                .get("/marker/{set}/{marker}", handler::getMarker,Role.READ)
                .get("/markersets", handler::getMarkerSets, Role.PUBLIC)
                .get("/markerset/{set}", handler::getMarkerSet, Role.READ)

                .post("/marker", handler::postMarkerDefaultSet, Role.WRITE)
                .post("/marker/{set}", handler::postMarker, Role.WRITE)
                .post("/markerset", handler::postMarkerSet, Role.WRITE)

                .delete("/marker/{marker}", handler::deleteMarkerDefaultSet, Role.WRITE)
                .delete("/marker/{set}/{marker}", handler::deleteMarker, Role.WRITE)
                .delete("/markerset/{set}/", handler::deleteMarkerSet, Role.WRITE)

                .start(port);

        this.plugin.LOG.info("Started webserver on port " + port + " !");
    }

    public void stop() {
        if (this.server == null) {
            return;
        }
        this.server.stop();
        this.server = null;
    }

    public void restart() {
        int port = this.plugin.getConfig().getInt("web.port");
        this.stop();
        this.start(port);
    }
}
