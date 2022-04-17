package com.curtcox;

public class Main {

    static void listenToDhcp() {
        Listener.listenTo(67,new DhcpMessage.Parser());
    }

    public static void main(String[] args) {
        listenToDhcp();
    }
}
