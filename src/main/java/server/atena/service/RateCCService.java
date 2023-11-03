package server.atena.service;

import org.springframework.beans.factory.annotation.Autowired;

public class RateCCService {
	
    private final RateCCService rateCCService;

    //@Autowired
    public RateCCService(RateCCService rateCCService) {
        this.rateCCService = rateCCService;
    }

}