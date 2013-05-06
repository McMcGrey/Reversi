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

    private NetworkPackageType type;
    private Object data;

    public NetworkPackage() {
    }

    public NetworkPackage(NetworkPackageType type) {
        this.type = type;
    }

    public NetworkPackage(NetworkPackageType type, Object data) {
        this.type = type;
        this.data = data;
    }

    public NetworkPackageType getType() {
        return type;
    }

    public void setType(NetworkPackageType type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
