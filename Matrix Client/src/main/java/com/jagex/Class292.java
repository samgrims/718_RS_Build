package com.jagex;/* Class292 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

public class Class292 {
	public static Class292 aClass292_3162 = new Class292();
	static Class292 aClass292_3163 = new Class292();
	static Class292 aClass292_3164 = new Class292();

	Class292() {
		/* empty */
	}

	static final void method2812(Class403 class403, int i) {
		try {
			int i_0_ = (((Class403) class403).anIntArray5244[((((Class403) class403).anInt5239 -= -391880689) * 681479919)]);
			IComponentDefinition class105 = Class50.getIComponentDefinitions(i_0_, (byte) -102);
			Class119 class119 = Class389.aClass119Array4165[i_0_ >> 16];
			Class522.method6326(class105, class119, class403, -1946261030);
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder()
					.append("md.hk(").append(')').toString());
		}
	}

	static final void method2813(Class403 class403, int i) {
		try {
			int i_1_ = (((Class403) class403).anIntArray5244[((((Class403) class403).anInt5239 -= -391880689) * 681479919)]);
			((Class403) class403).anIntArray5244[((((Class403) class403).anInt5239 += -391880689) * 681479919 - 1)] = (Class298_Sub32_Sub14.aClass477_9400
					.getItemDefinitions(i_1_).anInt5772) * 1625363587;
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder()
					.append("md.aax(").append(')').toString());
		}
	}

	static final void method2814(Class403 class403, int i) {
		try {
			int i_2_ = (((Class403) class403).anIntArray5244[((((Class403) class403).anInt5239 -= -391880689) * 681479919)]);
			NPC class365_sub1_sub1_sub2_sub1 = ((NPC) ((Class403) class403).aClass365_Sub1_Sub1_Sub2_5242);
			int i_3_ = class365_sub1_sub1_sub2_sub1.method4462(i_2_,
					-1877195973);
			int i_4_ = class365_sub1_sub1_sub2_sub1
					.method4466(i_2_, (byte) -50);
			((Class403) class403).anIntArray5244[((((Class403) class403).anInt5239 += -391880689) * 681479919 - 1)] = i_3_;
			((Class403) class403).anIntArray5244[((((Class403) class403).anInt5239 += -391880689) * 681479919 - 1)] = i_4_;
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder()
					.append("md.apt(").append(')').toString());
		}
	}

	public static Class481 method2815(String string, int i, int i_5_) {
		try {
			Class481_Sub1 class481_sub1 = new Class481_Sub1();
			((Class481) class481_sub1).aString6034 = string;
			((Class481) class481_sub1).anInt6033 = 1609563993 * i;
			return class481_sub1;
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder()
					.append("md.a(").append(')').toString());
		}
	}

	static void method2816(IComponentDefinition icompdef_0, int i_1, int i_2, byte i_7_) {
		try {
			if (icompdef_0.aspectXType == 0)
				icompdef_0.x = -1672688609 * icompdef_0.basePositionX;
			else if (icompdef_0.aspectXType == 1)
				icompdef_0.x = (-1014229119 * (icompdef_0.basePositionX * 1499181983 + (i_1 - -2093041337 * icompdef_0.width) / 2));
			else if (2 == icompdef_0.aspectXType)
				icompdef_0.x = -1014229119 * (i_1 - icompdef_0.width * -2093041337 - 1499181983 * icompdef_0.basePositionX);
			else if (icompdef_0.aspectXType == 3)
				icompdef_0.x = (i_1 * (1499181983 * icompdef_0.basePositionX) >> 14) * -1014229119;
			else if (4 == icompdef_0.aspectXType)
				icompdef_0.x = -1014229119 * ((i_1 - -2093041337 * icompdef_0.width) / 2 + (i_1	* (1499181983 * icompdef_0.basePositionX) >> 14));
			else
				icompdef_0.x = (i_1 - -2093041337 * icompdef_0.width - (1499181983 * icompdef_0.basePositionX * i_1 >> 14)) * -1014229119;
			if (icompdef_0.aspectYType == 0)
				icompdef_0.y = 705123139 * icompdef_0.basePositionY;
			else if (icompdef_0.aspectYType == 1)
				icompdef_0.y = ((icompdef_0.basePositionY * -901738979 + (i_2 - 457937409 * icompdef_0.height) / 2) * 1145252063);
			else if (icompdef_0.aspectYType == 2)
				icompdef_0.y = (i_2 - 457937409 * icompdef_0.height - -901738979 * icompdef_0.basePositionY) * 1145252063;
			else if (3 == icompdef_0.aspectYType)
				icompdef_0.y = 1145252063 * (icompdef_0.basePositionY * -901738979 * i_2 >> 14);
			else if (icompdef_0.aspectYType == 4)
				icompdef_0.y = (((-901738979 * icompdef_0.basePositionY * i_2 >> 14) + (i_2 - 457937409 * icompdef_0.height) / 2) * 1145252063);
			else
				icompdef_0.y = 1145252063 * (i_2 - icompdef_0.height * 457937409 - (icompdef_0.basePositionY * -901738979 * i_2 >> 14));
			if (client.aBoolean8846	&& (client.getIComponentSettings(icompdef_0).settingsHash * -1266165749 != 0 || 0 == icompdef_0.type * -1215239439)) {
				if (icompdef_0.x * 1354508417 < 0)
					icompdef_0.x = 0;
				else if ((icompdef_0.x * 1354508417 + -2093041337 * icompdef_0.width) > i_1)
					icompdef_0.x = -1014229119 * (i_1 - -2093041337 * icompdef_0.width);
				if (icompdef_0.y * -749038817 < 0)
					icompdef_0.y = 0;
				else if ((icompdef_0.y * -749038817 + 457937409 * icompdef_0.height) > i_2)
					icompdef_0.y = 1145252063 * (i_2 - 457937409 * icompdef_0.height);
			}
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder().append("md.li(").append(')').toString());
		}
	}

	public static void method2817(int i) {
		try {
			for (Class298_Sub49 class298_sub49 = ((Class298_Sub49) Class423.aClass437_5354.method5816(2004354413)); null != class298_sub49; class298_sub49 = ((Class298_Sub49) Class423.aClass437_5354
					.method5815((byte) -69))) {
				if (!((Class298_Sub49) class298_sub49).aBoolean7594)
					Class285.method2710((((Class298_Sub49) class298_sub49).anInt7589) * 1566028323, (byte) 49);
				else
					((Class298_Sub49) class298_sub49).aBoolean7594 = false;
			}
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder()
					.append("md.u(").append(')').toString());
		}
	}
}
