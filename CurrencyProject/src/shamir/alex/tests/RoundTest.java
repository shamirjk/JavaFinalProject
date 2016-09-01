/**
 * tests round method at Calculator Window class
 * @author Shamir & Alexander
 */
package shamir.alex.tests;

import static org.junit.Assert.*;
import java.math.BigDecimal;
import org.junit.Test;
import shamir.alex.project.CalculatorWindow;

public class RoundTest {
	public CalculatorWindow calcWindow = new CalculatorWindow();
	@Test
	public void test(){
		double rateBeforeRound = 10.123867;
		double rateAfterRound = 10.124;
		assertEquals(rateAfterRound , CalculatorWindow.round(rateBeforeRound,3, BigDecimal.ROUND_HALF_EVEN),0.001);
	}
}