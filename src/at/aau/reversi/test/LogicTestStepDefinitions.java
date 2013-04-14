package at.aau.reversi.test;

import cucumber.annotation.de.Angenommen;
import cucumber.annotation.de.Dann;
import cucumber.annotation.de.Wenn;
import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;

/*
 * Created with IntelliJ IDEA.
 * User: phil
 * Date: 11.04.13
 * Time: 16:09
 * To change this template use File | Settings | File Templates.
 */


public class LogicTestStepDefinitions {

    @Angenommen("^es existiert ein neues Einzelspielerspiel$")
    public void es_existiert_ein_neues_Einzelspielerspiel() {
    }

    @Dann("^sollte ein \"([^\"]*)\" Stein auf \"([^\"]*)\" liegen$")
    public void sollte_ein_Stein_auf_liegen(String arg1, String arg2) {
    }

    @Dann("^es sollte ein \"([^\"]*)\"E(\\d+)\" liegen$")
    public void es_sollte_ein_E_liegen(String arg1, int arg2) {
    }

    @Dann("^es sollte ein \"([^\"]*)\" Stein auf \"([^\"]*)\" liegen$")
    public void es_sollte_ein_Stein_auf_liegen(String arg1, String arg2) {
    }

    @Wenn("^ich einen \"([^\"]*)\" Stein auf \"([^\"]*)\" lege$")
    public void ich_einen_Stein_auf_lege(String arg1, String arg2) {
    }

    @Dann("^sollte ich \"([^\"]*)\" als Fehler bekommen$")
    public void sollte_ich_als_Fehler_bekommen(String arg1) {
    }

}
