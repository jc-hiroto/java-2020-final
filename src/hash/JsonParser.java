import java.io.FileNotFoundException;
import java.lang.StringIndexOutOfBoundsException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.JSONException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

// Include a JSON read-in method, a JSON to String method, a get travel code name by travel code method
public class JsonParser{
	
	JSONArray travelCodeList = new JSONArray();

	// JSON file read-in and return a JSON Array
	public JSONArray ReadJsonFile(){
	
		@SuppressWarnings("unchecked");
		JSONParser jsonParser = new JSONParser();

		try(FileReader reader = new FileReader("travel_code.json")){

			travelCodeList = jsonParser.parser(reader);
			return travelCodeList;

		}catch(FileNotFoundException){
			e.printStackTrace();
		}catch(IOException){
			e.printStackTrace();
		}catch(ParseException){
			e.printStackTrace();
		}

	}	

	// Convert JSON to string buffer and return travel code info in a 2-dim string buffer (default)
	public StringBuffer JsonToStringBuffer(){
		
		StringBuffer[][] travelCodeInfo = new StringBuffer[this.travelCodeList.length()][2];

		for(int i = 0; i < this.travelCodeList.length(); i++){
			travelCodeInfo[i][0] = this.travelCodeList[i].getJSONObject("travel_code");
			travelCodeInfo[i][1] = this.travelCodeList[i].getJSONObject("travel_code_name");
		}

		return travelCodeInfo;
	}

	// Convert JSON to string buffer and return travel code info in a 2-dim string buffer
	public StringBuffer JsonToStringBuffer(JSONArray trvlCodeList){
		
		StringBuffer[][] travelCodeInfo = new StringBuffer[trvlCodeList.length()][2];

		for(int i = 0; i < trvlCodeList.length(); i++){
			travelCodeInfo[i][0] = trvlCodeList[i].getJSONObject("travel_code");
			travelCodeInfo[i][1] = trvlCodeList[i].getJSONObject("travel_code_name");
		}

		return travelCodeInfo;
	}
	
	// Pick out the travel_code that match the input travel_code_name and return a string buffer
	public StringBuffer getTravelCodeName(StringBuffer travelCodeName){
		
		StringBuffer travelCodeMatch = new StringBuffer;

		for(int i = 0; i < this.travelCodeList.length(); i++){
			if(this.travelCodeList[i].getJSONObject("travel_code_name").JsonToStringBuffer().equals(travelCodeName)){
				travelCodeMatch.append(" " + this.travelCodeList[i].getJSONObject("travel_code").JsonToStringBuffer());
			}else{
				continue;
			}
		}

		return travelCodeMatch;
	}
	
}