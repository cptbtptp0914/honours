package au.com.martinponce.honours.server;

import au.com.martinponce.honours.core.Names;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Arrays;

import static java.lang.System.exit;

public class Main {

  private static final Logger LOG = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {
    initSecurity();
    initServer();
  }

  private static void initServer() {
    try {
      Naming.rebind(Names.AUTH_NAME, new Auth());
      Naming.rebind(Names.HONOURS_NAME, new HonoursEngine());
      Arrays.stream(Naming.list(Names.HONOURS_NAME))
          .forEach(i -> LOG.info("Server bound {}", i));
      LOG.info("Server waiting");
    } catch (MalformedURLException e) {
      LOG.error("Malformed url", e);
      exit(1);
    } catch (RemoteException e) {
      LOG.error("Remote exception", e);
      exit(1);
    }
  }

  private static void initSecurity() {
    if (System.getSecurityManager() ==  null)
      System.setSecurityManager(new SecurityManager());
  }
}
