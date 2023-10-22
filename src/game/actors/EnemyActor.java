package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.behaviours.FollowBehavior;
import game.general.Ability;
import game.general.Status;
import game.actions.AttackAction;
import game.behaviours.AttackBehavior;
import game.behaviours.WanderBehaviour;
import game.items.RefreshingFlask;
import game.items.Runes;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * abstract class that enemy actors inherit from
 * Created by:
 * @author Ewan Lumsden-Smith, Phoebe Jiang & Vasi Karabourniotis
 * @version 1.0.0
 * @see Actor
 */
public abstract class EnemyActor extends Actor implements Droppable{
    /**
     * A map list of behaviours and their corresponding priorities
     */
    protected Map<Integer, Behaviour> behaviours = new HashMap<>();
    /**
     * The actor who is the target of the enemy actor
     */
    protected Actor target;
    /**
     * The amount of Runes (currency) an actor or item has
     */
    protected final int runeAmount;
    /**
     * The priority of wander behaviour
     */
    protected final int WANDER_BEHAVIOUR_PRIORITY = 999;
    /**
     * The priority of attack behaviour
     */
    protected final int ATTACK_BEHAVIOUR_PRIORITY = 800;
    /**
     * the priority for the follow behaviour
     */
    protected final int FOLLOW_BEHAVIOUR_PRIORITY = 900;
    /**
     * the odds of runes being dropped 1/1 currently
      */
    protected final float RUNE_DROP_CHANCE = 1.0f;

    /**
     * construct for the enemyActor abstract class.
     * @param name sets name
     * @param displayChar sets display char
     * @param hitPoints sets hitPoints
     * @param runeAmount sets runeAmount
     */
    public EnemyActor(String name, char displayChar, int hitPoints, int runeAmount) {
        super(name, displayChar, hitPoints);
        this.behaviours.put(WANDER_BEHAVIOUR_PRIORITY, new WanderBehaviour());
        this.behaviours.put(ATTACK_BEHAVIOUR_PRIORITY, new AttackBehavior());
        this.capabilitySet.addCapability(Status.ENEMY);
        this.runeAmount = runeAmount;
    }

    /**
     * a method to potentially drop an item
     * @param location the location the item is being dropped
     * @param item the item being dropped
     * @param chance the chance of it dropping
     */
    @Override
    public void drop(Location location, Item item, float chance) {
        Random random = new Random();
        if(random.nextFloat() < chance){
            location.addItem(item);
        }
    }

    /**
     * Gives the enemy actor the ability to have multiple behaviours
     * @param priority the order in which behaviours should execute based on their importance
     * @param behaviour the behaviour to be used
     */
    public void addBehavior(int priority, Behaviour behaviour){
        this.behaviours.put(priority, behaviour);
    }

    /**
     * At each turn, select a valid action to perform.
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return the valid action that can be performed in that iteration or null if no valid action is found
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        for (Behaviour behaviour : behaviours.values()) {
            Action action = behaviour.getAction(this, map);
            if (action != null)
                return action;
        }
        return new DoNothingAction();
    }

    /**
     * checks for weapons in the other actors inventory and if there are any weapons it returns an attack action for those weapons
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return ActionList actions.
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();
        if (otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
            actions.add(new AttackAction(this, direction, otherActor.getIntrinsicWeapon()));
        }
        return actions;
    }
}
