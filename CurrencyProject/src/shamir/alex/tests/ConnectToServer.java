package shamir.alex.tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import shamir.alex.project.CurrencyModule;

public class ConnectToServer {

	public CurrencyModule myCurrencyModule = new CurrencyModule();
    @Test
    public void gettingDocument() throws IOException{
    	
    	myCurrencyModule.onlineData();
    	assertTrue(myCurrencyModule.getDoc() == null);
    	myCurrencyModule.offlineData("Test.xml");
    	assertTrue(myCurrencyModule.getDoc() != null);   	
    }

}
