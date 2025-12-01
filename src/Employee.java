
public class Employee {
    private String id;
    private String name;
    private String position;

    // Hàm khởi tạo (Constructor)
    public Employee(String id, String name, String position) {
        this.id = id;
        this.name = name;
        this.position = position;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
