package com.kalpana.asin2tk.svd.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.kalpana.asin2tk.svd.interfaces.SvdParseInterface;

/**
 * @author 2tk
 * @date 2020/8/12 18:50
 * @version 1.0
 **/
@Component
public class SvdParseInterfaceFactory implements ApplicationContextAware {

    private static Map<String, SvdParseInterface> svdParseInterfaceMap = new ConcurrentHashMap<>();

    /**
     * Set the ApplicationContext that this object runs in.
     * Normally this call will be used to initialize the object.
     * <p>Invoked after population of normal bean properties but before an init callback such
     * as {@link InitializingBean#afterPropertiesSet()}
     * or a custom init-method. Invoked after {@link ResourceLoaderAware#setResourceLoader},
     * {@link ApplicationEventPublisherAware#setApplicationEventPublisher} and
     * {@link MessageSourceAware}, if applicable.
     *
     * @param applicationContext the ApplicationContext object to be used by this object
     * @throws ApplicationContextException in case of context initialization errors
     * @throws BeansException              if thrown by application context methods
     * @see BeanInitializationException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, SvdParseInterface> map = applicationContext.getBeansOfType(SvdParseInterface.class);
        map.forEach((key, value) -> svdParseInterfaceMap.put(value.getType(), value));
    }

    public static SvdParseInterface getObject(String type){
        return svdParseInterfaceMap.get(type);
    }
}
