import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.util.*;

public class Test extends JPanel {
    public static void main(String ... args) {
        Test m = new Test();
        JFrame f = new JFrame();
        f.add(m);
        f.setTitle("Bunny");
        f.setSize(600, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    public void bezierCurve(Graphics g, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        for (int i = 0; i <= 1000; i++) {
            double t = i / 1000.0;

            int x = (int)(Math.pow((1 - t), 3) * x1 + 
                    3 * t * Math.pow((1 - t), 2) * x2 + 
                    3 * Math.pow(t, 2) * (1 - t) * x3 +
                    Math.pow(t, 3) * x4); 
            
            int y = (int)(Math.pow((1 - t), 3) * y1 + 
                    3 * t * Math.pow((1 - t), 2) * y2 + 
                    3 * Math.pow(t, 2) * (1 - t) * y3 +
                    Math.pow(t, 3) * y4); 

            plot(g, x, y, 1);
        }
    }

    public void plot(Graphics g, int x, int y, int size) {
        g.fillRect(x, y, size, size);
    }

    public void ddaLine(Graphics g, int x1, int y1, int x2, int y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;

        double m = dy / dx;
        double x, y;

        if (m <= 1 && m >= 0) {
            y = Math.min(y1, y2);
            for (x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
                y += m;
                plot(g, (int)x, (int)y, 1);
            }
        }
        else if (m >= -1 && m < 0) {
            y = Math.max(y1, y2);
            for (x = Math.max(x1, x2); x >= Math.min(x1, x2); x--) {
                y += m;
                plot(g, (int)x, (int)y, 1);
            }
        }
        else if (m > 1) {
            x = Math.min(x1, x2);
            for (y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
                x += 1 / m;
                plot(g, (int)x, (int)y, 1);
            }
        }
        else {
            x = Math.max(x1, x2);
            for (y = Math.max(y1, y2); y >= Math.min(y1, y2); y--) {
                x += 1 / m;
                plot(g, (int)x, (int)y, 1);
            }
        }
    }

    public BufferedImage floodFill(BufferedImage m, int x, int y, Color targetColor, Color replacementColor) {
        Graphics2D g2 = m.createGraphics();
        Queue<Point> q = new LinkedList<>();
        
        if (new Color(m.getRGB(x, y)).equals(targetColor)) {
            g2.setColor(replacementColor);
            plot(g2, x, y, 1);
            q.add(new Point(x, y));
        }

        while (!q.isEmpty()) {
            Point p = q.poll();

            // south
            if (p.y < 600 && new Color(m.getRGB(p.x, p.y + 1)).equals(targetColor)) {
                g2.setColor(replacementColor);
                plot(g2, p.x, p.y + 1, 1);
                q.add(new Point(p.x, p.y + 1));
            }

            // north
            if (p.y > 0 && new Color(m.getRGB(p.x, p.y - 1)).equals(targetColor)) {
                g2.setColor(replacementColor);
                plot(g2, p.x, p.y - 1, 1);
                q.add(new Point(p.x, p.y - 1));
            }

            // east
            if (p.x < 600 && new Color(m.getRGB(p.x + 1, p.y)).equals(targetColor)) {
                g2.setColor(replacementColor);
                plot(g2, p.x + 1, p.y, 1);
                q.add(new Point(p.x + 1, p.y));
            }

            // west
            if (p.x > 0 && new Color(m.getRGB(p.x - 1, p.y)).equals(targetColor)) {
                g2.setColor(replacementColor);
                plot(g2, p.x - 1, p.y, 1);
                q.add(new Point(p.x - 1, p.y));
            }
        }
        return m;
    }

    public void paintComponent(Graphics g) {
        BufferedImage buffer = new BufferedImage(601, 601, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = buffer.createGraphics();

        
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, 600, 600);
        
        
        g2.setColor(Color.BLACK);
        bezierCurve(g2, 263, 227, 150, 280, 210, 430, 371, 365); //หัวซ้ายถึงขวาล่าง

        bezierCurve(g2, 370, 365, 372, 364, 382, 357, 387, 349); //หัวขวาล่างตรงแก้ม

        bezierCurve(g2, 381, 349, 415, 348, 440, 330, 450, 300); //แก้มขวาล่าง (ล่าง)

        bezierCurve(g2, 450, 300, 436, 295, 418, 294, 402, 307); //แก้มขวาล่าง (บน)

        bezierCurve(g2, 402, 307, 413, 296, 418, 283, 417, 270); //แก้มขวาบน (ล่าง)

        bezierCurve(g2, 417, 270, 400, 270, 384, 290, 380, 300); //แก้มขวาบน (บน)

        bezierCurve(g2, 392, 283, 385, 258, 370, 240, 352, 232); //หัวขวาบน

        bezierCurve(g2, 335, 241, 371, 225, 398, 202, 416, 168); //เศษหูขวาล่าง

        bezierCurve(g2, 416, 168, 403, 168, 384, 176, 375, 185); //เศษหูขวาบน

        bezierCurve(g2, 393, 173, 413, 128, 426, 72, 418, 14); //หูขวา (ขวา)

        bezierCurve(g2, 418, 14, 358, 38, 323, 89, 308, 132); //หูขวา (ซ้าย)

        bezierCurve(g2, 310, 124, 300, 85, 278, 45, 242, 14); //หูซ้าย (ขวา)

        bezierCurve(g2, 242, 14, 215, 93, 222, 168, 263, 227); //หูซ้าย (ซ้าย)

        bezierCurve(g2, 208, 279, 203, 273, 194, 262, 185, 259); //แก้มซ้ายบน (บน)

        bezierCurve(g2, 185, 259, 179, 267, 182, 281, 190, 293); //แก้มซ้ายบน (ล่าง)

        bezierCurve(g2, 190, 293, 177, 288, 164, 290, 154, 289); //แก้มซ้ายล่าง (บน)

        bezierCurve(g2, 154, 289, 161, 318, 191, 338, 211, 336); //แก้มซ้ายล่าง (ล่าง)

        bezierCurve(g2, 308, 156, 303, 173, 302, 194, 304, 210); //ขีดกลางหู

        bezierCurve(g2, 320, 223, 344, 194, 363, 159, 372, 110); //ในหูขวา (ขวา)

        bezierCurve(g2, 372, 110, 344, 137, 323, 183, 320, 223); //ในหูขวา (ซ้าย)

        bezierCurve(g2, 287, 223, 286, 182, 277, 147, 258, 112); //ในหูซ้าย (ขวา)

        bezierCurve(g2, 258, 112, 256, 152, 264, 191, 287, 221); //ในหูซ้าย (ซ้าย)



        bezierCurve(g2, 267, 266, 282, 264, 301, 264, 324, 269); //แผ่นแปะจมูก

        bezierCurve(g2, 324, 269, 326, 280, 326, 296, 321, 304); //แผ่นแปะจมูก

        ddaLine(g2, 321, 304, 261, 304); //แผ่นแปะจมูก

        bezierCurve(g2, 267, 266, 260, 277, 259, 291, 261, 304); //แผ่นแปะจมูก



        bezierCurve(g2, 225, 289, 233, 269, 248, 269, 248, 289); //ตาซ้ายบน

        bezierCurve(g2, 225, 289, 226, 311, 244, 311, 248, 289); //ตาซ้ายล่าง

        bezierCurve(g2, 231, 280, 235, 271, 248, 271, 243, 280); //ตาขาวบน

        bezierCurve(g2, 231, 280, 232, 289, 240, 292, 244, 280); //ตาขาวล่าง

        bezierCurve(g2, 225, 297, 231, 290, 242, 291, 244, 299); //ตาส้ม



        bezierCurve(g2, 338, 298, 343, 278, 358, 278, 362, 298); //ตาขวาบน

        bezierCurve(g2, 338, 298, 342, 320, 360, 320, 362, 298); //ตาขวาล่าง

        bezierCurve(g2, 344, 288, 347, 280, 355, 281, 356, 288); //ตาขาวบน

        bezierCurve(g2, 344, 288, 346, 300, 355, 300, 356, 288); //ตาขาวล่าง

        bezierCurve(g2, 342, 307, 345, 299, 356, 299, 359, 308); //ตาส้ม



        bezierCurve(g2, 282, 304, 285, 311, 294, 311, 297, 304); //จมูก

        bezierCurve(g2, 290, 309, 287, 319, 287, 329, 289, 342); //ขีดจมูกถึงปาก

        bezierCurve(g2, 258, 330, 273, 348, 301, 348, 325, 330); //ปาก



        bezierCurve(g2, 284, 381, 290, 390, 268, 390, 258, 376); //ปลอกคอซ้าย

        bezierCurve(g2, 303, 381, 298, 394, 321, 391, 339, 375); //ปลอกคอขวา



        bezierCurve(g2, 260, 380, 120, 445, 180, 530, 265, 399); //แขนซ้าย

        bezierCurve(g2, 181, 467, 187, 450, 195, 440, 200, 435); //ลายแขนซ้าย

        bezierCurve(g2, 335, 380, 460, 440, 430, 530, 323, 399); //แขนขวา

        bezierCurve(g2, 410, 463, 408, 455, 405, 445, 392, 435); //ลายแขนขวา

        bezierCurve(g2, 265, 399, 252, 438, 252, 464, 257, 475); //ตัวซ้าย

        bezierCurve(g2, 332, 410, 336, 445, 337, 465, 330, 475); //ตัวขวา

        bezierCurve(g2, 257, 475, 245, 493, 237, 513, 238, 530); //ขาซ้าย (ซ้าย)

        bezierCurve(g2, 238, 530, 228, 565, 175, 500, 144, 550); //เท้าซ้าย (บน)

        bezierCurve(g2, 150, 540, 144, 552, 140, 560, 143, 569); //ปลายเท้าซ้าย

        bezierCurve(g2, 142, 565, 156, 610, 270, 585, 265, 565); //เท้าซ้าย (ล่าง)

        bezierCurve(g2, 265, 565, 267, 554, 258, 538, 261, 531); //ส้นเท้าซ้าย

        bezierCurve(g2, 261, 531, 259, 520, 264, 500, 274, 484); //ขาซ้าย (ขวา)

        bezierCurve(g2, 274, 484, 281, 490, 298, 490, 312, 484); //ก้น

        bezierCurve(g2, 312, 485, 324, 499, 334, 527, 335, 546); //ขาขวา (ซ้าย)

        bezierCurve(g2, 335, 546, 331, 550, 328, 563, 331, 568); //ส้นเท้าขวา

        bezierCurve(g2, 330, 567, 375, 590, 425, 585, 439, 568); //เท้าขวา (ล่าง)

        bezierCurve(g2, 439, 568, 447, 561, 444, 548, 436, 536); //ปลายเท้าขวา

        bezierCurve(g2, 436, 536, 398, 520, 374, 545, 357, 535); //เท้าขวาบน

        bezierCurve(g2, 357, 535, 350, 513, 345, 491, 330, 475); //ขาขวา (ขวา)

        bezierCurve(g2, 150, 576, 148, 562, 156, 551, 166, 548); //นิ้วเท้าซ้าย (บน)

        bezierCurve(g2, 164, 584, 163, 566, 171, 557, 180, 555); //นิ้วเท้าซ้าย (ล่าง)

        bezierCurve(g2, 439, 567, 440, 553, 436, 547, 431, 543); //นิ้วเท้าขวา (บน)

        bezierCurve(g2, 425, 575, 426, 562, 421, 553, 413, 550); //นิ้วเท้าขวา (ล่าง)

        

        g2.setColor(Color.decode("#e05639"));
        bezierCurve(g2, 226, 123, 243, 107, 275, 96, 300, 99); //ลายสีส้มเข้มหูซ้าย

        bezierCurve(g2, 325, 97, 352, 94, 384, 103, 411, 123); //ลายสีส้มเข้มหูขวา

        bezierCurve(g2, 192, 533, 210, 545, 216, 562, 213, 587); //ลายเท้าซ้าย

        bezierCurve(g2, 407, 531, 389, 539, 383, 549, 385, 581); //ลายเท้าขวา


        
        g2.setColor(Color.decode("#faa566"));
        bezierCurve(g2, 230, 153, 240, 143, 250, 140, 257, 136); //ลายสีส้มอ่อนหูซ้าย (ซ้าย)

        bezierCurve(g2, 268, 131, 279, 125, 292, 123, 309, 124); //ลายสีส้มอ่อนหูซ้าย (ขวา)

        bezierCurve(g2, 311, 124, 326, 122, 337, 123, 353, 129); //ลายสีส้มอ่อนหูขวา (ซ้าย)

        bezierCurve(g2, 367, 133, 380, 136, 391, 140, 401, 151); //ลายสีส้มอ่อนหูขวา (ขวา)



        buffer = floodFill(buffer, 243, 107, Color.WHITE, Color.decode("#e05639")); //ลายสีส้มเข้มหูซ้าย

        buffer = floodFill(buffer, 352, 94, Color.WHITE, Color.decode("#e05639")); //ลายสีส้มเข้มหูขวา

        buffer = floodFill(buffer, 240, 143, Color.WHITE, Color.decode("#faa566")); //ลายสีส้มอ่อนหูซ้าย

        buffer = floodFill(buffer, 326, 122, Color.WHITE, Color.decode("#faa566")); //ลายสีส้มอ่อนหูขวา

        buffer = floodFill(buffer, 260, 152, Color.WHITE, Color.decode("#f5cf7f")); //ในหูซ้าย

        buffer = floodFill(buffer, 338, 194, Color.WHITE, Color.decode("#f5cf7f")); //ในหูขวา

        buffer = floodFill(buffer, 282, 270, Color.WHITE, Color.decode("#fad07c")); //แผ่นแปะจมูก
        
        buffer = floodFill(buffer, 280, 381, Color.WHITE, Color.decode("#de5739")); //ปลอกคอซ้าย

        buffer = floodFill(buffer, 310, 381, Color.WHITE, Color.decode("#de5739")); //ปลอกคอขวา

        buffer = floodFill(buffer, 200, 545, Color.WHITE, Color.decode("#de5739")); //เท้าซ้าย

        buffer = floodFill(buffer, 395, 539, Color.WHITE, Color.decode("#de5739")); //เท้าขวา

        buffer = floodFill(buffer, 285, 306, Color.WHITE, Color.decode("#e9c4a8")); //จมูก

        buffer = floodFill(buffer, 229, 289, Color.WHITE, Color.decode("#000000")); //ตาดำซ้าย

        buffer = floodFill(buffer, 230, 297, Color.WHITE, Color.decode("#de5739")); //ตาส้มซ้าย

        buffer = floodFill(buffer, 340, 298, Color.WHITE, Color.decode("#000000")); //ตาดำขวา

        buffer = floodFill(buffer, 345, 307, Color.WHITE, Color.decode("#de5739")); //ตาส้มขวา

        buffer = floodFill(buffer, 1, 1, Color.WHITE, Color.decode("#e3e8e8")); //พื้นหลัง

        g.drawImage(buffer, 0, 0, null);
    }
}