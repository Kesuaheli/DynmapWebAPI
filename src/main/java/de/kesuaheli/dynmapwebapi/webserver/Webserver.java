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
        this.server = Javalin.create(c -> c.showJavalinBanner = false)
                .get("/markers", handler::getMarkersDefaultSet)
                .get("/markers/{set}", handler::getMarkers)
                .get("/marker/{marker}", handler::getMarkerDefaultSet)
                .get("/marker/{set}/{marker}", handler::getMarker)
                .get("/markersets", handler::getMarkerSets)
                .get("/markerset/{set}", handler::getMarkerSet)

                .post("/marker", handler::postMarkerDefaultSet)
                .post("/marker/{set}", handler::postMarker)
                .post("/markerset", handler::postMarkerSet)

                .delete("/marker/{marker}", handler::deleteMarkerDefaultSet)
                .delete("/marker/{set}/{marker}", handler::deleteMarker)
                .delete("/markerset/{set}/", handler::deleteMarkerSet)

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
