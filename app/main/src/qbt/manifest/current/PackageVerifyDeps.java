package qbt.manifest.current;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Map;
import misc1.commons.ds.ImmutableSalvagingMap;
import misc1.commons.ds.MapStruct;
import misc1.commons.ds.MapStructBuilder;
import misc1.commons.ds.MapStructType;
import misc1.commons.merge.Merge;
import misc1.commons.merge.Merges;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.tuple.Pair;
import qbt.manifest.JsonSerializer;
import qbt.manifest.JsonSerializers;
import qbt.manifest.StringSerializer;
import qbt.tip.PackageTip;

public final class PackageVerifyDeps extends MapStruct<PackageVerifyDeps, PackageVerifyDeps.Builder, Pair<PackageTip, String>, ObjectUtils.Null, ObjectUtils.Null> {
    private PackageVerifyDeps(ImmutableMap<Pair<PackageTip, String>, ObjectUtils.Null> map) {
        super(TYPE, map);
    }

    public static class Builder extends MapStructBuilder<PackageVerifyDeps, Builder, Pair<PackageTip, String>, ObjectUtils.Null, ObjectUtils.Null> {
        public Builder(ImmutableSalvagingMap<Pair<PackageTip, String>, ObjectUtils.Null> map) {
            super(TYPE, map);
        }
    }

    public static final MapStructType<PackageVerifyDeps, Builder, Pair<PackageTip, String>, ObjectUtils.Null, ObjectUtils.Null> TYPE = new MapStructType<PackageVerifyDeps, Builder, Pair<PackageTip, String>, ObjectUtils.Null, ObjectUtils.Null>() {
        @Override
        protected PackageVerifyDeps create(ImmutableMap<Pair<PackageTip, String>, ObjectUtils.Null> map) {
            return new PackageVerifyDeps(map);
        }

        @Override
        protected Builder createBuilder(ImmutableSalvagingMap<Pair<PackageTip, String>, ObjectUtils.Null> map) {
            return new Builder(map);
        }

        @Override
        protected ObjectUtils.Null toStruct(ObjectUtils.Null vb) {
            return vb;
        }

        @Override
        protected ObjectUtils.Null toBuilder(ObjectUtils.Null vs) {
            return vs;
        }

        @Override
        protected Merge<ObjectUtils.Null> mergeValue() {
            return Merges.<ObjectUtils.Null>trivial();
        }
    };

    public static final JsonSerializer<Builder> SERIALIZER = new JsonSerializer<Builder>() {
        @Override
        public JsonElement toJson(Builder b) {
            JsonObject r = new JsonObject();
            for(Map.Entry<Pair<PackageTip, String>, ObjectUtils.Null> e : b.map.entries()) {
                r.add(StringSerializer.VERIFY_DEP_KEY.toString(e.getKey()), JsonSerializers.OU_NULL.toJson(e.getValue()));
            }
            return r;
        }

        @Override
        public Builder fromJson(JsonElement e) {
            Builder b = TYPE.builder();
            for(Map.Entry<String, JsonElement> e2 : e.getAsJsonObject().entrySet()) {
                b = b.with(StringSerializer.VERIFY_DEP_KEY.fromString(e2.getKey()), JsonSerializers.OU_NULL.fromJson(e2.getValue()));
            }
            return b;
        }
    };
}
