package G_exceptions;

public class InvalidLoginException extends Exception {

    public InvalidLoginException() {
        System.out.println("Invalid login, please try again.");
    }
}
