package blog.valerioemanuele.ab.service;

import java.util.HashSet;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import blog.valerioemanuele.ab.model.InfiniteGrid;
import blog.valerioemanuele.ab.model.Point;
import lombok.Data;

/**
 * Represents an infinite grid
 * is an HashSet that contains only the black colored squares (all the others are white)
 * @author Valerio Emanuele
 *
 */
@Service
@RequestScope
@Data
public class MemoryInfiniteGrid extends HashSet<Point> implements InfiniteGrid{
	
	@Override
	public boolean put(Point point) {
		validateInput(point);
		return add(point);
	}

	@Override
	public boolean remove(Point point) {
		validateInput(point);
		return super.remove(point);
	}

	@Override
	public boolean contains(Point point) {
		validateInput(point);
		return super.contains(point);
	}
	
	@Override
	public long count() {
		return super.size();
	}
	
}
