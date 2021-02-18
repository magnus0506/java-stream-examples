import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class JavaStreams {

    public static void main(String[] args) throws IOException {
        integerStream();
        integerStreamSkip();
        integerStreamSum();
        streamOf();
        arrayStream();
        integerStreamAvg();
        streamList();
        streamFromTextFile();
        streamFromTextFileSave();
        streamFromCSV();
        streamFromCSVParse();
        streamFromCSVHashMap();
        streamReduction();
        streamReductionSummary();
    }

    //1. Integer Stream
    static void integerStream() {
        IntStream
                .range(1, 10)
                .forEach(System.out::print);
        System.out.println();
    }

    //2. Integer Stream med skip, den springer alle elementer før skip funktionen over
    static void integerStreamSkip() {
        IntStream
                .range(1, 10)
                .skip(5)
                .forEach(System.out::print);
        System.out.println();
    }

    //3. Integer Stream der lægger alle tallene sammen og printer sum
    static void integerStreamSum() {
        System.out.println(
                IntStream
                        .range(1, 5)
                        .sum());
    }

    //4. Stream.of, sorted og findFirst. sorted sorterer streamen i alfabetisk rækkefølge og findFirst finder den tidligste i alfabetet
    static void streamOf() {
        Stream.of("Magnus", "Asger", "Mikkel")
                .sorted()
                .findFirst()
                .ifPresent(System.out::println);
    }

    //5. Stream fra array, med sort, filter og print. filtrerer streamen så den kun finder dem der starter med F og sorterer dem
    static void arrayStream() {
        String[] names = {"Magnus", "Asger", "Mikkel", "Emil", "Frederik", "Peter", "Lisbeth", "Benedicte", "Lucas"};
        Arrays.stream(names)
                .filter(x -> x.startsWith("F"))
                .sorted()
                .forEach(System.out::println);

    }

    //6. Gennemsnit af en integer array. map(x->x*x) ganger hver element med sig selv, average finder så gennemsnittet af mappet (summen).
    static void integerStreamAvg() {
        Arrays.stream(new int[]{2, 4, 6, 8, 10})
                .map(x -> x * x)
                .average()
                .ifPresent(System.out::println);
    }

    //7. Stream fra List, filter og print. mapper arrayen og converter alle til lowercase, finder alle med l og printer dem
    static void streamList() {
        List<String> people = Arrays.asList("Magnus", "Asger", "Mikkel", "Emil", "Frederik", "Peter", "Lisbeth", "Benedicte", "Lucas");
        people
                .stream()
                .map(String::toLowerCase)
                .filter(x -> x.startsWith("l"))
                .forEach(System.out::println);
    }

    //8. Stream fra tekst fil, sorterer, filtrerer så den kun finder linjer med mere end 13 chars og printer dem
    static void streamFromTextFile() throws IOException {
        Stream<String> bands = Files.lines(Paths.get("bands.txt"));
        bands
                .sorted()
                .filter(x -> x.length() > 13)
                .forEach(System.out::println);
        bands.close();
    }

    //9. Stream fra tekst fil og gem til en List
    static void streamFromTextFileSave() throws IOException {
        List<String> bands = Files.lines(Paths.get("bands.txt"))
                .filter(x -> x.contains("thy"))
                .collect(Collectors.toList());
        bands.forEach(x -> System.out.println(x));

    }

    //10. Stream fra CSV fil og count. count tæller hvor mange rows der er som ikke indeholder mellemrum
    static void streamFromCSV() throws IOException {
        Stream<String> rows = Files.lines(Paths.get("bands.txt"));
        int rowCount = (int)rows
                .map(x -> x.split(" "))
                .filter(x -> x.length == 2)
                .count();
        System.out.println(rowCount + " rows.");
        rows.close();
    }

    //11. Stream fra CSV fil, parse til int og print de rækker der er højere end 100
    static void streamFromCSVParse() throws IOException {
        Stream<String> rows = Files.lines(Paths.get("numbers.txt"));
        rows
                .map(x -> x.split(","))
                .filter(x -> x.length == 2)
                .filter(x -> Integer.parseInt(x[1]) > 100)
                .forEach(x -> System.out.print(x[0] + "=" + x[1] + " "));
        rows.close();
    }

    //12. Stream fra CSV fil, og gem i HashMap
    static void streamFromCSVHashMap() throws IOException {
        Stream<String> rows = Files.lines(Paths.get("numbers.txt"));
        Map<String, Integer> map;
        System.out.println();
        map = rows
                .map(x -> x.split(","))
                .filter(x -> x.length == 2)
                .filter(x -> Integer.parseInt(x[1]) > 100)
                .collect(Collectors.toMap(
                        x -> x[0],
                        x -> Integer.parseInt(x[1])));
        rows.close();
        for (String key : map.keySet()){
            System.out.print(key + "=" + map.get(key) + " ");
        }
    }

    //13. Reduction - sum
    static void streamReduction(){
        System.out.println();
        double total = Stream.of(7.3, 1.5, 4.8)
                .reduce(0.0, (Double a, Double b) -> a + b);
        System.out.println("Total = " + total);
    }

    //14. Reduction - summary (virker kun for integer), viser count, sum, min, average, max af int streamen
    static void streamReductionSummary(){
        IntSummaryStatistics summary = IntStream.of(7,2,19,88,73,4,10)
                .summaryStatistics();
        System.out.println(summary);
    }
}