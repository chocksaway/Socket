package com.chocksaway;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        if (args.length != 1) {
            System.err.println("Usage: java FileServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        try (
                ServerSocket serverSocket =
                        new ServerSocket(Integer.parseInt(args[0]));
                Socket socket = serverSocket.accept();
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        ) {

            Student student = (Student) inputStream.readObject();
            student.getPicture();

            File dstFile = new File("milesOut.jpg");
            FileOutputStream fileOutputStream = new FileOutputStream(dstFile);
            fileOutputStream.write(student.getPicture());
            fileOutputStream.flush();
            fileOutputStream.close();

            System.out.println("Object received = " + student);
            socket.close();
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}