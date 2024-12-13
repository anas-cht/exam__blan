package chattatt.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import chattatt.Membre;
import chattatt.Incident;

public class MembreDaoImpl implements MembreDao {

    @Override
    public void insertMembre(Membre membre) {
        String sql = "insert into membre values(?,?,?,?,?)";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, membre.getIdentifiant());
            ps.setString(2, membre.getNom());
            ps.setString(3, membre.getPrenom());
            ps.setString(4, membre.getEmail());
            ps.setString(5, membre.getPhone());

            ps.executeUpdate();

        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public List<Incident> chargerListIncident(int membreId) {
        List<Incident> incidents = new ArrayList<>();
        String sql = "SELECT reference, time, status FROM incident WHERE membre_id = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, membreId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Incident incident = new Incident();
                    incident.setReference(rs.getString("reference"));
                    incident.setTime(rs.getTime("time"));
                    incident.setStatus(rs.getBoolean("status"));
                    incidents.add(incident);
                }
            }
        } catch (Exception ex) {
            ex.getMessage();
        }
        return incidents;
    }

    public boolean doesIdentifiantExist(String identifiant) {
        String query = "SELECT COUNT(*) FROM membre WHERE identifiant = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, identifiant);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.getMessage();
            return false;
        }
    }




}

