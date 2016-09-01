/**
 * Interface for handling Currency XML Files
 * @author Shamir & Alexander
 */

package shamir.alex.project;

import java.util.*;
import org.w3c.dom.Document;

public interface IExchange 
{
	/**
     * the method gets the XML doc update time
     */
    public String getUpdateTime(Document doc);
    /**
     * the method return all currencies in hash-map (the key is the currencyCode field in class currency )
     */
    public Map<String, Currency> getCurrencies(Document doc);
    /**
     * the method converts the sum from source currency to destination currency
     */
    public double currencyConvert(Double sum, Currency source, Currency destination) throws NullPointerException;
}