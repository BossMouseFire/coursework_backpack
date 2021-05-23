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
        Collections.sort(items);
        for (Item item : items) {
            if (item.getWeight() <= weightBag) {
                weightBag -= item.getWeight();
                itemsBefore.add(new Item(item.getName(), item.getCost(), item.getWeight()));
            }
        }
        return itemsBefore;
    }
}

class Item implements Comparable<Item>{
    private final int cost;
    private final int weight;
    private final String name;
    public Item(String name, int cost, int weight) {
        this.name = name;
        this.cost = cost;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Item{" +
                "cost=" + cost +
                ", weight=" + weight +
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


    @Override
    public int compareTo(Item o) {
        double r1 = (double) o.getWeight() / o.getCost();
        double r2 = (double) getWeight() / getCost();
        if(r1 < r2){
            return 1;
        }
        else if(r1 > r2){
            return -1;
        }
        return 0;
    }
}

