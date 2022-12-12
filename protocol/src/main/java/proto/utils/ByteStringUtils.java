package proto.utils;

import android.text.TextUtils;

import com.google.protobuf.ByteString;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class ByteStringUtils {
    public static String bytesToString(ByteString src, String charSet) {
        if(TextUtils.isEmpty(charSet)) {
            charSet = "GB2312";
        }
        return bytesToString(src.toByteArray(), charSet);
    }

    public static String bytesToString(byte[] input, String charSet) {
        if(input==null) {
            return "";
        }
        if(input.length==0){
            return "";
        }

        ByteBuffer buffer = ByteBuffer.allocate(input.length);
        buffer.put(input);
        buffer.flip();

        Charset charset = null;
        CharsetDecoder decoder = null;
        CharBuffer charBuffer = null;

        try {
            charset = Charset.forName(charSet);
            decoder = charset.newDecoder();
            charBuffer = decoder.decode(buffer.asReadOnlyBuffer());

            return charBuffer.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }
}
