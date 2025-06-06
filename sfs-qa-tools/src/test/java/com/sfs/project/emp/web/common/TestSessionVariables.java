package com.sfs.project.emp.web.common;

import com.google.common.collect.ImmutableMap;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class TestSessionVariables implements SessionMap<Object,Object> {
    private final ConcurrentHashMap<String, String> metadata = new ConcurrentHashMap<>();

    @Override
    public void shouldContainKey(Object key) {
        Object result = get(key);
        if (result == null) {
            throw new AssertionError("Session variable " + key + " expected but not found.");
        }
    }

    @Override
    public Map<String, String> getMetaData() {
        return ImmutableMap.copyOf(metadata);
    }

    @Override
    public void addMetaData(String key, String value) {
        metadata.put(key, value);
    }

    @Override
    public void clearMetaData() {
        metadata.clear();
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public Object get(Object key) {
        return null;
    }

    @Override
    public Object put(Object key, Object value) {
        return null;
    }

    @Override
    public Object remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map m) {
        // Do nothing because of X and Y.
    }


    @Override
    public void clear() {
        clearMetaData();
        clear();
    }

    @Override
    public Set keySet() {
        return (Set) Collections.emptyList();
    }

    @Override
    public Collection values() {
        return Collections.emptyList();
    }

    @Override
    public Set<Entry<Object, Object>> entrySet() {
        return (Set) Collections.emptyList();
    }
}
