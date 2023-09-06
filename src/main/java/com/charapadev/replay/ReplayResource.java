package com.charapadev.replay;

import java.util.List;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.RestResponse;

import com.charapadev.game.Game;
import com.charapadev.log.Log;
import com.charapadev.log.LogService;
import com.charapadev.service.ShowdownService;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
@ApplicationScoped
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
    public Uni<RestResponse<Game>> find(@PathParam("replayId") String replayId) {
        Uni<ReplayData> replayData = showdownService.findById(replayId)
            .onFailure().retry().atMost(3)
            .onFailure().recoverWithNull();

        Uni<Game> populatedGame = replayData
            .onItem().transformToUni(data -> {
                if (data == null) return Uni.createFrom().nullItem();

                Uni<Game> voidGame = Uni.createFrom().item(replayService.getBasicGameData(data));
                Uni<List<? extends Log>> logs = Uni.createFrom().item(logService.detachLines(data.log())); 

                return Uni.combine().all().unis(voidGame, logs).asTuple()
                    .chain(tuple -> {
                        Game game = tuple.getItem1();
                        tuple.getItem2()
                            .forEach(log -> logService.resolve(game, log));
        
                        return Uni.createFrom().item(game);
                    });
            });

            return populatedGame
                .onItem().transform(data -> data != null ? RestResponse.ok(data) : RestResponse.notFound());
    }
}
