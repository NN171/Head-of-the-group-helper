package com.immortalidiot.studentapp.auth;


public interface CallbackFragment {
    void changeFragment(FragmentUtils fragment, boolean isReturning);
    void setNavHostVisibility(int state);
}
