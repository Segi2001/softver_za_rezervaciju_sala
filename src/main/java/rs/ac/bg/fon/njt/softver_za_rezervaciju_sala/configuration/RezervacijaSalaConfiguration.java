package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


import javax.sql.DataSource;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.filter.ApiKeyFilter;

@Configuration
public class RezervacijaSalaConfiguration {


//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(12);
//    }


    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

                jdbcUserDetailsManager.setUsersByUsernameQuery(
                "select o.email as username,k.sifra as password,1 as enabled" +
                        " from korisnik k JOIN osoba o on (k.osoba_id=o.id) where" +
                        " o.email=?"
                );


                    jdbcUserDetailsManager    .setAuthoritiesByUsernameQuery(
                "select o.email as username,r.role as authority from korisnik k Join osoba o on (k.osoba_id=o.id) join" +
                        " roles r on (r.korisnik_id=k.id) where o.email=?"
        );


        return  jdbcUserDetailsManager;
    }


//        @Bean
//    public InMemoryUserDetailsManager userDetailsManager(){
//
//        UserDetails john= User.builder()
//                .username("john")
//                .password("{noop}test123")
//                .roles("EMPLOYEE")
//                .build();
//        UserDetails mary= User.builder()
//                .username("mary")
//                .password("{noop}test123")
//                .roles("EMPLOYEE","MANAGER")
//                .build();
//
//        UserDetails susan= User.builder()
//                .username("susan")
//                .password("{noop}test123")
//                .roles("EMPLOYEE","MANAGER","ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(john,mary,susan);
//    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers( "/", "/css/**", "/js/**", "/images/**","/register/form","/register/result","/register/confirm/**").permitAll()
                                .requestMatchers("/api/public/**").permitAll()
                                .requestMatchers("/reservation/chooseDate","/reservation/hallsPerDay","/reservation/createReservation",
                                        "/reservation/myReservations","/reservation/deleteReservation"
                                        ).hasRole("KORISNIK")
                               .requestMatchers("/reservation/allReservations","/reservation/updateReservation","/hall/**").hasRole("ADMIN")
                                
                                .anyRequest().authenticated()
                                
                )
                .formLogin(form ->
                        form
                                .loginPage("/login")
                                .loginProcessingUrl("/authenticateTheUser")
                                .defaultSuccessUrl("/main", true)
                                .permitAll()
                )
                
            .logout(logout->
            
                    logout.permitAll()
            ).exceptionHandling(configurer->configurer.accessDeniedPage("/access-denied"));
                
        http.csrf((csrf->csrf.disable()));
        
        

        return http.build();
    }
    
    


}
