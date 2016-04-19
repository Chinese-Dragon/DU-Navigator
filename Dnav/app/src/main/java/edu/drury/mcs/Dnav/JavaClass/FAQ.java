package edu.drury.mcs.Dnav.JavaClass;

/**
 * Created by yma004 on 4/18/2016.
 */
public class FAQ {
    private String FAQ_name;
    private String FAQ_link;
    public FAQ(String name, String link){
        this.FAQ_name = name;
        this.FAQ_link =link;
    }

    public String getFAQ_name() {
        return FAQ_name;
    }

    public String getFAQ_link() {
        return FAQ_link;
    }
}
