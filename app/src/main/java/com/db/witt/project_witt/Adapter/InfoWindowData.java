package com.db.witt.project_witt.Adapter;

public class InfoWindowData {
    private String open_time;
    private String rating;
    private String toilet_id;

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRating() {
        return rating;
    }

    public void setOpen_time(String open_time) {
        this.open_time = open_time;
    }

    public String getOpen_time() {
        return open_time;
    }

    public String getToilet_id() {
        return toilet_id;
    }

    public void setToilet_id(String toilet_id) {
        this.toilet_id = toilet_id;
    }
}
