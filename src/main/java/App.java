import ServerHandler.UsersHandler;
import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;

import static java.lang.Integer.parseInt;

public class App {
    private final static Logger log = LoggerFactory.getLogger(App.class);

    private static int port;
    static {
        try {
            port = parseInt(System.getenv("PORT"));
        } catch (NumberFormatException e) {
            port = 5000;
        } finally {
            log.info("PORT set to {}", port);
        }
    }

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/users", new UsersHandler());
        server.setExecutor(null);
        server.start();
        log.info("Server listening on PORT {}", port);
    }
}
