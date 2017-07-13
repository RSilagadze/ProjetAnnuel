package utils;

/**
 * Created by Romaaan on 28/06/2017.
 */
public class DataBase {

    @Override
    public String toString() {
        return this.name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public DataBase(String name){
        this.name = name;
    }

    public static class Table {

        @Override
        public String toString() {
            return this.name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String name;

        public Table(String name){
            this.name = name;
        }
    }
    
}
