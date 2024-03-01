package tn.esprit.models;

public class Commande {
    private int id_commande;
    private int idResto;
    private String numC;
    private String adresseC;
    private String panier;
    private double price;
    private status status;
    private Resto restaurant;

    public Commande(int id_commande, int idResto, String numC, String adresseC, String panier , double price) {
        this.id_commande = id_commande;
        this.idResto = idResto;
        this.adresseC = adresseC;
        this.numC = numC;
        this.panier = panier;
        this.price = price;
    }

    public Commande(int id_commande, int idResto, String numC, String adresseC, String panier, status status ,double price) {
        this.id_commande = id_commande;
        this.idResto = idResto;
        this.adresseC = adresseC;
        this.numC = numC;
        this.panier = panier;
        this.status = status;
        this.price = price;
    }

    public Commande(int idResto, String numC, String adresseC, String panier, double price, tn.esprit.models.status status, Resto restaurant) {
        this.idResto = idResto;
        this.numC = numC;
        this.adresseC = adresseC;
        this.panier = panier;
        this.price = price;
        this.status = status;
        this.restaurant = restaurant;
    }

    public Commande() {

    }

    public int getId_commande() {
        return id_commande;
    }

    public void setId_commande(int id_commande) {
        this.id_commande = id_commande;
    }

    public int getIdResto() {
        return idResto;
    }

    public void setIdResto(int idResto) {
        this.idResto = idResto;
    }

    public String getNumC() {
        return numC;
    }

    public void setNumC(String numC) {
        this.numC = numC;
    }

    public String getAdresseC() {
        return adresseC;
    }

    public void setAdresseC(String adresseC) {
        this.adresseC = adresseC;
    }

    public String getPanier() {
        return panier;
    }

    public void setPanier(String panier) {
        this.panier = panier;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public status getStatus() {
        return status;
    }

    public void setStatus(status status) {
        this.status = status;
    }

    public Resto getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Resto restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "id_commande=" + id_commande +
                ", idResto=" + idResto +
                ", numC='" + numC + '\'' +
                ", adresseC='" + adresseC + '\'' +
                ", panier='" + panier + '\'' +
                ", price=" + price +
                ", status=" + status +
                ", restaurant=" + restaurant +
                '}';
    }
}
