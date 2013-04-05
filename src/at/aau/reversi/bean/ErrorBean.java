package at.aau.reversi.bean;

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
	private short errorDisplayType;
	
	public ErrorBean(String errorMessage, short errorDisplayType) {
		super();
		this.errorMessage = errorMessage;
		this.errorDisplayType = errorDisplayType;
	}
	
}
