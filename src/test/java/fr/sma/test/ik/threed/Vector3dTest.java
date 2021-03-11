package fr.sma.test.ik.threed;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector3dTest {
	@Test
	public void testRotateAround() {
		Vector3d v = new Vector3d(1, 1, 1);

		assertEquals(v, v.rotateAroundY(0, new Vector3d(0, 1, 0)));
		assertEquals(v, v.rotateAroundY(Math.PI * 2, new Vector3d(0, 1, 0)));
	}
}