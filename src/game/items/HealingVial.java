package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.items.Item;
import game.actions.ConsumableAction;

/**
 * healing vial gives the replinish action for HP
 */
public class HealingVial extends Item {

    /***
     * Constructor.
     */
    public HealingVial() {
        super("Healing Vial", 'a', true);

    }

    /**
     * returns a replenish action object
     * @param owner the actor that owns the item
     * @return
     */
    @Override
    public ActionList allowableActions(Actor owner) {
        ActionList actionlist = new ActionList();
        actionlist.add(super.allowableActions(owner));
        actionlist.add(new ConsumableAction(BaseActorAttributes.HEALTH, 20, "HP",this));
        return actionlist;
    }
}
