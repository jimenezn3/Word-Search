import java.util.ArrayList; 
import java.util.Hashtable; 
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class WordFind{
    

    public static int[] getGridDimension(File fileName_) throws IOException{

        int numRows = 0;
        int numCols = 0;
        int dimension[] = {0, 0};

        
        Scanner sc = new Scanner(fileName_);
        String line = sc.nextLine();
        if(!line.startsWith("-")){
            numRows +=1;
        }

        while (sc.hasNextLine()) {
            line = sc.nextLine();

            if (line.startsWith("-")){
                continue;
            }
            numRows++;
        }
        
        if (line.length() % 2 == 0){ // Even # of chars in a line
            numCols = line.length()/2;
        }
        else{ // Odd # of chars in a line
            numCols = (line.length() - 1)/2;
        }

        dimension[0] = numRows;
        dimension[1] = numCols;

        sc.close();

        return dimension;
    }

    public static char[][] setUpGrid(File fileName_) throws IOException{

        int gridDimensions_[] = getGridDimension(fileName_);
        char Grid_[][] = new char[gridDimensions_[0]][gridDimensions_[1]];
        int row = 0;
        int col = 0;

        Scanner sc = new Scanner(fileName_);
        
        while (sc.hasNextLine()){
            String line  = sc.nextLine();
            col = 0;

            if (line.startsWith("-")){
                continue;
            }
            for (int i = 1; col < Grid_[0].length; i += 2){
                Grid_[row][col] = line.charAt(i);
                col++;
            }
            row++;
        }   
        sc.close();

        return Grid_;
    } 

    public static void setUpPattern(ArrayList<Character> pattern_, String Word_){

        for (int i = 0; i < Word_.length(); i++){
            if (Word_.charAt(i) == ' '){
                continue;
            }
            pattern_.add(Word_.charAt(i));
        }
        return;
    }

    public static boolean ignoreCaseCompare(char patternChar, char gridChar) {

        if (Character.toLowerCase(patternChar) == Character.toLowerCase(gridChar)){
            return true;
        }
        return false;
    }
    
    public static int[] findWord(char[][] Grid_, ArrayList<Character> Pattern_){

        int comparisons = 0;
        int patternSize = Pattern_.size();
        int coordinates[] = {0, 0, 0, 0}; // THIRD ELEMENT REPRESENTS DIRECTION; FOURTH ELEMENT REPRESENTS # OF COMPARISONS

        
        // SEARCH EAST
        for (int row = 0; row < Grid_.length; row++){
            for (int col = 0; col <= (Grid_[0].length - patternSize); col++){

                int j = 0;
                while ((j < patternSize) && (ignoreCaseCompare(Pattern_.get(j), Grid_[row][col + j]))){
                    j++;
                    comparisons++;
                }
                if (j < patternSize){
                    comparisons++;
                }
                if (j == patternSize){
                    return setCoord(coordinates, row + 1, col + 1, 1, comparisons);
                }
                
            }

        }

        // SEARCH WEST
        for (int row = 0; row < Grid_.length; row++) {
            for (int col = Grid_[0].length-1; col >= patternSize - 1; col--){

                int j = 0;
               
                while ((j < patternSize) && (ignoreCaseCompare(Pattern_.get(j), Grid_[row][col - j]))){
                    j++;
                    comparisons++;
                }
                if (j < patternSize){
                    comparisons++;
                }
                if(j == patternSize){
                    return setCoord(coordinates, row + 1, col + 1, 2, comparisons);
                }
            }
        }

        // SEARCH SOUTH
        for (int col = 0; col < Grid_[0].length; col++){
            for (int row = 0; row <= (Grid_.length - patternSize); row++){

                int j = 0;

                while ((j < patternSize) && (ignoreCaseCompare(Pattern_.get(j), Grid_[row + j][col]))){
                    j++;
                    comparisons++;
                }
                if (j < patternSize){
                    comparisons++;
                }
                if (j == patternSize){
                    return setCoord(coordinates, row + 1, col + 1, 3, comparisons);
                }
            }
        }

        // SEARCH NORTH
        for (int col = 0; col < Grid_[0].length; col++){
            for (int row = Grid_.length - 1; row >= patternSize - 1; row--){
                 int j = 0;

                while ((j < patternSize) && (ignoreCaseCompare(Pattern_.get(j), Grid_[row - j][col]))){
                    j++;
                    comparisons++;
                }
                if (j < patternSize){
                    comparisons++;
                }
                if (j == patternSize){
                    return setCoord(coordinates, row + 1, col + 1, 4, comparisons);
                }
            }
        }
        
        // SEARCH SOUTH-EAST
        for (int row = 0; row <= (Grid_.length - patternSize); row++){
            for (int col = 0; col <= (Grid_[0].length - patternSize); col++){

                int j = 0;

                while ((j < patternSize) && (ignoreCaseCompare(Pattern_.get(j), Grid_[row + j][col + j]))){
                    j++;
                    comparisons++;
                }
                if (j < patternSize){
                    comparisons++;
                }
                if (j == patternSize){
                    return setCoord(coordinates, row + 1, col + 1, 5, comparisons);
                }
            }
        }

        // SEARCH NORTH-WEST
        for (int row = Grid_.length - 1; row >= patternSize - 1; row--){
            for (int col = Grid_[Grid_.length - 1].length -1 ; col >= patternSize - 1; col--){

                int j = 0;

                while ((j < patternSize) && (ignoreCaseCompare(Pattern_.get(j), Grid_[row - j][col - j]))){
                    j++;
                    comparisons++;
                }
                if (j < patternSize){
                    comparisons++;
                }
                if (j == patternSize){
                    return setCoord(coordinates, row + 1, col + 1, 6, comparisons);
                }
            }
        }

        // SEARCH NORTH-EAST
        for (int row = Grid_.length - 1; row >= patternSize - 1; row--){
            for (int col = 0; col <= (Grid_[0].length - patternSize); col++){

                int j = 0;

                while ((j < patternSize) && (ignoreCaseCompare(Pattern_.get(j), Grid_[row - j][col + j]))){
                    j++;
                    comparisons++;
                }
                if (j < patternSize){
                    comparisons++;
                }
                if (j == patternSize){
                    return setCoord(coordinates, row + 1, col + 1, 7, comparisons);
                }
            }
        }

        // SEARCH SOUTH-WEST
        for (int row = 0; row <= (Grid_.length - patternSize); row++){
            for (int col = Grid_[0].length-1; col >= patternSize - 1; col--){

                int j = 0;

                while ((j < patternSize) && (ignoreCaseCompare(Pattern_.get(j), Grid_[row + j][col - j]))){
                    j++;
                    comparisons++;
                }
                if (j < patternSize){
                    comparisons++;
                }
                if (j == patternSize){
                    return setCoord(coordinates, row + 1, col + 1, 8, comparisons);
                }
            }
        }

        return coordinates;
    }

    public static int[] setCoord(int coordinates_[], int row, int col, int dir, int comps){

        coordinates_[0] = row;
        coordinates_[1] = col;
        coordinates_[2] = dir;
        coordinates_[3] = comps;

        return coordinates_;
    }

    public static void printCoord(String word, int x, int y, int dir, int comps){

        Hashtable<Integer, String> directions = new Hashtable<>();
        directions.put(0, "was not found");
        directions.put(1, "East");
        directions.put(2, "West");
        directions.put(3, "South");
        directions.put(4, "North");
        directions.put(5, "South-East");
        directions.put(6, "North-West");
        directions.put(7, "North-East");
        directions.put(8, "South-West");

        if (dir == 0){
            System.out.println(word + " " + directions.get(dir));
        }
        else{
            System.out.println(word + " found at " + x + ", " + y + " and oriented " + directions.get(dir) + "; " + comps);
        }
    }

    /********************************************** MAIN FUNCTION *************************************** */

    public static void main(String[] args) throws IOException{

        File textFile = new File(args[0]);
        Scanner sc = new Scanner(System.in);
        ArrayList<Character> patToFind = new ArrayList<Character>();
        String word;
  
        char wordGrid[][];
        wordGrid = setUpGrid(textFile);

        if (args.length == 1){ // INTERACTIVE SEARCH
            
            System.out.println("Enter a word to search ('-1' to exit)");
            word = sc.nextLine();

            while (!word.equals("-1")){
                 
                setUpPattern(patToFind, word);
                int wordFoundAt[] = findWord(wordGrid, patToFind);
                printCoord(word.toUpperCase(), wordFoundAt[0], wordFoundAt[1], wordFoundAt[2], wordFoundAt[3]);
                patToFind.clear();
                word = sc.nextLine();
            }
            sc.close();
        }
        
        else{ // AUTOMATIC SEARCH
            File wordList = new File(args[1]);

            Scanner wordSC = new Scanner(wordList);

            while (wordSC.hasNextLine()){
                word = wordSC.nextLine();
                setUpPattern(patToFind, word);
                int wordFoundAt[] = findWord(wordGrid, patToFind);
                printCoord(word.toUpperCase(), wordFoundAt[0], wordFoundAt[1], wordFoundAt[2], wordFoundAt[3]);
                patToFind.clear();
            }
            wordSC.close();
        }
    }
}