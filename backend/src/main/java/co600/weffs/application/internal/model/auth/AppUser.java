package co600.weffs.application.internal.model.auth;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Data
@Setter(AccessLevel.NONE)
@AllArgsConstructor
@RequestScope
@Component
public class AppUser {

  private String username;
  private String email;
  private SamlRole role;

}
