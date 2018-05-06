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
package automenta.vivisect.swing.property.util;

import java.awt.Toolkit;

import javax.swing.UIManager;

/**
 * Provides methods related to the runtime environment.
 */
public class OS {

  private static final boolean osIsMacOsX;
  private static final boolean osIsWindows;
  private static final boolean osIsWindowsXP;
  private static final boolean osIsWindows2003;
  private static final boolean osIsWindowsVista;
  private static final boolean osIsLinux;

  static {
    String os = System.getProperty("os.name").toLowerCase();

    osIsMacOsX = "mac os x".equals(os);
    osIsWindows = os.contains("windows");
    osIsWindowsXP = "windows xp".equals(os);
    osIsWindows2003 = "windows 2003".equals(os);
    osIsWindowsVista = "windows vista".equals(os);
    osIsLinux = os != null && os.contains("linux");
  }

  /**
   * @return true if this VM is running on Mac OS X
   */
  public static boolean isMacOSX() {
    return osIsMacOsX;
  }

  /**
   * @return true if this VM is running on Windows
   */
  public static boolean isWindows() {
    return osIsWindows;
  }

  /**
   * @return true if this VM is running on Windows XP
   */
  public static boolean isWindowsXP() {
    return osIsWindowsXP;
  }

  /**
   * @return true if this VM is running on Windows 2003
   */
  public static boolean isWindows2003() {
    return osIsWindows2003;
  }

  /**
   * @return true if this VM is running on Windows Vista
   */
  public static boolean isWindowsVista() {
    return osIsWindowsVista;
  }
  
  /**
   * @return true if this VM is running on a Linux distribution
   */
  public static boolean isLinux() {
    return osIsLinux;
  }

  /**
   * @return true if the VM is running Windows and the Java
   *         application is rendered using XP Visual Styles.
   */
  public static boolean isUsingWindowsVisualStyles() {
    if (!isWindows()) {
      return false;
    }

    boolean xpthemeActive = Boolean.TRUE.equals(Toolkit.getDefaultToolkit()
        .getDesktopProperty("win.xpstyle.themeActive"));
    if (!xpthemeActive) {
      return false;
    } else {
      try {
        return System.getProperty("swing.noxp") == null;
      } catch (RuntimeException e) {
        return true;
      }
    }
  }

  /**
   * Returns the name of the current Windows visual style.
   * <ul>
   * <li>it looks for a property name "win.xpstyle.name" in UIManager and if not found
   * <li>it queries the win.xpstyle.colorName desktop property ({@link Toolkit#getDesktopProperty(java.lang.String)})
   * </ul>
   * 
   * @return the name of the current Windows visual style if any. 
   */
  public static String getWindowsVisualStyle() {
    String style = UIManager.getString("win.xpstyle.name");
    if (style == null) {
      // guess the name of the current XPStyle
      // (win.xpstyle.colorName property found in awt_DesktopProperties.cpp in
      // JDK source)
      style = (String)Toolkit.getDefaultToolkit().getDesktopProperty(
        "win.xpstyle.colorName");
    }
    return style;
  }
  
}
