package main.java.es.agata.twochess.board.moves;

import java.util.Optional;

public interface Cache<K, E> {

    Optional<E> get(K key);

    void put(K key, E element);
}
