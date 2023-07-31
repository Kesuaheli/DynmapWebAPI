package de.kesuaheli.dynmapwebapi;

import org.dynmap.markers.MarkerIcon;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        Set<MarkerIcon> iconSet = this.markerSet.getAllowedMarkerIcons();
        if (iconSet == null) {
            iconSet = new HashSet<>();
        }
        return iconSet.stream().map(MarkerIcon::getMarkerIconID).toList();
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
