package com.curtcox;

import java.io.ByteArrayOutputStream;

final class Util {

    static String hexdump(byte[] bytes) {
        StringBuilder out1 = new StringBuilder();
        StringBuilder out2 = new StringBuilder();
        for (int i=0; i<bytes.length; i++) {
            byte b = bytes[i];
            if (i>1024 || b==-1) {
                break;
            } else {
                out1.append(" " + index(i));
                out2.append(" " + hex(b));
            }
        }
        return out1 + "\n" + out2;
    }

    static String hex(byte[] bytes) {
        StringBuilder out = new StringBuilder();
        for (byte b : bytes) {
            out.append(" " + hex(b));
        }
        return out.toString();
    }

    static String index(int i) {
        String s = "  " + i;
        return s.substring(s.length() - 2);
    }

    static String hex(byte b) { return String.format("%02x", b); }

    static byte[] nextBytes(byte[] bytes, int start, int maxLength) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        for (int i=start; i<bytes.length; i++) {
            byte b = bytes[i];
            if (b==-1 || out.size() == maxLength) {
                break;
            } else {
                out.write(b);
            }
        }
        return out.toByteArray();
    }

    static String next(byte[] bytes, int start, int maxLength) {
        return hexdump(nextBytes(bytes,start,maxLength));
    }

    static boolean printable(byte b) { return b >= 32 && b < 128; }

    static String printableChar(byte b) {
        return printable(b) ? new String(new byte[] {b}) : "?";
    }

    static String printable(byte[] bytes) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        for (int i=0; i<bytes.length; i++) {
            byte b = bytes[i];
            if (b==-1) {
                break;
            } else {
                if (printable(b)) {
                    out.write(b);
                }
            }
        }
        return new String(out.toByteArray());
    }

}
