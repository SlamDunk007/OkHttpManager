package com.guannan.network;

/**
 * Created by guannan on 2017/7/7.
 */

public class NetConfig {

    public static final long mReadTimeOut = 15000L;

    public static final long mWriteTimeOut = 15000L;

    public static final long mConnectTimeOut = 15000L;

    public static final class StatusCode{

        public static final int NET_CALLED = 0;

        public static final int NET_ERROR = 1;

        public static final int PARSE_ERROR = 2;
    }

}
