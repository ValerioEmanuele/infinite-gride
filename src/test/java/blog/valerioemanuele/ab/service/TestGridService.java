package blog.valerioemanuele.ab.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import blog.valerioemanuele.ab.exceptions.InvalidStepsException;
import blog.valerioemanuele.ab.model.Direction;
import blog.valerioemanuele.ab.model.Point;

@SpringBootTest
class TestGridService {

	@Autowired
	private GridService gridService;
	
	@Autowired
	private MemoryInfiniteGrid grid;
	
	@BeforeEach
	private void initGrid() {
		
	}
	
	
	@Test
	void test_moveOfNegative_throwsException() {
		assertThrows(InvalidStepsException.class, () -> gridService.gridFor(BigInteger.ONE.negate()));
	}
	
	@Test
	void test_moveOfNull_throwsException() {
		assertThrows(InvalidStepsException.class, () -> gridService.gridFor(null));
	}
	
	@Test
	void test_initialConditions() {
		assertEquals(0, grid.size());
	}
	
	
	@Test
	void test_moveOfOneStep() {
		gridService.moveMachine(grid);
		
		assertEquals(1, grid.size());
		assertTrue(Point.of(BigInteger.ZERO, BigInteger.ONE.negate()).equals(gridService.machinePosition()));
		
		assertEquals(Direction.DOWN, gridService.machineDirection());
	}
	
	
	@Test
	void test_moveOfOneStep_fromBlackSquare() {
		Point startPoint = Point.of(BigInteger.ZERO, BigInteger.ZERO);
		//set the start point as black square
		grid.put(startPoint);
		
		gridService.gridFor(BigInteger.ONE);
		
		assertEquals(0, grid.size());
		assertTrue(Point.of(BigInteger.ZERO, BigInteger.ONE).equals(gridService.machinePosition()));
		
		assertEquals(Direction.UP, gridService.machineDirection());
	}
	
	@Test
	void test_moveOfTwoSteps() {
		gridService.moveMachine(grid);
		gridService.moveMachine(grid);
		
		assertEquals(2, grid.size());
		assertTrue(Point.of(BigInteger.ONE.negate(), BigInteger.ONE.negate()).equals(gridService.machinePosition()));
		assertEquals(Direction.LEFT, gridService.machineDirection());
	}
	
	@Test
	void test_moveOfXSteps() {
		gridService.moveMachine(grid);
		//Black Squares={0,0}
		assertTrue(Point.of(BigInteger.ZERO, BigInteger.ONE.negate()).equals(gridService.machinePosition()));
		
		gridService.moveMachine(grid);
		//Black Squares={0,0},{0,-1}
		assertEquals(2, grid.size());
		assertTrue(Point.of(BigInteger.ONE.negate(), BigInteger.ONE.negate()).equals(gridService.machinePosition()));
		assertEquals(Direction.LEFT, gridService.machineDirection());
		
		gridService.moveMachine(grid);
		//Black Squares={0,0},{0,-1},{-1,-1}
		assertEquals(3, grid.size());
		assertTrue(Point.of(BigInteger.ONE.negate(), BigInteger.ZERO).equals(gridService.machinePosition()));
		assertEquals(Direction.UP, gridService.machineDirection());
		
		gridService.moveMachine(grid);
		//Black Squares={0,0},{0,-1},{-1,-1},{-1,0}
		assertEquals(Direction.RIGHT, gridService.machineDirection());
		assertEquals(4, grid.size());
		assertTrue(Point.of(BigInteger.ZERO, BigInteger.ZERO).equals(gridService.machinePosition()));
		
		gridService.moveMachine(grid);
		//Black Squares={0,-1},{-1,-1},{-1,0}
		assertEquals(Direction.UP, gridService.machineDirection());
		assertEquals(3, grid.size());
		assertTrue(Point.of(BigInteger.ZERO, BigInteger.ONE).equals(gridService.machinePosition()));
		
		gridService.moveMachine(grid);
		//Black Squares={0,-1},{-1,-1},{-1,0},{0,1}
		assertEquals(Direction.RIGHT, gridService.machineDirection());
		assertEquals(4, grid.size());
		assertTrue(Point.of(BigInteger.ONE, BigInteger.ONE).equals(gridService.machinePosition()));
		
		
		gridService.moveMachine(grid);
		//Black Squares={0,-1},{-1,-1},{-1,0},{0,1},{1,1}
		assertEquals(Direction.DOWN, gridService.machineDirection());
		assertEquals(5, grid.size());
		assertTrue(Point.of(BigInteger.ONE, BigInteger.ZERO).equals(gridService.machinePosition()));
		
		gridService.moveMachine(grid);
		//Black Squares={0,-1},{-1,-1},{-1,0},{0,1},{1,1},{1,0}
		assertEquals(Direction.LEFT, gridService.machineDirection());
		assertEquals(6, grid.size());
		assertTrue(Point.of(BigInteger.ZERO, BigInteger.ZERO).equals(gridService.machinePosition()));
		
		gridService.moveMachine(grid);
		//Black Squares={0,-1},{-1,-1},{-1,0},{0,1},{1,1},{1,0},{0,0}
		assertEquals(Direction.UP, gridService.machineDirection());
		assertEquals(7, grid.size());
		assertTrue(Point.of(BigInteger.ZERO, BigInteger.ONE).equals(gridService.machinePosition()));
		
		gridService.moveMachine(grid);
		//Black Squares={0,-1},{-1,-1},{-1,0},{1,1},{1,0},{0,0}
		assertEquals(6, grid.size());
		assertEquals(Direction.LEFT, gridService.machineDirection());
		assertTrue(Point.of(BigInteger.ONE.negate(), BigInteger.ONE).equals(gridService.machinePosition()));
		
		gridService.moveMachine(grid);
		//Black Squares={0,-1},{-1,-1},{-1,0},{1,1},{1,0},{0,0},{-1,1}
		assertEquals(7, grid.size());
		assertEquals(Direction.UP, gridService.machineDirection());
		assertTrue(Point.of(BigInteger.ONE.negate(), BigInteger.TWO).equals(gridService.machinePosition()));
		
	}
	
	
	/**
	 * Pay attention: it takes long time to execute it
	 */
	@Test
	@Disabled
	void test_BigStep() {
		gridService.gridFor(BigInteger.valueOf(Integer.MAX_VALUE));
		System.out.println("Number of black squares: "+grid.size());
		//Number of black squares: 247 785 763
		// 30 minutes to complete
	}
	
	@Test
	void test_printGrid() {
		
		//TODO: for examples: 0 moves: infinite grid of whites
		//negative number: bad request
	}

}
