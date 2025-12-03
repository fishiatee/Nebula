package emu.nebula.game.tower.room;

import emu.nebula.data.resources.StarTowerStageDef;
import emu.nebula.game.tower.StarTowerGame;
import emu.nebula.game.tower.StarTowerShopGoods;
import emu.nebula.game.tower.cases.StarTowerHawkerCase;
import emu.nebula.game.tower.cases.StarTowerSyncHPCase;
import lombok.Getter;

@Getter
public class StarTowerHawkerRoom extends StarTowerBaseRoom {
    
    public StarTowerHawkerRoom(StarTowerGame game, StarTowerStageDef stage) {
        super(game, stage);
    }

    @Override
    public void onEnter() {
        // Create hawker case
        var hawker = new StarTowerHawkerCase();
        
        // TODO
        for (int i = 0; i < 8; i++) {
            hawker.addGoods(new StarTowerShopGoods(1, 1, 200));
        }
        
        this.getGame().addCase(hawker);
        
        // Create door case
        this.getGame().createExit();
        
        // Create sync hp case
        this.getGame().addCase(new StarTowerSyncHPCase());
    }
}
