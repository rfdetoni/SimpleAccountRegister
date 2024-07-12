package com.ogisystems.technicaltest.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ogisystems.technicaltest.enums.TransactionTypes;

import java.io.IOException;

public class TransactionTypeDeserializer extends JsonDeserializer<TransactionTypes> {

    @Override
    public TransactionTypes deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        String value = parser.getText().toUpperCase();
        for (TransactionTypes type : TransactionTypes.values()) {
            if (type.name().toUpperCase().equals(value)) {
                return type;
            }
        }

        throw new JsonMappingException(parser, "Invalid transaction type: " + value);
    }
}

