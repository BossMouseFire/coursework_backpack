import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

class ContinuousBackpack {
    public static void main(String[] args) {
        ContinuousBackpack task = new ContinuousBackpack();
        task.run();
    }
    public void run(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите количество элементов: ");
        int amount = sc.nextInt();
        Item [] items = new Item [amount];
        for (int i = 0; i < amount; i++){
            System.out.print("Введите цену, вес предмета №" + (i+ 1) + "\n");
            int cost = sc.nextInt();
            int weight = sc.nextInt();
            items[i] = new Item(cost, weight);
        }
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
        System.out.print("Введите размер рюкзака: ");
        int W = sc.nextInt();
        double result = 0;
        for(Item item : items){
            if(item.getWeight() <= W){
                result += item.getCost();
                W -= item.getWeight();
            }
            else{
                result += (double) item.getCost() * W / item.getWeight();
            }
        }
        for (int i = 0; i < amount; i++){
            System.out.print(items[i] + "\n");
        }
        System.out.print("Максимальня стоимость - " + result);
    }
}

class Item{
    private final int cost;
    private final int weight;

    public Item(int cost, int weight) {
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
}
