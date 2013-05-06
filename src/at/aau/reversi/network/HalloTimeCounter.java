package at.aau.reversi.network;

import at.aau.reversi.constants.Constants;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: phil
 * Date: 06.05.13
 * Time: 17:25
 * To change this template use File | Settings | File Templates.
 */
public class HalloTimeCounter {

    private Date startDate;
    private int waitTime;

    public HalloTimeCounter() {
        this.startDate = new Date();
        this.waitTime = Constants.SERVER_HALLO_TIME;
    }

    public HalloTimeCounter(int waitTime) {
        this.startDate = new Date();
        this.waitTime = waitTime;
    }

    public void reset(){
        startDate = new Date();
    }

    public boolean isTimeExceeded(){
        return new Date().getTime()>(startDate.getTime()+ Constants.SERVER_HALLO_TIME);
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}