package tr.edu.yildiz.recepdemirci;

import java.util.Date;

public class Event {
    private String name;
    private String type;
    private Date date;
    private String location;


    public Event () {

    }

    public Event(String name, String type, Date date, String location) {
        this.name = name;
        this.type = type;
        this.date = date;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
