package com.dynamischrijbaansysteem.interfaces;

import com.dynamischrijbaansysteem.LaneStatusService;

public interface ServiceInjectable<T> {
    void setContext(T context);

}
