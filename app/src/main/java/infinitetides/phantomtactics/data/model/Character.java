package infinitetides.phantomtactics.data.model;

import org.json.JSONObject;

import java.util.List;

public class Character {
    public String Name;
    public Stat Stats;
    public List<Action> Actions;
    public Location Location;
    public JSONObject Effect;
}