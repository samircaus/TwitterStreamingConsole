package com.causs.twitter.streaming;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

@Service
public class TwitterStreamingService {

	private final static Logger log = Logger.getLogger(TwitterStreamingService.class); 
	
	public final static String CONSUMER_KEY= "qqLwc0dEil4g4E5qpIdl5Q";
	public final static String CONSUMER_SECRET= "XI48pnfgWGweyhmkKQneTqGbDY7ZI8lvU7RFxtQoEb0";
	public final static String ACCESS_TOKEN= "12757272-mm5yXdKoLcMGsAe5IygMEdPFIOdTxwTJyALPUa7bt";
	public final static String TOKEN_SECRET= "ivIfT1eJjJgQLhMxGkiutQaCBPRX6S0BIobfg7lxfM";

	
	private BlockingQueue<String> queue;
	private StatusesFilterEndpoint endpoint;
	private Authentication auth;
	private Client client;
	
	
	
	public TwitterStreamingService() {
		log.info("Initializing twitter streaming service");
		queue = new LinkedBlockingQueue<String>();
		endpoint = new StatusesFilterEndpoint();
		auth = new OAuth1(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);
		// Authentication auth = new BasicAuth(username, password);
	}
	
	private void resetClient(){
		if (client != null){
			client.stop();
		}
		queue.clear();
		//reinitialize client with new keywords...
		client = new ClientBuilder()
		.hosts(Constants.STREAM_HOST)
		.endpoint(endpoint)
		.authentication(auth)
		.processor(new StringDelimitedProcessor(queue))
		.build();
		
	}

	
	
	public void setKeywords(String keywords){
		log.info("Changing keywords to " + keywords);
		endpoint.trackTerms(Lists.newArrayList(keywords.split(",")));
		resetClient();
		
	}
	
	public void start(){
		log.debug("Starting twitter streaming");
		client.connect();
	}
	
	public void stop(){
		log.debug("Stopping twitter streaming");
		client.stop();
	}
	
	public void reconnect(){
		log.debug("client reconnect");
		client.reconnect();
	}
	
	public String getMessage() throws InterruptedException{
		return queue.take();
	}
	
}
