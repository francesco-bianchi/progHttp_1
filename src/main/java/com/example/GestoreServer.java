package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class GestoreServer extends Thread {
    Socket s;

    GestoreServer(Socket s) {
        this.s = s;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            do {
                String header;
                String firstLine = in.readLine();
                String[] ricIniziale = firstLine.split(" ");

                String method = ricIniziale[0];
                String resource = ricIniziale[1];
                String version = ricIniziale[2];

                do {
                    header = in.readLine();
                } while (!header.isEmpty());
                
                
                if (resource.equals("/index.html") || resource.equals("/")) {
                    String responseBody = "<html><body><b>Benvenuto nella mia pagina<b></body></html>";
                    out.writeBytes("HTTP/1.1 200 OK\r\n");
                    out.writeBytes("Content-Length: " + responseBody.length() + "\r\n");
                    out.writeBytes("Content-Type: text/html\r\n");
                    out.writeBytes("\r\n");
                    out.writeBytes(responseBody);
                    break;
                }
                else{
                    String responseBody = "<html><body><b>File non trovato<b></body></html>";
                    out.writeBytes("HTTP/1.1 404 Not found\r\n");
                    out.writeBytes("Content-Length: " + responseBody.length() + "\r\n");
                    out.writeBytes("Content-Type: text/html\r\n");
                    out.writeBytes("\r\n");
                    out.writeBytes(responseBody);
                    break;
                }
            } while (true);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
