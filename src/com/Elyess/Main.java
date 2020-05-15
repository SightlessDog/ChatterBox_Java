package com.Elyess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	  try (Socket socket = new Socket("127.0.0.1",8000)){
	      //receives
        BufferedReader echoes = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //sends
        PrintWriter stringToEcho = new PrintWriter(socket.getOutputStream(),true);
        Scanner scanner = new Scanner(System.in);
        //holds the sent message
        String echoString ;
        //holds the received message
        String response ;

        do {
            System.out.println("Enter a string to be echoed: ");
            echoString = scanner.nextLine();
            stringToEcho.println(echoString);
            if (!echoString.equals("exit")) {
                response = echoes.readLine();
                System.out.println(response);
            }

        } while (!echoString.equals("exit"));
    } catch (IOException e ){
	      e.printStackTrace();
    }
    }
}
