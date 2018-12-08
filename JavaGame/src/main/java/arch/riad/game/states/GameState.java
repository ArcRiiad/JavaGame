package arch.riad.game.states;

import arch.riad.game.utils.KeyHandler;
import arch.riad.game.utils.MouseHandler;

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
