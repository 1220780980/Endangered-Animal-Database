package src.backend.delegates;

import java.util.List;

/**
 * This interface uses the delegation design pattern where instead of having
 * the TerminalTransactions class try to do everything, it will only
 * focus on handling the UI. The actual logic/operation will be delegated to the
 * controller class (in this case Bank).
 *
 * TerminalTransactions calls the methods that we have listed below but
 * Bank is the actual class that will implement the methods.
 */
public interface Delegate<T> {
    List<T> list();
    public void insert(T t);

    public void get(String id);

    public void delete(T t);
    public void update(T t, String sqlString);

    public void close();
}