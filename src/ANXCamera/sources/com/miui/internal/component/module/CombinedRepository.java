package com.miui.internal.component.module;

import java.io.File;
import miui.module.Repository;

public class CombinedRepository extends Repository {
    private Repository[] repositories;

    public CombinedRepository(Repository... repositoryArr) {
        this.repositories = repositoryArr;
    }

    @Override // miui.module.Repository
    public boolean contains(String str) {
        for (Repository repository : this.repositories) {
            if (repository.contains(str)) {
                return true;
            }
        }
        return false;
    }

    @Override // miui.module.Repository
    public String fetch(File file, String str) {
        Repository[] repositoryArr = this.repositories;
        for (Repository repository : repositoryArr) {
            if (repository.contains(str)) {
                return repository.fetch(file, str);
            }
        }
        return null;
    }
}
