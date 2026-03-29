package org.example.util;

import static com.codeborne.selenide.Selenide.executeJavaScript;

public class JavaScriptUtils {
    public static void enableClickHighlight() {
        executeJavaScript("""
        document.addEventListener('click', function(e) {
            const el = e.target;
            el.style.outline = '3px solid limegreen';
            el.style.backgroundColor = 'rgba(0, 255, 0, 0.25)';
            setTimeout(() => {
                el.style.outline = '';
                el.style.backgroundColor = '';
            }, 400);
        }, true);
    """);
    }
}
