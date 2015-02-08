package com.pascalwelsch.apkmirror.connection;

import com.pascalwelsch.backendswitch.Server;

/**
 * Created by pascalwelsch on 2/1/15.
 */
public class ProductionBackend extends Server {

    public static final String NAME = "prod";

    @Override
    public String getAuthority() {
        return "pascalwelsch.com:4040";
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getScheme() {
        return "http";
    }
}
