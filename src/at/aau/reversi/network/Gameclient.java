package at.aau.reversi.network;

import at.aau.reversi.bean.ErrorBean;
import at.aau.reversi.bean.GameBean;
import at.aau.reversi.bean.Move;
import at.aau.reversi.constants.Constants;
import at.aau.reversi.controller.ReversiController;
import at.aau.reversi.enums.ErrorDisplayType;
import at.aau.reversi.enums.Field;
import at.aau.reversi.enums.NetworkPackageType;
import at.aau.reversi.logic.GameLogic;
import at.aau.reversi.logic.GameLogicAbstract;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: phil
 * Date: 06.05.13
 * Time: 17:53
 * To change this template use File | Settings | File Templates.
 */
public class Gameclient extends GameLogicAbstract implements Runnable, GameLogic {

    private InetAddress server;
    private Socket connection;
    private Gson gson = new Gson();
    private boolean running = true;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    ReversiController controller;
    private Field[][] gameFieldBuffer;


    public Gameclient(InetAddress server, ReversiController controller) throws IOException {
        gameField = new Field[8][8];
        for (Field[] row : gameField) {
            Arrays.fill(row, Field.EMPTY);
        }
        gameField[3][3] = Field.WHITE;
        gameField[4][4] = Field.WHITE;
        gameField[4][3] = Field.BLACK;
        gameField[3][4] = Field.BLACK;

        this.server = server;
        this.controller = controller;

        connection = new Socket(server, Constants.SERVER_PORT);
        connection.setSoTimeout(Constants.SERVER_TIMEOUT);

        new Thread(this).start();
    }

    @Override
    public void run() {
        try {

            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());


            while(running){

                try{
                    String message = (String) input.readObject();
                    NetworkPackage request = gson.fromJson(message, NetworkPackage.class);

                    if(request.getType().equals(NetworkPackageType.GAME_BEAN)){

                        if(Constants.LOGGING) {
                            System.out.println("CLIENT - Got GameBean");
                        }
                        GamebeanPackage gamebeanPackage = gson.fromJson(message, GamebeanPackage.class);
                        gameFieldBuffer = gamebeanPackage.getBean().getGameField();
                        try {
                            Thread.sleep(0);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        controller.notifyControllerAtClientGame(gamebeanPackage.getBean());

                    }

                }  catch(SocketTimeoutException ex){
                    // The socket timeout exception occurs when nothing was send, ignore this in this case
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

    @Override
    public Field[][] calcNewGameField(short xCoord, short yCoord, Field color) {

        if(Constants.LOGGING) {
            System.out.println("CLIENT - A Field was clicked");
        }

        gameFieldBuffer = null;

        Move m = new Move(xCoord, yCoord);
        NetworkPackage request = new MovePackage( m);

        try {
            output.writeObject(gson.toJson(request));
            output.flush();

            while(gameFieldBuffer == null){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }

            }
            return gameFieldBuffer;

        } catch (IOException e) {

            sendErrorMessageToController();
        }


        return new Field[0][];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean turnStones(int xCoord, int yCoord, Field color, int w) {
        //fixme: is method necessary?
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Field endGame() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private void sendErrorMessageToController(){
        controller.sendErrorMessageToObservers(new ErrorBean("Fehler bei Netzwerkverbindung", ErrorDisplayType.POPUP));
    }
}
