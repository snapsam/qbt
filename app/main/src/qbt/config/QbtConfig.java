package qbt.config;

import groovy.lang.GroovyShell;
import java.nio.file.Path;
import java.util.Optional;
import misc1.commons.ExceptionUtils;
import qbt.VcsVersionDigest;
import qbt.artifactcacher.ArtifactCacher;
import qbt.repo.CommonRepoAccessor;
import qbt.repo.LocalRepoAccessor;
import qbt.tip.RepoTip;

public final class QbtConfig {
    public final LocalRepoFinder localRepoFinder;
    public final LocalPinsRepo localPinsRepo;
    public final QbtRemoteFinder qbtRemoteFinder;
    public final ArtifactCacher artifactCacher;

    public QbtConfig(LocalRepoFinder localRepoFinder, LocalPinsRepo localPinsRepo, QbtRemoteFinder qbtRemoteFinder, ArtifactCacher artifactCacher) {
        this.localRepoFinder = localRepoFinder;
        this.localPinsRepo = localPinsRepo;
        this.qbtRemoteFinder = qbtRemoteFinder;
        this.artifactCacher = artifactCacher;
    }

    public static QbtConfig parse(Path f) {
        GroovyShell shell = new GroovyShell();
        shell.setVariable("workspaceRoot", f.getParent());
        try {
            return (QbtConfig) shell.evaluate(f.toFile());
        }
        catch(Exception e) {
            throw ExceptionUtils.commute(e);
        }
    }

    public CommonRepoAccessor requireCommonRepo(RepoTip repo, Optional<VcsVersionDigest> version) {
        LocalRepoAccessor local = localRepoFinder.findLocalRepo(repo);
        if(local != null) {
            return local;
        }
        if(!version.isPresent()) {
            throw new IllegalArgumentException("Could not find override " + repo);
        }
        return localPinsRepo.requirePin(repo, version.get(), "Could not find override or local pin for " + repo + " at " + version.get());
    }
}
