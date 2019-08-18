package cl.dataencryptation.hello.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import cl.dataencryptation.hello.dto.RequestDto;
import cl.dataencryptation.hello.dto.ResponseDto;

@Controller
public class IndexController {

	@PostMapping("/")
	public ResponseEntity<ResponseDto> sayHello(@RequestBody @Valid RequestDto requestDto){
		String msg = "Hello '%s' !";
		ResponseDto dtoR = new ResponseDto(requestDto.getName(), String.format(msg, requestDto.getName()));
		return new ResponseEntity<ResponseDto>(dtoR, HttpStatus.CREATED);
	}
}