package com.charapadev.pokemon;

/**
 * Represents the data containing the variants about each Pokemon species.
 * <p><p>
 * For example, Samurott is the general name of species and Hisui is one of variants from Samurott.
 */

public record SpeciesVariant(
    String generalName,
    String variant
) {
    
}
