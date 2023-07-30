package de.kesuaheli.dynmapwebapi;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class MarkerSet {
    private final org.dynmap.markers.MarkerSet markerSet;

    public MarkerSet(org.dynmap.markers.MarkerSet markerSet) {
        this.markerSet = markerSet;
    }

    public List<String> getMarkers() {
        List<String> markers = new ArrayList<>();
        this.markerSet.getMarkers().forEach(m -> markers.add(m.getMarkerID()));
        return markers;
    }

    public List<String> getAreaMarkers() {
        List<String> markers = new ArrayList<>();
        this.markerSet.getAreaMarkers().forEach(m -> markers.add(m.getMarkerID()));
        return markers;
    }

    public List<String> getPolyLineMarkers() {
        List<String> markers = new ArrayList<>();
        this.markerSet.getPolyLineMarkers().forEach(m -> markers.add(m.getMarkerID()));
        return markers;
    }

    public List<String> getCircleMarkers() {
        List<String> markers = new ArrayList<>();
        this.markerSet.getCircleMarkers().forEach(m -> markers.add(m.getMarkerID()));
        return markers;
    }

    public String getMarkerSetID() {
        return this.markerSet.getMarkerSetID();
    }

    public String getMarkerSetLabel() {
        return this.markerSet.getMarkerSetLabel();
    }

    public List<String> getAllowedMarkerIcons() {
        List<String> icons = new ArrayList<>();
        this.markerSet.getAllowedMarkerIcons().forEach(i -> icons.add(i.getMarkerIconID()));
        return icons;
    }

    public int getLayerPriority() {
        return this.markerSet.getLayerPriority();
    }

    public int getMinZoom() {
        return this.markerSet.getMinZoom();
    }

    public int getMaxZoom() {
        return this.markerSet.getMaxZoom();
    }

    public Boolean getLabelShow() {
        return this.markerSet.getLabelShow();
    }

    public String getDefaultMarkerIcon() {
        return this.markerSet.getDefaultMarkerIcon().getMarkerIconID();
    }
}
