package de.kesuaheli.dynmapwebapi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("markers")
    public List<String> getMarkers() {
        List<String> markers = new ArrayList<>();
        this.markerSet.getMarkers().forEach(m -> markers.add(m.getMarkerID()));
        return markers;
    }

    @JsonProperty("area_markers")
    public List<String> getAreaMarkers() {
        List<String> markers = new ArrayList<>();
        this.markerSet.getAreaMarkers().forEach(m -> markers.add(m.getMarkerID()));
        return markers;
    }

    @JsonProperty("poly_line_markers")
    public List<String> getPolyLineMarkers() {
        List<String> markers = new ArrayList<>();
        this.markerSet.getPolyLineMarkers().forEach(m -> markers.add(m.getMarkerID()));
        return markers;
    }

    @JsonProperty("circle_markers")
    public List<String> getCircleMarkers() {
        List<String> markers = new ArrayList<>();
        this.markerSet.getCircleMarkers().forEach(m -> markers.add(m.getMarkerID()));
        return markers;
    }

    @JsonProperty("id")
    public String getMarkerSetID() {
        return this.markerSet.getMarkerSetID();
    }

    @JsonProperty("label")
    public String getMarkerSetLabel() {
        return this.markerSet.getMarkerSetLabel();
    }

    @JsonIgnore
    public List<String> getAllowedMarkerIcons() {
        Set<MarkerIcon> iconSet = this.markerSet.getAllowedMarkerIcons();
        if (iconSet == null) {
            iconSet = new HashSet<>();
        }
        return iconSet.stream().map(MarkerIcon::getMarkerIconID).toList();
    }

    @JsonProperty("layer_priority")
    public int getLayerPriority() {
        return this.markerSet.getLayerPriority();
    }

    @JsonProperty("min_zoom")
    public int getMinZoom() {
        return this.markerSet.getMinZoom();
    }

    @JsonProperty("maxZoom")
    public int getMaxZoom() {
        return this.markerSet.getMaxZoom();
    }

    @JsonProperty("visible")
    public Boolean getLabelShow() {
        return this.markerSet.getLabelShow();
    }

    @JsonProperty("default_icon_id")
    public String getDefaultMarkerIcon() {
        return this.markerSet.getDefaultMarkerIcon().getMarkerIconID();
    }
}
