package lab3.dataModel;

import java.text.DecimalFormat;

public class Student {

    private String name, marjor;
    private double mark;

    public Student(String name, double mark, String marjor) {
        this.name = name;
        this.mark = mark;
        this.marjor = marjor;
    }

    public Student() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMark() {
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(mark));
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public String getMarjor() {
        return marjor;
    }

    public void setMarjor(String marjor) {
        this.marjor = marjor;
    }

    public String getAcademic() {
        if (this.mark < 3) {
            return "Kém";
        }
        if (this.mark < 5) {
            return "Yếu";
        }
        if (this.mark < 6.5) {
            return "Trung bình";
        }
        if (this.mark < 7.5) {
            return "Khá";
        }
        if (this.mark < 9) {
            return "Giỏi";
        }
        return "Xuất sắc";
    }

    public boolean isBonus() {
        return this.mark >= 7.5;
    }
}
