import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

class ContinuousBackpack {
    private int weightBag;
    private final Item[] items;
    public ContinuousBackpack(int weightBag, Item[] items) {
        this.weightBag = weightBag;
        this.items = items;
    }

    public Item[] run(){
        Arrays.sort(items, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                double r1 = (double) o1.getWeight() / o1.getCost();
                double r2 = (double) o2.getWeight() / o2.getCost();
                if(r1 > r2){
                    return 1;
                }
                else if(r1 < r2){
                    return -1;
                }
                return 0;
            }
        });
        double result = 0;
        Item[] itemsBefore = new Item[items.length];
        int resultWeight = 0;
        for(int i = 0; i < items.length; i++){
            if(items[i].getWeight() <= weightBag){
                result += items[i].getCost();
                weightBag -= items[i].getWeight();
                itemsBefore[i] = new Item(items[i].getName(), items[i].getCost(), items[i].getWeight());
                resultWeight += items[i].getWeight();
            }
        }
        System.out.print("Максимальня стоимость - " + result + " Максимальный вес - " + resultWeight);
        return itemsBefore;
    }
}

class Item{
    private final int cost;
    private final int weight;
    private final String name;
    public Item(String name, int cost, int weight) {
        this.cost = cost;
        this.weight = weight;
        this.name = name;
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
}
