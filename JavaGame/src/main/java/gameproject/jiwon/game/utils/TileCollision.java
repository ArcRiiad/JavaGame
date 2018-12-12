package gameproject.jiwon.game.utils;

import gameproject.jiwon.game.entity.Entity;
import gameproject.jiwon.game.tiles.TileMapObj;
import gameproject.jiwon.game.tiles.blocks.Block;

public class TileCollision {

    private Entity e;
    private Block block;

    public TileCollision(Entity e) {
        this.e = e;
    }

    public boolean collisionTile(float ax, float ay){
        for(int c = 0; c < 4; c++){
            int xt = (int) ((e.getBounds().getPos().x + ax) + (c % 2) * e.getBounds().getWidth() + e.getBounds().getXOffset()) / 64;
            int yt = (int) ((e.getBounds().getPos().y + ay) + ((int)(c / 2)) * e.getBounds().getHeight() + e.getBounds().getYOffset()) / 64;

            if (TileMapObj.tmo_blocks.containsKey(String.valueOf(xt) + "," + String.valueOf(yt))){
                TileMapObj.tmo_blocks.get(String.valueOf(xt) + "," + String.valueOf(yt)).update(e.getBounds());
                return true;
            }
        }
        return false;
    }
}