package shamir.alex.project;

import java.awt.EventQueue;
import org.apache.log4j.*;

public class Application {
	final static Logger logger = Logger.getLogger(Application.class.getName());
	//final static Logger logger = Logger.getLogger(Application.class);
	
	public static void main(String[] args) 
	{		
		PropertyConfigurator.configure("log4j.properties");
		EventQueue.invokeLater(new Runnable(){
			public void run()
			{
				try
				{	
					logger.info("this is Yotam the KING");
					GUI window = new GUI();
					window.loadFromOnline();
					window.autoUpdate();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
}
