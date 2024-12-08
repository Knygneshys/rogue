package GameEngine;

import Interfaces.DungeonSet;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Stack;

public class Dungeon implements DungeonSet
{
    private Character[][] types;
    private int size;

    public Dungeon(int size, Character[][] locations)
    {
        types = new Character[size][size];
        this.size = size;
        for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++)
                types[i][j] = locations[i][j];
    }

    @Override
    public boolean isLegalMove(Site v, Site w)
    {
        if(!isWithinBounds(w))
        {
            System.out.println("Bounds reached!");
        }
        else
        {
            if(types[w.i()][w.j()] == null)
                return false;

            if(isRoom(v) && isRoom(w))
                return true;
            else if(isCorridor(v) && isCorridor(w))
                return v.i() == w.i() || v.j() == w.j();
            else if(isRoom(v) && isCorridor(w) || isRoom(w) && isCorridor(v))
            {
                return v.i() == w.i() || v.j() == w.j();
            }
        }
        return false;
    }

    @Override
    public boolean isCorridor(Site v)
    {
        return types[v.i()][v.j()].equals('+');
    }

    @Override
    public boolean isRoom(Site v)
    {
        return types[v.i()][v.j()].equals('.');
    }

    public Character getType(int i, int j)
    {
        return types[i][j];
    }

    @Override
    public int size()
    {
        return size;
    }

    private boolean isWithinBounds(Site site)
    {
        return site.i() < size && site.j() < size && site.i() >= 0 && site.j() >= 0;
    }

    public ArrayList<Site> findNeighbours(Site home)
    {
        ArrayList<Site> neighbours = new ArrayList<>();
        // Top 3 neighbours
        for(int i = -1; i < 2; i++)
        {
            Site possibleTopNeighbour = new Site(home.i()-1, home.j() + i);
            Site possibleBottomNeighbour = new Site(home.i()+1, home.j() + i);
            if(neighbourIsValid(possibleTopNeighbour) && isLegalMove(home, possibleTopNeighbour))
                neighbours.add(possibleTopNeighbour);
            if(neighbourIsValid(possibleBottomNeighbour) && isLegalMove(home, possibleBottomNeighbour))
                neighbours.add(possibleBottomNeighbour);
        }

        // Adjacent side neighbours
        Site possibleLeftNeighbour = new Site(home.i(), home.j() - 1);
        Site possibleRightNeighbour = new Site(home.i(), home.j() + 1);

        if(neighbourIsValid(possibleLeftNeighbour))
            neighbours.add(possibleLeftNeighbour);
        if(neighbourIsValid(possibleRightNeighbour))
            neighbours.add(possibleRightNeighbour);

        return neighbours;
    }

    private boolean neighbourIsValid(Site neighbour)
    {
        return isWithinBounds(neighbour) && types[neighbour.i()][neighbour.j()] != null;
    }

    public boolean isCorridorEntry(Site possibleEntry)
    {
        ArrayList<Site> neighbours = findNeighbours(possibleEntry);
        if(neighbours.size() == 2)
        {
            Site firstNeighbour = neighbours.getFirst();
            Site secondNeighbour = neighbours.getLast();

            if(getType(possibleEntry.i(), possibleEntry.j()).equals('+'))
                return getType(firstNeighbour.i(), firstNeighbour.j()).equals('.') || getType(secondNeighbour.i(), secondNeighbour.j()).equals('.');
            return firstNeighbour.i() == secondNeighbour.i() || firstNeighbour.j() == secondNeighbour.j();
        }
        else return getType(possibleEntry.i(), possibleEntry.j()).equals('+');
    }

    public boolean isStartOfCycle(Site start, ArrayList<Site> testedSites)
    {
        Object[][] prec = new Object[size][size];
        Stack<Site> stack = new Stack<>();
        ArrayList<Site> neighbours = findNeighbours(start); // O(8)
        // pasiziuri, kad DFS eitų į koridoriaus langelius pirmiau negu kad į kambario
        Site first = getType(neighbours.getFirst().i(), neighbours.getFirst().j()).equals('+') ? neighbours.getFirst() : neighbours.getLast();
        prec[first.i()][first.j()] = start;
        stack.push(first);
        while(!stack.empty())
        {
            Site current = stack.pop();
            if(!testedSites.contains(current))
                testedSites.add(current);
            neighbours = findNeighbours(current);
            neighbours.sort(new SiteComparator(start, false));
            for(Site neighbour : neighbours)
            {
                // jeigu kaimynas dar nebuvo aplankytas arba i current neatejom is kaimyno
                if(prec[current.i()][current.j()] == null || !prec[current.i()][current.j()].equals(neighbour))
                {
                    if(prec[neighbour.i()][neighbour.j()] == null)
                    {
                        stack.push(neighbour);
                        prec[neighbour.i()][neighbour.j()] = current;
                    }
                    else if(prec[neighbour.i()][neighbour.j()].equals(start))
                        return true;
                }
            }
        }

        return false;
    }
}
