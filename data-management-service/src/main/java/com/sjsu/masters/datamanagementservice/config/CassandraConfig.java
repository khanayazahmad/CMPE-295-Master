package com.sjsu.masters.datamanagementservice.config;

import com.datastax.driver.core.*;
import com.datastax.driver.core.AuthProvider;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.*;
import org.springframework.data.cassandra.repository.config.*;

import java.util.*;

@Configuration
@EnableCassandraRepositories(basePackages = {"com.sjsu.masters.datamanagementservice.repository.cassandra"})
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Value("${spring.data.cassandra.contact-points}")
    private String contactPoints;

    @Value("${spring.data.cassandra.port}")
    private int port;

    @Value("${cassandra.keyspace.startup.script}")
    private String keySpaceScript;

    @Value("${spring.data.cassandra.keyspace-name}")
    private String keySpace;

//    @Value("${spring.data.cassandra.username}")
//    private String username;
//
//    @Value("${spring.data.cassandra.password}")
//    private String password;

    @Override
    protected String getKeyspaceName() {
        return keySpace;
    }

    @Override
    protected List<String> getStartupScripts() {
        return Arrays.asList(keySpaceScript);
    }

    @Override
    protected String getContactPoints() {
        return contactPoints;
    }

    @Override
    protected int getPort() {
        return port;
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }


    @Override
    public String[] getEntityBasePackages() {
        return new String[] {"com.sjsu.masters.datamanagementservice.model"};
    }

//    @Override
//    protected AuthProvider getAuthProvider() {
//        return new PlainTextAuthProvider(username, password);
//    }

    @Override
    protected boolean getMetricsEnabled() { return false; }
}