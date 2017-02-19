public abstract class Request {
	private MessageType msgType;

	protected Request(MessageType _operation) {
		msgType = _operation;
	}

	public MessageType getMessageType() {
		return msgType;
	}
}

public class NewAccountCreationRequest extends Request {
	super(MessageType.Create);
}

public class DepositRequest extends Request {
	private int acc;
	private int fund;

	super(MessageType.Deposit);

	public DepositRequest (int _acc, int _fund) {
		this.acc = _acc;
		this.fund = _fund;
	}

	public int getAccountNumber() {return acc;}
	public int getFundToDeposit() {return fund;}
}

public class BalanceRequest extends Request {
	private int acc;

	super(MessageType.Balance);

	public BalanceRequest (int _acc) {this.acc = _acc;}
	public int getAccountNumber() {return acc;}
}

public class TransferRequest extends Request {
	private int fromAcc;
	private int toAcc;

	super(MessageType.Transfer, acc);

	public TransferRequest (int _fromAcc, int _toAcc) {this.fromAcc = _fromAcc; this.fromAcc = _toAcc;}

	public int transferFrom() {return fromAcc;}

	public int transferTo() {return toAcc;}
}

