package server.atena.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import server.atena.app.TypeRateCC;

@Entity
public class RateCC {

	private TypeRateCC typeRateCC;
	private long mode;

	private long id_note;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private double rate;
	@OneToOne
	@JoinColumn(name = "agent_id") // Tworzy kolumnę "agent_id" w tabeli do przechowywania klucza obcego
	private User agent;
	@OneToOne
	@JoinColumn(name = "coach_id")
	private User coach;
	private String dateRate;
	private String dateCall;
	private String dateShare;
	private String idCall;
	@OneToOne
	@JoinColumn(name = "queue_id")
	private Queue queueId;
	private String topic;
	private long extraScore;
	private String extraScoreTxt;
	private String ratePartJSON;
	@OneToOne
	@JoinColumn(name = "wiedzaBlock_Id")
	private RateBlock wiedzaBlock_Id;
	@OneToOne
	@JoinColumn(name = "obslugaBlock_Id")
	private RateBlock obslugaBlock_Id;
	@OneToOne
	@JoinColumn(name = "technikaBlock_Id")
	private RateBlock technikaBlock_Id;
	@OneToOne
	@JoinColumn(name = "komunikacjaBlock_Id")
	private RateBlock komunikacjaBlock_Id;
	@OneToOne
	@JoinColumn(name = "standardBlock_Id")
	private RateBlock standardBlock_Id;

	public TypeRateCC getTypeRateCC() {
		return typeRateCC;
	}

	public void setTypeRateCC(TypeRateCC typeRateCC) {
		this.typeRateCC = typeRateCC;
	}

	public long getMode() {
		return mode;
	}

	public void setMode(long mode) {
		this.mode = mode;
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

	public long getAgentId() {
		return agentId;
	}

	public void setAgentId(long agentId) {
		this.agentId = agentId;
	}

	public long getCoachId() {
		return coachId;
	}

	public void setCoachId(long coachId) {
		this.coachId = coachId;
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

	public long getQueueId() {
		return queueId;
	}

	public void setQueueId(long queueId) {
		this.queueId = queueId;
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

	public String getRatePartJSON() {
		return ratePartJSON;
	}

	public void setRatePartJSON(String ratePartJSON) {
		this.ratePartJSON = ratePartJSON;
	}

	public long getWiedzaBlockId() {
		return wiedzaBlockId;
	}

	public void setWiedzaBlockId(long wiedzaBlockId) {
		this.wiedzaBlockId = wiedzaBlockId;
	}

	public long getObslugaBlockId() {
		return obslugaBlockId;
	}

	public void setObslugaBlockId(long obslugaBlockId) {
		this.obslugaBlockId = obslugaBlockId;
	}

	public long getTechnikaBlockId() {
		return technikaBlockId;
	}

	public void setTechnikaBlockId(long technikaBlockId) {
		this.technikaBlockId = technikaBlockId;
	}

	public long getKomunikacjaBlockId() {
		return komunikacjaBlockId;
	}

	public void setKomunikacjaBlockId(long komunikacjaBlockId) {
		this.komunikacjaBlockId = komunikacjaBlockId;
	}

	public long getStandardBlockId() {
		return standardBlockId;
	}

	public void setStandardBlockId(long standardBlockId) {
		this.standardBlockId = standardBlockId;
	}

}
