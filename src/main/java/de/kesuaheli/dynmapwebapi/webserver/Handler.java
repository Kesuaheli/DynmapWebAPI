package de.kesuaheli.dynmapwebapi.webserver;

import de.kesuaheli.dynmapwebapi.DynmapWebAPI;
import de.kesuaheli.dynmapwebapi.Marker;
import de.kesuaheli.dynmapwebapi.MarkerSet;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Handler {

    private final DynmapWebAPI plugin;

    @SuppressWarnings("unused")
    public Handler(@NotNull DynmapWebAPI plugin) {
        this.plugin = plugin;
    }

    public void getMarkersDefaultSet(@NotNull Context ctx) {
        ctx.json(this.plugin.dMapApi.getMarkersShortedList());
    }

    public void getMarkers(@NotNull Context ctx) {
        String set = ctx.pathParam("set");
        ctx.json(this.plugin.dMapApi.getMarkersShortedList(set));

    }

    public void getMarkerDefaultSet(@NotNull Context ctx) {
        String markerID = ctx.pathParam("marker");
        Marker m = this.plugin.dMapApi.getMarkerShorted(markerID);
        if (m == null) {
            ctx.result("This marker does not exist");
            ctx.status(404);
            return;
        }
        ctx.json(m);
    }

    public void getMarker(@NotNull Context ctx) {
        String markerID = ctx.pathParam("marker");
        String set = ctx.pathParam("set");
        Marker m = this.plugin.dMapApi.getMarkerShorted(markerID, set);
        if (m == null) {
            ctx.result("This marker does not exist");
            ctx.status(404);
            return;
        }
        ctx.json(m);
    }

    public void getMarkerSets(@NotNull Context ctx) {
        ctx.json(this.plugin.dMapApi.getMarkerSetsShortedList());
    }

    public void getMarkerSet(@NotNull Context ctx) {
        String set = ctx.pathParam("set");
        MarkerSet s = this.plugin.dMapApi.getMarkerSetShorted(set);
        if (s == null) {
            ctx.result("This set does not exist");
            ctx.status(404);
            return;
        }
        ctx.json(s);
    }

    public void postMarkerDefaultSet(@NotNull Context ctx) {
        ParsedMarker pm = ParsedMarker.parse(ctx);
        if (pm == null) {
            ctx.result("Could not parse all values");
            ctx.status(400);
            return;
        }

        if (!this.plugin.dMapApi.addMarker(pm.id, pm.label, pm.world, pm.posX, pm.posY, pm.posZ, pm.icon_id)) {
            ctx.result("Could not create marker");
            ctx.status(400);
            return;
        }

        ctx.result("Added marker");
    }

    public void postMarker(@NotNull Context ctx) {
        String set = ctx.pathParam("set");
        ParsedMarker pm = ParsedMarker.parse(ctx);
        if (pm == null) {
            ctx.result("Could not parse all values");
            ctx.status(400);
            return;
        }

        if (!this.plugin.dMapApi.addMarker(set, pm.id, pm.label, pm.world, pm.posX, pm.posY, pm.posZ, pm.icon_id)) {
            ctx.result("Could not create marker");
            ctx.status(400);
            return;
        }

        ctx.result("Added marker");
    }

    public void postMarkerSet(@NotNull Context ctx) {
        ParsedMarkerSet pms = ParsedMarkerSet.parse(ctx);
        if (pms == null) {
            ctx.result("Could not parse all values");
            ctx.status(400);
            return;
        }

        if (!this.plugin.dMapApi.addMarkerSet(pms.id, pms.label)) {
            ctx.result("Could not create marker");
            ctx.status(400);
            return;
        }

        ctx.result("Added marker set");
    }

    public void deleteMarkerDefaultSet(@NotNull Context ctx) {
        String id = ctx.formParam("marker");
        if (id == null) {
            ctx.result("Could not parse all values");
            ctx.status(400);
            return;
        }

        if (!this.plugin.dMapApi.deleteMarker(id)) {
            ctx.result("Could not delete marker");
            ctx.status(400);
            return;
        }

        ctx.result("Deleted marker");
    }

    public void deleteMarker(@NotNull Context ctx) {
        String id = ctx.formParam("marker");
        String set = ctx.formParam("set");
        if (id == null || set == null) {
            ctx.result("Could not parse all values");
            ctx.status(400);
            return;
        }

        if (!this.plugin.dMapApi.deleteMarker(id, set)) {
            ctx.result("Could not delete marker");
            ctx.status(400);
            return;
        }

        ctx.result("Deleted marker");
    }

    public void deleteMarkerSet(@NotNull Context ctx) {
        String set = ctx.formParam("set");
        if (set == null) {
            ctx.result("Could not parse all values");
            ctx.status(400);
            return;
        }

        if (!this.plugin.dMapApi.deleteMarkerSet(set)) {
            ctx.result("Could not delete marker set");
            ctx.status(400);
            return;
        }

        ctx.result("Deleted marker set");
    }

    private record ParsedMarker(@NotNull String id, @NotNull String label, @NotNull String world, int posX, int posY, int posZ, @NotNull String icon_id) {
        @Nullable
        private static ParsedMarker parse(@NotNull Context ctx) {
            String _id = ctx.formParam("id");
            String _label = ctx.formParam("label");
            String _world = ctx.formParam("world");
            String _sPosX = ctx.formParam("posX");
            String _sPosY = ctx.formParam("posY");
            String _sPosZ = ctx.formParam("posZ");
            String _icon_id = ctx.formParam("icon_id");

            // All value must not be null
            if (_id == null || _label == null || _world == null ||
                    _sPosX == null || _sPosY == null || _sPosZ == null ||
                    _icon_id == null) {
                return null;
            }

            try {
                return new ParsedMarker(_id, _label, _world, Integer.parseInt(_sPosX), Integer.parseInt(_sPosY), Integer.parseInt(_sPosZ), _icon_id);
            } catch (NumberFormatException e) {
                return null;
            }
        }
    }

    private record ParsedMarkerSet(@NotNull String id, @NotNull String label) {
        @Nullable
        private static ParsedMarkerSet parse(@NotNull Context ctx) {
            String _id = ctx.formParam("id");
            String _label = ctx.formParam("label");

            // All value must not be null
            if (_id == null || _label == null) {
                return null;
            }

            return new ParsedMarkerSet(_id, _label);
        }
    }
}
