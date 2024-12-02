package com.library_management.api.request;

public interface InterfaceRequest<M> {
    Boolean checkRequest(M requestData);
}
