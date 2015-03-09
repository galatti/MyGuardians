package com.example.galatti.myguardians;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rodrigo Galatti on 05/03/2015.
 */
public class Guardian {

    private String guardianClass;
    private String level;
    private String timePlayed;

    private String emblemPath;

    private static final Map<Long, String> hashes;

    static {
        Map<Long, String> aMap = new HashMap<Long, String>();
        aMap.put(159615086l, "glimmer");
        aMap.put(1415355184l, "crucible marks");
        aMap.put(1415355173l, "vanguard marks");
        aMap.put(898834093l, "exo");
        aMap.put(3887404748l, "human");
        aMap.put(2803282938l, "awoken");
        aMap.put(3111576190l, "male");
        aMap.put(2204441813l, "female");
        aMap.put(671679327l, "hunter");
        aMap.put(3655393761l, "titan");
        aMap.put(2271682572l, "warlock");
        aMap.put(3871980777l, "new monarchy");
        aMap.put(529303302l, "cryptarch");
        aMap.put(2161005788l, "iron banner");
        aMap.put(452808717l, "queen");
        aMap.put(3233510749l, "vanguard");
        aMap.put(1357277120l, "crucible");
        aMap.put(2778795080l, "dead orbit");
        aMap.put(1424722124l, "future war cult");
        hashes = Collections.unmodifiableMap(aMap);
    }

    public final static String getTerm(long hash) {
        return hashes.get(hash);
    }

    public String getTimePlayed() {
        return timePlayed;
    }

    public void setTimePlayed(String timePlayed) {
        this.timePlayed = timePlayed;
    }

    public String getGuardianClass() {
        return guardianClass;
    }

    public void setGuardianClass(String guardianClass) {
        this.guardianClass = guardianClass;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getEmblemPath() {
        return emblemPath;
    }

    public void setEmblemPath(String emblemPath) {
        this.emblemPath = emblemPath;
    }
}
