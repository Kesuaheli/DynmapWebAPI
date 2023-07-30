package de.kesuaheli.dynmapwebapi;

@SuppressWarnings("unused")
public class Marker {

    private final org.dynmap.markers.Marker marker;

    public Marker(org.dynmap.markers.Marker marker) {
        this.marker = marker;
    }
    
    public double getX() {
        return this.marker.getX();
    }

    public double getY() {
        return this.marker.getY();
    }

    public double getZ() {
        return this.marker.getZ();
    }

    public String getMarkerIcon() {
        return this.marker.getMarkerIcon().getMarkerIconID();
    }

    public String getDescription() {
        return this.marker.getDescription();
    }

    public String getMarkerID() {
        return this.marker.getMarkerID();
    }

    public String getMarkerSet() {
        return this.marker.getMarkerSet().getMarkerSetID();
    }

    public String getWorld() {
        return this.marker.getWorld();
    }

    public boolean isPersistentMarker() {
        return this.marker.isPersistentMarker();
    }

    public String getLabel() {
        return this.marker.getLabel();
    }

    public boolean isLabelMarkup() {
        return this.marker.isLabelMarkup();
    }
}
