package chatbot.infra;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import chatbot.component.DomainClassifier;
import chatbot.component.SlotFiller;
import chatbot.component.StockMarketIntentClassifier;
import chatbot.component.CrytocurrencyIntentClassifier;
//import chatbot.component.FoodIntentClassifier;
import chatbot.component.DialogueManager;

public class Chatbot {
	
	private String userName = "YOUR NAME HERE";
	private String botName = "BOT NAME HERE";
	
	//=====Code Added for Assignment 3 (Language Understanding) Begins=====
	
		//one domain classifier
		private DomainClassifier nowDomainClassifier;
		
		//each domain has one intent classifier 
		private StockMarketIntentClassifier stockMarketIntentClassifier;
		private CrytocurrencyIntentClassifier crytocurrencyIntentClassifier;
	
	//=====Code Added for Assignment 3 (Language Understanding) Ends=====
	
	//=====Code Added for Assignment 4 (Language Understanding) Begins=====
		
		//Don't forget to add the following line to the top of this Java file:
		//import chatbot.component.SlotFiller;
		
		private SlotFiller nowSlotFiller;
		
	//=====Code Added for Assignment 4 (Language Understanding) Ends=====
	

	//=====Code Added for "Dialogue Management" Begins=====
	private DialogueManager nowDialogueManager;
	//=====Code Added for "Dialogue Management" Ends=====
	
	public Chatbot(String userName, String botName) {
		
		this.userName = userName;
		this.botName = botName;
		
		//=====Code Added for Assignment 3 (Language Understanding) Begins=====
		
			this.nowDomainClassifier = new DomainClassifier();
			this.stockMarketIntentClassifier = new StockMarketIntentClassifier();
			this.crytocurrencyIntentClassifier = new CrytocurrencyIntentClassifier();
			this.nowSlotFiller = new SlotFiller();
			this.nowDialogueManager = new DialogueManager();
		
		//=====Code Added for Assignment 3 (Language Understanding) Ends=====
		
	}
	
	/*
	 * Task 3: Add a response in Chatbot.java to respond to user message
	 * 
	 * Please modify the getResponse() method in the Chatbot class to respond
	 * to three or more different user messages meaningfully. I provided one
	 * example in the getResponse().
	 */
	
	public String getResponse(String nowInputText) {
		
		
		//=====Code Added for Assignment 3 (Language Understanding) Begins=====
		
		System.out.println("--------------");
		System.out.println("User Message: "+nowInputText);
		String nowDomain = nowDomainClassifier.getLabel(nowInputText);
		System.out.println("Domain: "+nowDomain);
		String nowIntent = "";
		
		//=====Code Added for Assignment 4 (Language Understanding) Begins=====
		Hashtable<String, String> extractedSlotValues = nowSlotFiller.extractSlotValues(nowInputText);
		//=====Code Added for Assignment 4 (Language Understanding) Ends=====
		
		if(!nowDomain.equals("Other")) {//in-domain message
					
			if(nowDomain.equals("StockMarket")) {//Food domain
				nowIntent = stockMarketIntentClassifier.getLabel(nowInputText);
			}else if(nowDomain.equals("Cryptocurrency")) {//Weather domain
				nowIntent = crytocurrencyIntentClassifier.getLabel(nowInputText);
			}else {//this shouldn't happen
				System.err.println("Domain name is incorrect!");
				System.exit(1);
				return null;
			}
		}else {//out-of-domain message. 
			//return "This message is out of the domains of the chatbot.";
		}
		
		//=====Code Added for Assignment 3 (Language Understanding) Ends=====
		
		//System.out.println("Intent: "+nowIntent);
		//String nowResponse = "Domain = "+nowDomain+"; Intent = "+nowIntent;
		
		//=====Code Added for Assignment 4 (Language Understanding) Begins=====
		//nowResponse += slotTableToString(extractedSlotValues);
		//=====Code Added for Assignment 4 (Language Understanding) Ends=====
		

		//=====Code Added for "Dialogue Management" Begins=====
		
		String nowDialogueStatus = "Domain = "+nowDomain+"; Intent = "+nowIntent;
		nowDialogueStatus += slotTableToString(extractedSlotValues);
		System.out.println("nowDialogueStatus: "+nowDialogueStatus);
		
		//Dialogue Management
		String nextState = nowDialogueManager.getNextState(nowDomain, nowIntent, extractedSlotValues);
		System.out.println("nextState: "+nextState);
		String nowResponse = nowDialogueManager.executeStateAndGetResponse(nextState);
		System.out.println("nowResponse: "+nowResponse);
		
		//=====Code Added for "Dialogue Management" Ends=====


		return nowResponse;
		//=====Code Added for Assignment 3 (Language Understanding) Ends=====
	}

	/*
	 * Method slotTableToString() is added for Assignment 4
	 * 
	 * [Input]
	 * A Hashtable<String, String> returned by extractSlotValues() in
	 * SlotFiller.java
	 * 
	 * [Output]
	 * A string that list all the extracted slot values
	 * 
	 */
	private String slotTableToString(Hashtable<String, String> extractedSlotValues) {
		
		String result = " (";
		
		for(String nowKey: extractedSlotValues.keySet()) {
			
			String nowValue = extractedSlotValues.get(nowKey);
			System.out.println(nowKey+"="+nowValue);
			result += nowKey+"="+nowValue+"; ";
		}
		
		result += ")";
		
		return result;
		
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getBotName() {
		return botName;
	}

	public void setBotName(String botName) {
		this.botName = botName;
	}

	
	
	

}
