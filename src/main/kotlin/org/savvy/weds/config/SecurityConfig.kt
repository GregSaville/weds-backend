package org.savvy.weds.config

import org.savvy.weds.input.HidalgosConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

    @Bean
    fun userDetailsService(): InMemoryUserDetailsManager {
        val users: List<UserDetails> = HidalgosConfig.adminUsers.map { admin ->
            val roles = admin.roles.toTypedArray()
            User.withUsername(admin.username)
                .password("{noop}" + admin.password)
                .roles(*roles)
                .build()
        }
        return InMemoryUserDetailsManager(users)
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/public/**").permitAll()
                    .requestMatchers("/admin/**").authenticated()
                    .anyRequest().permitAll()
            }
            .httpBasic { }
        return http.build()
    }
}
