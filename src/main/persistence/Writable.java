package persistence;

import org.json.JSONObject;

// interface structure borrowed from JsonDemo
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
