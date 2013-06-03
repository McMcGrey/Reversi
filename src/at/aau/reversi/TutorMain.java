package at.aau.reversi;

import at.aau.reversi.bean.Move;
import at.aau.reversi.controller.ReversiController;
import at.aau.reversi.enums.AIType;
import at.aau.reversi.enums.Field;
import at.aau.reversi.enums.Player;
import at.aau.reversi.enums.PlayerType;
import at.aau.reversi.gui.Game_Field;
import at.aau.reversi.logic.GameLogic;
import at.aau.reversi.logic.GameLogicLocalImpl;
import at.aau.reversi.logic.ai.AI;
import at.aau.reversi.logic.ai.EvaporationAIImpl;
import at.aau.reversi.tutor.RemoteReversiStub;

import java.util.Map;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: phil
 * Date: 29.05.13
 * Time: 16:38
 * To change this template use File | Settings | File Templates.
 */
public class TutorMain {

    RemoteReversiStub tutorlib = new RemoteReversiStub(RemoteReversiStub.SERVER_PUBLIC_V2);
    Scanner scanner = new Scanner(System.in);
    RemoteReversiStub.GameStatus currentState;
    AI ai = new EvaporationAIImpl();
    GameLogicLocalImpl logic = new GameLogicLocalImpl();

    public static void main(String[] args) {

        TutorMain m = new TutorMain();

        System.out.println("Welcome to Reversi");
        System.out.println("------------------");
        char command = m.getStartCommand();

        Player player = null;

        if (command == 'n') {

            // Create a new game and join it
            m.startNewGame();
            player = Player.WHITE;


        } else if (command == 'j') {

            // List all games and ask which to join
            m.listAllGames();
            m.joinGame();
            player = Player.BLACK;

        }

        m.waitingForConnection();

        m.playGame();

    }

    public void playGame(){

        while(!(currentState == RemoteReversiStub.GameStatus.WIN
                || currentState == RemoteReversiStub.GameStatus.LOSE)){

            if(currentState == RemoteReversiStub.GameStatus.MYTURN){

                System.out.println("Its my Turn!");

                tutorlib.printField();

                Move m = ai.calcNextStep(convertFields(), getColor() ,0);
                System.out.println("Set brick to "+m.getxCoord()+", "+m.getyCoord());

                tutorlib.setBrick(m.getxCoord(), m.getyCoord());
                getStatus();
            }else{

                System.out.println(currentState);
            }

        }

    }

    public void waitingForConnection(){
        System.out.println("Waiting for oponent to connect");
        while(getStatus().equals(RemoteReversiStub.GameStatus.WAITING_FOR_OPPONENT)){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Error at waitingForConnection()");
                System.exit(0);
            }
        }
        System.out.println("Oponent connected");
    }

    public RemoteReversiStub.GameStatus getStatus(){

        RemoteReversiStub.GameStatus status = tutorlib.getStatus();
        currentState = status;
        return status;
    }

    public char getStartCommand() {
        System.out.println("Type:");
        System.out.println("[N]ew Game");
        System.out.println("[J]oin Game");
        String command = scanner.next();
        command = command.toLowerCase();
        while (!(command.startsWith("n") || command.startsWith("j"))) {
            System.out.println("Invalid command!");
            command = scanner.next();
            command.toLowerCase();
        }
        return command.charAt(0);
    }

    public void startNewGame() {

        System.out.print("Name of the game: ");
        String name = scanner.next();

        int id = tutorlib.startGame(name);
        boolean success = tutorlib.joinGame(id);
        if (success) {

            System.out.println("Joined new Game with name " + name + " and ID " + id);

        } else {
            System.out.println("Error at joining game - EXIT");
            System.exit(0);
        }


    }

    public void joinGame() {

        System.out.print("Please type ID of the game to join: ");
        int gameId = scanner.nextInt();

        boolean success = tutorlib.joinGame(gameId);
        if (success) {

            System.out.println("Joined Game with ID " + gameId);

        } else {
            System.out.println("Error at joining game - EXIT");
            System.exit(0);
        }

    }

    public void listAllGames() {

        System.out.println("List of available games:");

        Map<Integer, String> games = tutorlib.getAvailableGames();
        for (Map.Entry<Integer, String> game : games.entrySet()) {

            System.out.println(game.getKey() + ": " + game.getValue());

        }

    }

    public Field[][] convertFields(){
        String[][] tutorfield = tutorlib.getField();
        Field[][] gameField = new Field[8][8];

        for(int i=0;i<tutorfield.length;i++){
            for(int z=0;z<tutorfield[0].length;z++){
                if(tutorfield[i][z].equals(" ")){
                    gameField[i][z] = Field.EMPTY;
                }else if(tutorfield[i][z].equals("w")){
                    gameField[i][z] = Field.WHITE;
                }else if(tutorfield[i][z].equals("b")){
                    gameField[i][z] = Field.BLACK;
                }
            }
        }

        logic.setGameField(gameField);

        for(int i=0;i<8;i++){
            for(int z=0;z<8;z++){
                if(gameField[i][z]==null){
                    gameField[i][z]= Field.EMPTY;
                }
            }
        }

        for(int i=0;i<8;i++){
            for(int z=0;z<8;z++){
                if(logic.validMove((short)i,(short)z, getColor())){
                    gameField[i][z] = Field.MAYBE;
                }
            }
        }

        return gameField;
    }

    public Field getColor(){
        if(tutorlib.getCurrentColor().equals("white")){
            return Field.WHITE;
        }
        return Field.BLACK;
    }
}
