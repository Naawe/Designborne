package game.actors;

import engine.actions.ActionList;
import engine.actors.Actor;
import engine.positions.GameMap;
import game.behaviours.FollowBehavior;
import game.general.Status;

/**
 * abstract class for EnemyActors that follow the player
 * Created by:
 * @author Ewan Lumsden Smith
 * @version 1.0.0
 * @see EnemyActor
 */
public abstract class ForestEnemy extends EnemyActor{

    /**
     * construct for the FollowingEnemy abstract class.
     * @param name        sets name
     * @param displayChar sets display char
     * @param hitPoints   sets hitPoints
     * @param _runeAmount sets runeAmount
     */
    public ForestEnemy(String name, char displayChar, int hitPoints, int _runeAmount) {
        super(name, displayChar, hitPoints, _runeAmount);
    }

    /**
     * Sets the follow behaviour in the object if the player comes within range and then returns allowable actions
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return ActionList of allowable actions for a given other actor.
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        if(otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)){
            addBehavior(FOLLOW_BEHAVIOUR_PRIORITY, new FollowBehavior(otherActor));
        }
        return super.allowableActions(otherActor, direction, map);
    }
}
