package me.jiawu.autoconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wuzhong on 2017/12/2.
 * @version 1.0
 */
@ConfigurationProperties(prefix = "me")
public class MeConfig {

    private String name;

    private boolean autoconfig;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAutoconfig() {
        return autoconfig;
    }

    public void setAutoconfig(boolean autoconfig) {
        this.autoconfig = autoconfig;
    }
}
