import java.io.FileNotFoundException;
import java.lang.StringIndexOutOfBoundsException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.JSONException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class JsonParser{
	
	public JSONArray ReadJsonFile(){
	
		@SuppressWarnings("unchecked");
		JSONParser jsonParser = new JSONParser();

		try(FileReader reader = new FileReader("travel_code.json")){

			JSONArray travelCodeList = jsonParser.parser(reader);
			return travelCodeList;

		}catch(FileNotFoundException){
			e.printStackTrace();
		}catch(IOException){
			e.printStackTrace();
		}catch(ParseException){
			e.printStackTrace();
		}

	}	

	public StringBuffer JsonToStringBuffer(JSONArray travelCodeList){
		
		StringBuffer[][] travelCodeInfo = new StringBuffer[travelCodeList.length()][2];

		for(int i = 0; i < travelCodeList.length(); i++){
			travelCodeInfo[i][0] = travelCodeList[i].getJSONObject("travel_code");
			travelCodeInfo[i][1] = travelCodeList[i].getJSONObject("travel_code_name");
		}

		return travelCodeInfo;
	}
	
	public StringBuffer getTravelCodeName(StringBuffer travelCodeName){
		;
	}
}