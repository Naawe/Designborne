package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.items.Item;
import game.actions.ReplenishAction;

/**
 * refreshing flask allows the actor to replenish stamina
 */
public class RefreshingFlask extends Item {
    /***
     * Constructor.
     */
    public RefreshingFlask() {
        super("Refreshing Flask", 'u', true);
    }

    /**
     * returns a replinsh action that replenishes the users Stamina
     * @param owner the actor that owns the item
     * @return
     */
    @Override
    public ActionList allowableActions(Actor owner) {
        ActionList actionlist = new ActionList();
        actionlist.add(super.allowableActions(owner));
        actionlist.add(new ReplenishAction(BaseActorAttributes.STAMINA, 20, "Stamina",this));
        return actionlist;
    }
}
