package com.typehunter;

import javax.sound.sampled.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Animal<Word> {
    private static String animalConstructor ="animalConstructor.txt";
    private final String name;
    private String imageFile;   // pulls from file/path
    private String soundFile;   // pulls from file/path
    private String wordCount;
    //private final List<String> words = new ArrayList<>();
    // NOTE1: Do we need int size for number or words per animal or String[] with a size of... Line 17-18
    private String[] wordsArray = new String[0];
    private int size = 0;
    private final Scanner scanner = new Scanner(System.in);
    //C:\StudentWork\MiniProject\animal_resources

    private Animal(String name, int size, String imageFile, String soundFile) {
        this.name = name;
        this.size = size;
        this.imageFile = imageFile;
        this.soundFile = soundFile;
    }

    public static List<Animal> createAnimalFromFile(String filePath) {
        List<Animal> animals = new ArrayList<>();
        try (BufferedReader animalTextFile = new BufferedReader(new FileReader(animalConstructor))) {
            String line;
            while ((line = animalTextFile.readLine()) !=null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    String name = data[0].trim();
                    int size = Integer.parseInt(data[1].trim());
                    String imageFile = data[2].trim();
                    String soundFile = data[3].trim();
                    Animal animal = new Animal(name, size, imageFile, soundFile);
                    animals.add(animal);
                } else {
                    System.out.println("Invalid line/data" + line);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return animals;
    }

    // if/else statement if array.length == 1 , animal is BLANK, if == 2, animal NEXT_BLANK,
    // if animal is BLANK, pull BLANK.txt and BLANK.sound and assign to image and sound
    // BLANK is assigned to name, size is assigned from location enum
    // getWordCount()

    // @TODO: Pull method from Location enum
    public String getName() {
        return name;
    }

    // @TODO: set a file path, create local path and provide file
    // Reads image from text file
    public String displayImage() {
        try (BufferedReader reader = new BufferedReader(new FileReader(imageFile))) {

            while ((imageFile = reader.readLine()) != null) {
                return imageFile;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile;
    }


    // TODO: ADD actual file path
    // Reads .wav file for sound clip of animal. I
    public static void playSound(String filePath) {
        try {
            File soundFile = new File(filePath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();

        } catch(UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();

        }
    }

//    public String getSound() {
//        return sound;
//    }

    // @TODO: Pull method from Location enum
    public int getSize() {
        return size;
    }

    // After words have been pulled from text file. This will assign to animals
    // TODO: change variables to reflect those above
    public String getWordCount() {
        List<String> animalWords = List.of(new String[wordsArray.length]); // needs to be given from Location enum
        int index = 0;
        for (String word : wordsArray) {
            animalWords.set(index, word);
            index++;
        }
        for (String word : wordsArray) {
            System.out.println(word);
            int deleteIndex = wordsArray.length;
            if (deleteIndex >= 0 && deleteIndex < animalWords.size()) {
                animalWords.remove(deleteIndex);
            }
        }
        return wordCount;
    }

    // Reading from the file to get words
    // TODO: change variables to reflect those above
    public String[] getWordFile() {
        List<String> words = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader("words.txt"))) {
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] wordsArray = new String[0];
        Collections.shuffle(words);
        wordsArray = words.toArray(wordsArray);
        return wordsArray;
    }

    // This checks if the user input matches the word. Check Prompter class from Jay?
    // TODO: change variables to reflect those above
    private String calculateHit() {
        int wordIndex = 0;
        int givenIndex = size; // @TODO this would be the number/size/wordCount from location enum, change accordingly
        wordCount = wordsArray[wordIndex];
        boolean validInput = false;
        while (!validInput) {
            System.out.println(wordCount);
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.matches(String.valueOf(wordCount))) {
                validInput = false;
                wordIndex++;
            }
            if (wordIndex >= givenIndex) {
                validInput = true;
            }
        }
        return wordCount;
    }
}