package com.aconcagua.clients.vertx;

import io.vertx.core.*; 
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberMatcher;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class ApiVerticleTest { 

    @Mock
    Vertx vertx;

    @Mock
    JsonObject jsonObject;

    @Before
    public void setUp() {
      MockitoAnnotations.initMocks(this);
    }

    private void mockHeaderResponse() {
        when(message.headers()).thenReturn(headers);
        when(headers.get(anyString())).thenReturn("SOME_VALUE");
    }
  
    
}