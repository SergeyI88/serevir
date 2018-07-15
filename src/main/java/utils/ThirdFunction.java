package utils;

@FunctionalInterface
public interface ThirdFunction<Q, W, E, G, R> {
    public R apply(Q q, W w, E e, G g);
}
