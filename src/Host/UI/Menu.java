package Host.UI;

import Host.UI.MenuAction.MenuAction;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private final ArrayList<MenuAction> menuActions = new ArrayList<>();

    public Menu addMenuAction(MenuAction menuAction) {
        this.menuActions.add(menuAction);

        return this;
    }

    public void render() {
        int i = 0;

        for (MenuAction menuAction : this.menuActions) {
            System.out.println("[" + (i++) + "] - " + menuAction.getName());
        }
    }

    public int readMenuAction() {
        try {
            Scanner scanner = new Scanner(System.in);

            int action = scanner.nextInt();

            if (!this.isValidAction(action)) {
                this.renderInvalidActionMessage();
                return this.readMenuAction();
            }

            return action;
        } catch (Exception exception) {
            this.renderInvalidActionMessage();
            return this.readMenuAction();
        }
    }

    private boolean isValidAction(int maybeAction) {
        return maybeAction >= 0 && maybeAction < this.menuActions.size();
    }

    private void renderInvalidActionMessage() {
        System.out.println("Nieprawidłowa akcja!");
    }

    public void callMenuAction(int action) {
        this.menuActions.get(action).call();
    }
}
