package com.connectfour.app.model;

import java.util.Objects;

/**
 * Represents the hash values of a Connect Four game {@code Board}.
 *
 * @see Board
 */
public class BoardHash {

    /**
     * The first hash value, representing the state of {@code PLAYER_1} disks on the board.
     */
    private final long hash1;

    /**
     * The second hash value, representing the state of {@code PLAYER_2} disks on the board.
     */
    private final long hash2;

    /**
     * Constructs a new {@code BoardHash} object with the given hash values.
     *
     * @param hash1 the {@link #hash1} value
     * @param hash2 the {@link #hash2} value
     */
    public BoardHash(long hash1, long hash2) {
        this.hash1 = hash1;
        this.hash2 = hash2;
    }

    /**
     * Compares this {@code BoardHash} object to another object for equality.
     *
     * @param o the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardHash boardHash = (BoardHash) o;
        return hash1 == boardHash.hash1 && hash2 == boardHash.hash2;
    }

    /**
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(hash1, hash2);
    }
}
