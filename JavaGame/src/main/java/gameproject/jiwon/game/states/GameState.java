package gameproject.jiwon.game.states;

import gameproject.jiwon.game.utils.KeyHandler;
import gameproject.jiwon.game.utils.MouseHandler;

import java.awt.Graphics2D;

public abstract class GameState {

    public GameStateManager gsm;

    public GameState(GameStateManager gsm) {
        this.gsm = gsm;
    }

    public abstract void update();
    public abstract void input(MouseHandler mouse, KeyHandler key);
    public abstract void render(Graphics2D g);
}
