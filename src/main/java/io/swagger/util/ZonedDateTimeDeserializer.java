package io.swagger.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static io.swagger.commons.Constants.ZONED_DATE_TIME_FORMAT;


public class ZonedDateTimeDeserializer extends JsonDeserializer<ZonedDateTime> {

    /**
     * Custom {@link ZonedDateTime} deserializer.
     *
     * @param jsonParser             for extracting the date in {@link String} format.
     * @param deserializationContext for the process of deserialization a single root-level value.
     * @return {@link ZonedDateTime} object of the date.
     * @throws IOException throws I/O exceptions.
     */
    @Override
    public ZonedDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        return ZonedDateTime.parse(jsonParser.getText(), DateTimeFormatter.ofPattern(ZONED_DATE_TIME_FORMAT));
    }
}