import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.util.*;

public class Assignment1_64050697_64050677 extends JPanel {
    public static void main(String [] args) {
        Assignment1_64050697_64050677 m = new Assignment1_64050697_64050677();
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

            plot(g, x, y, 3);
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
                plot(g, (int)x, (int)y, 3);
            }
        }
        else if (m >= -1 && m < 0) {
            y = Math.max(y1, y2);
            for (x = Math.max(x1, x2); x >= Math.min(x1, x2); x--) {
                y += m;
                plot(g, (int)x, (int)y, 3);
            }
        }
        else if (m > 1) {
            x = Math.min(x1, x2);
            for (y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
                x += 1 / m;
                plot(g, (int)x, (int)y, 3);
            }
        }
        else {
            x = Math.max(x1, x2);
            for (y = Math.max(y1, y2); y >= Math.min(y1, y2); y--) {
                x += 1 / m;
                plot(g, (int)x, (int)y, 3);
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


        g2.setColor(Color.decode("#853e17"));
        bezierCurve(g2, 154, 555, 155, 516, 163, 476, 177, 438); //left body

        bezierCurve(g2, 203, 444, 133, 440, 88, 378, 110, 325); //left cheek

        bezierCurve(g2, 110, 325, 105, 110, 440, 90, 470, 320); //left head to right head

        bezierCurve(g2, 470, 320, 510, 402, 449, 455, 383, 441); //right cheek 

        bezierCurve(g2, 406, 555, 405, 540, 404, 523, 402, 506); //right body 

        bezierCurve(g2, 398, 507, 415, 496, 423, 485, 407, 443); //outside right arm

        bezierCurve(g2, 402, 507, 333, 518, 328, 450, 392, 468); //inside right arm 

        bezierCurve(g2, 212, 493, 330, 540, 350, 430, 242, 460); //left arm

        bezierCurve(g2, 234, 162, 125, 136, 205, 1, 299, 155); //left ear

        bezierCurve(g2, 299, 155, 210, 47, 360, 10, 340, 160); //right ear

        bezierCurve(g2, 265, 155, 210, 133, 250, 90, 285, 155); //inside left ear

        bezierCurve(g2, 317, 155, 285, 98, 335, 87, 330, 157); //inside right ear



        bezierCurve(g2, 296, 317, 303, 323, 303, 333, 296, 339); //left mouth corner

        bezierCurve(g2, 334, 318, 329, 323, 329, 332, 334, 338); //right mouth corner

        bezierCurve(g2, 300, 326, 303, 331, 310, 330, 316, 325); //left mouth

        bezierCurve(g2, 316, 325, 321, 330, 326, 337, 331, 326); //right mouth



        bezierCurve(g2, 230, 310, 230, 290, 260, 290, 260, 310); //upper left eye

        bezierCurve(g2, 230, 310, 230, 330, 260, 330, 260, 310); //lower left eye

        bezierCurve(g2, 374, 310, 374, 290, 404, 290, 404, 310); //upper right eye

        bezierCurve(g2, 374, 310, 374, 330, 404, 330, 404, 310); //lower right eye



        g2.setColor(Color.decode("#a44f23"));
        bezierCurve(g2, 294, 455, 299, 440, 309, 408, 319, 379); //left carrot

        bezierCurve(g2, 361, 465, 358, 439, 353, 408, 346, 381); //right carrot

        bezierCurve(g2, 319, 379, 331, 378, 339, 379, 346, 381); //bottom carrot

        bezierCurve(g2, 314, 506, 321, 504, 333, 504, 339, 509); //carrot pole

        bezierCurve(g2, 293, 503, 300, 515, 308, 517, 321, 516); //left carrot head

        bezierCurve(g2, 358, 503, 354, 514, 344, 520, 330, 516); //right carrot head

        ddaLine(g2, 299, 448, 312, 448); //carrot pattern

        ddaLine(g2, 342, 406, 351, 406); //carrot pattern

        ddaLine(g2, 337, 398, 350, 398); //carrot pattern



        g2.setColor(Color.decode("#539635"));
        bezierCurve(g2, 323, 506, 321, 515, 320, 522, 319, 529); //left carrot stalk

        bezierCurve(g2, 329, 507, 329, 514, 330, 519, 330, 528); //right carrot stalk

        bezierCurve(g2, 320, 525, 306, 520, 298, 529, 304, 540); //carrot stalk

        bezierCurve(g2, 304, 540, 292, 547, 294, 558, 311, 557); //carrot stalk

        bezierCurve(g2, 311, 557, 321, 568, 340, 563, 339, 550); //carrot stalk

        bezierCurve(g2, 339, 550, 351, 552, 354, 536, 341, 533); //carrot stalk

        bezierCurve(g2, 341, 533, 347, 524, 336, 517, 330, 524); //carrot stalk



        g2.setColor(Color.decode("#853e17"));
        ddaLine(g2, 116, 492, 96, 492); //text box

        ddaLine(g2, 116, 492, 100, 480); //text box

        bezierCurve(g2, 96, 492, -30, 500, 120, 370, 100, 480); //left emotion

        ddaLine(g2, 464, 508, 486, 512); //text box

        bezierCurve(g2, 464, 508, 469, 504, 471, 499, 474, 496); //text box

        bezierCurve(g2, 474, 496, 520, 390, 559, 557, 486, 512); //right emotion



        g2.setColor(Color.decode("#ce4d67"));
        bezierCurve(g2, 92, 477, 100, 464, 86, 439, 77, 456); //left heart(right)

        bezierCurve(g2, 92, 477, 68, 485, 54, 456, 77, 456); //left heart(left)

        bezierCurve(g2, 493, 497, 485, 495, 500, 455, 510, 481); //right heart(left)

        bezierCurve(g2, 493, 497, 505, 507, 530, 491, 510, 481); //right heart(right)



        g2.setColor(Color.decode("#ffe3c3"));
        bezierCurve(g2, 112, 325, 176, 350, 285, 366, 296, 291); //left head pattern

        bezierCurve(g2, 295, 291, 315, 250, 320, 250, 335, 291); //center head pattern

        bezierCurve(g2, 335, 291, 340, 341, 395, 360, 468, 320); //right head pattern

        bezierCurve(g2, 227, 556, 229, 536, 231, 516, 233, 503); //left body pattern

        ddaLine(g2, 204, 446, 244, 447); //left body pattern

        ddaLine(g2, 244, 447, 243, 456); //left body pattern

        bezierCurve(g2, 270, 457, 260, 475, 262, 494, 264, 501); //left arm pattern

        bezierCurve(g2, 383, 468, 385, 478, 390, 492, 385, 505); //right arm pattern

        bezierCurve(g2, 386, 556, 387, 540, 388, 520, 384, 510); //right body patten

        bezierCurve(g2, 386, 444, 387, 450, 387, 456, 386, 463); //right body patten



        g2.setColor(Color.decode("#f6a499"));
        bezierCurve(g2, 227, 310, 220, 305, 195, 305, 200, 330); //upper left cheek pattern dot

        bezierCurve(g2, 200, 330, 205, 350, 237, 342, 235, 326); //lower left cheek pattern dot

        bezierCurve(g2, 407, 310, 420, 305, 432, 312, 430, 330); //upper right cheek pattern dot

        bezierCurve(g2, 430, 330, 425, 345, 397, 340, 398, 326); //lower right cheek pattern dot



        g2.setColor(Color.decode("#853e17"));
        ddaLine(g2, 154, 557, 296, 557); //below left body

        ddaLine(g2, 341, 555, 404, 555); //below right body



        buffer = floodFill(buffer, 300, 455, Color.WHITE, Color.decode("#ffa255")); //carrot color

        buffer = floodFill(buffer, 340, 530, Color.WHITE, Color.decode("#8dcd6c")); //carrot header color

        buffer = floodFill(buffer, 220, 530, Color.WHITE, Color.decode("#ffe3c3")); //left body pattern color

        buffer = floodFill(buffer, 390, 540, Color.WHITE, Color.decode("#ffe3c3")); //right body pattern color

        buffer = floodFill(buffer, 390, 478, Color.WHITE, Color.decode("#ffe3c3")); //right arm pattern color

        buffer = floodFill(buffer, 120, 320, Color.WHITE, Color.decode("#ffe3c3")); //head pattern color

        buffer = floodFill(buffer, 235, 145, Color.WHITE, Color.decode("#ffe3c3")); //left ear color

        buffer = floodFill(buffer, 300, 150, Color.WHITE, Color.decode("#ffe3c3")); //right ear color

        buffer = floodFill(buffer, 270, 150, Color.WHITE, Color.decode("#853e17")); //inside left ear color

        buffer = floodFill(buffer, 320, 150, Color.WHITE, Color.decode("#853e17")); //inside right ear color

        buffer = floodFill(buffer, 240, 310, Color.WHITE, Color.decode("#853e17")); //left eye color

        buffer = floodFill(buffer, 380, 310, Color.WHITE, Color.decode("#853e17")); //right eye color

        buffer = floodFill(buffer, 90, 475, Color.WHITE, Color.decode("#fd7488")); //left heart color

        buffer = floodFill(buffer, 493, 492, Color.WHITE, Color.decode("#fd7488")); //right heart color
        
        buffer = floodFill(buffer, 225, 312, Color.WHITE, Color.decode("#f6a499")); //left cheek pattern dot color
        
        buffer = floodFill(buffer, 410, 312, Color.WHITE, Color.decode("#f6a499")); //right cheek pattern dot color

        buffer = floodFill(buffer, 1, 1, Color.WHITE, Color.decode("#fcf3e8")); //background

        g.drawImage(buffer, 0, 0, null);
    }
}