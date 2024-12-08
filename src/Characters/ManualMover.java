package Characters;

import GameEngine.Dungeon;
import GameEngine.Game;
import GameEngine.Site;

class ManualMover
{
    private static Game game;

    static void manualMove(String input, Site from, Game curr, Class<?> charClass)
    {
        game = curr;
        Site to;
        switch (input)
        {
            case("S"):
                to = new Site(from.i()+1, from.j());
                updateGame(from, to, charClass);
                break;
            case("N"):
                to = new Site(from.i()-1, from.j());
                updateGame(from, to, charClass);
                break;
            case("E"):
                to = new Site(from.i(), from.j()+1);
                updateGame(from, to, charClass);
                break;
            case("W"):
                to = new Site(from.i(), from.j()-1);
                updateGame(from, to, charClass);
                break;
            case("NW"):
                to = new Site(from.i()-1, from.j()-1);
                updateGame(from, to, charClass);
                break;
            case("NE"):
                to = new Site(from.i()-1, from.j()+1);
                updateGame(from, to, charClass);
                break;
            case("SW"):
                to = new Site(from.i()+1, from.j()-1);
                updateGame(from, to, charClass);
                break;
            case("SE"):
                to = new Site(from.i()+1, from.j()+1);
                updateGame(from, to, charClass);
                break;
        }
    }

    private static void updateGame(Site from, Site to, Class<?> charClass)
    {
        Dungeon dungeon = game.getDungeon();
        if(dungeon.isLegalMove(from, to))
        {
            String name = charClass.getName();
            if(name.equals("Characters.Rogue"))
                game.updateRogueSite(to);
            else
                game.updateMonsterSite(to);
        }
    }
}
