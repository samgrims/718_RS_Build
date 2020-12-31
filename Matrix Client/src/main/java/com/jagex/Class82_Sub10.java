package com.jagex;/* Class82_Sub10 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

public class Class82_Sub10 extends Class82 {
	public void method868() {
		Class107.method1144(true, (short) 3546);
	}

	public void method866(int i) {
		try {
			Class107.method1144(true, (short) 12990);
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder()
					.append("xv.f(").append(')').toString());
		}
	}

	public void method869() {
		Class107.method1144(true, (short) 8961);
	}

	Class82_Sub10(RsByteBuffer class298_sub53) {
		super(class298_sub53);
	}

	static final void method903(int i, int i_0_, int i_1_) {
		try {
			if (Class378.method4671(i, null, -1864950961))
				Class170.method1812(
						(Class389.aClass119Array4165[i].components),
						i_0_, (byte) 65);
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder()
					.append("xv.lv(").append(')').toString());
		}
	}

	static void initSizes(IComponentDefinition iCompDef, int i_1, int i_2_, boolean bool, byte i_3_) {
		try {
			int i_4_ = -2093041337 * iCompDef.width;
			int i_5_ = iCompDef.height * 457937409;
			if (iCompDef.aspectWidthType == 0)
				iCompDef.width = iCompDef.baseWidth * -86683899;
			else if (1 == iCompDef.aspectWidthType)
				iCompDef.width = -614915977	* (i_1 - 1769572195 * iCompDef.baseWidth);
			else if (2 == iCompDef.aspectWidthType)
				iCompDef.width = -614915977	* (i_1 * (1769572195 * iCompDef.baseWidth) >> 14);
			if (0 == iCompDef.aspectHeightType)
				iCompDef.height = -472364941 * iCompDef.baseHeight;
			else if (1 == iCompDef.aspectHeightType)
				iCompDef.height = (i_2_ - iCompDef.baseHeight * -1747263885) * -67604991;
			else if (iCompDef.aspectHeightType == 2)
				iCompDef.height = -67604991	* (i_2_ * (-1747263885 * iCompDef.baseHeight) >> 14);
			if (iCompDef.aspectWidthType == 4)
				iCompDef.width = -614915977	* (iCompDef.aspectX * 1183580231 * (iCompDef.height * 457937409) / (iCompDef.aspectY * 1289873889));
			if (iCompDef.aspectHeightType == 4)
				iCompDef.height = -67604991	* (-2093041337 * iCompDef.width	* (1289873889 * iCompDef.aspectY) / (1183580231 * iCompDef.aspectX));
			if (client.aBoolean8846	&& (client.getIComponentSettings(iCompDef).settingsHash * -1266165749 != 0 || iCompDef.type * -1215239439 == 0)) {
				if (iCompDef.height * 457937409 < 5	&& -2093041337 * iCompDef.width < 5) {
					iCompDef.height = -338024955;
					iCompDef.width = 1220387411;
				} else {
					if (457937409 * iCompDef.height <= 0)
						iCompDef.height = -338024955;
					if (iCompDef.width * -2093041337 <= 0)
						iCompDef.width = 1220387411;
				}
			}
			if (iCompDef.anInt1145 * 907611645 == IComponentDefinition.anInt1269 * -451364727)
				client.GAME_SCREENINTERFACE = iCompDef;
			if (bool && iCompDef.anObjectArray1164 != null && (i_4_ != iCompDef.width * -2093041337 || 457937409 * iCompDef.height != i_5_)) {
				Class298_Sub46 class298_sub46 = new Class298_Sub46();
				class298_sub46.aClass105_7525 = iCompDef;
				class298_sub46.anObjectArray7530 = iCompDef.anObjectArray1164;
				client.aClass453_8893.method5935(class298_sub46, 528123519);
			}
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder().append("xv.kj(").append(')').toString());
		}
	}

	static void method905(Class403 class403, byte i) {
		try {
			((Class403) class403).anIntArray5244[681479919 * ((Class403) class403).anInt5239 - 2] = (Class316.aClass362_3318
					.method4307(
							(((Class403) class403).anIntArray5244[((Class403) class403).anInt5239 * 681479919 - 2]),
							245040087).anIntArrayArray4017[(((Class403) class403).anIntArray5244[((Class403) class403).anInt5239 * 681479919 - 1])][0]);
			((Class403) class403).anInt5239 -= -391880689;
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder()
					.append("xv.s(").append(')').toString());
		}
	}
}
