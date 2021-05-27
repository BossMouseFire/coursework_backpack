import java.util.ArrayList;

public class TestingProduct {
    public static void main(String[] args) {
        int weightBag = 1000000;

        for(long listsize = 1000; listsize <= 100000000; listsize*=10) {
            ArrayList<Item> arrayList = new ArrayList<>();
            for(int i = 0; i < listsize; i++){
                int cost = rnd(20, 1500);
                int weight = rnd(20, 1500);
                arrayList.add(new Item("" + i, cost, weight));
            }
            ContinuousBackpack continuousBackpack = new ContinuousBackpack(weightBag, arrayList);

            long start = System.currentTimeMillis();
            continuousBackpack.run();
            long usedBytes = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
            System.out.println("v3 List(" + listsize + ") add to head " + (double) (System.currentTimeMillis() - start) / 10000);
            System.out.print((double) usedBytes / 1048576 + "\n");
        }
    }
    public static int rnd(int min, int max)
    {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }
}
