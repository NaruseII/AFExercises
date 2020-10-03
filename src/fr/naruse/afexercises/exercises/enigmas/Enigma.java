package fr.naruse.afexercises.exercises.enigmas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Enigma {
    public static HashMap<EnigmaType, List<Enigma>> ENIGMAS_MAP = new HashMap<>();
    private static final Random RANDOM = new Random();

    static {
        List<Enigma> mathsEnigmas = new ArrayList<>();
        mathsEnigmas.add(new Enigma("Guillaume est avec un alpiniste confirmé. Celui-ci s’apprête à faire une ascension avec l’aide de porteurs. Le parcourt représente 6 jours de marche mais Guillaume, aussi bien que chacun des porteurs qu’il pourrait engager, ne peur porter que la quantité de nourriture nécessaire a un homme pour 4 jours. De combien de porteurs Guillaume a-t-il besoin ?",
                "2"));
        mathsEnigmas.add(new Enigma("La vitesse aéro indiquée doit être augmentée de 2% par 300m afin d’obtenir la vitesse aéro vraie. Si la vitesse aéro indiquée à 3000m est de 250km/h, quelle est la vitesse aéro vraie ?",
                "300"));

        ENIGMAS_MAP.put(EnigmaType.MATH, mathsEnigmas);
    }

    public static Enigma getRandomEnigma(){
        return getRandomEnigma(null);
    }

    public static Enigma getRandomEnigma(EnigmaType type){
        if(type == null){
            type = EnigmaType.values()[RANDOM.nextInt(EnigmaType.values().length)];
        }
        return ENIGMAS_MAP.get(type).get(RANDOM.nextInt(ENIGMAS_MAP.get(type).size()));
    }

    private String statement;
    private String response;
    public Enigma(String statement, String response) {
        this.statement = statement;
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public String getStatement() {
        return statement;
    }
}
