package kalah.house;



public class House {

    //every house has 4 seeds
    private  int num0fSeeds = 4;

    public House() {
    }

    public House(int num_0f_seeds) {
        this.num0fSeeds = num_0f_seeds;
    }

    public int getNum0fSeeds() {
        return num0fSeeds;
    }

    public void setNum0fSeeds(int num_0f_seeds) {
        this.num0fSeeds = num_0f_seeds;
    }

    @Override
    public String toString() {
        return String.valueOf(num0fSeeds);
    }

    public void addNum0fSeeds() {
        num0fSeeds++;
    }

}
