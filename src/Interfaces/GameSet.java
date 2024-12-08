package Interfaces;
import GameEngine.*;

public interface GameSet
{
    public Site getMonsterSite();            // return the site occupied by the monster
    public Site getRogueSite();                // return the site occupied by the rogue
    public Dungeon getDungeon();              // return the dungeon
}
