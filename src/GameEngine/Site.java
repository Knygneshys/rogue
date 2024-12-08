package GameEngine;

import Interfaces.SiteSet;

import java.util.Comparator;

public class Site implements SiteSet
{
    private final int i;
    private final int j;

    public Site(int i, int j) // instantiate a new Site for location (i, j)
    {
        this.i = i;
        this.j = j;
    }

    @Override
    public int i()
    {
        return i;
    }

    @Override
    public int j()
    {
        return j;
    }

    @Override
    public int manhattan(Site w)
    {
        return Math.abs(i - w.i()) + Math.abs(j - w.j());
    }

    @Override
    public boolean equals(Site w)
    {
        return w.i == i && w.j == j;
    }

    @Override
    public boolean equals(Object obj)
    {
        return equals((Site)obj);
    }

    @Override
    public String toString()
    {
        return String.format("(" + (i+1) + " " + (j + 1) + ")");
    }
}

