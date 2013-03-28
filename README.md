TwitterStreamingConsole
=======================

Simple Twitter Streaming Command Line Client
-------------------------

This is just a simple demo command line client that will call Twitter Streaming Api https://dev.twitter.com/docs/streaming-apis 
The request will be made for https://dev.twitter.com/docs/api/1.1/post/statuses/filter call, with input parameters - keywords, read from command line.
The program uses Hosebird Client (hbc) https://github.com/twitter/hbc

-------------------------
run "mvn exec:java" in console to start app.

TODO
#move configs to .properties file
#unit tests