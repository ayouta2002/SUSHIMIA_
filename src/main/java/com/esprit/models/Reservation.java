package com.esprit.models;

import java.sql.Date;

public class Reservation {

    private int id_R;
    private int  id_C;
    private String zone;
    private int table_id;
    private Date dateR;
    private Date date;
    private String status;

    public Reservation() {
    }

    public Reservation(int id_R, int id_C, String zone, int table_id, Date dateR, String status) {
        this.id_R = id_R;
        this.id_C = id_C;
        this.zone = zone;
        this.table_id = table_id;
        this.dateR = dateR;
        this.status = status;
    }

    public Reservation(int id_C, String zone, int table_id, Date dateR, String status) {
        this.id_C = id_C;
        this.zone = zone;
        this.table_id = table_id;
        this.dateR = dateR;
        this.status = status;
    }

    public Reservation(int id_C, String zone, int table_id, Date dateR) {
        this.id_C = id_C;
        this.zone = zone;
        this.table_id = table_id;
        this.dateR = dateR;
    }

    public Reservation(int id_R, int id_C, String zone, int table_id, Date dateR, Date date, String status) {
        this.id_R = id_R;
        this.id_C = id_C;
        this.zone = zone;
        this.table_id = table_id;
        this.dateR = dateR;
        this.date = date;
        this.status = status;
    }

    public Reservation(int id_C, String zone, int table_id, Date dateR, Date date, String status) {
        this.id_C = id_C;
        this.zone = zone;
        this.table_id = table_id;
        this.dateR = dateR;
        this.date = date;
        this.status = status;
    }



    public int getId_R() {
        return id_R;
    }

    public void setId_R(int id_R) {
        this.id_R = id_R;
    }

    public int getId_C() {
        return id_C;
    }

    public void setId_C(int id_C) {
        this.id_C = id_C;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTable_id() {
        return table_id;
    }

    public void setTable_id(int table_id) {
        this.table_id = table_id;
    }

    public Date getDateR() {
        return dateR;
    }

    public Date getDate() {
        return date;
    }

    public void setDateR(Date dateR) {
        this.dateR = dateR;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id_R=" + id_R +
                ", id_C=" + id_C +
                ", zone='" + zone + '\'' +
                ", table_id=" + table_id +
                ", dateR=" + dateR +
                ", status='" + status + '\'' +
                '}';
    }
}
