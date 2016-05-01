/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.pars.opennlp.persian.sent;

import java.io.File;
import java.io.IOException;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

/**
 *
 * @author Behrang QasemiZadeh <me at atmykitchen.info>
 */
public class UseSentenceSplitting {
    public static void main(String[]s) throws IOException{
        SentenceModel model = new SentenceModel(new File("lang/fa/fa-sent.bin"));
        SentenceDetectorME sentence = new SentenceDetectorME(model);
        
        // for (File file : f.listFiles()) {
        String ssss = "وی خطاب به آنها گفت: «شما نمی‌توانید هر دو را با هم بخواهید».\n"
                + "\n"
                + "رئیس‌جمهوری آمریکا تصریح کرد، سوریه یکی از بسیاری موضوعاتی است که ماهیت فراملی دارد و نیازمند یک واکنش فراملی است».\n"
                + "\n"
                + "وی در اینباره گفت: «فکر می‌کنم برای بسیاری وسوسه‌انگیز است که بتوانیم پل متحرک را متوقف کنیم و یک خندق در اطراف خود حفر کنیم مجبور نباشیم با مشکلات سراسر جهان روبرو شویم اما بدون همکاری و ائتلاف، ضعیف‌تر می‌شویم و این مشکلات حل نمی‌شود».\n"
                + "\n"
                + "وی درباره شکست داعش گفت: «ادامه دادن مبارزه، ضروری است و گرچه پیش‌بینی نمی‌کنم که در 9 ماه بعدی، مبارزه به پایان برسد، زیرا متأسفانه، حتی تعداد اندکی از افراط‌گرایان خود را برای مردن آماده کنند، هنوز می‌توانند خرابی‌های زیادی را در شهرهای ما به وجود آورند اما من فکر می‌کنم که ما می‌توانیم به آهستگی محیطی را که آنها در آن عملیات انجام می‌دهند را کوچکتر کنیم و بر پناهگاههای آنها همچون موصل و رقه که قلب جنبش آنها در آنجا است، تسلط یابیم».\n"
                + "\n"
                + "مذاکرات کنونی ژنو بین دولت و معارضان سوری تا هفته آینده ادامه دارد اما تاکنون نتایجی در پی نداشته است. معارضان سوری دولت اسد را متهم به نقض آتش‌بش می‌کنند.";
        String[] sentences = sentence.sentDetect(ssss);
        for (String sen : sentences) {
            System.out.println(sen);
        }
    }
}
