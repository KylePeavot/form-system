package co600.weffs.application.internal.security.jwt;

import co600.weffs.application.internal.model.auth.AppUser;
import co600.weffs.application.internal.model.auth.SamlRole;
import co600.weffs.application.internal.model.auth.TokenResponse;
import co600.weffs.application.internal.model.error.NotAuthorizedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * When a request has been made, intercept it and find the method to be used.
 * If the method has a {@link MustBeAuthorized} annotation then validate the token.
 */
public class JwtValidationInterceptor extends HandlerInterceptorAdapter {

  private static String USERINFO_ENDPOINT = "https://weffs.eu.auth0.com/userinfo/";

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    if (handler instanceof HandlerMethod) {
      var handlerMethod = (HandlerMethod) handler;
      if (handlerMethod.hasMethodAnnotation(MustBeAuthorized.class)) {
        var token = Optional.ofNullable(request.getHeader("X-Once-Token"));
        if (token.isPresent()) {
          var user = getUserFromToken(token.get());
          request.setAttribute("User", user);
          return true;
        }
        return false;
      }
    }
    return super.preHandle(request, response, handler);
  }

  private AppUser getUserFromToken(String token) throws IOException {
    var payload = verifyToken(token);
    var payloadAsString = payload.lines().collect(Collectors.joining());
    var mapper = new ObjectMapper();
    var tokenResponse = mapper.readValue(payloadAsString, TokenResponse.class);
    var role = SamlRole.getFromSamlReference(tokenResponse.getFamily_name());
    return new AppUser(tokenResponse.getName(), tokenResponse.getEmail(), role);
  }

  /**
   * Send the access token to auth0 servers to validate the token.
   *
   * @param token
   * @return
   * @throws IOException
   */
  private BufferedReader verifyToken(String token) throws IOException {
    URL url = new URL(USERINFO_ENDPOINT);
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("GET");
    con.setRequestProperty("Authorization", String.format("Bearer %s", token));
    int status = con.getResponseCode();
    if (status == 200) {
      // Web response requires BufferedReader as data is streamed back.
      return new BufferedReader(new InputStreamReader(con.getInputStream()));
    } else {
      throw new NotAuthorizedException(
          "Attempted to access a restricted page with incorrect authentication details.");
    }
  }
}
