package uga.hack.mavenproject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.precisely.ApiClient;
import com.precisely.ApiException;
import com.precisely.Configuration;
import com.precisely.apis.RisksServiceApi;
import com.precisely.apis.model.CrimeRiskResponse;

public class Risks {
	
	CrimeRiskResponse result;
	String ranking = null;

	public String risk(String address) {

		ApiClient defaultClient = Configuration.getDefaultApiClient();
		RisksServiceApi api = new RisksServiceApi();
		

		try {
			api.getApiClient().generateAndSetAccessToken("FIuS5cl1ToLlspLDfsqByrDUnOm3FfWt","lEoFOmGAwqlEbpPb");
		} catch (ApiException o) {
			o.printStackTrace();
		}

		RisksServiceApi apiInstance = new RisksServiceApi(defaultClient);
		String type = null; // String | this is crime type; valid values are following 11 crime types with 'all' as default (more than one can also be given as comma separated types)
		String includeGeometry = "Y"; // String | Y or N (default is N) - if it is Y, then geometry will be part of response
		try {
			result = apiInstance.getCrimeRiskByAddress(address, type, includeGeometry);
//			System.out.println(result);
		} catch (ApiException e) {
			System.err.println("Exception when calling RisksServiceApi#getCrimeRiskByAddress");
			System.err.println("Status code: " + e.getCode());
			System.err.println("Reason: " + e.getResponseBody());
			System.err.println("Response headers: " + e.getResponseHeaders());
			e.printStackTrace();
		}
		
		JSONObject obj;

		
		obj = new JSONObject(result);
		try {
			JSONArray arr = obj.optJSONArray("themes");
			ranking = arr.getJSONObject(0).toString();
			JSONObject json = new JSONObject(ranking); 
			ranking = json.getJSONObject("crimeIndexTheme").getString("indexVariable");
			int i = ranking.indexOf("{");
			ranking = ranking.substring(i);
			json = new JSONObject(ranking);
			ranking = json.getString("score");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return ranking;
	}
}