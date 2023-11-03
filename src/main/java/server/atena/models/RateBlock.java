package server.atena.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RateBlock {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String key;


	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}


}
