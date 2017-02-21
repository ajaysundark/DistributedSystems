public class DepositResponse extends Response {
    public DepositResponse(int _status) {
        super(_status);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Response::Deposit ");
        sb.append(super.getStatusString());
        return sb.toString();
    }
}
