package Common.data;

import java.io.Serializable;

/**
 * Класс перечислений профессий
 */
public enum Position implements Serializable {
    DIRECTOR,
    ENGINEER,
    LEAD_DEVELOPER,
    CLEANER;

    /**
     * @return Список профессий
     */
    public static String lookPosition(){
        String positionList="";
        for(Position position:values()){
            positionList +=position.name() + "\n";
        }
        System.out.println("Выберите один из предложенных вариантов" );
        return positionList;
    }
}

