package com.othr.vs.rmi.immatrikulation.server;

import java.io.Serializable;

public class Student implements Serializable {
    private long matrikelNr;
    private String name;
    private int ects;

    public Student(String name) {
        this.name = name;
        this.ects = 0;
    }

    public long getMatrikelNr() {
        return matrikelNr;
    }

    public void setMatrikelNr(long matrikelNr) {
        this.matrikelNr = matrikelNr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEcts() {
        return ects;
    }

    public void setEcts(int ects) {
        this.ects = ects;
    }

    @Override
    public String toString() {
        return "Student{" +
                "matrikelNr=" + matrikelNr +
                ", name='" + name + '\'' +
                ", ects=" + ects +
                '}';
    }

}
