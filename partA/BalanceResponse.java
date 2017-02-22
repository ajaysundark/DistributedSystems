public class BalanceResponse extends Response {
    private int balance;
    public BalanceResponse(int _balance) {
        super(_balance>=0);
        balance = _balance;
    }

    public int getAccountBalance() {
        return balance;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Response::Balance ");
        sb.append(super.getStatusString());
        return sb.toString();
    }
}

