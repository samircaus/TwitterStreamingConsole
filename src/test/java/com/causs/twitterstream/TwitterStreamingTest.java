package com.causs.twitterstream;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

public class TwitterStreamingTest {

	
	public final static String CONSUMER_KEY= "qqLwc0dEil4g4E5qpIdl5Q";
	public final static String CONSUMER_SECRET= "XI48pnfgWGweyhmkKQneTqGbDY7ZI8lvU7RFxtQoEb0";
	public final static String ACCESS_TOKEN= "12757272-mm5yXdKoLcMGsAe5IygMEdPFIOdTxwTJyALPUa7bt";
	public final static String TOKEN_SECRET= "ivIfT1eJjJgQLhMxGkiutQaCBPRX6S0BIobfg7lxfM";
	
	private final static Logger log = Logger.getLogger(TwitterStreamingTest.class); 
	
	@Test
	public void testHbc() throws InterruptedException {
		log.info("just testing oauth etc...");
		BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);
	    StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
	    // add some track terms
	    endpoint.trackTerms(Lists.newArrayList("twitterapi", "#yolo"));

	    Authentication auth = new OAuth1(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);
	    // Authentication auth = new BasicAuth(username, password);

	    // Create a new BasicClient. By default gzip is enabled.
	    Client client = new ClientBuilder()
	      .hosts(Constants.STREAM_HOST)
	      .endpoint(endpoint)
	      .authentication(auth)
	      .processor(new StringDelimitedProcessor(queue))
	      .build();

	    // Establish a connection
	    client.connect();

	    // Do whatever needs to be done with messages
	    for (int msgRead = 0; msgRead < 100; msgRead++) {
	      String msg = queue.take();
	      System.out.println(msg);
	    }

	    client.stop();
	}
}
