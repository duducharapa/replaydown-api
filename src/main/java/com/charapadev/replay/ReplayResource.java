package com.charapadev.replay;

import java.util.List;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.charapadev.game.Game;
import com.charapadev.log.Log;
import com.charapadev.log.LogService;
import com.charapadev.service.ShowdownService;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class ReplayResource {
    
    @RestClient
    private ShowdownService showdownService;

    @Inject
    private ReplayService replayService;

    @Inject
    private LogService logService;

    @GET
    @Path("/{replayId}")
    public Uni<Game> find(@PathParam("replayId") String replayId) {
        return showdownService.findById(replayId)
            .chain(data -> {
                Uni<Game> game = Uni.createFrom().item(replayService.getBasicGameData(data));
                Uni<List<? extends Log>> logs = Uni.createFrom().item(logService.detachLines(data.log())); 

                return Uni.combine().all().unis(game, logs).asTuple();
            })
            .chain(tuple -> {
                Game game = tuple.getItem1();
                tuple.getItem2()
                    .forEach(log -> logService.resolve(game, log));

                return Uni.createFrom().item(game);
            });
    }
}
