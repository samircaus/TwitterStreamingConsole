package com.causs.twitter.streaming;

import java.io.Console;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class TwitterStreamingConsole {
	
	@Autowired
	TwitterStreamingService twitterStreaming;
	
    @Autowired
    TwitterStreamingThread outputThread;

	private final static Logger log = Logger
			.getLogger(TwitterStreamingConsole.class);

	private Console console = getConsole();

	private Console getConsole() {
		Console console = System.console();
		if (console == null) {
			log.error("Error: no console.");
			System.exit(1);
		}
		return console;
	}

	public void startConsole() {
		try{
			//show first time message
			displayInfo(console);
			String initialKeywords = getNewKeywords(console);
			twitterStreaming.setKeywords(initialKeywords);
			
			start();
	
			while (true) {
				console.readLine("");
	
				outputThread.pause();
				Thread.sleep(1000);
				
				String userInput = getUserInput(console);
				if (userInput.equalsIgnoreCase("1")){
					String keywords = getNewKeywords(console);
					twitterStreaming.setKeywords(keywords);
					twitterStreaming.start();
				}else if (userInput.equalsIgnoreCase("2")){
					stop();
					break;
				}
				
				outputThread.unpause();
			}
		
		}catch (Exception e) {
			log.error("Error ", e);
		}
		

	}
	
	private void start(){
		//starts twitter streaming service & thread 
		log.info("Starting up...");
		twitterStreaming.start();
		outputThread.start();
	}
	
	private void stop() {
		log.info("Shutting down...");
        if (!outputThread.isInterrupted()) {
        	outputThread.interrupt();
        }
        outputThread = null;
        
        twitterStreaming.stop();
    }
    

	private void displayInfo(Console console) {
		log.info("---------------------------------------------------------");
		log.info("Hello, this is Twitter Streaming console client!");
		log.info("Press Enter to pause/unpause!");
	}
	
	private String getUserInput(Console console) {
		log.info("---------------------------------------------------------");
		log.info("Please enter command:");
		log.info("1)Change filter keywords");
		log.info("2)Exit application");
		log.info("---------------------------------------------------------");
		String userInput = console.readLine(">>>>>");
		return userInput;
	}
	
	private String getNewKeywords(Console console) {
		log.info("---------------------------------------------------------");
		log.info("Please enter comma separated list of new keywords:");
		log.info("---------------------------------------------------------");
		String userInput = console.readLine(">>>>>");
		return userInput;
	}
}
