public class BalanceRequest extends Request {
    private int acc;

    public BalanceRequest (int _acc) {
        super(MessageType.Balance);
        this.acc = _acc;
    }

    public int getAccountNumber() {return acc;}

    public String toString() {
        StringBuilder sb = new StringBuilder("Request::Balance ");
        sb.append(" : account number : ").append(this.getAccountNumber());
        return sb.toString();
    }
}
