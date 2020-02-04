package springapp.jokefactory.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Override
    public void configure(HttpSecurity security) throws Exception {

        security.authorizeRequests()
                .antMatchers("h2-console/**").permitAll()
                .antMatchers("/jokes").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/joke").hasAnyAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/jokes");

        // settings only for development purposes
        // For production should be commented
        security.csrf().disable();
        security.headers().frameOptions().disable();
    }

    @Autowired
    public void securityUsers(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT username, password, enabled FROM USER WHERE username = ?")
                .authoritiesByUsernameQuery("SELECT username, role FROM ROLE WHERE username = ?");
    }
}
