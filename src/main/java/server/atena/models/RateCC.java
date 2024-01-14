package server.atena.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import server.atena.app.TypeRateCC;

@Entity
public class RateCC {
	
	@ManyToOne
	@JoinColumn(name="note_id")
    private NoteCC note;
	
	@Enumerated(EnumType.STRING)
	private TypeRateCC typeRate;

	private long id_note;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private double rate;
	@ManyToOne
	@JoinColumn(name = "agent_id")
	@JsonProperty("agent")
	private User agent;
	@ManyToOne
	@JoinColumn(name = "coach_id")
	@JsonProperty("coach")
	private User coach;
	private String dateRate;
	private String dateCall;
	private String dateShare;
	private String idCall;
	@ManyToOne
	@JoinColumn(name = "queue_id")
	@JsonProperty("queue")
	private Queue queue;
	private String topic;
	private long extraScore;
	private String extraScoreTxt;
	
	

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "rate", orphanRemoval = true)
	@JsonManagedReference
	private List<RatePart> ratePart;

	public List<RatePart> getRatePart() {
		return ratePart;
	}

	public void setRatePart(List<RatePart> ratePart) {
		this.ratePart = ratePart;
	}

	public TypeRateCC getTypeRate() {
		return typeRate;
	}

	public void setTypeRate(TypeRateCC typeRate) {
		this.typeRate = typeRate;
	}

	public long getId_note() {
		return id_note;
	}

	public void setId_note(long id_note) {
		this.id_note = id_note;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
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

	public String getDateRate() {
		return dateRate;
	}

	public void setDateRate(String dateRate) {
		this.dateRate = dateRate;
	}

	public String getDateCall() {
		return dateCall;
	}

	public void setDateCall(String dateCall) {
		this.dateCall = dateCall;
	}

	public String getDateShare() {
		return dateShare;
	}

	public void setDateShare(String dateShare) {
		this.dateShare = dateShare;
	}

	public String getIdCall() {
		return idCall;
	}

	public void setIdCall(String idCall) {
		this.idCall = idCall;
	}

	public Queue getQueue() {
		return queue;
	}

	public void setQueue(Queue queue) {
		this.queue = queue;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public long getExtraScore() {
		return extraScore;
	}

	public void setExtraScore(long extraScore) {
		this.extraScore = extraScore;
	}

	public String getExtraScoreTxt() {
		return extraScoreTxt;
	}

	public void setExtraScoreTxt(String extraScoreTxt) {
		this.extraScoreTxt = extraScoreTxt;
	}

	public NoteCC getNote() {
		return note;
	}

	public void setNote(NoteCC note) {
		this.note = note;
	}
	
	

}
