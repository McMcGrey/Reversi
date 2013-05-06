package at.aau.reversi.network;

import at.aau.reversi.constants.Constants;
import at.aau.reversi.controller.ReversiController;
import at.aau.reversi.enums.NetworkPackageType;
import com.google.gson.Gson;
import com.sun.org.apache.bcel.internal.classfile.ConstantString;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

/**
 * Created with IntelliJ IDEA.
 * User: phil
 * Date: 06.05.13
 * Time: 14:09
 * To change this template use File | Settings | File Templates.
 */
public class Gameserver implements Observer, Runnable{

    private ReversiController controller;
    private Socket client;
    private ServerSocket server;
    private boolean isRunning = true;
    private boolean logging = true;
    private HalloTimeCounter halloTimeCounter;
    private Gson gson = new Gson();
    private ObjectInputStream input;
    private ObjectOutputStream output;

    public Gameserver(ReversiController controller) throws IOException {

        this.controller = controller;

        server = new ServerSocket(Constants.SERVER_PORT);
        server.setSoTimeout(Constants.SERVER_CONNECT_TIMEOUT);
        // Wait for client connection
        client = server.accept();

        client.setSoTimeout(Constants.SERVER_TIMEOUT);

        if(Constants.LOGGING) {
            System.out.println("Verbindung akzeptiert.");
        }
        Thread t = new Thread(this);
        t.start();

    }

    @Override
    public void update(Observable o, Object arg) {

        Gson gson = new Gson();
        String message = gson.toJson(arg);

        if(logging){
            System.out.println(message);
        }

    }

    @Override
    public void run() {
        try {

            halloTimeCounter = new HalloTimeCounter();
            output = new ObjectOutputStream(client.getOutputStream());
            output.flush();
            input = new ObjectInputStream(client.getInputStream());

            while(isRunning){

                if(halloTimeCounter.isTimeExceeded()){
                    if(Constants.LOGGING) {
                        System.out.println("SERVER - Send Hallo Package");
                    }
                    sendHalloPackage();
                    if(Constants.LOGGING) {
                        System.out.println("SERVER - Hallo Package sent successful");
                    }
                    halloTimeCounter.reset();
                }


                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    private void sendHalloPackage() throws IOException, ClassNotFoundException {


        output.flush();
        output.writeObject(gson.toJson(new NetworkPackage(NetworkPackageType.REQUEST)));
        output.flush();

        String message = (String) input.readObject();
        NetworkPackage response = gson.fromJson(message, NetworkPackage.class);

        if(!response.getType().equals(NetworkPackageType.RESPONSE)){
            throw new RuntimeException("Test");
        }

    }
}
