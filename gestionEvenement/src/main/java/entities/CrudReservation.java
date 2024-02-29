package entities;

import java.sql.SQLException;
import java.util.List;

public interface CrudReservation <Res> {
    public void ajouter(Res r);
    public void modifier(Res r);
    public void supprimer(int id) throws SQLException;
    public List<Reservation> Show();
}
