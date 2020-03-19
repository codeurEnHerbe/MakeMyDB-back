package fr.iut.makemydb.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private DataSource dataSource;

    @Autowired
    public SecurityConfiguration(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(HttpSecurity http)throws Exception{
        http.formLogin().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/users/**").permitAll()
                .antMatchers("/api/schema/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();

        http.headers().frameOptions().sameOrigin();
        http.addFilter(new JsonAuthenticationFilter(authenticationManager(), new ObjectMapper()));
    }

   @Override
   protected void configure(AuthenticationManagerBuilder auth) throws Exception {

       auth.jdbcAuthentication().dataSource(dataSource)
               .withDefaultSchema()
               .withUser(
                       User.withUsername("admin")
                               .password(passwordEncoder().encode("admin"))
                               .authorities(AuthorityUtils.createAuthorityList("ADMIN")).build()
               );
   }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager() {
        JdbcUserDetailsManager userDetailsService = new JdbcUserDetailsManager();
        userDetailsService.setDataSource(dataSource);
        return userDetailsService;
    }
}
