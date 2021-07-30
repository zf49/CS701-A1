package kalah.store;

public class Store {
    private int storeSeeds;

    public Store() {
    }

    public Store(int storeSeeds) {
        this.storeSeeds = storeSeeds;
    }

    public int getStoreSeeds() {
        return storeSeeds;
    }

    public void setStoreSeeds(int storeSeeds) {
        this.storeSeeds = storeSeeds;
    }

    public void addStoreSeeds() {
        storeSeeds++;
    }

    public void getOppositeSeeds(int seeds){
        storeSeeds += seeds;
    }


}
