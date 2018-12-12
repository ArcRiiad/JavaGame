package gameproject.jiwon.game.states;

import gameproject.jiwon.game.GamePanel;
import gameproject.jiwon.game.entity.Enemy;
import gameproject.jiwon.game.entity.Enemy2;
import gameproject.jiwon.game.entity.Player;
import gameproject.jiwon.game.graphics.Sprite;
import gameproject.jiwon.game.items.Heart;
import gameproject.jiwon.game.tiles.TileManager;
import gameproject.jiwon.game.utils.*;
import gameproject.jiwon.game.utils.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class PlayState2  extends GameState{
    public Player player;
    private ArrayList<Enemy> enemies;
    private ArrayList<Enemy2>       enemies2;
    private ArrayList<Heart>        hearts;
    private TileManager tm;
    public static Vector2f map;

    private BufferedImage playimg;
    private String                  playfile = "buttons/play.png";
    private Vector2f                playpos = new Vector2f(540, 330);

    private BufferedImage           backimg;
    private String                  backFile = "buttons/menu.png";
    private Vector2f                backpos = new Vector2f(540, 420);

    private BufferedImage           giveupImg;
    private String                  giveupFile = "buttons/giveup.png";
    private Vector2f                giveuppos = new Vector2f(540, 330);

    private BufferedImage           continueImg;
    private String                  continueFile = "buttons/continue.png";
    private Vector2f                continuepos = new Vector2f(540, 420);

    private boolean                 toggleMenu = false;

    private Vector2f                buttonDim = new Vector2f(200, 75);

    private Score _score = new Score();

    private boolean                 giveup = false;
    private boolean                 secondChance = false;

    private Music gameMusic;


    public PlayState2(GameStateManager gsm)  {
        super(gsm);

        new Score();
        map = new Vector2f();
        enemies = new ArrayList<Enemy>();
        enemies2 = new ArrayList<Enemy2>();
        hearts = new ArrayList<Heart>();
        gameMusic = new Music("res/music/game_music.wav", true);

        Vector2f.setWorldVar(map.x, map.y);
        gameMusic.run();

        try {
            playimg = ImageIO.read((getClass().getClassLoader().getResourceAsStream(playfile)));
            backimg = ImageIO.read((getClass().getClassLoader().getResourceAsStream(backFile)));
            continueImg = ImageIO.read((getClass().getClassLoader().getResourceAsStream(continueFile)));
            giveupImg = ImageIO.read((getClass().getClassLoader().getResourceAsStream(giveupFile)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        tm = new TileManager("tile/tilemap2.xml");

        hearts.add(new Heart("items/heart.png", 0 + (GamePanel.width / 2) - 32, 0 + (GamePanel.height / 2) - 32 + 150, 32));

        enemies.add(new Enemy(new Sprite("entity/littlegirl.png", 48, 48) , new Vector2f(0 + (GamePanel.width / 2) - 32 + 470, 0 + (GamePanel.height / 2) - 32 + 150), 64));
        enemies2.add(new Enemy2(new Sprite("entity/yoshi.png", 38, 40), new Vector2f(0 + (GamePanel.width / 2) - 32 + 210, 0 + (GamePanel.height / 2) - 32 + 100), 64));


        enemies.add(new Enemy(new Sprite("entity/littlegirl.png", 48, 48) , new Vector2f(0 + (GamePanel.width / 2) - 32 + 410, 0 + (GamePanel.height / 2) - 32 + 850), 64));
        enemies2.add(new Enemy2(new Sprite("entity/yoshi.png", 38, 40) , new Vector2f(0 + (GamePanel.width / 2) - 32 + 510, 0 + (GamePanel.height / 2) - 32 + 850), 64));


        enemies.add(new Enemy(new Sprite("entity/littlegirl.png", 48, 48) , new Vector2f(0 + (GamePanel.width / 2) - 32 + 210, 0 + (GamePanel.height / 2) - 32 + 850), 64));
        enemies2.add(new Enemy2(new Sprite("entity/yoshi.png", 38, 40) , new Vector2f(0 + (GamePanel.width / 2) - 32 + 210, 0 + (GamePanel.height / 2) - 32 + 850), 64));

        player  = new Player(new Sprite("entity/linkformatted.png"), new Vector2f(0 + (GamePanel.width / 2) - 32, 0 + (GamePanel.height / 2) - 32 ), 64);

    }

    @Override
    public void update() {
        if ((enemies.size() + enemies2.size()) <= 0){
            gameMusic.close();
            _score.addScore(Integer.toString(player.getScore()));
            gsm.addAndPop(gsm.MENU);
            return;
        }

        if (player.getHeartNumber() <= 0 && !giveup){
            secondChance = true;
        }

        Vector2f.setWorldVar(map.x, map.y);
        if (!secondChance) {
            player.update(enemies, hearts, enemies2, map);
        }
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).update(player);
        }

        for (int i = 0; i < enemies2.size(); i++) {
            enemies2.get(i).update(player);
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
                    && (mouse.getMouseY() >= (playpos.y) && mouse.getMouseY() <= (playpos.y + buttonDim.y))
                    && mouse.getMouseButton() == 1) {
                toggleMenu = false;
            } else if ((mouse.getMouseX() >= (backpos.x) && mouse.getMouseX() <= (backpos.x + buttonDim.x))
                    && (mouse.getMouseY() >= (backpos.y) && mouse.getMouseY() <= (backpos.y + buttonDim.y))
                    && mouse.getMouseButton() == 1) {
                gameMusic.close();
                gsm.addAndPop(gsm.MENU);
            }
        }

        if (secondChance) {
            if ((mouse.getMouseX() >= (continuepos.x) && mouse.getMouseX() <= (continuepos.x + buttonDim.x))
                    && (mouse.getMouseY() >= (continuepos.y) && mouse.getMouseY() <= (continuepos.y + buttonDim.y))
                    && mouse.getMouseButton() == 1) {
                secondChance = false;
                player.reset = false;
                player.setHealthPoint(100);
                player.setHeartNumber(1);
            } else if ((mouse.getMouseX() >= (giveuppos.x) && mouse.getMouseX() <= (giveuppos.x + buttonDim.x))
                    && (mouse.getMouseY() >= (giveuppos.y) && mouse.getMouseY() <= (giveuppos.y + buttonDim.y))
                    && mouse.getMouseButton() == 1) {
                gameMusic.close();
                gsm.addAndPop(gsm.GAMEOVER);
            }
        }

        if (!secondChance) {
            player.input(mouse, key);
        }
    }

    @Override
    public void render(Graphics2D g) {
        tm.render(g);
        player.render(g);

        for (int i = 0; i < hearts.size(); i++){
            hearts.get(i).render(g);
        }

        for (int i = 0; i < enemies2.size(); i++) {
            enemies2.get(i).render(g);
        }

        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).render(g);
        }

        g.setColor(Color.white);
        g.setFont(new java.awt.Font( "SansSerif", java.awt.Font.BOLD, 12 ));
        g.drawString("Enemies Remaining : " + (enemies.size() + enemies2.size()), 20, 60);

        if (toggleMenu){
            g.drawImage(this.playimg, (int)playpos.x,(int)playpos.y,200, 75, null);
            g.drawImage(this.backimg, (int)backpos.x,(int)backpos.y,200, 75, null);
        }

        if (secondChance){
            g.drawImage(this.continueImg, (int)continuepos.x, (int)continuepos.y, 200, 75, null);
            g.drawImage(this.giveupImg, (int)giveuppos.x, (int)giveuppos.y, 200, 75, null);
        }
    }
}
