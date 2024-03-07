package game.general;

import java.util.Random;
import engine.actions.MoveActorAction;
import engine.displays.Display;
import engine.positions.FancyGroundFactory;
import engine.positions.GameMap;
import engine.positions.World;
import game.actors.*;
import game.extended.ForestGameMap;
import game.ground.*;
import game.ground.Graveyard;
import game.ground.Void;
import game.items.*;
import game.spawner.*;

/**
 * The main class to start the game.
 * Created by:
 * @author Adrian Kristanto
 * Modified by:
 * @author Phoebe Jiang
 * @author Vasi Karabourniotis
 * @author Ewan Lumsden Smith
 * @version 2.0.0
 */
public class  Application {
    /**
     * The main entry point for the game as well as starting its execution.
     * This method sets up the game world, including game maps, actors, items, and various game elements.
     *
     * @param args Command-line arguments.
     */

    public static void main(String[] args) {
        World world = new World(new Display());
         FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt(),
                new Wall(), new Floor(), new Puddle(), new Void());

        GameMap abandonedVillageGameMap = new GameMap(groundFactory, GameMapCollection.abandonedVillageMap);
        world.addGameMap(abandonedVillageGameMap);

        GameMap burialGroundGameMap = new GameMap(groundFactory, GameMapCollection.burialGroundMap);
        world.addGameMap(burialGroundGameMap);

        ForestGameMap ancientWoodsGameMap = new ForestGameMap(groundFactory, GameMapCollection.ancientWoodsMap);
        world.addGameMap(ancientWoodsGameMap);
        ForestGameMap abxervyerGameMap = new ForestGameMap(groundFactory, GameMapCollection.abxervyerMap);
        world.addGameMap(abxervyerGameMap);
        GameMap overGrownSanctuary = new GameMap(groundFactory, GameMapCollection.overGrownSanctuary);
        world.addGameMap(overGrownSanctuary);

        for (String line : FancyMessage.TITLE.split("\n")) {
            new Display().println(line);
            try {
                Thread.sleep(200);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        abandonedVillageGameMap.at(30, 11).setGround(new Graveyard(new WanderingUndeadSpawner(0.25f, new Random())));
        ancientWoodsGameMap.at(10, 10).addItem(new BloodBerry());
        abandonedVillageGameMap.at(27,6).addItem(new Broadsword());
        abandonedVillageGameMap.at(31,5).addActor(new Blacksmith());

        Gate gateToBurialGround = new Gate();
        gateToBurialGround.addAllowableAction(new MoveActorAction(burialGroundGameMap.at(1,1),"to the Burial Grounds!"));
        abandonedVillageGameMap.at(10, 6).setGround(gateToBurialGround);

        Gate gateBackToAbandonedVillage = new Gate();
        gateBackToAbandonedVillage.addAllowableAction(new MoveActorAction(abandonedVillageGameMap.at(1,1), "Back to Abandoned Village"));
        burialGroundGameMap.at(5,7).setGround(gateBackToAbandonedVillage);

        burialGroundGameMap.at(30,11).setGround(new Graveyard(new HollowSoldierSpawner(0.1f, new Random())));

        Gate gateToAncientWoods = new Gate();
        gateToAncientWoods.addAllowableAction(new MoveActorAction(ancientWoodsGameMap.at(1,1), "to the Ancient Woods!"));
        burialGroundGameMap.at(1,1).setGround(gateToAncientWoods);

        Gate gateBackToBurialGround = new Gate();
        gateBackToBurialGround.addAllowableAction(new MoveActorAction(burialGroundGameMap.at(1,1), "Back to Burial Grounds"));
        ancientWoodsGameMap.at(10,5).setGround(gateBackToBurialGround);

        Gate gateToAbxervyerRoom = new Gate();
        gateToAbxervyerRoom.addAllowableAction(new MoveActorAction(abxervyerGameMap.at(10,11), "to Abxervyer battle field"));
        ancientWoodsGameMap.at(10,6).setGround(gateBackToBurialGround);

        Player player = new Player("The Abstracted One", '@', 150, abandonedVillageGameMap);

        ancientWoodsGameMap.at(30,5).setGround(new Bush(new RedWolfSpawner(0.3f,new Random())));
        ancientWoodsGameMap.at(20,7).setGround(new Hut(new ForestKeeperSpawner(0.15f,new Random())));
        ancientWoodsGameMap.at(18, 3).addActor(new Traveller());
        ancientWoodsGameMap.at(20, 4).addItem(new GreatKnife());

        abxervyerGameMap.at(1,12).addItem(new GiantHammer());
        abxervyerGameMap.at(7, 8).setGround(new Bush(new RedWolfSpawner(0.3f,new Random())) );
        abxervyerGameMap.at(7, 9).setGround(new Bush(new RedWolfSpawner(0.3f,new Random())) );
        abxervyerGameMap.at(7, 10).setGround(new Hut(new ForestKeeperSpawner(0.15f,new Random())));

        Gate defeatedAbxervyerGate = new Gate();
        defeatedAbxervyerGate.addAllowableAction(new MoveActorAction(ancientWoodsGameMap.at(1,1),"to the Ancient Woods!"));
        defeatedAbxervyerGate.addAllowableAction(new MoveActorAction(overGrownSanctuary.at(4,1), "to the overGrown Sanctuary"));
        Abxervyer abxervyer = new Abxervyer(defeatedAbxervyerGate);
        abxervyerGameMap.at(8, 11).addActor(abxervyer);
        abxervyer.addWeatherMap(abxervyerGameMap);
        abxervyer.addWeatherMap(ancientWoodsGameMap);
        abxervyer.addBattleGameMap(abxervyerGameMap);

        overGrownSanctuary.at(17,12).setGround(new Graveyard(new HollowSoldierSpawner(0.1f, new Random())));
        overGrownSanctuary.at(10,10).setGround(new Hut(new EldenTreeSpawner(0.2f,new Random())));
        overGrownSanctuary.at(5,5).setGround(new Bush(new LivingTreeBranchSpawner(0.9f,new Random())));
        world.addPlayer(player, abandonedVillageGameMap.at(29, 5));
        world.run();
    }
}
