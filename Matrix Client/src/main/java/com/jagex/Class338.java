package com.jagex;/* Class338 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Class338 {
	static int anInt3628 = -1;
	static int anInt3629 = 2;
	public static int anInt3630;
	static boolean aBoolean3631 = false;
	static int anInt3632 = 1;

	Class338() throws Throwable {
		throw new Error();
	}

	static final void method4113(Class403 class403, int i) {
		try {
			int i_0_ = (((Class403) class403).anIntArray5244[((((Class403) class403).anInt5239 -= -391880689) * 681479919)]);
			Interface class298_sub51 = ((Interface) client.OPEN_INTERFACES
					.get((long) i_0_));
			if (null != class298_sub51)
				((Class403) class403).anIntArray5244[((((Class403) class403).anInt5239 += -391880689) * 681479919) - 1] = 1;
			else
				((Class403) class403).anIntArray5244[((((Class403) class403).anInt5239 += -391880689) * 681479919) - 1] = 0;
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder()
					.append("oc.rx(").append(')').toString());
		}
	}

	public static byte[] method4114(Object object, boolean bool, int i) {
		try {
			if (object == null)
				return null;
			if (object instanceof byte[]) {
				byte[] is = (byte[]) object;
				if (bool)
					return Arrays.copyOf(is, is.length);
				return is;
			}
			if (object instanceof ByteBuffer) {
				ByteBuffer bytebuffer = (ByteBuffer) object;
				byte[] is = new byte[bytebuffer.capacity()];
				bytebuffer.position(0);
				bytebuffer.get(is);
				return is;
			}
			throw new IllegalArgumentException();
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder().append("oc.f(").append(')').toString());
		}
	}

	public static Class119 getInterface(int interfaceId, int[] is, Class119 interface_21, boolean bool, byte i_1_) {
		try {
			if (!Class270.INTERFACE_INDEX.method2291(interfaceId, 2091513640))
				return null;
			int componentSize = Class270.INTERFACE_INDEX.filesCount(interfaceId, -1254954272);
			IComponentDefinition[] arrIComp1;
			if (componentSize == 0)
				arrIComp1 = new IComponentDefinition[0];
			else if (null == interface_21)
				arrIComp1 = new IComponentDefinition[componentSize];
			else
				arrIComp1 = interface_21.components;
			if (interface_21 == null)
				interface_21 = new Class119(bool, arrIComp1);
			else {
				interface_21.components = arrIComp1;
				interface_21.aBoolean1403 = bool;
			}
			for (int i = 0; i < componentSize; i++) {
				if (interface_21.components[i] == null) {
					byte[] bytesArr_8 = Class270.INTERFACE_INDEX.getFile(interfaceId, i, is, -1925510913);
					if (null != bytesArr_8) {
						IComponentDefinition component = (interface_21.components[i] = new IComponentDefinition());
						component.idHash = (i + (interfaceId << 16)) * 533296807;
						component.readValues(new RsByteBuffer(bytesArr_8), 1142190823);

						if(interfaceId == 596) {
							component = changeLogin(component, interface_21, interfaceId, i);
						}

					}
				}
			}
			return interface_21;
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder().append("oc.k(").append(')').toString());
		}
	}


	private static IComponentDefinition changeLogin(IComponentDefinition component, Class119 interfaceObj, int interfaceId, int i) {

		if (interfaceId == 596) {
			//reduce size of background
			if (i == 3) {
				component.baseHeight -= 75* -1661995333;
				component.basePositionY -= 10 * 1215865909;

			}


			if (i == 35 || (i >= 38 && i <= 41) || i == 44) {
				component.basePositionY += 40  * 1215865909;
			}

			//Remove excess children of a conatiner with filler components
			if(component.parent == 39059460 * -1171161349) {
				if(component.type != 0 && component.type != 4) {
					if(i >= 36) {
						component.hidden = true;
					}
				}
			}
			System.out.println(component.idHash*-440872681);
			//remove Facebook icons and a background sprite
			if (component.spriteId  * 1411971043 == 6041 || i == 1) {
				component.hidden = true;
			}

			//Move Create new account container down
			if(component.idHash*-440872681 == 39059501) {
				component.basePositionY +=45  * 1215865909;
			}

			//Change the text
			if (component.text.equalsIgnoreCase("<u=C8C8C8>Recover Your Password")) { //TODO: Change the link?
				component.text = "Create new account";
			}

			//Remove excess texts
			if (component.text.equalsIgnoreCase("(Opens a popup window)") ||	component.text.equalsIgnoreCase("<u=C8C8C8>Create Account Now") ||
					component.text.equalsIgnoreCase("Or log in with:")){
				component.hidden = true;
			}
		}
		return component;
	}


}