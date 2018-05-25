/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.packtpub.javaee8.boundary;

import java.net.URI;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jsonb.JsonBindingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.spi.TestContainerException;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author VMC027
 */
public class VersionResourceTest extends JerseyTest {

    @Override
    protected int getAsyncTimeoutMultiplier() {
        return super.getAsyncTimeoutMultiplier(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected URI getBaseUri() {
        return super.getBaseUri(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void configureClient(ClientConfig config) {
        
        config.register(JsonBindingFeature.class);
        //super.configureClient(config); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Client setClient(Client client) {
        return super.setClient(client); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Client getClient() {
        return super.getClient(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setUp() throws Exception {
        super.setUp(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected TestContainerFactory getTestContainerFactory() throws TestContainerException {
        return super.getTestContainerFactory(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected DeploymentContext configureDeployment() {
        return super.configureDeployment(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Application configure() {
        
        ResourceConfig config=new ResourceConfig(VersionResource.class);
        
        config.register(new AbstractBinder(){
            @Override
            protected void configure(){
                bind(Logger.getAnonymousLogger());
            }
        });
        
        return config;
    }
    
    @Test
    public void v1(){
        Response response=target("/version/v1").request().get();
        assertThat(response.getStatus(),is(200));
        assertThat(response.readEntity(String.class),is("v1.0"));
    }
    

    @Test
    public void v2(){
        Response response=target("/version/v2").request().get();
        assertThat(response.getStatus(),is(200));
        assertThat(response.readEntity(String.class),is("v3.0"));
    }
    
    
}
