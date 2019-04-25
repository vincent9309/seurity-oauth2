package com.happylifeplat.security.common;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.core.json.JsonWriteContext;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * <p>Description: .</p>
 * <p>Company:  </p>
 * <p>Date: 2017/9/18 14:08</p>
 * <p>Copyright: 2015-2016 happylifeplat.com All Rights Reserved</p>
 *
 * @author vincent
 */
public class StringUnicodeSerializer extends JsonSerializer<String> {

    /**
     * Hex Chars
     */
    private static final char[] HEX_CHARS = CharTypes.copyHexChars();

    /**
     * Escapes
     */
    private static final int[] ESCAPE_CODES = CharTypes.get7BitOutputEscapes();


    @Override
    public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException, JsonProcessingException {
        int status = ((JsonWriteContext) jsonGenerator.getOutputContext()).writeValue();
        switch (status) {
            case JsonWriteContext.STATUS_OK_AFTER_COLON:
                jsonGenerator.writeRaw(':');
                break;
            case JsonWriteContext.STATUS_OK_AFTER_COMMA:
                jsonGenerator.writeRaw(',');
                break;
            case JsonWriteContext.STATUS_EXPECT_NAME:
                throw new JsonGenerationException("Can not write string value here");
            default:
        }
        //写入JSON中字符串的开头引号
        jsonGenerator.writeRaw('"');
        for (char c : value.toCharArray()) {
            if (c >= 0x80) {
                // 为所有非ASCII字符生成转义的unicode字符
                writeUnicodeEscape(jsonGenerator, c);
            } else {
                // 为ASCII字符中前128个字符使用转义的unicode字符
                int code = (c < ESCAPE_CODES.length ? ESCAPE_CODES[c] : 0);
                if (code == 0) {
                    // 此处不用转义
                    jsonGenerator.writeRaw(c);
                } else if (code < 0) {
                    // 通用转义字符
                    writeUnicodeEscape(jsonGenerator, (char) (-code - 1));
                } else {
                    // 短转义字符 (\n \t ...)
                    writeShortEscape(jsonGenerator, (char) code);
                }
            }
        }
        //写入JSON中字符串的结束引号
        jsonGenerator.writeRaw('"');
    }

    /**
     * write unicode escape
     *
     * @param generator generator
     * @param c         char
     * @throws IOException io exception
     */
    private void writeUnicodeEscape(JsonGenerator generator, char c) throws IOException {
        generator.writeRaw('\\');
        generator.writeRaw('u');
        generator.writeRaw(HEX_CHARS[(c >> 12) & 0xF]);
        generator.writeRaw(HEX_CHARS[(c >> 8) & 0xF]);
        generator.writeRaw(HEX_CHARS[(c >> 4) & 0xF]);
        generator.writeRaw(HEX_CHARS[c & 0xF]);
    }

    /**
     * write short escape
     *
     * @param generator generator
     * @param c         char
     * @throws IOException io exception
     */
    private void writeShortEscape(JsonGenerator generator, char c) throws IOException {
        generator.writeRaw('\\');
        generator.writeRaw(c);
    }
}
