/**
 * populate data from XML file in to a doc var.
 * implements will be in the extends classes
 * @throws IOException
 * @author Shamir & Alexander
 */
package shamir.alex.project;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class CurrencyModule implements IExchange
{
	private Document doc;
	/**
	 * The method gets the XML doc update time
	 */
	public String getUpdateTime(Document doc)
	{
		if(doc != null)
		{
			//Return all the nodes from the doc that contains "LAST_UPDATE" open tag
			NodeList list = doc.getElementsByTagName("LAST_UPDATE");						
			String tmp = list.item(0).getFirstChild().getNodeValue();			
			return tmp;
		}
		else
		{
			return "BAD FILE";
		}
	}
	/**
	 * This method returns the currencies that are in the hash-map
	 * (key = currencyCode field in class currency)
	 */
	public HashMap<String, Currency> getCurrencies(Document doc)
	{
		//Currencies hash-map instantiate
		HashMap<String, Currency> map = new HashMap<String, Currency>();									                                   	   
        //Return all "CURRENCY" open tag nodes from doc
		NodeList list = doc.getElementsByTagName("CURRENCY");
		//Entire list scan
        for(int i=0 ; i < list.getLength() ; ++i)															            	                                                         
        {
            																	                                                                         
            //Get the "i" currency
        	Node currCurrency = list.item(i);
            																                                                              
            //Create object of type Currency in order to fill in & set to map
        	Currency tmpCurrency = new Currency();
            
            NodeList currencyElements = ((Element)currCurrency).getElementsByTagName("NAME");//NAME parsing the currency					                 
            tmpCurrency.name = currencyElements.item(0).getFirstChild().getNodeValue();							                        
            //Update var inside new currency object
            
            currencyElements = ((Element)currCurrency).getElementsByTagName("UNIT"); //currency UNIT parsing							                            
            tmpCurrency.unit = Double.parseDouble(currencyElements.item(0).getFirstChild().getNodeValue());
            // var Update inside the new currency object
            
            currencyElements = ((Element)currCurrency).getElementsByTagName("CURRENCYCODE"); //CURRENCYCODE parsing					          
            tmpCurrency.currencyCode = currencyElements.item(0).getFirstChild().getNodeValue();					           
            //update the var inside the new currency object
            
            currencyElements = ((Element)currCurrency).getElementsByTagName("COUNTRY"); //currency COUNTRY parsing							                   
            tmpCurrency.country = currencyElements.item(0).getFirstChild().getNodeValue();							                
            //update the var inside the new currency object
            
            currencyElements = ((Element)currCurrency).getElementsByTagName("RATE"); //currency RATE parsing							                            
            tmpCurrency.rate = Double.parseDouble(currencyElements.item(0).getFirstChild().getNodeValue());        
            //update the var inside the new currency object
            
            currencyElements = ((Element)currCurrency).getElementsByTagName("CHANGE"); //currency CHANGE parsing							                          
            tmpCurrency.change = Double.parseDouble(currencyElements.item(0).getFirstChild().getNodeValue());	
            //update the var inside the new currency object
            
            map.put(tmpCurrency.currencyCode, tmpCurrency);														                                                  
            //insert new currency to currencies map
        }
        return map;		
	}
	
	/**
	 * the method converts the sum from source currency to destination currency
	 */
	public double currencyConvert(Double sum, Currency source, Currency destination) throws NullPointerException
	{
		if(source == null || destination == null)
        {
            throw new NullPointerException();
        }
        return (sum * (source.rate/source.unit)) / (destination.rate/ destination.unit);
	}
	
	public void onlineData() throws IOException 
	{
		URL israelBankSite; //define URL var			
		HttpURLConnection connection = null ; // define a connection										
		InputStream inputStr = null;
		
		
		try
        {
			israelBankSite = new URL("http://www.boi.org.il/currency.xml");  
        }		
        catch(MalformedURLException ex) //initial URL exception handle											
        {
            throw new MalformedURLException();	//throw exception to caller					           		   
        }
        
		try
        {
            connection = (HttpURLConnection)israelBankSite.openConnection(); //initial the connection
            connection.setRequestMethod("GET");	//define the HTTP method
            connection.connect(); //open the connection to URL
            inputStr = connection.getInputStream(); //get input streaming

            //save reserve copy to file
            OutputStream os = new FileOutputStream("Offline.xml");
            byte[] buffer = new byte[1024];
            int bytesRead;
            //read from is to buffer
            while((bytesRead = inputStr.read(buffer)) !=-1){
                os.write(buffer, 0, bytesRead);
            }
            //flush OutputStream to write any buffered data to file
            os.flush();
            os.close();
            //offlineData("Offline.xml");

        }
        catch(IOException conErr)
        {
            throw new IOException(); //throw general connection exception
        }
        finally
        {
            if(inputStr != null) //input stream closure													                                                        
            {
                try
                {
                    inputStr.close();
                }
              
                catch(IOException inpCls) //catch exception											                                                 
                {
                   throw new IOException();
                }
            }

            if(connection != null)
            {
                connection.disconnect();
            }
        }
	}
	
	public void offlineData(String filePath) throws IOException
	{
		File fXmlFile = new File(filePath); //load the file to var
		
		//build the doc var from the file
		DocumentBuilder dBuilder = null;
		try
		{	
			dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder(); //create the builder
		}
		catch (ParserConfigurationException e1)
		{
			throw new IOException();
		}
		try
		{
			doc = dBuilder.parse(fXmlFile); //parse the file
		}
		catch (SAXException | IOException e)
		{
			throw new IOException();
		}
		doc.getDocumentElement().normalize();
	}
	
	
	/**
	 * get the doc var, the implement will pass to extends classes
	 */
	
	public Document getDoc()
	{
		return doc;
	}

}
