package gameproject.jiwon.game.tiles.blocks;

import gameproject.jiwon.game.utils.Vector2f;
import gameproject.jiwon.game.utils.AABB;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class Block {

    protected int w;
    protected int h;

    protected BufferedImage img;
    protected Vector2f pos;

    public Block(BufferedImage img, Vector2f pos, int w, int h){
        this.img = img;
        this.pos = pos;
        this.w = w;
        this.h = h;
    }

    public abstract boolean update(AABB p);
    public abstract boolean isInside(AABB p);
    public Vector2f getPos() { return pos; }
    public int getWidth() { return w; }
    public int getHeight() { return h; }

    public void render(Graphics2D g) {
        g.drawImage(img, (int) pos.getWorldVar().x, (int) pos.getWorldVar().y, w, h, null);
    }
}
