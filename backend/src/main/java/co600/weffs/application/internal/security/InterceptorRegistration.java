package co600.weffs.application.internal.security;

import co600.weffs.application.internal.security.jwt.JwtValidationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * All interceptors must be registered as they aren't detected via the usual annotations.
 */

@Configuration
public class InterceptorRegistration implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new JwtValidationInterceptor());
  }
}
