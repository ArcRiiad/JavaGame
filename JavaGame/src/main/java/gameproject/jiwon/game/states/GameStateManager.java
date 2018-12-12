package gameproject.jiwon.game.states;

import gameproject.jiwon.game.utils.KeyHandler;
import gameproject.jiwon.game.utils.MouseHandler;
import gameproject.jiwon.game.utils.Vector2f;
import gameproject.jiwon.game.GamePanel;
import gameproject.jiwon.game.entity.Player;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class GameStateManager {

    private ArrayList<GameState> states;

    public static final int  PLAY       = 0;
    public static final int  MENU       = 1;
    public static final int  PAUSE      = 2;
    public static final int  GAMEOVER   = 3;
    public static final int  SCORE      = 4;
    public static final int  PLAY2      = 5;


    public static Vector2f map;

    public GameStateManager() {
        map = new Vector2f(GamePanel.width, GamePanel.height);
        Vector2f.setWorldVar(map.x, map.y);
        states = new ArrayList<GameState>();

        //states.add(new PlayState(this));
        states.add(new MenuState(this));
    }

    public void pop(int state) {
        states.remove(state);
    }

    public void add(int state){
        if (state == PLAY){
            states.add(new PlayState(this));
        }
        if (state == MENU){
            states.add(new MenuState(this));
        }
        if (state == PAUSE){
            states.add(new PauseState(this));
        }
        if (state == GAMEOVER){
            states.add(new GameOverState(this));
        }
        if (state == SCORE){
            states.add(new ScoreBoard(this));
        }
        if (state == PLAY2){
            states.add(new PlayState2(this));
        }
    }

    public void addAndPop(int state){
        states.remove(0);
        add(state);
    }

    public void update() {
        Vector2f.setWorldVar(map.x, map.y);
        for (int i = 0; i < states.size(); i++){
            states.get(i).update();
        }
    }

    public void input(MouseHandler mouse, KeyHandler key){
        for (int i = 0; i < states.size(); i++){
            states.get(i).input(mouse, key);
        }
    }

    public void render(Graphics2D g){
        for (int i = 0; i < states.size(); i++){
            states.get(i).render(g);
        }
    }

    public void nextStage(int stage, Player player){
        states.remove(0);
        add(stage);
        ((PlayState2)(states.get(0))).player.setHeartNumber(player.getHeartNumber());
        ((PlayState2)(states.get(0))).player.setHealthPoint(player.getHeartNumber());
        ((PlayState2)(states.get(0))).player.addScore(player.getScore());
    }
}
