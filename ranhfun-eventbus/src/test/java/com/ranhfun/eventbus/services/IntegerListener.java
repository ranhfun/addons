package com.ranhfun.eventbus.services;

import com.google.common.eventbus.Subscribe;

public class IntegerListener {

    private Integer lastMessage;

    @Subscribe
    public void listen(Integer integer) throws Exception {
        lastMessage = integer;
        System.out.println("begin....." + integer);
        if (integer%50==0) {
			throw new Exception();
		}
    }

    public Integer getLastMessage() {
        return lastMessage;
    }
}