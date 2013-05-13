package at.aau.reversi.network;

import at.aau.reversi.bean.ErrorBean;
import at.aau.reversi.bean.GameBean;
import at.aau.reversi.constants.Constants;
import at.aau.reversi.controller.ReversiController;
import at.aau.reversi.enums.ErrorDisplayType;
import at.aau.reversi.enums.NetworkPackageType;
import at.aau.reversi.enums.Player;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
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
    private Gson gson = new Gson();
    private ObjectInputStream input;
    private ObjectOutputStream output;

    public Gameserver(ReversiController controller) throws IOException {

        this.controller = controller;

        server = new ServerSocket(Constants.SERVER_PORT);
        server.setSoTimeout(Constants.SERVER_CONNECT_TIMEOUT);
        // Wait for client connection
        try{
            client = server.accept();
        }catch(IOException e){
            throw e;
        }finally {
            server.close();
            if(Constants.LOGGING) {
                System.out.println("SERVER - No client Connected, BYE.");
            }
        }

        client.setSoTimeout(Constants.SERVER_TIMEOUT);

        if(Constants.LOGGING) {
            System.out.println("SERVER - Verbindung akzeptiert.");
        }
        Thread t = new Thread(this);
        t.start();

    }

    public void killServer(){
        if(Constants.LOGGING) {
            System.out.println("SERVER - Got Kill");
        }
        isRunning = false;
    }

    @Override
    public synchronized void update(Observable o, Object arg) {

        if(arg instanceof GameBean){

            GameBean bean = (GameBean)arg;
            GamebeanPackage gamebeanPackage = new GamebeanPackage(bean);

            try {
                if(Constants.LOGGING) {
                    System.out.println("SERVER - Write GameBean");
                }
                if(!client.isClosed()){
                    output.writeObject(gson.toJson(gamebeanPackage));
                    output.flush();
                }
            } catch (IOException e) {
                closeAndSendErrorMessageToController();
            }

        }else if(arg instanceof ErrorBean){

            if(Constants.LOGGING) {
                System.out.println("SERVER - Write Errorbean");
            }
            ErrorBean error = (ErrorBean)arg;
            ErrorBeanPackage errorBeanPackage = new ErrorBeanPackage(error);

            try {
                if(!client.isClosed()){
                    output.writeObject(gson.toJson(errorBeanPackage));
                    output.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
                closeAndSendErrorMessageToController();
            }

        }

    }

    @Override
    public void run() {

        try {

            output = new ObjectOutputStream(client.getOutputStream());
            output.flush();
            input = new ObjectInputStream(client.getInputStream());

            gotConnection();

            while(isRunning){

                // wait for client messages
                waitForClientEvent();

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }

            if(Constants.LOGGING) {
                System.out.println("SERVER - Started shutdown processs");
            }

            if(client.isConnected()){
                client.close();
            }
            server.close();

            if(Constants.LOGGING) {
                System.out.println("SERVER - BYE");
            }
        } catch (IOException e) {
            closeAndSendErrorMessageToController();
        } catch (ClassNotFoundException e) {
            closeAndSendErrorMessageToController();
        }

    }

    /**
     * Client events could be a field click
     */
    private void waitForClientEvent() throws IOException, ClassNotFoundException {

        try{
            String message = (String)input.readObject();
            NetworkPackage networkPackage = gson.fromJson(message, NetworkPackage.class);

            if(Constants.LOGGING) {
                System.out.println("SERVER - Got a request");
            }
            if(networkPackage.getType().equals(NetworkPackageType.MOVE))  {

                MovePackage movePackage = gson.fromJson(message, MovePackage.class);
                controller.fieldClicked(Player.BLACK, movePackage.getMove().getxCoord(), movePackage.getMove().getyCoord(), true);

            }

        }catch(SocketTimeoutException ex){
            // The socket timeout exception occurs when nothing was send, ignore this in this case
        }

    }

    private void closeAndSendErrorMessageToController(){

        if(Constants.LOGGING){
            System.out.println("SERVER - ERROR: Closing Connection");
        }
        this.killServer();
        try {
            if(client.isConnected()){
                client.close();
            }
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        controller.sendErrorMessageToObservers(new ErrorBean("Fehler bei Netzwerkverbindung", ErrorDisplayType.NETWORK));
    }

    private void gotConnection(){
        controller.sendErrorMessageToObservers(new ErrorBean("switchToGame", ErrorDisplayType.PROGRAM_FLOW));
    }
}
