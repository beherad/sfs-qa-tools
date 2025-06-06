package com.sfs.global.qa.core.framework.utils;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.text.diff.StringsComparator;

public interface FileDiff {

    static String turnListIntoSLined(List<String> list) {
        Iterator<String> ifile = list.iterator();

        // Initialize visitor.
        String slined = "";

        // Read file line by line.
        while (ifile.hasNext()) {
            slined = (ifile.hasNext() ? ifile.next() : "") + "\n";
        }
        return slined;
    }

    /**
     * Compares two String lists and gets the difference as a FileCommandVisitor object.
     * @see FileCommandsVisitor
     * @param list1 - First list
     * @param list2 - Second list
     * @return The difference between both lists a FileCommandVisitor object
     */
    static FileCommandsVisitor getDiffAsFileCommandsVisitor(List<String> list1, List<String> list2) {
        Iterator<String> ifile1 = list1.iterator();
        Iterator<String> ifile2 = list2.iterator();

        // Initialize visitor.
        FileCommandsVisitor fileCommandsVisitor = new FileCommandsVisitor();

        // Read file line by line so that comparison can be done line by line.
        while (ifile1.hasNext() || ifile2.hasNext()) {
            /*
             * In case both files have different number of lines, fill in with empty
             * strings. Also append newline char at end so next line comparison moves to
             * next line.
             */
            String left = (ifile1.hasNext() ? ifile1.next() : "") + "\n";
            String right = (ifile2.hasNext() ? ifile2.next() : "") + "\n";

            // Prepare difference comparator with lines from both files.
            StringsComparator comparator = new StringsComparator(left, right);

            if (comparator.getScript().getLCSLength() > (Integer.max(left.length(), right.length()) * 0.4)) {
                /*
                 * If both lines have at least 40% commonality then only compare with each other
                 * so that they are aligned with each other in final difference HTML.
                 */
                comparator.getScript().visit(fileCommandsVisitor);
            } else {
                /*
                 * If both lines do not have 40% commonality then compare each with empty line so
                 * that they are not aligned to each other in final difference instead they show up on
                 * separate lines.
                 */
                StringsComparator leftComparator = new StringsComparator(left, "\n");
                leftComparator.getScript().visit(fileCommandsVisitor);
                StringsComparator rightComparator = new StringsComparator("\n", right);
                rightComparator.getScript().visit(fileCommandsVisitor);
            }
        }
        return fileCommandsVisitor;
    }

    static String getFileDiffAsHtml(List<String> list1, List<String> list2) throws IOException {
        FileCommandsVisitor fileCommandsVisitor = getDiffAsFileCommandsVisitor(list1, list2);
        if (fileCommandsVisitor.areFileIdentical()) {
            return "";
        } else {
            return fileCommandsVisitor.generateHTMLAsString();
        }
    }
}