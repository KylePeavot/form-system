package co600.weffs.application.utils.routes;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodCall;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import co600.weffs.application.internal.controller.AuthorizationTestController;
import java.net.URI;
import java.util.Map;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class Router {

  public static String determineRoute(Object builder) {
    return MvcUriComponentsBuilder.fromMethodCall(builder).build().toUriString();
  }

}
