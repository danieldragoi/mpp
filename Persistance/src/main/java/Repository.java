import java.util.List;

public interface Repository<T, Tid> {
    void add(T elem);
    void delete(T elem);
    void update(T elem, Tid id);
    T findById(Tid id);
    List<T> getAll();

}
