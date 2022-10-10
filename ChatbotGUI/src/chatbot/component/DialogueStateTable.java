package chatbot.component;

import java.util.Hashtable;
import java.util.List;

public class DialogueStateTable {

	public DialogueStateTable() {
		
	}
	
	public static String execute(String dialogueStateName, List<Hashtable<String, String>> slotHistory) {
		
		String response = "I am not sure. Could you say more?";
		
		switch (dialogueStateName) {
		
			
			case "CHIT-CHAT":
				response = "[CHIT-CHAT RESPONSE]";
			break;
			
			
			case "GREETING":
				response = "Hello! How can I help you?";
			break;
			
			/*
		 	case "START-STATE":
		 		response = "Hello! How can I help you?";
		 	break;
			*/
		
			//Dialogue States that are independent from domains/intents 
		
        	case "ASK-LOCATION":
        		response = "Where are you?";
            break;
            	
            //Dialogue States in the Weather domain  
            
        	case "ANSWER-WEATHER":
        		response = "Today's weather forecast [REPORT]";
            break;
            
        	case "ANSWER-SNOW":
        		response = "Today will snow [REPORT]";
            break;
            
        	case "ANSWER-RAIN":
        		response = "Today will rain [REPORT]";
            break;
            
            //Dialogue States in the Food domain
            
            /*
        	case "ASK-LOCATION-ORDER-FOOD":
        		response = "Where do you want to order from?";
            break;
        		
        	case "ASK-LOCATION-FIND-FOOD":
        		response = "I will find some restaurants near you. Which area are you in?";
            break;
            
        	case "ANSWER-ORDER-FOOD":
        		response = "I will find some restaurants near you. Which area are you in?";
            break;
            
        	case "ANSWER-FIND-FOOD":
        		response = "I will find some restaurants near you. Which area are you in?";
            break;
        	*/
        	
        	default:
        		System.err.println("Invalid dialogueStateName: " + dialogueStateName);
        		System.exit(1);
        		//throw new IllegalArgumentException("Invalid dialogueStateName: " + dialogueStateName);
        		
		}
		
		return response;
		
	}
	
	
	
}
