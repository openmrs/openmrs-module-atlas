package org.openmrs.module.atlas.web;

import org.openmrs.api.context.Context;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

public class RefAppConfiguration implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        try {
            Class cls = Context.loadClass("org.openmrs.ui.framework.StandardModuleUiConfiguration");
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(cls);
            builder.addPropertyValue("moduleId", "atlas"); // set property value
            ((DefaultListableBeanFactory) beanFactory).registerBeanDefinition("atlasStandardModuleUiConfiguration",
                    builder.getBeanDefinition());
            } catch (ClassNotFoundException ex) {
                //ignore as this means we are not running under the reference app
            }
    }
}
    
