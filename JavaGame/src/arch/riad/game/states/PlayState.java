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

import java.awt.*;
import java.util.ArrayList;


public class PlayState extends GameState{

    private Player                  player;
    private ArrayList<Enemy>        enemies;
    private TileManager             tm;
    public static Vector2f          map;

    public PlayState(GameStateManager gsm) {
        super(gsm);

        map = new Vector2f();
        enemies = new ArrayList<Enemy>();
        Vector2f.setWorldVar(map.x, map.y);

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
    }
}
