package com.db.witt.project_witt;

public class Toilet_info_item {
    private String division;
    private String toilet_name;
    private String toilet_address1;
    private String toilet_address2;
    private String unisex_toilet;
    private String man_toilet_bowl_num;
    private String man_urinal_num;
    private String man_disabled_toilet_bowl_num;
    private String man_disabled_urinal_num;
    private String man_child_toilet_bowl_num;
    private String man_child_urinal_num;
    private String woman_toilet_bowl_num;
    private String woman_disabled_toilet_bowl_num;
    private String woman_child_toilet_bowl_num;
    private String management_name;
    private String phone_number;
    private String open_time;
    private String installation_year;
    private String latitude;
    private String longitude;
    private String rating;

    public Toilet_info_item(String division, String toilet_name, String toilet_address1, String toilet_address2, String unisex_toilet, String man_toilet_bowl_num, String man_urinal_num,
                            String man_disabled_toilet_bowl_num, String man_disabled_urinal_num, String man_child_toilet_bowl_num, String man_child_urinal_num, String woman_toilet_bowl_num,
                            String woman_disabled_toilet_bowl_num, String woman_child_toilet_bowl_num, String management_name, String phone_number, String open_time, String installation_year,
                            String latitude, String longitude, String rating) {
        this.division = division;
        this.toilet_name = toilet_name;
        this.toilet_address1 = toilet_address1;
        this.toilet_address2 = toilet_address2;
        this.unisex_toilet = unisex_toilet;
        this.man_toilet_bowl_num = man_toilet_bowl_num;
        this.man_urinal_num = man_urinal_num;
        this.man_disabled_toilet_bowl_num = man_disabled_toilet_bowl_num;
        this.man_disabled_urinal_num = man_disabled_urinal_num;
        this.man_child_toilet_bowl_num = man_child_toilet_bowl_num;
        this.man_child_urinal_num = man_child_urinal_num;
        this.woman_toilet_bowl_num = woman_toilet_bowl_num;
        this.woman_disabled_toilet_bowl_num = woman_disabled_toilet_bowl_num;
        this.woman_child_toilet_bowl_num = woman_child_toilet_bowl_num;
        this.management_name = management_name;
        this.phone_number = phone_number;
        this.open_time = open_time;
        this.installation_year = installation_year;
        this.latitude = latitude;
        this.longitude = longitude;
        this.rating = rating;
    }

    public String getDivision() {
        return division;
    }

    public String getMan_toilet_bowl_num() {
        return man_toilet_bowl_num;
    }

    public String getToilet_address1() {
        return toilet_address1;
    }

    public String getToilet_address2() {
        return toilet_address2;
    }

    public String getMan_urinal_num() {
        return man_urinal_num;
    }

    public String getToilet_name() {
        return toilet_name;
    }

    public String getInstallation_year() {
        return installation_year;
    }

    public String getMan_disabled_toilet_bowl_num() {
        return man_disabled_toilet_bowl_num;
    }

    public String getUnisex_toilet() {
        return unisex_toilet;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getMan_child_toilet_bowl_num() {
        return man_child_toilet_bowl_num;
    }

    public String getMan_child_urinal_num() {
        return man_child_urinal_num;
    }

    public String getMan_disabled_urinal_num() {
        return man_disabled_urinal_num;
    }

    public String getManagement_name() {
        return management_name;
    }

    public String getOpen_time() {
        return open_time;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getWoman_child_toilet_bowl_num() {
        return woman_child_toilet_bowl_num;
    }

    public String getWoman_disabled_toilet_bowl_num() {
        return woman_disabled_toilet_bowl_num;
    }

    public String getWoman_toilet_bowl_num() {
        return woman_toilet_bowl_num;
    }

    public String getRating() {
        return rating;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public void setMan_disabled_toilet_bowl_num(String man_disabled_toilet_bowl_num) {
        this.man_disabled_toilet_bowl_num = man_disabled_toilet_bowl_num;
    }

    public void setMan_toilet_bowl_num(String man_toilet_bowl_num) {
        this.man_toilet_bowl_num = man_toilet_bowl_num;
    }

    public void setToilet_address1(String toilet_address1) {
        this.toilet_address1 = toilet_address1;
    }

    public void setMan_child_toilet_bowl_num(String man_child_toilet_bowl_num) {
        this.man_child_toilet_bowl_num = man_child_toilet_bowl_num;
    }

    public void setMan_child_urinal_num(String man_child_urinal_num) {
        this.man_child_urinal_num = man_child_urinal_num;
    }

    public void setToilet_address2(String toilet_address2) {
        this.toilet_address2 = toilet_address2;
    }

    public void setMan_disabled_urinal_num(String man_disabled_urinal_num) {
        this.man_disabled_urinal_num = man_disabled_urinal_num;
    }

    public void setMan_urinal_num(String man_urinal_num) {
        this.man_urinal_num = man_urinal_num;
    }

    public void setToilet_name(String toilet_name) {
        this.toilet_name = toilet_name;
    }

    public void setUnisex_toilet(String unisex_toilet) {
        this.unisex_toilet = unisex_toilet;
    }

    public void setInstallation_year(String installation_year) {
        this.installation_year = installation_year;
    }

    public void setWoman_child_toilet_bowl_num(String woman_child_toilet_bowl_num) {
        this.woman_child_toilet_bowl_num = woman_child_toilet_bowl_num;
    }

    public void setWoman_toilet_bowl_num(String woman_toilet_bowl_num) {
        this.woman_toilet_bowl_num = woman_toilet_bowl_num;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setManagement_name(String management_name) {
        this.management_name = management_name;
    }

    public void setOpen_time(String open_time) {
        this.open_time = open_time;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setWoman_disabled_toilet_bowl_num(String woman_disabled_toilet_bowl_num) {
        this.woman_disabled_toilet_bowl_num = woman_disabled_toilet_bowl_num;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}

