package com.causs.twitter.streaming;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class TwitterStreamingThread extends Thread {
	
	private final static Logger log = Logger
			.getLogger(TwitterStreamingThread.class);

	@Autowired
	TwitterStreamingService twitterStreaming;

	private AtomicBoolean pause = new AtomicBoolean(false);
	private AtomicBoolean running = new AtomicBoolean(true);

	public void run() {
		try {
			log.info("Started thread");
			while(running.get()){
				if (!pause.get()){
//					System.out.println(twitterStreaming.getMessage());
					log.info(twitterStreaming.getMessage());
				}
			}
		} catch (Exception e) {
			log.error("Error ",e);
		}
	}
	
    @Override
    public void interrupt() {
    	log.debug("interrupt");
        super.interrupt();
        running.set(false);
    }
    
    public void pause(){
    	log.debug("pause!");
    	pause.set(true);
    }
    
    public void unpause(){
    	log.debug("unpause!");
    	pause.set(false);
    }
}
