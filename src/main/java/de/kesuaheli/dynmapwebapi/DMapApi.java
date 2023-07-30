package de.kesuaheli.dynmapwebapi;

import org.dynmap.DynmapCommonAPI;
import org.dynmap.DynmapCommonAPIListener;
import org.dynmap.markers.Marker;
import org.dynmap.markers.MarkerAPI;
import org.dynmap.markers.MarkerIcon;
import org.dynmap.markers.MarkerSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;

import java.util.*;

@SuppressWarnings("unused")
public class DMapApi extends DynmapCommonAPIListener {

    private final DynmapWebAPI plugin;
    @SuppressWarnings("FieldCanBeLocal")
    private DynmapCommonAPI api;
    private MarkerAPI markerApi;

    public DMapApi(DynmapWebAPI plugin) {
        this.plugin = plugin;
    }

    @Override
    public void apiEnabled(@NotNull DynmapCommonAPI api) {
        this.api = api;
        this.markerApi = this.api.getMarkerAPI();
        plugin.LOG.info("dmap registered");
    }

    public void printMarkers() {
        Set<Marker> markers = this.markerApi.getMarkerSet("markers").getMarkers();
        markers.forEach(m -> {
            Vector3d pos = new Vector3d(m.getX(),m.getY(),m.getX());
            String id = m.getMarkerID();
            String label = m.getLabel();
            String world = m.getWorld();
            this.plugin.LOG.info("Marker: " + label + " (" + id + "), pos: " + pos + ", world: " + world);
        });
    }

    @NotNull
    public List<Marker> getMarkers() {
        return this.getMarkers(MarkerSet.DEFAULT);
    }

    @NotNull
    public List<Marker> getMarkers(@NotNull String set) {
        List<Marker> markerList = new ArrayList<>();
        MarkerSet markerSet = this.markerApi.getMarkerSet(set);
        if (markerSet != null) {
            markerList = markerSet.getMarkers().stream().toList();
        }
        return markerList;
    }

    @NotNull
    public List<MarkerSet> getMarkerSets() {
        return this.markerApi.getMarkerSets().stream().toList();
    }

    @NotNull
    public List<de.kesuaheli.dynmapwebapi.Marker> getMarkersShortedList() {
        return this.getMarkersShortedList(MarkerSet.DEFAULT);
    }

    @NotNull
    public List<de.kesuaheli.dynmapwebapi.Marker> getMarkersShortedList(@NotNull String set) {
        List<de.kesuaheli.dynmapwebapi.Marker> markers = new ArrayList<>();
        getMarkers(set).forEach(m -> markers.add(new de.kesuaheli.dynmapwebapi.Marker(m)));
        return markers;
    }

    @Nullable
    public de.kesuaheli.dynmapwebapi.Marker getMarkerShorted(@NotNull String id) {
        return getMarkerShorted(id, MarkerSet.DEFAULT);

    }

    @Nullable
    public de.kesuaheli.dynmapwebapi.Marker getMarkerShorted(@NotNull String id, @NotNull String set) {
        Optional<Marker> optionalMarker = getMarkers(set).stream()
                .filter(m -> m.getMarkerID().equals(id))
                .findFirst();
        return optionalMarker.map(de.kesuaheli.dynmapwebapi.Marker::new).orElse(null);
    }

    @NotNull
    public List<de.kesuaheli.dynmapwebapi.MarkerSet> getMarkerSetsShortedList() {
        List<de.kesuaheli.dynmapwebapi.MarkerSet> sets = new ArrayList<>();
        getMarkerSets().forEach(s -> sets.add(new de.kesuaheli.dynmapwebapi.MarkerSet(s)));
        return sets;
    }

    @Nullable
    public de.kesuaheli.dynmapwebapi.MarkerSet getMarkerSetShorted(@NotNull String set) {
        Optional<MarkerSet> optionalMarker = getMarkerSets().stream()
                .filter(m -> m.getMarkerSetID().equals(set))
                .findFirst();
        return optionalMarker.map(de.kesuaheli.dynmapwebapi.MarkerSet::new).orElse(null);
    }


    public boolean addMarker(@NotNull String id, @NotNull String label, @NotNull String world,
                             int posX, int posY, int posZ,
                             @NotNull String icon_id) {
        return addMarker(MarkerSet.DEFAULT, id, label, world, posX, posY, posZ, icon_id);
    }
    public boolean addMarker(@NotNull String set,
                             @NotNull String id, @NotNull String label, @NotNull String world,
                             int posX, int posY, int posZ,
                             @NotNull String icon_id) {
        MarkerSet markerSet = this.markerApi.getMarkerSet(set);
        if (markerSet == null) {
            return false;
        }

        MarkerIcon icon = this.markerApi.getMarkerIcon(icon_id);
        if (icon == null) {
            return false;
        }
        Marker m = markerSet.createMarker(id, label, world, posX, posY, posZ, icon, markerSet.isMarkerSetPersistent());
        if (m == null) {
            this.plugin.LOG.warning("Created marker is null: set="+set+
                    ", id="+id+
                    ", label="+label+
                    ", world="+world+
                    ", pos=["+posX+","+posY+","+posZ+
                    "], icon="+icon_id);
            return false;
        }
        return true;
    }
    
    public boolean addMarkerSet(@NotNull String id, @NotNull String label) {
        MarkerSet markerSet = this.markerApi.createMarkerSet(id, label, null, true);
        if (markerSet == null) {
            this.plugin.LOG.warning("Created marker set is null: id="+id+
                    ", label="+label);
            return false;
        }
        return true;
    }

    public boolean deleteMarker(@NotNull String id) {
        return deleteMarker(id, MarkerSet.DEFAULT);
    }
    public boolean deleteMarker(@NotNull String id, @NotNull String set) {
        MarkerSet markers = this.markerApi.getMarkerSet(set);
        if (markers == null) {
            return false;
        }
        Marker m = markers.findMarker(id);
        if (m == null) {
            return false;
        }
        m.deleteMarker();
        return true;
    }

    public boolean deleteMarkerSet(@NotNull String set) {
        MarkerSet markers = this.markerApi.getMarkerSet(set);
        if (markers == null) {
            return false;
        }
        markers.deleteMarkerSet();
        return true;
    }
}
