/*
 * chatbot.component is added for Assignment 3 (Language Understanding)
 * 
 * DomainClassifier.java is added for Assignment 3 (Language
 * Understanding)
 */

package chatbot.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DomainClassifier {
	
	private static String[] domainDictionary;
	
	public DomainClassifier() {
		initializeDomainDictionary();
	}
	
	/**
	 * Create a dictionary of domains  
	 */
	private void initializeDomainDictionary() {
		
		//list all the domains
		domainDictionary = new String[]{"Other", "StockMarket", "Cryptocurrency"};
		
		//create the display string
		System.out.print("Domains: (");
		for(int i=0;i<domainDictionary.length;i++) {
			System.out.print(domainDictionary[i]);
			if(i!=domainDictionary.length-1) {
				System.out.print(", ");
			}
		}
		System.out.println(")");
		
	}
	
	/**
	 * Calculate the given meesage's score for each domain. The chatbot will
	 * select the domain with the *highest* score.
	 * 
	 * The initial score of each domain is 0.0.
	 * 
	 * @param nowInputText	An English message sent from the user.
	 * @return				An Double array that contains the score of each
	 * 						domain.
	 */
	private Double[] calculateDomainScores(String nowInputText) {
		
		//DO NOT change the following 4 lines
		//initiate all the scores to 0.0 
		Double[] scoreArray = new Double[domainDictionary.length];
		for(int i=0;i<scoreArray.length;i++) {
			scoreArray[i] = Double.valueOf(0.0);
		}
		
		//The following is the part you need to modify.
		
		//============= Please Modify Here (begins) =============== 

		//Count key words in a small stockmarket dictionary
		String[] tokenList = nowInputText.trim().toLowerCase().split("[\\W]+");		
		String[] stockDictionary = new String[] {"price", "good", "company", "bear", "bull", "market", "close", "stock", "share", "portfolio", "order", "open", "low", "high", "leverage", "bid", "samsung", "apple"};
		for(String stockmarketKeyword: stockDictionary) {
			for(String token: tokenList) {
				if(token.equals(stockmarketKeyword)) {
					scoreArray[1] = scoreArray[1].doubleValue()+1.0;
				}
			}
		}
		
		//Count key words in a small crytocurrency dictionary	
		String[] crytoDictionary = new String[] {"price", "good", "dangerous", "altcoin", "bitcoin", "block", "blockchain", "coin", "crytocurrency", "ethereum", "hash", "mining", "token", "virtual", "wallet"};
		for(String crytocurrencyKeyword: crytoDictionary) {
			for(String token: tokenList) {
				if(token.equals(crytocurrencyKeyword)) {
					scoreArray[2] = scoreArray[2].doubleValue()+1.0;
				}
			}
		}
		
		
		//============= Please Modify Here (ends) =============== 
		
		//============= Example Methods (begins) =============== 
		
		//Example 1: Use set instead of array
		/*
		String[] weatherDictionary = new String[] {"snow", "rain", "weather", "rain", "snow"};
		Set<String> keywordSet = new HashSet<>(Arrays.asList(weatherDictionary));
		System.out.println("set size:"+keywordSet.size());
		for(String weatherKeyword: keywordSet) {
			if(nowInputText.toLowerCase().indexOf(weatherKeyword)>=0) {
				scoreArray[1] = scoreArray[1].doubleValue()+1.0;
			}
		}
		*/
		
		//Example 2: Smart scoring (the later the better)
		/*
		String[] foodDictionary = new String[] {"snow", "rain", "weather"};
		for(String foodKeyword: foodDictionary) {
			if(nowInputText.toLowerCase().indexOf(foodKeyword)>=0) {
				int nowIndex = nowInputText.toLowerCase().indexOf(foodKeyword);
				scoreArray[1] = scoreArray[1].doubleValue()+1.0+(double)nowIndex;
			}
		}
		*/
		
		//Example 3: Tokenization using white spaces 
		/*
		String[] tokenList = nowInputText.trim().toLowerCase().split("[\\s]+");
		String[] foodDictionary = new String[] {"snow", "rain", "weather"};
		for(String foodKeyword: foodDictionary) {
			for(String token: tokenList) {
				if(token.equals(foodKeyword)) {
					scoreArray[1] = scoreArray[1].doubleValue()+1.0;
				}
			}
		}
		*/
		
		//Example 4: Tokenization using non-English words
		/*
		String[] tokenList = nowInputText.trim().toLowerCase().split("[\\W]+");		
		String[] foodDictionary = new String[] {"snow", "rain", "weather"};
		for(String foodKeyword: foodDictionary) {
			for(String token: tokenList) {
				if(token.equals(foodKeyword)) {
					scoreArray[1] = scoreArray[1].doubleValue()+1.0;
				}
			}
		}
		*/
		
		//Example 5: Regular Expression
		//You can use the online tester: https://www.freeformatter.com/java-regex-tester.html
		/*
		String[] patternArray = {//the pattern uses lower case here
				"(should|do)[\\s]+i[\\s]+(carry|bring|take|need)[\\s]+an[\\s]+umbrella[\\s|\\.|?]*"
				};
		for(String nowPatternStr: patternArray) {
			Pattern nowPattern = Pattern.compile(nowPatternStr);
			//the patterns use lower case, so the input needs to convert to lower cases
			Matcher nowMatcher = nowPattern.matcher(nowInputText.trim().toLowerCase());
			if(nowMatcher.matches()) {
				scoreArray[1] = 100.00;
			}
		}
		*/
		
		
		//============= Example Methods (ends) =============== 
		
		
		//Do not change the following lines
		//Check before returning the scoreArray
		if(scoreArray.length!=domainDictionary.length) {
			System.err.println("The score array size does not equal to the domain array size.");
			System.exit(1);
		}
		for(Double nowValue: scoreArray) {
			if(nowValue==null) {
				System.err.println("The score array contains null values.");
				System.exit(1);
			}
		}
		return scoreArray;
	}
	
	
	/**
	 * Input:
	 * 	nowInputText: the message that the user sent to your chatbot
	 * 
	 * Output:
	 * 	the label (domain) name string
	 * 
	 * @param nowInputText	An English message sent from the user.
	 * @return 				The name of the domain.
	 * 
	 */
	public String getLabel(String nowInputText) {
		
		//get the score array
		Double[] intentScores = calculateDomainScores(nowInputText);
		
		//print the scores of each domain
		Double nowMaxScore = null;
		int nowMaxIndex = -1;
		System.out.print("Domain Scores: (");
		for(int i=0;i<intentScores.length;i++){
			System.out.print(intentScores[i].doubleValue());
			if(i!=intentScores.length-1) {
				System.out.print(", ");
			}
			if(nowMaxScore==null||nowMaxIndex==-1||intentScores[i].doubleValue()>nowMaxScore.doubleValue()) {
				nowMaxIndex = i;
				nowMaxScore = intentScores[i].doubleValue();
			}
		}
		System.out.println(")");
		
		return domainDictionary[nowMaxIndex];
	}

}
