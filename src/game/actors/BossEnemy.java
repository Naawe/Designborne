package game.actors;

import engine.actors.attributes.ActorAttributeOperations;
import engine.actors.attributes.BaseActorAttributes;
import engine.displays.Display;

/**
 * Class for the Boss enemy
 * Created by:
 * @author Zhuojun Zhao
 * @version 1.0.0
 * @see EnemyActor
 */
public abstract class BossEnemy extends EnemyActor{
    /***
     * Constructor
     * @param name name of enemy
     * @param displayChar display char
     * @param hitPoints HP
     * @param runeAmount amount of runes will be dropped once the enemy is defeated
     */
    public BossEnemy(String name, char displayChar, int hitPoints, int runeAmount){
        super(name, displayChar, hitPoints, runeAmount);
    }
    /**
    * perform operation to enemy. Execution may vary depending on implemetation.
    */
    @Override
    public void resetEnemy(){
        this.modifyAttribute(BaseActorAttributes.HEALTH, ActorAttributeOperations.UPDATE, getAttributeMaximum(BaseActorAttributes.HEALTH));
        Display display = new Display();
        display.println(name + "'s HP is resetted to " + getAttributeMaximum(BaseActorAttributes.HEALTH));
}
}
