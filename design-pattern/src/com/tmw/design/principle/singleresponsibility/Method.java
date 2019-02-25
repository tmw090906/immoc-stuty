package com.tmw.design.principle.singleresponsibility;

/**
 * 方法单一职责
 */
public class Method {

    private void updateUserInfo(String username, String address) {
        username = "Echo";
        address = "Cheng Du";
    }

    private void updateUserInfo(String username, String... properties) {
        username = "Echo";
        //address = "Cheng Du";
    }

    private void updateUsername(String username) {
        username = "Echo";
    }
    private void updateUserAddress(String address) {
        address = "Cheng Du";
    }

    /**
     * 这里很明显可以分为两个方法， 然后由业务层来决定调用哪个方法
     */
    private void updateUserInfo(String username, String address, boolean bool) {
        if (bool) {
            //todo sth 1
        } else {
            //todo sth 2
        }
        username = "Echo";
        address = "Cheng Du";
    }


}
