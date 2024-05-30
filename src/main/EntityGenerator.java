package main;

import entity.Entity;
import object.*;

public class EntityGenerator {

    GamePanel gamepanel;
    public EntityGenerator(GamePanel gp) {
        this.gamepanel = gp;
    }
    public Entity getObject(String itemName)
    {
        return switch (itemName) {
            case AxeObject.objName -> new AxeObject(gamepanel);
            case BedObject.objName -> new BedObject(gamepanel);
            case SwordObject.objName -> new SwordObject(gamepanel);
            case DoorObject.objName -> new DoorObject(gamepanel);
            case KeyObject.objName -> new KeyObject(gamepanel);
            case Potion_RedObject.objName -> new Potion_RedObject(gamepanel);
            case ShieldObject.objName -> new ShieldObject(gamepanel);
            case LanternObject.objName -> new LanternObject(gamepanel);
            case ChestObject.objName -> new ChestObject(gamepanel);
            case HeartObject.objName -> new HeartObject(gamepanel);
            case BronzeCoinObject.objName -> new BronzeCoinObject(gamepanel);
            case SilverCoinObject.objName -> new SilverCoinObject(gamepanel);
            case GoldCoinObject.objName -> new GoldCoinObject(gamepanel);
            case ManaObject.objName -> new ManaObject(gamepanel);
            case FireballObject.objName -> new FireballObject(gamepanel);
            default -> null;
        };
    }
}
