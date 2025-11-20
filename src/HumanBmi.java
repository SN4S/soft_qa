public class HumanBmi {

    private double weight;  // in kg
    private double height;  // in meters

    public HumanBmi(double weight, double height) {
        setWeight(weight);
        setHeight(height);
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        if (weight <= 0) {
            throw new IllegalArgumentException("Weight must be positive.");
        }
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        if (height <= 0) {
            throw new IllegalArgumentException("Height must be positive.");
        }
        this.height = height;
    }

    /**
     * Computes BMI = weight / (height^2)
     */
    public double getBmi() {
        return weight / (height * height);
    }

    /**
     * Returns a text status based on BMI index.
     */
    public String getStatus() {
        double bmi = getBmi();

        if (bmi < 18.5) return "Deficit";
        else if (bmi < 25) return "Norm";
        else if (bmi < 30) return "Warning!";
        else return "Fat";
    }
}
