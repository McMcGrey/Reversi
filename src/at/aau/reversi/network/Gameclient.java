package at.aau.reversi.network;

import at.aau.reversi.constants.Constants;
import at.aau.reversi.enums.NetworkPackageType;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: phil
 * Date: 06.05.13
 * Time: 17:53
 * To change this template use File | Settings | File Templates.
 */
public class Gameclient implements Runnable {

    private InetAddress server;
    private Socket connection;
    private Gson gson = new Gson();
    private boolean running = true;


    public Gameclient(InetAddress server) throws IOException {
        this.server = server;

        connection = new Socket(server, Constants.SERVER_PORT);

        new Thread(this).start();
    }

    @Override
    public void run() {
        try {

            ObjectOutputStream output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            ObjectInputStream input = new ObjectInputStream(connection.getInputStream());


            while(running){

                    if(Constants.LOGGING) {
                        System.out.println("CLIENT - WAITING FOR HALLO");
                    }

                    String message = (String) input.readObject();
                    NetworkPackage request = gson.fromJson(message, NetworkPackage.class);

                    if(request.getType().equals(NetworkPackageType.REQUEST)){
                        output.writeObject(gson.toJson(new NetworkPackage(NetworkPackageType.RESPONSE)));
                        output.flush();
                    }

                    if(Constants.LOGGING) {
                        System.out.println("CLIENT - HALLO RESPONSE SENT");
                    }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }


        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }
}
