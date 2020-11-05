package com.prokarma.consumer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/produce")
public class ProducerController {

	@RequestMapping(method = RequestMethod.GET, path = "/producer-data")
	public ResponseEntity<String> consumeService() {
		ResponseEntity<String> response = new ResponseEntity<String>("Working", HttpStatus.OK);
		return response;
	}

}
