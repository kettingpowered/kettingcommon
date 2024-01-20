package org.kettingpowered.ketting.internal.hacks;

import java.util.Set;

/**
 * @author  AllmightySatan
 */
@SuppressWarnings("unused")
public final class JavaHacks {
    //Fixes weird behaviour with org.apache.commons.lang.enum where enum would be seen as a java keyword
    public static void clearReservedIdentifiers() {
        try {
            Unsafe.lookup().findStaticSetter(Class.forName("jdk.internal.module.Checks"), "RESERVED", Set.class).invoke(Set.of());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
