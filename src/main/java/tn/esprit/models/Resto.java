package tn.esprit.models;

public class Resto {
    private int id_resto;
    private String nomR;
    private String adresseR;
    private int numR;

    public Resto(int id_resto, String nomR, String adresseR, int numR) {
        this.id_resto = id_resto;
        this.nomR = nomR;
        this.adresseR = adresseR;
        this.numR = numR;
    }

    public Resto(String nomR, String adresseR, int numR) {
        this.nomR = nomR;
        this.adresseR = adresseR;
        this.numR = numR;
    }


    public int getId_resto() {
        return id_resto;
    }

    public void setId_resto(int id_resto) {
        this.id_resto = id_resto;
    }

    public String getNomR() {
        return nomR;
    }

    public void setNomR(String nomR) {
        this.nomR = nomR;
    }

    public String getAdresseR() {
        return adresseR;
    }

    public void setAdresseR(String adresseR) {
        this.adresseR = adresseR;
    }

    public int getNumR() {
        return numR;
    }

    public void setNumR(int numR) {
        this.numR = numR;
    }

    @Override
    public String toString() {
        return nomR;
    }
}
