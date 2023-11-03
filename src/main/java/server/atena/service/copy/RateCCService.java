package server.atena.service.copy;

import org.springframework.beans.factory.annotation.Autowired;

public class RateCCService {
	
    private final RateCCService rateCCService;

    //@Autowired
    public RateCCService(RateCCService rateCCService) {
        this.rateCCService = rateCCService;
    }

}