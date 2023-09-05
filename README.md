# Replaydown - The Pokémon Showdown! analyzer

The Replaydown API is a project focused on **extract informations about a match** on [Pokémon Showdown!](https://pokemonshowdown.com/) simulator.

## Summary

To navigate easier on docs, the topics are splitted on:

- [Which information can I get?](#which-information-can-i-get)
- [Install](#install)
- [How it works](#how-it-works)
- [Technical topics](#technical-topics)

## Which information can I get?

Using the logs available on replays, we can get some informations like:

- The envolved players
- The Pokémon and your discoverable moves

And so much more. The complete list are available [here](#complete-list-of-data).

---

## Install

You can run the application on **Dev Mode** or **built JAR**.

### Dev mode

To run in this mode, just go on project root and type this on terminal:
> ./gradlew quarkusDev

### Built JAR

On project root, run the command below to generate the executable JAR:
> ./gradlew build

If works properly, the built JAR file is on ```build/quarkus-app/quarkus-run.jar```.
Then, run the command below to start the server:
> java -jar build/quarkus-app/quarkus-run.jar

---

## How it works

### What application needs

First, you need the replay ID to application retrieves the text logs. This information looks like it:
> oumonotype-82345404

If you have the complete link of replay, you can find this after the ```https://replay.pokemonshowdown.com/``` link, like this:

> <https://replay.pokemonshowdown.com/oumonotype-82345404>

### Requesting data

Considering your application is hosted on ```http://localhost:8080/```, just hit this link passing the replay ID after ```/```, as above:
> <http://localhost:8080/oumonotype-82345404>

### Returned data

After, send this, if the replay ID exists, the application will return some data containing the players, pokémon and another data as above:

```json
{
    "format": "oumonotype",
        "player1": {
            "nickname": "kdarewolf",
            "team": [
            {
                "name": "Kecleon",
                "nickname": "May Day Parade",
                "moves": [
                    "Sucker Punch",
                    "Power-Up Punch",
                    "Fake Out",
                    "Shadow Sneak"
                ]
            },
            ...
        ]
    }
    ...
}
```

**Note:** Using the replay, sometimes it's not possible to get all Pokémon movepool, but if a move is used on battle, the application will get it.

### Now, it works

If the steps are sucessfully ended, the application is working properly. Time to Mega-Analyze!!!

---

## Technical topics

If you just wanna run the application, **you don't need to read this topic**!
Otherwise, if you wanna more about that is used, how works better or futures updates, this topic will interest you!

### Technologies used

Develop this application envolves:

- [Quarkus](https://quarkus.io/) + Java 17
- [GraalVM](https://www.graalvm.org/)
- [RestEasy Reactive](https://quarkus.io/guides/resteasy-reactive)
- [Reactive programming](https://www.baeldung.com/cs/reactive-programming)
- Pokémon Showdown! [Replays](https://replay.pokemonshowdown.com/)

### Complete list of data

Currently, the application gets:

- Players nicknames and teams
- Pokémon nicknames and species
- Pokémon discoverable movepools
- Battle format/tier

And I'm working to extract more informations, like:

- How much turns the battle reached
- Who is the winner
- Battle score
- Reason of winning
- And so much more!

### Limitations

Currently, the API has some limitations that I'll be listing on this topic on every update.
Some of limitations discovered are:

- Pokémon with ```-``` between species name like ```Ho-oh``` and ```Kommo-o``` (```Samurott-Hisui``` and variants works properly).
- Battles with Pokémon not showable on first view(random battles)
