package com.organize4event.organize.commons;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
class BooleanTypeAdapter implements JsonDeserializer<Boolean>
{
    public Boolean deserialize(JsonElement json, Type typeOfT,
                               JsonDeserializationContext context) throws JsonParseException
    {
        int code = json.getAsInt();
        return code == 0 ? false :
                code == 1 ? true :
                        null;
    }
}
