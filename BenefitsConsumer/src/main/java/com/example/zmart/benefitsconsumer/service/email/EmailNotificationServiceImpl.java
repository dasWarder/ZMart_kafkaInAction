package com.example.zmart.benefitsconsumer.service.email;

import com.example.zmart.benefitsconsumer.dto.info.PartnerBonusesResponse;
import com.example.zmart.benefitsconsumer.dto.info.PartnersBonuses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailNotificationServiceImpl implements EmailNotificationService {

  @Value("${mail.template.bonuses}")
  private String bonusTemplate;

  private final JavaMailSender javaMailSender;

  private final TemplateEngine templateEngine;

  @Override
  public void sendBenefits(PartnerBonusesResponse response) {

    log.info("Send bonuses to a client with an email = {}", response.getClientEmail());

    if(response.getBonuses().isEmpty()) {
      return;
    }

    List<PartnersBonuses> bonuses = response.getBonuses();
    String templateInContext = buildEmailContext(bonuses);
    MimeMessagePreparator preparator =
        buildMimeMessagePreparatorWithMessageForClient(
            response.getClientEmail(), templateInContext);

    javaMailSender.send(preparator);
  }

  private String buildEmailContext(List<PartnersBonuses> partnersBonuses) {

    Context context = new Context();

    context.setVariable("partnerBonuses", partnersBonuses);
    String process = templateEngine.process(bonusTemplate, context);

    return process;
  }

  private MimeMessagePreparator buildMimeMessagePreparatorWithMessageForClient(
      String email, String template) {

    MimeMessagePreparator preparator =
        mimeMessage -> {
          MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
          helper.setSubject(email);
          helper.setText(template, true);
          helper.setTo(email);
        };

    return preparator;
  }
}
