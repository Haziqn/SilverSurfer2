package sg.edu.rp.c346.silversurfer;

/**
 * Created by 15017523 on 1/17/2017.
 */

public class Photoes {

    private String title;
    private String desc;
    private String image;

    public Photoes() {}

    public Photoes(String title, String desc, String image) {
        this.title = title;
        this.desc = desc;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }





}
