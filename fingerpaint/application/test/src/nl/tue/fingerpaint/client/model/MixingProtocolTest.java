package nl.tue.fingerpaint.client.model;

import nl.tue.fingerpaint.client.model.MixingProtocol;
import nl.tue.fingerpaint.client.model.MixingStep;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

/**
 * GWT jUnit tests for the class {@link MixingProtocol}.
 * 
 * @author Group Fingerpaint
 */
public class MixingProtocolTest extends GWTTestCase {
	/** The mixing protocol to be tested in this test class. */
	private MixingProtocol program;

	/** First mixing step of the above mixing protocol. */
	private MixingStep step0;

	/** Second mixing step of the above mixing protocol. */
	private MixingStep step1;

	/** Third mixing step of the above mixing protocol. */
	private MixingStep step2;

	/**
	 * A test to check whether a new mixing protocol is initialised correctly.
	 * That is, the mixing protocol should be empty after initialisation.
	 */
	@Test
	public void testConstructor() {
		program = new MixingProtocol();
		assertEquals("Size of new program", 0, program.getProgramSize());
	}

	/**
	 * A test to check whether the addStep function correctly appends a given
	 * mixing step.
	 */
	@Test
	public void testAddStep() {
		init();
		MixingStep newStep = new MixingStep(2.0, false, false);
		program.addStep(newStep);
		// test if the new step is appended
		assertEquals(newStep, program.getStep(3));
		// assert that the rest of the program is unchanged
		assertEquals(step0, program.getStep(0));
		assertEquals(step1, program.getStep(1));
		assertEquals(step2, program.getStep(2));
	}

	/**
	 * A test to check whether the removeStep function correctly removes a
	 * certain mixing step.
	 */
	@Test
	public void testRemoveStep() {
		init();
		// should remove step1
		program.removeStep(1);
		// check if the total size is correct
		assertEquals(2, program.getProgramSize());
		// check if the first element is unchanged
		assertEquals(step0, program.getStep(0));
		// check if the last element is moved up correctly
		assertEquals(step2, program.getStep(1));
	}

	/**
	 * A test to check whether the step size is correctly edited for the given
	 * mixing step.
	 */
	@Test
	public void testEditStepSize() {
		init();
		MixingStep edited = program.getStep(1);
		// should change the step size of step1 to 2.25
		program.editStep(1, 2.25, edited.isClockwise(), edited.isTopWall());
		assertEquals(2.25, program.getStep(1).getStepSize());
	}

	/**
	 * A test to check whether the step direction is correctly edited for the
	 * given mixing step.
	 */
	@Test
	public void testEditStepDirection() {
		init();
		MixingStep edited = program.getStep(0);
		// should change the stepDirection of step0 equal to false
		program.editStep(0, edited.getStepSize(), false, edited.isTopWall());
		assertEquals(false, program.getStep(0).isClockwise());
	}

	/**
	 * A test to check whether the step wall is correctly edited for the given
	 * mixing step.
	 */
	@Test
	public void testEditStepWall() {
		init();
		MixingStep edited = program.getStep(2);
		// should change the stepWall of step2 equal to true
		program.editStep(2, edited.getStepSize(), true, edited.isTopWall());
		assertEquals(true, program.getStep(2).isClockwise());
	}

	/**
	 * A test to check whether a mixing step can be moved to a lower index
	 * correctly.
	 */
	@Test
	public void testMoveStepForward() {
		init();
		// move step1 one down
		program.moveStep(1, 0);
		// check if the order is correct
		assertEquals(step1, program.getStep(0));
		assertEquals(step0, program.getStep(1));
		assertEquals(step2, program.getStep(2));
	}

	/**
	 * A test to check whether a mixing step can be moved to a higher index
	 * correctly.
	 */
	@Test
	public void testMoveStepBack() {
		init();
		// move step1 one up
		program.moveStep(1, 2);
		// check if the order is correct
		assertEquals(step0, program.getStep(0));
		assertEquals(step2, program.getStep(1));
		assertEquals(step1, program.getStep(2));
	}

	// Exception tests----------------------------------------------
	/**
	 * A test to check whether the getStep function throws a correct
	 * IndexOutOfBoundsException, for an out of bounds retrieve index.
	 */
	@Test
	public void testGetStepException() {
		init();
		try {
			// out of bounds index
			program.getStep(3);
			fail("IndexOutOfBoundsException expected");
		} catch (IndexOutOfBoundsException e) {
			assertTrue(true);
		} catch (Exception e) {
			fail(e.toString());
		}
	}

	/**
	 * A test to check whether the addStep function throws a correct
	 * NullPointerException, for a mixing step that is null.
	 */
	@Test
	public void testAddStepException() {
		init();
		try {
			program.addStep(null);
			fail("NullPointerException expected");
		} catch (NullPointerException e) {
			assertTrue(true);
		} catch (Exception e) {
			fail(e.toString());
		}
	}

	/**
	 * A test to check whether the editStep function throws a correct
	 * IndexOutOfBoundsException, for an out of bounds retrieve index.
	 */
	@Test
	public void testEditStepException() {
		init();
		try {
			// out of bounds index, rest is bogus
			program.editStep(3, 666, false, false);
			fail("IndexOutOfBoundsException expected");
		} catch (IndexOutOfBoundsException e) {
			assertTrue(true);
		} catch (Exception e) {
			fail(e.toString());
		}
	}

	/**
	 * A test to check whether the moveStep function throws a correct
	 * IndexOutOfBoundsException, for an out of bounds initialIndex.
	 */
	@Test
	public void testMoveStepException1() {
		init();
		try {
			// out of bounds index for the initialIndex
			program.moveStep(3, 1);
			fail("IndexOutOfBoundsException expected");
		} catch (IndexOutOfBoundsException e) {
			assertTrue(true);
		} catch (Exception e) {
			fail(e.toString());
		}
	}

	/**
	 * A test to check whether the moveStep function throws a correct
	 * IndexOutOfBoundsException, for an out of bounds newIndex.
	 */
	@Test
	public void testMoveStepException2() {
		init();
		try {
			// out of bounds index for the initialIndex
			program.moveStep(1, 3);
			fail("IndexOutOfBoundsException expected");
		} catch (IndexOutOfBoundsException e) {
			assertTrue(true);
		} catch (Exception e) {
			fail(e.toString());
		}
	}

	/**
	 * A test to check whether the removeStep function throws a correct
	 * IndexOutOfBoundsException, for an out of bounds retrieve index.
	 */
	@Test
	public void testRemoveStepException() {
		init();
		try {
			// out of bounds index
			program.removeStep(3);
			fail("IndexOutOfBoundsException expected");
		} catch (IndexOutOfBoundsException e) {
			assertTrue(true);
		} catch (Exception e) {
			fail(e.toString());
		}
	}

	@Override
	public String getModuleName() {
		return "nl.tue.fingerpaint.Fingerpaint";
	}

	// --- PRIVATE PART --------------------------------------------------
	/*
	 * Setup method for the mixing protocol tested in this test class.
	 */
	private void init() {
		program = new MixingProtocol();
		step0 = new MixingStep(0.5, true, false);
		step1 = new MixingStep(1.75, true, true);
		step2 = new MixingStep(1.0, false, false);
		program.addStep(step0);
		program.addStep(step1);
		program.addStep(step2);
	}

}