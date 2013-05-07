package at.aau.reversi.network;

import at.aau.reversi.bean.GameBean;
import at.aau.reversi.enums.NetworkPackageType;

/**
 * Created with IntelliJ IDEA.
 * User: phil
 * Date: 06.05.13
 * Time: 20:23
 * To change this template use File | Settings | File Templates.
 */
public class GamebeanPackage extends NetworkPackage {

    protected GameBean bean;

    public GamebeanPackage(GameBean bean) {
        super(NetworkPackageType.GAME_BEAN);
        this.bean = bean;
    }

    public GameBean getBean() {
        return bean;
    }

    public void setBean(GameBean bean) {
        this.bean = bean;
    }
}
