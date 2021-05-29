package Common.data;
import Client.util.User;
import Common.data.Coordinates;
import Common.data.Person;
import Common.data.Position;

import java.io.Serializable;
import  java.util.Date;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Класс Worker, который хранится в коллекции
 */
public class Worker  implements Comparable<Worker>, Serializable {

    private Long id;
    private String name;
    private Coordinates coordinates;
    private Date creationDate;
    private Long salary;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Position position;
    private Person person;
    private User user;

    public Worker(long id, String name, Coordinates coordinates, Date creationDate,Long salary, LocalDateTime startDate, LocalDateTime endDate, Position position, Person person, User user){
        this.id=id;
        this.name=name;
        this.coordinates=coordinates;
        this.creationDate=creationDate;
        this.salary=salary;
        this.startDate=startDate;
        this.endDate=endDate;
        this.position=position;
        this.person=person;
        this.user=user;
    }

    public Worker(){

    }


    /**
     * @return id работника
     */
    public long getId(){
        return id;
    }

    /**
     * @return имя работника
     */
    public String getName(){
        return name;
    }

    /**
     * @return x-y координаты работника
     */
    public Coordinates getCoordinates(){
        return coordinates;
    }

    /**
     * @return дата занесения в протокол
     */
    public Date getCreationDate(){
        return creationDate;
    }

    /**
     *
     * @return зарплата работника
     */
    public Long getSalary(){
        return salary;
    }

    /**
     * @return дата начала работы работника
     */
    public LocalDateTime getStartDate(){
        return startDate;
    }

    /**
     * @return дата окончания работы работника
     */
    public LocalDateTime getEndDate(){
        return endDate;
    }

    /**
     * @return профессия работника
     */
    public Position getPosition(){
        return position;
    }

    /**
     * @return личность работника
     */
    public Person getPerson(){
        return person;
    }

    public User getUser(){
        return user;
    }

    public void setId(Long id){
        this.id=id;
    }

    public void setName(String name){
        this.name=name;
    }

    public void setCoordinates(Coordinates coordinates){
        this.coordinates=coordinates;
    }

    public void setSalary(Long salary){
        this.salary=salary;
    }

    public void setStartDate(LocalDateTime startDate){
        this.startDate=startDate;
    }

    public void setEndDate(LocalDateTime endDate){
        this.endDate=endDate;
    }

    public void setPosition(Position position){
        this.position=position;
    }

    public void setPerson(Person person){
        this.person=person;
    }

    public void setCreationDate(Date creationDate){
        this.creationDate=creationDate;
    }

    public void setUser(User user){
        this.user=user;
    }


    public String saveToCSV(){
        return String.valueOf(id) + "," + name + ","+ String.valueOf(coordinates.coordinatesToCSV())+ "," + creationDate.toString() + "," + salary+","+ startDate+"," + endDate + "," + position+ "," + String.valueOf(person.personToCSV());
    }

    @Override
    public String toString() {
        return  "id=" + id + "\n" +
                "name=" + name + '\n' +
                "coordinates=" + coordinates +
                "creationDate=" + creationDate + "\n" +
                "salary=" + salary + "\n" +
                "startDate=" + startDate + "\n" +
                "endDate=" + endDate + "\n" +
                "position=" + position + "\n" +
                "person=" + person + "\n" ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Worker worker = (Worker) o;
        return id == worker.id &&
                Objects.equals(name, worker.name) &&
                Objects.equals(coordinates, worker.coordinates) &&
                Objects.equals(creationDate, worker.creationDate) &&
                Objects.equals(salary, worker.salary) &&
                Objects.equals(startDate, worker.startDate) &&
                Objects.equals(endDate, worker.endDate) &&
                position == worker.position &&
                Objects.equals(person, worker.person);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, salary, startDate, endDate, position, person);
    }


    @Override
    public int compareTo(Worker worker) {
        if (coordinates.getX()-worker.coordinates.getX() !=0) return (int)(coordinates.getX()-worker.coordinates.getX());
        else return (int)(coordinates.getY() - worker.coordinates.getY());
    }


}
