package utils;

@FunctionalInterface
public interface ThirdFunction<Q, W, E> {
    E apply(Q q, W w, int id, String nameColumn);
}
