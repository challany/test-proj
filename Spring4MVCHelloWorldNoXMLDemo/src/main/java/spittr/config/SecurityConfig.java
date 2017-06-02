package spittr.config;



import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.encoding.Md4PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.ldap.LdapAuthenticationProviderConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends  WebSecurityConfigurerAdapter {
	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		
		//auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(new StandardPasswordEncoder("53cr3t"));
		
		//auth.inMemoryAuthentication().withUser("user").password("password").roles("USER").and().withUser("admin").password("password").roles("ADMIN","USER");
		LdapAuthenticationProviderConfigurer<AuthenticationManagerBuilder>.ContextSourceBuilder ldif = auth.ldapAuthentication()
		.userSearchBase("ou=people")
		.userSearchFilter("(uid={0})")
		.groupSearchBase("ou=groups")
		.groupSearchFilter("member={0}")
		.contextSource()
		.root("dc=habuma,dc=com")
		.ldif("classpath:users.ldif");	
		
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
	http.formLogin().loginPage("/login").and().httpBasic().realmName("spittr").and().authorizeRequests()
	.antMatchers("/spitters/me").authenticated()
	.antMatchers(HttpMethod.POST,"/spittles").authenticated()
	.anyRequest().permitAll()
	.and()
	.requiresChannel().antMatchers("/spitter/form").requiresSecure().and()
	.rememberMe().tokenValiditySeconds(213).key("spittrkey")
	.and()
	.logout().logoutSuccessUrl("/");
	}
}
