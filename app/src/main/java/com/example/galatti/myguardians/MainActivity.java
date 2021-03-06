package com.example.galatti.myguardians;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public static class MainFragment extends Fragment {
        private View rootView;
        private Activity activity;
        private GuardianListAdapter adapter;
        private ArrayList guardians;
        private ProgressBar spinner;

        public MainFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_main, container, false);
            activity = this.getActivity();

            final EditText editTextPsnId = (EditText) rootView.findViewById(R.id.editTextPsnId);


            Button buttonGo = (Button) rootView.findViewById(R.id.buttonGo);
            buttonGo.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    String psnId = editTextPsnId.getText().toString();

                    // auto hide keyboard
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    editTextPsnId.clearFocus();
                    imm.hideSoftInputFromWindow(editTextPsnId.getWindowToken(), 0);

                    if (psnId != null) {
                        new AsyncTaskParseJson().execute(psnId);
                    }

                }
            });


            initialiseGuardList();

            spinner = (ProgressBar) rootView.findViewById(R.id.progressBar);

            return rootView;
        }

        private void initialiseGuardList() {
            guardians = new ArrayList<Guardian>();

            for (int i = 0; i < 3; i++) {
                Guardian guardian = new Guardian();
                guardian.setGuardianClass("No Class");
                guardian.setLevel("No Light Level");
                guardian.setTimePlayed("0 hours played");
                guardian.setStats("Armor: 0 Agility: 0 Recover 0");
                guardian.setEmblemPath(null);
                guardians.add(guardian);
            }
            adapter = new GuardianListAdapter(activity, guardians);
            ListView lv1 = (ListView) rootView.findViewById(R.id.listViewGuardians);
            lv1.setAdapter(adapter);
        }

        private void resetGuardList() {
            for (int i = 0; i < 3; i++) {
                Guardian guardian = (Guardian) guardians.get(i);
                guardian.setGuardianClass("No Class");
                guardian.setLevel("No Light Level");
                guardian.setTimePlayed("0 hours played");
                guardian.setStats("Armor: 0 Agility: 0 Recover 0");
                guardian.setEmblemPath(null);
                guardians.set(i, guardian);
            }
        }

        private class AsyncTaskParseJson extends AsyncTask<String, String, String> {

            final String TAG = "AsyncTaskParseJson.java";
            String membershipId = null;


            @Override
            protected void onPreExecute() {
                spinner.setVisibility(View.VISIBLE);
                membershipId = null;
                resetGuardList();
            }

            @Override
            protected String doInBackground(String... arg0) {

                String psnId = arg0[0];
                if (psnId != null && psnId.length() > 0) {
                    Boolean isSand = psnId.trim().equalsIgnoreCase("sandman_br");
                   if (!isSand.booleanValue()) {
                        membershipId = getMembershipId(psnId.replaceAll("\\s+",""));
                   }
                    if (membershipId != null) {
                        getGuardians(membershipId);
                    }
                }

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
                    JSONObject response = json.getJSONObject("Response");
                    JSONObject data = response.getJSONObject("data");



                    dataJsonArr = data.getJSONArray("characters");
                    Guardian guardian;
                    JSONObject character;
                    JSONObject characterBase;
                    JSONObject stats;
                    JSONObject statArmor;
                    JSONObject statAgility;
                    JSONObject statRecover;
                    String token;
                    Long hash;
                    String guardianClass;
                    Long timePlayed;

                    for (int i = 0; i < dataJsonArr.length(); i++) {

                        guardianClass = "";
                        character = dataJsonArr.getJSONObject(i);

                        // show the values in our logcat
                        characterBase = character.getJSONObject("characterBase");
                        Log.e(TAG, "characterId: " + characterBase.getString("characterId"));

                        guardian = (Guardian) guardians.get(i);

                        token = characterBase.getString("raceHash");
                        if (token != null) {
                            hash = new Long(token);
                            guardianClass = guardianClass + Guardian.getTerm(hash) + " ";
                        }

                        token = characterBase.getString("genderHash");
                        if (token != null) {
                            hash = new Long(token);
                            guardianClass = guardianClass + Guardian.getTerm(hash) + " ";
                        }

                        token = characterBase.getString("classHash");
                        if (token != null) {
                            hash = new Long(token);
                            guardianClass = guardianClass + Guardian.getTerm(hash);
                        }

                        guardian.setGuardianClass(guardianClass);

                        token = characterBase.getString("powerLevel");

                        if (token != null) {
                            Integer level = new Integer(token);
                            if (level != null) {
                                if (level <= 1) {
                                    guardian.setLevel("No Light Level");
                                } else {
                                    guardian.setLevel("Light Level " + level.toString());
                                }
                            }
                        }


                        token = characterBase.getString("minutesPlayedTotal");
                        if (token != null) {
                            timePlayed = new Long(token);
                            timePlayed = timePlayed / 60;
                            guardian.setTimePlayed("Played " + timePlayed.toString() + " hours");
                        }

                        guardian.setEmblemPath("http://www.bungie.net" + character.getString("emblemPath"));

                        stats = characterBase.getJSONObject("stats");
                        statArmor = stats.getJSONObject("STAT_ARMOR");
                        statAgility = stats.getJSONObject("STAT_AGILITY");
                        statRecover = stats.getJSONObject("STAT_RECOVERY");

                        guardian.setArmor(statArmor.getString("value"));
                        guardian.setAgility(statAgility.getString("value"));
                        guardian.setRecovery(statRecover.getString("value"));

                        guardian.setStats("Armor: " + guardian.getArmor() +
                                " Agility: " + guardian.getAgility() +
                                " Recovery: " + guardian.getRecovery());

                        guardians.set(i, guardian);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void onPostExecute(String strFromDoInBg) {
                adapter.upDateEntries(guardians);
                spinner.setVisibility(View.GONE);

            }
        }
    }
}
