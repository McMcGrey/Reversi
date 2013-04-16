package at.aau.reversi.bean;

import at.aau.reversi.enums.ErrorDisplayType;

/**
 * The error bean will be send from the GameController to all Observers when
 * a problem occurs. This could be for example: Invalid Move, 
 * Network connection Timeout,...
 * @author phil
 *
 */
public class ErrorBean {

	/**
	 * The errormessage which should be displayed
	 */
	private String errorMessage;
	/**
	 * how the message should be displayed. See at Constants
	 */
	private ErrorDisplayType errorDisplayType;
	
	public ErrorBean(String errorMessage, ErrorDisplayType errorDisplayType) {
		super();
		this.errorMessage = errorMessage;
		this.errorDisplayType = errorDisplayType;
	}

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ErrorDisplayType getErrorDisplayType() {
        return errorDisplayType;
    }

    public void setErrorDisplayType(ErrorDisplayType errorDisplayType) {
        this.errorDisplayType = errorDisplayType;
    }
}
