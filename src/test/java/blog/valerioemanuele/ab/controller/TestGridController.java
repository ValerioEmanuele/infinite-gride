package blog.valerioemanuele.ab.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class TestGridController {

	@Autowired
	private GridController gridController;
	
	@Test
	void test_generateGridFor() {
		ResponseEntity<Void> response = gridController.generateGridFor(BigInteger.valueOf(3));
		
		assertNotNull(response);
		assertTrue(response.equals(ResponseEntity.ok().build()));
	}

}
