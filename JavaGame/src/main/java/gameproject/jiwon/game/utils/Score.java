package gameproject.jiwon.game.utils;

import com.google.gson.*;
import com.google.gson.JsonParser;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class Score {

    public  HashMap hm = new HashMap();

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");

    class PlayerScore implements Comparable {
        public String Score;
        public String Date;
        public String playerName;

        public PlayerScore(String score, String date, String name) {
            this.Score = score;
            this.Date = date;
            this.playerName = name;
        }

        public int compareTo(Object o) {
            int target = Integer.parseInt(((PlayerScore)o).Score);
            int src = Integer.parseInt(this.Score);
            return target - src;
        }

    }

    public JsonParser              parser = new JsonParser();
    public JsonObject              jsonObject;
    public JsonArray               ScoresList;
    public List<PlayerScore>       playerScoresList  = new ArrayList<PlayerScore>();


    public Score() {
        try {
            Object obj = parser.parse(new FileReader("res/Score/scores.json"));
            jsonObject = (JsonObject) obj;

            ScoresList = (JsonArray) jsonObject.getAsJsonArray("Scores");

            for (int i = 0; i < ScoresList.size(); i++){
                String  player = ScoresList.get(i).getAsJsonObject().get("playerName").getAsString();
                String  playerScore = ScoresList.get(i).getAsJsonObject().get("Score").getAsString();
                String  playerDate = ScoresList.get(i).getAsJsonObject().get("Date").getAsString();

                playerScoresList.add(new PlayerScore(playerScore, playerDate, player));
            }
            Collections.sort(playerScoresList);
        } catch (Exception e) {
            System.out.println("Something went wrong !");
        }
    }

    public void addScore(String score){
        Date date = new Date();
        PlayerScore tmp = new PlayerScore(score, dateFormat.format(date), "Toto");

        playerScoresList.add(tmp);
        Collections.sort(playerScoresList);
        hm.put("Scores", playerScoresList);

        FileWriter fstream = null;
        try {
            File file = new File("res/Score/scores.json");

            fstream = new FileWriter(file,false);
            BufferedWriter out = new BufferedWriter(fstream);
            out.flush();
            out.write(new Gson().toJson(hm));
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
