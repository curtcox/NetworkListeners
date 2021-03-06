package com.curtcox;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Date;

final class Listener {

    private static void listenTo(DatagramSocket socket, PackerHandler handler) throws IOException {
        int port = socket.getLocalPort();
        System.out.println("Listening on " + port + " with " + handler);
        for (int i=0; i<1000; i++) {
            byte [] bytes = new byte[1024];
            DatagramPacket packet = new DatagramPacket(bytes,bytes.length);
            socket.receive(packet);
            System.out.println(port + " " + i + ") " + new Date() + " " + handler.parse(bytes));
        }
    }

    static void listenTo(int port, PackerHandler handler) {
        new Thread(() -> {
            try {
                listenTo(new DatagramSocket(port,null),handler);
            } catch (IOException e) {
                throw new RuntimeException(handler + " on " + port,e);
            }
        }).start();
    }

}
