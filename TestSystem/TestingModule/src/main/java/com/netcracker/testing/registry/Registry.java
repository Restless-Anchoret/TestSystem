package com.netcracker.testing.registry;

import java.util.Collection;

public interface Registry<T> {

    T getDefault();
    void put(String id, Supplier<T> supplier);
    T get(String id);
    Collection<String> getAvailableIds();
    
}
