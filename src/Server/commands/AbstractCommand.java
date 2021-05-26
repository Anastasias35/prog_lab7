package Server.commands;
import Common.interfaces.Command;

import java.util.Objects;

/**
 * Абстрактный класс содержит поля, методы  и описание
 */
public abstract  class AbstractCommand implements Command {
    private final String name;
    private final String description;
    public AbstractCommand(String name, String description){
        this.name=name;
        this.description=description;
    }

    /**
     * @return имя команды
     */
    public String getName(){
        return name;
    }

    /**
     * @return описание команды
     */
    public String getDescription(){
        return description;
    }



    @Override
    public String toString() {
        return "AbstractCommand{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractCommand that = (AbstractCommand) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }
}
