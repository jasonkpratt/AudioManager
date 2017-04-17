package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ui.UI_Constants;

public class Test implements UI_Constants {

	public static void main(String[] args) {
		String result="true>1:55:35</j";
		Pattern p = Pattern.compile(".*?("+REGEX_VIDEO_LENGTH+")");
		Matcher m = p.matcher(result);
	  while (m.find())
	  {	    	String videoId=m.group(1);
	  System.out.println("FOund it "+videoId);
	  }
	  
	}

}
