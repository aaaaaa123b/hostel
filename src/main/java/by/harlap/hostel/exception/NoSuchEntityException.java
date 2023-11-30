package by.harlap.hostel.exception;

public class NoSuchEntityException extends IllegalArgumentException{
    public NoSuchEntityException() {
        super();
    }

    public NoSuchEntityException(String s) {
        super(s);
    }
}
