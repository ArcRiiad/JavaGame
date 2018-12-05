package arch.riad.game.states;

import arch.riad.game.GamePanel;
import arch.riad.game.utils.KeyHandler;
import arch.riad.game.utils.MouseHandler;
import arch.riad.game.utils.Vector2f;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class GameOverState extends GameState {

    private BufferedImage   img;
    private BufferedImage   playAgain;
    private String          file = "screen/gameover.jpg";
    private String          filePlayAgain = "buttons/playAgainButton.png";

    private Vector2f        playAgainPos = new Vector2f(490, 580);
    private Vector2f        playAgainDim = new Vector2f(300, 150);

    public GameOverState(GameStateManager gsm) {

        super(gsm);

        try {
            img = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(file)));
            playAgain = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filePlayAgain)));
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
        }
    }

    @Override
    public void render(Graphics2D g) {

        g.drawImage(this.img, 0, 0, GamePanel.width, GamePanel.height, null);
        g.drawImage(this.playAgain, (int)playAgainPos.x, (int)playAgainPos.y, 300, 150, null);


        /*g.setColor(Color.white);
        String str = "Game Over !";
        g.setFont(new java.awt.Font( "SansSerif", java.awt.Font.BOLD, 32 ));
        System.out.println(GamePanel.width / 2 - ((str.length() / 2) * 42));
        g.drawString(str, GamePanel.width / 2 - ((str.length() / 2) * 42), GamePanel.height / 2);*/

    }
}
