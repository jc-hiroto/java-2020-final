package src.hash;

import java.io.FileNotFoundException;
import java.lang.StringIndexOutOfBoundsException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.JSONException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class ReadJsonFile{
	
	@SuppressWarnings("unchecked");
	JSONParser jsonParser = new JSONParser();


	try(FileReader reader = new FileReader("travel_code.json")){
		JSONArray rawTravelCodeList = jsonParser.parser(reader);
		rawTravelCodeList.forEach(tvlc -> ParseTravelCodeObject((JSONObject) tvlc));
	}catch(FileNotFoundException){
		e.printStackTrace();
	}catch(IOException){
		e.printStackTrace();
	}catch(ParseException){
		e.printStackTrace();
	}

}

private static class ParseTravelCodeObject(JSONObject travelCode){
	
}

public class JsonParser{
	
	public String[] getTravelCodeInfo(JSONObject JsonParser.ReadJsonFile){

	}
}