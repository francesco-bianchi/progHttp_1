package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
                System.out.println(firstLine);
                String[] ricIniziale = firstLine.split(" ");

                String method = ricIniziale[0];
                String resource = ricIniziale[1];
                String version = ricIniziale[2];

                do {
                    header = in.readLine();
                    System.out.println(header);
                } while (!header.isEmpty());
                
                
                if (resource.equals("/index.html") || resource.equals("/")) {
                    File file = new File("htdocs/index.html");
                    out.writeBytes("HTTP/1.1 200 OK\r\n");
                    out.writeBytes("Content-Length: " + file.length() + "\r\n");
                    out.writeBytes("Content-Type: text/html\r\n");
                    out.writeBytes("\r\n");

                    InputStream input = new FileInputStream(file);
                    byte[] buf = new byte[8192];
                    int n;
                    while((n = input.read(buf)) != -1){
                        out.write(buf, 0, n);
                    }
                    input.close();
                    
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
            s.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
