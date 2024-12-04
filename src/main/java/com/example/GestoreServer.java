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

            int prima = 0;
            String ricSplit = "";
            do {
                String richiesta = in.readLine();
                if(prima==0){
                    ricSplit= richiesta.split(" ")[0];
                }
                if (richiesta.isEmpty() && ricSplit.equals("GET")) {
                    String responseBody = "<html><body><b>Benvenuto nella mia pagina<b></body></html>";
                    out.writeBytes("HTTP/1.1 200 OK\r\n");
                    out.writeBytes("Content-Length: " + responseBody.length() + "\r\n");
                    out.writeBytes("Content-Type: text/html\r\n");
                    out.writeBytes("\r\n");
                    out.writeBytes(responseBody);
                    break;
                }
                prima++;
            } while (true);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
