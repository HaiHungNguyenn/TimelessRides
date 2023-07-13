package com.duy.carshowroomdemo.util;

import com.duy.carshowroomdemo.dto.CarDto;
import com.duy.carshowroomdemo.entity.Client;
import com.duy.carshowroomdemo.entity.ClientNotification;
import com.duy.carshowroomdemo.service.Service;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.swing.text.MaskFormatter;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Util {

    Service service = new Service();



    static Random random = new Random();
    static BCryptPasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
    static Lorem lorem = new LoremIpsum();
    static final String DATA_SRC_PATH = "src\\main\\java\\com\\duy\\carshowroomdemo\\util\\car_data.txt";
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH = 6;


    public static String getRandPhone(){
        String phone = "09";


        for (int i = 0; i < 8; i++) {
            phone += random.nextInt(10);
        }

        return phone;
    }

    public static String getRandEmail() {
//        String[] s = name.split(" ");
//        String[] d = dob.toString().split("-");
//        return s[2] + s[0].charAt(0) + s[1].charAt(0) + d[2] + d[1] + "@gmail.com";
        return lorem.getEmail();
    }

    public static String getRandGender(){
        return (random.nextInt(2) == 0) ? "male":"female";
    }

    public static int getRandInt(int upperBound) {
        return random.nextInt(upperBound);
    }

    public static String getRandName() {
//        String[] lastNames = {"Nguyen", "Cao", "Doan", "Dang", "Le", "Tran", "Pham", "Duong", "Dinh", "Ha"};
//        String[] middleNames = {"Quoc", "Huynh", "Hoang", "Thanh", "Binh", "Huu", "Hong", "Thi", "Ngoc", "Minh"};
//        String[] firstNames = {"Thai", "Kiet", "Khoi", "Huan", "Long", "Nhat", "Dat", "Tri", "Quan", "Khang"};
//        return lastNames[getRandInt(lastNames.length)] + " " + middleNames[getRandInt(middleNames.length)] + " " + firstNames[getRandInt(firstNames.length)];
        return lorem.getName();
    }

    public static String getRandColor() {
        String[] colors = {"red", "blue", "yellow", "white", "black", "brown", "violet", "emerald", "green", "pink"};
        return colors[getRandInt(colors.length)];
    }

    public static String getRandFuelType() {
        String[] fuelTypes = {"Diesel", "Gasoline", "Bio-diesel", "Ethanol"};
        return fuelTypes[getRandInt(fuelTypes.length)];
    }

    public static String getRandCarName() {
        String[] carNames = {"BOLT EV", "TRAX", "TRAILBLAZER", "EQUINOX", "BLAZER", "TRAVERSE", "TAHOE", "2024 Mustang", "Escape", "Bronco Sport", "Explorer", "Expedition"};
        return carNames[getRandInt(carNames.length)];
    }

    public static String getRandBrand() {
        String[] brands = {"Chevy", "Ford", " Volkswagen Beetel", "Cadilac", "Corolla", "Herbie", "", "Mitsubishi", "Camry", "Alfa Romeo", "Toyota", "Ferrari", "Kia"};
        return brands[Util.getRandInt(brands.length)];
    }

    public static Long getRandPrice() {
        return (getRandInt(4500) + 500) * 1000000L;
    }

    public static int getRandInt(int lowerBound, int upperBound) {
        return getRandInt(upperBound - lowerBound) + lowerBound;
    }

    public static LocalDate getRandBirthDay(){
        LocalDate date1 = LocalDate.of(1980, 1, 1);
        LocalDate date2 = LocalDate.of(2000, 1, 1);

        long randomEpochDay = ThreadLocalRandom.current().longs(date1.toEpochDay(), date2.toEpochDay()).findAny().getAsLong();
        return LocalDate.ofEpochDay(randomEpochDay);
    }

    public static LocalDate getRandDate(LocalDate date1, LocalDate date2){
        long randomEpochDay = ThreadLocalRandom.current().longs(date1.toEpochDay(), date2.toEpochDay()).findAny().getAsLong();
        return LocalDate.ofEpochDay(randomEpochDay);
    }

    public static String encodePassword(String rawPW){
        return passwordEncoder.encode(rawPW);
    }

    public static boolean isValidPW(String rawPW, String encodedPW){
        return passwordEncoder.matches(rawPW, encodedPW);
    }

    public static String getRandText(int wordCount){
        return lorem.getWords(wordCount);
    }

    public static String getRandLicensePlate() {
        StringBuilder text = new StringBuilder();
        text.append(getRandInt(11, 99));
        text.append((char) (getRandInt(1, 7) + 65));
        for (int i = 0; i < 5; i++) {
            text.append(getRandInt(9));
        }
        return text.toString();
    }



    public static String getRandAddress() {
        return getRandInt(1,500) + " " + lorem.getCity() + " " + lorem.getStateFull() + " " + lorem.getCountry();
    }

    public static void setupImageGallery(int cars) {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(DATA_SRC_PATH))){
            List<String> dirNames = new ArrayList<>();
            int carCount = 0;
            String line;
            String imageRootDir = "D:/Image_Gallery/";
            Path path = Paths.get(imageRootDir);
            if(!Files.exists(path)){
                Files.createDirectory(path);
            }
            String currentDir = "";
            int imageCount = 1;
            while((line = bufferedReader.readLine()) != null){
                if(line.contains("START:")){

                    System.out.println("Downloading folder started!");

                } else if(line.contains("Car name:")){


                    String directory = handleDirName(line.replace("Car name:", "").trim());
                    if(dirNames.contains(directory)){
                        currentDir = null;
                        continue;
                    }
                    dirNames.add(directory);
                    currentDir = imageRootDir + directory;
                    Files.createDirectory(Paths.get(currentDir));
                    carCount++;
                    if(carCount > cars){
                        break;
                    }

                } else if(line.contains("link")){

                    if(currentDir != null){

                        String imageName = "carvago" + (imageCount++) + ".jpg";
                        downloadImage(handleImageTag(line), currentDir + "/" + imageName);

                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String handleImageTag(String line){
        int start = line.indexOf("src=\"") + "src=\"".length();
        int end = line.length() - 3;
        String substring = line.substring(start, end);
        return substring.replaceAll("amp;", "");
    }

    public static String handleDirName(String dirName){
        if(dirName.contains("/")){
            return dirName.replaceAll("\\W","");
        }
        return dirName;
    }

    public static int handleInteger(String src){
        return Integer.parseInt(src.replaceAll("\\D", ""));
    }

    public static void downloadImage(String remoteUrl, String path){
        try {
            URL url = new URL(remoteUrl);
            InputStream inputStream = url.openStream();
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            byte[] buffer = new byte[2048];
            int length = 0;
            while((length = inputStream.read(buffer)) != -1){
                fileOutputStream.write(buffer, 0, length);
            }
            fileOutputStream.close();
            inputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String getRandCity() {
        return lorem.getCity();
    }

    public static Long calculateTotal(Long price, String tax) {
        StringBuilder taxNumber = new StringBuilder();

        for (char c: tax.toCharArray()) {
            if(Character.isDigit(c)){
                taxNumber.append(c);
            }
        }
        return price + price * (Long.parseLong(taxNumber.toString()) / 100);
    }
    public static LocalDate parseLocalDate(String dateString) {
        if (dateString.isBlank()){
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
        return LocalDate.parse(dateString, formatter);
    }
    public static LocalTime parseLocalTime(String timeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return LocalTime.parse(timeString, formatter);
    }
    public static String[] splitDateTimeString(String dateTimeString) {
        // Remove the parentheses and leading/trailing whitespace
        String cleanString = dateTimeString.replace("(", "").replace(")", "").trim();

        // Split the string using space as the delimiter
        String[] parts = cleanString.split(" ");

        // Extract the date, time, and time zone
        String date = parts[1] + " " + parts[2] + " " + parts[3];
        String time = parts[4];
        String timeZone = parts[5];

        return new String[]{date, time, timeZone};
    }


    public static String formatVndPrice(Long price) {
        String priceString = NumberFormat.getCurrencyInstance(Locale.JAPAN).format(price);
        return priceString.substring(1) + " vnd";
    }

    public static String formatPhone(String phone) {
        try {
            MaskFormatter maskFormatter = new MaskFormatter("###-###-####");
            maskFormatter.setValueContainsLiteralCharacters(false);
            return maskFormatter.valueToString(phone);
        } catch (ParseException e) {
            return "";
        }
    }


    public static String generateRandomString() {
        StringBuilder sb = new StringBuilder(LENGTH);
        Random random = new Random();

        for (int i = 0; i < LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }

    public static String getRandPW(){
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }

    public static List<String> getMakeList(){
        String defaultText = """
                Acura,
                Alfa Romeo,
                Aston Martin,
                Audi,
                Bentley,
                BMW,
                Bugatti,
                Buick,
                Cadillac,
                Chevrolet,
                Chrysler,
                Citroen,
                Dodge,
                Ferrari,
                Fiat,
                Ford,
                GMC,
                Honda,
                Hyundai,
                Infiniti,
                Jaguar,
                Jeep,
                Kia,
                Lamborghini,
                Land Rover,
                Lexus,
                Lincoln,
                Maserati,
                Mazda,
                McLaren,
                Mercedes-Benz,
                Mini,
                Mitsubishi,
                Nissan,
                Porsche,
                Ram,
                Rolls-Royce,
                Subaru,
                Tesla,
                Toyota,
                Volkswagen,
                Volvo.""";

        List<String> result = new ArrayList<>();
        String[] brands = defaultText.split(",");
        Arrays.stream(brands).toList().forEach(x -> result.add(x.trim().replaceAll("\\W", "")));
        return result;
    }

    public static List<String> getModelList() {
        String defaultText = """
                21. Toyota RAV4
                22. Honda CR-V
                23. Ford Explorer
                24. Chevrolet Equinox
                25. BMW X5
                26. Audi A3
                27. Mercedes-Benz GLC
                28. Nissan Sentra
                29. Jeep Cherokee
                30. Subaru Impreza
                31. Volkswagen Passat
                32. Hyundai Tucson
                33. Kia Sorento
                34. Mazda CX-9
                35. Tesla Model X
                36. Porsche Macan
                37. Land Rover Defender
                38. Lexus NX
                39. Volvo S60
                40. Jaguar XF
                41. Toyota Highlander
                42. Honda Pilot
                43. Ford Escape
                44. Chevrolet Traverse
                45. BMW X3
                46. Audi Q7
                47. Mercedes-Benz GLE
                48. Nissan Maxima
                49. Jeep Renegade
                50. Subaru Legacy
                51. Volkswagen Tiguan
                52. Hyundai Santa Fe
                53. Kia Soul
                54. Mazda MX-5 Miata
                55. Tesla Model Y
                56. Porsche Panamera
                57. Land Rover Discovery Sport
                58. Lexus GX
                59. Volvo V60
                60. Jaguar F-Type
                61. Toyota Tacoma
                62. Honda HR-V
                63. Ford Edge
                64. Chevrolet Tahoe
                65. BMW 7 Series
                66. Audi A5
                67. Mercedes-Benz GLA
                68. Nissan Murano
                69. Jeep Compass
                70. Subaru Crosstrek
                71. Volkswagen Atlas
                72. Hyundai Kona
                73. Kia Forte
                74. Mazda CX-30
                75. Tesla Cybertruck
                76. Porsche 718 Cayman
                77. Land Rover Range Rover Sport
                78. Lexus IS
                79. Volvo XC40
                80. Jaguar I-PACE
                81. Toyota 4Runner
                82. Honda Odyssey
                83. Ford Expedition
                84. Chevrolet Suburban
                85. BMW X6
                86. Audi Q3
                87. Mercedes-Benz GLB
                88. Nissan Pathfinder
                89. Jeep Gladiator
                90. Subaru Ascent
                91. Volkswagen Arteon
                92. Hyundai Palisade
                93. Kia Stinger
                94. Mazda6
                95. Tesla Roadster
                96. Porsche Cayman
                97. Land Rover Range Rover Evoque
                98. Lexus LS
                99. Volvo S90
                100. Jaguar XJ
                """;
        List<String> result = new ArrayList<>();
        String[] split = defaultText.split("[\\d]{2,3}.");
        Arrays.stream(split).toList().forEach(x -> result.add(x.trim()));
        return result;
    }

    public static List<String> getBodyTypes() {
        String defaultText = """
                1. Sedan
                2. Hatchback
                3. Coupe
                4. Convertible
                5. Station Wagon
                6. SUV (Sport Utility Vehicle)
                7. Crossover
                8. Minivan
                9. Pickup Truck
                10. Van
                11. MPV (Multi-Purpose Vehicle)
                12. Estate
                13. Roadster
                14. Sports Car
                15. Supercar
                16. Limousine
                17. Cabriolet
                18. Compact
                19. Microcar
                20. Off-Road Vehicle
                22. Electric Vehicle (EV)
                23. Hybrid
                24. Plug-in Hybrid (PHEV)
                25. Hydrogen Fuel Cell Vehicle
                """;
        List<String> result = new ArrayList<>();
        String[] bodyTypes = defaultText.split("\\d{1,2}.");
        Arrays.stream(bodyTypes).toList().forEach(x -> result.add(x.trim()));
        return result;
    }

    public static String formatUSDPrice(Long price) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(price);
    }

    public static Map<String, Long> processPriceRange(String priceRange){
        String[] parts = priceRange.split("-");
        Map<String, Long> result = new HashMap<>();
        result.put("lower", Long.parseLong(parts[0].replaceAll("\\D", "")));
        result.put("upper", Long.parseLong(parts[1].replaceAll("\\D", "")));
        return result;
    }
}
