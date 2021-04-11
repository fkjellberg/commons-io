/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.junit.jupiter.api.Test;


/**
 *
 */
public class HexDumpTest {

    private char toHex(final int n) {
        final char[] hexChars =
                {
                    '0', '1', '2', '3', '4', '5', '6', '7',
                    '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
                };

        return hexChars[n % 16];
    }

    @Test
    public void testDumpAppendable()
            throws IOException {
        final byte[] testArray = new byte[256];
        StringBuilder out;

        // create test data to dump
        for (int j = 0; j < 256; j++) {
            testArray[j] = (byte) j;
        }

        // verify proper behavior
        out = new StringBuilder();
        HexDump.dump(testArray, out);
        assertEquals(
            "00000000 00 01 02 03 04 05 06 07 08 09 0A 0B 0C 0D 0E 0F ................" + HexDump.EOL +
            "00000010 10 11 12 13 14 15 16 17 18 19 1A 1B 1C 1D 1E 1F ................" + HexDump.EOL +
            "00000020 20 21 22 23 24 25 26 27 28 29 2A 2B 2C 2D 2E 2F  !\"#$%&'()*+,-./" + HexDump.EOL +
            "00000030 30 31 32 33 34 35 36 37 38 39 3A 3B 3C 3D 3E 3F 0123456789:;<=>?" + HexDump.EOL +
            "00000040 40 41 42 43 44 45 46 47 48 49 4A 4B 4C 4D 4E 4F @ABCDEFGHIJKLMNO" + HexDump.EOL +
            "00000050 50 51 52 53 54 55 56 57 58 59 5A 5B 5C 5D 5E 5F PQRSTUVWXYZ[\\]^_" + HexDump.EOL +
            "00000060 60 61 62 63 64 65 66 67 68 69 6A 6B 6C 6D 6E 6F `abcdefghijklmno" + HexDump.EOL +
            "00000070 70 71 72 73 74 75 76 77 78 79 7A 7B 7C 7D 7E 7F pqrstuvwxyz{|}~." + HexDump.EOL +
            "00000080 80 81 82 83 84 85 86 87 88 89 8A 8B 8C 8D 8E 8F ................" + HexDump.EOL +
            "00000090 90 91 92 93 94 95 96 97 98 99 9A 9B 9C 9D 9E 9F ................" + HexDump.EOL +
            "000000A0 A0 A1 A2 A3 A4 A5 A6 A7 A8 A9 AA AB AC AD AE AF ................" + HexDump.EOL +
            "000000B0 B0 B1 B2 B3 B4 B5 B6 B7 B8 B9 BA BB BC BD BE BF ................" + HexDump.EOL +
            "000000C0 C0 C1 C2 C3 C4 C5 C6 C7 C8 C9 CA CB CC CD CE CF ................" + HexDump.EOL +
            "000000D0 D0 D1 D2 D3 D4 D5 D6 D7 D8 D9 DA DB DC DD DE DF ................" + HexDump.EOL +
            "000000E0 E0 E1 E2 E3 E4 E5 E6 E7 E8 E9 EA EB EC ED EE EF ................" + HexDump.EOL +
            "000000F0 F0 F1 F2 F3 F4 F5 F6 F7 F8 F9 FA FB FC FD FE FF ................" + HexDump.EOL,
            out.toString());

        // verify proper behavior with non-zero offset
        out = new StringBuilder();
        HexDump.dump(testArray, 0x10000000, out, 0, testArray.length);
        assertEquals(
            "10000000 00 01 02 03 04 05 06 07 08 09 0A 0B 0C 0D 0E 0F ................" + HexDump.EOL +
            "10000010 10 11 12 13 14 15 16 17 18 19 1A 1B 1C 1D 1E 1F ................" + HexDump.EOL +
            "10000020 20 21 22 23 24 25 26 27 28 29 2A 2B 2C 2D 2E 2F  !\"#$%&'()*+,-./" + HexDump.EOL +
            "10000030 30 31 32 33 34 35 36 37 38 39 3A 3B 3C 3D 3E 3F 0123456789:;<=>?" + HexDump.EOL +
            "10000040 40 41 42 43 44 45 46 47 48 49 4A 4B 4C 4D 4E 4F @ABCDEFGHIJKLMNO" + HexDump.EOL +
            "10000050 50 51 52 53 54 55 56 57 58 59 5A 5B 5C 5D 5E 5F PQRSTUVWXYZ[\\]^_" + HexDump.EOL +
            "10000060 60 61 62 63 64 65 66 67 68 69 6A 6B 6C 6D 6E 6F `abcdefghijklmno" + HexDump.EOL +
            "10000070 70 71 72 73 74 75 76 77 78 79 7A 7B 7C 7D 7E 7F pqrstuvwxyz{|}~." + HexDump.EOL +
            "10000080 80 81 82 83 84 85 86 87 88 89 8A 8B 8C 8D 8E 8F ................" + HexDump.EOL +
            "10000090 90 91 92 93 94 95 96 97 98 99 9A 9B 9C 9D 9E 9F ................" + HexDump.EOL +
            "100000A0 A0 A1 A2 A3 A4 A5 A6 A7 A8 A9 AA AB AC AD AE AF ................" + HexDump.EOL +
            "100000B0 B0 B1 B2 B3 B4 B5 B6 B7 B8 B9 BA BB BC BD BE BF ................" + HexDump.EOL +
            "100000C0 C0 C1 C2 C3 C4 C5 C6 C7 C8 C9 CA CB CC CD CE CF ................" + HexDump.EOL +
            "100000D0 D0 D1 D2 D3 D4 D5 D6 D7 D8 D9 DA DB DC DD DE DF ................" + HexDump.EOL +
            "100000E0 E0 E1 E2 E3 E4 E5 E6 E7 E8 E9 EA EB EC ED EE EF ................" + HexDump.EOL +
            "100000F0 F0 F1 F2 F3 F4 F5 F6 F7 F8 F9 FA FB FC FD FE FF ................" + HexDump.EOL,
            out.toString());

        // verify proper behavior with non-zero index
        out = new StringBuilder();
        HexDump.dump(testArray, 0x10000000, out, 0x81, testArray.length - 0x81);
        assertEquals(
            "10000081 81 82 83 84 85 86 87 88 89 8A 8B 8C 8D 8E 8F 90 ................" + HexDump.EOL +
            "10000091 91 92 93 94 95 96 97 98 99 9A 9B 9C 9D 9E 9F A0 ................" + HexDump.EOL +
            "100000A1 A1 A2 A3 A4 A5 A6 A7 A8 A9 AA AB AC AD AE AF B0 ................" + HexDump.EOL +
            "100000B1 B1 B2 B3 B4 B5 B6 B7 B8 B9 BA BB BC BD BE BF C0 ................" + HexDump.EOL +
            "100000C1 C1 C2 C3 C4 C5 C6 C7 C8 C9 CA CB CC CD CE CF D0 ................" + HexDump.EOL +
            "100000D1 D1 D2 D3 D4 D5 D6 D7 D8 D9 DA DB DC DD DE DF E0 ................" + HexDump.EOL +
            "100000E1 E1 E2 E3 E4 E5 E6 E7 E8 E9 EA EB EC ED EE EF F0 ................" + HexDump.EOL +
            "100000F1 F1 F2 F3 F4 F5 F6 F7 F8 F9 FA FB FC FD FE FF    ..............." + HexDump.EOL,
            out.toString());

        // verify proper behavior with non-zero index and length
        out = new StringBuilder();
        HexDump.dump(testArray, 0, out, 0x60, 32);
        assertEquals(
            "00000060 60 61 62 63 64 65 66 67 68 69 6A 6B 6C 6D 6E 6F `abcdefghijklmno" + HexDump.EOL +
            "00000070 70 71 72 73 74 75 76 77 78 79 7A 7B 7C 7D 7E 7F pqrstuvwxyz{|}~." + HexDump.EOL,
            out.toString());

        // verify proper behavior with non-zero index and length beyond array length
        out = new StringBuilder();
        HexDump.dump(testArray, 0, out, 0xf0, 17);
        assertEquals(
            "000000F0 F0 F1 F2 F3 F4 F5 F6 F7 F8 F9 FA FB FC FD FE FF ................" + HexDump.EOL,
            out.toString());

        // verify proper behavior with null out
        try {
            HexDump.dump(testArray, (Appendable)null);
            fail("should have caught IllegalArgumentException on null out");
        } catch (final IllegalArgumentException ignored_exception) {

            // as expected
        }
    }

