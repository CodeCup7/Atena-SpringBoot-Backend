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
import server.atena.app.enums.FeedbackType;

@Entity
public class Feedback {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	@JoinColumn(name = "agent_id")
	@JsonProperty("agent")
	private User agent;
	private String dateFeedback;
	@Enumerated(EnumType.STRING)
	private FeedbackType feedback;

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

	public String getDateFeedback() {
		return dateFeedback;
	}

	public void setDateFeedback(String dateFeedback) {
		this.dateFeedback = dateFeedback;
	}

	public FeedbackType getFeedback() {
		return feedback;
	}

	public void setFeedback(FeedbackType feedback) {
		this.feedback = feedback;
	}

}
