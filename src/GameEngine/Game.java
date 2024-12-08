package GameEngine;

import Interfaces.GameSet;

public class Game implements GameSet
{
    private final Dungeon dungeon;
    private Site monsterSite;
    private Site rogueSite;

    public Game(Dungeon dungeon, Site rogueSite, Site monsterSite)
    {
        this.dungeon = dungeon;
        this.rogueSite = rogueSite;
        this.monsterSite = monsterSite;
    }

    @Override
    public Site getMonsterSite()
    {
        return monsterSite;
    }

    @Override
    public Site getRogueSite()
    {
        return rogueSite;
    }

    @Override
    public Dungeon getDungeon()
    {
        return dungeon;
    }

    public void updateRogueSite(Site to)
    {
        rogueSite = to;
    }

    public void updateMonsterSite(Site to)
    {
        monsterSite = to;
    }

    public boolean monsterCaughtRogue()
    {
        return rogueSite.equals(monsterSite);
    }
}
