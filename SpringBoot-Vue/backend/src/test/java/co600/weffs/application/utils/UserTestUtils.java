package co600.weffs.application.utils;

import co600.weffs.application.internal.model.auth.AppUser;
import co600.weffs.application.internal.model.auth.SamlRole;

/**
 * Helper class to create a basic user
 */
public class UserTestUtils {

  public static AppUser createAppUser(String name, SamlRole role) {
    return new AppUser(name, name + "@kent.ac.uk", role);
  }

  public static AppUser createDefaultUndergraduateAppUser() {
    return createAppUser("test_user", SamlRole.UNDERGRADUATE);
  }

}
