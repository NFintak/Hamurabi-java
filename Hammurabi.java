package hammurabi;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Hammurabi {
    static Random rand = new Random();
    Scanner scan = new Scanner(System.in);
    int yearNum = 0;
    int population = 100;
    int grainBushels = 2800;
    int landOwned = 1000;
    int landVal = 19;
    int deaths = 0;
    int starvationDeaths = 0;
    int plagueDeaths = 0;
    int newImmigrants = 0;
    int currentHarvest = 0;
    int rateOfHarvest = 0;
    int grainDestroyed = 0;

    public static void main(String[] args) {
        new Hammurabi().playGame();
    }

    void playGame() {
        this.yearNum = yearNum;
        this.population = population;
        this.grainBushels = grainBushels;
        this.landOwned = landOwned;
        this.landVal = landVal;
        this.deaths = deaths;
        this.starvationDeaths = starvationDeaths;
        this.plagueDeaths = plagueDeaths;
        this.newImmigrants = newImmigrants;
        this.currentHarvest = currentHarvest;
        this.rateOfHarvest = rateOfHarvest;
        this.grainDestroyed = grainDestroyed;

        while (yearNum < 11) {

            openingMessage();
            askHowManyAcresToBuy(this.landVal, this.grainBushels);
            askHowManyAcresToSell(this.landOwned);
            askHowMuchGrainToFeedPeople(this.grainBushels);
            askHowManyAcresToPlant(this.landOwned, this.population, this.grainBushels);

            //plagueDeaths();
            //starvationDeaths();
            //uprising();
            //immigrants();
            //harvest();
            //grainEatenByRats();
            //newCostOfLand();

            yearNum++;
        }
    }

    int getNumber(String message) {
        while (true) {
            System.out.print(message);
            try {
                return scan.nextInt();
            }
            catch (InputMismatchException e) {
                System.out.println("\"" + scan.next() + "\" isn't a number!");
            }
        }
    }

    void openingMessage() {
        if (this.yearNum == 1) {
            System.out.println("O great Hammurabi!\n");
            System.out.println("You are in year 1 of your ten year rule.\n");
            System.out.println("In the previous year 0 people starved to death.\n");
            System.out.println("In the previous year 5 people entered the kingdom.\n");
            System.out.println("The population is now 100 people.\n");
            System.out.println("We harvested 3000 bushels at 3 bushels per acre.\n");
            System.out.println("Rats destroyed 200 bushels, leaving 2800 bushels in storage.\n");
            System.out.println("The city owns 1000 acres of land.\n");
            System.out.println("Land is currently worth 19 bushels per acre.\n");
        } else {
            System.out.println("O great Hammurabi!\n");
            System.out.println(String.format("You are in year %s of your ten year rule.\n", this.yearNum));
            System.out.println(String.format("In the previous year %s people starved to death.\n", this.starvationDeaths));
            System.out.println(String.format("In the previous year %s people died from a plague.\n", this.plagueDeaths));
            System.out.println(String.format("In the previous year %s people entered the kingdom.\n", this.newImmigrants));
            System.out.println(String.format("The population is now %s people.\n", this.population));
            System.out.println(String.format("We harvested %s bushels at %s bushels per acre.\n", this.currentHarvest, this.rateOfHarvest));
            System.out.println(String.format("Rats destroyed %s bushels, leaving %s bushels in storage.\n", this.grainDestroyed, this.grainBushels));
            System.out.println(String.format("The city owns %s acres of land.\n", this.landOwned));
            System.out.println(String.format("Land is currently worth %s bushels per acre.\n", this.landVal));
        }
    }

    int askHowManyAcresToBuy(int price, int bushels) {
        int acresToBuy = getNumber("O great Hammurabi, how many acres shall you buy?\n");
        if (acresToBuy * price > bushels) {
            acresToBuy = getNumber("O great Hammurabi, surely you jest! " +
                    "We only have " + bushels + " bushels left!");
        }
        //this.landOwned += acresToBuy;
        return acresToBuy;
    }

    int askHowManyAcresToSell(int acresOwned) {
        int acresToSell = getNumber("O great Hammurabi, how many acres shall you sell?\n");
        if (acresToSell > acresOwned) {
            acresToSell = getNumber("O great Hammurabi, surely you jest! " +
                    "We only have " + acresOwned + " acres of land!");
        }
        //acresOwned -= acresToSell;
        return acresToSell;
    }

    int askHowMuchGrainToFeedPeople(int bushels) {
        int grainsFed = getNumber("O great Hammurabi, how mmuch grain shall you feed your people?\n");
        if (grainsFed > bushels) {
            grainsFed = getNumber("O great Hammurabi, surely you jest! " +
                    "We only have " + bushels + " bushels left!");
        }
        //bushels -= grainsFed;
        return grainsFed;
    }

    int askHowManyAcresToPlant(int acresOwned, int population, int bushels) {
        return 0;
    }

    int plagueDeaths(int population) {
        return 0;
    }

    int starvationDeaths(int population, int bushelsFedToPeople) {
        return 0;
    }

    boolean uprising(int population, int howManyPeopleStarved) {
        return false;
    }

    int immigrants(int population, int acresOwned, int grainInStorage) {
        return 0;
    }

    int harvest(int acres, int bushelsUsedAsSeeds) {
        return 0;
    }

    int grainEatenByRats(int bushels) {
        return 0;
    }

    int newCostOfLand() {
        this.landVal = rand.nextInt(17, 24);
        return this.landVal;
    }
}
