package shamir.alex.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ConnectToServer.class, ParseFileTest.class, RoundTest.class })
public class AllTests {

}
