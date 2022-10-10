/*
 * SlotFiller.java is added for Assignment 4 (Language Understanding)
 */

package chatbot.component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SlotFiller {

	/*
	 * Task 1: Extract slot values from the input user message
	 * 
	 * [Input] 
	 * One user message (e.g., "What's the weather in State College?")
	 * 
	 * [Output]
	 * A hash table that contains a set of (key, value) tuples, where the "key"
	 * is the name of the slot (e.g., "location") and "value" is the extracted
	 * value (e.g., "State College").
	 * 
	 */
	public Hashtable<String, String> extractSlotValues(String nowInputText) {
		
		//initialize the hash table. You do not need to change this line of code.
		Hashtable<String, String> result = new Hashtable<String, String>();
		
		//-------------- Modify Code Here (Assignment 4) Begins ---------------
		
		//modify the following code to implement your own slot extractor
		String[] dayOfWeekList = new String[] {"FRIDAY", "MONDAY", "SATURDAY", "SUNDAY", "THURSDAY", "TUESDAY", "WEDNESDAY"};
		for(String nowDayOfWeek: dayOfWeekList) {
			if(nowInputText.toUpperCase().contains(nowDayOfWeek)) {
				//adding value to the result hash table
				result.put("DayOfWeek", nowDayOfWeek);
			}
		}

		//modify the following code to implement your own slot extractor
		String[] relativeDateList = new String[] {"TODAY", "TOMORROW", "YESTERDAY"};
		for(String nowRelativeDate: relativeDateList) {
			if(nowInputText.toUpperCase().contains(nowRelativeDate)) {
				//adding value to the result hash table
				result.put("RelativeDate", nowRelativeDate);
			}
		}
		
		//Demo 1: Match key phrases with multiple words by normalizing input strings 
		//Tips: Tokenize and reconstruct the sentence using a "standard" way
		//Error Case: This example incorrect returns "LA" if you mentioned "LAs Vegas"
		/*String[] nowTokens = nowInputText.trim().toUpperCase().split("[\\s]+");
		String normalizedStr = "";
		for(String nowToken: nowTokens) {
			normalizedStr+=nowToken+" ";//add one space
		}
		normalizedStr = normalizedStr.trim();
		System.out.println("normalized input (Demo 1):"+normalizedStr);
		String[] locationList = new String[] {
			"LA", 
			"STATE COLLEGE",
			"NYC" 
		};
		for(String nowLocation: locationList) {
			if(normalizedStr.toUpperCase().contains(nowLocation)) {
				//adding value to the result hash table
				result.put("Location", nowLocation);
			}
		}
		*/
		
		//Demo 2: Match key phrases with multiple words using double for loop
		String[] nowInputWords = nowInputText.trim().toUpperCase().split("[\\W]+");
		String[] countryList = new String[] {
			"SOUTH KOREA", 
			"UNITED STATES OF AMERICA",
			"US",
			"KOREA",
			"CHINA",
			"JAPAN" 
		};
		for(String nowLocation: countryList) {
			String[] nowLocationWords = nowLocation.trim().toUpperCase().split("[\\W]+");
			//findPhrase() is our own method, see below
			if(findPhrase(nowLocationWords, nowInputWords)){
				//adding value to the result hash table
				result.put("Country", nowLocation);
			}
		}

		String[] priceList = new String[] {
			"AVERAGE PRICE", 
			"AVERAGE",
			"LOW PRICE",
			"LOW",
			"HIGH PRICE",
			"HIGH",
			"EXPENSIVE",
			"CHEAP" 
		};
		for(String nowPrice: priceList) {
			String[] nowPriceWords = nowPrice.trim().toUpperCase().split("[\\W]+");
			//findPhrase() is our own method, see below
			if(findPhrase(nowPriceWords, nowInputWords)){
				//adding value to the result hash table
				result.put("Price", nowPrice);
			}
		}

		String[] timeList = new String[] {
			"MINUTE", 
			"HOUR",
			"WEEK",
			"MONTH",
			"YEAR",
			"MINUTES",
			"HOURS",
			"WEEKS",
			"MONTHS",
			"YEARS" 
		};
		for(String nowPrice: timeList) {
			String[] nowTimeWords = nowPrice.trim().toUpperCase().split("[\\W]+");
			//findPhrase() is our own method, see below
			if(findPhrase(nowTimeWords, nowInputWords)){
				//adding value to the result hash table
				result.put("Time", nowPrice);
			}
		}
		
		//to check whether a stock exists or not
		//try {
			//stockChecker(nowInputWords);
		//} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		//} 

		//Demo 3: Extract substring using regular expression
		/*
		Pattern nowPattern = Pattern.compile("\\b\\w+@PSU\\.EDU\\b");
	    Matcher nowMatcher = nowPattern.matcher(nowInputText.trim().toUpperCase());
	    while (nowMatcher.find()) {
	    	String nowMatchedSubstring = nowMatcher.group();
	    	System.out.println("PSU Email found: " + nowMatchedSubstring);
	    	//adding value to the result hash table
			result.put("Email", nowMatchedSubstring);
	  	}
	  	*/
		
		//-------------- Modify Code Here (Assignment 4) Ends ---------------
		
		//return the result hash table. You do not need to change this part of code.
		return result;
		
	}

	/*
	 * Code added for Demo 2
	 * Return true if nowLocationWords can be found in nowInputWords
	 * Tips: Double-layer loop
	 */
	private boolean findPhrase(String[] nowLocationWords, String[] nowInputWords) {
		
		//iterate through each word in the sentence
		for(int i=0;i<nowInputWords.length;i++) {
			//allWordsMatchStartsWith() is our own method, see below
			if(allWordsMatchStartsWith(nowLocationWords, nowInputWords, i)) {
				return true;
			}
		}
		return false;
	}

	/*
	 * Code added for Demo 2
	 * Return true if all the words in nowLocationWords match with nowInputWords, starting in index
	 */
	private boolean allWordsMatchStartsWith(String[] nowLocationWords, String[] nowInputWords, int index) {
		// TODO Auto-generated method stub
		for(int i=0;i<nowLocationWords.length;i++) {
			if(!nowLocationWords[i].equals(nowInputWords[index+i])) {
				return false;
			}
		}
		return true;
	}

	private boolean stockChecker(String[] word) throws IOException
	{
		String stocktester = "https://www.nasdaq.com/search?q=";
		for (String wordForTesting: word)
		{
			stocktester += wordForTesting;
			URL url = new URL(stocktester);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			try 
			{
				//Runtime.getRuntime().exec(new String[]{"cmd", "/c","start chrome https://www.nasdaq.com/search?q="+wordForTesting});
				//int code = connection.getResponseCode();
				//System.out.println("Response code of the object is "+code);
				//return true;
				In page = new In("http://finance.yahoo.com/quote/" + wordForTesting);
        String html = page.readAll();
		System.out.println(html + "test");
        if (html.contains("<title></title>"))
		{
			
		}
			} 
			catch (Exception e) 
			{
				//connection.disconnect();
				System.out.println(e.getMessage());
				//System.out.println("It might not work");
				//return false;
        	}
		}
		return false;
	}
}
