package co600.weffs.application.internal.model.auth;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(AccessLevel.PRIVATE)
public class TokenResponse {

  private String sub;
  private String family_name;
  private String nickname;
  private String name;
  private String picture;
  private String updated_at;
  private String email;

}
