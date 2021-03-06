package org.bouncycastle.asn1;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.util.io.Streams;

class DefiniteLengthInputStream extends LimitedInputStream {
    private static final byte[] EMPTY_BYTES = new byte[0];
    private final int _originalLength;
    private int _remaining;

    DefiniteLengthInputStream(InputStream inputStream, int i) {
        super(inputStream, i);
        if (i >= 0) {
            this._originalLength = i;
            this._remaining = i;
            if (i == 0) {
                setParentEofDetect(true);
                return;
            }
            return;
        }
        throw new IllegalArgumentException("negative lengths not allowed");
    }

    int getRemaining() {
        return this._remaining;
    }

    public int read() throws IOException {
        if (this._remaining == 0) {
            return -1;
        }
        int read = this._in.read();
        if (read >= 0) {
            int i = this._remaining - 1;
            this._remaining = i;
            if (i == 0) {
                setParentEofDetect(true);
            }
            return read;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DEF length ");
        stringBuilder.append(this._originalLength);
        stringBuilder.append(" object truncated by ");
        stringBuilder.append(this._remaining);
        throw new EOFException(stringBuilder.toString());
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (this._remaining == 0) {
            return -1;
        }
        int read = this._in.read(bArr, i, Math.min(i2, this._remaining));
        if (read >= 0) {
            i = this._remaining - read;
            this._remaining = i;
            if (i == 0) {
                setParentEofDetect(true);
            }
            return read;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DEF length ");
        stringBuilder.append(this._originalLength);
        stringBuilder.append(" object truncated by ");
        stringBuilder.append(this._remaining);
        throw new EOFException(stringBuilder.toString());
    }

    byte[] toByteArray() throws IOException {
        if (this._remaining == 0) {
            return EMPTY_BYTES;
        }
        byte[] bArr = new byte[this._remaining];
        int readFully = this._remaining - Streams.readFully(this._in, bArr);
        this._remaining = readFully;
        if (readFully == 0) {
            setParentEofDetect(true);
            return bArr;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DEF length ");
        stringBuilder.append(this._originalLength);
        stringBuilder.append(" object truncated by ");
        stringBuilder.append(this._remaining);
        throw new EOFException(stringBuilder.toString());
    }
}
