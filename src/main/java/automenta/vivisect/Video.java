/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automenta.vivisect;

import java.awt.*;
import java.io.IOException;

/**
 *
 * @author me
 */
public class Video {
    public static Font FontAwesome;
    public static Font monofont;
 

        static {
        System.setProperty("sun.java2d.opengl","True");        
    }

    
    static {
        Video.monofont = new Font(Font.MONOSPACED, Font.PLAIN, 12);
        /*
        try {
            //monofont = Font.createFont(Font.TRUETYPE_FONT, NARSwing.class.getResourceAsStream("Inconsolata-Regular.ttf"));
            
            
        } catch (FontFormatException ex) {
            Logger.getLogger(NARSwing.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NARSwing.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
    }
        
    
    static {
        try {
            Video.FontAwesome = Font.createFont(Font.TRUETYPE_FONT, Video.class.getResourceAsStream("/FontAwesome.ttf")).deriveFont(Font.PLAIN, 14);
        } catch (FontFormatException e) {
            throw new IllegalStateException("Font format invalid", e);
        } catch (IOException e) {
            throw new IllegalStateException("Unexpected IOException", e);
        }

    }

    
  public static final int color(int x, int y, int z, int a) {
    
      if (a > 255) a = 255; else if (a < 0) a = 0;
      if (x > 255) x = 255; else if (x < 0) x = 0;
      if (y > 255) y = 255; else if (y < 0) y = 0;
      if (z > 255) z = 255; else if (z < 0) z = 0;

      return (a << 24) | (x << 16) | (y << 8) | z;
  }


  public static final int color(float x, float y, float z, float a) {
      if (a > 255) a = 255; else if (a < 0) a = 0;
      if (x > 255) x = 255; else if (x < 0) x = 0;
      if (y > 255) y = 255; else if (y < 0) y = 0;
      if (z > 255) z = 255; else if (z < 0) z = 0;

      return ((int)a << 24) | ((int)x << 16) | ((int)y << 8) | (int)z;
  }
  
    public static int getColor(final String s, final float alpha) {
        float hue = (((float)s.hashCode()) / Integer.MAX_VALUE);
        return colorHSB(hue,0.8f,0.9f,alpha);
    }    

    public static int getColor(final Class c) {            
        float hue = (((float)c.hashCode()) / Integer.MAX_VALUE);
        return color(hue,0.8f,0.9f,1f);
    }
    
    public static int getColor(final String s) {            
        float hue = (((float)s.hashCode()) / Integer.MAX_VALUE);
        return color(hue,0.8f,0.9f,1f);
    }

    public static Font fontMono(float size) {
        return monofont.deriveFont(size);
    }

    public static final float hashFloat(final int h) {
        return (h) / (((float) Integer.MAX_VALUE) - ((float) Integer.MIN_VALUE));
    }

    @Deprecated public static final Color getColor(final String s, final float saturation, final float brightness) {
        return getColor(s.hashCode(), saturation, brightness);
    }

    @Deprecated public static final Color getColor(int hashCode, float saturation, float brightness) {
        float hue = hashFloat(hashCode);
        return Color.getHSBColor(hue, saturation, brightness);
    }

    public static final int getColor(int hashCode, float saturation, float brightness, float alpha) {
        return colorHSB(hashCode, saturation, brightness, alpha);
    }

    @Deprecated public static final Color getColor(final Color c, float alpha) {
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), (int) (255.0 * alpha));
    }

    public static final int getColor(final String s, float sat, float bright, float alpha) {
        return colorHSB( hashFloat(s.hashCode()), sat, bright, alpha);
    }

    public static int colorHSB(float hue, float saturation, float brightness, float alpha) {
        return Color.HSBtoRGB(hue, saturation, brightness) & 0x00ffffff | ((int)(255f*alpha) << 24);
    }
  
}
