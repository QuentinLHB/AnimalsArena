package game;

import Animal.Creation.Concrete.Animal;

import java.io.*;
import java.util.ArrayList;

public class Serialization {
    private static String FILEPATH = "tmp/custom.ser";

    private static boolean saveCustomAnimals(ArrayList<Animal> animals){
        boolean ok = true;

        ObjectOutputStream oos = null;
        try{
            File fichier =  new File(FILEPATH);
            try{
                fichier.getParentFile().mkdirs();
                fichier.createNewFile();

            }catch (Exception e){
                ok = false;
            }
            oos =  new ObjectOutputStream(new FileOutputStream(fichier)) ;
            oos.writeObject(animals) ;
        }catch (Exception e){
            ok = false;
        }finally {
            try {
                if (oos != null) {
                    oos.flush();
                    oos.close();
                }
            } catch (final IOException ex) {
                ok = false;
            }
        }
        return ok;
    }

    static ArrayList<Animal> loadAnimals(){
        ArrayList<Animal> animals = new ArrayList<>();
        File fichier =  new File(FILEPATH) ;

        ObjectInputStream ois = null;
        try{
            ois =  new ObjectInputStream(new FileInputStream(fichier)) ;
            // désérialization de l'objet
            animals = (ArrayList<Animal>)ois.readObject() ;
        }catch (Exception e){

        }finally {
            try{
                ois.close();
            }catch(Exception e){
            }
        }
        return animals;
    }

    static void addAnimalToSave(Animal animal){
        ArrayList<Animal> animals = loadAnimals();
        animals.add(animal);
        saveCustomAnimals(animals);
    }

    static void removeAnimalFromSave(Animal animal){
        ArrayList<Animal> animals = loadAnimals();
        try{
            animals.remove(animals);
        }catch (Exception e){
            e.printStackTrace();
        }
        saveCustomAnimals(animals);
    }

    static void clearSave(){
        ArrayList<Animal> animals = loadAnimals();
        animals.clear();
        saveCustomAnimals(animals);
    }

    public static boolean isSaveEmpty(){
        return loadAnimals().isEmpty();
    }
}
