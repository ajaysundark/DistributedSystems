public class DepositRequest extends Request {
    private int acc;
    private int fund;

    public DepositRequest (int _acc, int _fund) {
        super(MessageType.Deposit);
        this.acc = _acc;
        this.fund = _fund;
    }

    public int getAccountNumber() {return acc;}
    public int getFundToDeposit() {return fund;}

    public String toString() {
        StringBuilder sb = new StringBuilder("Request::Deposit ");
        sb.append(" : account number : ").append(this.getAccountNumber()).append(" : $").append(this.getFundToDeposit());
        return sb.toString();
    }
}