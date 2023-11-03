package server.atena.controller;

import org.springframework.beans.factory.annotation.Autowired;

public class RateCCController {
	
    private final RateCCController rateCCController;

    @Autowired
    public RateCCController(RateCCController rateCCController) {
        this.rateCCController = rateCCController;
    }

}
