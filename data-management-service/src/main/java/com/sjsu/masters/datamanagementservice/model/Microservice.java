package com.sjsu.masters.datamanagementservice.model;

import javax.persistence.*;

@Entity
@Table(name = "microservice")
public class Microservice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String networkIP;
    private String name;
    private Boolean enableScaling;

    public Microservice(String networkIP, String name, Boolean enableScaling) {
        this.networkIP = networkIP;
        this.name = name;
        this.enableScaling = enableScaling;
    }

    public Microservice() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNetworkIP() {
        return networkIP;
    }

    public void setNetworkIP(String networkIP) {
        this.networkIP = networkIP;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEnableScaling() {
        return enableScaling;
    }

    public void setEnableScaling(Boolean enableScaling) {
        this.enableScaling = enableScaling;
    }
}
