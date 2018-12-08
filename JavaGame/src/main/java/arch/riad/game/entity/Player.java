package arch.riad.game.entity;

import arch.riad.game.graphics.Sprite;
import arch.riad.game.states.PlayState;
import arch.riad.game.utils.KeyHandler;
import arch.riad.game.utils.MouseHandler;
import arch.riad.game.utils.Vector2f;

import java.awt.*;
import java.util.ArrayList;

public class Player  extends Entity{

    private int healthPoint = 100;
    private int heartNumber = 3;
    private int score = 0;

    public Player(Sprite sprite, Vector2f origin, int size) {

        super(sprite, origin, size);

        bounds.setWidth(42);
        bounds.setHeight(20);
        bounds.setXOffset(12);
        bounds.setYOffset(40);
    }

    public void takingDamage(int damage){
        healthPoint -= damage;
    }

    public int getHeartNumber() { return heartNumber; }

    public int getScore() { return score; }

    public void addScore(int score) { this.score += score; }

    private void move() {
        if(up) {
            dy -= acc;
            if(dy < -maxSpeed) {
                dy = -maxSpeed;
            }
        } else {
            if(dy < 0) {
                dy += deacc;
                if(dy > 0) {
                    dy = 0;
                }
            }
        }
        if(down) {
            dy += acc;
            if(dy > maxSpeed) {
                dy = maxSpeed;
            }
        } else {
            if(dy > 0) {
                dy -= deacc;
                if(dy < 0) {
                    dy = 0;
                }
            }
        }
        if(left) {
            dx -= acc;
            if(dx < -maxSpeed) {
                dx = -maxSpeed;
            }
        } else {
            if(dx < 0) {
                dx += deacc;
                if(dx > 0) {
                    dx = 0;
                }
            }
        }
        if(right) {
            dx += acc;
            if(dx > maxSpeed) {
                dx = maxSpeed;
            }
        } else {
            if(dx > 0) {
                dx -= deacc;
                if(dx < 0) {
                    dx = 0;
                }
            }
        }
    }

    public void update(ArrayList<Enemy> enemies){
        super.update();

        for (int i = 0; i < enemies.size(); i++) {
            if(!fallen && bounds.collides(enemies.get(i).getBounds())){
                takingDamage(enemies.get(i).getDamage());
                if (healthPoint <= 0){
                    heartNumber -= 1;
                    fallen = true;
                }
            }

            if (!fallen && attack && hitBounds.collides(enemies.get(i).getBounds())) {
                addScore(enemies.get(i).getScore());
                enemies.get(i).destroy();
                enemies.remove(i);
            }
        }

        if (!fallen){
            move();
            if (!tc.collisionTile(dx, 0)){
                PlayState.map.x += dx;
                pos.x += dx;
            }
            if (!tc.collisionTile(0, dy)) {
                PlayState.map.y += dy;
                pos.y += dy;
            }
        }else {
            if (ani.hasPlayedOnce()){
                resetPosition();
                healthPoint = 100;
                fallen = false;
            }
        }

    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.GREEN);
        g.drawRect((int)(pos.getWorldVar().x + bounds.getXOffset()), (int)(pos.getWorldVar().y + bounds.getYOffset()), (int)bounds.getWidth(),(int)bounds.getHeight());

        if (attack){
            g.setColor(Color.RED);
            g.drawRect((int)(hitBounds.getPos().getWorldVar().x + hitBounds.getXOffset()),(int)(hitBounds.getPos().getWorldVar().y + hitBounds.getYOffset()),(int) hitBounds.getWidth(),(int) hitBounds.getHeight());
        }

        g.drawImage(ani.getImage(), (int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), size, size, null);

        g.setColor(Color.white);
        g.setFont(new Font( "SansSerif", Font.BOLD, 12 ));
        g.drawString("Health Point : " + healthPoint, 20, 20);
        g.drawString("Life number: " + heartNumber, 20, 40);
        g.drawString("Score : " + score, 20, 80);
    }

    public void input(MouseHandler mouse, KeyHandler key){
        if (!fallen) {
            if (key.up.down) {
                up = true;
            } else {
                up = false;
            }
            if (key.down.down) {
                down = true;
            } else {
                down = false;
            }
            if (key.left.down) {
                left = true;
            } else {
                left = false;
            }
            if (key.right.down) {
                right = true;
            } else {
                right = false;
            }
            if (key.attack.down) {
                attack = true;
            } else {
                attack = false;
            }
        }else {
            up = false;
            down = false;
            right = false;
            left = false;
        }
    }
}
