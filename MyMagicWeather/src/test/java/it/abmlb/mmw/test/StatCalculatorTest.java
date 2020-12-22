/**
 * 
 */
package it.abmlb.mmw.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import it.abmlb.mmw.exceptions.MmwStatsException;
import it.abmlb.mmw.utilities.*;

/**
 * @author matteolorenzo&agnese
 *
 */
class StatCalculatorTest {
	
	private StatCalculator s0, s1;

	
	@BeforeEach
	void setUp() {
		s0 = new StatCalculator();
		
		s1 = new StatCalculator();
		s1.addSample(12.0);
		s1.addSample(8.0);
		s1.addSample(4.0);
		
	}

	
	@AfterEach
	void tearDown() {
	}

	@Test
	@DisplayName("Test senza campioni")
	void test0() {
		try {
			assertEquals(0.0 , s0.getMax());
			assertEquals(0.0 , s0.getMin());
			assertEquals(0.0 , s0.getAverage());
			assertEquals(0.0 , s0.getVariance());
		} catch (MmwStatsException e) {
			
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	@DisplayName("Test per controllare i conti")
	void test1() {
		try {
			assertEquals(12.0, s1.getMax());
			assertEquals(4.0, s1.getMin());
			assertEquals(10.66, s1.getVariance());
			assertEquals(8.0, s1.getAverage());
		} catch (MmwStatsException e) {
			
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	@DisplayName("Test StatCalculator exception")
	public void testStatExc(){
		MmwStatsException ex = Assertions.assertThrows(MmwStatsException.class, () -> {
	    	s0.getMax();
	      });
		
	    assertEquals(ex.getMessage(), "Assenza di campioni");
	}

}