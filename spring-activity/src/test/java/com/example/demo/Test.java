package com.example.demo;

public class Test {

	public static void main(String[] args) {
			//与或非更快
			short aShort=3;
			short bShort=4;
			System.out.println("aShort="+aShort+" bShort="+bShort);
			aShort^=bShort;
			bShort^=aShort;
			aShort^=bShort;
			System.out.println("aShort="+aShort+" bShort="+bShort);
			System.out.println("---------------------------------");
			
			
			byte aByte=1;
			byte bByte=2;
			System.out.println("aByte="+aByte+" bByte="+bByte);
			aByte^=bByte;
			bByte^=aByte;
			aByte^=bByte;
			System.out.println("aByte="+aByte+" bByte="+bByte);
			//这个更简单一点
			int a=2;
			int b=3;
			a=a+b;
			b=a-b;
			a=a-b;
			System.out.print(a+":"+b);


	}

}
