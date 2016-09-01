/**
 * the class Currency describes a Currency by its name, unit, currency code, rate etc...
 * @author Shamir & Alexander
 */
package shamir.alex.project;

public class Currency 
{
	// var def. according to the XML entities. 
		public String name;
		public double unit;
		public String currencyCode;
		public String country;
		public double rate;
		public double change;
		
		/**
		 * default constructor
		 */
		public Currency()
	    {
			 //initial all var to the default value
	        name = "unavailable";                                       
	        unit = 0;
	        currencyCode = "unavailable";
	        country = "unavailable";
	        rate = 0;
	        change = 0;
	    }
		
		/**
		 * value constructor
		 * @param name
		 * @param unit
		 * @param currencyCode
		 * @param country
		 * @param rate
		 * @param change
		 */
		public Currency(String name,double unit,String currencyCode,String country,double rate,double change)
		{
			this.name = name;
			this.unit = unit;
			this.currencyCode = currencyCode;
			this.country = country;
			this.rate = rate;
			this.change = change;
		}
		
		@Override
	    public String toString()
	    {
	        return "Currency Object[name=" + name + ", unit=" + unit + ", currencyCode="
	                + currencyCode + ", country=" + country + ", rate=" + rate
	                + ", change=" + change + "]";
	    }		
}