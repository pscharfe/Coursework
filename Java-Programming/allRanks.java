
/**
 * This class contains methods that can be iterated over a large number of spreadsheets (.csv files)
 * Each of these csv files represents a single year (e.g., "yob1960.csv") and has three columns: birth name (e.g., "Mary"), gender ("M" or "F"), and the total births by that name/year
 * 
 * @author (Patrick) 
 * @version (0.1) 2017-07-19
 */

import java.io.*;
import java.util.*;
import edu.duke.*;
import org.apache.commons.csv.*;

public class allRanks {

    public void printNames () {
        FileResource fr = new FileResource();
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            if (numBorn <= 100) {
                System.out.println("Name " + rec.get(0) +
                           " Gender " + rec.get(1) +
                           " Num Born " + rec.get(2));
            }
        }
    }

    public void totalBirths (FileResource fr) {
        int totalBirths = 0;
        int totalBoys = 0;
        int totalGirls = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths += numBorn;
            if (rec.get(1).equals("M")) {
                totalBoys += numBorn;
            }
            else {
                totalGirls += numBorn;
            }
        }
        System.out.println("total births = " + totalBirths);
        System.out.println("female girls = " + totalGirls);
        System.out.println("male boys = " + totalBoys);
    }
    
    public void testTotalBirths () {
        FileResource fr = new FileResource("yob1900.csv");
        totalBirths(fr);
    }
    
    public long getFirstMale (int year) {
        long firstMaleRank = -1;
        String male = "M";
        FileResource fr = new FileResource("us_babynames/us_babynames_by_year/yob" + year + ".csv");
        CSVParser parser = fr.getCSVParser(false);
            for (CSVRecord rec : parser) {
            if (male.equals(rec.get(1))) {
            firstMaleRank = rec.getRecordNumber();
            System.out.println(firstMaleRank);
            break;
    }
}
    return firstMaleRank;
    }

    // This method gets the popularity rank of a male birth name in a given year by gender. The catch is that male names begin halfway through the document, so you have to start counting again there.
    public long getRankM (int year, String name, String gender) {
        long rank = -1;
        FileResource fr = new FileResource("us_babynames/us_babynames_by_year/yob" + year + ".csv");
        CSVParser parser = fr.getCSVParser(false);
        long firstMaleRank = 0;
        String male = "M";
        String female = "F";
            for (CSVRecord rec : parser) {
            if (male.equals(rec.get(1))) {
            firstMaleRank = rec.getRecordNumber();
            System.out.println(firstMaleRank);
            break;
    }
}
        for(CSVRecord record : parser) {

            String currentName = record.get(0);
            String currentGender = record.get(1);
            
            if(currentGender.equals(gender) && currentName.equals(name)) {
                long unadjustedRank = record.getRecordNumber();
                rank = unadjustedRank - firstMaleRank + 1;
            }
        }
    System.out.println("The rank of " + name + " in " + year + " was " + rank);
    return rank;
    }
    
        public long getRankF (int year, String name, String gender) {
        long rank = -1;
        FileResource fr = new FileResource("us_babynames/us_babynames_by_year/yob" + year + ".csv");
        CSVParser parser = fr.getCSVParser(false);
        long firstMaleRank = 0;
        String male = "M";
        String female = "F";
        for(CSVRecord record : parser) {
            if (female.equals(gender)) {
            String currentName = record.get(0);
            String currentGender = record.get(1);
            
            if(currentGender.equals(gender) && currentName.equals(name)) {
                rank = record.getRecordNumber();
            }
    }
}
    System.out.println("The rank of " + name + " in " + year + " was " + rank);
    return rank;
}

    // This method gets the birth name that has a particular popularity rank in a given year. Again, male popularity has to take into account the fact that male names appear 2nd in the list.
    public String getName (int year, int rank, String gender) {
        String name = "NO NAME";
        String male = "M";
        String female = "F";
        long firstMaleRank = 0;
        long unadjustedRank = 0;
        FileResource fr = new FileResource("us_babynames/us_babynames_by_year/yob" + year + ".csv");
        CSVParser parser = fr.getCSVParser(false);
       for (CSVRecord rec : parser) {
            if (male.equals(rec.get(1))) {
            firstMaleRank = rec.getRecordNumber();
            System.out.println(firstMaleRank);
            break;
    }
}
            if (gender == "M")  {
            for (CSVRecord rec : parser) {
            String currentName = rec.get(0);
            String currentGender = rec.get(1);
            unadjustedRank = rec.getRecordNumber();
            long adjustedRank = unadjustedRank - firstMaleRank + 1;
            if ((adjustedRank == rank) && (currentGender.equals(gender))) {
            name = currentName;
        }
    }
}
            else  {
            for (CSVRecord rec : parser)  {
            String currentName = rec.get(0);
            String currentGender = rec.get(1);
            long currentRank = rec.getRecordNumber();
            System.out.println(currentName);
            if ((currentRank == rank) && (currentGender.equals(gender))) {
            name = currentName;
        }
    }
}
            return name;
}

    // This method returns the name might have been named had you been born in another year, assuming that its popularity rank in that year matches the popularity rank of your name at your birth.
    public void whatIsNameInYear (String name, int year, int newYear, String gender) {
        FileResource fr1 = new FileResource("us_babynames/us_babynames_by_year/yob" + year + ".csv");
        FileResource fr2 = new FileResource("us_babynames/us_babynames_by_year/yob" + newYear + ".csv");
        CSVParser parser1 = fr1.getCSVParser(false);
        CSVParser parser2 = fr2.getCSVParser(false);
        String newName = "NO NAME";
        String male = "M";
        String female = "F";
        long firstMaleRank1 = 0;
        long firstMaleRank2 = 0;
        long rank = 0;
        for(CSVRecord rec : parser1) {
            if (male.equals(rec.get(1))) {
            firstMaleRank1 = rec.getRecordNumber();
            System.out.println(firstMaleRank1);
            break;
    }
}
        for(CSVRecord rec : parser1) {
           if (gender == "F")  {
        String currentName = rec.get(0);
        String currentGender = rec.get(1);
            if(currentGender.equals(gender) && currentName.equals(name)) {
                rank = rec.getRecordNumber();
                System.out.println("Rank in " + year + " among females: " + rank);
            }
}
           if (gender == "M") {
                String currentName = rec.get(0);
        String currentGender = rec.get(1);
        if(currentName.equals(name) && currentGender.equals(gender)) {
        long unadjustedRank1 = rec.getRecordNumber();
        rank = unadjustedRank1 - firstMaleRank1 + 1;
        System.out.println("Rank in " + year + " among males:" + rank);
    }
}
}
            for (CSVRecord rec : parser2) {
            if (male.equals(rec.get(1))) {
            firstMaleRank2 = rec.getRecordNumber();
            System.out.println(firstMaleRank2);
            break;
    }
}
        for(CSVRecord rec : parser2) {
            if (gender == "F")  {
            String currentName = rec.get(0);
            String currentGender = rec.get(1);
            long currentRank = rec.getRecordNumber();
            if ((currentRank == rank) && (currentGender.equals(gender))) {
            newName = currentName;
        }
        }
            if (gender == "M") {
            String currentName = rec.get(0);
            String currentGender = rec.get(1);
            long unadjustedRank2 = rec.getRecordNumber();
            long adjustedRank2 = unadjustedRank2 - firstMaleRank2 + 1;
            if ((adjustedRank2 == rank) && (currentGender.equals(gender))) {
            newName = currentName;
    }
}
}
        System.out.println(name + " born in " + year + " would be " + newName + " if they were born in " + newYear);
}

            // This method returns the peak popularity of a female name over many years found in many spreadsheets of a dataset (=DirectoryResource).
    public double yearOfHighestRankF (String name, String gender) {
                // Initialize a DirectoryResource
		DirectoryResource dr = new DirectoryResource();
		// Define rankTotal, howMany
		double rankTotal = 0.0;
		int howMany = 0;
		// For every file the directory add name rank to agvRank
		for(File f : dr.selectedFiles()) {
			FileResource fr = new FileResource(f);
			CSVParser parser = fr.getCSVParser(false);
			for(CSVRecord record : parser) {
				String currName = record.get(0);
				String currGender = record.get(1);
				if(currName.equals(name) && currGender.equals(gender)){
					long currRank = record.getRecordNumber();
					rankTotal += (double)currRank;
					howMany += 1;
				}
			}
		}
		// Define avgRank = rankTotal / howMany
		double avgRank = rankTotal / (double)howMany;
		return avgRank;
    }
            // This method returns the peak popularity of a male name over many years found in many spreadsheets of a dataset (=DirectoryResource).
    public long yearOfHighestRankM (String name, String gender) {
        long highestRank = 0;
        int yearOfHighestRank = -1;
        String male = "M";
        String female = "F";
        long firstMaleRank = 0;
        String fileName = "";
        DirectoryResource dr = new DirectoryResource();
       
        // Iterate through all files
        for(File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVParser parser = fr.getCSVParser(false);
            
        // Iterate through all records in file
        for(CSVRecord rec : parser) {
        if (male.equals(rec.get(1))) {
        firstMaleRank = rec.getRecordNumber();
        break;
    }
}
                for(CSVRecord record : parser) {
                
                String currName = record.get(0);
                String currGender = record.get(1);

                if(currName.equals(name) && currGender.equals(gender)) {
                    long currRank = record.getRecordNumber();
                    long adjustedRank = currRank - firstMaleRank;
                    
                    if(highestRank == 0) {
                        highestRank = adjustedRank;
                        fileName = f.getName();
                    } 
                    else {
                        if(highestRank > adjustedRank) {
                            highestRank = adjustedRank;
                            fileName = f.getName();
                        }
                    }
                }
            }
        }

        // Remove all non-numeric characters from the filename
        fileName = fileName.replaceAll("[^\\d]", "");
        
        // Convert String fileName to Integer
        yearOfHighestRank = Integer.parseInt(fileName);

        return yearOfHighestRank;
    }

    // This method gives the average rank of a name over many years found in many spreadsheets of a dataset (=DirectoryResource).
    public double getAverageRankM (String name, String gender) {
        DirectoryResource dr = new DirectoryResource();
        double rankTotal = 0.0;
        int howMany = 0;
        long firstMaleRank = 0;
        String male = "M";
        for(File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVParser parser = fr.getCSVParser(false);
            for(CSVRecord record : parser) {
                String currName = record.get(0);
                String currGender = record.get(1);
                if(currName.equals(name) && currGender.equals(gender)){
                    long currRank = record.getRecordNumber();
                    long adjustedRank = currRank - firstMaleRank;
                    rankTotal += (double)adjustedRank;
                    howMany += 1;
                }
            }
        }
        double avgRank = rankTotal / (double)howMany;
        return avgRank;
    }
    
        // This method gives the average rank of a name over many years found in many spreadsheets of a dataset (=DirectoryResource).
    public double getAverageRankF (String name, String gender) {
        DirectoryResource dr = new DirectoryResource();
        double rankTotal = 0.0;
        int howMany = 0;
        for(File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVParser parser = fr.getCSVParser(false);
            for(CSVRecord record : parser) {
                String currName = record.get(0);
                String currGender = record.get(1);
                if(currName.equals(name) && currGender.equals(gender)){
                    long currRank = record.getRecordNumber();
                    rankTotal += (double)currRank;
                    howMany += 1;
                }
            }
        }
        double avgRank = rankTotal / (double)howMany;
        return avgRank;
    }


    // This method adds up the # of births of those names from the same year and gender that are more popular than a given name.
    public int getTotalBirthsRankedHigherM (int year, String name, String gender) {
        int numBorn = 0;
        long rank = getRankM(year, name, gender);
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser(false);
        for(CSVRecord record : parser) {
            int currBorn = Integer.parseInt(record.get(2));
            String currGender = record.get(1);
            long currRank = record.getRecordNumber();
            if(gender.equals(currGender) && rank > currRank) {
                numBorn += currBorn;
            }
        }
        return numBorn;
}
}

