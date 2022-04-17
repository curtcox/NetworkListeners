package com.curtcox;

import static com.curtcox.Util.*;

final class MdnsMessage {

    final static class Parser implements PackerHandler {

        @Override
        public String parse(byte[] bytes) { return MdnsMessage.parse(bytes).toString(); }
    }

    private final byte[] bytes;

    MdnsMessage(byte[] bytes) {
        this.bytes = bytes;
    }

    static MdnsMessage parse(byte[] bytes) {
        return new MdnsMessage(bytes);
    }

    public String toString() {
        return hexdump(bytes) + "\n" +
                printable(bytes);
    }

}
