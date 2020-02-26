package com.appraisers.app.gmail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GmailBuilderService {

    private List<String> destiny = new ArrayList<>();
    private List<String> cc = new ArrayList<>();
    private List<String> bcc = new ArrayList<>();
    private String subject = "";
    private String body = "";

    public GmailBuilderService setTo(String... to) {
        this.destiny.addAll(Arrays.asList(to));
        return this;
    }

    public GmailBuilderService setTo(String to) {
        this.destiny.add(to);
        return this;
    }

    public GmailBuilderService setCc(String... to) {
        this.cc.addAll(Arrays.asList(to));
        return this;
    }

    public GmailBuilderService setCc(String to) {
        this.cc.add(to);
        return this;
    }

    public GmailBuilderService setBcc(String... to) {
        this.bcc.addAll(Arrays.asList(to));
        return this;
    }

    public GmailBuilderService setBcc(String to) {
        this.bcc.add(to);
        return this;
    }

    public GmailBuilderService setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public GmailBuilderService setBody(String message) {
        this.body = message;
        return this;
    }

    public GmailBuilderService setBody(StringBuilder message) {
        this.body = message.toString();
        return this;
    }

    public boolean send(GmailService gmailService) {
        try {
            gmailService.sendMessage(this.destiny.toArray(new String[0]), this.subject, this.body, this.cc.toArray(new String[0]), this.bcc.toArray(new String[0]));
            return true;
        }catch (Exception ex) {
            return false;
        }
    }


}
