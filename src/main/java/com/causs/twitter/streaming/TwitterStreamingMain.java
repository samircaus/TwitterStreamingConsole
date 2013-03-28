package com.causs.twitter.streaming;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class TwitterStreamingMain {

	
	private final static Logger log = Logger.getLogger(TwitterStreamingMain.class); 
	
	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext("com.causs.twitter.streaming");
		TwitterStreamingConsole console = ctx.getBean(TwitterStreamingConsole.class);
		console.startConsole();
	}
}
