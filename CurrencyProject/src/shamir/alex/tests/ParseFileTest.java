package shamir.alex.tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import shamir.alex.project.CurrencyModule;

public class ParseFileTest{
	public CurrencyModule myCurrencyModule = new CurrencyModule();
    @Test
    public void gettingDocument() throws IOException{
    	
    	myCurrencyModule.offlineData("Test.xml");
    	Document testDoc = myCurrencyModule.getDoc();
		NodeList list = testDoc.getElementsByTagName("LAST_UPDATE");	
		
		assertEquals("2001-09-11",list.item(0).getFirstChild().getNodeValue());	
		
		list = testDoc.getElementsByTagName("CURRENCY");
		
		assertTrue(list.getLength() > 0);
		
		//Entire list scan
       
       	Node currCurrency = list.item(0);
            
        NodeList currencyElements = ((Element)currCurrency).getElementsByTagName("NAME");//NAME parsing the currency					                 
        assertEquals("Test",currencyElements.item(0).getFirstChild().getNodeValue());
        
        currencyElements = ((Element)currCurrency).getElementsByTagName("UNIT");//NAME parsing the currency					                 
        assertEquals("1",currencyElements.item(0).getFirstChild().getNodeValue());
        
        currencyElements = ((Element)currCurrency).getElementsByTagName("CURRENCYCODE");//NAME parsing the currency					                 
        assertEquals("TST",currencyElements.item(0).getFirstChild().getNodeValue());
        
        currencyElements = ((Element)currCurrency).getElementsByTagName("COUNTRY");//NAME parsing the currency					                 
        assertEquals("Galaxy",currencyElements.item(0).getFirstChild().getNodeValue());
        
        currencyElements = ((Element)currCurrency).getElementsByTagName("RATE");//NAME parsing the currency					                 
        assertEquals("4.72354",currencyElements.item(0).getFirstChild().getNodeValue());
        
        currencyElements = ((Element)currCurrency).getElementsByTagName("CHANGE");//NAME parsing the currency					                 
        assertEquals("0.01",currencyElements.item(0).getFirstChild().getNodeValue());
    }
    
}
