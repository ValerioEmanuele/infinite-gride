package blog.valerioemanuele.ab.model;

import blog.valerioemanuele.ab.exceptions.InvalidInputException;

public interface InfiniteGrid {
	boolean put(Point point);
	boolean remove(Point point);
	boolean contains(Point point);
	long count();
	
	default public void validateInput(Point point) {
		if(point == null || !point.isSet()) {
			throw new InvalidInputException("The input square is not valid");
		}
	}
	
	default String executionId() {
		return "";
	}
}
