package org.kettingpowered.ketting.internal;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author C0D3 M4513R
 * @param major major version
 * @param minor minor version
 * @param patch patch version
 * @param <T> version type
 */
public record MajorMinorPatchVersion<T extends Comparable<T>>(T major, T minor, T patch) implements Comparable<MajorMinorPatchVersion<T>> {
    public static MajorMinorPatchVersion<String> parse(String version){
        return parse(version, "\\.");
    }
    public static MajorMinorPatchVersion<String> parse(String version, String seperator){
        String major, minor, patch;
        String[] parts = version.split(seperator);
        if (parts.length>=1) major=parts[0];
        else throw new IllegalStateException("Split returned an empty array");
        if (parts.length>=2) minor = parts[1];
        else minor="0";
        if (parts.length>=3) patch = parts[2];
        else patch="0";
        return new MajorMinorPatchVersion<>(major, minor, patch);
    }
    
    @Override
    public String toString(){
        return String.format("%s.%s.%s", major, minor, patch);
    }

    @Override
    public int compareTo(@NotNull MajorMinorPatchVersion<T> o) {
        int mjc = major.compareTo(o.major);
        if (mjc!=0) return mjc;
        int mnc = minor.compareTo(o.minor);
        if (mnc!=0) return mnc;
        return patch.compareTo(o.patch);
    }

    public static HashMap<MajorMinorPatchVersion<Integer>, List<Tuple<MajorMinorPatchVersion<Integer>, MajorMinorPatchVersion<Integer>>>> parseKettingServerVersionList(Stream<String> versions){
        HashMap<MajorMinorPatchVersion<Integer>, List<Tuple<MajorMinorPatchVersion<Integer>, MajorMinorPatchVersion<Integer>>>> map = versions.map(version -> MajorMinorPatchVersion.parse(version, "-"))
                .map(version -> new MajorMinorPatchVersion<>(
                        MajorMinorPatchVersion.parse(version.major()),//mc version
                        MajorMinorPatchVersion.parse(version.minor()),//forge version
                        MajorMinorPatchVersion.parse(version.patch())//Ketting version
                )).map(version->new MajorMinorPatchVersion<>(
                        new MajorMinorPatchVersion<>(Integer.parseInt(version.major().major()), Integer.parseInt(version.major().minor()), Integer.parseInt(version.major().patch())),
                        new MajorMinorPatchVersion<>(Integer.parseInt(version.minor().major()), Integer.parseInt(version.minor().minor()), Integer.parseInt(version.minor().patch())),
                        new MajorMinorPatchVersion<>(Integer.parseInt(version.patch().major()), Integer.parseInt(version.patch().minor()), Integer.parseInt(version.patch().patch()))
                ))
                .reduce(new HashMap<>(), (hm, mmp)->{
                    hm.computeIfAbsent(mmp.major(), (key)->new ArrayList<>()).add(new Tuple<>(mmp.minor(), mmp.patch()));
                    return hm;
                }, (hm1, hm2)->{
                    for(final Map.Entry<MajorMinorPatchVersion<Integer>, List<Tuple<MajorMinorPatchVersion<Integer>, MajorMinorPatchVersion<Integer>>>> entry : hm2.entrySet()){
                        hm1.computeIfAbsent(entry.getKey(), (key)-> new ArrayList<>()).addAll(entry.getValue());
                    }
                    hm2.clear();
                    return hm1;
                });
        map.values().forEach(list->list.sort(Comparator.comparing((Tuple<MajorMinorPatchVersion<Integer>, MajorMinorPatchVersion<Integer>> t) -> t.t1()).thenComparing(Tuple::t2).reversed()));
        return map;
    }
}