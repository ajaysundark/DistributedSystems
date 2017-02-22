public class NewAccountCreationRequest extends Request {
    public NewAccountCreationRequest() {
        super(MessageType.Create);
    }

    public String toString() {
        return "Request::CreateAccount ";
    }
}
