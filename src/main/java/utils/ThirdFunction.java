package utils;

@FunctionalInterface
public interface ThirdFunction<Q, W, E, G, R> {
    R apply(Q q, W w, E e, G g);
}
