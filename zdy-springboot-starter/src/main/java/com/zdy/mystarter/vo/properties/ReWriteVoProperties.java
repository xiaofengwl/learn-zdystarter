package com.zdy.mystarter.vo.properties;

import com.zdy.mystarter.config.HelloProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by Jason on 2020/1/7.
 */
@Component
@ConfigurationProperties(prefix = "rewrite.hello")
@PropertySource("classpath:/conf/rewrite.properties")
public class ReWriteVoProperties extends HelloProperties {

    @Value("${rewrite.hello.start}")
    private String start;

    @Value("${rewrite.hello.start}")
    private String end;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

}
