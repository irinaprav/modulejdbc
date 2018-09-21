package com.module.alevel;

import java.util.Objects;

public class SchoolClass {

    private Long id;
    private String className;
    private Long amount;
    private String classTeacher;

    public SchoolClass(){

    }

    public SchoolClass(Long id, String className, Long amount, String classTeacher) {
        this.id = id;
        this.className = className;
        this.amount = amount;
        this.classTeacher = classTeacher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SchoolClass)) return false;
        SchoolClass that = (SchoolClass) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(className, that.className) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(classTeacher, that.classTeacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, className, amount, classTeacher);
    }

    @Override
    public String toString() {
        return "SchoolClass{" +
                "id=" + id +
                ", className='" + className + '\'' +
                ", amount=" + amount +
                ", classTeacher='" + classTeacher + '\'' +
                '}';
    }



    public void setId(Long id) {
        this.id = id;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public void setClassTeacher(String classTeacher) {
        this.classTeacher = classTeacher;
    }

    public Long getId() {
        return id;
    }

    public String getClassName() {
        return className;
    }

    public Long getAmount() {
        return amount;
    }

    public String getClassTeacher() {
        return classTeacher;
    }



}
