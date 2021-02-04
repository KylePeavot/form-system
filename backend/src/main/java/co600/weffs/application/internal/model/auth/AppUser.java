package co600.weffs.application.internal.model.auth;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

/**
 * Store information about the currently logged in user.
 */
@Getter
@AllArgsConstructor
// This allows Jackson to convert this from a Map to a POJO without exposing the no-args-constructor internally.
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class AppUser {

  private String username;
  private String email;
  private SamlRole role;

}
