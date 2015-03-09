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
        aMap.put(159615086l, "Glimmer");
        aMap.put(1415355184l, "Crucible Marks");
        aMap.put(1415355173l, "Vanguard Marks");
        aMap.put(898834093l, "Exo");
        aMap.put(3887404748l, "Human");
        aMap.put(2803282938l, "Awoken");
        aMap.put(3111576190l, "Male");
        aMap.put(2204441813l, "Female");
        aMap.put(671679327l, "Hunter");
        aMap.put(3655393761l, "Titan");
        aMap.put(2271682572l, "Warlock");
        aMap.put(3871980777l, "new monarchy");
        aMap.put(529303302l, "Cryptarch");
        aMap.put(2161005788l, "Iron Banner");
        aMap.put(452808717l, "Queen");
        aMap.put(3233510749l, "Vanguard");
        aMap.put(1357277120l, "Crucible");
        aMap.put(2778795080l, "Dead Orbit");
        aMap.put(1424722124l, "Future War Cult");
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
