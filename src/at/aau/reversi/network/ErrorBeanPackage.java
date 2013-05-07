package at.aau.reversi.network;

import at.aau.reversi.bean.ErrorBean;
import at.aau.reversi.enums.NetworkPackageType;

/**
 * Created with IntelliJ IDEA.
 * User: phil
 * Date: 07.05.13
 * Time: 19:30
 * To change this template use File | Settings | File Templates.
 */
public class ErrorBeanPackage extends NetworkPackage {

    ErrorBean bean;

    public ErrorBeanPackage(ErrorBean bean) {
        super(NetworkPackageType.ERROR_BEAN);
        this.bean = bean;
    }

    public ErrorBean getBean() {
        return bean;
    }

    public void setBean(ErrorBean bean) {
        this.bean = bean;
    }
}
