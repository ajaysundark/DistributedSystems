public class TransferRequest extends Request {
    private int fromAcc;
    private int toAcc;
    private int amount;

    public TransferRequest (int _fromAcc, int _toAcc, int _amount) {
        super(MessageType.Transfer, acc);
        this.fromAcc = _fromAcc;
        this.toAcc = _toAcc;
        this.amount = _amount;
    }

    public int transferFrom() {return fromAcc;}

    public int transferTo() {return toAcc;}

    public int getAmountToTransfer() {return amount;}


    public String toString() {
        StringBuilder sb = new StringBuilder("Request::Transfer ");
        sb.append(" : from account number : ").append(this.transferFrom())
                .append(" : to account number : ").append(this.transferTo())
                .append(" : $").append(this.getAmountToTransfer());
        return sb.toString();
    }
}

