package org.example.testdata;

public final class UserRepository {

    private UserRepository() {}

    public static UsersData getDefault() {
        return  getValid();
    }

    public static UsersData getValid() {
        return new UsersData()
                .setEmail("testtestovich611@gmail.com")
                .setPassword("!Testtestovich611!")
                //.setPassword(System.getenv("USER_PASSWORD"))
                .setUsername("Grisha");
    }

    public static UsersData getUnregistered() {
        return new UsersData()
                .setEmail("someemailone@gmail.com")
                .setPassword("!Passwordone212");
    }

    /*
    public static List<User> fromList() {
        List<User> lst = new ArrayList<>();
        lst.add(getValidUserQwertyY());
        lst.add(getValidUserMyName());
        return lst;
    }

    public static List<User> fromCsv(String filename) {
        return User.getByLists(new CSVReader(filename).getAllCells());
    }

    public static List<User> fromCsv() {
        return fromCsv("users.csv");
    }

    public List<User> fromExcel(String filename) {
        return User.getByLists(new ExcelReader(filename).getAllCells());
    }

    public List<User> fromExcel() {
        return fromExcel("users.xlsx");
    }
    */
}   