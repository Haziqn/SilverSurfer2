package sg.edu.rp.c346.silversurfer;

/**
 * Created by 15017523 on 18/1/2017.
 */


public class HealthSomething {

    private String title;
    private String desc;

    public HealthSomething() {}

    public HealthSomething(String title, String desc) {
        this.title = title;
        this.desc = desc;
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
