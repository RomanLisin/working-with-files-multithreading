package ru.packajes;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class DirectoryStats {
    public int fileCount = 0;
    public int directoryCount = 0;
    public long totalSize = 0;
    public Set<String> fileTypes = new HashSet<>();

    @Override
    public String toString() {
        return String.format("Файлов: %d, Папок: %d, Общий размер: %d байт, Типы файлов: %s",
                fileCount, directoryCount, totalSize, String.join((CharSequence) ", ", (CharSequence) Collections.singleton(fileTypes)));
    }
}

