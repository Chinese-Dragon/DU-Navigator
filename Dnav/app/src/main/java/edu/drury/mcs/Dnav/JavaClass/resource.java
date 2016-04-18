package edu.drury.mcs.Dnav.JavaClass;

/**
 * Created by mark93 on 4/16/2016.
 */
public class resource {
    private String res_name;
    private String res_description;
    private Contact_Info res_contact1;
    private Contact_Info res_contact2;

    public resource(String res_name, String res_description, Contact_Info res_contact1, Contact_Info res_contact2) {
        this.res_name = res_name;
        this.res_description = res_description;
        this.res_contact1 = res_contact1;
        this.res_contact2 = res_contact2;
    }

    public resource(String res_name, String res_description, Contact_Info res_contact1) {
        this.res_name = res_name;
        this.res_description = res_description;
        this.res_contact1 = res_contact1;
    }

    //setter
    public String getRes_name() {
        return res_name;
    }

    public String getRes_description() {
        return res_description;
    }

    public Contact_Info getRes_contact1() {
        return res_contact1;
    }

    public Contact_Info getRes_contact2() {
        return res_contact2;
    }

    //getter
    public void setRes_name(String newName) {
        res_name = newName;
    }

    public void setRes_description(String newDescription) {
        res_description = newDescription;
    }

    public void setRes_contact1(Contact_Info newContact) {
        res_contact1 = newContact;
    }

    public void setRes_contact2(Contact_Info newContact) {
        res_contact2 = newContact;
    }

}
