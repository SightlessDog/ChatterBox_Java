package com.Elyess;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)){
           Socket socket  = serverSocket.accept() ;
           System.out.println("Client Connected");
           try (Socket socket2= new Socket("127.0.0.1", 5001)) {
               ReaderThread reader = new ReaderThread(socket);
               reader.start();
               PrintWriter out = new PrintWriter(socket2.getOutputStream());
               Scanner scanner = new Scanner (System.in);
               String output ;
               do {
                   output = scanner.nextLine();
                   out.print(output);
                   System.out.println("Server sent : " + output);
               } while (!output.equals("exit"));

           }} catch (Exception e) {
            e.getMessage();
        }
    }

}

class WriterThread extends Thread {
    private Socket socket ;
    public WriterThread (Socket socket) {
        this.socket = socket ;
    }

    @Override
    public void run () {
        try {
            PrintWriter output = new PrintWriter(this.socket.getOutputStream(),true);
            System.out.println("Thread writer working from server");
            String echoString;
            Scanner scanner = new Scanner(System.in);
            do {
                echoString = scanner.nextLine();
                output.print(echoString);
                System.out.println("you sent "+ echoString);
            } while (!echoString.equals("exit"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

class ReaderThread extends Thread {
    private Socket socket;

    public ReaderThread(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        try {

            BufferedReader input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            System.out.println("Thread reader working from server");
            String response = "";
            while (true) {
                response = input.readLine();
                System.out.println("Client sent  :"+ response);
                if (response.equals("exit")){
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
