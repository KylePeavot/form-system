package co600.weffs.application.internal.security.jwt;

import co600.weffs.application.internal.model.auth.AppUser;
import co600.weffs.application.internal.model.auth.SamlRole;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

/**
 * Used to abstract decode-specific tasks outside of interceptor.
 */
public class JwtDecoderUtils {

  public static void decode(NimbusJwtDecoder decoder, String audience, String issuer) {
    OAuth2TokenValidator<Jwt> audienceValidator = new JwtAudienceValidator(audience);
    OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer);
    OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);
    decoder.setJwtValidator(withAudience);
  }

  public static AppUser createAppUserFromDecoder(NimbusJwtDecoder decoder, String token) {
    var decoded = decoder.decode(token);
    var name = decoded.getClaimAsString("name");
    var email = decoded.getClaimAsString("email");
    var role = SamlRole.getFromSamlReference(decoded.getClaimAsString("family_name"));
    return new AppUser(name, email, role);
  }

}
