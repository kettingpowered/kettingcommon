package org.kettingpowered.ketting.internal;

import java.io.File;

public class KettingFileVersioned {
    private static final String
            MC = KettingConstants.MINECRAFT_VERSION,
            FORGE = KettingConstants.FORGE_VERSION,
            MCP = KettingConstants.MCP_VERSION,
            MC_FORGE = MC + "-" + FORGE,
            MC_FORGE_KETTING = MC + "-" + FORGE + "-" + KettingConstants.KETTING_VERSION,
            MC_MCP = MC + "-" + MCP;
    public static final File MCP_BASE_DIR = new File(KettingFiles.MCP_BASE_DIR, MC_MCP + "/");
    public static final File NMS_BASE_DIR = new File(KettingFiles.NMS_BASE_DIR, MC + "/");
    public static final File NMS_PATCHES_DIR = new File(KettingFiles.NMS_BASE_DIR, MC_MCP + "/");

    public static final String
            KETTINGSERVER_PATH = "org/kettingpowered/server",
            FMLCORE_NAME = "fmlcore-" + MC_FORGE_KETTING + ".jar",
            FMLLOADER_NAME = "fmlloader-" + MC_FORGE_KETTING + ".jar",
            JAVAFMLLANGUAGE_NAME = "javafmllanguage-" + MC_FORGE_KETTING + ".jar",
            LOWCODELANGUAGE_NAME = "lowcodelanguage-" + MC_FORGE_KETTING + ".jar",
            MCLANGUAGE_NAME = "mclanguage-" + MC_FORGE_KETTING + ".jar";

    public static final File
            FMLCORE = new File(KettingFiles.KETTINGSERVER_BASE_DIR, "fmlcore/" + MC_FORGE_KETTING + "/" + FMLCORE_NAME),
            FMLLOADER = new File(KettingFiles.KETTINGSERVER_BASE_DIR, "fmlloader/" + MC_FORGE_KETTING + "/" + FMLLOADER_NAME),
            JAVAFMLLANGUAGE = new File(KettingFiles.KETTINGSERVER_BASE_DIR, "javafmllanguage/" + MC_FORGE_KETTING + "/" + JAVAFMLLANGUAGE_NAME),
            LOWCODELANGUAGE = new File(KettingFiles.KETTINGSERVER_BASE_DIR, "lowcodelanguage/" + MC_FORGE_KETTING + "/" + LOWCODELANGUAGE_NAME),
            MCLANGUAGE = new File(KettingFiles.KETTINGSERVER_BASE_DIR, "mclanguage/" + MC_FORGE_KETTING + "/" + MCLANGUAGE_NAME);

    public static final String
            FORGE_UNIVERSAL_NAME = "forge-" + MC_FORGE_KETTING + "-universal.jar";

    public static final File
            FORGE_UNIVERSAL_JAR = new File(KettingFiles.KETTINGSERVER_FORGE_DIR, MC_FORGE_KETTING + "/" + FORGE_UNIVERSAL_NAME),
            FORGE_PATCHED_JAR = new File(KettingFiles.KETTINGSERVER_FORGE_DIR, MC_FORGE_KETTING + "/forge-" + MC_FORGE_KETTING + "-server.jar"),
            FORGE_KETTING_LIBS = new File(KettingFiles.KETTINGSERVER_FORGE_DIR, MC_FORGE_KETTING + "/forge-" + MC_FORGE_KETTING + "-ketting-libraries.txt"),
            FORGE_INSTALL_JSON = new File(KettingFiles.KETTINGSERVER_FORGE_DIR, MC_FORGE_KETTING + "/forge-" + MC_FORGE_KETTING + "-installscript.json"),
            MCP_ZIP = new File(MCP_BASE_DIR, "mcp_config-" + MC_MCP + ".zip"),
            SERVER_JAR = new File(NMS_BASE_DIR, "server-" + MC + ".jar"),
            SERVER_UNPACKED = new File(NMS_PATCHES_DIR, "server-" + MC_MCP + "-unpacked.jar"),
            SERVER_SLIM = new File(NMS_PATCHES_DIR, "server-" + MC_MCP + "-slim.jar"),
            SERVER_EXTRA = new File(NMS_PATCHES_DIR, "server-" + MC_MCP + "-extra.jar"),
            SERVER_SRG = new File(NMS_PATCHES_DIR, "server-" + MC_MCP + "-srg.jar"),
            MCP_MAPPINGS = new File(MCP_BASE_DIR, "mappings.txt"),
            MOJANG_MAPPINGS = new File(NMS_PATCHES_DIR, "mappings.txt"),
            MERGED_MAPPINGS = new File(MCP_BASE_DIR, "mappings-merged.txt");

}
