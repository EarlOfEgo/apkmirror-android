package com.pascalwelsch.apkmirror.connection;

import com.pascalwelsch.backendswitch.Server;

/**
 * Created by pascalwelsch on 2/1/15.
 */
public class TestBackend extends Server {

    public static final String NAME = "test";

    @Override
    public String getAuthority() {
        return "10.10.245.255:4040";
    }

    @Override
    public String getName() {
        return NAME;
    }
}
