public class Response {
	public static final int FAIL = -1;
	public static final int SUCCESS = 1;

	private int status;

	public Response(boolean _status) {
		this.status = _status ? SUCCESS : FAIL;
	}

	public int getStatus() {
		return status;
	}

	public String getStatusString() {
		return (status>=0) ? "OK" : "FAIL";
	}
}