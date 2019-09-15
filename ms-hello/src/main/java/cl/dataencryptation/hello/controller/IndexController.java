package cl.dataencryptation.hello.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import cl.dataencryptation.hello.dto.RequestDto;
import cl.dataencryptation.hello.dto.ResponseDto;

@Controller
public class IndexController {

	private final static String MSG = "Hello '%s' !";
	private ArrayList<String> names = new ArrayList<String>();
	
	@PostMapping("/")
	public ResponseEntity<Void> sayHello(@RequestBody @Valid RequestDto requestDto){
		names.add(requestDto.getName());
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<ResponseDto>> getList(){
		List<ResponseDto> dtoRlist = new ArrayList<ResponseDto>();
	    
	    for (String name : this.names) {
	    	dtoRlist.add(
	    			new ResponseDto(name, 
	    					String.format(MSG, name))
	    			);
	    }
		return new ResponseEntity<List<ResponseDto>>(dtoRlist, HttpStatus.OK);
	}
}