package qbt.mains;

import java.io.IOException;
import misc1.commons.options.OptionsFragment;
import misc1.commons.options.OptionsLibrary;
import misc1.commons.options.OptionsResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qbt.HelpTier;
import qbt.QbtCommand;
import qbt.QbtCommandName;
import qbt.QbtCommandOptions;
import qbt.config.QbtConfig;
import qbt.manifest.current.QbtManifest;
import qbt.manifest.current.RepoManifest;
import qbt.options.ConfigOptionsDelegate;
import qbt.options.ManifestOptionsDelegate;
import qbt.options.ManifestOptionsResult;
import qbt.tip.PackageTip;

public final class RemovePackage extends QbtCommand<RemovePackage.Options> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RemovePackage.class);

    @QbtCommandName("removePackage")
    public static interface Options extends QbtCommandOptions {
        public static final OptionsLibrary<Options> o = OptionsLibrary.of();
        public static final ConfigOptionsDelegate<Options> config = new ConfigOptionsDelegate<Options>();
        public static final ManifestOptionsDelegate<Options> manifest = new ManifestOptionsDelegate<Options>();
        public static final OptionsFragment<Options, String> pkg = o.oneArg("package").transform(o.singleton()).helpDesc("Package to remove");
    }

    @Override
    public Class<Options> getOptionsClass() {
        return Options.class;
    }

    @Override
    public HelpTier getHelpTier() {
        return HelpTier.UNCOMMON;
    }

    @Override
    public String getDescription() {
        return "remove a package from the manifest";
    }

    @Override
    public boolean isProgrammaticOutput() {
        return false;
    }

    @Override
    public int run(final OptionsResults<? extends Options> options) throws IOException {
        final QbtConfig config = Options.config.getConfig(options);
        final ManifestOptionsResult manifestResult = Options.manifest.getResult(options);
        QbtManifest manifest = manifestResult.parse(config.manifestParser);
        PackageTip pt = PackageTip.TYPE.parseRequire(options.get(Options.pkg));

        if(!manifest.packageToRepo.containsKey(pt)) {
            throw new IllegalArgumentException("Package " + pt + " does not exist");
        }

        manifest = manifest.builder().transform(manifest.packageToRepo.get(pt), (rm) -> rm.transform(RepoManifest.PACKAGES, (pkgs) -> pkgs.without(pt.name))).build();
        manifestResult.deparse(config.manifestParser, manifest);
        LOGGER.info("Package " + pt + " removed and manifest written successfully");
        return 0;
    }
}
