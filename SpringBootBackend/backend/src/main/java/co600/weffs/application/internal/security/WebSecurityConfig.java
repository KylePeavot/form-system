/**
 * Production produces CORS errors when live because the incoming requests are from the same origin.
 * To fix this @Profile is used to ensure that CORS is only enabled when developing. (serve on :8080, app on :80)
 *
 * To load this security bean:
 *  - In your run config set "development" as your active profile.
 */

package co600.weffs.application.internal.security;

import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;

@Profile("development")
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
        http.csrf().disable();
    }

}
