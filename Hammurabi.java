package hammurabi;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Hammurabi {
    static Random rand = new Random();
    Scanner scan = new Scanner(System.in);
    int yearNum = 1;
    int population = 100;
    int totalPop = 100;
    int grainBushels = 2800;
    int landOwned = 1000;
    int landVal = 19;
    int plantedLand = 0;
    int bushelsFed = 0;
    int deaths = 0;
    int starvationDeaths = 0;
    int plagueDeaths = 0;
    int newImmigrants = 0;
    int currentHarvest = 0;
    int rateOfHarvest = 0;
    int grainDestroyed = 0;
    double percentageStarved = 0.0;
    int acresPerPerson = 0;

    public static void main(String[] args) {
        new Hammurabi().playGame();
    }

    void playGame() {
        while (this.yearNum < 11) {
            openingMessage();
            askHowManyAcresToBuy(this.landVal, this.grainBushels);
            askHowManyAcresToSell(this.landVal, this.landOwned);
            askHowMuchGrainToFeedPeople(this.grainBushels);
            askHowManyAcresToPlant(this.landOwned, this.population, this.grainBushels);
            //plagueDeaths();
            starvationDeaths(this.population, this.bushelsFed);
            if (uprising(this.population, this.starvationDeaths)) {
                this.yearNum = 12;
            }
            //while (this.starvationDeaths == 0) {
            //    immigrants(this.population, this.landOwned, this.grainBushels);
            //}
            harvest(this.plantedLand);
            //grainEatenByRats(this.grainBushels);
            newCostOfLand();
            this.yearNum++;
        }
        calcEndGameStats();
        endGameSummary();
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
            System.out.println("O great Hammurabi!");
            System.out.println("You are in year 1 of your ten year rule.");
            System.out.println("In the previous year 0 people starved to death.");
            System.out.println("In the previous year 5 people entered the kingdom.");
            System.out.println("The population is now 100 people.");
            System.out.println("We harvested 3000 bushels at 3 bushels per acre.");
            System.out.println("Rats destroyed 200 bushels, leaving 2800 bushels in storage.");
            System.out.println("The city owns 1000 acres of land.");
            System.out.println("Land is currently worth 19 bushels per acre.");
        } else {
            System.out.println("O great Hammurabi!");
            System.out.println(String.format("You are in year %s of your ten year rule.", this.yearNum));
            System.out.println(String.format("In the previous year %s people starved to death.", this.starvationDeaths));
            System.out.println(String.format("In the previous year %s people died from a plague.", this.plagueDeaths));
            System.out.println(String.format("In the previous year %s people entered the kingdom.", this.newImmigrants));
            System.out.println(String.format("The population is now %s people.", this.population));
            System.out.println(String.format("We harvested %s bushels at %s bushels per acre.", this.currentHarvest, this.rateOfHarvest));
            System.out.println(String.format("Rats destroyed %s bushels, leaving %s bushels in storage.", this.grainDestroyed, this.grainBushels));
            System.out.println(String.format("The city owns %s acres of land.", this.landOwned));
            System.out.println(String.format("Land is currently worth %s bushels per acre.", this.landVal));
        }
    }

    void endGameSummary() {
        if (uprising(this.population, this.starvationDeaths)) {
            System.out.println(String.format("Due to your poor decisions, %s of %s people starved.", this.starvationDeaths, this.population));
            System.out.println("The remaining people revolted, removing you from office.");
            System.out.println("Better luck next time.\n");
        } else {
            System.out.println(String.format("In your 10-year term of office, %s percent of the population starved, for a total of %s.", this.percentageStarved, this.deaths));
            System.out.println(String.format("You started with 10 acres per person, and ended with %s acres per person.", this.acresPerPerson));
            System.out.println("Congratulations on your successful term!");
            //what other stats should be added?
        }
    }

    int askHowManyAcresToBuy(int price, int bushels) {
        int acresToBuy = getNumber("O great Hammurabi, how many acres shall you buy?\n");
        if (acresToBuy * price > bushels) {
            acresToBuy = getNumber("O great Hammurabi, surely you jest! " +
                    "We only have " + bushels + " bushels left!\n");
        } else if (acresToBuy < 0) {
            acresToBuy = getNumber("O great Hammurabi, please use a positive number!\n");
        }
        this.grainBushels -= (acresToBuy * price);
        this.landOwned += acresToBuy;
        return acresToBuy;
    }

    int askHowManyAcresToSell(int price, int acresOwned) {
        int acresToSell = getNumber("O great Hammurabi, how many acres shall you sell?\n");
        if (acresToSell > acresOwned) {
            acresToSell = getNumber("O great Hammurabi, surely you jest! " +
                    "We only have " + acresOwned + " acres of land!\n");
        } else if (acresToSell < 0) {
            acresToSell = getNumber("O great Hammurabi, please use a positive number!\n");
        }
        this.grainBushels += (acresToSell * price);
        this.landOwned -= acresToSell;
        return acresToSell;
    }

    int askHowMuchGrainToFeedPeople(int bushels) {
        int grainsFed = getNumber("O great Hammurabi, how much grain shall you feed your people?\n");
        if (grainsFed > bushels) {
            grainsFed = getNumber("O great Hammurabi, surely you jest! " +
                    "We only have " + bushels + " bushels left!\n");
        } else if (grainsFed < 0) {
            grainsFed = getNumber("O great Hammurabi, please use a  positive number!\n");
        }
        this.grainBushels -= grainsFed;
        this.bushelsFed = grainsFed;
        return grainsFed;
    }

    int askHowManyAcresToPlant(int acresOwned, int population, int bushels) {
        int acresToPlant = getNumber("O great Hammurabi, on how many acres shall we plant grains?\n");
        if (acresToPlant > acresOwned) {
            acresToPlant = getNumber("O great Hammurabi, surely you jest! " +
                    "We only have " + acresOwned + " acres to plant on!\n");
        } else if (acresToPlant > population * 10) {
            acresToPlant = getNumber("O great Hammurabi, surely you jest! " +
                    "We only have " + population + " people to tend the crops!\n");
        } else if (acresToPlant > bushels) {
            acresToPlant = getNumber("O great Hammurabi, surely you jest! " +
                    "We only have " + bushels + " bushels to use for seeds!\n");
        } else if (acresToPlant < 0) {
            acresToPlant = getNumber("O great Hammurabi, please use a positive number!\n");
        }
        this.grainBushels -= acresToPlant;
        this.plantedLand = acresToPlant;
        return acresToPlant;
    }

    int plagueDeaths(int population) {
        return 0;
    }

    int starvationDeaths(int population, int bushelsFedToPeople) {
        int peopleStarved = population - (bushelsFedToPeople / 20);
        if (peopleStarved < 0) {
            peopleStarved = 0;
        }
        this.starvationDeaths = peopleStarved;
        return peopleStarved;
    }

    boolean uprising(int population, int howManyPeopleStarved) {
        boolean uprisingImminent = false;
        if ((double)howManyPeopleStarved / population > .45) {
            uprisingImminent = true;
        } else {
            this.population -= this.starvationDeaths;
            this.deaths += this.starvationDeaths;
        }
        return uprisingImminent;
    }

    int immigrants(int population, int acresOwned, int grainInStorage) {
        int numOfImmigrants = ((20 * acresOwned + grainInStorage) / (100 * population)) + 1;
        this.newImmigrants = numOfImmigrants;
        this.population += this.newImmigrants;
        this.totalPop += this.newImmigrants;
        return numOfImmigrants;
    }

    int harvest(int acres) {
        int harvestQuality =  rand.nextInt(1, 7);
        int totalHarvest = harvestQuality * acres;
        this.rateOfHarvest = harvestQuality;
        this.currentHarvest = totalHarvest;
        this.grainBushels += totalHarvest;
        return totalHarvest;
    }

    int grainEatenByRats(int bushels) {
        int amountEaten = 0;
        if (rand.nextInt(100) < 41) {
            amountEaten = rand.nextInt(10, 31) * 100 / bushels;
        }
        this.grainDestroyed = amountEaten;
        this.grainBushels -= amountEaten;
        return amountEaten;
    }

    int newCostOfLand() {
        this.landVal = rand.nextInt(17, 24);
        return this.landVal;
    }

    void calcEndGameStats() {
        this.percentageStarved = (double)this.starvationDeaths / this.totalPop;
        this.acresPerPerson = this.landOwned / this.population;
    }

}
