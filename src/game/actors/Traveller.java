package game.actors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import engine.actions.ActionList;
import engine.actors.Actor;
import engine.items.Item;
import engine.positions.GameMap;
import game.actions.BuyAction;
import game.actions.Conversation;
import game.actions.ConversationAction;
import game.actions.Monologue;
import game.general.Ability;
import game.general.Status;
import game.items.Broadsword;
import game.items.GreatKnife;
import game.items.HealingVial;
import game.items.RefreshingFlask;

/**
 * A class that represents a trader actor called Traveller
 *
 * Created by:
 * @author Vasi Karabourniotis
 * modified by:
 * @author Zhuojun Zhao
 * @version 1.0.0
 * @see Trader
 */
public class Traveller extends Trader implements Conversation{
    /**
     * The amount of hit points the traveller has
     */
    private static final int HIT_POINTS = 10;
    /**
     * The cost of a broadsword
     */
    private final int BROADSWORD_COST = 250;
    /**
     * The cost of a refreshing flask
     */
    private final int REFRESHING_FLASK_COST = 75;
    /**
     * The cost of a healing Vial
     */
    private final int HEALING_VIAL_COST = 100;
    /**
     * The cost of a Great Knife
     */
    private final int GREAT_KNIFE_COST = 300;
        /**
     * An instance of the Monologue class
     */
    private final Monologue travellerMonologue = new Monologue();
    /**
     * An array list of monologue string options
     */
    private final List<String> monologueTraveller = new ArrayList<>();

    /**
     * An array list of default string options
     */
    private final List<String> travellerRandomStrings = new ArrayList<>(Arrays.asList(
            "Of course, I will never give you up, valuable customer!",
            "I promise I will never let you down with the quality of the items that I sell.",
            "You can always find me here. I'm never gonna run around and desert you, dear customer!",
            "I'm never gonna make you cry with unfair prices.",
            "Trust is essential in this business. I promise I’m never gonna say goodbye to a valuable customer like you.",
            "Don't worry, I’m never gonna tell a lie and hurt you."));

    /**
     * A constructor that creates an instance of Traveller
     */
    public Traveller() {
        super("Traveller", 'ඞ', HIT_POINTS);
    }

    /**
     * A list of buyable actions that an actor can choose to buy from the Traveller
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return A collection of actions to be executed by the Traveller
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actionList = super.allowableActions(otherActor, direction, map);
        actionList.add(new BuyAction("buys Broadsword", new Broadsword(), BROADSWORD_COST));
        actionList.add(new BuyAction("buys Refreshing Flask", new RefreshingFlask(), REFRESHING_FLASK_COST));
        actionList.add(new BuyAction("buys Healing Vial", new HealingVial(), HEALING_VIAL_COST));
        actionList.add(new BuyAction("buys Great Knife", new GreatKnife(), GREAT_KNIFE_COST));
        actionList.add(new ConversationAction("listens to the Traveller's monologue", this));
        return actionList;
    }
    /**
     * A getter method to retrieve a random string from the monologueTraveller array list
     * @return A randomly chosen string
     */
    public String getTravellerMonologue() {
        return travellerMonologue.getRandomMonologue(monologueTraveller);
    }
    /**
     * This method allows isolated traveller to have unique monologue options
     * @param actor The actor that the traveller can have a conversation with
     * @return A randomly chosen string to be said by the traveller
     */
    @Override
    public String conversation(Actor actor) {
        //Add the common monologue lines for Traveller
        for(String monologue : this.travellerRandomStrings) {
            travellerMonologue.addMonologue(monologueTraveller, monologue);
        }

        String monologueAbxNotDefeated = "You know the rules of this world, and so do I. Each area is ruled by a lord. Defeat the lord of this area, Abxervyer, and you may proceed to the next area.";
        //If actor hasn't defeated the Abxervyer add this monologue
        if(!actor.hasAttribute(Status.ABXERVYER_DEFEATED)){
            travellerMonologue.addMonologue(monologueTraveller, monologueAbxNotDefeated);
        }

        String monologueAbxDefeated = "Congratulations on defeating the lord of this area. I noticed you still hold on to that hammer. Why don’t you sell it to me? We've known each other for so long. I can tell you probably don’t need that weapon any longer.";
        for (Item item : actor.getItemInventory()) {
            if (item.hasCapability(Ability.HAS_GIANT_HAMMER)) {
                //If actor has a giant hammer add this monologue
                String monologueHasGHammer = "Ooh, that’s a fascinating weapon you got there. I will pay a good price for it. You wouldn't get this price from any other guy.";
                travellerMonologue.addMonologue(monologueTraveller, monologueHasGHammer);
                if(actor.hasAttribute(Status.ABXERVYER_DEFEATED)){
                    //If actor has defeated the Abxervyer and still holds giant hammer, add this monologue and remove the not defeated monologue
                    travellerMonologue.addMonologue(monologueTraveller, monologueAbxDefeated);
                    travellerMonologue.removeMonologue(monologueTraveller, monologueAbxNotDefeated);
                }
            }
        }
        return getTravellerMonologue();
    }
}
