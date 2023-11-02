package server.atena.models;

import java.util.List;

public class RateBlock {

	private String key;
	private List<RatePart> ratePartCol;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<RatePart> getRatePartCol() {
		return ratePartCol;
	}

	public void setRatePartCol(List<RatePart> ratePartCol) {
		this.ratePartCol = ratePartCol;
	}

}
