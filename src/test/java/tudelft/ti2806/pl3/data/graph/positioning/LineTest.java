package tudelft.ti2806.pl3.data.graph.positioning;

import static org.junit.Assert.assertFalse;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LineTest {
	@Test
	public void lineFunctionTest() {
		Line line1 = new Line(0, 0, 4, 4);
		assertTrue(line1.slope == 1);
		assertTrue(line1.constant == 0);
		
		Line line2 = new Line(5, 4, 7, 0);
		assertTrue(line2.slope == -2);
		assertTrue(line2.constant == 14);
		
		// No intersection on lines who intersect outside of their borders
		assertTrue(line1.getXOfIntersection(line2) == 14 / 3f);
		assertFalse(line1.intersect(line2));
		
		Line line3 = new Line(0, 0, 4, 2);
		assertTrue(line3.slope == 0.5);
		assertTrue(line3.constant == 0);
		
		Line line4 = new Line(0, 1, 4, 3);
		assertTrue(line4.slope == 0.5);
		assertTrue(line4.constant == 1);
		
		/*
		 * No intersection or java.lang.ArithmeticException, for dividing by
		 * zero, on parallel lines.
		 */
		assertFalse(line3.intersect(line4));
		
		// Intersection when two lines intersect within their borders
		assertTrue(line1.getXOfIntersection(line4) == 2);
		assertTrue(line1.intersect(line4));
		
	}
}
