package game.items;

import engine.items.Item;
import game.general.Ability;

/**
 * key for the gate
 *
 * Created by:
 * @author Ewan Lumsden Smith
 * Modified by:
 * @author Phoebe Jiang
 * @version 1.0.0
 * @see Item
 */
public class Key extends Item {
    /***
     * Constructor which creates an instance of Key with an unlock capability
     */
    public Key() {
        super("Gate Key", '-', true);
        this.capabilitySet.addCapability(Ability.UNLOCK);
    }
}
