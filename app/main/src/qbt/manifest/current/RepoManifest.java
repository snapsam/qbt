package qbt.manifest.current;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Map;
import java.util.Optional;
import misc1.commons.ds.ImmutableSalvagingMap;
import misc1.commons.ds.Struct;
import misc1.commons.ds.StructBuilder;
import misc1.commons.ds.StructKey;
import misc1.commons.ds.StructType;
import misc1.commons.merge.Merge;
import misc1.commons.merge.Merges;
import qbt.VcsVersionDigest;
import qbt.manifest.JsonSerializer;
import qbt.manifest.JsonSerializers;

public final class RepoManifest extends Struct<RepoManifest, RepoManifest.Builder> {
    public final Optional<VcsVersionDigest> version;
    public final ImmutableMap<String, PackageManifest> packages;

    private RepoManifest(ImmutableMap<StructKey<RepoManifest, ?, ?>, Object> map) {
        super(TYPE, map);

        this.version = get(VERSION);
        this.packages = get(PACKAGES).map;
    }

    public static class Builder extends StructBuilder<RepoManifest, Builder> {
        public Builder(ImmutableSalvagingMap<StructKey<RepoManifest, ?, ?>, Object> map) {
            super(TYPE, map);
        }
    }

    public static final StructKey<RepoManifest, RepoManifestPackages, RepoManifestPackages.Builder> PACKAGES;
    public static final StructKey<RepoManifest, Optional<VcsVersionDigest>, Optional<VcsVersionDigest>> VERSION;
    public static final StructType<RepoManifest, Builder> TYPE;
    static {
        ImmutableList.Builder<StructKey<RepoManifest, ?, ?>> b = ImmutableList.builder();

        b.add(PACKAGES = new StructKey<RepoManifest, RepoManifestPackages, RepoManifestPackages.Builder>("packages", RepoManifestPackages.TYPE.builder()) {
            @Override
            public RepoManifestPackages toStruct(RepoManifestPackages.Builder vb) {
                return (vb).build();
            }

            @Override
            public RepoManifestPackages.Builder toBuilder(RepoManifestPackages vs) {
                return (vs).builder();
            }

            @Override
            public Merge<RepoManifestPackages> merge() {
                return RepoManifestPackages.TYPE.merge();
            }
        });
        b.add(VERSION = new StructKey<RepoManifest, Optional<VcsVersionDigest>, Optional<VcsVersionDigest>>("version") {
            @Override
            public Optional<VcsVersionDigest> toStruct(Optional<VcsVersionDigest> vb) {
                return vb;
            }

            @Override
            public Optional<VcsVersionDigest> toBuilder(Optional<VcsVersionDigest> vs) {
                return vs;
            }

            @Override
            public Merge<Optional<VcsVersionDigest>> merge() {
                return Merges.<Optional<VcsVersionDigest>>trivial();
            }
        });

        TYPE = new StructType<RepoManifest, Builder>(b.build(), RepoManifest::new, Builder::new);
    }

    public static final JsonSerializer<Builder> SERIALIZER = new JsonSerializer<Builder>() {
        @Override
        public JsonElement toJson(Builder b) {
            JsonObject r = new JsonObject();
            RepoManifestPackages.Builder packages = b.get(PACKAGES);
            if(!packages.equals(RepoManifestPackages.TYPE.builder())) {
                r.add("packages", (RepoManifestPackages.SERIALIZER).toJson(packages));
            }
            r.add("version", (JsonSerializers.OPTIONAL_VCS_VERSION_DIGEST).toJson(b.get(VERSION)));
            return r;
        }

        @Override
        public Builder fromJson(JsonElement e) {
            Builder b = TYPE.builder();
            for(Map.Entry<String, JsonElement> e2 : e.getAsJsonObject().entrySet()) {
                switch(e2.getKey()) {
                    case "packages":
                        b = b.set(PACKAGES, (RepoManifestPackages.SERIALIZER).fromJson(e2.getValue()));
                        break;

                    case "version":
                        b = b.set(VERSION, (JsonSerializers.OPTIONAL_VCS_VERSION_DIGEST).fromJson(e2.getValue()));
                        break;

                    default:
                        throw new IllegalArgumentException(e2.getKey());
                }
            }
            return b;
        }
    };
}
