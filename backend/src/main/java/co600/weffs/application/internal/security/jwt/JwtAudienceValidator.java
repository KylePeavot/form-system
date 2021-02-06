package co600.weffs.application.internal.security.jwt;

import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * Used to ensure the audience header received is the same as the one sent in the JWT.
 */
public class JwtAudienceValidator implements OAuth2TokenValidator<Jwt> {

  private final String audience;

  public JwtAudienceValidator(String audience) {
    this.audience = audience;
  }

  @Override
  public OAuth2TokenValidatorResult validate(Jwt jwt) {
    if (jwt.getAudience().contains(audience)) {
      return OAuth2TokenValidatorResult.success();
    } else {
      return OAuth2TokenValidatorResult.failure(new OAuth2Error(
          "invalid_token",
          "The required audience is missing",
          null
      ));
    }
  }
}
