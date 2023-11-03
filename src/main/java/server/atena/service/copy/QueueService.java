package server.atena.service.copy;

import org.springframework.beans.factory.annotation.Autowired;

public class QueueService {
	
    private final QueueService queueService;

   // @Autowired
    public QueueService(QueueService queueService) {
        this.queueService = queueService;
    }

}
