package net.bakaar.sandbox.messaging.common;

import java.util.Map;

public interface Message<T> {

    Map<String, String> getHeaders();

    T getPayload();
}
