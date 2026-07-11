package NotificationService.Notifciation.serviceimpl;

import NotificationService.Notifciation.dto.NotificationRequestDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class NotificationService {
    private final JavaMailSender mailSender;

    public NotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    public void sendOrderConfirmation(NotificationRequestDto dto) {

        try {

            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true);

            helper.setTo(dto.getEmail());

            helper.setSubject("Order Confirmation - " + dto.getOrderNumber());

            String html = buildHtml(dto);

            helper.setText(html, true);

            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send email", e);
        }
    }
    private String buildHtml(NotificationRequestDto dto) {

        return """
                <!DOCTYPE html>
                <html>
                <body style="font-family:Arial,sans-serif;background:#f4f4f4;padding:20px">

                <div style="max-width:700px;margin:auto;background:white;border-radius:8px;padding:30px">

                <h2 style="color:#2E86C1;">
                Order Confirmation
                </h2>

                <p>
                Thank you for shopping with us.
                Your order has been received successfully.
                </p>

                <table style="border-collapse:collapse;width:100%%">

                    <tr>
                        <td><b>Order Number</b></td>
                        <td>%s</td>
                    </tr>
                    <tr>
                        <td><b>Product Name</b></td>
                        <td>%s</td>
                    </tr>
                     <tr>
                        <td><b>Product Description</b></td>
                        <td>%s</td>
                    </tr>

                    <tr>
                        <td><b>Status</b></td>
                        <td>%s</td>
                    </tr>

                    <tr>
                        <td><b>Quantity</b></td>
                        <td>%s</td>
                    </tr>

                    <tr>
                        <td><b>Price</b></td>
                        <td>₹ %s</td>
                    </tr>

                    <tr>
                        <td><b>Total Amount</b></td>
                        <td>₹ %s</td>
                    </tr>

                    <tr>
                        <td><b>Shipping Address</b></td>
                        <td>%s</td>
                    </tr>

                </table>

                <br>

                <p>
                Your order is currently being processed.
                We'll notify you once it has been shipped.
                </p>

                <hr>

                <center>
                <small>
                Thank you for choosing Genesys Product Store.
                </small>
                </center>

                </div>

                </body>
                </html>
                """.formatted(
                dto.getOrderNumber(),
                dto.getProductName(),
                dto.getProductDescription(),
                dto.getProcessingStatus(),
                dto.getQuantity(),
                dto.getPrice(),
                dto.getTotalAmount(),
                dto.getShippingAddress()
        );
    }


}
