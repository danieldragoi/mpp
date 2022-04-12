import utils.AbsConcurrentServer;

import java.net.Socket;


public class RpcConcurrentServer extends AbsConcurrentServer {
    private IService server;
    public RpcConcurrentServer(int port, IService chatServer) {
        super(port);
        this.server = chatServer;
        System.out.println("RpcConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        ClientRpcReflectionWorker worker=new ClientRpcReflectionWorker(server, client);

        Thread tw=new Thread(worker);
        return tw;
    }

    @Override
    public void stop(){
        System.out.println("Stopping services ...");
    }
}
