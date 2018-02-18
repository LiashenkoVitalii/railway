package com.liashenko.app.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.Writer;

//Custom jstl-tag designed to implement pagination on the view
public class PaginationTaglib extends SimpleTagSupport {

    //	action to be hit when clicked.
    private String uri;

    //	the offset of pagination.
    private int offset;

    //	total number of elements to be shown.
    private int count;

    //	maximum number of pages to be shown in the pagination bar.
    private int max;

    //	maximum number of elements to be shown per page.
    private int steps;

    //	text to be shown for previous page link.
    private String previous = "Previous";

    //	text to be shown for next page link.
    private String next = "Next";

    private Writer getWriter() {
        JspWriter out = getJspContext().getOut();
        return out;
    }

    @Override
    public void doTag() throws JspException {
        Writer out = getWriter();
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("<nav>");
            sb.append("<ul class=\"pagination\" pull-right>");

            //first visible page
            if (offset < steps) {
                sb.append(constructLink(1, previous, "disabled", true));
            } else {
                sb.append(constructLink(offset - steps, previous, null, false));
            }

            if (steps == 0) steps = count;
            double offsetD = (double) offset;
            double countD = (double) count;
            double stepsD = (double) steps;
            double maxD = (double) max;

            int currentPage = 1 + (int) Math.ceil(offsetD / stepsD);
            int pagesCount = (int) Math.ceil(countD / stepsD);
            int leftBorderPage = 1;
            int rightBorderPage = 1 + max;

            if (currentPage > (max - 1)) {
                int halfOfMax = (int) Math.ceil(maxD / 2);
                leftBorderPage = currentPage - halfOfMax;
                rightBorderPage = currentPage + halfOfMax;
            }

            for (int i = 1; i <= pagesCount; i++) {
                if ((i >= leftBorderPage) & (i <= rightBorderPage)) {
                    if (currentPage == i) {
                        sb.append(constructLink((i - 1) * steps, String.valueOf(i), "active", true));
                    } else {
                        sb.append(constructLink((i - 1) * steps, String.valueOf(i), null, false));
                    }
                }
            }

            //last visible page
            if (offset + steps >= count) {
                sb.append(constructLink(offset + steps, next, "disabled", true));
            } else {
                sb.append(constructLink(offset + steps, next, null, false));
            }

            sb.append("</ul>");
            sb.append("</nav>");
            out.write(sb.toString());
        } catch (java.io.IOException ex) {
            throw new JspException("Error in Paginator tag", ex);
        }
    }

    private String constructLink(int page, String text, String className, boolean disabled) {


        StringBuilder link = new StringBuilder("<li");
        if (className != null) {
            link.append(" class=\"");
            link.append(className);
            link.append("\"");
        }
        if (disabled) {
            link.append(">")
                    .append("<a href=\"#\">")
                    .append(text)
                    .append("</a></li>");
        } else {
            link.append(">")
                    .append("<a href=\"")
                    .append(uri)
                    .append("?offset=")
                    .append(page)
                    .append("\">")
                    .append(text)
                    .append("</a></li>");
        }
        return link.toString();
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }
}

