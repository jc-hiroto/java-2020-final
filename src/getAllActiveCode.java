package src;
import src.hash.searchEngine;
public class getAllActiveCode {
    public static void main(String[] args){
        int []travelCode= {40,41,43,44,100,101,342,343,368,384,391,392,393,394,395,396,397,398,399,
                401,404,405,406,407,408,409,410,411,412,413,414,415,416,417,426,427,428,430,431,432,433,435,436,439,440,441,442};
        searchEngine engine = new searchEngine();
        for(int i=0;i<travelCode.length;i++)
        System.out.println("[第 "+(i+1)+" 筆資料] TravelCode: "+travelCode[i]+ " | Keyword: "+engine.reverseSearch(Integer.toString(travelCode[i])));
    }
}
