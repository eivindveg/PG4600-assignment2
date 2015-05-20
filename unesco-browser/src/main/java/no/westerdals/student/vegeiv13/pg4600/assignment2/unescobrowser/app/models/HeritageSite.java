package no.westerdals.student.vegeiv13.pg4600.assignment2.unescobrowser.app.models;

public class HeritageSite {

    private final String title;
    private final String description;
    private final String image;
    private final String link;
    private final Double latitude;
    private final Double longitude;
    private String markerId;

    public HeritageSite(final String title, final String description, final String image, final String link, final Double latitude, final Double longitude) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.link = link;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getMarkerId() {
        return markerId;
    }

    public void setMarkerId(final String markerId) {
        this.markerId = markerId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getLink() {
        return link;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
