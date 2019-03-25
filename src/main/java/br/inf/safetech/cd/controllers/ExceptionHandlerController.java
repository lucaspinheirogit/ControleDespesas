package br.inf.safetech.cd.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandlerController {
	
	//Controller que cuida dos erros da aplicação e os exibe na tela 
    @ExceptionHandler(Exception.class)
    public ModelAndView trataExceptionGenerica(Exception exception){
        exception.printStackTrace();
        
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("exception", exception);
        
        return modelAndView;
    }
}
