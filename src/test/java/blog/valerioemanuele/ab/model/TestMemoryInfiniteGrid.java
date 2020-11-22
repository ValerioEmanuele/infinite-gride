package blog.valerioemanuele.ab.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import blog.valerioemanuele.ab.exceptions.InvalidInputException;
import blog.valerioemanuele.ab.service.MemoryInfiniteGrid;

@SpringBootTest
class TestMemoryInfiniteGrid {

	@Autowired
	private MemoryInfiniteGrid grid;
	
	@Test
	void test_initialStatus() {
		assertNotNull(grid);
		assertEquals(0, grid.size());
	}
	
	@Test
	void test_putNullPoint_throwsException() {
		assertThrows(InvalidInputException.class, () -> grid.put(null));
	}
	
	@Test
	void test_putNotValidPoint_throwsException() {
		assertThrows(InvalidInputException.class, () -> grid.put(Point.builder().build()));

		assertThrows(InvalidInputException.class, () -> grid.put(Point.builder().x(BigInteger.ONE).build()));

		assertThrows(InvalidInputException.class, () -> grid.put(Point.builder().y(BigInteger.ONE).build()));
	}
	
	@Test
	void test_putPoint() {
		Point p = Point.of(BigInteger.ONE, BigInteger.TWO);
		assertNotNull(grid.put(p));
	}

}
