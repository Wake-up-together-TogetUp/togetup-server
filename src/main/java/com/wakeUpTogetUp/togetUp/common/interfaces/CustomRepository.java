package com.wakeUpTogetUp.togetUp.common.interfaces;

import java.util.Optional;

public interface CustomRepository<T> {
    public void save(T object);

    public Optional<T> findById(Integer id);
}