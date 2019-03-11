package br.inf.safetech.cd.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.inf.safetech.cd.models.Usuario;

public class UsuarioValidation implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Usuario.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		System.out.println("Target: " +target);
		
		ValidationUtils.rejectIfEmpty(errors, "senha", "field.required");
		ValidationUtils.rejectIfEmpty(errors, "senhaRepetida", "field.required");
		
		Usuario usuario = (Usuario) target;
		
		if(!usuario.getSenha().equals(usuario.getSenhaRepetida())) {
			errors.rejectValue("senha", "field.password.match");
			errors.rejectValue("senhaRepetida", "field.password.match");
		}
	
	}
	
}
