package blog.valerioemanuele.ab.service;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import blog.valerioemanuele.ab.model.InfiniteGrid;
import blog.valerioemanuele.ab.model.Point;
import lombok.Data;

/**
 * Represents an infinite grid in RAM-memory
 * It's an HashMap that contains only the black colored squares (all the others are white)
 * 
 * This implementation of the InfiniteGrid is used when the number of steps is less than
 * the prefixed threshold
 * @author Valerio Emanuele
 *
 */
@Service
@RequestScope
@Data
public class MemoryInfiniteGrid extends InfiniteGrid{
	
	private HashMap<Integer,Point> blackSquares;
	
	@PostConstruct
	public void init() {
		blackSquares = new HashMap<>();
	}
	
	@Override
	public boolean put(Point point) {
		validateInput(point);
		return blackSquares.put(point.hashCode(),point) != null;
	}

	@Override
	public boolean remove(Point point) {
		validateInput(point);
		return blackSquares.remove(point.hashCode()) != null;
	}

	@Override
	public boolean contains(Point point) {
		validateInput(point);
		return blackSquares.containsValue(point);
	}
	
	@Override
	public BigInteger countBlackSquares() {
		return BigInteger.valueOf(blackSquares.size());
	}

	@Override
	public Optional<Point> getMinXPointImpl() {
		return blackSquares.values()
	      .stream()
	      .min(Comparator.comparing(Point::getX));
	}

	@Override
	public Optional<Point> getMaxXPointImpl() {
		return blackSquares.values()
			      .stream()
			      .max(Comparator.comparing(Point::getX));
	}

	@Override
	protected Optional<Point> getMinYPointImpl() {
		return blackSquares.values()
			      .stream()
			      .min(Comparator.comparing(Point::getY));
	}

	@Override
	protected Optional<Point> getMaxYPointImpl() {
		return blackSquares.values()
			      .stream()
			      .max(Comparator.comparing(Point::getY));
	}
	
}
