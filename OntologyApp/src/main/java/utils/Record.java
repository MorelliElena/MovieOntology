package utils;

import java.util.ArrayList;
import java.util.List;

public class Record {
    private final List<String> resultList;

    public Record() {
        this.resultList = new ArrayList<>();
    }

    public void addParameter(List<String> list) {
        this.resultList.addAll(list);
    }

    @Override
   public String toString() {
       StringBuilder str = null;
       for (String s: resultList) {
           if(str != null){
               str.append("\t").append(s);
           } else {
               str = new StringBuilder(s);
           }
       }
        return str != null ? str.toString() +'\n' : "";
   }
}
