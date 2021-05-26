package Server.utilitka;

import Common.data.*;

import Common.exceptions.IncorrectVariableException;

import javax.swing.text.Style;
import java.io.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;


/**
 * Класс, работающий с загрузкой и чтением коллекции из файла
 */
public class FileManager {
    private final String fileName;
    private File file;
    Stack<Worker> workerStack=new Stack<>();

    public FileManager(String fileName){
        this.fileName=fileName;
        try{
            this.file=new File(fileName);
        }catch(NullPointerException exception){
            System.out.println();
        }
    }


    /**
     * Запись коллекции в файл
     * @param collection
     */
    public void writeCollection(LinkedHashSet<Worker> collection){
        try (PrintWriter printWriter = new PrintWriter(this.file)) {
            String text="";
            for(Worker w:collection){
                text=w.saveToCSV()+"\n";
                printWriter.write(text);

            }
        }catch (FileNotFoundException exception){
            if(!file.canWrite() & file.exists()) System.out.println("Нет прав на запись");
            else
                System.out.println("Файл с таким именем не найден");
        } catch (Exception exception) {
            System.out.println("");
        }
    }


    /**
     * Чтение коллекции из файла
     * @return Коллекция, которая хранилась в файле
     */
    public LinkedHashSet<Worker> readCollection(){
        try(Scanner scannerCollection=new Scanner(new File(this.fileName))){
            LinkedHashSet<Worker> workers=new LinkedHashSet<>();
            String line="";
            if (!scannerCollection.hasNext()) throw new NoSuchElementException();
            while(scannerCollection.hasNext()){
                 line=scannerCollection.nextLine();
                 Worker worker=new Worker();
                 String[] reader = line.split(",");
                 for(int i=0;i<9;i++){
                     switch (i){
                         case 0:
                             Long id=Long.parseLong(reader[0]);
                             worker.setId(id);
                             break;
                         case 1:
                             worker.setName(reader[1]);
                             break;
                         case 2:
                             String[] coordinates1=reader[2].split(" ");
                             worker.setCoordinates(new Coordinates(Integer.parseInt(coordinates1[0]),Double.parseDouble(coordinates1[1])));
                             break;
                         case 3:
                             Instant instant=Instant.now();
                             worker.setCreationDate(Date.from(instant));
                             break;
                         case 4:
                             worker.setSalary(Long.parseLong(reader[4]));
                             break;
                         case 5:
                             worker.setStartDate(LocalDateTime.parse(reader[5]));
                             break;
                         case 6:
                             worker.setEndDate(ZonedDateTime.parse(reader[6]));
                             break;
                         case 7:
                             reader[7]=reader[7].toUpperCase();
                             switch (reader[7]) {
                                 case "DIRECTOR":
                                     worker.setPosition(Position.DIRECTOR);
                                     break;
                                 case "ENGINEER":
                                     worker.setPosition(Position.ENGINEER);
                                     break;
                                 case "LEAD_DEVELOPER":
                                     worker.setPosition(Position.LEAD_DEVELOPER);
                                     break;
                                 case "CLEANER":
                                     worker.setPosition(Position.CLEANER);
                                     break;
                                 default:
                                     throw new IncorrectVariableException();
                             }
                             break;
                         case 8:
                             String[] person1=reader[8].split(" ");
                             Person person=new Person();
                             person.setWeight(Double.parseDouble(person1[0]));
                             switch (person1[1].toUpperCase()){
                                 case "GREEN":
                                     person.setEyecolor(Color.GREEN);
                                     break;
                                 case "BLUE":
                                     person.setEyecolor(Color.BLUE);
                                     break;
                                 case "ORANGE":
                                     person.setEyecolor(Color.ORANGE);
                                     break;
                                 case "YELLOW":
                                     person.setEyecolor(Color.YELLOW);
                                     break;
                                 default:
                                     throw new IncorrectVariableException();
                             }
                             switch (person1[2].toUpperCase()){
                                 case "GREEN":
                                     person.setHaircolor(Color.GREEN);
                                     break;
                                 case "BLUE":
                                     person.setHaircolor(Color.BLUE);
                                     break;
                                 case "ORANGE":
                                     person.setHaircolor(Color.ORANGE);
                                     break;
                                 case "YELLOW":
                                     person.setHaircolor(Color.YELLOW);
                                     break;
                                 default:
                                     throw new IncorrectVariableException();
                             }
                             switch (person1[3].toUpperCase()){
                                 case "RUSSIA":
                                     person.setNationality(Country.RUSSIA);
                                     break;
                                 case "GERMANY":
                                     person.setNationality(Country.GERMANY);
                                     break;
                                 case "VATICAN":
                                     person.setNationality(Country.VATICAN);
                                     break;
                                 default:
                                     throw new IncorrectVariableException();
                             }
                             worker.setPerson(person);
                             break;
                         default:
                             throw new IncorrectVariableException();
                     }
                 }
                 workers.add(worker);
            }
            System.out.println("Коллекция успешно загружена");
            return workers;
        }catch (FileNotFoundException exception){
            if(!file.canRead()& file.exists()) StringResponse.appendError("Нет прав на чтение файла");
            else
                System.out.println("файл с таким именем не найден");
        }catch(NoSuchElementException exception) {
            System.out.println("файл пуст");
        }catch (IncorrectVariableException  | ArrayIndexOutOfBoundsException variableException) {
            System.out.println("Необходимая коллекция не найдена");
        }catch (NumberFormatException exception){
            System.out.println("Неверный формат данных");
        } catch(Exception exception){
            System.out.println("");
        }
        return new LinkedHashSet<>();
    }

}

