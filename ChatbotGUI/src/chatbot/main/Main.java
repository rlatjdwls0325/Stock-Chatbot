package chatbot.main;

import java.math.BigDecimal;

import chatbot.infra.Chatbot;
import chatbot.infra.ChatbotGUI;

public class Main {

	public static void main(String[] args)  {
		
		//create your chatbot (user name, bot name)
		Chatbot nowChatbot = new Chatbot("Kenneth", "TestBot");
		
		//create a GUI that connects to your chatbot
		ChatbotGUI nowChatbotGUI = new ChatbotGUI(nowChatbot);

		Stock stock = YahooFinance.get("INTC");
 
BigDecimal price = stock.getQuote().getPrice();
BigDecimal change = stock.getQuote().getChangeInPercent();
BigDecimal peg = stock.getStats().getPeg();
BigDecimal dividend = stock.getDividend().getAnnualYieldPercent();
 
stock.print();
	}

}
