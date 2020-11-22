package blog.valerioemanuele.ab.service;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.valerioemanuele.ab.exceptions.InvalidStepsException;
import blog.valerioemanuele.ab.model.Direction;
import blog.valerioemanuele.ab.model.InfiniteGrid;
import blog.valerioemanuele.ab.model.Machine;
import blog.valerioemanuele.ab.model.Point;
import lombok.Data;

@Service
@Data
public class GridService {
	
	@Autowired
	private Machine machine;
	
	@Autowired
	private MemoryInfiniteGrid memoryInfiniteGrid;
	
	public void gridFor(BigInteger numberOfSteps) {
		//TODO: put a condition where the behaviour change if the num of step is bigger than a threshold? and save them in db
		/*
		 * Personally I would advice against loading a massive file directly into memory, 
		 * rather try to load it in chunks or use some sort of temp file to store intermediate data.
			https://stackoverflow.com/questions/29534063/check-if-there-is-enough-memory-before-allocating-byte-array
		 */
		validateInput(numberOfSteps);
		
		for (BigInteger bi = BigInteger.ZERO; bi.compareTo(numberOfSteps) < 0; bi = bi.add(BigInteger.ONE)) {
			moveMachine(memoryInfiniteGrid);
		}
	}

	public void moveMachine(InfiniteGrid grid) {

		boolean isCurrSquareBlack = grid.contains(machine.getPosition());
		
		if(isCurrSquareBlack) {
			machine.counterClockwiseMove();
			grid.remove(machine.getPosition());
		}
		else {
			machine.clockwiseMove();
			grid.put(machine.getPosition());
		}
		machine.move();
	}

	private void validateInput(BigInteger numberOfSteps) {
		if(numberOfSteps == null || numberOfSteps.signum() == -1) {
			throw new InvalidStepsException("The number of steps must be a positive number");
		}
	}
	
	
	public Direction machineDirection() {
		return machine.getDirection();
	}
	
	public Point machinePosition() {
		return machine.getPosition();
	}
}
