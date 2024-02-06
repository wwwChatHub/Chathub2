import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class SimpleWebSocketServer {

    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.out.println("New connection: " + session.getRemoteAddress().getHostName());
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
        System.out.println("Received message: " + message);
        // Handle the received message here (e.g., broadcast to other clients)
    }

    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reason) {
        System.out.println("Connection closed: " + session.getRemoteAddress().getHostName());
    }

    public static void main(String[] args) {
        Server server = new Server(8080);
        try {
            org.eclipse.jetty.websocket.server.WebSocketHandler wsHandler = new org.eclipse.jetty.websocket.server.WebSocketHandler() {
                @Override
                public void configure(org.eclipse.jetty.websocket.server.ServerWebSocketContainer container) {
                    container.addEndpoint(SimpleWebSocketServer.class);
                }
            };
            server.setHandler(wsHandler);
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
