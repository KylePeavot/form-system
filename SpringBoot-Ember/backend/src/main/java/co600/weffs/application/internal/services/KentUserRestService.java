package co600.weffs.application.internal.services;

import co600.weffs.application.internal.model.error.UrlNotAvailableException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

@Service
public class KentUserRestService {

  public List<?> getKentUsersAsStringList() {
    try {
      URL url = new URL("http://raptor.kent.ac.uk/~ca469/weffs/users/");
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("GET");
      int status = con.getResponseCode();
      if (status == 200) {
        // Web response requires BufferedReader as data is streamed back.
        var buffer = new BufferedReader(new InputStreamReader(con.getInputStream()));
        return new ObjectMapper().readValue(buffer, List.class);
      } else {
        throw new UrlNotAvailableException("Raptor is unavailable");
      }
    } catch (Exception e) {
      Logger.getGlobal().log(Level.WARNING, e.toString());
      return List.of();
    }
  }

}
