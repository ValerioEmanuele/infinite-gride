package blog.valerioemanuele.ab.service;

import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.valerioemanuele.ab.model.InfiniteGrid;
import blog.valerioemanuele.ab.model.Point;
import blog.valerioemanuele.repository.InfiniteGridRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DatabaseInfiniteGrid implements InfiniteGrid{
	
	private UUID executionId;
	
	@PostConstruct
	public void init() {
		executionId = UUID.randomUUID();
	}
	
	@Autowired
	private InfiniteGridRepository infiniteGridRepository;

	@Override
	public boolean put(Point point) {
		validateInput(point);
		log.info("Adding the following point {}", point);
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
	
	@Override
	public String executionId() {
		return executionId.toString();
	}
}
