package fr.ecp.sio.piopio.model;

/**
 * Created by Diana on 05/12/2014.
 */
public class Talk {
    private String id;
    private String pio;
    private String date;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return pio;
    }

    public void setContent(String content) {
        this.pio = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
