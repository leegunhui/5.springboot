package com.example.demo.di5;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
	public static void main(String[] args) {
		//왜 객체 컨테이너라고 하냐
		//내부에서 Map으로 클래스를 읽어와서 객체를 value로 저장하기 때문
		//스프링 부트에서 제공하는 객체 컨테이너 클래스가 있다.
		//"com.example.demo.di5" 패키지 하위에 있는 클래스중 어노테이션 붙은것들
		//메모리에 객체좀 올려줘
		//new AnnotationConfigApplicationContext("com.example.demo.di5");
		//우리가 이전에 만들었던 AppContext의 역할을 한다
		ApplicationContext ac = new AnnotationConfigApplicationContext("com.example.demo.di5");
		Car car = (Car)ac.getBean(Car.class);
		Engine engine = (Engine)ac.getBean(Engine.class);
		Wheel wheel = (Wheel)ac.getBean(Wheel.class);
		
		System.out.println("car="+car);
		System.out.println("engine=" + engine);
		System.out.println("wheel="+wheel);
		
		System.out.println(car);
	}
}
