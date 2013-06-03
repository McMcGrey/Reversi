package at.aau.reversi;

import at.aau.reversi.controller.ReversiController;
import at.aau.reversi.enums.AIType;
import at.aau.reversi.enums.Player;
import at.aau.reversi.enums.PlayerType;
import at.aau.reversi.gui.Game_Field;
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

    RemoteReversiStub tutorlib = new RemoteReversiStub(RemoteReversiStub.SERVER_PUBLIC);
    Scanner scanner = new Scanner(System.in);

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

        ReversiController controller = new ReversiController();

        // Start Gui
        Game_Field gui = new Game_Field(controller);
        gui.getFrame().setVisible(true);
        controller.addObserver(gui);

        if(player.equals(Player.BLACK)){
            controller.startGame(PlayerType.TUTOR, PlayerType.AI, null, AIType.AI_GROUP, false);
        }else{
            controller.startGame(PlayerType.AI, PlayerType.TUTOR, AIType.AI_GROUP, null, false);
        }

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
}
