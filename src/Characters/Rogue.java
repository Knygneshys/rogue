package Characters;
import Interfaces.RogueSet;
import GameEngine.*;

import java.util.*;


public class Rogue implements RogueSet
{
    private final Game game;
    private final List<Site> corridorEntries;

    public Rogue(Game g)  // create a new rogue who is playing a game g
    {
        game = g;
        Dungeon dungeon = game.getDungeon();
        corridorEntries = new ArrayList<>();
        ArrayList<Site> testedSites = new ArrayList<>();
        ArrayList<Site> cycleSites = new ArrayList<>();
        for(int i = 0; i < dungeon.size(); i++)
        {
            for(int j = 0; j < dungeon.size(); j++)
            {
                Character type = dungeon.getType(i, j);
                Site possibleEntry = new Site(i, j);
                if(type != null && dungeon.isCorridorEntry(possibleEntry))
                {
                    if(cycleSites.contains(possibleEntry) || dungeon.isStartOfCycle(possibleEntry, testedSites))
                    {
                        corridorEntries.add(possibleEntry);
                        for(Site site : testedSites)
                            if(!cycleSites.contains(site))
                                cycleSites.add(site);
                        testedSites = new ArrayList<>();
                    }
                }
            }
        }
    }

    public void manualMove(String input)
    {
        ManualMover.manualMove(input, game.getRogueSite(), game, Rogue.class);
    }

    @Override
    public Site move()
    {
        try {
            Dungeon dungeon = game.getDungeon();
            if (dungeon.isRoom(game.getRogueSite()) && !corridorEntries.isEmpty())
            {
                Site next = BFSTowardsCorridor(dungeon);
                if(!next.equals(game.getRogueSite()))
                    return next;
            }

            // sorts neighbouring sites by closest to monster
            ArrayList<Site> neighbours = dungeon.findNeighbours(game.getRogueSite());
            neighbours.sort(new SiteComparator(game.getMonsterSite(), true));
            filterSafeNeighbours(neighbours);

            return neighbours.getLast();
        }
        // The exceptions mean that the Rogue had no safe neighbours to go to
        catch (NullPointerException | NoSuchElementException e)
        {
            return game.getRogueSite();
        }
    }

    private Site BFSTowardsCorridor(Dungeon dungeon)
    {
        Object[][] prec = new Object[dungeon.size()][dungeon.size()];
        Site current = game.getRogueSite();
        Site corridor = current;
        prec[current.i()][current.j()] = current;
        Queue<Site> queue = new LinkedList<>();
        queue.add(current);

        while (!queue.isEmpty()) {
            current = queue.poll();
            if (corridorEntries.contains(current))
            {
                corridor = current;
                break;
            }

            ArrayList<Site> neighbours = dungeon.findNeighbours(current);
            filterSafeNeighbours(neighbours);
            for (Site neighbour : neighbours) {
                if (prec[neighbour.i()][neighbour.j()] == null) {
                    prec[neighbour.i()][neighbour.j()] = current;
                    queue.add(neighbour);
                }
            }
        }

        return goThroughPrec(prec, corridor);
    }

    private void filterSafeNeighbours(ArrayList<Site> neighbours)
    {
        for(int i = 0; i < neighbours.size(); i++)
        {
            Site neighbour = neighbours.get(i);
            if(neighbour.equals(game.getMonsterSite()))
            {
                neighbours.remove(i);
                i--;
            }
            ArrayList<Site> neighbourNeighbours = game.getDungeon().findNeighbours(neighbour);
            for(int j = 0; j < neighbourNeighbours.size(); j++)
                if(neighbourNeighbours.get(j).equals(game.getMonsterSite()))
                {
                    neighbours.remove(i);
                    i--;
                    break;
                }
        }
    }

    private Site goThroughPrec(Object[][] prec, Site corridor)
    {
        Site current = corridor;
        Site end = game.getRogueSite();
        Site previous = end;
        while(!current.equals(end))
        {
            previous = current;
            current = (Site)prec[current.i()][current.j()];
        }

        return previous;
    }
}
