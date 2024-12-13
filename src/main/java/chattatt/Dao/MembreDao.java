package chattatt.Dao;



import java.util.List;

import chattatt.Incident;
import chattatt.Membre;

public interface MembreDao {
    void insertMembre(Membre membre);
    List<Incident> chargerListIncident(int membreId);
}
