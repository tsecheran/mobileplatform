package com.example.simpletwitter;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.FlickrApi;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.example.simpletwitter.models.User;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class SimpleTwtrClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
    //public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
    //public static final String REST_CONSUMER_KEY = "Qn74yvEHjdG93RpxJjupQAL2m"; //"Qn74yvEHjdG93RpxJjupQAL2m";       // Change this
    //public static final String REST_CONSUMER_SECRET = "BV56PmSuyMLJYpasSGnwmz4zewBbU6UTrxyMaLUV8mvVOOK0fU"; //"BV56PmSuyMLJYpasSGnwmz4zewBbU6UTrxyMaLUV8mvVOOK0fU"; // Change this
    //public static final String REST_CALLBACK_URL = "oauth://cpbasictweets"; // Change this (here and in manifest)
   
    public static final String REST_URL = "https://api.twitter.com/1.1"; //"http://api.flickr.com/services"; // Change this, base API URL
    public static final String REST_CONSUMER_KEY = "hbdKRWpnEcAo6zhsaXBSxFXin";       // Change this
    public static final String REST_CONSUMER_SECRET = "lRWJDP8bsHySFu8hVeSeAKKrBc3EhqIB89tpFsFmu9cc53KJJ2"; // Change this
    public static final String REST_CALLBACK_URL = "oauth://cpbasictweets"; // Change this (here and in manifest)
    
    public static int sinceId = 0;
    public static int maxId = 0;
    
    public SimpleTwtrClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }
    
    public void getHomeTimeLine(AsyncHttpResponseHandler respHandler) {
    	String url = getApiUrl("statuses/home_timeline.json");
    	RequestParams params = new RequestParams();
    	params.put("since_id", "1");
    	client.get(url, params, respHandler);
    }
    
    public void getHomeTimeLine(AsyncHttpResponseHandler respHandler, int sinceId, long maxId) {
    	String url = getApiUrl("statuses/home_timeline.json");
    	RequestParams params = new RequestParams();
    	if(sinceId > 0) {
    		params.put("since_id", String.valueOf(sinceId));
    	} else {
    		params.put("since_id", "1");
    	}
    	
    	if(maxId > 0) {
    		params.put("max_id", String.valueOf(maxId));
    	} 
    	
    	client.get(url, params, respHandler);
    }
    
    public void getCurrentUserDetails(AsyncHttpResponseHandler respHandler) {
    	String url = "users/show.json"; //?screen_name=tsecheran";
    	RequestParams params = new RequestParams();
        params.put("screen_name", "tsecheran");
    	client.get(url, params, respHandler);
    }
    
    public void postTweet(String body, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status", body);
        client.post(apiUrl, params, handler);
    }
    
    public void getMentionsTimeLine(AsyncHttpResponseHandler respHandler) {
    	String url = getApiUrl("statuses/mentions_timeline.json");
    	RequestParams params = new RequestParams();
    	params.put("since_id", "1");
    	client.get(url, params, respHandler);
    }
    
    public void getMyInfo(AsyncHttpResponseHandler respHandler) {
    	String url = getApiUrl("account/verify_credentials.json");
    	client.get(url, null, respHandler);
    }
    
    public void getUserInfo (AsyncHttpResponseHandler handler, String screenName) {
		String apiURL = getApiUrl("users/show.json");
		RequestParams params = new RequestParams();
		params.put("screen_name", screenName);
		client.get(apiURL, params, handler);
	}
    
    public void getUserTimeLine(AsyncHttpResponseHandler respHandler) {
    	String url = getApiUrl("statuses/user_timeline.json");
    	client.get(url, null, respHandler);
    }
    
    public void getUserTimeLine(AsyncHttpResponseHandler respHandler, User user) {
    	String url = getApiUrl("statuses/user_timeline.json");
    	RequestParams params = new RequestParams();
    	params.put("screen_name", user.getScreenName());
    	client.get(url, params, respHandler);
    }
    
    // CHANGE THIS
    // DEFINE METHODS for different API endpoints here
    /**public void getInterestingnessList(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("format", "json");
        client.get(apiUrl, params, handler);
    }*/
    
    /* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
     * 	  i.e getApiUrl("statuses/home_timeline.json");
     * 2. Define the parameters to pass to the request (query or body)
     *    i.e RequestParams params = new RequestParams("foo", "bar");
     * 3. Define the request method and make a call to the client
     *    i.e client.get(apiUrl, params, handler);
     *    i.e client.post(apiUrl, params, handler);
     */
}