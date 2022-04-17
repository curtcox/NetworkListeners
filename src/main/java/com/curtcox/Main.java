package com.curtcox;

import static com.curtcox.Listener.listenTo;

public final class Main {

    public static void main(String[] args) {
        listenTo(67,new DhcpMessage.Parser());
        //listenTo(1900,new UpnpMessage.Parser());
        listenTo(1900,new UpnpMessage.Parser());
        listenTo(3702,new GenericMessage.Parser());
    }
}
