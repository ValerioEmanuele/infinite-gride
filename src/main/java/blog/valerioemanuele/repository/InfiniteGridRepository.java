package blog.valerioemanuele.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import blog.valerioemanuele.ab.model.Point;

@Repository
public interface InfiniteGridRepository extends JpaRepository<Point, Long> {

	Point findByXAndYAndExecutionId(BigInteger x, BigInteger y, String executionId);

}
