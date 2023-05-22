//package ro.ubb.boardgameapp.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//public class WeSecurityConfig {
//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        return http
//////                .cors(Customizer.withDefaults()) // by default uses a Bean by the name of corsConfigurationSource
////            .authorizeRequests(auth -> auth
////                    .anyRequest().authenticated())
//////            .httpBasic(Customizer.withDefaults())
////                .csrf().disable()
////                .build();
////versiunea de mai jos merge
//        return http
//                .cors().and()
//                .authorizeRequests()
//                .anyRequest().permitAll()
//                .and()
//                .csrf().disable()
//                .build();
//    }
//}
