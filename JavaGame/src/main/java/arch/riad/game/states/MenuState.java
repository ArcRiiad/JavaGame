package arch.riad.game.states;

import arch.riad.game.GamePanel;
import arch.riad.game.utils.KeyHandler;
import arch.riad.game.utils.MouseHandler;
import arch.riad.game.utils.Vector2f;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;


public class MenuState extends GameState {

    private BufferedImage   img;
    private String          file = "screen/menu.png";

    private BufferedImage   playimg;
    private String          playfile = "buttons/play.png";
    private Vector2f        playpos = new Vector2f(540, 360);

    private BufferedImage   exitimg;
    private String          exitFile = "buttons/exit.png";
    private Vector2f        exitpos = new Vector2f(540, 540);

    private BufferedImage   scoreimg;
    private String          scoreFile = "buttons/score.png";
    private Vector2f        scorepos = new Vector2f(540, 450);

    private Vector2f        buttonDim = new Vector2f(200, 75);

    public MenuState(GameStateManager gsm) {
        super(gsm);

        try{

            img = ImageIO.read((getClass().getClassLoader().getResourceAsStream(file)));
            playimg = ImageIO.read((getClass().getClassLoader().getResourceAsStream(playfile)));
            exitimg = ImageIO.read((getClass().getClassLoader().getResourceAsStream(exitFile)));
            scoreimg = ImageIO.read((getClass().getClassLoader().getResourceAsStream(scoreFile)));


        } catch (Exception e){
            System.out.println("something went wrong !");
        }
    }

    @Override
    public void update() {


    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        if (mouse.getMouseButton() == 1){
            if ((mouse.getMouseX() >= (playpos.x) && mouse.getMouseX() <= (playpos.x + buttonDim.x))
                    && (mouse.getMouseY() >= (playpos.y) && mouse.getMouseY() <= (playpos.y + buttonDim.y))) {
                gsm.addAndPop(gsm.PLAY);
            }
            else if ((mouse.getMouseX() >= (scorepos.x) && mouse.getMouseX() <= (scorepos.x + buttonDim.x))
                    && (mouse.getMouseY() >= (scorepos.y) && mouse.getMouseY() <= (scorepos.y + buttonDim.y))) {
                gsm.addAndPop(gsm.SCORE);
            }
            else if ((mouse.getMouseX() >= (exitpos.x) && mouse.getMouseX() <= (exitpos.x + buttonDim.x))
                    && (mouse.getMouseY() >= (exitpos.y) && mouse.getMouseY() <= (exitpos.y + buttonDim.y))) {
                System.exit(0);
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(this.img, 0, 0, GamePanel.width, GamePanel.height, null);
        g.drawImage(this.playimg, (int)playpos.x,(int)playpos.y,200, 75, null);
        g.drawImage(this.scoreimg, (int)scorepos.x,(int)scorepos.y,200, 75, null);
        g.drawImage(this.exitimg, (int)exitpos.x,(int)exitpos.y,200, 75, null);

    }
}