    @Test
    public void testDumpOutputStream()
            throws IOException {
        final byte[] testArray = new byte[256];

        for (int j = 0; j < 256; j++) {
            testArray[j] = (byte) j;
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        HexDump.dump(testArray, 0, stream, 0);
        byte[] outputArray = new byte[16 * (73 + HexDump.EOL.length())];

        for (int j = 0; j < 16; j++) {
            int offset = (73 + HexDump.EOL.length()) * j;

            outputArray[offset++] = (byte) '0';
            outputArray[offset++] = (byte) '0';
            outputArray[offset++] = (byte) '0';
            outputArray[offset++] = (byte) '0';
            outputArray[offset++] = (byte) '0';
            outputArray[offset++] = (byte) '0';
            outputArray[offset++] = (byte) toHex(j);
            outputArray[offset++] = (byte) '0';
            outputArray[offset++] = (byte) ' ';
            for (int k = 0; k < 16; k++) {
                outputArray[offset++] = (byte) toHex(j);
                outputArray[offset++] = (byte) toHex(k);
                outputArray[offset++] = (byte) ' ';
            }
            for (int k = 0; k < 16; k++) {
                outputArray[offset++] = (byte) toAscii((j * 16) + k);
            }
            System.arraycopy(HexDump.EOL.getBytes(), 0, outputArray, offset,
                    HexDump.EOL.getBytes().length);
        }
        byte[] actualOutput = stream.toByteArray();

        assertEquals(outputArray.length, actualOutput.length, "array size mismatch");
        for (int j = 0; j < outputArray.length; j++) {
            assertEquals(outputArray[j], actualOutput[j], "array[ " + j + "] mismatch");
        }

        // verify proper behavior with non-zero offset
        stream = new ByteArrayOutputStream();
        HexDump.dump(testArray, 0x10000000, stream, 0);
        outputArray = new byte[16 * (73 + HexDump.EOL.length())];
        for (int j = 0; j < 16; j++) {
            int offset = (73 + HexDump.EOL.length()) * j;

            outputArray[offset++] = (byte) '1';
            outputArray[offset++] = (byte) '0';
            outputArray[offset++] = (byte) '0';
            outputArray[offset++] = (byte) '0';
            outputArray[offset++] = (byte) '0';
            outputArray[offset++] = (byte) '0';
            outputArray[offset++] = (byte) toHex(j);
            outputArray[offset++] = (byte) '0';
            outputArray[offset++] = (byte) ' ';
            for (int k = 0; k < 16; k++) {
                outputArray[offset++] = (byte) toHex(j);
                outputArray[offset++] = (byte) toHex(k);
                outputArray[offset++] = (byte) ' ';
            }
            for (int k = 0; k < 16; k++) {
                outputArray[offset++] = (byte) toAscii((j * 16) + k);
            }
            System.arraycopy(HexDump.EOL.getBytes(), 0, outputArray, offset,
                    HexDump.EOL.getBytes().length);
        }
        actualOutput = stream.toByteArray();
        assertEquals(outputArray.length, actualOutput.length, "array size mismatch");
        for (int j = 0; j < outputArray.length; j++) {
            assertEquals(outputArray[j], actualOutput[j], "array[ " + j + "] mismatch");
        }

        // verify proper behavior with negative offset
        stream = new ByteArrayOutputStream();
        HexDump.dump(testArray, 0xFF000000, stream, 0);
        outputArray = new byte[16 * (73 + HexDump.EOL.length())];
        for (int j = 0; j < 16; j++) {
            int offset = (73 + HexDump.EOL.length()) * j;

            outputArray[offset++] = (byte) 'F';
            outputArray[offset++] = (byte) 'F';
            outputArray[offset++] = (byte) '0';
            outputArray[offset++] = (byte) '0';
            outputArray[offset++] = (byte) '0';
            outputArray[offset++] = (byte) '0';
            outputArray[offset++] = (byte) toHex(j);
            outputArray[offset++] = (byte) '0';
            outputArray[offset++] = (byte) ' ';
            for (int k = 0; k < 16; k++) {
                outputArray[offset++] = (byte) toHex(j);
                outputArray[offset++] = (byte) toHex(k);
                outputArray[offset++] = (byte) ' ';
            }
            for (int k = 0; k < 16; k++) {
                outputArray[offset++] = (byte) toAscii((j * 16) + k);
            }
            System.arraycopy(HexDump.EOL.getBytes(), 0, outputArray, offset,
                    HexDump.EOL.getBytes().length);
        }
        actualOutput = stream.toByteArray();
        assertEquals(outputArray.length, actualOutput.length, "array size mismatch");
        for (int j = 0; j < outputArray.length; j++) {
            assertEquals(outputArray[j], actualOutput[j], "array[ " + j + "] mismatch");
        }

        // verify proper behavior with non-zero index
        stream = new ByteArrayOutputStream();
        HexDump.dump(testArray, 0x10000000, stream, 0x81);
        outputArray = new byte[(8 * (73 + HexDump.EOL.length())) - 1];
        for (int j = 0; j < 8; j++) {
            int offset = (73 + HexDump.EOL.length()) * j;

            outputArray[offset++] = (byte) '1';
            outputArray[offset++] = (byte) '0';
            outputArray[offset++] = (byte) '0';
            outputArray[offset++] = (byte) '0';
            outputArray[offset++] = (byte) '0';
            outputArray[offset++] = (byte) '0';
            outputArray[offset++] = (byte) toHex(j + 8);
            outputArray[offset++] = (byte) '1';
            outputArray[offset++] = (byte) ' ';
            for (int k = 0; k < 16; k++) {
                final int index = 0x81 + (j * 16) + k;

                if (index < 0x100) {
                    outputArray[offset++] = (byte) toHex(index / 16);
                    outputArray[offset++] = (byte) toHex(index);
                } else {
                    outputArray[offset++] = (byte) ' ';
                    outputArray[offset++] = (byte) ' ';
                }
                outputArray[offset++] = (byte) ' ';
            }
            for (int k = 0; k < 16; k++) {
                final int index = 0x81 + (j * 16) + k;

                if (index < 0x100) {
                    outputArray[offset++] = (byte) toAscii(index);
                }
            }
            System.arraycopy(HexDump.EOL.getBytes(), 0, outputArray, offset,
                    HexDump.EOL.getBytes().length);
        }
        actualOutput = stream.toByteArray();
        assertEquals(outputArray.length, actualOutput.length, "array size mismatch");
        for (int j = 0; j < outputArray.length; j++) {
            assertEquals(outputArray[j], actualOutput[j], "array[ " + j + "] mismatch");
        }

        // verify proper behavior with negative index
        try {
            HexDump.dump(testArray, 0x10000000, new ByteArrayOutputStream(),
                    -1);
            fail("should have caught ArrayIndexOutOfBoundsException on negative index");
        } catch (final ArrayIndexOutOfBoundsException ignored_exception) {

            // as expected
        }

        // verify proper behavior with index that is too large
        try {
            HexDump.dump(testArray, 0x10000000, new ByteArrayOutputStream(),
                    testArray.length);
            fail("should have caught ArrayIndexOutOfBoundsException on large index");
        } catch (final ArrayIndexOutOfBoundsException ignored_exception) {

            // as expected
        }

        // verify proper behavior with null stream
        try {
            HexDump.dump(testArray, 0x10000000, (OutputStream)null, 0);
            fail("should have caught IllegalArgumentException on negative index");
        } catch (final IllegalArgumentException ignored_exception) {

            // as expected
        }
    }

    private char toAscii(final int c) {
        char rval = '.';

        if ((c >= 32) && (c <= 126)) {
            rval = (char) c;
        }
        return rval;
    }
}
