package arch.riad.game.tiles.blocks;

import arch.riad.game.utils.AABB;
import arch.riad.game.utils.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public class NormBlock extends Block {

    public NormBlock(BufferedImage img, Vector2f pos, int w, int h) {
        super(img, pos, w, h);
    }

    @Override
    public boolean update(AABB p) {
        return false;
    }

    @Override
    public boolean isInside(AABB p) {
        return false;
    }

    public void render(Graphics2D g){
        super.render(g);
		/* g.setColor(Color.green);
		g.drawString(pos.x / 64 + ((pos.y / 64) * 50)+ ": " + pos.x / w + "," + pos.y / h, pos.x, pos.y); */
    }
}
