package at.aau.reversi.network;

import at.aau.reversi.enums.NetworkPackageType;

/**
 * Created with IntelliJ IDEA.
 * User: phil
 * Date: 06.05.13
 * Time: 17:20
 * To change this template use File | Settings | File Templates.
 */
public class NetworkPackage {

    protected NetworkPackageType type;

    public NetworkPackage() {
    }

    public NetworkPackage(NetworkPackageType type) {
        this.type = type;
    }

    public NetworkPackageType getType() {
        return type;
    }

    public void setType(NetworkPackageType type) {
        this.type = type;
    }
}
