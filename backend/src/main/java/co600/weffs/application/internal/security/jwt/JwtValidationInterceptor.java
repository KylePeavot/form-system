package co600.weffs.application.internal.security.jwt;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * When a request has been made, intercept it and find the method to be used.
 * If the method has a {@link MustBeAuthorized} annotation then validate JWT token passed via SPA.
 */
public class JwtValidationInterceptor extends HandlerInterceptorAdapter {

  private static String ISSUER = "https://weffs.eu.auth0.com/";

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    if (handler instanceof HandlerMethod) {
      var handlerMethod = (HandlerMethod) handler;
      if (handlerMethod.hasMethodAnnotation(MustBeAuthorized.class)) {
        var token = Optional.ofNullable(request.getHeader("X-Once-Token"));
        var aud = Optional.ofNullable(request.getHeader("X-Once-Aud"));
        if (token.isPresent() && aud.isPresent()) {
          var jwtDecoder = (NimbusJwtDecoder) JwtDecoders.fromOidcIssuerLocation(ISSUER);
          JwtDecoderUtils.decode(jwtDecoder, aud.get(), ISSUER);
          var user = JwtDecoderUtils.createAppUserFromDecoder(jwtDecoder, token.get());
          request.setAttribute("User", user);
          return true;
        }
        return false;
      }
    }
    return super.preHandle(request, response, handler);
  }
}
