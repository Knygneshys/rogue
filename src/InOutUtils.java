import GameEngine.Dungeon;
import GameEngine.Game;
import GameEngine.Site;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class InOutUtils
{
    private static char monsterIcon;

    public static Game ReadInput(File file)
    {
        try
        {
            Site monsterSite = null, rogueSite = null;
            Scanner scanner = new Scanner(file);
            int n = scanner.nextInt();
            scanner.nextLine();
            Character[][] sites = new Character[n][n];
            int row = 0;
            while(scanner.hasNext())
            {
                int column = 0;
                String line = scanner.nextLine();
                StringBuilder compactLine = new StringBuilder();
                for(int i = 0; i < line.length(); i++)
                {
                    char type = line.charAt(i++);
                    compactLine.append(type);
                    if(type != ' ')
                    {
                        if(type == '@')
                        {
                            rogueSite = new Site(row, column);
                            type = '.';
                        }
                        else if(Character.isLetter(type))
                        {
                            monsterSite = new Site(row, column);
                            monsterIcon = type;
                            type = '.';
                        }
                        sites[row][column] = type;
                    }
                    column++;
                }
                row++;
            }
            Dungeon dungeon = new Dungeon(n, sites);

            return new Game(dungeon, rogueSite, monsterSite);
        } catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void print(Game game)
    {
        Site rogueSite = game.getRogueSite();
        Site monsterSite = game.getMonsterSite();
        int size = game.getDungeon().size();
        printTopIndices(size);
        for(int i = 0 ; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                if(j == 0)
                {
                    int index = i+1;
                    if(index < 10)
                        System.out.print(index + "  ");
                    else
                        System.out.print(index + " ");
                }
                Character site = game.getDungeon().getType(i, j);
                if(site != null)
                {
                    if(i == monsterSite.i() && j == monsterSite.j())
                        site = monsterIcon;
                    else if(i == rogueSite.i() && j == rogueSite.j())
                        site = '@';
                    System.out.print(site + " ");
                }
                else
                    System.out.print("  ");
            }
            System.out.println();
        }
    }

    private static void printTopIndices(int size)
    {
        System.out.print("   ");
        for(int i = 0; i < size; i++)
            System.out.print((i+1) + " ");
        System.out.println();
    }
}
