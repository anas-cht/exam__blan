package chattatt.Dao;

    import java.util.Set;

import chattatt.Incident;
    
    public interface IncidentDao {
        void insertIncident(Incident incident);
        void insertIncidents(Set<Incident> incidents);
    }
