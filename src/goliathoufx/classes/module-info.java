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
    
    exports goliathoufx;
    opens goliathoufx.css;
    opens goliathoufx.osd;
}
