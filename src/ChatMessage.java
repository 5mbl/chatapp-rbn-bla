import java.io.Serializable;

public class ChatMessage implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String content;
	
	public ChatMessage( String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

}
