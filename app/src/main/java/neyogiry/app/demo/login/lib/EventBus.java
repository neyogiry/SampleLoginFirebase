package neyogiry.app.demo.login.lib;

/**
 * Created by Neyo on 13/10/2017.
 */

public interface EventBus {

    void register(Object subscriber);
    void unregister(Object subscriber);
    void post(Object event);

}
