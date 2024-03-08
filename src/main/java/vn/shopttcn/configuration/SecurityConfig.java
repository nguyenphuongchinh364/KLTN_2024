package vn.shopttcn.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
				.dataSource(dataSource)
				.passwordEncoder(passwordEncoder())
				.usersByUsernameQuery("SELECT username,password,enabled FROM users WHERE BINARY username = ?")
				.authoritiesByUsernameQuery("SELECT username,roleName FROM users u INNER JOIN roles r ON u.roleId = r.roleId WHERE BINARY username = ?");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/admin/user/**").access("hasRole('ROLE_ADMIN')")
				.antMatchers("/admin/reviews/**").access("hasRole('ROLE_ADMIN')")
				.antMatchers("/admin/cat/add").access("hasRole('ROLE_ADMIN')")
				.antMatchers("/admin/cat/update/**").access("hasRole('ROLE_ADMIN')")
				.antMatchers("/admin/cat/delete/**").access("hasRole('ROLE_ADMIN')")
				.antMatchers("/admin/product/add").access("hasRole('ROLE_ADMIN')")
				.antMatchers("/admin/product/update/**").access("hasRole('ROLE_ADMIN')")
				.antMatchers("/admin/product/picture/**").access("hasRole('ROLE_ADMIN')")
				.antMatchers("/admin/product/delete/**").access("hasRole('ROLE_ADMIN')")
				.antMatchers("/admin/**").access("hasAnyRole('ROLE_ADMIN','ROLE_MOD')")
				.antMatchers("/").permitAll()
				.and()
				.formLogin()
				.loginPage("/auth/login")
				.loginProcessingUrl("/auth/login")
				.usernameParameter("username")
				.passwordParameter("password")
				.failureUrl("/auth/login?msg=login-error")
				.defaultSuccessUrl("/admin", false)
				.and()
				.logout()
				.logoutUrl("/auth/logout")
				.logoutSuccessUrl("/auth/login")
				.and()
				.exceptionHandling()
				.accessDeniedPage("/error/403")
				.and()
				.csrf().disable();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
