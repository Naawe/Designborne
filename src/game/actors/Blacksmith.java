package game.actors;

import engine.actions.ActionList;
import engine.actors.Actor;
import engine.items.Item;
import engine.positions.GameMap;
import game.actions.Conversation;
import game.actions.ConversationAction;
import game.actions.Monologue;
import game.general.Ability;
import game.general.Status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class for the Blacksmith
 * Created by:
 * @author Phoebe
 * Modified by:
 * @author Vasi Karabourniotis
 * @version 1.0.0
 * @see NPC
 */
public class Blacksmith extends NPC implements Conversation{
    /**
     * Hit points for the Blacksmith
     */
    private static final int HIT_POINTS = 10;
    /**
     * An instance of the Monologue class
     */
    private final Monologue blacksmithMonologue = new Monologue();
    /**
     * An array list of monologue string options
     */
    private final List<String> monologueBlacksmith = new ArrayList<>();
    /**
     * An array list of the default strings
     */
    private final List<String> blacksmithRandomMonologue = new ArrayList<>(Arrays.asList(
            "I used to be an adventurer like you, but then …. Nevermind, let’s get back to smithing.",
            "It’s dangerous to go alone. Take my creation with you on your adventure!",
            "Ah, it’s you. Let’s get back to make your weapons stronger."
    ));
    /**
     * The constructor of the Actor class.
     */
    public Blacksmith() {
        super("Blacksmith", 'B', HIT_POINTS);
        addCapability(Ability.CAN_UPGRADE);
    }

    /**
     * Allows the Blacksmith to have a conversation
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return ActionList actions.
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actionList = new ActionList();
        actionList.add(new ConversationAction("listens to the Blacksmith's monologue", this));
        return actionList;
    }

    /**
     * A getter method to retrieve a random string from the monologueBlacksmith array list
     * @return A randomly chosen string
     */
    public String getBlacksmithMonologue() {
        return blacksmithMonologue.getRandomMonologue(monologueBlacksmith);
    }

    /**
     * This method allows Blacksmith to have unique monologue options
     * @param actor The actor who can have a conversation
     * @return A randomly chosen string to be said by the Blacksmith
     */
    @Override
    public String conversation(Actor actor) {
        //Add the common monologue lines for Blacksmith
        for(String monologue : this.blacksmithRandomMonologue) {
            blacksmithMonologue.addMonologue(monologueBlacksmith, monologue);
        }

        //If actor has a great knife add this monologue
        for (Item item : actor.getItemInventory()) {
            if (item.hasCapability(Ability.HAS_GREAT_KNIFE)) {
                String monologueHasGKnife = "Hey now, that’s a weapon from a foreign land that I have not seen for so long. I can upgrade it for you if you wish.";
                blacksmithMonologue.addMonologue(monologueBlacksmith, monologueHasGKnife);
            }
        }

        String monologueAbxNotDefeated = "Beyond the burial ground, you’ll come across the ancient woods ruled by Abxervyer. Use my creation to slay them and proceed further!";
        //If actor hasn't defeated the Abxervyer add this monologue
        if(!actor.hasCapability(Status.ABXERVYER_DEFEATED)){
            blacksmithMonologue.addMonologue(monologueBlacksmith, monologueAbxNotDefeated);
        }

        String monologueAbxDefeated = "Somebody once told me that a sacred tree rules the land beyond the ancient woods until this day.";
        //If actor has defeated the Abxervyer add this monologue and remove the not defeated monologue
        if(actor.hasCapability(Status.ABXERVYER_DEFEATED)){
            blacksmithMonologue.addMonologue(monologueBlacksmith, monologueAbxDefeated);
            blacksmithMonologue.removeMonologue(monologueBlacksmith, monologueAbxNotDefeated);
        }

        return getBlacksmithMonologue();
    }
}
