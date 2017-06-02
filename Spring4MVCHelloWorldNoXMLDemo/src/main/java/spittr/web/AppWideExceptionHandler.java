package spittr.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppWideExceptionHandler {

	@ExceptionHandler(Exception.class)
	public String handleDuplicateSpittleException() {
		return "error/duplicate";
	}
}
