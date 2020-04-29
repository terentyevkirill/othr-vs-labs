package com.othr.vs.threadpoolwebserver;

import java.io.*;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.List;

// active class
public class RequestHandler implements Runnable {
    private final Socket clientRequest;

    public RequestHandler(Socket clientRequest) {
        this.clientRequest = clientRequest;
    }

    @Override
    public void run() {
        PrintWriter writer = null;
        try {
            // create reader & writer
            InputStream in = clientRequest.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            OutputStream out = clientRequest.getOutputStream();
            writer = new PrintWriter(out);

            // read & parse request
            String request = reader.readLine();
            String[] requestParts = request.split(" ");
            String fileName = requestParts[1];
            System.out.println("Requested file: " + fileName);

            System.out.println("Current path: " + Paths.get(".").toAbsolutePath().toString());
            List<String> contentLines = Files.readAllLines(Paths.get("src\\com\\othr\\vs\\threadpoolwebserver\\resources\\" + fileName));
            // write a response with header
            writer.println("HTTP/1.1 200 OK");
            writer.println("Content-Type: text/html");
            writer.print("Content-Length: ");
            writer.println(contentLines.stream().mapToInt(line -> line.length() + 1).sum());
            writer.println();       // delimiter
            contentLines.forEach(writer::println);
            writer.flush();         // send now
            clientRequest.close();  // close
        } catch (NoSuchFileException ex) {
            writer.println("HTTP/1.1 404 Not found");
            writer.println("Content-Type: text/html");
            writer.println("Content-Length: 31");
            writer.println();
            writer.println("Error 404: No such file found!");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
