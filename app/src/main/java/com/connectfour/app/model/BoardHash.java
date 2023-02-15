package com.connectfour.app.model;

import java.util.Objects;

public class BoardHash {

    private final long hash1;
    private final long hash2;

    public BoardHash(long hash1, long hash2) {
        this.hash1 = hash1;
        this.hash2 = hash2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardHash boardHash = (BoardHash) o;
        return hash1 == boardHash.hash1 && hash2 == boardHash.hash2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hash1, hash2);
    }
}
