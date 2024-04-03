package org.kettingpowered.ketting.internal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.jar.JarFile;

/**
 * @author C0D3 M4513R
 * @author JustRed32
 */
@SuppressWarnings("unused")
public class KettingConstants {
    public static final String NAME = "Ketting";
    public static final String BRAND = "KettingPowered";
    public static final String SITE_LINK = "https://github.com/kettingpowered/";
    public static final String VERSION;
    public static final String MINECRAFT_VERSION;
    public static final String BUKKIT_PACKAGE_VERSION;
    public static final String BUKKIT_VERSION;
    public static final String FORGE_VERSION;
    public static final String KETTING_VERSION;
    public static final String MCP_VERSION;
    public static final Type TYPE;
    public static final String INSTALLER_LIBRARIES_FOLDER = "libraries";
    public static final String KETTING_GROUP = "org.kettingpowered";
    public static final String KETTINGSERVER_GROUP = KETTING_GROUP + ".server";
    static{
        HashMap<Type, HashMap<MajorMinorPatchVersion<Integer>, List<Tuple<MajorMinorPatchVersion<Integer>, MajorMinorPatchVersion<Integer>>>>> map = new HashMap<>();
        map.put(Type.Forge, MajorMinorPatchVersion.parseKettingServerVersionList(KettingFiles.getKettingServerVersions(KettingFiles.KETTINGSERVER_FORGE_DIR).stream()));
        map.put(Type.NeoForge, MajorMinorPatchVersion.parseKettingServerVersionList(KettingFiles.getKettingServerVersions(KettingFiles.KETTINGSERVER_NEOFORGE_DIR).stream()));
        {
            List<Type> toRemove = new ArrayList<>();
            for (final var entry:map.entrySet()){
                if (entry.getValue().isEmpty()){
                    toRemove.add(entry.getKey());
                }
            }
            for(final Type type:toRemove){
                map.remove(type);
            }
        }
        
        if (map.isEmpty()) throw new IllegalStateException("No Ketting Server Version found.");
        else if (map.size() > 1) throw new IllegalStateException("Multiple different Ketting Server Types.");

        final Map.Entry<Type, HashMap<MajorMinorPatchVersion<Integer>, List<Tuple<MajorMinorPatchVersion<Integer>, MajorMinorPatchVersion<Integer>>>>> entry = map.entrySet().iterator().next();
        TYPE = entry.getKey();
        final HashMap<MajorMinorPatchVersion<Integer>, List<Tuple<MajorMinorPatchVersion<Integer>, MajorMinorPatchVersion<Integer>>>> typeMap = entry.getValue();
        if (map.isEmpty()) throw new IllegalStateException("No Ketting Server Version found.");
        else if (map.size() > 1) throw new IllegalStateException("Multiple Ketting Servers found for different Versions of "+TYPE.name()+".");
        final Map.Entry<MajorMinorPatchVersion<Integer>, List<Tuple<MajorMinorPatchVersion<Integer>, MajorMinorPatchVersion<Integer>>>> mcEntry = typeMap.entrySet().iterator().next();
        MINECRAFT_VERSION = mcEntry.getKey().toString();
        if (mcEntry.getValue().isEmpty()) throw new IllegalStateException("Found a Minecraft Version, but no Ketting Server versions for that version. This should not happen.");
        else if (mcEntry.getValue().size() > 1) throw new IllegalStateException("Found multiple Ketting Server versions for "+TYPE.name() +" for the Minecraft Version "+MINECRAFT_VERSION+" .");
        Tuple<MajorMinorPatchVersion<Integer>, MajorMinorPatchVersion<Integer>> version = mcEntry.getValue().get(0);
        FORGE_VERSION = version.t1().toString();
        KETTING_VERSION = version.t2().toString();
        VERSION = MINECRAFT_VERSION + "-" + KETTING_VERSION;

        final String[] mcv = MINECRAFT_VERSION.split("\\.");
        mcv[mcv.length-1] = "R"+mcv[mcv.length-1];
        BUKKIT_PACKAGE_VERSION = String.join("_", mcv);
        BUKKIT_VERSION = MINECRAFT_VERSION + "-R0.1-SNAPSHOT";

        final String MC_FORGE_KETTING = MINECRAFT_VERSION + "-" + FORGE_VERSION + "-" + KETTING_VERSION;
        final String TYPE_NAME = TYPE.typeOrThrow();
        final String UNIVERSAL_NAME = TYPE_NAME + "-" + MC_FORGE_KETTING + "-universal.jar";
        final File INSTALL_DIR = TYPE.installDirOrThrow();

        try (final JarFile jarFile = new JarFile(new File(INSTALL_DIR, MC_FORGE_KETTING + "/" + UNIVERSAL_NAME))){
            final String fullVersion = (String) jarFile.getManifest().getEntries().get("org/kettingpowered/ketting/").getValue("Implementation-Version");
            MCP_VERSION = fullVersion.substring(fullVersion.lastIndexOf('-')+1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
