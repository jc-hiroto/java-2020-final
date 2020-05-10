package src.hash;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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

		try(FileReader reader = new FileReader("src/hash/travel_code.json")){

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
		//System.out.println(travelCodeArray.size());
		for(int i = 0; i < travelCodeArray.size(); i++){
			JSONObject travelCodeObj = (JSONObject)travelCodeArray.get(i);
			index.addIndex((String) travelCodeObj.get("travel_code"), (String) travelCodeObj.get("travel_code_name"));
		}
	}

	public String searchTravelCode(String searchWord){
		for(int i = 0; i < index.getTravelCode().size(); i++){
			if(index.getTravelCodeName().get(i).contains(searchWord)){
				System.out.println("Using word: "+searchWord+" Find result TravelCode: "+index.getTravelCode().get(i)+" for category: "+ index.getTravelCodeName().get(i));
				return index.getTravelCode().get(i);
			}
		}
		System.out.println("Using word: "+searchWord+". Result not found!");
		return "ERR";
	}

	public String reverseSearch(String code){
		for(int i = 0; i < index.getTravelCode().size(); i++){
			if(index.getTravelCode().get(i).equals(code)){
				System.out.println("Using code: "+code+" Find result Category: "+index.getTravelCodeName().get(i));
				return index.getTravelCodeName().get(i);
			}
		}
		System.out.println("Using code: "+code+". Result not found!");
		return "ERR";
	}
}