package com.appraisers.app.gmail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GmailBuilderService {

    private List<String> destiny = new ArrayList<>();
    private List<String> cc = new ArrayList<>();
    private List<String> bcc = new ArrayList<>();
    private String subject = "";
    private String body = "";

    @Value("${com.appraisers.app.testing.assignment.request.use.tester.only:true}")
    private boolean emailTesterOnly;

    @Value("${com.appraisers.app.testing.assignment.request.include.tester:true}")
    private boolean includeTester;

    @Value("${com.appraisers.app.tester.email}")
    private String testerEmail;

    public GmailBuilderService setTo(String... to) {
        this.addAll(Arrays.asList(to));
        return this;
    }

    public GmailBuilderService setTo(String to) {
        this.addAll(Arrays.asList(to));
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
        } catch (Exception ex) {
            return false;
        }
    }

    private GmailBuilderService addAll(List<String> all) {
        if (this.emailTesterOnly) {
            this.destiny.add(testerEmail);
        } else {
            if (includeTester) {
                this.destiny.add(testerEmail);
            }
            this.destiny.addAll(all);
        }
        return this;
    }
}
