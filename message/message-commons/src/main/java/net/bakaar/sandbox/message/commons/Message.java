package net.bakaar.sandbox.message.commons;

import java.util.Map;

public interface Message<T> {

    Map<String, String> getHeaders();

    T getPayload();
}
