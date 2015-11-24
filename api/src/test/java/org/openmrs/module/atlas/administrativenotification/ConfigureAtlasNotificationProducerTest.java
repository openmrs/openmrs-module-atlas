package org.openmrs.module.atlas.administrativenotification;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.appframework.domain.AdministrativeNotification;
import org.openmrs.module.atlas.AtlasModuleActivator;
import org.openmrs.module.atlas.AtlasService;
import org.openmrs.web.test.BaseModuleWebContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ConfigureAtlasNotificationProducerTest extends BaseModuleWebContextSensitiveTest {

    @Autowired
    ConfigureAtlasNotificationProducer producer;

    @Before
    public void setUp() throws Exception {
        new AtlasModuleActivator().started();
        producer.clearCache();
    }

    @Test
    public void testGenerateNotificationsWhenNotConfigured() throws Exception {
        Context.getService(AtlasService.class).disableAtlasModule();
        List<AdministrativeNotification> notifications = producer.generateNotifications();
        assertThat(notifications.size(), is(1));
        assertThat(notifications.get(0).getId(), is("atlas.configure"));
    }

    @Test
    public void testGenerateNotificationsWhenConfigured() throws Exception {
        Context.getService(AtlasService.class).enableAtlasModule();
        List<AdministrativeNotification> notifications = producer.generateNotifications();
        assertThat(notifications.size(), is(0));
    }

    @Test
    public void testGenerateNotificationsWhenStoppedAsking() throws Exception {
        Context.getService(AtlasService.class).disableAtlasModule();
        Context.getService(AtlasService.class).setStopAskingToConfigure(true);
        List<AdministrativeNotification> notifications = producer.generateNotifications();
        assertThat(notifications.size(), is(0));
    }

}