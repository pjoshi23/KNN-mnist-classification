import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Classifier {
    private ArrayList<DataPoint> trainingData;
    private int k;

    public Classifier(int k) {
        this.k = k;
        trainingData = new ArrayList<DataPoint>();
    }

    public void addTrainingData(List<DataPoint> points) {
        trainingData.addAll(points);
    }

    public void addTrainingData(DataPoint point) {
        trainingData.add(point);
    }

    public String classify(double[] featureVector) {
        if (trainingData.size() == 0) return "no training data";

        double min = this.distance(featureVector, trainingData.get(0).getData());
        int minIndex = 0;
        for (int i = 1; i < trainingData.size(); i++) {
            if (this.distance(featureVector, trainingData.get(i).getData()) < min) {
                min = this.distance(featureVector, trainingData.get(i).getData());
                minIndex = i;
            }
        }

        return trainingData.get(minIndex).getLabel();
    }

    public double distance(double[] d1, double[] d2) {
        double sum = 0;
        for (int i = 0; i < d1.length; i++) {
            sum += (d1[i] - d1[i]) * (d1[i] - d1[i]);
        }

        return Math.pow(sum, 1/d1.length);
    }

    public void test(List<DataPoint> test) {
        ArrayList<DataPoint> correct = new ArrayList<>();
        ArrayList<DataPoint> wrong = new ArrayList<>();

        int i = 0;
        for (DataPoint p : test) {
            String predict = classify(p.getData());
            System.out.print("#" + i + " REAL:\t" + p.getLabel() + " predicted:\t" + predict);
            if (predict.equals(p.getLabel())) {
                correct.add(p);
                System.out.print(" Correct ");
            } else {
                wrong.add(p);
                System.out.print(" WRONG ");
            }

            i++;
            System.out.println(" % correct: " + ((double) correct.size() / i));
        }

        System.out.println(correct.size() + " correct out of " + test.size());
        System.out.println(wrong.size() + " wrong out of " + test.size());
        System.out.println("% Error: " + (double) wrong.size() / test.size());
    }
}
