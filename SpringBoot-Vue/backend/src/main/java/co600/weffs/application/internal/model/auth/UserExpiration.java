package co600.weffs.application.internal.model.auth;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserExpiration {

  private final AppUser appUser;
  private final LocalDateTime userExpirationDateTime;

}
