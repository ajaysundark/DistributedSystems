public enum	MessageType {
	Create(1), Deposit(2), Balance(3), Transfer(4);

	private int type;

	private MessageType (int _type) {
		this.type = _type;
	}

	public int getType() {
		return type;
	}

	public static MessageType fromInt (int intType) {
		for (MessageType m : MessageType.values()) {
			if (m.getType() == intType) { return m; }
		}
	}
}