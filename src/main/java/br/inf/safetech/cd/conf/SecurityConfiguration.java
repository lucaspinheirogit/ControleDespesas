package br.inf.safetech.cd.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.inf.safetech.cd.dao.UsuarioDAO;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UsuarioDAO usuarioDao;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
//			.antMatchers("/resources/**").permitAll()
				.antMatchers("/contas").hasAnyRole("USER,ADMIN")
				.antMatchers("/contas/buscar").hasAnyRole("USER,ADMIN")
				.antMatchers("/contas/admin/**").hasRole("ADMIN")
				
				.antMatchers("/movimentacoes").hasAnyRole("USER,ADMIN")
				.antMatchers("/movimentacoes/ver").hasAnyRole("USER,ADMIN")
				.antMatchers("/movimentacoes/editar").hasAnyRole("USER,ADMIN")
				.antMatchers("/movimentacoes/form").hasAnyRole("USER,ADMIN")
				.antMatchers("/movimentacoes/remover").hasAnyRole("USER,ADMIN")
				.antMatchers("/movimentacoes/admin/**").hasRole("ADMIN")
				
				.antMatchers("/clientes").hasRole("ADMIN")
				.antMatchers("/clientes/**").hasRole("ADMIN")
				
				.antMatchers("/usuarios").hasRole("ADMIN")
				.antMatchers("/usuarios/**").hasRole("ADMIN")
				
				.antMatchers("/").permitAll()
				.antMatchers("/**").permitAll()
				.antMatchers("/magia").hasRole("ADMIN")
				.anyRequest().authenticated().and().formLogin().loginPage("/login")
				.defaultSuccessUrl("/")
				.failureUrl("/login?error=true")
				.permitAll().and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()
				.logoutSuccessUrl("/login");

		http.csrf().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usuarioDao).passwordEncoder(new BCryptPasswordEncoder());
	}

	// Forma recomendada de ignorar no filtro de segurança as requisições para
	// recursos estáticos
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}

}
