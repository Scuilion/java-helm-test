import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.stream.Collectors;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Service {

    static final int PORT = 8080;

    public static void main(String... args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/read", new ListFile());
        server.createContext("/health", new Health());
        server.setExecutor(null);
        server.start();
        System.out.println("started on port: " + PORT);
    }

    static class ListFile implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            System.out.println("call to list files");
            final String files = listFiles();
            if(files.length() > 0) {
                t.sendResponseHeaders(200, files.length());
            } else {
                t.sendResponseHeaders(404, 0);
            }
            OutputStream os = t.getResponseBody();
            os.write(files.getBytes());
            os.close();
        }
    }

    static class Health implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "OK";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static String listFiles() {
        File[] files = new File("/cfg").listFiles(); 
        assert files != null;
        return Arrays.stream(files)
            .filter(f -> f.getName().contains(".yaml"))
            .map(File::getName)
            .collect(Collectors.joining(","));
    }
}
