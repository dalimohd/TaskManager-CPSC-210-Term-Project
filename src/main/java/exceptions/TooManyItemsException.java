package exceptions;

public class TooManyItemsException extends Exception{
    public TooManyItemsException(String msg){
        super(msg);
    }
}
