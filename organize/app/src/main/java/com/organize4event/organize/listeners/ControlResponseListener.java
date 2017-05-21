package com.organize4event.organize.listeners;

public interface ControlResponseListener {

    void success(Object object);

    void fail(Error error);
}
