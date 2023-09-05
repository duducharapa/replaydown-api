package com.charapadev.log;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.charapadev.game.Game;
import com.charapadev.player.Player;
import com.charapadev.player.PlayerNumber;
import com.charapadev.player.PlayerService;
import com.charapadev.pokemon.Pokemon;
import com.charapadev.pokemon.PokemonService;
import com.charapadev.pokemon.SpeciesVariant;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class LogService {

    @Inject
    private PokemonService pokemonService;

    @Inject
    private PlayerService playerService;

    // Actions related to logs
    // To see the details of which log should do, see LogType instance.
    private Map<String, LogType> actions = Map.of(
        "poke", LogType.POKEMON,
        "move", LogType.MOVESET,
        "switch", LogType.SWITCH,
        "turn", LogType.TURN,
        "win", LogType.WIN
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

        return pokemonService.extractSpeciesVariants(extractedName);
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

        switch (actionType) {
            case POKEMON:
                /*
                 * The presentations of pokémons contains your name and owner.
                 * 
                 * An example: |poke|p1|Sneasler, M|
                 * Then, after split, the Pokémon name will be on position 2
                 * and the player who belongs in position 1.
                 */
                PlayerNumber player = parsePlayerNumeration(params.get(1));
                SpeciesVariant pokemonInfo = parsePokemonName(params.get(2));
                Pokemon pokemon = new Pokemon(pokemonInfo.generalName(), pokemonInfo.variant());

                return new TeamLog(player, pokemon);
            case MOVESET:
                /*
                 * The pokémon using a move.
                 * 
                 * An example: |move|p1a: Sneasler|Close Combat|p2a: I hear caw caw caw
                 * Then, after split, the Pokémon nickname and player who belongs will be on position 1, separated by colon,
                 * and the move used on position 2.
                 */
                player = parsePlayerNumeration(params.get(1));
                String pokemonNickname = parsePokemonName(params.get(1))
                    .generalName();
                String move = params.get(2);

                return new MoveLog(player, pokemonNickname, move);
            case SWITCH:
                /*
                 * The user switching a pokémon.
                 * 
                 * An example: |switch|p1a: Paramedic|Chansey, F, shiny|704/704
                 * Then, after split, the player will be on position 1 with the nickname of pokémon, separated by colon,
                 * and the species name on position 2.
                 */
                player = parsePlayerNumeration(params.get(1));
                pokemonNickname = parsePokemonName(params.get(1))
                    .generalName();
                String pokemonName = params.get(2).split(", ")[0];

                return new SwitchLog(player, pokemonName, pokemonNickname);
            case TURN:
                /*
                 * The new turn beginning.
                 * 
                 * An example: |turn|1
                 * None data is extracted from here. This action just works as a trigger to increment turn.
                 */
                return new TurnLog();
            case WIN:
                /*
                 * One of players winning the game.
                 * 
                 * An example: |win|kdarewolf
                 * Then, after split, the winner will be on position 1.
                 */
                String winner = params.get(1);

                return new WinLog(winner);
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
                /*
                 * The POKEMON action tries to find on given line:
                 * - The player's who pokémon belongs;
                 * - The pokémon itself.
                 * 
                 * After find these variables, adds the pokémon to player's team.
                 */
                TeamLog teamLog = (TeamLog) log;

                Pokemon pokemon = teamLog.getPokemon();
                Player pokemonOwner = playerService.getPlayerFromGame(game, teamLog.getPlayer());
                
                pokemonOwner.addPokemon(pokemon);

                break;
            case MOVESET:
                /*
                 * The MOVESET action tries to find on given line:
                 * - The player's who pokémon belongs;
                 * - The pokémon using the move;
                 * - The move that will be used by.
                 * 
                 * After find these variables, add the move to desired pokémon's movepool.
                 */
                MoveLog moveLog = (MoveLog) log;
                
                pokemonOwner = playerService.getPlayerFromGame(game, moveLog.getPlayer());
                Pokemon pokemonFound = pokemonService.findByNickname(pokemonOwner, moveLog.getPokemonNickname());

                pokemonFound.addMove(moveLog.getMove());

                break;
            case SWITCH:
                /*
                 * The SWITCH action tries to find on line:
                 * - The player that's switching;
                 * - The pokémon name that's entering;
                 * - The pokémon's nickname.
                 * 
                 * After find these variables, changes the pokémon nickname from default to given.
                 */
                SwitchLog switchLog = (SwitchLog) log;
                
                pokemonOwner = playerService.getPlayerFromGame(game, switchLog.getPlayer());
                pokemonFound = pokemonService.findBySpeciesName(pokemonOwner, switchLog.getPokemonName());

                pokemonFound.setNickname(switchLog.getPokemonNickname());

                break;
            case TURN:
                /*
                 * The TURN action triggers the turns increment method on Game.
                 * 
                 * No specific data is used here.
                */
                game.addTurn();
                
                break;
            case WIN:
                /*
                 * The WIN action tries to find on line:
                 * - The winner of matcher.
                 * 
                 * After find this, sets on game instance the winner.
                 */
                WinLog winLog = (WinLog) log;

                game.setWinner(winLog.getWinnerName());

                break;
            default:
                break;
        }
    }

}
