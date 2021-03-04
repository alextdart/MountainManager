package persistence;

import model.SkiRun;
import model.SkiResort;
import model.Lift;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// class structure borrowed from JsonDemo
// Represents a reader that reads SkiResort from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads SkiResort from file and returns it;
    // throws IOException if an error occurs reading data from file
    public SkiResort read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseSkiResort(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses SkiResort from JSON object and returns it
    private SkiResort parseSkiResort(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        SkiResort sr = new SkiResort(name);
        addRuns(sr, jsonObject);
        addLifts(sr, jsonObject);
        return sr;
    }

    // MODIFIES: sr
    // EFFECTS: parses runs from JSON object and adds them to SkiResort
    private void addRuns(SkiResort sr, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("runs");
        for (Object json : jsonArray) {
            JSONObject nextRun = (JSONObject) json;
            addRun(sr, nextRun);
        }
    }

    // MODIFIES: sr
    // EFFECTS: parses lifts from JSON object and adds them to SkiResort
    private void addLifts(SkiResort sr, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("lifts");
        for (Object json : jsonArray) {
            JSONObject nextLift = (JSONObject) json;
            addLift(sr, nextLift);
        }
    }

    // MODIFIES: sr
    // EFFECTS: parses run from JSON object and adds it to SkiResort
    private void addRun(SkiResort sr, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String status = jsonObject.getString("status");
        int id = jsonObject.getInt("id");
        boolean open = jsonObject.getBoolean("open");
        SkiRun run = new SkiRun(name, status, id, open);
        sr.importRun(run);
    }

    // MODIFIES: sr
    // EFFECTS: parses lift from JSON object and adds it to SkiResort
    private void addLift(SkiResort sr, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int seats = jsonObject.getInt("seats");
        int lineup = jsonObject.getInt("lineup");
        int id = jsonObject.getInt("id");
        boolean open = jsonObject.getBoolean("open");
        Lift lift = new Lift(name, open, id, seats, lineup);
        sr.importLift(lift);
    }
}
