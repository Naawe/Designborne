package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.items.GiantHammer;

public class GreatSlamAction extends Action {
    /**
     * the item that is using this action
     */
    private GiantHammer weaponItem;
    private Actor target;
    private String direction;

    /**
     * sets the weapon
     * @param weapon
     */
    public GreatSlamAction(Actor target, String direction, GiantHammer weapon){
        this.target = target;
        this.direction = direction;
        this.weaponItem = weapon;
    }
    @Override
    public String execute(Actor actor, GameMap map) {
        actor.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.DECREASE, 10);
        AttackAction targetAttack = new AttackAction(target, direction, weaponItem);
        targetAttack.execute(actor, map);

        this.weaponItem.updateDamageMultiplier(80);

        for (Exit exit : map.locationOf(actor).getExits()) {
            Location surroundingLocation = exit.getDestination();
            if (surroundingLocation.containsAnActor()) {
                Actor surroundingActor = surroundingLocation.getActor();
                AttackAction surroundingAttack = new AttackAction(surroundingActor, direction, weaponItem);
                surroundingAttack.execute(actor, map);
            }
        }

        this.weaponItem.updateDamageMultiplier(160);
        return "SLAMMING";
    }


    @Override
    public String menuDescription(Actor actor) {
        return actor + " uses great slam with " + weaponItem ;
    }
}
