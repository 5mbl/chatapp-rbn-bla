import java.io.Serializable;
import java.util.List;

public class RegistrationResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private boolean success;
    private List<PeerInfo> registeredPeers;

    public RegistrationResponse(boolean success, List<PeerInfo> registeredPeers) {
        this.success = success;
        this.registeredPeers = registeredPeers;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<PeerInfo> getRegisteredPeers() {
        return registeredPeers;
    }

    public void setRegisteredPeers(List<PeerInfo> registeredPeers) {
        this.registeredPeers = registeredPeers;
    }
}
