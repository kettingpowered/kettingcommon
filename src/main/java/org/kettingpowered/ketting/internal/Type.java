package org.kettingpowered.ketting.internal;

import java.io.File;

//Do not any of the existing names. It WILL cause issues with launching on some KettingLauncher versions.
public enum Type {
    Forge("forge", "net.minecraftforge.", KettingFiles.KETTINGSERVER_FORGE_DIR),
    NeoForge("neoforge", "net.neoforged.", KettingFiles.KETTINGSERVER_NEOFORGE_DIR),
    Unknown;

    private final String typeName;
    private final String pkgName;
    private final File installDir;

    Type(String typeName, String pkgName, File installDir) {
        this.typeName = typeName;
        this.pkgName = pkgName;
        this.installDir = installDir;
    }
    Type() {
        this(null, null, null);
    }

    public String typeOrThrow() {
        if (this == Unknown) throw new IllegalStateException("Unknown modloader type");
        return typeName;
    }

    public String pkgOrThrow() {
        if (this == Unknown) throw new IllegalStateException("Unknown modloader type");
        return pkgName;
    }

    public File installDirOrThrow() {
        if (this == Unknown) throw new IllegalStateException("Unknown modloader type");
        return installDir;
    }

    public File installJsonOrThrow() {
        return switch (KettingConstants.TYPE) {
            case Forge -> KettingFileVersioned.FORGE_INSTALL_JSON;
            case NeoForge -> KettingFileVersioned.NEOFORGE_INSTALL_JSON;
            default -> throw new IllegalStateException("Unknown modloader type");
        };
    }

    public File kettingLibsOrThrow() {
        return switch (KettingConstants.TYPE) {
            case Forge -> KettingFileVersioned.FORGE_KETTING_LIBS;
            case NeoForge -> KettingFileVersioned.NEOFORGE_KETTING_LIBS;
            default -> throw new IllegalStateException("Unknown modloader type");
        };
    }

    public File patchedJarOrThrow() {
        return switch (KettingConstants.TYPE) {
            case Forge -> KettingFileVersioned.FORGE_PATCHED_JAR;
            case NeoForge -> KettingFileVersioned.NEOFORGE_PATCHED_JAR;
            default -> throw new IllegalStateException("Unknown modloader type");
        };
    }
}
