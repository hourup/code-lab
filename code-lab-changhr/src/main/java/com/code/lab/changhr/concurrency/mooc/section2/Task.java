package com.code.lab.changhr.concurrency.mooc.section2;

/**
 * @author changhr
 * @create 2020-03-27 14:59
 */
public class Task {

    private Integer leftCopyCount;

    private String wordToCopy;

    public Task() {
    }

    public Task(Integer leftCopyCount, String wordToCopy) {
        this.leftCopyCount = leftCopyCount;
        this.wordToCopy = wordToCopy;
    }

    public Integer getLeftCopyCount() {
        return leftCopyCount;
    }

    public void setLeftCopyCount(Integer leftCopyCount) {
        this.leftCopyCount = leftCopyCount;
    }

    public String getWordToCopy() {
        return wordToCopy;
    }

    public void setWordToCopy(String wordToCopy) {
        this.wordToCopy = wordToCopy;
    }
}
