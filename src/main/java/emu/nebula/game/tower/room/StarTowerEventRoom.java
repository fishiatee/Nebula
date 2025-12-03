package emu.nebula.game.tower.room;

import emu.nebula.data.resources.StarTowerStageDef;
import emu.nebula.game.tower.StarTowerGame;
import emu.nebula.game.tower.cases.StarTowerSyncHPCase;

import lombok.Getter;

@Getter
public class StarTowerEventRoom extends StarTowerBaseRoom {
    
    public StarTowerEventRoom(StarTowerGame game, StarTowerStageDef stage) {
        super(game, stage);
    }

    @Override
    public void onEnter() {
        // Create door case
        this.getGame().createExit();
        
        // Create sync hp case
        this.getGame().addCase(new StarTowerSyncHPCase());
    }
}
