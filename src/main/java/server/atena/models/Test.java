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
import server.atena.app.enums.TestPass;

@Entity
public class Test {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	@JoinColumn(name = "agent_id")
	@JsonProperty("agent")
	private User agent;
	private String dateTest;
	private long score;
	private String progress;
	private long levelPass;
	@Enumerated(EnumType.STRING)
	private TestPass testPass;

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

	public String getDateTest() {
		return dateTest;
	}

	public void setDateTest(String dateTest) {
		this.dateTest = dateTest;
	}

	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public long getLevelPass() {
		return levelPass;
	}

	public void setLevelPass(long levelPass) {
		this.levelPass = levelPass;
	}

	public TestPass getTestPass() {
		return testPass;
	}

	public void setTestPass(TestPass testPass) {
		this.testPass = testPass;
	}

}
