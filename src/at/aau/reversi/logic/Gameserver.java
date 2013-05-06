package at.aau.reversi.logic;

import at.aau.reversi.bean.ErrorBean;
import at.aau.reversi.bean.GameBean;
import at.aau.reversi.constants.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: phil
 * Date: 06.05.13
 * Time: 14:09
 * To change this template use File | Settings | File Templates.
 */
public class Gameserver implements Observer{

    Socket client;

    public Gameserver() throws IOException {

        ServerSocket socket = new ServerSocket(Constants.SERVER_PORT);
        socket.setSoTimeout(Constants.SERVER_TIMEOUT);
        // Wait for client connection
        client = socket.accept();

        System.out.println("Verbindung akzeptiert.");

    }

    private Stack eventStack = new Stack();
    private boolean isRunning = true;

    @Override
    public void update(Observable o, Object arg) {

        Gson gson = new Gson();
        String message = gson.toJson(arg);

        System.out.println(message);


    }
}
