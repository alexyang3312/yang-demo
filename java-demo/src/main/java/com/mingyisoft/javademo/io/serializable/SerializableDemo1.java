package com.mingyisoft.javademo.io.serializable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SerializableDemo1 {
	public static void main(String[] args) {
		Teacher t = new Teacher();
		t.setName("alex");
		t.setAge(18);
		
		Student s1 = new Student();
		s1.setName("张三555");
		s1.setSex('5');
		s1.setYear(5);
		s1.description = "xxx";
		//嵌套对象
		s1.setTeacher(t);
		
		Student s2 = new Student();
		s2.setName("李四555");
		s2.setSex('6');
		s2.setYear(6);
		s2.description = "666";
		//嵌套对象
		s2.setTeacher(t);
		
		try {
			// Student对象序列化过程，使用ObjectOutputStream
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("student.ser"));
			oos.writeObject(s1);
			oos.writeObject(s2);
			oos.writeObject(t);
			s1.setName("王二麻子");
			oos.writeObject(s1);
			
			Student2 s3 = new Student2();
			s3.setYear(33);
			s3.setGpa(33);
			oos.writeObject(s3);
			
			
			oos.flush();
			oos.close();

			// Student对象反序列化过程，使用ObjectInputStream
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("student.ser"));
			Student ss1 = (Student) ois.readObject();
			System.out.println("name = " + ss1.getName());
			System.out.println("sex = " + ss1.getSex());
			System.out.println("year = " + ss1.getYear());
			System.out.println("gpa = " + ss1.getGpa());
			System.out.println("description = " + ss1.description);
			System.out.println("t.name = " + ss1.getTeacher().getName());
			System.out.println("t.age = " + ss1.getTeacher().getAge());
			
			Student ss2 = (Student) ois.readObject();
			System.out.println("name = " + ss2.getName());
			System.out.println("sex = " + ss2.getSex());
			System.out.println("year = " + ss2.getYear());
			System.out.println("gpa = " + ss2.getGpa());
			System.out.println("description = " + ss1.description);
			System.out.println("t.name = " + ss2.getTeacher().getName());
			System.out.println("t.age = " + ss2.getTeacher().getAge());
			
			Teacher tt = (Teacher) ois.readObject();
			System.out.println(tt.getName()+" "+tt.getAge());
			/**
			 * 所有采用序列化机制的Java对象都有一个序列化编号（不同于序列化版本号），
			 * 当程序尝试序列化一个对象时，先检查当前对象是否已经被序列化过，只有从未序列化（在本次JVM进程）过的对象，
			 * 系统才会将该对象转换成字节序列，如果当前对象已经被序列化过，则自动使用其序列化编号而不是重新
			 */
			System.out.println(ss1.getTeacher()==ss2.getTeacher()); // true
			System.out.println(ss1.getTeacher()==tt); // true
			System.out.println(ss2.getTeacher()==tt); // true
			
			Student ss3 = (Student) ois.readObject();
			/**
			 * 即使属性改变，也不会序列化成新属性。内存里只有1个s1对象，并且s1的属性不能再被改变。
			 */
			System.out.println("应该是王二麻子吗->"+ss3.getName());
			/**
			 * 自定义序列化，返回集合
			 */
			List returnList = (List) ois.readObject();
			for (Object obj : returnList) {
				System.out.println(obj);
			}
			ois.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class Student extends Person implements Serializable{
	private String name;
	/**
	 * 不进行序列化transient关键字，transient只能修饰属性
	 */
	private transient char sex;
	private int year;
	private double gpa;
	
	private Teacher teacher;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public char getSex() {
		return sex;
	}
	public void setSex(char sex) {
		this.sex = sex;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public double getGpa() {
		return gpa;
	}
	public void setGpa(double gpa) {
		this.gpa = gpa;
	}
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
}

class Teacher implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1053051757102136827L;
	private String name;
	private Integer age;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
	private void writeObject(ObjectOutputStream out) throws IOException {
		System.out.println("自定义写出");
		//反转一下名字
		out.writeObject(new StringBuffer(name).reverse());
		out.writeInt(age);
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		System.out.println("自定义读入");
		//反转一下名字
		this.name = (((StringBuffer)in.readObject()).reverse()).toString();
		this.age = in.readInt();
	}
}

class Student2 extends Person implements Serializable{
	private String name;
	/**
	 * 不进行序列化transient关键字，transient只能修饰属性
	 */
	private transient char sex;
	private int year;
	private double gpa;
	
	private Teacher teacher;
	
	private Object writeReplace() {
		List<Object> returnList = new ArrayList<Object>();
		returnList.add(year);
		returnList.add(gpa);
		return returnList;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public char getSex() {
		return sex;
	}
	public void setSex(char sex) {
		this.sex = sex;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public double getGpa() {
		return gpa;
	}
	public void setGpa(double gpa) {
		this.gpa = gpa;
	}
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
}

class Person implements Serializable{
	protected String description;
}