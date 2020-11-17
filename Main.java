import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
public class Main {
public static void main(String[] args) throws FileNotFoundException {
	File csvFile = new File("/Users/muskaanisrani/Downloads/players_homeruns.csv");
	Scanner sc = new Scanner(csvFile);

//Construct a tree map to map from string(playername)to int(home run total)
RedBlackTreeMap<String, Integer> rbTree = new RedBlackTreeMap<>();

//read first 5 line & insert players into map
for (int i = 0; i < 5; i++){
	String line = sc.nextLine();
//seperate name and home run total, then store in map
String[] seperate = line.split(",", 2);
int hrTotal = Integer.parseInt(seperate[1]);
rbTree.add(seperate[0], hrTotal);
}

//Test 1
System.out.println("Test 1: adding first 5 lines to the map");
rbTree.printStructure();
System.out.println();


//read next 5 line & insert players into map
for (int i = 0; i < 5; i++){
String line = sc.nextLine();
//seperate name and home run total, then store in map
String[] seperate = line.split(",", 2);
int hrTotal = Integer.parseInt(seperate[1]);
rbTree.add(seperate[0], hrTotal);
}

//Test 2
System.out.println("Test 2: adding next 5 lines to the map");
rbTree.printStructure();
System.out.println();


//Find method Test
System.out.println("Find method tests on four different keys in the tree");

System.out.println("Leaf key: Hank Aaron: " + rbTree.find("Hank Aaron"));
System.out.println("Root key: Honus Wagner: " + rbTree.find("Honus Wagner"));
System.out.println("Key with one NIL child & one non-NIl child: Rogers Hornsby: " + rbTree.find("Rogers Hornsby"));
System.out.println("Red node key: Stan Musial: " + rbTree.find("Stan Musial"));
System.out.println();

//Final test
System.out.println("Final Test");

//read and add remaining of the file to the tree
while (sc.hasNext()){
String line = sc.nextLine();

//seperate name and home run total, then store in map
String[] seperate = line.split(",", 2);
int hrTotal = Integer.parseInt(seperate[1]);

rbTree.add(seperate[0], hrTotal);
}

//now verify that the find methods still work
System.out.println("Leaf key: Hank Aaron: " + rbTree.find("Hank Aaron"));
System.out.println("Root key: Honus Wagner: " + rbTree.find("Honus Wagner"));
System.out.println("Key with one NIL child & one non-NIl child: Rogers Hornsby: " + rbTree.find("Rogers Hornsby"));
System.out.println("Red node key: Stan Musial: " + rbTree.find("Stan Musial"));
System.out.println();

}
}
