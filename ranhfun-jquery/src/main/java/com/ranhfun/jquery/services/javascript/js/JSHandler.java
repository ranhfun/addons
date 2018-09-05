package com.ranhfun.jquery.services.javascript.js;

import java.io.IOException;

import org.apache.tapestry5.services.Response;

public interface JSHandler {
    public boolean handleRequest(String path, Response response) throws IOException;
}
