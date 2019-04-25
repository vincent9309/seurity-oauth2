package com.happylifeplat.security.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * <p>Description: .</p>
 * <p>Company: </p>
 * <p>Date: 2017/9/25 10:26</p>
 * <p>Copyright: 2015-2016 happylifeplat.com All Rights Reserved</p>
 *
 * @author vincent
 */
public class EnumSerializer extends JsonSerializer<EnumView> {

    @Override
    public void serialize(EnumView enumView, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeFieldName("name");
        jsonGenerator.writeString(enumView.getName());
        jsonGenerator.writeFieldName("value");
        jsonGenerator.writeString(enumView.getValue());
        jsonGenerator.writeEndObject();
    }
}
