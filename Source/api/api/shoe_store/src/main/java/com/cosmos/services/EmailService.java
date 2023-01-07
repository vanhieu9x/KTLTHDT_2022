package com.cosmos.services;

import com.sendgrid.helpers.mail.objects.Attachments;

import java.util.List;

public interface EmailService {

    void sendHtmlMessage(String to, String templateId,
                         DynamicTemplatePersonalization dynamicTemplatePersonalization);

    void sendHtmlMessageWithAttachment(String to, String templateId,
                                       DynamicTemplatePersonalization dynamicTemplatePersonalization, List<Attachments> attachments);


}
