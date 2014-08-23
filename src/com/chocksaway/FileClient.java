package com.chocksaway;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class FileClient {
    private static byte[] getFileData(String name) throws IOException {
        File myFile = new File(name);

        DataInputStream diStream = new DataInputStream(new FileInputStream(myFile));
        long len = (int) myFile.length();
        byte[] fileBytes = new byte[(int) len];
        int read = 0;
        int numRead = 0;
        while (read < fileBytes.length && (numRead = diStream.read(fileBytes, read,
                fileBytes.length - read)) >= 0) {
            read = read + numRead;
        }

        return fileBytes;
    }

    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.err.println(
                    "Usage: java FileClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try {
                Socket socket = new Socket(hostName, portNumber);
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                Student student = new Student(43, "Miles", FileClient.getFileData("/tmp/miles.jpg") );
                outputStream.writeObject(student);
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }
    }
}