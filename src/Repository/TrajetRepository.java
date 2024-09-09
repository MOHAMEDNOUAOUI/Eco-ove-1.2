package Repository;

import Config.Database;
import Model.Billets;
import Model.Contrats;
import Model.Reservation;
import Model.Trajet;
import Repository.Interface.TrajetRepositoryInterface;

import java.math.BigDecimal;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

import Enum.*;

public class TrajetRepository implements TrajetRepositoryInterface {






    public static Trajet fromResultSet(ResultSet rs) throws SQLException {

        Trajet trajet = new Trajet();
        trajet.setId(rs.getObject("id", UUID.class));
        trajet.setVille_depart(rs.getString("ville_depart"));
        trajet.setVille_arrivee(rs.getString("ville_arrivee"));
        trajet.setDistanceKm(rs.getDouble("distanceKm"));
        trajet.setTrajet_status(TrajetStatus.valueOf(rs.getString("trajet_status")));
        trajet.setTravelTime(rs.getString("traveltime"));
        return trajet;
    }


    @Override
    public void addToDatabase(Trajet trajet) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = Database.getConnection();
            String sql = "INSERT INTO TRAJET (id , ville_depart , ville_arrivee ,distanceKm, traveltime,trajet_status) VALUES (?,?,?,?,?,?::trajet_status)";

            ps = conn.prepareStatement(sql);
            ps.setObject(1, trajet.getId());
            ps.setString(2, trajet.getVille_depart());
            ps.setString(3, trajet.getVille_arrivee());
            ps.setDouble(4, trajet.getDistanceKm());
            ps.setObject(5, trajet.getTravelTime());
            ps.setObject(6, trajet.getTrajet_status().toString());
            ps.executeUpdate();


        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String deleteTrajet(Trajet trajet) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;


        conn = Database.getConnection();

        String sql = "UPDATE trajet SET trajet_status = 'UNAVAILABLE' WHERE id = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setObject(1,trajet.getId());
        pstmt.executeUpdate();

        return "";
    }



    @Override
    public List<Trajet> getAllTrajets() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Trajet> trajetsList = new ArrayList<>();
        Map<UUID , Trajet> trajetMap = new HashMap<>();

        try {
            conn = Database.getConnection();
            String sql = "SELECT B.id as billet_id , B.prix_achat , B.prix_vente , B.date_vente , B.statut_billet , B.type_transport ,trajet.* FROM trajet JOIN Billets as B on B.trajet_id = trajet.id WHERE B.reservation_id IS NULL";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                UUID trajetId = rs.getObject("id", UUID.class);
                Trajet trajet = trajetMap.get(trajetId);
                if (trajet == null) {
                    trajet = fromResultSet(rs);
                   trajetMap.put(trajetId, trajet);
                }

                UUID billetId = rs.getObject("billet_id", UUID.class);

                if (billetId != null) {
                    Billets billet = new Billets();
                    billet.setId(billetId);
                    billet.setPrix_achat(rs.getBigDecimal("prix_achat"));
                    billet.setPrix_vente(rs.getBigDecimal("prix_vente"));
                    billet.setDate_vente(rs.getObject("date_vente", LocalDate.class));
                    billet.setStatut_billet(StatutBillets.valueOf(rs.getString("statut_billet")));
                    billet.setType_transport(TypeTransport.valueOf(rs.getString("type_transport")));

                    trajet.setBilletsList(billet);
                }

                trajetsList.add(trajet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }





        return trajetsList;
    }

    @Override
    public Trajet getTrajetById(UUID id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Trajet trajet = null;
        try {
            conn = Database.getConnection();
            String sql = "select B.prix_achat , B.prix_vente , B.date_vente , B.statut_billet , B.type_transport , B.id as Billet_id ,Trajet.* from Trajet LEFT JOIN Billets AS B on B.trajet_id = Trajet.id WHERE Trajet.id = ? AND B.statut_billet = ENATTENTE";
            stmt = conn.prepareStatement(sql);
            stmt.setObject(1, id);
            rs = stmt.executeQuery();

          if(rs.next()) {
              trajet = fromResultSet(rs);

              do{
                  UUID billet_id = rs.getObject("billet_id", UUID.class);
                  if(billet_id != null){
                      Billets billet = new Billets();
                      billet.setId(billet_id);
                      billet.setPrix_vente(rs.getBigDecimal("prix_vente"));
                      billet.setPrix_achat(rs.getBigDecimal("prix_achat"));
                      billet.setType_transport(TypeTransport.valueOf(rs.getString("type_transport")));
                      billet.setStatut_billet(StatutBillets.valueOf(rs.getString("statut_billet")));
                      if(rs.getDate("date_vente") != null){
                          billet.setDate_vente(rs.getDate("date_vente").toLocalDate());
                      }

                      trajet.setBilletsList(billet);
                  }

              }while (rs.next());

          }


        }catch (SQLException e) {
            e.printStackTrace();
        }

        return trajet;
    }

    @Override
    public Trajet getTrajetByCordination(String depart, String arrival) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Trajet trajet = null;
        try {
            conn = Database.getConnection();
            String sql = "select trajet.id as trajetId ,billets.id as billet_id , * from trajet JOIN billets ON billets.trajet_id = trajet.id WHERE trajet.ville_depart = ? AND trajet.ville_arrivee = ? AND billets.reservation_id IS NULL";
            pstmt = conn.prepareStatement(sql);
            pstmt.setObject(1, depart);
            pstmt.setObject(2, arrival);
            rs = pstmt.executeQuery();

            trajet = fromResultSet(rs);

            do{
                UUID billet_id = rs.getObject("billet_id", UUID.class);
                if(billet_id != null){
                    Billets billet = new Billets();
                    billet.setId(billet_id);
                    billet.setPrix_vente(rs.getBigDecimal("prix_vente"));
                    billet.setPrix_achat(rs.getBigDecimal("prix_achat"));
                    billet.setType_transport(TypeTransport.valueOf(rs.getString("type_transport")));
                    billet.setStatut_billet(StatutBillets.valueOf(rs.getString("statut_billet")));
                    billet.setDate_vente(rs.getDate("date_vente").toLocalDate());

                    trajet.setBilletsList(billet);
                }

            }while (rs.next());



        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }


        return trajet;
    }



    @Override
    public void updateTrajet(Trajet trajet, String column, String value) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null ;
        conn = Database.getConnection();
        try {
            String sql = "UPDATE trajet SET " + column + " = ? WHERE id = ?";
            pstmt = conn.prepareStatement(sql);

            if(column.equals("distanceKm")){
                pstmt.setDouble(1, Double.parseDouble(value));
            }
            else if (column.equals("traveltime")){
                pstmt.setObject(1 ,value);
            }
            else if(column.equals("id")) {
                pstmt.setObject(1 , UUID.fromString(value));
            }
            else if(column.equals("trajet_status")) {
                pstmt.setString(1 , String.valueOf(TrajetStatus.valueOf(value)));
            }
            else {
                pstmt.setString(1 , value);
            }

            pstmt.setObject(2 , trajet.getId());



            pstmt.executeUpdate();


            System.out.println("Trajet "+column+" has been succefully updated to "+value);

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
