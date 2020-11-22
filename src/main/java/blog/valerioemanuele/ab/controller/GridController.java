package blog.valerioemanuele.ab.controller;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import blog.valerioemanuele.ab.service.GridService;
import blog.valerioemanuele.ab.service.MemoryInfiniteGrid;

@RestController
public class GridController {
	
	@Autowired
	private GridService gridService;
	
	

	@PutMapping(path="/grid/{n}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> generateGridFor(@PathVariable(value = "numberOfSteps") BigInteger numberOfSteps) {
		gridService.gridFor(numberOfSteps);
		return ResponseEntity.ok().build();
	}

}
