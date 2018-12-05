package arch.riad.game.entity;

import arch.riad.game.GamePanel;
import arch.riad.game.graphics.Animation;
import arch.riad.game.graphics.Sprite;
import arch.riad.game.states.PlayState;
import arch.riad.game.utils.*;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class Entity {

    private final int   UP = 3;
    private final int   DOWN = 2;
    private final int   RIGHT = 0;
    private final int   LEFT = 1;
    private final int   FALLEN = 4;
    protected int       currentAnimation;

    protected Animation ani;
    protected Sprite    sprite;
    protected Vector2f  pos;
    protected int       size;


    protected boolean   up;
    protected boolean   down;
    protected boolean   right;
    protected boolean   left;
    protected boolean   attack;
    protected boolean   fallen;
    protected int       attackSpeed;
    protected int       attackDuration;

    protected float     dx;
    protected float     dy;

    protected float     maxSpeed = 4f;
    protected float     acc = 3f;
    protected float     deacc= 0.4f;

    protected AABB      hitBounds;
    protected AABB      bounds;
    protected TileCollision tc;

    public Entity(Sprite sprite, Vector2f origin, int size){
        this.sprite = sprite;
        pos = origin;
        this.size = size;

        bounds = new AABB(origin, size, size);
        hitBounds = new AABB(origin, size, size);
        hitBounds.setXOffset(size / 2);

        ani = new Animation();
        setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), 10);

        tc = new TileCollision(this);
    }

    public void setSprite(Sprite sprite){ this.sprite = sprite; }
    public void setSize(int size) { this.size = size; }
    public void setMaxSpeed(float maxSpeed) { this.maxSpeed = maxSpeed; }
    public int getSize() { return size; }
    public Animation getAnimation() { return ani; }
    public void setAcc(float acc) { this.acc = acc; }
    public void setDeacc(float deacc) { this.deacc = deacc; }
    public AABB getBounds() { return bounds; }

    public void setFallen(boolean fallen) {
        this.fallen = fallen;
    }

    public void setAnimation(int i, BufferedImage[] frames, int delay){
        currentAnimation  = i;
        ani.setFrames(frames);
        ani.setDelay(delay);
    }

    public void animate(){
        if (up){
            if(currentAnimation != UP || ani.getDelay() == -1){
                setAnimation(UP, sprite.getSpriteArray(UP), 5);
            }
        }
        else if (down){
            if(currentAnimation != DOWN || ani.getDelay() == -1){
                setAnimation(DOWN, sprite.getSpriteArray(DOWN), 5);
            }
        }
        else if (left){
            if(currentAnimation != LEFT || ani.getDelay() == -1){
                setAnimation(LEFT, sprite.getSpriteArray(LEFT), 5);
            }
        }
        else if (right){
            if(currentAnimation != RIGHT || ani.getDelay() == -1){
                setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), 5);
            }
        }else if (fallen) {
            if (currentAnimation != FALLEN || ani.getDelay() == -1) {
                setAnimation(FALLEN, sprite.getSpriteArray(FALLEN), 15);
            }
        } else {
            setAnimation(currentAnimation, sprite.getSpriteArray(currentAnimation), 1);
        }
    }

    private void setHitBoxDirection(){
        if (up){
            hitBounds.setXOffset(0);
            hitBounds.setYOffset(-size / 2);
        }
        else if (down){
            hitBounds.setXOffset(0);
            hitBounds.setYOffset(size / 2);

        }
        else if (left){
            hitBounds.setXOffset(-size / 2);
            hitBounds.setYOffset(0);
        }
        else if (right){
            hitBounds.setXOffset(size / 2);
            hitBounds.setYOffset(0);
        }
    }

    public void resetPosition(){
        System.out.println("Reseting the player ...");
        pos.x = GamePanel.width / 2 - 32;
        PlayState.map.x = 0;

        pos.y = GamePanel.height / 2 - 32;
        PlayState.map.y = 0;

        setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), 10);
    }

    public void update(){
        animate();
        setHitBoxDirection();
        ani.update();
    }

    public abstract void render(Graphics2D g);

}
