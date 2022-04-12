import javax.swing.text.Utilities;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class ServicesRpcProxy implements IService {
    private String host;
    private int port;

    private Observer client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;
    public ServicesRpcProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses=new LinkedBlockingQueue<Response>();
    }

    private void closeConnection() {
        finished=true;
        try {
            input.close();
            output.close();
            connection.close();
            client=null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendRequest(Request request)throws MyException {
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new MyException("Error sending object " + e);
        }

    }

    private Response readResponse() throws MyException {
        Response response=null;
        try{

            response=qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }
    private void initializeConnection() throws MyException {
        try {
            connection=new Socket(host,port);
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            finished=false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void startReader(){
        Thread tw=new Thread(new ReaderThread());
        tw.start();
    }


    private void handleUpdate(Response response){
        if (response.type()== ResponseType.UPDATE){

            System.out.println("doUpdate ... ");
            try {
                this.client.doUpdate();

            } catch (MyException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isUpdate(Response response){
        return response.type() == ResponseType.UPDATE;
    }

    @Override
    public Oficiu login(Oficiu oficiu, Observer observer) throws MyException {
        initializeConnection();
        //UserDTO udto= DTOUtils.getDTO(user);
        OficiuDTO ofDTO = DTOUtils.getDTO(oficiu);
        Request req=new Request.Builder().type(RequestType.LOGIN).data(ofDTO).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.OK){
            this.client = observer;
            Oficiu of = DTOUtils.getFromDTO((OficiuDTO) response.data());
            return of;
        }
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            closeConnection();
            throw new MyException(err);
        }
        return null;
    }

    @Override
    public void logout(String username) throws MyException {
        Request req=new Request.Builder().type(RequestType.LOGOUT).data(username).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.OK){
            closeConnection();
        }
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            closeConnection();
            throw new MyException(err);
        }
    }


    public int getNrLocuriLibereAici(Cursa cursa) {
        int nr=18;
        return 18 - cursa.getRezervari().size();
    }

    @Override
    public List<CursaDTO> getCurse() throws MyException {
        //UserDTO udto= DTOUtils.getDTO(user);

        Request req=new Request.Builder().type(RequestType.GET_CURSE).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new MyException(err);
        }
        CurseDTO[] frDTO=(CurseDTO[])response.data();
        Cursa[] curse= DTOUtils.getFromDTO(frDTO);

        List<CursaDTO> curseDTO = new ArrayList<>();
        for(int i=0; i<curse.length; i++){
            CursaDTO cursaDTO = new CursaDTO(curse[i], getNrLocuriLibereAici(curse[i]));
            curseDTO.add(cursaDTO);
        }

        return curseDTO;
    }

    @Override
    public void addRezervari(Rezervare[] rezervari) throws MyException {
        RezervareDTO[] rezervariDTO = DTOUtils.getDTO(rezervari);
        Request req = new Request.Builder().type(RequestType.ADD_REZERVARI).data(DTOUtils.getDTO(rezervari)).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR){
            String err = response.data().toString();
            throw new MyException(err);
        }
    }

    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    Object response=input.readObject();
                    System.out.println("response received "+response);
                    if (isUpdate((Response)response)){
                        handleUpdate((Response)response);
                    }else{

                        try {
                            qresponses.put((Response)response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error "+e);
                } catch (ClassNotFoundException e) {
                    System.out.println("Reading error "+e);
                }
            }
        }
    }
}
