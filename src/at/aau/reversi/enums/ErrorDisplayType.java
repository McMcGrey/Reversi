package at.aau.reversi.enums;

/**
 * How to display an error
 */
public enum ErrorDisplayType {
    /**
     * Display an inline error
     */
    INLINE,
    /**
     * Use a popup to display the error
     */
    POPUP,
    /**
     * Should be used when the network connection was lost
     */
    NETWORK,
    /**
     * Is used to handle Information about the game to the observers
     */
    PROGRAM_FLOW
}

