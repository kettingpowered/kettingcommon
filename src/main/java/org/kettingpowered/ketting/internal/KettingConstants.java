package org.kettingpowered.ketting.internal;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;
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
    public static final String INSTALLER_LIBRARIES_FOLDER = "libraries";
    public static final String KETTING_GROUP = "org.kettingpowered";
    public static final String KETTINGSERVER_GROUP = KETTING_GROUP + ".server";
    public static boolean NEOFORGE = false;
    static{
        HashMap<MajorMinorPatchVersion<Integer>, List<Tuple<MajorMinorPatchVersion<Integer>, MajorMinorPatchVersion<Integer>>>> map = MajorMinorPatchVersion.parseKettingServerVersionList(KettingFiles.getKettingServerVersions().stream());
        if (map.isEmpty()) throw new IllegalStateException("No Ketting Server Version found.");
        else if (map.size() > 1) throw new IllegalStateException("Multiple Ketting Server Versions for multiple Minecraft Versions found.");
        final Map.Entry<MajorMinorPatchVersion<Integer>, List<Tuple<MajorMinorPatchVersion<Integer>, MajorMinorPatchVersion<Integer>>>> entry = map.entrySet().iterator().next();
        MINECRAFT_VERSION = entry.getKey().toString();
        if (entry.getValue().isEmpty()) throw new IllegalStateException("Found a Minecraft Version, but no Ketting Server versions for that version. This should not happen.");
        else if (entry.getValue().size() > 1) throw new IllegalStateException("Found multiple Ketting Server versions.");
        Tuple<MajorMinorPatchVersion<Integer>, MajorMinorPatchVersion<Integer>> version = entry.getValue().get(0);
        FORGE_VERSION = version.t1().toString();
        KETTING_VERSION = version.t2().toString();
        VERSION = MINECRAFT_VERSION + "-" + KETTING_VERSION;

        final String[] mcv = MINECRAFT_VERSION.split("\\.");
        mcv[mcv.length-1] = "R"+mcv[mcv.length-1];
        BUKKIT_PACKAGE_VERSION = String.join("_", mcv);
        BUKKIT_VERSION = MINECRAFT_VERSION + "-R0.1-SNAPSHOT";

        final String
                MC_FORGE_KETTING = MINECRAFT_VERSION + "-" + FORGE_VERSION + "-" + KETTING_VERSION,
                FORGE_UNIVERSAL_NAME = "forge-" + MC_FORGE_KETTING + "-universal.jar";
        try (final JarFile jarFile = new JarFile(new File(KettingFiles.KETTINGSERVER_FORGE_DIR, MC_FORGE_KETTING + "/" + FORGE_UNIVERSAL_NAME))){
            final Attributes fullVersionAttribute = jarFile.getManifest().getEntries().get("org/kettingpowered/ketting/");
            if (fullVersionAttribute != null) {
                String fullVersion = fullVersionAttribute.getValue("Implementation-Version");
                MCP_VERSION = fullVersion.substring(fullVersion.lastIndexOf('-')+1);
            } else {
                MCP_VERSION = jarFile.getManifest().getEntries().get("net/neoforged/neoforge/versions/neoform/").getValue("Implementation-Version").split("-")[1];
                NEOFORGE = true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
