public class TransferResponse extends Response {
    public TransferResponse(int _status) {
        super(_status>0);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Response::Transfer ");
        sb.append(super.getStatusString());
        return sb.toString();
    }
}