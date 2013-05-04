package pl.edu.agh.to1.dice.logic;

public class GameLogicException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public GameLogicException(String message) {
        super(message);
    }

    public GameLogicException(String message, Throwable cause) {
        super(message, cause);
    }

    public GameLogicException(Throwable cause) {
        super(cause);
    }

    public GameLogicException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
	
}
