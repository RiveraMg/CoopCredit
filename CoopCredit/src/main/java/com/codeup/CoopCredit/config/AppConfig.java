package com.codeup.CoopCredit.config;

import com.codeup.CoopCredit.application.usecase.application.CreateCreditApplicationUseCaseImpl;
import com.codeup.CoopCredit.application.usecase.application.EvaluateCreditApplicationUseCaseImpl;
import com.codeup.CoopCredit.application.usecase.application.GetAllPendingApplicationsUseCaseImpl;
import com.codeup.CoopCredit.application.usecase.application.GetApplicationsByMemberUseCaseImpl;
import com.codeup.CoopCredit.application.usecase.application.GetCreditApplicationByIdUseCaseImpl;
import com.codeup.CoopCredit.application.usecase.auth.LoginUserUseCaseImpl;
import com.codeup.CoopCredit.application.usecase.auth.RegisterUserUseCaseImpl;
import com.codeup.CoopCredit.application.usecase.member.GetMemberByDocumentUseCaseImpl;
import com.codeup.CoopCredit.application.usecase.member.GetMemberByIdUseCaseImpl;
import com.codeup.CoopCredit.application.usecase.member.RegisterMemberUseCaseImpl;
import com.codeup.CoopCredit.application.usecase.member.UpdateMemberUseCaseImpl;
import com.codeup.CoopCredit.domain.ports.out.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // Member Use Cases
    @Bean
    public RegisterMemberUseCaseImpl registerMemberUseCase(IMemberRepositoryPort memberRepository) {
        return new RegisterMemberUseCaseImpl(memberRepository);
    }

    @Bean
    public GetMemberByIdUseCaseImpl getMemberByIdUseCase(IMemberRepositoryPort memberRepository) {
        return new GetMemberByIdUseCaseImpl(memberRepository);
    }

    @Bean
    public GetMemberByDocumentUseCaseImpl getMemberByDocumentUseCase(IMemberRepositoryPort memberRepository) {
        return new GetMemberByDocumentUseCaseImpl(memberRepository);
    }

    @Bean
    public UpdateMemberUseCaseImpl updateMemberUseCase(IMemberRepositoryPort memberRepository) {
        return new UpdateMemberUseCaseImpl(memberRepository);
    }

    // Auth Use Cases
    @Bean
    public RegisterUserUseCaseImpl registerUserUseCase(IUserRepositoryPort userRepository, PasswordEncoder passwordEncoder) {
        return new RegisterUserUseCaseImpl(userRepository, passwordEncoder);
    }

    @Bean
    public LoginUserUseCaseImpl loginUserUseCase(IUserRepositoryPort userRepository, PasswordEncoder passwordEncoder) {
        return new LoginUserUseCaseImpl(userRepository, passwordEncoder);
    }

    // Credit Application Use Cases
    @Bean
    public CreateCreditApplicationUseCaseImpl createCreditApplicationUseCase(
            ICreditApplicationRepositoryPort applicationRepository,
            IMemberRepositoryPort memberRepository) {
        return new CreateCreditApplicationUseCaseImpl(applicationRepository, memberRepository);
    }

    @Bean
    public GetCreditApplicationByIdUseCaseImpl getCreditApplicationByIdUseCase(ICreditApplicationRepositoryPort applicationRepository) {
        return new GetCreditApplicationByIdUseCaseImpl(applicationRepository);
    }

    @Bean
    public GetApplicationsByMemberUseCaseImpl getApplicationsByMemberUseCase(ICreditApplicationRepositoryPort applicationRepository) {
        return new GetApplicationsByMemberUseCaseImpl(applicationRepository);
    }

    @Bean
    public GetAllPendingApplicationsUseCaseImpl getAllPendingApplicationsUseCase(ICreditApplicationRepositoryPort applicationRepository) {
        return new GetAllPendingApplicationsUseCaseImpl(applicationRepository);
    }

    @Bean
    public EvaluateCreditApplicationUseCaseImpl evaluateCreditApplicationUseCase(
            ICreditApplicationRepositoryPort applicationRepository,
            IMemberRepositoryPort memberRepository,
            IRiskEvaluationRepositoryPort riskEvaluationRepository,
            IRiskCentralPort riskCentralPort) {
        return new EvaluateCreditApplicationUseCaseImpl(applicationRepository, memberRepository, riskEvaluationRepository, riskCentralPort);
    }
}
