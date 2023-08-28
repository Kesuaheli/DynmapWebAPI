package de.kesuaheli.dynmapwebapi;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("unused")
public class Marker {

    private final org.dynmap.markers.Marker marker;

    public Marker(org.dynmap.markers.Marker marker) {
        this.marker = marker;
    }

    @JsonProperty("posX")
    public double getX() {
        return this.marker.getX();
    }

    @JsonProperty("posY")
    public double getY() {
        return this.marker.getY();
    }

    @JsonProperty("posZ")
    public double getZ() {
        return this.marker.getZ();
    }

    @JsonProperty("icon_id")
    public String getMarkerIcon() {
        return this.marker.getMarkerIcon().getMarkerIconID();
    }

    @JsonProperty("description")
    public String getDescription() {
        return this.marker.getDescription();
    }

    @JsonProperty("id")
    public String getMarkerID() {
        return this.marker.getMarkerID();
    }

    @JsonProperty("set")
    public String getMarkerSet() {
        return this.marker.getMarkerSet().getMarkerSetID();
    }

    @JsonProperty("world")
    public String getWorld() {
        return this.marker.getWorld();
    }

    @JsonProperty("persistent")
    public boolean isPersistentMarker() {
        return this.marker.isPersistentMarker();
    }

    @JsonProperty("label")
    public String getLabel() {
        return this.marker.getLabel();
    }

    @JsonProperty("markup_label")
    public boolean isLabelMarkup() {
        return this.marker.isLabelMarkup();
    }
}
