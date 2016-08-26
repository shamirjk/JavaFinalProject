package shamir.alex.project;

import java.awt.EventQueue;
import org.apache.log4j.*;

public class Application {
	final static Logger logger = Logger.getLogger(Application.class);
	
	/**
	 * Application starts
	 */
	
	public static void main(String[] args) 
	{		
		PropertyConfigurator.configure("log4j.properties");
		logger.fatal("--------------------------------");
		EventQueue.invokeLater(new Runnable(){
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