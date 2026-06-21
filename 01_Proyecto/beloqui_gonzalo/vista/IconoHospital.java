package com.beloqui.vista;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public final class IconoHospital {
    private static final Color AZUL = new Color(32, 89, 138);
    private static final Color AZUL_CLARO = new Color(220, 238, 250);
    private static final Color ROJO = new Color(202, 45, 45);
    private static final String[] RUTAS_ICONO = {
        "02_imagenes/icono_hospital.png",
        "01_Proyecto/beloqui_gonzalo/05_recursos/icono_hospital.png",
        "05_recursos/icono_hospital.png"
    };

    private IconoHospital() {
    }

    public static Image crearImagen(int tamanio) {
        Image iconoArchivo = cargarIconoDesdeArchivo(tamanio);
        if (iconoArchivo != null) {
            return iconoArchivo;
        }
        return crearIconoRespaldo(tamanio);
    }

    public static ImageIcon crearIcono(int tamanio) {
        return new ImageIcon(crearImagen(tamanio));
    }

    private static Image cargarIconoDesdeArchivo(int tamanio) {
        for (String ruta : RUTAS_ICONO) {
            try {
                BufferedImage imagen = ImageIO.read(new File(ruta));
                if (imagen != null) {
                    return imagen.getScaledInstance(tamanio, tamanio, Image.SCALE_SMOOTH);
                }
            } catch (IOException e) {
                // Se intenta con la siguiente ruta disponible.
            }
        }
        return null;
    }

    private static Image crearIconoRespaldo(int tamanio) {
        BufferedImage imagen = new BufferedImage(tamanio, tamanio, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = imagen.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        int margen = Math.max(4, tamanio / 12);
        int edificioX = tamanio / 5;
        int edificioY = tamanio / 4;
        int edificioAncho = tamanio - edificioX * 2;
        int edificioAlto = tamanio - edificioY - margen;

        g.setColor(AZUL);
        g.fillRoundRect(0, 0, tamanio, tamanio, tamanio / 5, tamanio / 5);
        g.setColor(Color.WHITE);
        g.fillRoundRect(edificioX, edificioY, edificioAncho, edificioAlto, tamanio / 14, tamanio / 14);

        g.setColor(AZUL_CLARO);
        int ventana = Math.max(5, tamanio / 10);
        int separacion = Math.max(4, tamanio / 12);
        for (int fila = 0; fila < 2; fila++) {
            for (int columna = 0; columna < 3; columna++) {
                int x = edificioX + separacion + columna * (ventana + separacion);
                int y = edificioY + separacion + fila * (ventana + separacion);
                g.fillRoundRect(x, y, ventana, ventana, 3, 3);
            }
        }

        int cruzCentroX = tamanio / 2;
        int cruzCentroY = edificioY - margen / 2;
        int cruzLargo = tamanio / 4;
        int cruzAncho = Math.max(5, tamanio / 12);
        g.setColor(ROJO);
        g.fillRoundRect(
                cruzCentroX - cruzAncho / 2,
                cruzCentroY - cruzLargo / 2,
                cruzAncho,
                cruzLargo,
                3,
                3);
        g.fillRoundRect(
                cruzCentroX - cruzLargo / 2,
                cruzCentroY - cruzAncho / 2,
                cruzLargo,
                cruzAncho,
                3,
                3);

        g.setColor(AZUL);
        g.setStroke(new BasicStroke(Math.max(2, tamanio / 32)));
        g.drawRoundRect(edificioX, edificioY, edificioAncho, edificioAlto, tamanio / 14, tamanio / 14);

        int puertaAncho = tamanio / 5;
        int puertaAlto = tamanio / 4;
        g.fillRoundRect(
                cruzCentroX - puertaAncho / 2,
                edificioY + edificioAlto - puertaAlto,
                puertaAncho,
                puertaAlto,
                4,
                4);

        g.dispose();
        return imagen;
    }
}
