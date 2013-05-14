package at.aau.reversi.test;

import java.util.Observable;
import java.util.Observer;

import at.aau.reversi.bean.ErrorBean;
import at.aau.reversi.bean.GameBean;
import at.aau.reversi.bean.Move;
import at.aau.reversi.controller.ReversiController;
import at.aau.reversi.enums.AIType;
import at.aau.reversi.enums.ErrorDisplayType;
import at.aau.reversi.enums.Field;
import at.aau.reversi.enums.Player;
import at.aau.reversi.enums.PlayerType;
import at.aau.reversi.exceptions.InvalidInputException;
import at.aau.reversi.gui.Game_Field;
import at.aau.reversi.logic.GameLogic;
import at.aau.reversi.logic.GameLogicLocalImpl;
import cucumber.annotation.de.Angenommen;
import cucumber.annotation.de.Dann;
import cucumber.annotation.de.Wenn;
import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;
import junit.framework.Assert;

/*
 * Created with IntelliJ IDEA.
 * User: phil
 * Date: 11.04.13
 * Time: 16:09
 * To change this template use File | Settings | File Templates.
 */


public class LogicTestStepDefinitions implements Observer {

    ReversiController controller;
    GameLogic logic = new GameLogicLocalImpl();
    ErrorBean error;

    @Angenommen("^es existiert ein neues Einzelspielerspiel$")
    public void es_existiert_ein_neues_Einzelspielerspiel() {

        controller = new ReversiController();

//        controller.startGame(PlayerType.HUMAN_PLAYER, PlayerType.AI, AIType.AI_GREEDY, AIType.AI_GREEDY, false);

        // Start Gui
        Game_Field gui = new Game_Field(controller);
        gui.getFrame().setVisible(true);
        controller.addObserver(gui);
        controller.addObserver(this);

        gui.testStartSinglePlayer();

        sleep();
    }

    @Dann("^sollte ein \"([^\"]*)\" Stein auf \"([^\"]*)\" liegen$")
    public void sollte_ein_Stein_auf_liegen(String arg1, String arg2) {
        es_sollte_ein_Stein_auf_liegen(arg1, arg2);
    }

    @Dann("^es sollte ein \"([^\"]*)\" Stein auf \"([^\"]*)\" liegen$")
    public void es_sollte_ein_Stein_auf_liegen(String color, String fieldname) {

        Field[][] array = controller.getGameBean().getGameField();

        Field field;
        if(color.equals("schwarzer"))  {
            field = Field.BLACK;
        }else{
            field = Field.WHITE;
        }

        try {
            Move m = logic.getMoveFromInputstring(fieldname);

            Assert.assertTrue("Feld "+fieldname+ " sollte "+ color+" sein",array[m.getxCoord()][m.getyCoord()].equals(field));

        } catch (InvalidInputException e) {

        }

        sleep();

    }

    @Wenn("^ich einen \"([^\"]*)\" Stein auf \"([^\"]*)\" lege$")
    public void ich_einen_Stein_auf_lege(String color, String field) {

        Player player;
        if(color.equals("schwarzer"))  {
            player = Player.BLACK;
        }else{
            player = Player.WHITE;
        }

        try {
            Move m = logic.getMoveFromInputstring(field);
            controller.fieldClicked(player, m.getxCoord(), m.getyCoord());
        } catch (InvalidInputException e) {

        }

        sleep();

    }

    @Dann("^sollte ich \"([^\"]*)\" als Fehler bekommen$")
    public void sollte_ich_als_Fehler_bekommen(String errormessage) {
    	
    	
    	sleep();
    	
    	if(error != null){
    		Assert.assertTrue("Falsche ...", errormessage.equals(error.getErrorMessage()));
    	}else{
    		Assert.assertTrue(false);
    	}
//    	Player player = null;
//    	GameBean gameBean = null;
//    	if (!player.equals(gameBean.getCurrentPlayer()) && gameBean.isGameFieldActive()){
//    	controller.notifyObservers(new ErrorBean("Ungueltiger Zug", ErrorDisplayType.INLINE));
//    	
//    	}
    }

    @Dann("^es sollte kein Stein auf \"([^\"]*)\" liegen$")
    public void es_sollte_kein_Stein_auf_liegen(String position) {

        Field[][] array = controller.getGameBean().getGameField();

        try {
            Move m = logic.getMoveFromInputstring(position);

            Assert.assertTrue("Feld "+position+ " sollte leer sein",array[m.getxCoord()][m.getyCoord()].equals(Field.EMPTY));

        } catch (InvalidInputException e) {

        }

        sleep();
    }

    private void sleep(){
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

	@Override
	public void update(Observable arg0, Object arg1) {

		if(arg1 instanceof ErrorBean){
			error = (ErrorBean)arg1;
		}
		
	}

}
