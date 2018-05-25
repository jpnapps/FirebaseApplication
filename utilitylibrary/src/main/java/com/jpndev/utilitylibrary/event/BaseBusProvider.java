package com.jpndev.utilitylibrary.event;


import com.squareup.otto.Bus;

/**
 * Created by ceino on 6/1/15.
 */
public final class BaseBusProvider {
    private static final Bus BUS = new Bus();

    public static Bus getInstance() {

        return BUS;
    }

    private BaseBusProvider() {
    }
}

