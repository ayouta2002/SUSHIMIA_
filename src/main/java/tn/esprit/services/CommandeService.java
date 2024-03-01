package tn.esprit.services;

import tn.esprit.models.Commande;
import tn.esprit.models.Resto;
import tn.esprit.models.status;
import tn.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommandeService implements IService<Commande> {

    private Connection connection;

    public CommandeService() {
        connection = DataSource.getInstance().getConnection();
    }

    @Override
    public void ajouter(Commande commande) {
        String req = "INSERT INTO commande(numC, adresseC, panier, price,status,idResto) VALUES (?, ?, ?, ?,?,?)";
        try (PreparedStatement st = connection.prepareStatement(req)) {
            st.setString(1, commande.getNumC());
            st.setString(2, commande.getAdresseC());
            st.setString(3, commande.getPanier());
            st.setDouble(4, commande.getPrice());
            st.setString(5, "non_traitee");
            st.setInt(6, commande.getIdResto());
            st.executeUpdate();
            System.out.println("Commande ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void modifier(Commande commande) {
        String req = "UPDATE commande SET numC = ?, adresseC = ?, panier = ? , price = ?,status = ? WHERE id_commande = ?";
        try (PreparedStatement st = connection.prepareStatement(req)) {
            st.setString(1, commande.getNumC());
            st.setString(2, commande.getAdresseC());
            st.setString(3, commande.getPanier());
            st.setDouble(4, commande.getPrice());
            st.setString(5, commande.getStatus().toString());
            st.setInt(6, commande.getId_commande());
            st.executeUpdate();
            commande.setStatus(status.en_cours);
            System.out.println("Commande modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void supprimer(Commande commande) {
        String req = "DELETE FROM commande WHERE id_commande = ?";
        try (PreparedStatement st = connection.prepareStatement(req)) {
            st.setInt(1, commande.getId_commande());
            st.executeUpdate();
            System.out.println("Commande supprimée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Commande> afficher() {
        List<Commande> commandes = new ArrayList<>();
        String req = "SELECT c.*, r.* FROM commande c LEFT JOIN resto r ON c.idResto = r.id_resto";
        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                Commande commande = new Commande(
                        rs.getInt("id_commande"), rs.getInt("idResto"),
                        rs.getString("numC"), rs.getString("adresseC"),rs.getString("panier"), status.valueOf(rs.getString("status"))
                        , rs.getDouble("price")
                );
                Resto restaurant = new Resto(
                        rs.getInt("id_resto"), rs.getString("nomR"), rs.getString("adresseR"), rs.getInt("numR")
                );
                commande.setRestaurant(restaurant);
                commandes.add(commande);
                System.out.println(commande);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(commandes);
        return commandes;
    }

    public List<Commande> afficherparstatus(String status) {
        List<Commande> commandes = new ArrayList<>();
        if (!isValidStatus(status)) {
            System.out.println("Invalid status: please choose between 'traitee', 'en_cours', or 'non_traitee': " + status);
            return commandes; // Return an empty list or handle the error accordingly
        }
        String req = "SELECT c.*, r.* FROM commande c LEFT JOIN resto r ON c.idResto = r.id_resto WHERE c.status = ?";
        try (PreparedStatement st = connection.prepareStatement(req)) {
            st.setString(1, status);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Commande commande = new Commande(
                            rs.getInt("id_commande"), rs.getInt("idResto"),
                            rs.getString("numC"), rs.getString("adresseC"), rs.getString("panier"), tn.esprit.models.status.valueOf(rs.getString("status"))
                            ,rs.getDouble("price")
                    );
                    Resto restaurant = new Resto(
                            rs.getInt("id_resto"), rs.getString("nomR"), rs.getString("adresseR"), rs.getInt("numR")
                    );
                    commande.setRestaurant(restaurant);
                    commandes.add(commande);
                    System.out.println(commande);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(commandes);
        return commandes;
    }

    private boolean isValidStatus(String status) {
        return status.equals("traitee") || status.equals("en_cours") || status.equals("non_traitee");
    }
}
