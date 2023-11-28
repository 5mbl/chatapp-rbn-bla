import java.io.Serializable;

public class PeerInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private String ipAddress;

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public PeerInfo(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getIpAddress() {
		return ipAddress;
	}

}
