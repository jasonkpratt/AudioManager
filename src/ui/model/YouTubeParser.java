package ui.model;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import ui.UI_Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;

public class YouTubeParser implements UI_Constants {

	private final String USER_AGENT = "Mozilla/5.0";
	private List<SearchResultData> imageList=new ArrayList<SearchResultData>();

	public List<SearchResultData> startParser(String searchString) throws Exception {
		imageList.clear();
		String parsed=searchString.replace(" ", "+");
		String url = "https://www.youtube.com/results?search_query="+parsed;
		HttpGet request = new HttpGet(url);

		// add request header
		request.addHeader("User-Agent", USER_AGENT);
		try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
			
			HttpResponse response = client.execute(request);

			//System.out.println("\nSending 'GET' request to URL : " + url);
			//System.out.println("Response Code : " +
		                      // response.getStatusLine().getStatusCode());

			BufferedReader rd = new BufferedReader(
          new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";

			while ((line = rd.readLine()) != null) {
				//if(line.contains("watch?v=")||line.contains("playlist?list"))
					result.append(line);
				}

			Pattern p = Pattern.compile("watch[?]v=(\\w+).*?dir=[\"]ltr[\"]>(.[^<]*).*?(("+REGEX_VIDEO_LENGTH+")|(playlist[?]list=(.[^\"]*)))", Pattern.MULTILINE );   // the pattern to search for
			Matcher m = p.matcher(result);
			String temp="";
			int count=1;	
			// if we find a match, get the group 
		  while (m.find())
		  {
	    //sometimes the same video id is found in the html, so ignore duplicates
	    if(!temp.equals(m.group(1))){
	    	String videoId=m.group(1);
	    		
	    	//remove extra characters in title
	    	String fullTitle=(m.group(2)).replaceAll("&#39;", "");
	    	String fullTitle2=fullTitle.replaceAll("&quot;", "");
	    	String fullTitle3=fullTitle2.replaceAll("&amp;", "");
	    	String parsedTitle=parseTitle(fullTitle3);
	    
	    	
	    	//video length could be the length or could return a playlist url
	    	String videoLength=m.group(3);
	    	//System.out.println("video length is "+videoLength);
	    	URL imgURL = new URL("http://img.youtube.com/vi/"+videoId+"/mqdefault.jpg");
	    	
	    	SearchResultData searchResult=new SearchResultData();
	    	searchResult.setData(videoLength);
	    	searchResult.setImageIconPath(imgURL);
	    	searchResult.setVideoId(videoId);
	    	//searchResult.setLength(videoLength);
	    	searchResult.setfullTitle(fullTitle2);
	    	searchResult.setParsedTitle(parsedTitle);
	    	imageList.add(searchResult);
	    	count++;
	    }
	    temp=m.group(1);
	   }
		    
		 // Pattern p2=Pattern.compile("(watch[?]v=\\w+).*?title=[\"](.[^\"]*)", Pattern.MULTILINE );
		//System.out.println("Finished search, total videos found is "+count+"\n "+result);
				
		} 
		catch (IOException e) {

		}
		return imageList;
	}
//********************************************************************************************************** 
	
	String parseTitle(String title){
		String parsedTitle=title.replaceAll("\\(.*\\)","");
		return parsedTitle;
	}
	
		
}
