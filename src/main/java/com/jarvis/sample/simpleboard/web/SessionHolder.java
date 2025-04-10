package com.jarvis.sample.simpleboard.web;

import org.springframework.stereotype.Component;

@Component
public class SessionHolder {

    /*가짜 session holder -> 시간 상 생략*/
    public Long requestUserId() {
        return 1L;
    }
}
