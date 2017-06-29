package com.mall.app.api;

/**
 *
 */
public class ApiContants {



    public static final class Urls {


        public final static String url = "http://192.168.0.211/";  //本地测试

        public final static String TEST = url + "test"; //test

    }

    public static final class EventTags {

        public final static int BEGIN_EVENT = 10;

        public final static int TEST = BEGIN_EVENT + 1; //test

    }

    public static final class Integers {
        public static final int PAGE_LAZY_LOAD_DELAY_TIME_MS = 200;
    }

    public static final class Request{
        public static final int PAGE_NUMBER = 20;
    }
}
