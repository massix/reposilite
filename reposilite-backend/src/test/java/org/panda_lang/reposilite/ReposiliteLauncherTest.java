package org.panda_lang.reposilite;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ReposiliteLauncherTest {

    @Test
    void shouldPrintVersion() {
        ReposiliteLauncher.create("--version");
        assertTrue(ReposiliteWriter.contains(ReposiliteConstants.VERSION));
    }

    @Test
    void shouldPrintHelp() {
        ReposiliteLauncher launcher = new ReposiliteLauncher();
        ReposiliteLauncher.create("--help");
        assertTrue(ReposiliteWriter.contains("Commands"));
    }

    @Test
    void shouldReturnReposilite() {
        assertTrue(ReposiliteLauncher.create().isPresent());
    }

}