package com.example.galatti.myguardians;

/**
 * Created by Rodrigo Galatti on 05/03/2015.
 */
public class Guardian {

    private String guardianClass;
    private String level;
    private String timePlayed;

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
}
