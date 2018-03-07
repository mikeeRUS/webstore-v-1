package info.mike.webstorev1.config;

import info.mike.webstorev1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter  {

    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .headers().frameOptions().disable().and()
                .authorizeRequests()
                .antMatchers("/category/**",
                        "/index",
                        "/",
                        "/h2-console/**",
                        "/registration",
                        "/images/**",
                        "/delete/**",
                        "/rest/**",
                        "/cart",
                        "/add/**",
                        "/resttest",
                        "/addcart",
                        "/product/**").permitAll() //ww.pozwalaj wszystkim
                .antMatchers("/product/list").hasAnyRole("ADMIN") //ww. pozwalaj tylko adminowi
                .anyRequest().authenticated() //inne nie lapiace się są tylko dla tych po autentyfikacji
                .and().formLogin().loginPage("/login").permitAll().defaultSuccessUrl("/index", true)
                .and().logout().permitAll();

        http.logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutUrl("/logout")
                .logoutSuccessUrl("/index")
                .permitAll();


    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        //User.UserBuilder users = User.withDefaultPasswordEncoder();
        auth.inMemoryAuthentication()
                .withUser("user")
                .password(passwordEncoder()
                        .encode("pass"))
                .roles("USER");
        auth.authenticationProvider(authenticationProvider());
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }


    /*
    @Bean(name = "dataSource")
    public DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("org.h2.Driver");
        driverManagerDataSource.setUrl("jdbc:h2:mem:testdb");
        driverManagerDataSource.setUsername("Sa");
        driverManagerDataSource.setPassword("");
        return driverManagerDataSource;
    }
    */




}
