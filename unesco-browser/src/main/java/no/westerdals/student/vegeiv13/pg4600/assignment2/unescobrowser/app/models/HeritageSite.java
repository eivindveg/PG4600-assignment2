package no.westerdals.student.vegeiv13.pg4600.assignment2.unescobrowser.app.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable
public class HeritageSite implements Serializable {

    public static final int MODEL_VERSION = 1;
    @DatabaseField
    private String title;
    @DatabaseField
    private String description;
    @DatabaseField
    private String image;
    @DatabaseField
    private String link;
    @DatabaseField
    private Double latitude;
    @DatabaseField
    private Double longitude;
    @DatabaseField(generatedId = true)
    private Integer id;
    private String markerId;

    public HeritageSite() {

    }

    public HeritageSite(final String title, final String description, final String image, final String link, final Double latitude, final Double longitude) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.link = link;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
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

    public MarkerOptions toMarkerOptions() {
        MarkerOptions options = new MarkerOptions();
        options.position(new LatLng(getLatitude(), getLongitude()));
        options.title(getTitle());
        options.snippet(getDescription());
        return options;
    }
}
