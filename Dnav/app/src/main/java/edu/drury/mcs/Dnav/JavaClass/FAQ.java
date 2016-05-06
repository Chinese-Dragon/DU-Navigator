package edu.drury.mcs.Dnav.JavaClass;

/**
 * Created by yma004 on 4/18/2016.
 */
public class FAQ {
    private String FAQ_name;
    private String FAQ_link;
    private int id;
    public FAQ(String name, String link,int id){
        this.FAQ_name = name;
        this.FAQ_link =link;
        this.id=id;
    }

    //getter
    public String getFAQ_name() {
        return FAQ_name;
    }
    public String getFAQ_link() {
        return FAQ_link;
    }

    public int getId() {
        return id;
    }

    //setter

    public void setId(int id) {
        this.id = id;
    }

    public void setFAQ_link(String FAQ_link) {
        this.FAQ_link = FAQ_link;
    }

    public void setFAQ_name(String FAQ_name) {
        this.FAQ_name = FAQ_name;
    }

}
