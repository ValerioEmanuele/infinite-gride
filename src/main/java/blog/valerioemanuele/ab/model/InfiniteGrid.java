package blog.valerioemanuele.ab.model;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;

import blog.valerioemanuele.ab.exceptions.InvalidInputException;
import lombok.Getter;

@Getter
public abstract class InfiniteGrid {
	
	public static final String INFINITY_WHITE_SQUARES_GRID = "This grid as an infinity of White Squares";

	@Value("${whiteSquare:1}")
	public String WHITE_SQUARE;
	
	@Value("${blackSquare:0}")
	public String BLACK_SQUARE;
	
	public abstract boolean put(Point point);
	public abstract boolean remove(Point point);
	public abstract boolean contains(Point point);
	public abstract BigInteger countBlackSquares();
	protected abstract Optional<Point> getMinXPointImpl();
	protected abstract Optional<Point> getMaxXPointImpl();
	protected abstract Optional<Point> getMinYPointImpl();
	protected abstract Optional<Point> getMaxYPointImpl();
	
	public void validateInput(Point point) {
		if(point == null || !point.isSet()) {
			throw new InvalidInputException("The input square is not valid");
		}
	}
	
	public String executionId() {
		return "";
	}
	
	public final Optional<Point> getMinXPoint() {
		if(noBlackSquares()) {
			return Optional.empty();
		}
		
		return getMinXPointImpl();
	}
	
	public Optional<Point> getMaxXPoint() {
		if(noBlackSquares()) {
			return Optional.empty();
		}
		
		return getMaxXPointImpl();
	}
	
	public Optional<Point> getMinYPoint() {
		if(noBlackSquares()) {
			return Optional.empty();
		}
		return getMinYPointImpl();
	}
	
	public Optional<Point> getMaxYPoint() {
		if(noBlackSquares()) {
			return Optional.empty();
		}

		return getMaxYPointImpl();
	}
	
	
	public boolean noBlackSquares() {
		return countBlackSquares().equals(BigInteger.ZERO);
	}
	
	public String graphicalGrid() {
		if(noBlackSquares()) {
			return INFINITY_WHITE_SQUARES_GRID;
		}
		
		BigInteger minX = getMinXPoint().get().getX().subtract(BigInteger.ONE);
		BigInteger maxX = getMaxXPoint().get().getX().add(BigInteger.ONE);
		
		BigInteger minY = getMinYPoint().get().getY().subtract(BigInteger.ONE);
		BigInteger maxY = getMaxYPoint().get().getY().add(BigInteger.ONE);

		
		StringBuilder grid = new StringBuilder();
		boolean isLastRow = false;
		for (BigInteger y = minY; y.compareTo(maxY) <= 0; y = y.add(BigInteger.ONE)) {
			for (BigInteger x = minX; x.compareTo(maxX) <= 0; x = x.add(BigInteger.ONE)) {
				if(contains(Point.of(x, y, executionId()))) {
					grid.append(BLACK_SQUARE);
				}
				else {
					grid.append(WHITE_SQUARE);
				}
			}
			
			isLastRow = y.compareTo(maxY) == 0;
			if(!isLastRow) {
				grid.append(System.lineSeparator());
			}
		}
		
		return grid.toString();
	}
	
}
