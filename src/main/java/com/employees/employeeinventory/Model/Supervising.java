package com.employees.employeeinventory.Model;


import lombok.Data;

@Data
public class Supervising {

    public Supervising(int id, String first_name, String last_name, int supervisor, int supervisor_id) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.supervisor = supervisor;
        this.supervisor_id = supervisor_id;
    }

    private int id;

    private String first_name;

    private String last_name;

    private String dob;

    private int ssn;

    private String start_date;

    private int salary;

    private int supervisor;

    private int supervisor_id;


    @Override
    public String toString() {
        return "Supervising{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", dob='" + dob + '\'' +
                ", ssn=" + ssn +
                ", start_date='" + start_date + '\'' +
                ", salary=" + salary +
                ", supervisor=" + supervisor +
                ", supervisor_id=" + supervisor_id +
                '}';
    }
}
