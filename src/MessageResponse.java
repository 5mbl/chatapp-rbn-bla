import java.io.Serializable;

public class MessageResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private boolean success;

    public MessageResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
