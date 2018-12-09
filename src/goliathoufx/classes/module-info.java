module goliathoufx
{
    requires java.logging;
    requires javafx.base;
    requires javafx.controls;
    requires goliath.nvsettings;
    requires goliath.nvsmi;
    requires goliath.nvxconfig;
    requires goliath.io;
    requires goliath.css;
    requires goliath.envious;
    requires java.desktop;
    
    exports goliathoufx;
    opens goliathoufx.css;
    opens goliathoufx.osd;
}
