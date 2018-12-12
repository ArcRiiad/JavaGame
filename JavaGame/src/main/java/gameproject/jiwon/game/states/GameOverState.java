package gameproject.jiwon.game.states;

import gameproject.jiwon.game.GamePanel;
import gameproject.jiwon.game.utils.KeyHandler;
import gameproject.jiwon.game.utils.MouseHandler;
import gameproject.jiwon.game.utils.Vector2f;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class GameOverState extends GameState {

    private BufferedImage   img;
    private BufferedImage   playAgain;
    private BufferedImage   menu;

    private String          file = "screen/gameover.jpg";
    private String          filePlayAgain = "buttons/playAgainButton.png";
    private String          fileMenu = "buttons/menu.png";

    private Vector2f        playAgainPos = new Vector2f(450, 580);
    private Vector2f        playAgainDim = new Vector2f(200, 75);

    private Vector2f        menuPos = new Vector2f(700, 580);

    public GameOverState(GameStateManager gsm) {

        super(gsm);

        try {
            img = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(file)));
            playAgain = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filePlayAgain)));
            menu = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(fileMenu)));
        } catch (Exception e){
            System.out.println("ERROR: could not load file: " + file);
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        if (mouse.getMouseButton() == 1){
            if ((mouse.getMouseX() >= (playAgainPos.x) && mouse.getMouseX() <= (playAgainPos.x + playAgainDim.x))
                && (mouse.getMouseY() >= (playAgainPos.y) && mouse.getMouseY() <= (playAgainPos.y + playAgainDim.y))) {
                gsm.addAndPop(gsm.PLAY);
            }
            if ((mouse.getMouseX() >= (menuPos.x) && mouse.getMouseX() <= (menuPos.x + playAgainDim.x))
                    && (mouse.getMouseY() >= (menuPos.y) && mouse.getMouseY() <= (menuPos.y + playAgainDim.y))) {
                gsm.addAndPop(gsm.MENU);
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(this.img, 0, 0, GamePanel.width, GamePanel.height, null);
        g.drawImage(this.playAgain, (int)playAgainPos.x, (int)playAgainPos.y, (int)playAgainDim.x, (int)playAgainDim.y, null);
        g.drawImage(this.menu, (int)menuPos.x, (int)menuPos.y, (int)playAgainDim.x, (int)playAgainDim.y, null);
    }
}
