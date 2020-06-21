package src.hash;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import src.Debugger;

import java.io.*;

// Include a JSON read-in method, a JSON to String method, a get travel code name by travel code method
public class searchEngine{
	private travelSearchIndex index = new travelSearchIndex();
	private JSONObject travelCodeList = new JSONObject();
	private JSONArray travelCodeArray = new JSONArray();
	public searchEngine(){
		ReadJsonFile();
		JsonToIndex();
	}
	// JSON file read-in and return a JSON Array
	public void ReadJsonFile(){
		JSONParser jsonParser = new JSONParser();
		InputStream in = getClass().getResourceAsStream("/src/hash/travel_code.json");


		try(BufferedReader reader = new BufferedReader(new InputStreamReader(in));){

			travelCodeList = (JSONObject) jsonParser.parse(reader);
			travelCodeArray = (JSONArray)travelCodeList.get("travel_list");

		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}catch(ParseException e){
			e.printStackTrace();
		}

	}
	// Convert JSON to string buffer and return travel code info in a 2-dim string buffer (default)
	public void JsonToIndex(){
		Debugger.showDebugMessage("[INFO] SearchEngine - Index list size: "+travelCodeArray.size());
		for(int i = 0; i < travelCodeArray.size(); i++){
			JSONObject travelCodeObj = (JSONObject)travelCodeArray.get(i);
			index.addIndex((String) travelCodeObj.get("travel_code"), (String) travelCodeObj.get("travel_code_name"));
		}
	}
	// search the travel code using debugger
	public String searchTravelCode(String searchWord){
		if(searchWord == ""){
			return "";
		}
		for(int i = 0; i < index.getTravelCode().size(); i++){
			if(index.getTravelCodeName().get(i).contains(searchWord)){
				Debugger.showDebugMessage("[SUCCESS] SearchEngine - Using word: "+searchWord+" Find result TravelCode: "+index.getTravelCode().get(i)+" for category: "+ index.getTravelCodeName().get(i));
				return index.getTravelCode().get(i);
			}
		}
		Debugger.showDebugMessage("[WARNING] SearchEngine - Using word: "+searchWord+". Result not found!");
		return "ERR";
	}
	// search the travel code name using travel code
	public String reverseSearch(String code){
		for(int i = 0; i < index.getTravelCode().size(); i++){
			if(index.getTravelCode().get(i).equals(code)){
				return new String(index.getTravelCodeName().get(i));
			}
		}
		Debugger.showDebugMessage("[WARNING] SearchEngine - Using code: "+code+". Result not found!");
		return "ERR";
	}
}
