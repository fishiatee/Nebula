package emu.nebula.game.tower;

import lombok.Getter;

/**
 * Data class to hold various modifiers for star tower.
 */
@Getter
public class StarTowerModifiers {
    private StarTowerGame game;
    
    // Strengthen machines
    private boolean enableEndStrengthen;
    private boolean enableShopStrengthen;
    
    private boolean freeStrengthen;
    private int strengthenDiscount;
    
    public StarTowerModifiers(StarTowerGame game) {
        this.game = game;
        
        // Strengthen machines
        this.enableEndStrengthen = this.hasGrowthNode(10601) && game.getDifficulty() >= 2;
        this.enableShopStrengthen = this.hasGrowthNode(20301) && game.getDifficulty() >= 4;
        
        this.freeStrengthen = this.hasGrowthNode(10801);
        
        if (this.hasGrowthNode(30402)) {
            this.strengthenDiscount += 60;
        } else if (this.hasGrowthNode(30102)) {
            this.strengthenDiscount += 30;
        }
    }
    
    public boolean hasGrowthNode(int nodeId) {
        return this.getGame().getManager().hasGrowthNode(nodeId);
    }
    
    public int getStartingCoin() {
        int gold = 0;
        
        if (this.hasGrowthNode(10103)) {
            gold += 50;
        } if (this.hasGrowthNode(10403)) {
            gold += 100;
        } if (this.hasGrowthNode(10702)) {
            gold += 200;
        }
        
        return gold;
    }

    public void setFreeStrengthen(boolean b) {
        this.freeStrengthen = b;
    }
}
