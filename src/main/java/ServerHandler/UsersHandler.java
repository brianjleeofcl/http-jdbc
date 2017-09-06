package ServerHandler;

import Database.JavaDBConnection;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

import static java.lang.Integer.parseInt;

public class UsersHandler implements HttpHandler {
    private JavaDBConnection connection = new JavaDBConnection();
    private final Logger log = LoggerFactory.getLogger(UsersHandler.class);

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String[] params = exchange.getRequestURI().toString().split("/");

        log.info(Arrays.toString(params));

        switch (method) {
            case "GET": {
                switch (params.length) {
                    case 2:
                        handleGetUsers(exchange);
                        break;
                    case 3:
                        try {
                            handleGetSpecificUser(exchange, parseInt(params[2]));
                        } catch (SQLException e) {
                            e.printStackTrace();
                            sendNotFoundError(exchange);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            sendInternalError(exchange);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            sendBadRequestError(exchange);
                        }
                        break;
                    default: sendNotFoundError(exchange);
                }
                break;
            }
            case "POST": {
                if (params.length != 2) sendBadRequestError(exchange);
                else ;
            }
            default: sendBadRequestError(exchange);
        }
    }

    private void handleGetUsers(HttpExchange exchange) throws SQLException, JSONException, IOException {
        byte[] response = connection.getAllUsers().getBytes();
        exchange.sendResponseHeaders(200, response.length);
        sendResponse(exchange, response);
    }

    private void handleGetSpecificUser(HttpExchange exchange, int id) throws SQLException, JSONException, IOException {
        byte[] response = connection.getSpecificUser(id).getBytes();
        exchange.sendResponseHeaders(200, response.length);
        sendResponse(exchange, response);
    }

    private void handlePostRequest(httpExchange exchange) {
        
    }

    private void sendBadRequestError(HttpExchange exchange) throws IOException {
        byte[] response = "Unable to serve request".getBytes();
        exchange.sendResponseHeaders(400, response.length);
        sendResponse(exchange, response);
    }

    private void sendNotFoundError(HttpExchange exchange) throws IOException {
        byte[] response = "No resource found for given path".getBytes();
        exchange.sendResponseHeaders(404, response.length);
        sendResponse(exchange, response);
    }

    private void sendInternalError(HttpExchange exchange) throws IOException {
        byte[] response = "Internal Server Error".getBytes();
        exchange.sendResponseHeaders(500, response.length);
        sendResponse(exchange, response);
    }

    private void sendResponse(HttpExchange exchange, byte[] response) throws IOException {
        exchange.getResponseBody().write(response);
        exchange.getResponseBody().close();
        exchange.close();
    }
}
