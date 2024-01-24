package server.atena.models;

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
import jakarta.persistence.OneToOne;
import server.atena.app.RateMode;
import server.atena.app.TypeRateCC;

@Entity
public class RateCC {

	@Enumerated(EnumType.STRING)
	private TypeRateCC typeRate;
	
	@Enumerated(EnumType.STRING)
	private RateMode mode;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wiedzaBlock_id")
    private RateBlock wiedzaBlock;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "obslugaBlock_id")
    private RateBlock obslugaBlock;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "technikaBlock_id")
    private RateBlock technikaBlock;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "komunikacjaBlock_id")
    private RateBlock komunikacjaBlock;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "standardBlock_id")
    private RateBlock standardBlock;

	public RateMode getMode() {
		return mode;
	}

	public void setMode(RateMode mode) {
		this.mode = mode;
	}


	public TypeRateCC getTypeRate() {
		return typeRate;
	}

	public void setTypeRate(TypeRateCC typeRate) {
		this.typeRate = typeRate;
	}

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

	public RateBlock getWiedzaBlock() {
		return wiedzaBlock;
	}

	public void setWiedzaBlock(RateBlock wiedzaBlock) {
		this.wiedzaBlock = wiedzaBlock;
	}

	public RateBlock getObslugaBlock() {
		return obslugaBlock;
	}

	public void setObslugaBlock(RateBlock obslugaBlock) {
		this.obslugaBlock = obslugaBlock;
	}

	public RateBlock getTechnikaBlock() {
		return technikaBlock;
	}

	public void setTechnikaBlock(RateBlock technikaBlock) {
		this.technikaBlock = technikaBlock;
	}

	public RateBlock getKomunikacjaBlock() {
		return komunikacjaBlock;
	}

	public void setKomunikacjaBlock(RateBlock komunikacjaBlock) {
		this.komunikacjaBlock = komunikacjaBlock;
	}

	public RateBlock getStandardBlock() {
		return standardBlock;
	}

	public void setStandardBlock(RateBlock standardBlock) {
		this.standardBlock = standardBlock;
	}



}
