import utils.AbstractServer;
import utils.ServerException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class StartServer {
    private static int defaultPort=55555;
    public static void main(String[] args) {
        // UserRepository userRepo=new UserRepositoryMock();
        Properties serverProps=new Properties();
        try {
            serverProps.load(StartServer.class.getResourceAsStream("/service.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatserver.properties "+e);
            return;
        }
        RepoCurse repoCurse = new RepoCurse(serverProps);
        List<Rezervare> rezervari = new ArrayList<>();
        //repoCurse.add(new Cursa("Destinatie3", LocalDateTime.now(), rezervari));

        RepoRezervari repoRezervari = new RepoRezervari(serverProps);
        RepoOficiu repoOficiu = new RepoOficiu(serverProps);

        IServiceAll service = new IServiceAll(repoOficiu, repoCurse, repoRezervari);

        int chatServerPort=defaultPort;
        try {
            chatServerPort = Integer.parseInt(serverProps.getProperty("chat.server.port"));
        }catch (NumberFormatException nef){
            System.err.println("Wrong  Port Number"+nef.getMessage());
            System.err.println("Using default port "+defaultPort);
        }
        System.out.println("Starting server on port: "+chatServerPort);
        AbstractServer server = new RpcConcurrentServer(chatServerPort, service);
        try {
            server.start();
        } catch (ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());
        }
    }
}
