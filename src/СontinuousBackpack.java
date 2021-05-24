import java.util.*;

class ContinuousBackpack {
    private int weightBag;
    private final ArrayList<Item> items;

    public ContinuousBackpack(int weightBag, ArrayList<Item> items) {
        this.weightBag = weightBag;
        this.items = items;
    }

    public ArrayList<Item> run(){
        ArrayList<Item> itemsBefore = new ArrayList<>();
        sortItems();
        for (Item item : items) {
            if (item.getWeight() <= weightBag) {
                weightBag -= item.getWeight();
                itemsBefore.add(new Item(item.getName(), item.getCost(), item.getWeight()));
            }
        }
        return itemsBefore;
    }
    public void sortItems(){
        Collections.sort(items);
    }
}

class Item implements Comparable<Item>{
    private final int cost;
    private final int weight;
    private final String name;
    private final double ratioWeightToPrice;
    public Item(String name, int cost, int weight) {
        this.name = name;
        this.cost = cost;
        this.weight = weight;
        this.ratioWeightToPrice = (double) weight / cost;
    }

    @Override
    public String toString() {
        return "Item{" +
                "cost=" + cost +
                ", weight=" + weight +
                ", name='" + name + '\'' +
                ", ratioWeightToPrice=" + ratioWeightToPrice +
                '}';
    }

    public int getCost() {
        return cost;
    }

    public int getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }

    public double getRatioWeightToPrice() {
        return ratioWeightToPrice;
    }

    @Override
    public int compareTo(Item o) {
        double r1 = o.getRatioWeightToPrice();
        double r2 = getRatioWeightToPrice();
        if(r1 < r2){
            return 1;
        }
        else if(r1 > r2){
            return -1;
        }
        return 0;
    }
}

