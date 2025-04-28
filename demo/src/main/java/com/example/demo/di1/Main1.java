package com.example.demo.di1;

import java.io.FileReader;
import java.util.Properties;

//상속,클래스 타입변환, 오버라이딩은 기본으로 알고 있어야함
class Car{};
class SportCar extends Car{};
class Truck extends Car{};
class Engine{};

public class Main1 {
	public static void main(String[] args)throws Exception {
		//변경사항이 발생했을 때 타입과 생성자 부분을 모두 변경해줘야한다.
		//SportCar car = new SportCar();
		//Truck car = new Truck();
		
		//타입변환을 이용하면 수정을 해야할 곳이 적어진다
		//Car car = new SportCar();
		
		//Car car = new Truck();
		
		//별도의 메서드를 만들어서 객체를 생성하면 수정포인트를 더 줄일 수 있다.
		//사용하는 곳이 많아질수록 메서드로 객체를 생성하는것이 더 유리하다.
		Car car = (Car) getObject("sportcar");
		Car car2 = (Car) getObject("truck");
		Engine e = (Engine) getObject("engine");
		
	}
	
//	static Car getCar()throws Exception {
//		//txt파일을 읽어온다.
//		//java.util.Properties
//		//키와 값의 쌍으로 구성된 속성 목록을 관리할 때 사용
//		Properties p = new Properties();
//		p.load(new FileReader("config.txt"));
//		
//		//텍스트파일에서 car라는 key의 value를 찾아서 반환
//		Class clazz = Class.forName(p.getProperty("car"));
//		
//		return (Car)clazz.newInstance();
//	}
//
	
	static Object getObject(String key)throws Exception {
		//텍스트 파일을 읽어와서
		Properties p = new Properties();
		p.load(new FileReader("config.txt"));
		
		//인자로 받은 문자열을 가지고 있는 key를통해서 value를 읽어온다
		//value는 클래스 경로가 들어있다
		Class clazz = Class.forName(p.getProperty(key));
		
		
		//clazz에 담겨있는 경로를 통해 newInstance()로 객체 생성해서 반환
		return clazz.newInstance();
	}
}