package entities;

import java.util.Date;

public class Evenement {
    private int id;
    private String nom;
    private int nbParticipant;
    private Date dateDebut;
    private Date dateFin;

    private String image;
    private String description;

    public Evenement(int id, String nom, int nbParticipant, Date dateDebut, Date dateFin, String image, String description) {
        this.id = id;
        this.nom = nom;
        this.nbParticipant = nbParticipant;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.image = image;
        this.description = description;
    }

    public Evenement(String nom, int nbParticipant, Date dateDebut, Date dateFin, String image, String description) {
        this.nom = nom;
        this.nbParticipant = nbParticipant;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.image = image;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNbParticipant() {
        return nbParticipant;
    }

    public void setNbParticipant(int nbParticipant) {
        this.nbParticipant = nbParticipant;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

