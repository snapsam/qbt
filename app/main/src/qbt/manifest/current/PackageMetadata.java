package qbt.manifest.current;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.Map;
import misc1.commons.Maybe;
import misc1.commons.ds.ImmutableSalvagingMap;
import misc1.commons.ds.Struct;
import misc1.commons.ds.StructBuilder;
import misc1.commons.ds.StructKey;
import misc1.commons.ds.StructType;
import misc1.commons.merge.Merge;
import misc1.commons.merge.Merges;
import qbt.manifest.JsonSerializer;
import qbt.manifest.JsonSerializers;
import qbt.manifest.PackageBuildType;

public final class PackageMetadata extends Struct<PackageMetadata, PackageMetadata.Builder> {
    private PackageMetadata(ImmutableMap<StructKey<PackageMetadata, ?, ?>, Object> map) {
        super(TYPE, map);
    }

    public static class Builder extends StructBuilder<PackageMetadata, Builder> {
        public Builder(ImmutableSalvagingMap<StructKey<PackageMetadata, ?, ?>, Object> map) {
            super(TYPE, map);
        }
    }

    public static final StructKey<PackageMetadata, Boolean, Boolean> ARCH_INDEPENDENT;
    public static final StructKey<PackageMetadata, PackageBuildType, PackageBuildType> BUILD_TYPE;
    public static final StructKey<PackageMetadata, String, String> PREFIX;
    public static final StructKey<PackageMetadata, ImmutableMap<String, Maybe<String>>, ImmutableMap<String, Maybe<String>>> QBT_ENV;
    public static final StructType<PackageMetadata, Builder> TYPE;
    static {
        ImmutableList.Builder<StructKey<PackageMetadata, ?, ?>> b = ImmutableList.builder();

        b.add(ARCH_INDEPENDENT = new StructKey<PackageMetadata, Boolean, Boolean>("archIndependent", false) {
            @Override
            public Boolean toStruct(Boolean vb) {
                return vb;
            }

            @Override
            public Boolean toBuilder(Boolean vs) {
                return vs;
            }

            @Override
            public Merge<Boolean> merge() {
                return Merges.<Boolean>trivial();
            }
        });
        b.add(BUILD_TYPE = new StructKey<PackageMetadata, PackageBuildType, PackageBuildType>("buildType", PackageBuildType.NORMAL) {
            @Override
            public PackageBuildType toStruct(PackageBuildType vb) {
                return vb;
            }

            @Override
            public PackageBuildType toBuilder(PackageBuildType vs) {
                return vs;
            }

            @Override
            public Merge<PackageBuildType> merge() {
                return Merges.<PackageBuildType>trivial();
            }
        });
        b.add(PREFIX = new StructKey<PackageMetadata, String, String>("prefix", "") {
            @Override
            public String toStruct(String vb) {
                return vb;
            }

            @Override
            public String toBuilder(String vs) {
                return vs;
            }

            @Override
            public Merge<String> merge() {
                return Merges.<String>trivial();
            }
        });
        b.add(QBT_ENV = new StructKey<PackageMetadata, ImmutableMap<String, Maybe<String>>, ImmutableMap<String, Maybe<String>>>("qbtEnv", ImmutableMap.<String, Maybe<String>>of()) {
            @Override
            public ImmutableMap<String, Maybe<String>> toStruct(ImmutableMap<String, Maybe<String>> vb) {
                return vb;
            }

            @Override
            public ImmutableMap<String, Maybe<String>> toBuilder(ImmutableMap<String, Maybe<String>> vs) {
                return vs;
            }

            @Override
            public Merge<ImmutableMap<String, Maybe<String>>> merge() {
                return Merges.<ImmutableMap<String, Maybe<String>>>trivial();
            }
        });

        TYPE = new StructType<PackageMetadata, Builder>(b.build(), PackageMetadata::new, Builder::new);
    }

    public static final JsonSerializer<Builder> SERIALIZER = new JsonSerializer<Builder>() {
        @Override
        public JsonElement toJson(Builder b) {
            JsonObject r = new JsonObject();
            Boolean archIndependent = b.get(ARCH_INDEPENDENT);
            if(!archIndependent.equals(false)) {
                r.add("archIndependent", (JsonSerializers.BOOLEAN).toJson(archIndependent));
            }
            PackageBuildType buildType = b.get(BUILD_TYPE);
            if(!buildType.equals(PackageBuildType.NORMAL)) {
                r.add("buildType", (JsonSerializers.forEnum(PackageBuildType.class)).toJson(buildType));
            }
            String prefix = b.get(PREFIX);
            if(!prefix.equals("")) {
                r.add("prefix", new JsonPrimitive(prefix));
            }
            ImmutableMap<String, Maybe<String>> qbtEnv = b.get(QBT_ENV);
            if(!qbtEnv.equals(ImmutableMap.<String, Maybe<String>>of())) {
                r.add("qbtEnv", (JsonSerializers.QBT_ENV).toJson(qbtEnv));
            }
            return r;
        }

        @Override
        public Builder fromJson(JsonElement e) {
            Builder b = TYPE.builder();
            for(Map.Entry<String, JsonElement> e2 : e.getAsJsonObject().entrySet()) {
                switch(e2.getKey()) {
                    case "archIndependent":
                        b = b.set(ARCH_INDEPENDENT, (JsonSerializers.BOOLEAN).fromJson(e2.getValue()));
                        break;

                    case "buildType":
                        b = b.set(BUILD_TYPE, (JsonSerializers.forEnum(PackageBuildType.class)).fromJson(e2.getValue()));
                        break;

                    case "prefix":
                        b = b.set(PREFIX, e2.getValue().getAsString());
                        break;

                    case "qbtEnv":
                        b = b.set(QBT_ENV, (JsonSerializers.QBT_ENV).fromJson(e2.getValue()));
                        break;

                    default:
                        throw new IllegalArgumentException(e2.getKey());
                }
            }
            return b;
        }
    };
}
