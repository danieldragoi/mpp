
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RepoOficiu implements RepositoryOficiu{
    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public RepoOficiu(Properties props) {
        logger.info("Initializing RepoCurse with properties: {} ",props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public void add(Oficiu elem) {
        logger.traceEntry("saving task {}", elem);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("insert into Oficii (nume, adresa, username, password) values (?, ?, ?, ?)")){
            preStmt.setString(1, elem.getNume());
            preStmt.setString(2, elem.getAdresa());
            preStmt.setString(3, elem.getUsername());
            preStmt.setString(4, elem.getPassword());
            int result = preStmt.executeUpdate();
            logger.trace("Saved {} instances", result);

        }catch(SQLException ex){
            logger.error(ex);
            System.err.println("Error DB" + ex);
        }
        logger.traceExit();
    }

    @Override
    public void delete(Oficiu elem) {
        logger.traceEntry("saving task {}", elem);
        try (Connection connection = dbUtils.getConnection()){

            PreparedStatement statement = connection.prepareStatement("delete from Oficii where id= ?");
            statement.setInt(1, elem.getId());
            int result = statement.executeUpdate();
            logger.trace("Saved {} instances", result);


        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error DB" + e);
        }
        logger.traceExit();

    }

    @Override
    public void update(Oficiu elem, Integer id) {
        logger.traceEntry("saving task {}", elem);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("update Oficii set nume=?, adresa=?, username=?, password=? where id=?")){
            preStmt.setString(1, elem.getNume());
            preStmt.setString(2, elem.getAdresa());
            preStmt.setString(3, elem.getUsername());
            preStmt.setString(4, elem.getPassword());
            preStmt.setInt(5, id);
            int result = preStmt.executeUpdate();
            logger.trace("Saved {} instances", result);

        }catch(SQLException ex){
            logger.error(ex);
            System.err.println("Error DB" + ex);
        }
        logger.traceExit();
    }

    @Override
    public Oficiu findById(Integer id) {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        Oficiu oficiu = null;
        try(PreparedStatement preStmt = con.prepareStatement("select * from Oficii where id=?")){
            preStmt.setInt(1, id);
            try(ResultSet result = preStmt.executeQuery()){
                if(result.next()) {
                    String nume = result.getString("nume");
                    String adresa = result.getString("adresa");
                    String username = result.getString("username");
                    String password = result.getString("password");

                    oficiu = new Oficiu(username, password, adresa, nume);
                }
            }
        }catch (SQLException e){
            logger.error(e);
            System.err.println("Error DB"+e);
        }
        logger.traceExit(oficiu);
        return oficiu;
    }

    @Override
    public List<Oficiu> getAll() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<Oficiu> oficii = new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Oficii")){
            try(ResultSet result = preStmt.executeQuery()){
                while(result.next()){
                    String nume = result.getString("nume");
                    String adresa = result.getString("adresa");
                    String username = result.getString("username");
                    String password = result.getString("password");

                    Oficiu oficiu = new Oficiu(username, password, adresa, nume);
                    oficii.add(oficiu);
                }
            }
        }catch (SQLException e){
            logger.error(e);
            System.err.println("Error DB"+e);
        }
        logger.traceExit(oficii);
        return oficii;
    }
}
