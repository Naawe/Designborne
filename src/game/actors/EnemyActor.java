package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.general.Ability;
import game.general.Status;
import game.actions.AttackAction;
import game.behaviours.AttackBehavior;
import game.behaviours.WanderBehaviour;
import game.items.HealingVial;
import game.items.Key;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * abstract class that enemy actors inherit from
 */
public abstract class EnemyActor extends Actor {
    protected Map<Integer, Behaviour> behaviours = new HashMap<>();

    /**
     * sets the attributes as well as the behaviours and capabilities on an enemy actor
     * @param name
     * @param displayChar
     * @param hitPoints
     */
    public EnemyActor(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        this.behaviours.put(999, new WanderBehaviour());
        this.behaviours.put(998, new AttackBehavior());
        this.capabilitySet.addCapability(Ability.WALK_ON_VOID);
    }

    /**
     * At each turn, select a valid action to perform.
     *
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
     * The wandering undead can be attacked by any actor that has the HOSTILE_TO_ENEMY capability
     *
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return
     */
    /**
     * checks for weapons in the other actors inventory and if there are any weapons it returns an attack action for those weapons
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();
        if (otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
            actions.add(new AttackAction(this, direction, otherActor.getIntrinsicWeapon()));
        }
        List<WeaponItem> weaponItems = otherActor.getItemInventory().stream()
                .filter(item -> item.hasCapability(Ability.ATTACK))
                .map(item -> (WeaponItem) item)
                .collect(Collectors.toList());

        for (WeaponItem weaponItem : weaponItems) {
            actions.add(new AttackAction(this, direction, weaponItem));
        }
        return actions;
    }

    /**
     * drops all of the items that the EnemyActors drop and then removes the actor drom the map
     * @param actor the perpetrator
     * @param map where the actor fell unconscious
     * @return
     */
    @Override
    public String unconscious(Actor actor, GameMap map) {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        int number = random.nextInt(10);
        if(number < 2){
            map.locationOf(this).addItem(new HealingVial());
            builder.append("\n" + name + " dropped a healing Vial" + "\n");
        }
        if(random.nextInt(4) == 1){
            map.locationOf(this).addItem(new Key());
            builder.append("\n" + name + " dropped a key");
        }
        builder.insert(0,super.unconscious(actor, map));
        return builder.toString();
    }
}
