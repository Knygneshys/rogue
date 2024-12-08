package Characters;

import GameEngine.*;
import Interfaces.MonsterSet;

import java.util.*;

public class Monster implements MonsterSet
{
    private final Game game;

    public Monster(Game g)  // create a new rogue who is playing a game g
    {
        game = g;
    }

    public void manualMove(String input)
    {
        ManualMover.manualMove(input, game.getMonsterSite(), game, Monster.class);
    }

    // AI movement
    @Override
    public Site move()
    {
        Dungeon dungeon = game.getDungeon();
        Object[][] prec = new Object[dungeon.size()][dungeon.size()];
        Site current = game.getMonsterSite();
        prec[current.i()][current.j()] = current;
        Site end = game.getRogueSite();
        PriorityQueue<Site> queue = new PriorityQueue<>(new SiteComparator(end, true));
        queue.add(current);

        while(!queue.isEmpty())
        {
            current = queue.poll();
            if(current.equals(end))
                break;
            ArrayList<Site> neighbours = dungeon.findNeighbours(current);

            for(Site neighbour : neighbours)
            {
                if(prec[neighbour.i()][neighbour.j()] == null)
                {
                    prec[neighbour.i()][neighbour.j()] = current;
                    queue.add(neighbour);
                }
            }
        }

        return goThroughPrec(prec);
    }

    private Site goThroughPrec(Object[][] prec)
    {
        Site current = game.getRogueSite();
        Site end = game.getMonsterSite();
        Site previous = null;
        while(!current.equals(end))
        {
            previous = current;
            current = (Site)prec[current.i()][current.j()];
        }

        return previous;
    }
}
