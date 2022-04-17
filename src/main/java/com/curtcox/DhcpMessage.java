package com.curtcox;

import java.net.*;
import java.util.*;
import java.util.stream.*;

import static com.curtcox.Util.*;

final class DhcpMessage {

    final static class Parser implements PackerHandler {

        @Override
        public String parse(byte[] bytes) { return DhcpMessage.parse(bytes).toString(); }
    }

    private final byte[] bytes;

    DhcpMessage(byte[] bytes) {
        this.bytes = bytes;
    }

    static DhcpMessage parse(byte[] bytes) {
        return new DhcpMessage(bytes);
    }

    String head() { return next(bytes,0,42); }

    String tail() { return next(bytes,44+192,500); }

    Map<Byte,byte[]> options() {
        byte[] optionsBytes = nextBytes(bytes,48+192,500);
        Map<Byte,byte[]> out = new HashMap<>();
        int at = 0;
        while (at + 2 <optionsBytes.length) {
            Byte key = optionsBytes[at];
            int len = optionsBytes[at+1];
            byte[] value = nextBytes(optionsBytes,at + 2, len);
            out.put(key,value);
            at = at + len + 2;
        }
        return out;
    }

    String optionsString() {
        return options().entrySet()
                .stream()
                .collect(Collectors.toMap(e -> option(e.getKey()), e -> value(e.getValue())))
                .toString();
    }

    static String option(byte b) {
        if (b==host_name)            return b + " Host name";
        if (b==requested_ip_address) return b + " Requested IP address";
        if (b==51) return b + " IP address lease time";
        if (b==message_type) return b + " DHCP message type";
        if (b==54) return b + " Server identifier";
        if (b==55) return b + " Parameter request list";
        if (b==57) return b + " Maximum DHCP message size";
        if (b==61) return b + " Client-identifier";
        return b + "";
    }

    static final byte host_name = 12;
    String hostName() {
        byte[] bytes = options().get(host_name);
        return bytes == null ? null : new String(bytes);
    }

    static final byte message_type = 53;
    String messageType() {
        byte[] bytes = options().get(message_type);
        if (bytes==null || bytes.length != 1) {
            return null;
        }
        byte b = bytes[0];
        if (b==1) return b + " Discover";
        if (b==2) return b + " Offer";
        if (b==3) return b + " Request";
        if (b==4) return b + " Decline";
        if (b==5) return b + " Ack";
        if (b==6) return b + " Nak";
        if (b==7) return b + " Release";
        return b + "?";
    }

    static final byte requested_ip_address = 50;
    InetAddress requestedIpAddress()  {
        byte[] bytes = options().get(requested_ip_address);
        if (bytes==null || bytes.length < 4) {
            return null;
        }
        try {
            return InetAddress.getByAddress(bytes);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    static String value(byte[] bytes) {
        StringBuilder out1 = new StringBuilder();
        StringBuilder out2 = new StringBuilder();
        for (int i=0; i<bytes.length; i++) {
            byte b = bytes[i];
            if (i>1024) {
                break;
            } else {
                out1.append(hex(b));
                out2.append(printableChar(b));
            }
        }
        return bytes.length + ":" + out1 + ":" + out2;
    }

    String mac() { return hex(nextBytes(bytes,28,6)); }

    public String dump() {
        return  head() + "\n" +
                tail() + "\n" +
                mac() + "\n" +
                optionsString() + "\n" +
                printable(bytes) + "\n" +
                messageType() + "\n" +
                hostName() + "\n" +
                requestedIpAddress();
    }

    public String toString() {
        return mac() + " " + messageType() + " " + hostName() + " " + requestedIpAddress();
    }
}
