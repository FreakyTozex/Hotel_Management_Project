/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MailSender;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
/**
 *
 * @author AEEstarreja
 */
public class EmailSender {
    
    // Defina suas credenciais de SMTP aqui
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String SMTP_USER = "a.aurdaneta25472@aeestarreja.pt";  // Substitua pelo seu e-mail
    private static final String SMTP_PASSWORD = "antoniodepinho0611";  // Substitua pela sua senha (ou senha de aplicativo)

    // Método para enviar o e-mail
    public static void enviarEmail(String destinatario, String assunto, String mensagemTexto) {
        // Configurações de propriedades para o servidor SMTP
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");  // Habilita STARTTLS

        // Criação da sessão com autenticação
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_USER, SMTP_PASSWORD);
            }
        });

        try {
            // Criação da mensagem
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SMTP_USER));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(assunto);
            message.setText(mensagemTexto);

            // Enviar o e-mail
            Transport.send(message);
            System.out.println("E-mail enviado com sucesso!");

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Erro ao enviar e-mail: " + e.getMessage());
        }
    }
}
