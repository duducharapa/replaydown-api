package com.charapadev.service;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import com.charapadev.replay.ReplayData;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@RegisterRestClient(configKey = "showdown-api")
public interface ShowdownService {

    @GET
    @Path("/{id}.json")
    Uni<ReplayData> findById(@PathParam("id") String id); 
    
}
