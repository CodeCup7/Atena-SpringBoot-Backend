package server.atena.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class RateBlock {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String key;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "rateBlock_id")
	private List<RatePart> ratePart;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<RatePart> getRatePart() {
		return ratePart;
	}

	public void setRatePart(List<RatePart> ratePart) {
		this.ratePart = ratePart;
	}

}
