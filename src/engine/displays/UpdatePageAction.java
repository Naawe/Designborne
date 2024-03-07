package engine.displays;

import engine.actions.Action;
import engine.actors.Actor;
import engine.positions.GameMap;

/**
 * An action for updating the page of the menu's pagination system.
 * This class is package-private since it should only be used by the {@link Menu} class
 */
class UpdatePageAction extends Action {
    private final Menu menu;
    private final int page;

    public UpdatePageAction(Menu menu, int page) {
        this.menu = menu;
        this.page = page;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        return menu.showMenu(actor, new Display(), page).execute(actor, map);
    }

    @Override
    public String menuDescription(Actor actor) {
        return "View page " + page;
    }
}
