
/* Class393 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import java.awt.Canvas;

final class Class393 implements Runnable {
    @Override
    public void run() {
	try {
	    try {
		IcmpService_Sub1.anIcmpService_Sub1_8551.run();
	    }
	    catch (Throwable throwable) {
		/* empty */
	    }
	    IcmpService_Sub1.anIcmpService_Sub1_8551 = null;
	}
	catch (RuntimeException runtimeexception) {
	    throw Class346.method4175(runtimeexception, new StringBuilder().append("qg.run(").append(')').toString());
	}
    }

    static final void method4890(Class403 class403, int i) {
	try {
	    Class343_Sub1 class343_sub1 = Class365_Sub1_Sub5_Sub2.method4537(-1844616011);
	    if (class343_sub1 != null) {
		class403.anIntArray5244[((class403.anInt5239 += -391880689) * 681479919) - 1] = class343_sub1.anInt7717 * -15394297;
		class403.anIntArray5244[((class403.anInt5239 += -391880689) * 681479919) - 1] = -877023375 * class343_sub1.anInt3670;
		class403.anObjectArray5240[((class403.anInt5241 += 969361751) * -203050393) - 1] = class343_sub1.aString7719;
		Class353 class353 = class343_sub1.method4163(275518730);
		class403.anIntArray5244[((class403.anInt5239 += -391880689) * 681479919) - 1] = class353.anInt3820 * 1675394033;
		class403.anObjectArray5240[((class403.anInt5241 += 969361751) * -203050393) - 1] = class353.aString3819;
		class403.anIntArray5244[((class403.anInt5239 += -391880689) * 681479919) - 1] = class343_sub1.anInt3666 * -945794709;
		class403.anIntArray5244[((class403.anInt5239 += -391880689) * 681479919) - 1] = class343_sub1.anInt7720 * 512449113;
		class403.anObjectArray5240[((class403.anInt5241 += 969361751) * -203050393) - 1] = class343_sub1.aString7718;
	    } else {
		class403.anIntArray5244[((class403.anInt5239 += -391880689) * 681479919) - 1] = -1;
		class403.anIntArray5244[((class403.anInt5239 += -391880689) * 681479919) - 1] = 0;
		class403.anObjectArray5240[((class403.anInt5241 += 969361751) * -203050393) - 1] = "";
		class403.anIntArray5244[((class403.anInt5239 += -391880689) * 681479919) - 1] = 0;
		class403.anObjectArray5240[((class403.anInt5241 += 969361751) * -203050393) - 1] = "";
		class403.anIntArray5244[((class403.anInt5239 += -391880689) * 681479919) - 1] = 0;
		class403.anIntArray5244[((class403.anInt5239 += -391880689) * 681479919) - 1] = 0;
		class403.anObjectArray5240[((class403.anInt5241 += 969361751) * -203050393) - 1] = "";
	    }
	}
	catch (RuntimeException runtimeexception) {
	    throw Class346.method4175(runtimeexception, new StringBuilder().append("qg.alh(").append(')').toString());
	}
    }

    static final void method4891(IComponentDefinition class105, Class119 class119, Class403 class403, byte i) {
	try {
	    class105.anInt1184 = 1234689410;
	    class105.aClass498_1307 = null;
	    class105.anInt1151 = (class403.anIntArray5244[(class403.anInt5239 -= -391880689) * 681479919]) * -1825442367;
	    if (class105.anInt1154 * -1309843523 == -1 && !class119.aBoolean1403)
		Class422.method5623(class105.ihash * -440872681, 2026838544);
	}
	catch (RuntimeException runtimeexception) {
	    throw Class346.method4175(runtimeexception, new StringBuilder().append("qg.hx(").append(')').toString());
	}
    }

    static synchronized GraphicsToolkit chooseRenderer(int renderOption, Canvas canvas, Interface_ma interface_ma, Class243 class243, int i_0_, int i_1_, int i_2_, int i_3_) {
	try {
		if(renderOption == 0)
			renderOption = renderOption;
	    if (renderOption == 0)
			return Class141.getSafeMode(canvas, interface_ma, i_1_, i_2_, (byte) 88);
	    if (renderOption == 2)
			return Class273.getJavaGraphics(canvas, interface_ma, i_1_, i_2_, (byte) -28);
	    if (1 == renderOption)
			return Class59.getOpenGL(canvas, interface_ma, i_0_);
	    if (5 == renderOption)
			return Class259.method2456(canvas, interface_ma, class243, i_0_);
	    if (3 == renderOption)
			return Class204.getDirectX(canvas, interface_ma, class243, i_0_);
	    throw new IllegalArgumentException("");
	}
	catch (RuntimeException runtimeexception) {
	    throw Class346.method4175(runtimeexception, new StringBuilder().append("qg.f(").append(')').toString());
	}
    }

    static final void method4893(Class403 class403, byte i) {
	try {
	    Class390 class390 = (class403.aBoolean5261 ? class403.aClass390_5247 : class403.aClass390_5246);
	    IComponentDefinition class105 = class390.aClass105_4168;
	    class403.anIntArray5244[((class403.anInt5239 += -391880689) * 681479919 - 1)] = class105.anInt1297 * -407676483;
	}
	catch (RuntimeException runtimeexception) {
	    throw Class346.method4175(runtimeexception, new StringBuilder().append("qg.pl(").append(')').toString());
	}
    }

    static final void method4894(Class403 class403, int i) {
	try {
	    class403.anInt5239 -= -783761378;
	    int i_4_ = (class403.anIntArray5244[681479919 * class403.anInt5239]);
	    int i_5_ = (class403.anIntArray5244[class403.anInt5239 * 681479919 + 1]);
	    if (0 == i_4_)
		class403.anIntArray5244[((class403.anInt5239 += -391880689) * 681479919) - 1] = 0;
	    else
		class403.anIntArray5244[((class403.anInt5239 += -391880689) * 681479919) - 1] = (int) Math.pow(i_4_, i_5_);
	}
	catch (RuntimeException runtimeexception) {
	    throw Class346.method4175(runtimeexception, new StringBuilder().append("qg.zb(").append(')').toString());
	}
    }

    static final void method4895(Class403 class403, int i) {
	try {
	    int i_6_ = (class403.anIntArray5244[((class403.anInt5239 -= -391880689) * 681479919)]);
	    IComponentDefinition class105 = Class50.getIComponentDefinitions(i_6_, (byte) -21);
	    class403.anIntArray5244[((class403.anInt5239 += -391880689) * 681479919 - 1)] = 684246511 * class105.anInt1166;
	}
	catch (RuntimeException runtimeexception) {
	    throw Class346.method4175(runtimeexception, new StringBuilder().append("qg.rn(").append(')').toString());
	}
    }

    public static Class165 method4896(RsByteBuffer class298_sub53, byte i) {
	try {
	    int i_7_ = class298_sub53.readBigSmart(1235052657);
	    return new Class165(i_7_);
	}
	catch (RuntimeException runtimeexception) {
	    throw Class346.method4175(runtimeexception, new StringBuilder().append("qg.a(").append(')').toString());
	}
    }
}
