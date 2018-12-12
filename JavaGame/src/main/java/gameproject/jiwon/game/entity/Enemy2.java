package gameproject.jiwon.game.entity;

import gameproject.jiwon.game.graphics.Sprite;
import gameproject.jiwon.game.utils.AABB;
import gameproject.jiwon.game.utils.Vector2f;

import java.awt.*;

public class Enemy2 extends Entity {

    private AABB sense;
    private int     r;
    private int     damage;
    private int     score;

    public Enemy2(Sprite sprite, Vector2f origin, int size) {
        super(sprite, origin, size);

        acc = 2f;
        maxSpeed = 4f;
        r = 350;
        bounds.setWidth(42);
        bounds.setHeight(20);
        bounds.setXOffset(12);
        bounds.setYOffset(40);
        damage = 4;
        score = 20;
        sense = new AABB(new Vector2f(origin.x + size / 2 - r / 2, origin.y + size / 2 - r / 2), r);
    }

    public int getScore() { return score; }

    public int getDamage() { return damage; }

    private void move(Player player) {
        if (sense.colCircleBox(player.getBounds())) {
            if (pos.y > player.pos.y + 1) {
                dy -= acc;
                up = true;
                down = false;
                if (dy < -maxSpeed) {
                    dy = -maxSpeed;
                }
            } else if (pos.y < player.pos.y - 1) {
                dy += acc;
                down = true;
                up = false;
                if (dy > maxSpeed) {
                    dy = maxSpeed;
                }
            } else {
                dy = 0;
                up = false;
                down = false;
            }

            if (pos.x > player.pos.x + 1) {
                dx -= acc;
                left = true;
                right = false;
                if (dx < -maxSpeed) {
                    dx = -maxSpeed;
                }
            } else if (pos.x < player.pos.x - 1) {
                dx += acc;
                right = true;
                left = false;
                if (dx > maxSpeed) {
                    dx = maxSpeed;
                }
            } else {
                dx = 0;
                right = false;
                left = false;
            }
        }else {
            up = false;
            down = false;
            left = false;
            right = false;
            dx = 0;
            dy = 0;
        }
    }

    public void destroy(){ }

    public void update(Player player){
        super.update();
        move(player);
        if (!fallen) {
            if (!tc.collisionTile(dx, 0)) {
                sense.getPos().x += dx;
                pos.x += dx;
            }
            if (!tc.collisionTile(0, dx)) {
                sense.getPos().y += dy;
                pos.y += dy;
            }
        }else {
            destroy();
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.green);
        g.drawRect((int)(pos.getWorldVar().x + bounds.getXOffset()), (int)(pos.getWorldVar().y + bounds.getYOffset()), (int) bounds.getWidth(), (int) bounds.getHeight());

        g.setColor(Color.BLUE);
        g.drawOval((int)(sense.getPos().getWorldVar().x), (int)(sense.getPos().getWorldVar().y), r,r);

        g.drawImage(ani.getImage(), (int)(pos.getWorldVar().x), (int)(pos.getWorldVar().y), size, size, null);
    }
}
