package entities;

import java.util.Date;

public class Reservation {
    private int id;
    private String description;
    private Date date;
    private int id_evenement;

    public Reservation(int id, String description, Date date, int id_evenement) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.id_evenement = id_evenement;
    }

    public Reservation(String description, Date date, int id_evenement) {
        this.description = description;
        this.date = date;
        this.id_evenement = id_evenement;
    }

    public Reservation(String description, Date date) {
        this.description = description;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId_evenement() {
        return id_evenement;
    }

    public void setId_evenement(int id_evenement) {
        this.id_evenement = id_evenement;
    }
}
