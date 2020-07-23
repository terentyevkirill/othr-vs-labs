package cafeteria.repeat;

public class Cafeteria {
    public static void main(String[] args) throws InterruptedException {
        Theke theke = new Theke(4);
        int kellnerAnzahl = 2;
        int studentenAnzahl = 8;
        for (int i = 1; i <= kellnerAnzahl; i++) {
            Thread kellner = new Thread(new Kellner("Kellner-" + i, theke));
            kellner.setDaemon(true);
            kellner.start();
        }
        for (int i = 1; i <= studentenAnzahl; i++) {
            Thread student = new Thread(new Student("Student-" + i, theke));
            student.setDaemon(true);
            student.start();
        }

        Thread.sleep(30000);
        System.out.println("Ende der Simulation");
    }
}
