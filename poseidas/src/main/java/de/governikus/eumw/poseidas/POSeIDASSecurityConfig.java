/*
 * Copyright (c) 2020 Governikus KG. Licensed under the EUPL, Version 1.2 or as soon they will be approved by
 * the European Commission - subsequent versions of the EUPL (the "Licence"); You may not use this work except
 * in compliance with the Licence. You may obtain a copy of the Licence at:
 * http://joinup.ec.europa.eu/software/page/eupl Unless required by applicable law or agreed to in writing,
 * software distributed under the Licence is distributed on an "AS IS" basis, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 */

package de.governikus.eumw.poseidas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import de.governikus.eumw.eidascommon.ContextPaths;


/**
 * This configuration ensures that only authenticated users can access the admin interface.
 *
 * @author bpr
 */
@Configuration
public class POSeIDASSecurityConfig extends WebSecurityConfigurerAdapter
{

  @Autowired
  PasswordFileAuthenticationProvider authenticationProvider;

  @Override
  protected void configure(HttpSecurity http) throws Exception
  {
    http.csrf()
        .ignoringAntMatchers(ContextPaths.EIDAS_CONTEXT_PATH + "/**")
        .and()
        .authorizeRequests()
        .antMatchers(ContextPaths.ADMIN_CONTEXT_PATH + "/list/**",
                     ContextPaths.ADMIN_CONTEXT_PATH + "/details/**")
        .authenticated()
        .antMatchers("/**")
        .permitAll()
        .and()
        .formLogin()
        .loginPage(ContextPaths.ADMIN_CONTEXT_PATH + "/login")
        .defaultSuccessUrl(ContextPaths.ADMIN_CONTEXT_PATH + "/list")
        .permitAll()
        .and()
        .logout()
        .logoutRequestMatcher(new AntPathRequestMatcher(ContextPaths.ADMIN_CONTEXT_PATH + "/logout",
                                                        HttpMethod.GET.name()))
        .logoutSuccessUrl(ContextPaths.ADMIN_CONTEXT_PATH + "/login?success")
        .permitAll()
        .and()
        .headers()
        // TODO: Change to scrip-src 'self' as soon as thymeleaf is used in the middleware and JS is loaded
        // via URL and not inline
        .contentSecurityPolicy("script-src 'self'");
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth)
  {
    auth.authenticationProvider(authenticationProvider);
  }
}
