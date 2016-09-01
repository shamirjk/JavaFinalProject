/**
 * Application Main Class
 * @author Shamir & Alexander
 */
package shamir.alex.project;

import java.io.File;
import javax.swing.SwingUtilities;
import org.apache.log4j.*;
import org.apache.log4j.xml.DOMConfigurator;

public class Application {
	final static Logger logger = Logger.getLogger(Application.class.getName());
	/**
	 * Application starts
	 */
	public static void main(String[] args) 
	{	
		//configure log4j
		String log4jConfigFile = System.getProperty("user.dir") + File.separator + "log4j.xml";
		DOMConfigurator.configure(log4jConfigFile);
		
		logger.fatal("--------------------------------");
		
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				try
				{	
					logger.info("Application Started");
					GUI window = new GUI();
					window.loadFromOnline();
					window.autoUpdate();
				}
				catch (Exception e)
				{
					e.printStackTrace();
					logger.fatal("Application Crashed while Start Running");
				}
			}
		});
	}
}