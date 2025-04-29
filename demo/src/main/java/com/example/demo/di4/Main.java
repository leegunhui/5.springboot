package com.example.demo.di4;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.google.common.reflect.ClassPath;

//자동차는 엔진과 문을 필요로한다!
@Component class Car{
	@Autowired
	Engine engine;
	
	@Autowired
	Door door;
	
	@Autowired
	Wheel wheel;
	
	@Override
	public String toString() {
		return "Car [engine=" + engine + ", door=" + door + ", wheel = " + wheel + "]";
	}	
};

@Component class SportCar extends Car{};
@Component class Truck extends Car{};
@Component class Engine {};
@Component class Door{};
@Component class Wheel{};

class AppContext{
	Map map; //객체 저장소
	
	public AppContext() {
		map = new HashMap();
		doComponentScan();
		doAutowired();
		//구아바 라이브러리 다운로드하기!
		
	}
	//map에 저장된 객체의 변수중 @Autowired가 붙어있으면
	//타입에 맞는 객체를 찾아서 연결한다
	
	private void doAutowired() {
		//map에 저장된 객체의 객체변수중에 @Autowired가 붙어 있으면
		//객체변수의 타입에 맞는 객체를 찾아서 연결(객체의 주소를 객체변수에 저장)
		
		try {
			//맵에 들어있는 객체를 하나씩 꺼내서
		for(Object bean : map.values()) {
			//getClass()로 클래스 정보를 얻어오고
			//getDeclaredFields()로 해당 클래스에 있는 필드의 정보들을 배열로 반환
			for(Field fld : bean.getClass().getDeclaredFields()) {
				//필드에 Autowired 어노테이션이 붙어있는지 확인하고
				if(fld.getAnnotation(Autowired.class)!=null) {
					//그 필드에 맞는 객체가 있으면 세팅을 해라
					fld.set(bean, getBean(fld.getType()));
				}
			}
		}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	//패키지에 클래스를 모두 순화하면서 @Component 어노테이션이 붙은 클래스를
//	Map에 객체로 등록을 한다
//	@componentScan이 아래 함수의 역할을 한다
	
	
	private void doComponentScan() {
		
		try {
		ClassLoader classLoader = AppContext.class.getClassLoader();
		ClassPath classPath = ClassPath.from(classLoader);
		
		//패키지 di4로 변경해주기
		Set<ClassPath.ClassInfo> set = classPath.getTopLevelClasses("com.example.demo.di4");
		
		for(ClassPath.ClassInfo classInfo : set) {
			Class clazz = classInfo.load();
			Component component = (Component)clazz.getAnnotation(Component.class);
			if(component != null) {
				String id = StringUtils.uncapitalize(classInfo.getSimpleName());
				map.put(id, clazz.newInstance());
			}
		}
		} catch(Exception e) {
			
		}
		
	}
	
	Object getBean(String key) {
		return map.get(key);
	}
	
	Object getBean(Class clazz) {
		for(Object obj : map.values()) {
			if(clazz.isInstance(obj)) {
				return obj;
			}
		}
		return null;
	}
}

public class Main {
	
	public static void main(String[] args)throws Exception {
		AppContext ac = new AppContext();
		
		//car 객체에 필드로 engine과 wheel을 갖는다
		Car car = (Car)ac.getBean("car"); //byName으로 객체를 검색
		
		Engine engine = (Engine)ac.getBean("engine");
		Wheel wheel = (Wheel)ac.getBean("wheel");
		Door door = (Door)ac.getBean(Door.class);
		
		//이부분을 주석처리하고 다시 실행하면 객체 변수에 대입이 안되기 때문에
		//car에 null값이 뜰것이다.
		//원래 자바에서는 필드에 직접 객체를 넣어줘야한다
//		car.engine = engine;
//		car.wheel = wheel;
//		car.door = door;
		
		System.out.println("car= " + car);
		System.out.println("engine= " + engine);
		System.out.println("door= " + door);
		System.out.println("wheel= " + wheel);
		
		
	}
}

/*
JavaApplication으로 실행하기
결과
car= Car [engine=com.korea.dependency.di4.Engine@371a67ec, door=com.korea.dependency.di4.Door@5ed828d]
engine= com.korea.dependency.di4.Engine@371a67ec
door= com.korea.dependency.di4.Door@5ed828d
*/