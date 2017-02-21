public abstract class Request {
	private MessageType msgType;

	protected Request(MessageType _operation) {
		msgType = _operation;
	}

	public MessageType getMessageType() {
		return msgType;
	}
}