package ro.ubb.boardgameapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

//package ro.ubb.boardgameapp.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import java.util.Arrays;
//
@Configuration
//@EnableWebMvc

public class WebConfig {


//        @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedOrigins("http://localhost:4200", "http://localhost:8080")
////                        .allowedOrigins("*")
//                        .allowedMethods("GET", "PUT", "POST", "DELETE")
//                        .allowedHeaders("*");
//            }
//        };
//    }
//
//

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
////                .cors(Customizer.withDefaults()) // by default uses a Bean by the name of corsConfigurationSource
//            .authorizeRequests(auth -> auth
//                    .anyRequest().authenticated())
////            .httpBasic(Customizer.withDefaults())
//                .csrf().disable()
//                .build();
//versiunea de mai jos merge
        return http
                .cors().and()
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .csrf().disable()
                .build();
    }
//

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "PUT", "POST", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
