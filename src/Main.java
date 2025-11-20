public class Main {
    public static void main(String[] args) {

        HumanBmi human = new HumanBmi(80, 1.52);

        System.out.println("BMI: " + human.getBmi());
        System.out.println("Status: " + human.getStatus());
    }
}
