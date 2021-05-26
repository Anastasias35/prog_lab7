package Server.utilitka;

import static java.lang.System.*;

public class StringResponse {
    private static StringBuilder stringBuilder=new StringBuilder();

    public  void append(Object obj){
        stringBuilder.append(obj);
    }

    public static void appendln(){
        stringBuilder.append("\n");
    }

    public static void appendln(Object obj){
        stringBuilder.append(obj + "\n");
    }

    public static  String get(){
        return stringBuilder.toString();
    }

    public static void clear(){
        stringBuilder.delete(0,stringBuilder.length());
    }

    public static String getAndClear(){
        String string=stringBuilder.toString();
        stringBuilder.delete(0, stringBuilder.length());
        return string;
    }

    public static void appendError(Object obj){
        stringBuilder.append(obj+"\n");
    }

}
