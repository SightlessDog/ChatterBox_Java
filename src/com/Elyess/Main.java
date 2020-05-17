package com.Elyess;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        try  (Socket socket = new Socket("127.0.0.1",5000)){
            try (ServerSocket serverSocket = new ServerSocket(5001)){
                Socket socket2 = serverSocket.accept();
                System.out.println("Server connected");
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                ReaderThreadClient reader = new ReaderThreadClient(socket2);
                reader.start();
                Scanner scanner = new Scanner(System.in);
                String echoString;
                String response = "";
                do {
                    echoString = scanner.nextLine();
                    out.println(echoString);
                    System.out.println("you sent " + echoString);
                } while (!echoString.equals("exit"));

            }  }

         catch (Exception e) {
            e.printStackTrace();
        }


    }
}

class Writer extends  Thread  {
    private Socket socket ;
    public Writer () {
    this.socket = socket ;
    }
    @Override
    public void run () {
        try  {

            System.out.println("Client connecting ...");
            PrintWriter stringToEcho = new PrintWriter(this.socket.getOutputStream(),true);
            Scanner scanner = new Scanner(System.in);
            String echoString = " " ;
            do {
                echoString = scanner.nextLine();
                if (!echoString.equals(null))
                stringToEcho.print(echoString);
            } while (!echoString.equals("exit"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class ReaderThreadClient extends Thread {
    private Socket socket;

    public ReaderThreadClient(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        try  {

            BufferedReader input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            System.out.println("Thread reader working from client");
            String response = "";
            while (( input.readLine()) != null )  {
                System.out.println(input.readLine());
//            response = input.readLine();
//            System.out.println("Server sent  :"+ response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}