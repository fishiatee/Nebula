package emu.nebula.game.tower.cases;

import java.util.HashMap;
import java.util.Map;

import emu.nebula.GameConstants;
import emu.nebula.game.tower.StarTowerShopGoods;
import emu.nebula.proto.PublicStarTower.HawkerGoods;
import emu.nebula.proto.PublicStarTower.StarTowerRoomCase;
import emu.nebula.proto.StarTowerInteract.StarTowerInteractReq;
import emu.nebula.proto.StarTowerInteract.StarTowerInteractResp;
import lombok.Getter;

@Getter
public class StarTowerHawkerCase extends StarTowerBaseCase {
    private Map<Integer, StarTowerShopGoods> goods;
    
    public StarTowerHawkerCase() {
        this.goods = new HashMap<>();
    }

    @Override
    public CaseType getType() {
        return CaseType.Hawker;
    }
    
    public void addGoods(StarTowerShopGoods goods) {
        this.getGoods().put(getGoods().size() + 1, goods);
    }
    
    @Override
    public StarTowerInteractResp interact(StarTowerInteractReq req, StarTowerInteractResp rsp) {
     // Set nil resp
        rsp.getMutableNilResp();
        
        // Get goods
        var goods = this.getGoods().get(req.getHawkerReq().getSid());
        
        // Make sure we have enough currency
        int gold = this.getGame().getRes().get(GameConstants.STAR_TOWER_GOLD_ITEM_ID);
        if (gold < goods.getPrice() || goods.isSold()) {
            return rsp;
        }
        
        // Mark goods as sold
        goods.markAsSold();
        
        // Add case
        this.getGame().addCase(rsp.getMutableCases(), this.getGame().createPotentialSelector());
        
        // Remove items
        var change = this.getGame().addItem(GameConstants.STAR_TOWER_GOLD_ITEM_ID, -goods.getPrice(), null);
        
        // Set change info
        rsp.setChange(change.toProto());
        
        // Success
        return rsp;
    }
    
    // Proto
    
    @Override
    public void encodeProto(StarTowerRoomCase proto) {
        var hawker = proto.getMutableHawkerCase();
        
        for (var entry : this.getGoods().entrySet()) {
            var sid = entry.getKey();
            var goods = entry.getValue();
            
            var info = HawkerGoods.newInstance()
                    .setIdx(goods.getGoodsId())
                    .setSid(sid)
                    .setType(goods.getType())
                    .setGoodsId(102) // ?
                    .setPrice(goods.getPrice())
                    .setTag(1);
            
            hawker.addList(info);
        }
    }
}
