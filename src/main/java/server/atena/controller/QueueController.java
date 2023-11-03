package server.atena.controller;

import org.springframework.beans.factory.annotation.Autowired;

public class QueueController {
	
    private final QueueController queueController;

    @Autowired
    public QueueController(QueueController queueController) {
        this.queueController = queueController;
    }



}
