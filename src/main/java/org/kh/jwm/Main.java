package org.kh.jwm;

import io.github.humbleui.jwm.App;
import io.github.humbleui.jwm.Event;
import io.github.humbleui.jwm.EventWindowCloseRequest;
import io.github.humbleui.jwm.LayerGL;
import io.github.humbleui.jwm.Window;
import io.github.humbleui.jwm.skija.EventFrameSkija;
import io.github.humbleui.jwm.skija.LayerGLSkija;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.Surface;

import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        App.start(() -> {
            Window window = App.makeWindow();
            window.setEventListener(new EventHandler(window));
            window.setVisible(true);
        });


    }

    static class EventHandler implements Consumer<Event> {
        private final Window window;
        public final LayerGL layer;

        public EventHandler(Window window) {
            this.window = window;
            layer = new LayerGLSkija();
            window.setLayer(layer);
        }
            
        @Override
        public void accept(Event t) {
            System.out.println(t);

            if (t instanceof EventWindowCloseRequest) {
                window.close();
                App.terminate();
            } else if (t instanceof EventFrameSkija ee) {
                final Surface surface = ee.getSurface();
                paint(surface);
            }
        }

        public void paint(Surface surface) {
            layer.makeCurrent();
            // do the drawing
            Paint paint = new Paint();
            paint.setColor(0xFFFF0000);
            surface.getCanvas().drawCircle(50, 50, 30, paint);
            layer.swapBuffers();
        }
    }
}