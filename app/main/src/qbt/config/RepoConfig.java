package qbt.config;

import com.google.common.collect.ImmutableList;
import qbt.PackageTip;
import qbt.VcsVersionDigest;
import qbt.repo.CommonRepoAccessor;
import qbt.repo.LocalRepoAccessor;
import qbt.repo.RemoteRepoAccessor;

public final class RepoConfig {
    private final ImmutableList<RepoConfigEntry> entries;

    public RepoConfig(ImmutableList<RepoConfigEntry> entries) {
        this.entries = entries;
    }

    public CommonRepoAccessor requireRepo(PackageTip repo, VcsVersionDigest version) {
        for(RepoConfigEntry e : entries) {
            CommonRepoAccessor r = e.findRepo(repo, version);
            if(r != null) {
                return r;
            }
        }
        throw new IllegalArgumentException("Could not find repo for " + repo);
    }

    public RemoteRepoAccessor requireRepoRemote(PackageTip repo, VcsVersionDigest version) {
        for(RepoConfigEntry e : entries) {
            RemoteRepoAccessor r = e.findRepoRemote(repo, version);
            if(r != null) {
                return r;
            }
        }
        throw new IllegalArgumentException("Could not find remote repo for " + repo);
    }

    public LocalRepoAccessor findLocalRepo(PackageTip repo) {
        for(RepoConfigEntry e : entries) {
            LocalRepoAccessor r = e.findRepoLocal(repo);
            if(r != null) {
                return r;
            }
        }
        return null;
    }

    public LocalRepoAccessor createLocalRepo(PackageTip repo) {
        for(RepoConfigEntry e : entries) {
            LocalRepoAccessor r = e.createLocalRepo(repo);
            if(r != null) {
                return r;
            }
        }
        return null;
    }
}
