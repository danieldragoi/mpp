
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.List;


public class ClientRpcReflectionWorker implements Runnable, Observer {
    private IService service;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;
    public ClientRpcReflectionWorker(IService server, Socket connection) {
        this.service = server;
        this.connection = connection;
        try{
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            connected=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(connected){
            try {
                Object request=input.readObject();
                Response response=handleRequest((Request)request);
                if (response!=null){
                    sendResponse(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error "+e);
        }
    }


    private Response handleRequest(Request request){
        Response response=null;
        String handlerName = "handle" + (request).type();
        System.out.println("HandlerName " + handlerName);
        try {
            Method method = this.getClass().getDeclaredMethod(handlerName, Request.class);
            response = (Response)method.invoke(this,request);
            System.out.println("Method "+ handlerName + " invoked");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return response;
    }


    private Response handleLOGIN(Request request){
        System.out.println("Login request ..." + request.type());
        Oficiu oficiu = DTOUtils.getFromDTO((OficiuDTO)request.data());

        try {
            Oficiu of = service.login(oficiu, this);
            return  new Response.Builder().type(ResponseType.OK).data(DTOUtils.getDTO(of)).build();
        } catch (MyException e) {
            connected=false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleLOGOUT(Request request){
        System.out.println("Logout request...");
        String username = (String) request.data();

        try {
            service.logout(username);
            connected=false;
            return new Response.Builder().type(ResponseType.OK).build();

        } catch (Exception e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleGET_CURSE(Request request){
            System.out.println("Get curse ...");

            try {
                List<CursaDTO> curse = service.getCurse();
                Cursa[] curseArray = new Cursa[curse.size()];
                int i = 0;
                for(CursaDTO cursaDTO : curse){
                    Cursa cursa = cursaDTO.getCursa();
                    curseArray[i] = cursa;
                    i++;
                }
                return new Response.Builder().type(ResponseType.GET_CURSE).data(DTOUtils.getDTO(curseArray)).build();
            } catch (MyException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }


    }

    private Response handleADD_REZERVARI(Request request){
        System.out.println("Add rezervari ...");
        Rezervare[] rezervari = DTOUtils.getFromDTO((RezervareDTO[])request.data());
        try {
            service.addRezervari(rezervari);
            return new Response.Builder().type(ResponseType.OK).build();
        } catch (MyException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }


    private void sendResponse(Response response) throws IOException{
        System.out.println("sending response "+ response);
        output.writeObject(response);
        output.flush();
    }

    @Override
    public void doUpdate(){
        Response resp = new Response.Builder().type(ResponseType.UPDATE).build();
        System.out.println("Send update response");
        try {
            //doUpdate();
            sendResponse(resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
