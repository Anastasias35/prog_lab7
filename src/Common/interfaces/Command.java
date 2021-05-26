package Common.interfaces;

import Common.data.Worker;

/**
 * Интерфейс для всех команд
 */
public interface Command {
    String getName();
    String getDescription();
    boolean execute(String argument, Worker worker);
}