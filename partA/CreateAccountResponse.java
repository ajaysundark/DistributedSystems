public class CreateAccountResponse extends Response {
    private int accountNumber;
    public CreateAccountResponse(int accountCreated) {
        super(accountCreated>0);
        accountNumber = accountCreated;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Response::CreateAccount ");
        sb.append(" : account number ").append(this.getAccountNumber()).append(" : ").append(super.getStatusString());
        return sb.toString();
    }
}
