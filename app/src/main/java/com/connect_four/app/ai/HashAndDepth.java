package com.connect_four.app.ai;

import java.util.Objects;

class HashAndDepth {
    private final int hash;
    private final int depth;

    HashAndDepth(int hash, int depth) {
        this.hash = hash;
        this.depth = depth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HashAndDepth that = (HashAndDepth) o;
        return hash == that.hash && depth == that.depth;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hash, depth);
    }
}
