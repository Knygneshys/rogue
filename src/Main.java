import Characters.*;
import GameEngine.*;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main
{
    public static void main(String[] args)
    {
        File mapFile = new File("src/Maps/Testing.txt");
        Game game = InOutUtils.ReadInput(mapFile);

        // Character setup
        Rogue lara = new Rogue(game);
        Monster shrike = new Monster(game);

        // Character selection
        InOutUtils.print(game);
        System.out.println("Type MONSTER if you want to play as the monster or ROGUE if you want to play as the rogue or write AI for an AI duel: ");
        String input = System.console().readLine().toUpperCase();
        Pattern pattern = Pattern.compile("\\w+");
        Matcher matcher = pattern.matcher(input);
        while(matcher.find())
        {
            if(matcher.group().equals("MONSTER") || matcher.group().equals("ROGUE") || matcher.group().equals("AI"))
                break;
            System.out.println("There is no such character!");
            input = System.console().readLine().toUpperCase();
            matcher = pattern.matcher(input);
        }
        String playerChar = matcher.group();

        // Actual game
        System.out.println("GAME BEGINS!");
        if(playerChar.equals("AI"))
        {
            while(!input.equals("X"))
            {
                game.updateMonsterSite(shrike.move());
                InOutUtils.print(game);
                System.out.println("MONSTER'S MOVE!");
                if (game.monsterCaughtRogue()) {
                    System.out.println("MONSTER WON!");
                    break;
                }
                game.updateRogueSite(lara.move());
                InOutUtils.print(game);
                System.out.println("ROGUE'S MOVE!");

                System.out.println("Type X if you want to stop the game or any key to continue");
                input = readInput();
            }
        }
        else {
            String movementOptions = "Where do you want to move?\nThe options are N, E, S, W, NW, NE, SW, SE or type X if you want to stop playing.";
            while (!input.equals("X")) {
                if (playerChar.equals("ROGUE")) // player chose Rogue so the AI needs to control Monster
                    game.updateMonsterSite(shrike.move());
                else // player chose Monster so they need to input its move
                {
                    System.out.println(movementOptions);
                    shrike.manualMove(input = readInput());
                }
                InOutUtils.print(game);
                System.out.println("MONSTER MOVE!");
                if (game.monsterCaughtRogue()) {
                    System.out.println("MONSTER WON!");
                    break;
                }

                if (playerChar.equals("ROGUE")) // player chose rogue so they need to control Rogue
                {
                    System.out.println(movementOptions);
                    lara.manualMove(input = readInput());
                } else
                    game.updateRogueSite(lara.move());
                InOutUtils.print(game);
                System.out.println("ROGUE MOVE!");
                if (game.monsterCaughtRogue()) {
                    System.out.println("MONSTER WON!");
                    break;
                }
            }
        }
    }

    private static String readInput()
    {
            String input = System.console().readLine().toUpperCase();
            if(input.isEmpty())
                return "";
            Pattern pattern = Pattern.compile("\\w+");
            Matcher matcher = pattern.matcher(input);
            matcher.find();
            return matcher.group();
    }
}
