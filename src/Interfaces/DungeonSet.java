package Interfaces;
import GameEngine.Site;
public interface DungeonSet
{
    public boolean isLegalMove(Site v, Site w);  // is moving from site v to w legal?
    public boolean isCorridor(Site v);          // is site v a corridor site?
    public boolean isRoom(Site v);            // is site v a room site?
    public int size();                         // return N = dimension of dungeon
}
