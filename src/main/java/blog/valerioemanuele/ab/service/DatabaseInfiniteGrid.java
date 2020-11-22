package blog.valerioemanuele.ab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.valerioemanuele.ab.model.InfiniteGrid;
import blog.valerioemanuele.ab.model.Point;
import blog.valerioemanuele.repository.InfiniteGridRepository;

@Service
public class DatabaseInfiniteGrid implements InfiniteGrid{
	
	@Autowired
	private InfiniteGridRepository infiniteGridRepository;

	@Override
	public boolean put(Point point) {
		validateInput(point);
		return infiniteGridRepository.save(point) != null;
	}

	@Override
	public boolean remove(Point point) {
		validateInput(point);
		infiniteGridRepository.delete(point);
		return true;
	}

	@Override
	public boolean contains(Point point) {
		validateInput(point);
		Point p = infiniteGridRepository.findByXAndYAndExecutionId(point.getX(), point.getY(), point.getExecutionId());
		return p != null;
	}

	@Override
	public long count() {
		return infiniteGridRepository.count();
	}
}
