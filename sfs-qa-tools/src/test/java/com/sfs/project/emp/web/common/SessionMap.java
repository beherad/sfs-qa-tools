package com.sfs.project.emp.web.common;

import java.util.Map;

public interface SessionMap<K, V> extends Map<K, V> {
    Map<String, String> getMetaData();

    void addMetaData(String key, String value);

    void clearMetaData();

    void shouldContainKey(K key);
}
