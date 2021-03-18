package it.unical.dimes.reti.restserver;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

public class HttpServerLauncher {

    public static final String BASE_URI = "http://127.0.0.1:8080/api/";

    public static HttpServer startServer() {
        final ResourceConfig rc = new ResourceConfig().packages("it.unical.dimes.reti.restserver.service");
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println(
                String.format("L'applicazione è in funzione sull'indirizzo %s " +
                        "\nDai invio per stopparla...", BASE_URI));
        System.in.read();
        server.shutdownNow();
    }
}
