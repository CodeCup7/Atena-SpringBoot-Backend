package server.atena.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import server.atena.app.enums.NotificationMode;
import server.atena.app.enums.NotificationType;

@Entity
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	@JoinColumn(name = "agent_id")
	@JsonProperty("agent")
	private User agent;
	@Enumerated(EnumType.STRING)
	private NotificationType type;
	@Enumerated(EnumType.STRING)
	private NotificationMode mode;
	private String text;
	private long previewId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getAgent() {
		return agent;
	}

	public void setAgent(User agent) {
		this.agent = agent;
	}

	public NotificationType getType() {
		return type;
	}

	public void setType(NotificationType type) {
		this.type = type;
	}

	public NotificationMode getMode() {
		return mode;
	}

	public void setMode(NotificationMode mode) {
		this.mode = mode;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public long getPreviewId() {
		return previewId;
	}

	public void setPreviewId(long previewId) {
		this.previewId = previewId;
	}

}
