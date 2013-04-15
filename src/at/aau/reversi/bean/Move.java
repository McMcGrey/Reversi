package at.aau.reversi.bean;

public class Move {

	private short xCoord;
	private short yCoord;

    public Move(){}

    public Move(short xCoord, short yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    /**
	 * @return the xCoord
	 */
	public short getxCoord() {
		return xCoord;
	}
	/**
	 * @param xCoord the xCoord to set
	 */
	public void setxCoord(short xCoord) {
		this.xCoord = xCoord;
	}
	/**
	 * @return the yCoord
	 */
	public short getyCoord() {
		return yCoord;
	}
	/**
	 * @param yCoord the yCoord to set
	 */
	public void setyCoord(short yCoord) {
		this.yCoord = yCoord;
	}
	
}
