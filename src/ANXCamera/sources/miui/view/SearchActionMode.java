package miui.view;

import android.view.ActionMode;
import android.view.View;
import android.widget.EditText;

public interface SearchActionMode {

    public interface Callback extends ActionMode.Callback {
    }

    void addAnimationListener(ActionModeAnimationListener actionModeAnimationListener);

    EditText getSearchInput();

    void removeAnimationListener(ActionModeAnimationListener actionModeAnimationListener);

    void setAnchorView(View view);

    void setAnimateView(View view);

    void setResultView(View view);
}
