package com.goaltracker.user.config;

import com.goaltracker.user.application.gateway.UserRepository;
import com.goaltracker.user.application.port.PasswordHasher;
import com.goaltracker.user.application.port.PasswordMatcher;
import com.goaltracker.user.application.port.TokenGenerator;
import com.goaltracker.user.application.port.TokenVerifier;
import com.goaltracker.user.application.usecases.*;
import com.goaltracker.user.infrastructure.UserMapper;
import com.goaltracker.user.infrastructure.adapter.BCryptPasswordHasher;
import com.goaltracker.user.infrastructure.adapter.BCryptPasswordMatcher;
import com.goaltracker.user.infrastructure.adapter.UserRepositoryAdapter;
import com.goaltracker.user.infrastructure.persistence.UserRepositoryJpa;
import com.goaltracker.user.infrastructure.security.UserDetailsServiceImpl;
import com.goaltracker.user.infrastructure.security.filter.JwtAuthenticationFilter;
import com.goaltracker.user.infrastructure.security.jwt.JwtTokenGenerator;
import com.goaltracker.user.infrastructure.security.jwt.JwtTokenVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserConfig {

    @Bean
    public UserRepository userRepository(UserRepositoryJpa repositoryJpa, UserMapper mapper){
        return new UserRepositoryAdapter(repositoryJpa, mapper);
    }

    @Bean
    public UserMapper userMapper(){
        return new UserMapper();
    }

    @Bean
    public CreateUserUseCase createUserUseCase(UserRepository repository, PasswordHasher passwordHasher){
        return new CreateUserUseCase(repository, passwordHasher);
    }

    @Bean
    public CalculatorXpUseCase calculatorXpUseCase(UserRepository repository){
        return new CalculatorXpUseCase(repository);
    }

    @Bean
    public PasswordHasher passwordHasher(){
        return new BCryptPasswordHasher();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PasswordMatcher passwordMatcher(PasswordEncoder passwordEncoder){
        return new BCryptPasswordMatcher(passwordEncoder);
    }

    @Bean
    public DeleteUserUseCase deleteUserUseCase(UserRepository userRepository){
        return new DeleteUserUseCase(userRepository);
    }

    @Bean
    public GetUserUseCase getUserUseCase(UserRepository userRepository){
        return new GetUserUseCase(userRepository);
    }

    @Bean
    public ListUsersUseCase listUsersUseCase(UserRepository userRepository){
        return new ListUsersUseCase(userRepository);
    }

    @Bean
    public UpdateUserUsecase updateUserUseCase(UserRepository userRepository){
        return new UpdateUserUsecase(userRepository);
    }

    @Bean
    public UpdateSkillsUseCase updateSkillsUseCase(UserRepository userRepository){
        return new UpdateSkillsUseCase(userRepository);
    }

    @Bean
    public AuthenticateUser authenticateUser(UserRepository userRepository, PasswordMatcher passwordMatcher,
                                             TokenGenerator tokenGenerator){
        return new AuthenticateUser(userRepository, passwordMatcher, tokenGenerator);
    }


    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(TokenVerifier verifier, UserDetailsServiceImpl userDetailsService){
        return new JwtAuthenticationFilter(verifier, userDetailsService);
    }

    @Bean
    public TokenVerifier tokenVerifier(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.issuer:service-user}") String issuer
    ) {
        return new JwtTokenVerifier(secret, issuer);
    }

    @Bean
    public TokenGenerator tokenGenerator(){
        return new JwtTokenGenerator();
    }

    @Bean
    public UserDetailsServiceImpl userDetailsService(UserRepository userRepository) {
        return new UserDetailsServiceImpl(userRepository);
    }
}
