package game.actors;

import engine.actions.Action;
import engine.actions.ActionList;
import engine.actions.DoNothingAction;
import engine.actors.Actor;
import engine.actors.Behaviour;
import engine.actors.attributes.ActorAttributeOperations;
import engine.actors.attributes.BaseActorAttributes;
import engine.displays.Display;
import engine.items.Item;
import engine.positions.GameMap;
import engine.positions.Location;
import game.actions.DieAction;
import game.general.Status;
import game.notification.DeathSubcriber;
import game.notification.PlayerDeathMessageBus;
import game.actions.AttackAction;
import game.behaviours.AttackBehavior;
import game.behaviours.WanderBehaviour;

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
public abstract class EnemyActor extends Actor implements DeathSubcriber {
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
     * HP that the enemy will be setted to remove the enemy
     */
    private int REMOVE_ENEMY_HP = 0;
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
        PlayerDeathMessageBus.addSubscriber(this);
        //TODO: REMOVE tester
        this.modifyAttribute(BaseActorAttributes.HEALTH, ActorAttributeOperations.DECREASE, 10);
    }

    /**
     * a method to potentially drop an item
     * @param location the location the item is being dropped
     * @param item the item being dropped
     * @param chance the chance of it dropping
     */
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
        if(!(isConscious())){
            return new DieAction();
        }
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
    /***
     * prompt operations on enemy once death of the actor it subscribes to happens.
     */
    @Override
    public void notifyDeath(){
        resetEnemy();
    }
    /**
    * perform operation to enemy. Execution may vary depending on implemetation.
    */
    public void resetEnemy(){
        modifyAttribute(BaseActorAttributes.HEALTH, ActorAttributeOperations.UPDATE, REMOVE_ENEMY_HP);
    }
}
