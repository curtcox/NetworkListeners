package com.curtcox;

import static com.curtcox.Util.hexdump;
import static com.curtcox.Util.printable;

final class GenericMessage {

    final static class Parser implements PackerHandler {

        @Override
        public String parse(byte[] bytes) { return GenericMessage.parse(bytes).toString(); }
    }

    private final byte[] bytes;

    GenericMessage(byte[] bytes) {
        this.bytes = bytes;
    }

    static GenericMessage parse(byte[] bytes) {
        return new GenericMessage(bytes);
    }

    public String toString() {
        return hexdump(bytes) + "\n" + printable(bytes);
    }

}
