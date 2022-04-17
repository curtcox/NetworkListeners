package com.curtcox;

import static com.curtcox.Util.hexdump;
import static com.curtcox.Util.printable;

final class UpnpMessage {

    final static class Parser implements PackerHandler {

        @Override
        public String parse(byte[] bytes) { return UpnpMessage.parse(bytes).toString(); }
    }

    private final byte[] bytes;

    UpnpMessage(byte[] bytes) {
        this.bytes = bytes;
    }

    static UpnpMessage parse(byte[] bytes) {
        return new UpnpMessage(bytes);
    }

    public String toString() {
        return hexdump(bytes) + "\n" + printable(bytes);
    }

}
