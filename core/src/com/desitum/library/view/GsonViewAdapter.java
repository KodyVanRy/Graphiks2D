package com.desitum.library.view;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

class GsonViewAdapter implements JsonSerializer<View>, JsonDeserializer<View> {

    private static final String CLASSNAME = "CLASS";
    private static final String INSTANCE  = "INSTANCE";

    @Override
    public JsonElement serialize(View src, Type typeOfSrc,
                                 JsonSerializationContext context) {

        JsonObject retValue = new JsonObject();
        String className = src.getClass().getName();
        retValue.addProperty(CLASSNAME, className);
        JsonElement elem = context.serialize(src); 
        retValue.add(INSTANCE, elem);
        return retValue;
    }

    @Override
    public View deserialize(JsonElement json, Type typeOfT,
            JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonPrimitive prim = (JsonPrimitive) jsonObject.get(CLASSNAME);
        String className = prim.getAsString();

        Class<?> klass = null;
        try {
            klass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new JsonParseException(e.getMessage());
        }
        return context.deserialize(jsonObject.get(INSTANCE), klass);
    }
}