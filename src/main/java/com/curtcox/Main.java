package com.curtcox;

import java.io.*;
import java.net.*;
import java.util.*;

public class Main {

    static void listenTo(DatagramSocket socket) throws IOException {
        System.out.println("Listening...");
        for (int i=0; i<1000; i++) {
            byte [] bytes = new byte[1024];
            DatagramPacket packet = new DatagramPacket(bytes,bytes.length);
            socket.receive(packet);
            System.out.println(i + ") " + new Date() + " " + DhcpMessage.parse(bytes));
        }
    }

    static void listenToDatagramSocket() throws Exception {
        listenTo(new DatagramSocket(67,null));
    }

    public static void main(String[] args) throws Exception {
        listenToDatagramSocket();
    }
}
