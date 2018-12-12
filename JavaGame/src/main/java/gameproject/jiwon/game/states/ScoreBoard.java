package gameproject.jiwon.game.states;


import gameproject.jiwon.game.utils.KeyHandler;
import gameproject.jiwon.game.utils.MouseHandler;
import gameproject.jiwon.game.GamePanel;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileReader;

public class ScoreBoard extends GameState {

    private BufferedImage   img;
    private String          file = "screen/LeaderBoard.png";
    private BufferedImage   backButtonImg;
    private String          backButtonfile = "buttons/back-button.png";
    private int             buttonX = 1000, buttonY = 580;
    JsonParser              parser = new JsonParser();
    JsonObject              jsonObject;
    JsonArray               ScoresList;

    public ScoreBoard(GameStateManager gsm) {
        super(gsm);

        try {
            Object obj = parser.parse(new FileReader("res/Score/scores.json"));
            jsonObject = (JsonObject) obj;

            ScoresList = (JsonArray) jsonObject.getAsJsonArray("Scores");
            img = ImageIO.read((getClass().getClassLoader().getResourceAsStream(file)));
            backButtonImg = ImageIO.read((getClass().getClassLoader().getResourceAsStream(backButtonfile)));
        } catch (Exception e) {
            System.out.println("Something went wrong !");
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        if (mouse.getMouseButton() == 1){
            if ((mouse.getMouseX() >= (buttonX) && mouse.getMouseX() <= (buttonX + 200))
                    && (mouse.getMouseY() >= (buttonY) && mouse.getMouseY() <= (buttonY + 76))) {
                gsm.addAndPop(gsm.MENU);
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(this.img, 0, 0, GamePanel.width, GamePanel.height, null);
        g.drawImage(this.backButtonImg, buttonX, buttonY, 200, 76, null);
        g.setColor(Color.white);
        int     Px = 400, Py = 410;

        for (int i = 0; i < ScoresList.size() && i < 5; i++){
            g.setFont(new Font( "SansSerif", Font.BOLD, 48 ));
            String  playerScore = ScoresList.get(i).getAsJsonObject().get("Score").getAsString();
            String  playerDate = ScoresList.get(i).getAsJsonObject().get("Date").getAsString();

            g.drawString(playerScore, Px + 160, Py);
            g.setFont(new Font( "SansSerif", Font.BOLD, 36 ));
            g.drawString(playerDate, Px + 360, Py);
            Py += 65;
        }
    }
}
