import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class RepoCurse implements RepositoryCurse {

    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public RepoCurse(Properties props) {
        logger.info("Initializing RepoCurse with properties: {} ",props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public void add(Cursa elem) {
        logger.traceEntry("saving task {}", elem);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("insert into Curse (data_plecarii, destinatie) values (?, ?)", Statement.RETURN_GENERATED_KEYS)){
            java.sql.Date dateSql = java.sql.Date.valueOf(elem.getData_plecarii().toLocalDate());
            Timestamp date = Timestamp.valueOf(elem.getData_plecarii());
            preStmt.setTimestamp(1, date);
            preStmt.setString(2, elem.getDestinatie());
            int result = preStmt.executeUpdate();
            logger.trace("Saved {} instances", result);
            //de aici in jos adaug in tabelul Curse_rezervari
            int id = 0;
            ResultSet rs = preStmt.getGeneratedKeys();
            if(rs.next()) {
                id = rs.getInt(1);
            }
            List<Rezervare> rezervari = elem.getRezervari();
            for (int i=0; i<rezervari.size(); i++){
                PreparedStatement preStmt2 = con.prepareStatement("insert into Curse_rezervari(id_cursa, nr_loc, nume_persoana) values (?, ?, ?)");
                preStmt2.setInt(1, id);
                preStmt2.setInt(2, rezervari.get(i).getNr_loc());
                preStmt2.setString(3, rezervari.get(i).getNume_persoana());
                result = preStmt2.executeUpdate();
                logger.trace("Saved {} instances", result);
            }

        }catch(SQLException ex){
            logger.error(ex);
            System.err.println("Error DB" + ex);
        }
        logger.traceExit();
    }

    @Override
    public void delete(Cursa elem) {
        logger.traceEntry("saving task {}", elem);
        try (Connection connection = dbUtils.getConnection()){

            PreparedStatement statement = connection.prepareStatement("delete from Curse where id= ?");
            statement.setInt(1, elem.getId());
            int result = statement.executeUpdate();
            logger.trace("Saved {} instances", result);

            PreparedStatement statement2 = connection.prepareStatement("delete from Curse_rezervari where id_cursa= ?");
            statement2.setLong(1, elem.getId());
            result = statement2.executeUpdate();
            logger.trace("Saved {} instances", result);

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error DB" + e);
        }
        logger.traceExit();
    }

    @Override
    public void update(Cursa elem, Integer id) {

        logger.traceEntry("saving task {}", elem);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("update Curse set data_plecarii=(?), destinatie=(?) where id=(?)")){
            Timestamp date = Timestamp.valueOf(elem.getData_plecarii());
            preStmt.setTimestamp(1, date);
            preStmt.setString(2, elem.getDestinatie());
            preStmt.setInt(3, id);
            int result = preStmt.executeUpdate();
            logger.trace("Saved {} instances", result);

        }catch(SQLException ex){
            logger.error(ex);
            System.err.println("Error DB" + ex);
        }
        logger.traceExit();
    }

    @Override
    public Cursa findById(Integer id) {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        Cursa cursa = null;
        try(PreparedStatement preStmt = con.prepareStatement("select * from Curse where id=?")){
            preStmt.setInt(1, id);
            try(ResultSet result = preStmt.executeQuery()){
                if(result.next()) {
                    LocalDateTime date = result.getTimestamp("data_plecarii").toLocalDateTime();
                    String destinatie = result.getString("destinatie");

                    PreparedStatement preStmt2 = con.prepareStatement("select * from Curse_rezervari where id_cursa=?");
                    preStmt2.setInt(1, id);
                    ResultSet resultSet = preStmt2.executeQuery();
                    List<Rezervare> rezervari = new ArrayList<>();
                    while (resultSet.next()) {
                        int nr_loc = resultSet.getInt("nr_loc");
                        String nume_persoana = resultSet.getString("nume_persoana");
                        Rezervare rezervare = new Rezervare(id, nr_loc, nume_persoana);
                        rezervari.add(rezervare);
                    }
                    cursa = new Cursa(destinatie, date, rezervari);
                    cursa.setId(id);
                }
            }
        }catch (SQLException e){
            logger.error(e);
            System.err.println("Error DB"+e);
        }
        logger.traceExit(cursa);
        return cursa;
    }

    @Override
    public List<Cursa> getAll() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<Cursa> curse = new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Curse")){
            try(ResultSet result = preStmt.executeQuery()){
                while(result.next()){
                    int id = result.getInt("id");
                    String destinatie = result.getString("destinatie");
                    LocalDateTime date = result.getTimestamp("data_plecarii").toLocalDateTime();

                    List<Rezervare> rezervari = new ArrayList<>();
                    PreparedStatement preStmt2 = con.prepareStatement("select * from Curse_rezervari where id_cursa=?");
                    preStmt2.setInt(1, id);
                    ResultSet resultSet = preStmt2.executeQuery();
                    while (resultSet.next()) {
                        int nr_loc = resultSet.getInt("nr_loc");
                        String nume_persoana = resultSet.getString("nume_persoana");
                        Rezervare rezervare = new Rezervare(id, nr_loc, nume_persoana);
                        rezervari.add(rezervare);
                    }
                    Cursa cursa = new Cursa(destinatie, date, rezervari);
                    cursa.setId(id);
                    curse.add(cursa);
                }
            }
        }catch (SQLException e){
            logger.error(e);
            System.err.println("Error DB"+e);
        }
        logger.traceExit(curse);
        return curse;
    }
}
