package gameproject.jiwon.game.items;

import gameproject.jiwon.game.utils.AABB;
import gameproject.jiwon.game.utils.Vector2f;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class items {

    private BufferedImage   img;
    private Vector2f        origin;
    private int             size;
    private AABB            hitBounds;
    private AABB            bounds;

    public items(String file, int x, int y, int size) {
        try {
            System.out.println("Item : " + file);
            img = ImageIO.read((getClass().getClassLoader().getResourceAsStream(file)));
            this.size = size;
            origin = new Vector2f(x, y);
            hitBounds = new AABB(origin, size, size);
            bounds = new AABB(origin, size, size);

        }catch (Exception e){
            System.out.println("Can't load items !");
        }
    }

    public void update() {}
    public void render(Graphics2D g) {}

    public AABB getHitBounds() {
        return hitBounds;
    }

    public AABB getBounds() {
        return bounds;
    }

    public BufferedImage getImg() {
        return img;
    }

    public Vector2f getOrigin() {
        return origin;
    }

    public int getSize() {
        return size;
    }
}
