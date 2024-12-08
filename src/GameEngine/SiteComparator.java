package GameEngine;
import java.util.Comparator;

public class SiteComparator implements Comparator<Site>
{
    private final Site end;
    private final boolean ascending;
    
    public SiteComparator(Site end, boolean ascending)
    {
        this.end = end;
        this.ascending = ascending;
    }
    
    @Override
    public int compare(Site o1, Site o2)
    {
        if (o1.manhattan(end) < o2.manhattan(end))
        {
            return ascending ? -1 : 1;
        } else if (o1.manhattan(end) > o2.manhattan(end))
        {
            return ascending ? 1 : -1;
        }

        return 0;
    }
}
