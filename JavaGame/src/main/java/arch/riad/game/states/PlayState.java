package arch.riad.game.states;

import arch.riad.game.GamePanel;
import arch.riad.game.entity.Enemy;
import arch.riad.game.entity.Player;
import arch.riad.game.graphics.Font;
import arch.riad.game.graphics.Sprite;
import arch.riad.game.tiles.TileManager;
import arch.riad.game.utils.KeyHandler;
import arch.riad.game.utils.MouseHandler;
import arch.riad.game.utils.Vector2f;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;


public class PlayState extends GameState{

    private Player                  player;
    private ArrayList<Enemy>        enemies;
    private TileManager             tm;
    public static Vector2f          map;

    private BufferedImage           playimg;
    private String                  playfile = "buttons/play.png";
    private Vector2f                playpos = new Vector2f(540, 330);

    private BufferedImage           backimg;
    private String                  backFile = "buttons/menu.png";
    private Vector2f                backpos = new Vector2f(540, 420);

    private boolean                 toggleMenu = false;

    private Vector2f                buttonDim = new Vector2f(200, 75);


    public PlayState(GameStateManager gsm)  {
        super(gsm);

        map = new Vector2f();
        enemies = new ArrayList<Enemy>();
        Vector2f.setWorldVar(map.x, map.y);


        try {
            playimg = ImageIO.read((getClass().getClassLoader().getResourceAsStream(playfile)));
            backimg = ImageIO.read((getClass().getClassLoader().getResourceAsStream(backFile)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        tm = new TileManager("tile/tilemap.xml");

        enemies.add(new Enemy(new Sprite("entity/littlegirl.png", 48, 48) , new Vector2f(0 + (GamePanel.width / 2) - 32 + 150, 0 + (GamePanel.height / 2) - 32 + 150), 64));
        //enemies.add(new Enemy(new Sprite("entity/littlegirl.png", 48, 48) , new Vector2f(0 + (GamePanel.width / 2) - 32 + 170, 0 + (GamePanel.height / 2) - 32 + 150), 64));
        //enemies.add(new Enemy(new Sprite("entity/littlegirl.png", 48, 48) , new Vector2f(0 + (GamePanel.width / 2) - 32 + 190, 0 + (GamePanel.height / 2) - 32 + 150), 64));
        //enemies.add(new Enemy(new Sprite("entity/littlegirl.png", 48, 48) , new Vector2f(0 + (GamePanel.width / 2) - 32 + 210, 0 + (GamePanel.height / 2) - 32 + 150), 64));
        player  = new Player(new Sprite("entity/linkformatted.png"), new Vector2f(0 + (GamePanel.width / 2) - 32, 0 + (GamePanel.height / 2) - 32 ), 64);

    }

    @Override
    public void update() {
        if (player.getHeartNumber() <= 0){
            this.gsm.addAndPop(gsm.GAMEOVER);
            return;
        }

        Vector2f.setWorldVar(map.x, map.y);
        player.update(enemies);
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).update(player);
        }
    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        char _key = key.getKeyTyped();
        if (_key == 'a'){
            toggleMenu  = !toggleMenu;
        }

        if (toggleMenu) {
            if ((mouse.getMouseX() >= (playpos.x) && mouse.getMouseX() <= (playpos.x + buttonDim.x))
                    && (mouse.getMouseY() >= (playpos.y) && mouse.getMouseY() <= (playpos.y + buttonDim.y))) {
                toggleMenu = false;
            } else if ((mouse.getMouseX() >= (backpos.x) && mouse.getMouseX() <= (backpos.x + buttonDim.x))
                    && (mouse.getMouseY() >= (backpos.y) && mouse.getMouseY() <= (backpos.y + buttonDim.y))) {
                gsm.addAndPop(gsm.MENU);
            }
        }

        player.input(mouse, key);
    }

    @Override
    public void render(Graphics2D g) {
        tm.render(g);
        player.render(g);
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).render(g);
        }

        g.setColor(Color.white);
        g.setFont(new java.awt.Font( "SansSerif", java.awt.Font.BOLD, 12 ));
        g.drawString("Enemies Remaining : " + enemies.size(), 20, 60);

        if (toggleMenu){
            g.drawImage(this.playimg, (int)playpos.x,(int)playpos.y,200, 75, null);
            g.drawImage(this.backimg, (int)backpos.x,(int)backpos.y,200, 75, null);
        }
    }
}
