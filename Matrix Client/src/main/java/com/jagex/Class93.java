package com.jagex;/* Class93 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import java.awt.Frame;

final class Class93 implements Interface23 {
	public int method254(int i) {
		Class298_Sub35 class298_sub35 = (Class298_Sub35) Class87.aClass437_793
				.get((long) i);
		if (class298_sub35 == null)
			return Class128.aClass148_6331.method252(i, (byte) 84);
		return class298_sub35.anInt7394 * -774922497;
	}

	public int method255(int i) {
		Class298_Sub35 class298_sub35 = (Class298_Sub35) Class87.aClass437_793
				.get((long) i);
		if (class298_sub35 == null)
			return Class128.aClass148_6331.method252(i, (byte) 1);
		return class298_sub35.anInt7394 * -774922497;
	}

	public int method252(int i, byte i_0_) {
		try {
			Class298_Sub35 class298_sub35 = (Class298_Sub35) Class87.aClass437_793
					.get((long) i);
			if (class298_sub35 == null)
				return Class128.aClass148_6331.method252(i, (byte) 21);
			return class298_sub35.anInt7394 * -774922497;
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder()
					.append("du.d(").append(')').toString());
		}
	}

	public int method251(int i) {
		Class298_Sub35 class298_sub35 = (Class298_Sub35) Class87.aClass437_793
				.get((long) i);
		if (class298_sub35 == null)
			return Class128.aClass148_6331.method252(i, (byte) 101);
		return class298_sub35.anInt7394 * -774922497;
	}

	public int method253(int i) {
		Class298_Sub35 class298_sub35 = (Class298_Sub35) Class87.aClass437_793
				.get((long) i);
		if (class298_sub35 == null)
			return Class128.aClass148_6331.method252(i, (byte) 98);
		return class298_sub35.anInt7394 * -774922497;
	}

	public int method250(int i, byte i_1_) {
		try {
			Class298_Sub35 class298_sub35 = ((Class298_Sub35) Class87.aClass437_793
					.get(0x100000000L | (long) i));
			if (class298_sub35 == null)
				return Class128.aClass148_6331.method250(i, (byte) 36);
			return -774922497 * class298_sub35.anInt7394;
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder()
					.append("du.u(").append(')').toString());
		}
	}

	public int method249(int i) {
		Class298_Sub35 class298_sub35 = ((Class298_Sub35) Class87.aClass437_793
				.get(0x100000000L | (long) i));
		if (class298_sub35 == null)
			return Class128.aClass148_6331.method250(i, (byte) 43);
		return -774922497 * class298_sub35.anInt7394;
	}

	static final void method1009(Class403 class403, int i) {
		try {
			if (null != client.aString8804)
				((Class403) class403).anObjectArray5240[((((Class403) class403).anInt5241 += 969361751) * -203050393) - 1] = Class227
						.method2114(client.aString8804, 864183945);
			else
				((Class403) class403).anObjectArray5240[((((Class403) class403).anInt5241 += 969361751) * -203050393) - 1] = "";
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder()
					.append("du.wy(").append(')').toString());
		}
	}

	public static void method1010(Class457 class457, Frame frame, int i) {
		try {
			class457.method5960();
			frame.setVisible(false);
			frame.dispose();
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder()
					.append("du.b(").append(')').toString());
		}
	}

	public static void method1011(byte i) {
		try {
			Class478.aClass453_6002 = new Class453();
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder()
					.append("du.a(").append(')').toString());
		}
	}

	public static void method1012(Class298_Sub50 class298_sub50, int i) {
		try {
			if (!Class435
					.method5804(client.anInt8752 * -1233866115, (byte) -26))
				class298_sub50.method3550(1342402184);
			else
				ClientScriptsExecutor.aClass374_Sub1_4125.method4620(class298_sub50,
						2143317873);
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder()
					.append("du.p(").append(')').toString());
		}
	}

	static final void findObjectRoute(int dstX, int dstY, long objdata) {
		try {
			int objtype = (int) objdata >> 14 & 0x1f;
			int objrot = (int) objdata >> 20 & 0x3;
			int objid = (int) (objdata >>> 32) & 0x7fffffff;
			GameObjectType typeDef = ((GameObjectType) Class422_Sub20.method5701(ExactStrategy.method4108(114624527), objtype, (byte) 2));
			PathStrategy strategy;
			if (GameObjectType.aClass424_6611 == typeDef	|| GameObjectType.aClass424_6604 == typeDef || GameObjectType.aClass424_6610 == typeDef) {
				ObjectDefinitions definitions = client.aClass283_8716.method2641(-1208362615).getObjectDefinitions(objid);
				int sizeX;
				int sizeY;
				if (0 == objrot || objrot == 2) {
					sizeX = -1125834887 * definitions.sizeX;
					sizeY = -565161399 * definitions.sizeY;
				} else {
					sizeX = -565161399 * definitions.sizeY;
					sizeY = definitions.sizeX * -1125834887;
				}
				if (objrot == 0) {
					/* empty */
				}
				strategy = Class336_Sub5.method4105(dstX, dstY, sizeX, sizeY, GameObjectType.aClass424_6614, 0, 1300552038);
			} else if (Class82_Sub9.isWall(-1976050083 * typeDef.anInt6613, (byte) 28))
				strategy = Class336_Sub5.method4105(dstX, dstY, 0, 0, typeDef, objrot, 740164949);
			else
				strategy = Class194.method1867(dstX, dstY, 0, 0, typeDef, objrot, (byte) -22);
			Class82_Sub21.method938(dstX, dstY, true, strategy, -1680742639);
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder().append("du.jd(").append(')').toString());
		}
	}

	public static boolean method1014(int i) {
		try {
			do {
				boolean bool;
				try {
					if (IcmpService_Sub1.available())
						break;
					bool = false;
				} catch (Throwable throwable) {
					return false;
				}
				return bool;
			} while (false);
			if (null != IcmpService_Sub1.anIcmpService_Sub1_8551)
				throw new IllegalStateException("");
			IcmpService_Sub1.anIcmpService_Sub1_8551 = new IcmpService_Sub1();
			Thread thread = new Thread(new Class393());
			thread.setDaemon(true);
			thread.start();
			return true;
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder()
					.append("du.k(").append(')').toString());
		}
	}
}
