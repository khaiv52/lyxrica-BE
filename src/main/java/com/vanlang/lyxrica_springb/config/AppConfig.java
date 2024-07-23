    package com.vanlang.lyxrica_springb.config;
    
    import jakarta.validation.constraints.NotNull;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
    import org.springframework.security.config.http.SessionCreationPolicy;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
    import org.springframework.web.cors.CorsConfiguration;
    import org.springframework.web.cors.CorsConfigurationSource;
    import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
    
    import java.util.List;
    
    @Configuration
    @EnableWebSecurity
    public class AppConfig {
    
        @Bean
        public SecurityFilterChain securityFilterChain(@NotNull HttpSecurity http) throws Exception {
            http.sessionManagement(session -> session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests(authorize -> authorize
                            .requestMatchers("/api/**").authenticated()
                            .anyRequest().permitAll())
                    .addFilterBefore(new JwtValidator(), BasicAuthenticationFilter.class)
                    .csrf(AbstractHttpConfigurer::disable)
                    .cors(cors -> cors.configurationSource(corsConfigurationSource()));
    
            return http.build();
        }
    
        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration cfg = new CorsConfiguration();
            cfg.setAllowedOrigins(List.of(
                    "http://localhost:3000",
                    "http://localhost:4200",
                    "https://lyxirca-shop.vercel.app",
                    "https://lyxrica-be.onrender.com"
            ));
            cfg.setAllowedHeaders(List.of("*"));
            cfg.setAllowedMethods(List.of("*"));
            cfg.setAllowCredentials(true);
            cfg.setMaxAge(3600L);
    
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", cfg);
    
            return source;
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }
