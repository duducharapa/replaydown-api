package com.charapadev.log;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.charapadev.game.Game;
import com.charapadev.player.Player;
import com.charapadev.player.PlayerNumber;
import com.charapadev.pokemon.Pokemon;
import com.charapadev.pokemon.PokemonService;
import com.charapadev.pokemon.SpeciesVariant;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class LogService {

    @Inject
    private PokemonService pokemonService;

    // Actions related to logs
    // To see the details of which log should do, see LogType instance.
    private Map<String, LogType> actions = Map.of(
        "poke", LogType.POKEMON,
        "move", LogType.MOVESET,
        "switch", LogType.SWITCH
    );
    
    // The separator used on Showdown logs
    // |poke|p1|Pikachu, M, shiny
    private String SEPARATOR = "\\|";
    
    /**
     * Transforms the entire text data into a list of typed logs.
     * 
     * @param text The entire log text.
     * @return The list of logs detached.
     */
    public List<? extends Log> detachLines(String text) {
        Stream<String> parsedData = Stream.of(text.split("\n"));

        return parsedData.map(this::parse)
            .filter(log -> log != null)
            .toList();
    }

    /**
     * Splits the pokemon information to a well formed instance of Pokémon.
     * 
     * @param pokemonInfo The pokemon stringfied information.
     * @return The pokemon instance.
     */
    private SpeciesVariant parsePokemonName(String pokemonInfo) {
        String extractedName =  pokemonInfo.contains(":") ?
            pokemonInfo.split(": ")[1] :
            pokemonInfo.split(",")[0];

        return pokemonService.resolveSpecificPokemon(extractedName);
    }

    /**
     * Recognize the numeration of player involved on log instance.
     * <p><p>
     * If the value is <strong>"p1"</strong>, then it'll refer to {@link Game#getPlayer1() game first player}.
     * Otherwise, it refers to {@link Game#getPlayer2() game second player}.
     * 
     * @param playerInfo The player stringfied information.
     * @return The player enumeration.
     */
    private PlayerNumber parsePlayerNumeration(String playerInfo) {
        String preparedInfo = playerInfo.contains(":") ?
            playerInfo.split(":")[0] :
            playerInfo;
        
        return preparedInfo.equals("p1") || preparedInfo.equals("p1a") ?
            PlayerNumber.P1 :
            PlayerNumber.P2;
    }

    /**
     * Separates the elements related to a given single log line.
     * 
     * @param line The log line.
     * @return The separated parts of log line.
     */
    private List<String> splitLine(String line) {
        return Stream.of(line.substring(1).split(SEPARATOR))
            .toList();
    }

    /**
     * Converts a stringfied line of text log to a well-formed log instance.
     * <p><p>
     * Based on log action found, some additional data is retrieved and passed to certain classes.
     * To see details about each class, see the inheritances of {@link Log} classes.
     * 
     * @param line The text line.
     * @return The log instance.
     */
    private Log parse(String line) {
        if (line.equals("\n") || line.equals(SEPARATOR)) return null;

        List<String> params = splitLine(line);
        String extractedAction = params.get(0);

        LogType actionType = actions.get(extractedAction);

        if (actionType == null) return null;

        PlayerNumber player = parsePlayerNumeration(params.get(1));

        switch (actionType) {
            case POKEMON:
                SpeciesVariant pokemonInfo = parsePokemonName(params.get(2));
                Pokemon pokemon = new Pokemon(pokemonInfo.generalName(), pokemonInfo.variant());

                return new TeamLog(player, pokemon);
            case MOVESET:
                String pokemonNickname = parsePokemonName(params.get(1))
                    .generalName();
                String move = params.get(2);

                return new MoveLog(player, pokemonNickname, move);
            case SWITCH:
                pokemonNickname = parsePokemonName(params.get(1))
                    .generalName();
                String pokemonName = params.get(2).split(", ")[0];

                return new SwitchLog(player, pokemonName, pokemonNickname);
            default:
                return null;
        }
    }

    /**
     * Performs the action related to the given log instance.
     * 
     * @param game The game instance to populate.
     * @param log The log instance.
     */
    public void resolve(Game game, Log log) {
        switch (log.getType()) {
            case POKEMON:
                TeamLog teamLog = (TeamLog) log;
                Pokemon pokemon = teamLog.getPokemon();

                if (teamLog.getPlayer() == PlayerNumber.P1) {
                    game.getPlayer1().addPokemon(pokemon);
                } else {
                    game.getPlayer2().addPokemon(pokemon);
                }

                break;
            
            case MOVESET:
                MoveLog moveLog = (MoveLog) log;
                
                Player pokemonOwner = moveLog.getPlayer() == PlayerNumber.P1 ?
                    game.getPlayer1() :
                    game.getPlayer2();

                System.out.println(moveLog);
                Pokemon pokemonFound = pokemonOwner.getTeam().stream()
                    .filter(pkmn -> {
                        return pkmn.getSpeciesName().equals(moveLog.getPokemonNickname()) ||
                            pkmn.getNickname().equals(moveLog.getPokemonNickname());
                    })
                    .findFirst()
                    .orElseThrow();

                pokemonFound.addMove(moveLog.getMove());

                break;

            case SWITCH:
                SwitchLog switchLog = (SwitchLog) log;

                pokemonOwner = switchLog.getPlayer() == PlayerNumber.P1 ?
                    game.getPlayer1() :
                    game.getPlayer2();

                pokemonFound = pokemonOwner.getTeam().stream()
                    .filter(pkmn -> pkmn.getName().equals(switchLog.getPokemonName()))
                    .findFirst()
                    .orElseThrow();

                pokemonFound.setNickname(switchLog.getPokemonNickname());

            default:
                break;
        }
    }

}
