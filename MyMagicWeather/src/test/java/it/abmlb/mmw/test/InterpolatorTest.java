package it.abmlb.mmw.test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import it.abmlb.mmw.exceptions.MmwInterpolationOutOfBoundsException;
import it.abmlb.mmw.model.Point;
import it.abmlb.mmw.utilities.Interpolator;

/**
 * @author matteolorenzo&agnese
 * 
 */
public class InterpolatorTest {
	
	private Interpolator interp;
	
	@BeforeEach
	public void setUp() {
		interp = new Interpolator();
		interp.addSample(new Point(0L, 5.0));
		interp.addSample(new Point(2L, 10.0));
	}
	
	@AfterEach
	public void tearDown() {}
	
	@Test
	@DisplayName("Test valueFor")
	public void testValueFor() throws MmwInterpolationOutOfBoundsException{
		Double[] test = {
				interp.valueFor(new Point(0L, 0.0)).getY(),
				interp.valueFor(new Point(1L, 0.0)).getY(),
				interp.valueFor(new Point(2L, 0.0)).getY()
				
		};
		
		Double[] result = {
				 5.0,
				 7.5,
				 10.0
		};
		
		assertArrayEquals(test, result);
	    }
	
	@Test
	@DisplayName("Test valueFor exception")
	public void testValueForExc(){
		MmwInterpolationOutOfBoundsException ex = Assertions.assertThrows(MmwInterpolationOutOfBoundsException.class, () -> {
	    	interp.valueFor(new Point(3L, 0.0)).getY();
	      });
		
	    assertEquals(ex.getMessage(), "Fuori range");
	}
	    
}
