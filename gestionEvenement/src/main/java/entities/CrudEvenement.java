package entities;

import java.sql.SQLException;
import java.util.List;

public interface CrudEvenement <Even> {
    public void ajouter(Even e);
    public void modifier(Even e);
    public void supprimer(int id) throws SQLException;
    public List<Evenement> Show();
}
