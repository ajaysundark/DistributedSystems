import java.io.Serializable;

public abstract class Request implements Serializable {
	private MessageType msgType;

	protected Request(MessageType _operation) {
		msgType = _operation;
	}

	public MessageType getMessageType() {
		return msgType;
	}
}