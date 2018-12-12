package gameproject.jiwon.game.tiles.blocks;

import gameproject.jiwon.game.utils.Vector2f;
import gameproject.jiwon.game.utils.AABB;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ObjBlock extends Block{

    public ObjBlock(BufferedImage img, Vector2f pos, int w, int h) {
        super(img, pos, w, h);
    }

    public void render(Graphics2D g){
        super.render(g);
        /*g.setColor(Color.green);
		g.drawString(pos.x / 64 + ((pos.y / 64) * 50)+ ": " + pos.x / w + "," + pos.y / h, pos.x, pos.y);*/
    }

    @Override
    public boolean update(AABB p) {
        return false;
    }

    @Override
    public boolean isInside(AABB p) {
        return false;
    }
}
