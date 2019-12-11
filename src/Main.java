public class Main {

    public static void main(String[] args) {
        Menu menu = new Menu();

        menu.createPlayers();

        menu.generateRandoms();
        menu.determineFirstPlayer(menu.getToons());
        do {
            menu.checkForWinner(menu.getToons());
            menu.displayPlayerMenuOptions();
            menu.executeMove(menu.getMenuOption(), menu.getCurrentPlayerIndex());
        } while (!menu.isWinnerFound());


    }

}
