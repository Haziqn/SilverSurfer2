package sg.edu.rp.c346.silversurfer;

/**
 * Created by 15017523 on 19/1/2017.
 */

public class Pharmacy {
    private String title;
    private String address;
    private String openHours;

    public Pharmacy(String title, String address, String openHouse) {
        this.title = title;
        this.address = address;
        this.openHours = openHours;
    }
    public Pharmacy() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpenHours() {
        return openHours;
    }

    public void setOpenHours(String openHours) {
        this.openHours = openHours;
    }


}
