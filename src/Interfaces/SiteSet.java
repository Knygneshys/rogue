package Interfaces;
import GameEngine.Site;

public interface SiteSet
{
    public int i();                              // get i coordinate
    public int j();                              // get j coordinate
    public int manhattan(Site w);                // return Manhattan distance from invoking site to w
    public boolean equals(Site w);               // is invoking site equal to w?
}
