package uga.hack.mavenproject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.precisely.ApiException;
import com.precisely.apis.SchoolsServiceApi;
import com.precisely.apis.model.SchoolsNearByResponse;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class SchoolByAddress {
    
    SchoolsNearByResponse result;
    String[] ans;

    public String[] generateSchool(String address) {
        SchoolsServiceApi api = new SchoolsServiceApi();
        try {
            api.getApiClient().generateAndSetAccessToken("FIuS5cl1ToLlspLDfsqByrDUnOm3FfWt","lEoFOmGAwqlEbpPb");
        } catch (ApiException e) {
            e.printStackTrace();
        }

        String edLevel = null; // String | Single digit code for education level applicable values are P -> primary, M -> Middle, H -> High, O -> Mixed Grades for public school type andE -> Elementary , S -> Secondary , O -> Others mixed grades for private schools 
        String schoolType = null; // String | Single digit code for schoolTypes applicable values are PRI and PUB
        String schoolSubType = null; // String | Single digit code for schoolSubType Applicable values are C, M, A, R, I, L, P, V, U, S (i.e. Charter, Magnet, Alternative, Regular, Indian, Military, Reportable Program, Vocational, Unknown, Special Education)
        String gender = null; // String | Single digit code for gender Applicable values are C, F, M (Coed, All Females, All Males) Applicable for Private Schools Only
        String assignedSchoolsOnly = null; // String | Single digit code for assignedSchoolOnly applicable values are  Y/N 
        String districtSchoolsOnly = null; // String | Single digit code for districtSchoolOnly applicable values are Y/N 
        String searchRadius = "1200"; // String | Search Radius within which schools are searched
        String searchRadiusUnit = "Meters"; // String | Search Radius unit applicable values are feet,kilometers,miles,meters
        String travelTime = null; // String | Travel Time based on ‘travelMode’ within which schools are searched.
        String travelTimeUnit = null; // String | Travel Time unit applicable values are minutes,hours,seconds,milliseconds 
        String travelDistance = null; // String | Travel Distance based on ‘travelMode’ within which schools are searched.
        String travelDistanceUnit = null; // String | Travel distanceUnit applicable values are feet,kilometers,miles,meters
        String travelMode = null; // String | Travel mode Required when travelDistance or travelTime is specified. Accepted values are walking,driving
        String maxCandidates = null; // String | Max result to search 
        try {
            result = api.getSchoolsByAddress(address, edLevel, schoolType, schoolSubType, 
                                    gender, assignedSchoolsOnly, districtSchoolsOnly, searchRadius, 
                                    searchRadiusUnit, travelTime, travelTimeUnit, travelDistance, 
                                    travelDistanceUnit, travelMode, maxCandidates);
//             System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling SchoolsServiceApi#getSchoolsByAddress");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }


        JSONObject obj;
        
        String ranking = null;
        try {
            obj = new JSONObject(result);
            JSONArray arr = obj.optJSONArray("school");
            ranking = arr.getJSONObject(0).toString();
            int i = ranking.indexOf("{");
            ranking = ranking.substring(i);


            JSONObject json = new JSONObject(ranking.trim()); 
            arr = json.optJSONArray("schoolRanking");
            ans = new String[arr.length()];
            for (int j = 0; j < arr.length(); j++) {
                ranking = arr.getJSONObject(j).getString("statePercentileScore");
                ans[j] = ranking;
            }
           
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException npe) {
            handleException(npe);
        }

        
        return ans;
    }
    
    private void handleException(NullPointerException npe) {
    	Alert a = new Alert(AlertType.WARNING);
    	a.setContentText("There is no school within 1200 meters.");
    	a.showAndWait();
    }
    
}