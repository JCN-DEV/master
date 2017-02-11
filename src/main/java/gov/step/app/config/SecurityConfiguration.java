package gov.step.app.config;

import gov.step.app.security.*;
import gov.step.app.web.filter.CsrfCookieGeneratorFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.csrf.CsrfFilter;

import javax.inject.Inject;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Inject
    private Environment env;

    @Inject
    private AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler;

    @Inject
    private AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler;

    @Inject
    private AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler;

    @Inject
    private Http401UnauthorizedEntryPoint authenticationEntryPoint;

    @Inject
    private UserDetailsService userDetailsService;

    @Inject
    private RememberMeServices rememberMeServices;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Inject
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers("/scripts/**/*.{js,html}")
            .antMatchers("/bower_components/**")
            .antMatchers("/i18n/**")
            .antMatchers("/assets/**")
            .antMatchers("/swagger-ui/index.html")
            .antMatchers("/test/**")
            .antMatchers("/console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
            .ignoringAntMatchers("/websocket/**")
            .and()
            .addFilterAfter(new CsrfCookieGeneratorFilter(), CsrfFilter.class)
            .exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint)
            .and()
            .rememberMe()
            .rememberMeServices(rememberMeServices)
            .rememberMeParameter("remember-me")
            .key(env.getProperty("jhipster.security.rememberme.key"))
            .and()
            .formLogin()
            .loginProcessingUrl("/api/authentication")
            .successHandler(ajaxAuthenticationSuccessHandler)
            .failureHandler(ajaxAuthenticationFailureHandler)
            .usernameParameter("j_username")
            .passwordParameter("j_password")
            .permitAll()
            .and()
            .logout()
            .logoutUrl("/api/logout")
            .logoutSuccessHandler(ajaxLogoutSuccessHandler)
            .deleteCookies("JSESSIONID")
            .permitAll()
            .and()
            .headers()
            .frameOptions()
            .disable()
            .and()
            .authorizeRequests()
            .antMatchers("/api/cat*").permitAll()
            .antMatchers("/api/cats/catsWithJobCounter*").permitAll()
            .antMatchers("/api/upazilas*").permitAll()
            .antMatchers("/api/districts*").permitAll()
            .antMatchers("/api/divisions*").permitAll()
            .antMatchers("/api/instGenInfos*").permitAll()
            .antMatchers("/api/instGenInfosLocalitys*").permitAll()
            .antMatchers("/api/job*").permitAll()
            .antMatchers("/api/jobs/**").permitAll()
            .antMatchers("/api/bankBranchs/**").permitAll()
            .antMatchers("/api/employeeApplications/my").permitAll()
            .antMatchers("/api/country*").permitAll()
            .antMatchers("/api/countrys/byName").permitAll()
            .antMatchers("/api/organizationCategory*").permitAll()
            .antMatchers("/api/organizationType*").permitAll()
            .antMatchers("/api/institutes/upazila/**").permitAll()
            .antMatchers("/api/_search/**").permitAll()
            .antMatchers("/api/_search/jobs/**").permitAll()
            .antMatchers("/api/_search/curJobs/location/**").permitAll()
            .antMatchers("/api/_search/curJobs/employer/**").permitAll()
            .antMatchers("/api/_search/curJobs/cat/**").permitAll()
            .antMatchers("/api/_search/curJobs").permitAll()
            .antMatchers("/api/jobs/availableJobs").permitAll()
            .antMatchers("/api/register").permitAll()
            .antMatchers("/api/organizationCategorys/active").permitAll()
            .antMatchers("/api/employer*").permitAll()
            .antMatchers("/api/tempemployer*").permitAll()
            .antMatchers("/api/activate").permitAll()
            .antMatchers("/api/authenticate").permitAll()
            .antMatchers("/api/account/reset_password/init").permitAll()
            .antMatchers("/api/tempEmployer/checkCompanyName/**").permitAll()
            .antMatchers("/api/institutes/checkInstituteByName/**").permitAll()
            .antMatchers("/api/account/reset_password/finish").permitAll()
            .antMatchers("/api/account/checkLogin/**").permitAll()
            .antMatchers("/api/mail/active/**").permitAll()
            .antMatchers("/api/instGenInfo/checkDuplicateCode/**").permitAll()
            .antMatchers("/api/instGenInfo/checkDuplicateMpoCode/**").permitAll()
            .antMatchers("/api/instCategorys/**").permitAll()
            .antMatchers("/api/instLevels/**").permitAll()
            .antMatchers("/api/instEmplDesignations/**").permitAll()
            .antMatchers("/api/instGenInfo/checkDuplicateEmail/**").permitAll()
            .antMatchers("/api/institutes/byType/government").permitAll()
            .antMatchers("/api/instGenInfo/eiin/**").permitAll()
            .antMatchers("/api/account/checkEmail/**").permitAll()
            .antMatchers("/api/organizationTypes/active").permitAll()
            .antMatchers("/api/logs/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/api/audits/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/api/**").authenticated()
            .antMatchers("/metrics/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/health/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/trace/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/dump/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/shutdown/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/beans/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/configprops/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/info/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/autoconfig/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/env/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/trace/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/mappings/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/liquibase/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/v2/api-docs/**").permitAll()
            .antMatchers("/configuration/security").permitAll()
            .antMatchers("/configuration/ui").permitAll()
            .antMatchers("/swagger-ui/index.html").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/protected/**").authenticated()
            .antMatchers("/api/miscTypeSetups*").permitAll()
            .antMatchers("/api/hrDesignationSetups*").permitAll()
            .antMatchers("/api/hrDepartmentSetups*").permitAll();;

    }

    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }
}
