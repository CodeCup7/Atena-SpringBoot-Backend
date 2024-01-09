package server.atena.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import server.atena.app.StatusNote;

@Entity
public class NoteCC {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private StatusNote status;
	@ManyToOne
	@JoinColumn(name = "agent_id")
	@JsonProperty("agent")
	private User agent;
	@ManyToOne
	@JoinColumn(name = "coach_id")
	@JsonProperty("coach")
	private User coach;
	private String coachDate;
	private String appliesDate;
	private String zalecenia;
	private String odwoladnie;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "note", orphanRemoval = true)
	private List<RateCC> rateCC_list;

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

	public User getCoach() {
		return coach;
	}

	public void setCoach(User coach) {
		this.coach = coach;
	}

	public String getCoachDate() {
		return coachDate;
	}

	public void setCoachDate(String coachDate) {
		this.coachDate = coachDate;
	}

	public String getAppliesDate() {
		return appliesDate;
	}

	public void setAppliesDate(String appliesDate) {
		this.appliesDate = appliesDate;
	}

	public String getZalecenia() {
		return zalecenia;
	}

	public void setZalecenia(String zalecenia) {
		this.zalecenia = zalecenia;
	}

	public String getOdwoladnie() {
		return odwoladnie;
	}

	public void setOdwoladnie(String odwoladnie) {
		this.odwoladnie = odwoladnie;
	}

	public List<RateCC> getRateCC_list() {
		return rateCC_list;
	}

	public void setRateCC_list(List<RateCC> rateCC_list) {
		this.rateCC_list = rateCC_list;
	}

	public StatusNote getStatus() {
		return status;
	}

	public void setStatus(StatusNote status) {
		this.status = status;
	}

}
