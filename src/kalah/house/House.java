package kalah.house;



public class House {

    private  int num_0f_seeds = 4;

    public House() {
    }

    public House(int num_0f_seeds) {
        this.num_0f_seeds = num_0f_seeds;
    }


    public int getNum_0f_seeds() {
        return num_0f_seeds;
    }

    public void setNum_0f_seeds(int num_0f_seeds) {
        this.num_0f_seeds = num_0f_seeds;
    }

    @Override
    public String toString() {
        return String.valueOf(num_0f_seeds);
    }

    public void setNum_0f_seeds() {
         num_0f_seeds++;
    }
    public void setNum_0f_seeds_chizi(int duimian) {
        num_0f_seeds = num_0f_seeds+duimian;
    }
}
