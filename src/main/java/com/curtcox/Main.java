package com.curtcox;

public final class Main {

    static void listenToDhcp() { Listener.listenTo(67,new DhcpMessage.Parser()); }
    static void listenToMdns() { Listener.listenTo(5353,new MdnsMessage.Parser()); }

    public static void main(String[] args) {
        listenToDhcp();
        listenToMdns();
    }
}
