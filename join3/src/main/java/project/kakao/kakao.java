package project.kakao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class kakao {
	
	
	private final static String ID = "369b216cafcc89156aa8d4dd04ab8675";
	private final static String URI = "http://localhost/join3/kakao_login_ok.do";

    public static String getAuthorizationUrl(HttpSession session) {
		System.out.println("kakao - getAuthorizationUrl");

      String kakaoUrl = "https://kauth.kakao.com/oauth/authorize?"
          + "client_id=" + ID + "&redirect_uri="
          + URI + "&response_type=code";
      System.out.println("kakaoUrl - " + kakaoUrl);
      return kakaoUrl;
    }
    
    public static JsonNode getAccessToken(String autorize_code) {
    	
    	final String RequestUrl = "https://kauth.kakao.com/oauth/token";
    	final List<NameValuePair> postParams = new ArrayList<NameValuePair>();
    	postParams.add(new BasicNameValuePair("grant_type", "authorization_code"));
    	postParams.add(new BasicNameValuePair("client_id", ID));
    	// REST API KEY
    	postParams.add(new BasicNameValuePair("redirect_uri", URI));
    	// 리다이렉트 URI
    	postParams.add(new BasicNameValuePair("code", autorize_code));
    	// 로그인 과정중 얻은 code 값
    	final HttpClient client = HttpClientBuilder.create().build();
    	final HttpPost post = new HttpPost(RequestUrl);
    	JsonNode returnNode = null;
    	
	    	try { post.setEntity(new UrlEncodedFormEntity(postParams));
	    	final HttpResponse response = client.execute(post);
		    	// JSON 형태 반환값 처리
		    	ObjectMapper mapper = new ObjectMapper();
		    	returnNode = mapper.readTree(response.getEntity().getContent());
	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        } catch (ClientProtocolException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	        }
	 
	        return returnNode;
    	}
    
    public static JsonNode getKakaoUserInfo(String accesscode) {
    	
    	final String RequestUrl = "https://kapi.kakao.com/v2/user/me";

	    String CLIENT_ID = ID; // REST API KEY
	    String REDIRECT_URI = URI; // 리다이렉트 URI
	    String code = accesscode; // 로그인 과정중 얻은 토큰 값


	    final HttpClient client = HttpClientBuilder.create().build();
	    final HttpPost post = new HttpPost(RequestUrl);

	    // add header

	    post.addHeader("Authorization", "Bearer " + accesscode);
	    JsonNode returnNode = null;

	    try {
	      final HttpResponse response = client.execute(post);
	      final int responseCode = response.getStatusLine().getStatusCode();

	      System.out.println("\nSending 'POST' request to URL : " + RequestUrl);
	      System.out.println("Response Code : " + responseCode);

	      //JSON 형태 반환값 처리
	      ObjectMapper mapper = new ObjectMapper();
	      returnNode = mapper.readTree(response.getEntity().getContent());

	    } catch (UnsupportedEncodingException e) {
	      e.printStackTrace();
	    } catch (ClientProtocolException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    } finally {
	        // clear resources
	    }
	    return returnNode;

	}

}


    



