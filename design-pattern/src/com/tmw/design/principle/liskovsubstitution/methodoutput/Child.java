package com.tmw.design.principle.liskovsubstitution.methodoutput;

import java.util.HashMap;
import java.util.Map;

public class Child extends Base {
    @Override
    public HashMap method() {
        return new HashMap();
    }
}
