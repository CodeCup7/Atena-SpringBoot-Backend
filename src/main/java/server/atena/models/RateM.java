package server.atena.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import server.atena.app.enums.RateMode;

@Entity
public class RateM {

	@Enumerated(EnumType.STRING)
	private RateMode mode;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "noteCC_id")
	private NoteCC noteCC;

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
	private String dateShare;
	private long extraScore;
	private String extraScoreTxt;
	private String attachmentPath;

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

	public NoteCC getNoteCC() {
		return noteCC;
	}

	public void setNoteCC(NoteCC noteCC) {
		this.noteCC = noteCC;
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

	public String getDateShare() {
		return dateShare;
	}

	public void setDateShare(String dateShare) {
		this.dateShare = dateShare;
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

	public String getAttachmentPath() {
		return attachmentPath;
	}

	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
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
