
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RepoRezervari implements RepositoryRezervari{

    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public RepoRezervari(Properties props) {
        logger.info("Initializing RepoCurse with properties: {} ",props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public void add(Rezervare elem) {
        logger.traceEntry("saving task {}", elem);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("insert into Curse_rezervari (id_cursa, nr_loc, nume_persoana) values (?, ?, ?)")){
            preStmt.setInt(1, elem.getId_cursa());
            preStmt.setInt(2, elem.getNr_loc());
            preStmt.setString(3, elem.getNume_persoana());
            int result = preStmt.executeUpdate();
            logger.trace("Saved {} instances", result);

        }catch(SQLException ex){
            logger.error(ex);
            System.err.println("Error DB" + ex);
        }
        logger.traceExit();
    }

    @Override
    public void delete(Rezervare elem) {
        logger.traceEntry("saving task {}", elem);
        try (Connection connection = dbUtils.getConnection()){

            PreparedStatement statement = connection.prepareStatement("delete from Curse_rezervari where id_cursa=? and nr_loc=?");
            statement.setInt(1, elem.getId_cursa());
            statement.setInt(2, elem.getNr_loc());
            int result = statement.executeUpdate();
            logger.trace("Saved {} instances", result);

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error DB" + e);
        }
        logger.traceExit();
    }

    @Override
    public void update(Rezervare elem, Integer id_cursa, Integer nr_loc) {
        logger.traceEntry("saving task {}", elem);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("update Curse_rezervari set nume_persoana=(?) where id_cursa=(?) and nr_loc=?")){
            preStmt.setString(1, elem.getNume_persoana());
            preStmt.setInt(2, id_cursa);
            preStmt.setInt(3, nr_loc);
            int result = preStmt.executeUpdate();
            logger.trace("Saved {} instances", result);

        }catch(SQLException ex){
            logger.error(ex);
            System.err.println("Error DB" + ex);
        }
        logger.traceExit();
    }

    @Override
    public Rezervare findById(Integer id_cursa, Integer nr_loc) {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        Rezervare rezervare = null;
        try(PreparedStatement preStmt = con.prepareStatement("select * from Curse_rezervari where id_cursa=? and nr_loc=?")){
            preStmt.setInt(1, id_cursa);
            preStmt.setInt(2, nr_loc);
            try(ResultSet result = preStmt.executeQuery()){
                if(result.next()) {
                    String nume_persoana = result.getString("nume_persoana");

                    rezervare = new Rezervare(id_cursa, nr_loc, nume_persoana);
                }
            }
        }catch (SQLException e){
            logger.error(e);
            System.err.println("Error DB"+e);
        }
        logger.traceExit(rezervare);
        return rezervare;
    }

    @Override
    public List<Rezervare> getAll(Integer id_cursa) {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<Rezervare> rezervari = new ArrayList<>();
        try(PreparedStatement preStmt = con.prepareStatement("select * from Curse_rezervari where id_cursa=?")){
            preStmt.setInt(1, id_cursa);
            try(ResultSet result = preStmt.executeQuery()){
                while(result.next()){
                    int nr_loc = result.getInt("nr_loc");
                    String nume_persoana = result.getString("nume_persoana");
                    Rezervare rezervare = new Rezervare(id_cursa, nr_loc, nume_persoana);
                    rezervari.add(rezervare);
                }
            }
        }catch (SQLException e){
            logger.error(e);
            System.err.println("Error DB"+e);
        }
        logger.traceExit(rezervari);
        return rezervari;
    }
}
