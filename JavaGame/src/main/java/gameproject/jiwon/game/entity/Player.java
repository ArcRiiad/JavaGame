package gameproject.jiwon.game.entity;

import gameproject.jiwon.game.graphics.Sprite;
import gameproject.jiwon.game.items.Heart;
import gameproject.jiwon.game.utils.KeyHandler;
import gameproject.jiwon.game.utils.MouseHandler;
import gameproject.jiwon.game.utils.Music;
import gameproject.jiwon.game.utils.Vector2f;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Player  extends Entity{

    private int healthPoint = 100;
    private int heartNumber = 3;
    private int score = 0;
    private BufferedImage   heartImg;
    public  boolean reset = true;

    private Music hitMusic;
    private Music itemMusic;


    public Player(Sprite sprite, Vector2f origin, int size) {

        super(sprite, origin, size);

        bounds.setWidth(42);
        bounds.setHeight(20);
        bounds.setXOffset(12);
        bounds.setYOffset(40);

        hitMusic = new Music("res/music/hit.wav", true);
        itemMusic = new Music("res/music/Coin01.wav", true);

        try {
            heartImg = ImageIO.read((getClass().getClassLoader().getResourceAsStream("items/heart.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    public void setHealthPoint(int healthPoint) {
        this.healthPoint = healthPoint;
    }

    public void setHeartNumber(int heartNumber) {
        this.heartNumber = heartNumber;
    }

    public void update(ArrayList<Enemy> enemies, ArrayList<Heart> hearts, ArrayList<Enemy2> enemies2, Vector2f  map){
        super.update();

        for (int i =0; i < hearts.size(); i++){
            if (!fallen && bounds.collides(hearts.get(i).getBounds())){
                itemMusic.playOnce();
                this.heartNumber += 1;
                hearts.remove(i);
            }
        }

        for (int i = 0; i < enemies.size(); i++) {
            if(!fallen && bounds.collides(enemies.get(i).getBounds())){
                takingDamage(enemies.get(i).getDamage());
                if (healthPoint <= 0){
                    heartNumber -= 1;
                    fallen = true;
                }
            }

            if (!fallen && attack && hitBounds.collides(enemies.get(i).getBounds())) {
                hitMusic.playOnce();
                addScore(enemies.get(i).getScore());
                enemies.get(i).destroy();
                enemies.remove(i);
            }
        }

        for (int i = 0; i < enemies2.size(); i++) {
            if(!fallen && bounds.collides(enemies2.get(i).getBounds())){
                takingDamage(enemies2.get(i).getDamage());
                if (healthPoint <= 0){
                    heartNumber -= 1;
                    fallen = true;
                }
            }

            if (!fallen && attack && hitBounds.collides(enemies2.get(i).getBounds())) {
                hitMusic.playOnce();
                addScore(enemies2.get(i).getScore());
                enemies2.get(i).destroy();
                enemies2.remove(i);
            }
        }

        if (!fallen){
            move();
            if (!tc.collisionTile(dx, 0)){
                map.x += dx;
                pos.x += dx;
            }
            if (!tc.collisionTile(0, dy)) {
                map.y += dy;
                pos.y += dy;
            }
        }else {
            if (ani.hasPlayedOnce()){
                if (reset) {
                    resetPosition(map);
                }
                healthPoint = 100;
                fallen = false;
                reset = true;
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
        int x = 20;

        for (int i = 0; i < this.heartNumber; i++){
            g.drawImage(this.heartImg, x, 100, 20, 20, null);
            x += 30;
        }

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
