package Common.data;

import Common.data.Worker;

import java.io.Serializable;
import java.util.Comparator;

public class SortBySalary implements Comparator<Worker> , Serializable {

    @Override
    public int compare(Worker worker1, Worker worker2){
        return worker1.getSalary().compareTo(worker2.getSalary());
    }
}
