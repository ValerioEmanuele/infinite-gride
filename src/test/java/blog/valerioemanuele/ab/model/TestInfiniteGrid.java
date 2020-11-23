package blog.valerioemanuele.ab.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import blog.valerioemanuele.ab.exceptions.InvalidInputException;
import blog.valerioemanuele.ab.provider.RandomPointsProvider;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class TestInfiniteGrid {

	@Autowired
	private InfiniteGrid memoryInfiniteGrid;
	
	@Autowired
	private InfiniteGrid databaseInfiniteGrid;
	
	private String W;
	private String B;
	private static final String N = System.lineSeparator();
	
	@BeforeEach
	public void initTest() {
		W = databaseInfiniteGrid.getWHITE_SQUARE();
		B = databaseInfiniteGrid.getBLACK_SQUARE();
	}
	
	@Test
	void test_initialStatus() {
		assertNotNull(memoryInfiniteGrid);
		assertNotNull(databaseInfiniteGrid);
		assertTrue(memoryInfiniteGrid.noBlackSquares());
		assertTrue(databaseInfiniteGrid.noBlackSquares());
	}
	
	@Test
	void test_putNullPoint_throwsException() {
		assertThrows(InvalidInputException.class, () -> memoryInfiniteGrid.put(null));
	}
	
	@Test
	void test_putNotValidPoint_throwsException() {
		assertThrows(InvalidInputException.class, () -> memoryInfiniteGrid.put(Point.builder().build()));

		assertThrows(InvalidInputException.class, () -> memoryInfiniteGrid.put(Point.builder().x(BigInteger.ONE).build()));

		assertThrows(InvalidInputException.class, () -> memoryInfiniteGrid.put(Point.builder().y(BigInteger.ONE).build()));
	}
	
	@Test
	void test_putPoint() {
		Point p = Point.of(BigInteger.ONE, BigInteger.TWO);
		assertNotNull(memoryInfiniteGrid.put(p));
	}
	
	@Test
	void test_gridGraphicalRepresentation_gridOfWhiteSquares() {
		String gridGraph = memoryInfiniteGrid.graphicalGrid();
		assertEquals("This grid as an infinity of White Squares", gridGraph);
	}
	
	
	@ParameterizedTest
	@ArgumentsSource(RandomPointsProvider.class)
	void test_getMinAndMaxPoints(List<Point> points) {
		//init the grid with the points
		points.stream()
			.peek(p -> p.setExecutionId(databaseInfiniteGrid.executionId()))
			.forEach(p -> {
				databaseInfiniteGrid.put(p);
				memoryInfiniteGrid.put(p);
			});
		
		
		// retrieve MIN X
		Optional<Point> p = databaseInfiniteGrid.getMinXPoint();
		
		assertTrue(p.isPresent());
		assertEquals(RandomPointsProvider.MIN_X, p.get().getX());
		
		p = memoryInfiniteGrid.getMinXPoint();
		
		assertTrue(p.isPresent());
		assertEquals(RandomPointsProvider.MIN_X, p.get().getX());
		
		// retrieve MAX X
		p = databaseInfiniteGrid.getMaxXPoint();
		
		assertTrue(p.isPresent());
		assertEquals(RandomPointsProvider.MAX_X, p.get().getX());
		
		p = memoryInfiniteGrid.getMaxXPoint();
		
		assertTrue(p.isPresent());
		assertEquals(RandomPointsProvider.MAX_X, p.get().getX());
		
		
		// retrieve MIN Y
		p = databaseInfiniteGrid.getMinYPoint();
		
		assertTrue(p.isPresent());
		assertEquals(RandomPointsProvider.MIN_Y, p.get().getY());
		
		p = memoryInfiniteGrid.getMinYPoint();
		
		assertTrue(p.isPresent());
		assertEquals(RandomPointsProvider.MIN_Y, p.get().getY());
		
		// retrieve MAX Y
		p = databaseInfiniteGrid.getMaxYPoint();
		
		assertTrue(p.isPresent());
		assertEquals(RandomPointsProvider.MAX_Y, p.get().getY());
		
		p = memoryInfiniteGrid.getMaxYPoint();
		
		assertTrue(p.isPresent());
		assertEquals(RandomPointsProvider.MAX_Y, p.get().getY());
	}
	
	
	@Test
	void test_gridGraphicalRepresentation_gridOfOneBlackSquare() {
		Point p = Point.of(BigInteger.ONE, BigInteger.TWO, databaseInfiniteGrid.executionId());
		
		assertTrue(databaseInfiniteGrid.put(p));
		
		String gridGraph = databaseInfiniteGrid.graphicalGrid();
		
		/*
		 * Expected grid
		 * 1 1 1
		 * 1 0 1
		 * 1 1 1
		 */
		String expectedGrid = W + W + W + N + 
							  W + B + W + N +
							  W + W + W;					
							  
		assertEquals(expectedGrid, gridGraph.toString());
	}

}
