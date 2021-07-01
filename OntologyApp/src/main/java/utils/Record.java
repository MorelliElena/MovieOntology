package utils;

import java.util.ArrayList;
import java.util.List;

public class Record {
    private final List<String> resultList;

    public Record() {
        this.resultList = new ArrayList<>();
    }

    public Record(List<String> paramList) {
        this.resultList = paramList;
    }

    public void addParameter(List<String> list) {
        this.resultList.addAll(list);
    }

    public void addParameter(String param) {
        this.resultList.add(param);
    }

    public List<String> getParamList() {
        return resultList;
    }

   @Override
   public String toString() {
       String str = null;
       for (String s: resultList) {
           if(str != null){
               str =  str + "\t" + s;
           } else {
               str = s;
           }
       }
        return str +'\n';
   }
}
