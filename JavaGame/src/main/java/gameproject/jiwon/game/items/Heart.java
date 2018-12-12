package gameproject.jiwon.game.items;

import gameproject.jiwon.game.utils.AABB;

import java.awt.*;

public class Heart extends items {


    public Heart(String file, int x, int y, int size) {
        super(file, x, y, size);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(this.getImg(), (int)getOrigin().getWorldVar().x,(int)getOrigin().getWorldVar().y, this.getSize(), this.getSize(), null);
    }

    @Override
    public AABB getHitBounds() {
        return super.getHitBounds();
    }

    @Override
    public AABB getBounds() {
        return super.getBounds();
    }

}
