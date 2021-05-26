package Server.utilitka;

import Client.util.Creator;
import Common.data.Coordinates;
import Common.data.Position;
import Common.data.SortBySalary;
import Common.data.Worker;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс управления коллекцией
 */
//работа с коллекциями: получение времени, размера, создание нового id, добавление элемента
public class CollectionManager{
      private LinkedHashSet<Worker> workerCollection =new LinkedHashSet<>();
      private List<Worker> sortWorkerByCoordinates= new ArrayList<>();
      private List<Worker> sortWorkerBySalary=new ArrayList<>();
      private Creator creator;
      private LocalDateTime lastIntTime;
      private FileManager fileManager;

      public CollectionManager(FileManager fileManager, Creator creator){
          this.fileManager=fileManager;
          this.creator=creator;
          this.lastIntTime=null;
          loadCollection();
      }

      public CollectionManager(FileManager fileManager){
          this.fileManager=fileManager;
          this.lastIntTime=null;
          loadCollection();
      }

    /**
     * Загрузка коллекции
     */
    public void loadCollection(){
        workerCollection=fileManager.readCollection();
        lastIntTime=LocalDateTime.now();
    }


    /**
     * @return Время инициализации
     */
    public LocalDateTime getLastIntTime(){
        return lastIntTime;
    }

    /**
     * @return Количество элементов в коллекции
     */
    public int sizeCollection(){
        return workerCollection.size();
    }

    /**
     * Очищение коллекции
     */
    public void clearCollection(){
        workerCollection.clear();
    }

    /**
     * Сохранение коллекции в файл
     */
    public void saveCollection(){
        fileManager.writeCollection(workerCollection);
    }

    /**
     * Проверка, есть ли элемент с таким же id
     * @param id
     * @return наличие элемента коллекции с таким же id
     */
    public boolean comparingId(Long id){
          return workerCollection.stream().anyMatch((x) ->id.equals(x.getId()));
    }

    /**
     * Удаление элемент,если у его id совпадает с параметром
     * @param id
     */
    public void removeItem(Long id){
          workerCollection.removeIf(worker1 -> id.equals(worker1.getId()));
    }


    /**
     * Добавление элемента в коллекцию
     * @param worker
     */
    public void addToCollection(Worker worker){
          workerCollection.add(worker);
          StringResponse.appendln("Worker успешно создан");
    }

    /**
     * Генерация id для нового элемента
     * @return
     */
    public Long nextId(){
         Long maxId=0l;
         maxId=workerCollection.stream().count();
         return maxId+1;
    }

    /**
     *Считает, сколько элементов в коллекции имеют такую же профессию
     * @param position
     * @return Количество элементов с такой же профессией
     */
    public int filterByPosition(Position position){
        return (int) workerCollection.stream().filter(x->position.compareTo(x.getPosition())<0).count();
    }


    public boolean maxCoordinates(Worker worker) {
        return workerCollection.stream().noneMatch(x -> worker.compareTo(x)<0);
    }

    public boolean minSalary(Worker worker) {
        return workerCollection.stream().noneMatch(x -> worker.compareTo(x) >0);
    }

    public void deleteIfLower(Worker worker){
        sortWorkerBySalary.removeIf(worker1 -> worker.compareTo(worker1)>0);
    }


    /**
     * Сортировка элементов по возрастанию зарплаты
     */
    public void sortSalary(){
          workerCollection.stream()
                  .sorted(new SortBySalary())
                  .forEach(x->StringResponse.appendln(x));

    }



    /**
     * Сортировка элементов по убыванию местоположение
     */
    public void reverseSort(){
        workerCollection.stream()
                          .sorted(new SortBySalary().reversed())
                          .forEach(x->StringResponse.appendln());

    }



    /**
     * Представление элемента в строковом формате
     */
    public void stringCollection(){
        if (workerCollection.isEmpty()) {
            StringResponse.appendln("Коллекция пуста");
        }
        else {
            workerCollection.stream().forEach(x->StringResponse.appendln(x.toString()));
        }
    }

}
