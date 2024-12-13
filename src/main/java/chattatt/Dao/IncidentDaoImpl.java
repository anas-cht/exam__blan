package chattatt.Dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Set;

import chattatt.Incident;


public class IncidentDaoImpl implements IncidentDao{

    @Override
    public void insertIncident(Incident incident) {
        String sql = "insert into incident (reference,time) VALUES (?,?)";
        try(Connection conn= DataBaseConnection.getConnection();
            PreparedStatement stmt= conn.prepareStatement(sql)) {
            stmt.setString(1, incident.getReference());
            stmt.setTime(2, incident.getTime());
            stmt.executeUpdate();
        }catch (Exception ex){
            ex.getMessage();
        }
    }

    @Override
    public void insertIncidents(Set<Incident> incidents) {
        String sql = "insert into incident (reference, time, status) VALUES (?, ?, ?)";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (Incident incident : incidents) {
                stmt.setString(1, incident.getReference());
                stmt.setTime(2, incident.getTime());
                stmt.setBoolean(3, incident.isStatus());
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (Exception ex) {
            ex.getMessage();
        }
    }
}

