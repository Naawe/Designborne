package game.actors;

import java.util.ArrayList;

import engine.actions.Action;
import engine.actions.ActionList;
import engine.actors.Actor;
import engine.actors.attributes.ActorAttributeOperations;
import engine.actors.attributes.BaseActorAttribute;
import engine.actors.attributes.BaseActorAttributes;
import engine.displays.Display;
import engine.positions.GameMap;
import engine.positions.Location;
import engine.displays.Menu;
import engine.weapons.IntrinsicWeapon;
import game.general.Ability;
import game.general.Status;
import game.items.*;
import game.notification.DeathPublisher;
import game.notification.PlayerDeathMessageBus;

/**
 * Class representing the Player.
 * Created by:
 * @author Adrian Kristanto
 * Modified by:
 * Ewan Lumsden-Smith & Phoebe Jiang
 * @see Actor
 */

public class Player extends Actor{
    /**
     * The maximum health of the player
     */
    private final int MAX_HEALTH = 200;
    /**
     * The time the player sleeps/rests for
     */
    private final int MILLIS = 200;
    /**
     * Whether the game can stop or not
     */
    private final int EXIT_STATUS = 0;
    /**
     * The damage inflicted by the player's intrinsic weapon
     */
    private final int INTRINSIC_DAMAGE = 15;
    /**
     * The rate in which the intrinsic weapon is successful in its execution
     */
    private final int INTRINSIC_HIT_RATE = 80;
    /***
     * message bus to publish player death
     */
    private DeathPublisher messageBus = new PlayerDeathMessageBus();
    /***
     * the map player will be respawned to
     */
    private GameMap birthMap;
    /**
     * x coordinate of respawn location
     */
    private int BIRTH_POINT_X = 29;
    /**
     * y coordinate of respawn location
     */
    private int BIRTH_POINT_Y = 5;
    /**
     * Constructor.
     * @param name        Name to call the player in the UI
     * @param displayChar Character to represent the player in the UI
     * @param hitPoints   Player's starting number of hit points
     */
    public Player(String name, char displayChar, int hitPoints, GameMap birthMap){
        super(name, displayChar, hitPoints);
        this.addCapability(Status.HOSTILE_TO_ENEMY);
        this.addCapability(Ability.WALK_ON_FLOOR);
        this.addAttribute(BaseActorAttributes.STAMINA, new BaseActorAttribute(MAX_HEALTH));
        this.birthMap = birthMap;
        //TODO: remove tester
        this.addItemToInventory(new BloodBerry());
        this.addBalance(100);
    }

    /**
     * simply prints current stats and then returns a menu with all possible actions in it
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return returns an action taken by the player
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.INCREASE, getAttributeMaximum(BaseActorAttributes.STAMINA)/100);
        // Handle multi-turn Actions
        if (lastAction.getNextAction() != null)
            return lastAction.getNextAction();
        if(!this.isConscious()) {
            respawn(map);
        }
        printPlayerStatus(display);
        // return/print the console menu
        Menu menu = new Menu(actions);
        return menu.showMenu(this, display);
    }

    /**
     * returns a string of actor name
     * @return returns a String of the name of the player
     */
    public String getName(){
        return name;
    }

    /**
     * sets the actors intrinsic weapon
     * @return returns the players intrinsic weapon
     */
    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(INTRINSIC_DAMAGE, "Punches", INTRINSIC_HIT_RATE);
    }

    /**
     * returns the string for the player
     * @return returns a string describing the player
     */
    @Override
    public String toString() {
        return super.toString();
    }
    
    
    /** reset player once it's dead by calling reset max attributes method, drop runes and printing out relavent console message.
     * @param display display object
     * @param map Gamemap where player fell unconscious
     */
    public void resetPlayer(Display display, GameMap map){
        display.println(this.getName() + " Died. Respawned to where they started the game.");
        display.println(this.getName() + " dropped " + getBalance() + " runes.");
        dropRune(map.locationOf(this));
        resetAttributes();
    }
/**
 * drop runes to ground
 * @param location the location that the runes will be dropped
 */
    public void dropRune(Location location){
        Rune dropped = new Rune(getBalance());
        location.addItem(dropped);
        this.deductBalance(this.getBalance());
    }
    /**
     * reset player max attributes and move player to birth point
     */
    public void resetAttributes(){
        birthMap.moveActor(this, birthMap.at(BIRTH_POINT_X,BIRTH_POINT_Y));
        this.modifyAttribute(BaseActorAttributes.HEALTH, ActorAttributeOperations.UPDATE, getAttributeMaximum(BaseActorAttributes.HEALTH));
        this.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.UPDATE, getAttributeMaximum(BaseActorAttributes.STAMINA));
    }
    /**
     * print out player status
     * @param display display object
     */
    public void printPlayerStatus(Display display){
        display.print(getName()+ "\n");
        display.print(getAttribute(BaseActorAttributes.HEALTH) + "/" + getAttributeMaximum(BaseActorAttributes.HEALTH) + " HP" + "\n");
        display.print(getAttribute(BaseActorAttributes.STAMINA) + "/" + getAttributeMaximum(BaseActorAttributes.STAMINA) + " Stamina" + "\n");
        display.print(getBalance() + " Runes\n");      
    }
    /**
     * @param map the map where player fell unconscious
     */
    @Override
    public String unconscious(GameMap map){
        respawn(map);
        return "Player unconscious.";
    }
    /**
     * the method that collects all operations that are needed to perform the respawn operation
     * @param map gamemap where player fell unconscious
     */
    public void respawn(GameMap map){
        messageBus.publishDeath();
        resetPlayer(new Display(), map);
    }
}
