package com.example.galatti.myguardians;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Rodrigo Galatti on 04/03/2015.
 */
public class AsyncTaskParseJson extends AsyncTask<String, String, String> {

    final String TAG = "AsyncTaskParseJson.java";


    @Override
    protected void onPreExecute() {}

    @Override
    protected String doInBackground(String... arg0) {

        String membershipId = getMembershipId("sandman_br");

        getGuardians(membershipId);

        return null;
    }

    private String getMembershipId(String id) {

        String membershipId = null;

        String yourJsonStringUrl = "http://www.bungie.net/Platform/Destiny/SearchDestinyPlayer/2/" + id;

        JSONArray dataJsonArr;

        try {

            // instantiate our json parser
            JsonParser jParser = new JsonParser();

            // get json string from url
            JSONObject json = jParser.getJSONFromUrl(yourJsonStringUrl);

            dataJsonArr = json.getJSONArray("Response");
            JSONObject member = dataJsonArr.getJSONObject(0);
            membershipId = member.getString("membershipId");

            Log.e(TAG, "membershipId: " + membershipId);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return membershipId;
    }

    private void getGuardians(String membershipId) {

        String yourJsonStringUrl = "http://www.bungie.net/Platform/Destiny/2/Account/" + membershipId;

        JSONArray dataJsonArr;

        try {

            // instantiate our json parser
            JsonParser jParser = new JsonParser();

            // get json string from url
            JSONObject json = jParser.getJSONFromUrl(yourJsonStringUrl);
            //Log.e(TAG, "*** json  *** = \n" + json.toString(3));
            JSONObject response = json.getJSONObject("Response");
            //Log.e(TAG, "*** response *** = \n" + response.toString(3));
            JSONObject data = response.getJSONObject("data");
            //Log.e(TAG, "*** data *** = \n" + data.toString(3));


            dataJsonArr = data.getJSONArray("characters");


            for (int i = 0; i < dataJsonArr.length(); i++) {

                JSONObject characters = dataJsonArr.getJSONObject(i);

                // show the values in our logcat
                JSONObject characterBase = characters.getJSONObject("characterBase");
                Log.e(TAG, "characterId: " + characterBase.getString("characterId"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(String strFromDoInBg) {}
}
